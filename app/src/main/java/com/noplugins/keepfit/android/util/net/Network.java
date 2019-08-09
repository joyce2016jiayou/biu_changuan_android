package com.noplugins.keepfit.android.util.net;


import android.content.Context;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.entity.Token;
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
import okhttp3.RequestBody;
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
    public MyService service;
    public static String token = "";
    //测试服
    //public static String main_url = "http://192.168.1.205:8888/api/gym-service/";

    public static String main_url = "http://kft.ahcomg.com/api/gym-service/";

    public static String place_number = "GYM19072138381319";

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


//    /**
//     * 登录
//     *
//     * @param params
//     * @param subscriber
//     * @return
//     */
//    public Subscription login(Map<String, String> params, Subscriber<Bean<Object>> subscriber) {
//        return service.fast_login(params)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }

    /**
     * 获取验证码
     *
     * @param subscriber
     * @return
     */
    public Subscription get_yanzhengma(Map<String, String> params, Subscriber<Bean<Object>> subscriber) {
        return service.get_yanzhengma(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 验证验证码
     *
     * @param subscriber
     * @return
     */
    public Subscription check_yanzhengma(Map<String, String> params, Subscriber<Bean<Object>> subscriber) {
        return service.check_yanzhengma(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 注册
     *
     * @param subscriber
     * @return
     */
    public Subscription register(Map<String, String> params, Subscriber<Bean<Object>> subscriber) {
        return service.register(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 登录
     *
     * @param subscriber
     * @return
     */
    public Subscription login(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.login(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 修改密码
     *
     * @param subscriber
     * @return
     */
    public Subscription submit_password(Map<String, String> params, Subscriber<Bean<Object>> subscriber) {
        return service.update_password(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 选择角色
     *
     * @param subscriber
     * @return
     */
    public Subscription select_role(Map<String, String> params, Subscriber<Bean<Object>> subscriber) {
        return service.select_role(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取七牛token
     *
     * @param subscriber
     * @return
     */
    public Subscription get_qiniu_token(Map<String, String> params, Subscriber<Bean<Object>> subscriber) {
        return service.get_qiniu_token(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 测试获取七牛token
     *
     * @param subscriber
     * @return
     */
    public Subscription get_qiniu_url(Map<String, String> params, Subscriber<Bean<Object>> subscriber) {
        return service.get_qiniu_url(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 提交审核资料
     *
     * @param subscriber
     * @return
     */
    public Subscription submit_information(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.submit_information(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取验证码
     *
     * @param subscriber
     * @return
     */
    public Subscription get_check_status(Map<String, String> params, Subscriber<Bean<Object>> subscriber) {
        return service.get_check_status(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取课程
     *
     * @param subscriber
     * @return
     */
    public Subscription get_class_resource(Map<String, String> params, Subscriber<Bean<Object>> subscriber) {
        return service.get_class_resource(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 添加课程
     *
     * @param subscriber
     * @return
     */
    public Subscription add_class(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.add_class(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 课程列表
     *
     * @param subscriber
     * @return
     */
    public Subscription class_list(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.class_list(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 消息列表
     *
     * @param subscriber
     * @return
     */
    public Subscription zhanghu_message_list(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.zhanghu_message_list(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 添加课程
     *
     * @param subscriber
     * @return
     */
    public Subscription get_max_num(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.get_max_num(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取月视角
     *
     * @param subscriber
     * @return
     */
    public Subscription get_month_view(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.get_month_view(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取月视角
     *
     * @param subscriber
     * @return
     */
    public Subscription agreeApply(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.agreeApply(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取申请详情
     *
     * @param subscriber
     * @return
     */
    public Subscription get_shenqing_detail(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.get_shenqing_detail(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 改变状态
     *
     * @param subscriber
     * @return
     */
    public Subscription change_status(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.change_status(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取消息总数
     *
     * @param subscriber
     * @return
     */
    public Subscription get_message_all(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.get_message_all(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取教练列表
     *
     * @param subscriber
     * @return
     */
    public Subscription get_teacher_list(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.get_teacher_list(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取教练列表
     *
     * @param subscriber
     * @return
     */
    public Subscription invite(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.invite(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取教练列表
     *
     * @param subscriber
     * @return
     */
    public Subscription cancel_invite(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.cancel_invite(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取统计信息
     *
     * @param subscriber
     * @return
     */
    public Subscription get_statistics(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.get_statistics(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 修改密码
     *
     * @param subscriber
     * @return
     */
    public Subscription update_my_password(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.update_my_password(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 设置高低峰时段
     *
     * @param subscriber
     * @return
     */
    public Subscription setHighAndLowTime(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.setHighAndLowTime(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取课程详情
     *
     * @param subscriber
     * @return
     */
    public Subscription class_detail(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.class_detail(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取课程详情
     *
     * @param subscriber
     * @return
     */
    public Subscription teacherDetail(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.teacherDetail(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 批量绑定角色
     *
     * @param subscriber
     * @return
     */
    public Subscription binding_role(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.bindingRole(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 批量绑定教练
     *
     * @param subscriber
     * @return
     */
    public Subscription binding_teacher(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.bindingTeacher(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
