package com.noplugins.keepfit.android.util.net;


import android.content.Context;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.interceptor.LogInterceptor;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by shiyujia02 on 2017/8/16.
 */
public class Network {
    public static final int DEFAULT_TIMEOUT = 7;
    private static Network mInstance;
    public static String token = "";
    public MyService service;
    //测试服
    public static String main_url = "https://app.shiyujia.com/";
    //正式服
//    public static String main_url = "https://app.shiyujia.com/";
//    public static String main_url = "https://appuat.shiyujia.com/";
    //public static String main_url = "https://appprd.shiyujia.com/";//临时测试正式服

    //public static String bucketPath = "appDebug/";//阿里测试服图片库地址
    public static String bucketPath = "appProduction/";//阿里正式服图片库地址

    //分享测试路径图片
    public static String ShareImage = "https://s3-011-shinho-syj-uat-bjs.s3.cn-north-1.amazonaws.com.cn/syjapp/2018_07/applogo.png";
    //分享正式路径图片
//    public static String ShareImage = "https://s3-014-shinho-syj-prd-bjs.s3.cn-north-1.amazonaws.com.cn/syjapp/2018_07/applogo.png";

    //分享网页测试地址
    public static String ShareUrl = Network.main_url +"answerPhone2/index.html#/";
    //    分享网页正式地址
//    public static String ShareUrl = Network.main_url +"answerPhone/index.html#/";
    //视频分享
    public static String VideoUrl = main_url + "answerPhone2/index.html?from=singlemessage#/videos?id=";
    public static String ImageTextUrl = main_url + "answerPhone2/index.html?from=singlemessage#/images?id=";

    //获取单例
    public static Network getInstance(String method, Context context) {
        if (context != null) {
            if ("".equals(SharedPreferencesHelper.get(context, "login_token", "").toString())) {
                token = "";
                Log.e("没有添加token", token);
            } else {
                token = SharedPreferencesHelper.get(context, "login_token", "").toString();
                Log.e("添加的头部的token", token);
            }
            if (mInstance == null) {
                synchronized (Network.class) {
                    mInstance = new Network(method, context);
                }
            }
        }
        return mInstance;
    }

    Retrofit retrofit;

    private Network(String method, Context context) {

        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};

        // Install the all-trusting trust manager
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory())//去掉okhttp https证书验证
                .addInterceptor(new LogInterceptor(method))//添加日志拦截器
                .addInterceptor(new Interceptor() {//添加token
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("token", token)
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                })
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MINUTES)//设置写的超时时间
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MINUTES)//超时处理
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MINUTES)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Network.main_url)//设置请求网址根部
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        service = retrofit.create(MyService.class);

    }

    public static void trustAllHttpsCertificates()
            throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[1];
        trustAllCerts[0] = new TrustAllManager();
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(
                sc.getSocketFactory());
    }

    private static class TrustAllManager
            implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkServerTrusted(X509Certificate[] certs,
                                       String authType)
                throws CertificateException {
        }

        public void checkClientTrusted(X509Certificate[] certs,
                                       String authType)
                throws CertificateException {
        }
    }


    /**
     * 登录
     * @param params
     * @param subscriber
     * @return
     */
    public Subscription login(Map<String, String> params, Subscriber<Bean<Object>> subscriber) {
        return service.fast_login(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }



}
