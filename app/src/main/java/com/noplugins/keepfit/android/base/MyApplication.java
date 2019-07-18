package com.noplugins.keepfit.android.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;


import com.noplugins.keepfit.android.util.net.callback.ErrorCallback;
import com.ql0571.loadmanager.core.LoadManager;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * Created by shiyujia02 on 2017/8/3.
 */

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    public static List<Integer> list = new ArrayList<>();

    private static MyApplication _application;//本类的实例
    public static OkHttpClient okHttpClient;

    //qcl用来在主线程中刷新ui
    private static Handler mHandler;
    public static String registrationId;
    public static boolean is_first = true;

    public static String[] lvjings = null;

    //保存List集合
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    private static Map<String, Activity> destoryMap = new HashMap<>();


    @Override
    public void onCreate() {
        super.onCreate();
        _application = this;

        //初始化handler
        mHandler = new Handler();

        //注册okhttp
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @SuppressLint("BadHostnameVerifier")
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);

        //网络监听（重连）
        LoadManager.beginBuilder()
                .addCallback(new ErrorCallback())
                .commit();

        //初始化友盟分享
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "5d2d6979570df3fe04000f97");
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setSinaWeibo("597832238", "1c6785dbf569cc74c60e24c223741593","http://sns.whalecloud.com");//微博
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");


    }


    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */

    public static void addDestoryActivity(Activity activity, String activityName) {
        destoryMap.put(activityName, activity);
    }

    /**
     * 销毁指定Activity
     */
    public static void destoryActivity(String activityName) {
        Set<String> keySet = destoryMap.keySet();
        for (String key : keySet) {
            destoryMap.get(key).finish();
        }
    }

    @Override
    public void onTerminate() {


        Log.d(TAG, "onTerminate");
        super.onTerminate();
    }

    /**
     * 在主线程中刷新UI的方法
     **/
    public static void runOnUIThread(Runnable r) {
        MyApplication.getMainHandler().post(r);
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static MyApplication getApplication() {
        return _application;
    }
}
