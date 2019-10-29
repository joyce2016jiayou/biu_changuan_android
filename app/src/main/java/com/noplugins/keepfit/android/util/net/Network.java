package com.noplugins.keepfit.android.util.net;


import android.content.Context;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.noplugins.keepfit.android.bean.BindCardBean;
import com.noplugins.keepfit.android.bean.BankCradBean;
import com.noplugins.keepfit.android.bean.BuyInformationBean;
import com.noplugins.keepfit.android.bean.CalenderEntity;
import com.noplugins.keepfit.android.bean.CgBindingBean;
import com.noplugins.keepfit.android.bean.ChangguanBean;
import com.noplugins.keepfit.android.bean.CheckBean;
import com.noplugins.keepfit.android.bean.CompnyBean;
import com.noplugins.keepfit.android.bean.DictionaryeBean;
import com.noplugins.keepfit.android.bean.HightListBean;
import com.noplugins.keepfit.android.bean.LoginBean;
import com.noplugins.keepfit.android.bean.OrderResultBean;
import com.noplugins.keepfit.android.bean.PrivateDetailBean;
import com.noplugins.keepfit.android.bean.TeacherBean;
import com.noplugins.keepfit.android.bean.TeacherDetailBean;
import com.noplugins.keepfit.android.bean.RiChengBean;
import com.noplugins.keepfit.android.bean.UserStatisticsBean;
import com.noplugins.keepfit.android.bean.WxPayBean;
import com.noplugins.keepfit.android.bean.mine.BalanceListBean;
import com.noplugins.keepfit.android.bean.mine.WalletBean;
import com.noplugins.keepfit.android.entity.AddClassEntity;
import com.noplugins.keepfit.android.entity.CheckEntity;
import com.noplugins.keepfit.android.entity.ClassEntity;
import com.noplugins.keepfit.android.entity.ClassTypeEntity;
import com.noplugins.keepfit.android.entity.InformationEntity;
import com.noplugins.keepfit.android.entity.LoginEntity;
import com.noplugins.keepfit.android.entity.RoleBean;
import com.noplugins.keepfit.android.entity.TeacherEntity;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.entity.Token;
import com.noplugins.keepfit.android.util.net.interceptor.LogInterceptor;
import com.orhanobut.logger.Logger;

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
    public static final int DEFAULT_TIMEOUT = 15;
    private static Network mInstance;
    public MyService service;
    //测试服
    public static String test_main_url = "http://192.168.1.45:8888/api";
    public static String main_url = "http://kft.ahcomg.com/api";
    public static String token = "";
    public static String login_token = "login_token";
    public static String phone_number = "phone_number";
    public static String no_submit_information = "no_submit_information";
    public static String changguan_number = "changguan_number";
    public static String get_examine_result = "get_examine_result";
    public static String username = "username";
    public static String phone = "phone";
    public static String is_set_alias = "is_set_alias";
    private static String MRTHOD_NAME = "";
    Retrofit retrofit;

    public String get_main_url(String str) {
        if (str.equals("test")) {
            return test_main_url + "/gym-service/";
        } else {
            return main_url + "/gym-service/";
        }
    }

    //获取单例
    public static Network getInstance(String method, Context context) {
        MRTHOD_NAME = method;
        if (context != null) {
            if ("".equals(SpUtils.getString(context, AppConstants.TOKEN))) {
                token = "";
                Logger.e(method + "没有添加token");
            } else {
                token = SpUtils.getString(context, AppConstants.TOKEN);
                Logger.e(method + "添加的token:" + token);
            }
            if (mInstance == null) {
                synchronized (Network.class) {
                    mInstance = new Network(method, context);
                }
            }
        }
        return mInstance;
    }


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
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)//超时处理
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(get_main_url("main"))//设置请求网址根部
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        service = retrofit.create(MyService.class);


    }

    private RequestBody retuen_json_params(Map<String, Object> params) {
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        Logger.e(MRTHOD_NAME + "->请求参数打印：->" + json_params);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        return requestBody;
    }


    private RequestBody retuen_json_object(Object params) {
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        Logger.e(MRTHOD_NAME + "->请求参数打印：->" + json_params);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        return requestBody;
    }


    /**
     * 获取验证码
     *
     * @param subscriber
     * @return
     */
    public Subscription get_yanzhengma(Map<String, String> params, Subscriber<Bean<String>> subscriber) {
        return service.get_yanzhengma(params)
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
    public Subscription login(Map<String, Object> params, Subscriber<Bean<LoginEntity>> subscriber) {
        return service.login(retuen_json_params(params))
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
    public Subscription verifyCodeLogin(Map<String, Object> params, Subscriber<Bean<LoginBean>> subscriber) {
        return service.verifyCodeLogin(retuen_json_params(params))
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
    public Subscription submit_information(Map<String, Object> params, Subscriber<Bean<CheckBean>> subscriber) {
        return service.submit_information(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取审核状态
     *
     * @param subscriber
     * @return
     */
    public Subscription get_check_status(Map<String, Object> params, Subscriber<Bean<CheckEntity>> subscriber) {
        return service.get_check_status(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取公司信息
     *
     * @param subscriber
     * @return
     */
    public Subscription get_compny_information(Map<String, Object> params, Subscriber<Bean<CompnyBean>> subscriber) {
        return service.get_compny_information(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 绑定银行卡
     *
     * @param subscriber
     * @return
     */
    public Subscription bind_card(BindCardBean params, Subscriber<Bean<Object>> subscriber) {
        return service.bind_card(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 生成订单
     *
     * @param subscriber
     * @return
     */
    public Subscription get_order(Map<String, Object> params, Subscriber<Bean<String>> subscriber) {
        return service.get_order(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 生成订单
     *
     * @param subscriber
     * @return
     */
    public Subscription get_buy_information(Map<String, Object> params, Subscriber<Bean<BuyInformationBean>> subscriber) {
        return service.get_buy_information(retuen_json_params(params))
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
     * 课程列表
     *
     * @param subscriber
     * @return
     */
    public Subscription class_list(Map<String, Object> params, Subscriber<Bean<ClassEntity>> subscriber) {
        return service.class_list(retuen_json_params(params))
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
    public Subscription cancel_invite(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.cancel_invite(retuen_json_params(params))
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
//    public Subscription teacherDetail(RequestBody params, Subscriber<Bean<Object>> subscriber) {
//        return service.teacherDetail(params)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }

    /**
     * 批量绑定角色
     *
     * @param subscriber
     * @return
     */
    public Subscription binding_role(RoleBean params, Subscriber<Bean<Object>> subscriber) {
        return service.bindingRole(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取角色列表
     *
     * @param subscriber
     * @return
     */
    public Subscription findBindingRoles(RequestBody params, Subscriber<Bean<RoleBean>> subscriber) {
        return service.findBindingRoles(params)
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

    /**
     * 获取教练列表
     *
     * @param subscriber
     * @return
     */
    public Subscription findBindingTeachers(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.findBindingTeachers(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 产品反馈
     *
     * @param subscriber
     * @return
     */
    public Subscription feedback(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.feedback(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取我的账户
     *
     * @param subscriber
     * @return
     */
    public Subscription searchWallet(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.searchWallet(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取我的账单列表
     *
     * @param subscriber
     * @return
     */
    public Subscription searchWalletDetail(RequestBody params, Subscriber<Bean<Object>> subscriber) {
        return service.searchWalletDetail(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发送订单
     *
     * @param subscriber
     * @return
     */
    public Subscription sen_order(Map<String,Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.sen_order(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription get_class_type(Map<String, Object> params, Subscriber<Bean<List<ClassTypeEntity>>> subscriber) {
        return service.get_class_type(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription add_class(Map<String, Object> params, Subscriber<Bean<AddClassEntity>> subscriber) {
        return service.add_class(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public Subscription get_teacher_list(Map<String, Object> params, Subscriber<Bean<TeacherEntity>> subscriber) {
        return service.get_teacher_list(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription check_yanzhengma(Map<String, Object> params, Subscriber<Bean<String>> subscriber) {
        return service.check_yanzhengma(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * memberOrderPay-支付宝
     */
    public Subscription memberOrderPay(Map<String, Object> params, Subscriber<Bean<String>> subscriber) {
        return service.memberOrderPay(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * memberOrderPay-微信
     */
    public Subscription memberOrderPayWx(Map<String, Object> params, Subscriber<Bean<WxPayBean>> subscriber) {
        return service.memberOrderPayWx(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 教练详情
     */
    public Subscription teacherDetails(Map<String, Object> params, Subscriber<Bean<PrivateDetailBean>> subscriber) {
        return service.teacherDetails(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 钱包
     */
    public Subscription myBalance(Map<String, Object> params, Subscriber<Bean<WalletBean>> subscriber) {
        return service.myBalance(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 明细列表
     */
    public Subscription myBalanceList(Map<String, Object> params, Subscriber<Bean<BalanceListBean>> subscriber) {
        return service.myBalanceList(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 明细详情
     */
    public Subscription myBalanceListDetail(Map<String, Object> params, Subscriber<Bean<BalanceListBean.ListBean>> subscriber) {
        return service.myBalanceListDetail(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 修改手机号
     */
    public Subscription updatePhone(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.updatePhone(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 设置密码
     */
    public Subscription setPassword(Map<String, Object> params, Subscriber<Bean<String>> subscriber) {
        return service.setPassword(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 修改密码
     */
    public Subscription forgetPassword(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.forgetPassword(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 设置提现密码
     */
    public Subscription settingPayPassword(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.settingPayPassword(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 提现
     *
     * @param params
     * @param subscriber
     * @return
     */
    public Subscription withdrawDeposit(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.withdrawDeposit(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 教练详情
     *
     * @param subscriber
     * @return
     */
    public Subscription teacherDetail(Map<String, Object> params, Subscriber<Bean<TeacherDetailBean>> subscriber) {
        return service.teacherDetail(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 添加教练列表
     *
     * @param subscriber
     * @return
     */
    public Subscription teacherMannerList(Map<String, Object> params, Subscriber<Bean<List<TeacherBean>>> subscriber) {
        return service.teacherMannerList(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 教练管理
     *
     * @param subscriber
     * @return
     */
    public Subscription teacherManner(Map<String, Object> params, Subscriber<Bean<List<TeacherBean>>> subscriber) {
        return service.teacherManner(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 同意，拒绝
     *
     * @param params
     * @param subscriber
     * @return
     */
    public Subscription agreeBindingArea(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.agreeBindingArea(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 精细化时间段
     *
     * @param params
     * @param subscriber
     * @return
     */
    public Subscription setHighTime(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.setHighTime(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 成本核算
     *
     * @param params
     * @param subscriber
     * @return
     */
    public Subscription updateCost(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.updateCost(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取日历
     *
     * @param subscriber
     * @return
     */
    public Subscription get_rili(Map<String, Object> params, Subscriber<Bean<CalenderEntity>> subscriber) {
        return service.get_rili(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription get_shouye_date(Map<String, Object> params, Subscriber<Bean<RiChengBean>> subscriber) {
        return service.get_shouye_date(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription get_types(Map<String, Object> params, Subscriber<Bean<List<DictionaryeBean>>> subscriber) {
        return service.get_types(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 我的场馆信息
     */
    public Subscription myArea(Map<String, Object> params, Subscriber<Bean<ChangguanBean>> subscriber) {
        return service.myArea(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription submitAudit(InformationEntity params, Subscriber<Bean<Object>> subscriber) {
        return service.submitAudit(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 场馆绑定教练
     */

    public Subscription areaInviteTeacher(CgBindingBean params, Subscriber<Bean<Object>> subscriber) {
        return service.areaInviteTeacher(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 提现
     */
    public Subscription areaWithdraw(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.areaWithdraw(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 提现
     */
    public Subscription bankCard(Map<String, Object> params, Subscriber<Bean<BankCradBean>> subscriber) {
        return service.bankCard(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取高峰时间价格
     */
    public Subscription findAreaPrice(Map<String, Object> params, Subscriber<Bean<List<HightListBean>>> subscriber) {
        return service.findAreaPrice(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription statistics(Map<String, Object> params, Subscriber<Bean<UserStatisticsBean>> subscriber) {
        return service.statistics(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription getPayResult(Map<String, Object> params, Subscriber<Bean<OrderResultBean>> subscriber) {
        return service.getPayResult(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }



}
