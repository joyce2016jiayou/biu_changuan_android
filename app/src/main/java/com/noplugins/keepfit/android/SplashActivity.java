package com.noplugins.keepfit.android;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.noplugins.keepfit.android.activity.BuyActivity;
import com.noplugins.keepfit.android.activity.CheckStatusFailActivity;
import com.noplugins.keepfit.android.activity.HeTongActivity;
import com.noplugins.keepfit.android.activity.LoginActivity;
import com.noplugins.keepfit.android.activity.SubmitInformationSelectActivity;
import com.noplugins.keepfit.android.activity.UserPermissionSelectActivity;
import com.noplugins.keepfit.android.activity.mine.CgPriceActivity;
import com.noplugins.keepfit.android.activity.user.Login2Activity;
import com.noplugins.keepfit.android.activity.user.SetPasswordActivity;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.CheckEntity;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.eventbus.MessageEvent;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.cache.Sp;
import rx.Subscription;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.splash_image)
    ImageView splash_image;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        // http://code.google.com/p/android/issues/detail?id=2373
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent
                    .ACTION_MAIN)) {
                finish();
                return;
            }
        }
        setContentLayout(R.layout.activity_splash);
        ButterKnife.bind(this);
        isShowTitle(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mHandler) {
            mHandler.removeCallbacks(mRegularAction);   //把runable移除队列
            mHandler = null;
        }

    }

    @Override
    public void doBusiness(Context mContext) {
        if (panduan_net()) {
            if ("".equals(SpUtils.getString(getApplicationContext(), AppConstants.TOKEN, ""))) {
                Intent intent = new Intent(SplashActivity.this, Login2Activity.class);
                startActivity(intent);
                finish();
            } else {
                //获取审核状态
                if (SpUtils.getInt(getApplicationContext(), AppConstants.USER_TYPE) == 1) {//只有场馆主身份才能调取审核接口
                    //获取审核状态
                    get_check_status();
                } else if (SpUtils.getInt(getApplicationContext(), AppConstants.USER_TYPE) == 2) {//经理
                    //根据身份价格显示
                    Intent intent1 = new Intent(SplashActivity.this, KeepFitActivity.class);
                    startActivity(intent1);
                } else if (SpUtils.getInt(getApplicationContext(), AppConstants.USER_TYPE) == 3) {//前台
                    //根据身份价格显示
                    Intent intent1 = new Intent(SplashActivity.this, KeepFitActivity.class);
                    startActivity(intent1);
                } else if (SpUtils.getInt(getApplicationContext(), AppConstants.USER_TYPE) == 0) {//默认，跳审核页
                    Intent intent1 = new Intent(SplashActivity.this, Login2Activity.class);
                    startActivity(intent1);
                }

            }


        } else {//等待网络或者弹窗
            if (null == mHandler) {
                mHandler = new Handler(Looper.getMainLooper());
            }
            mHandler.removeCallbacks(mRegularAction);
            mHandler.post(mRegularAction);
        }

//        splash_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SplashActivity.this, KeepFitActivity.class);
//                Bundle message_bunder = new Bundle();
//                message_bunder.putString("jpush_enter", "jpush_enter2");
//                intent.putExtras(message_bunder);
//                startActivity(intent);
//
//            }
//        });

        //CrashReport.testJavaCrash();

    }

    private void get_check_status() {
        Map<String, Object> params = new HashMap<>();
        params.put("token", SpUtils.getString(getApplicationContext(), AppConstants.TOKEN));
        Log.e("获取审核状态参数", params.toString());
        Subscription subscription = Network.getInstance("获取审核状态", this)
                .get_check_status(params,
                        new ProgressSubscriber<>("获取审核状态", new SubscriberOnNextListener<Bean<CheckEntity>>() {
                            @Override
                            public void onNext(Bean<CheckEntity> result) {
                                Log.e(TAG, "获取审核状态成功：" + result.getData().getStatus());
                                if (result.getData().getStatus() == 1) {//成功
                                    //0没买过，1是2999 2是3999 3是6999
                                    switch (result.getData().getHaveMember()){
                                        case "0":
                                            Intent intent = new Intent(SplashActivity.this, HeTongActivity.class);
                                            startActivity(intent);
                                            finish();
                                            return;
                                        case "1":
                                            SpUtils.putString(getApplicationContext(), AppConstants.USER_DENGJI, "2999");
                                            break;
                                        case "2":
                                            SpUtils.putString(getApplicationContext(), AppConstants.USER_DENGJI, "3999");
                                            break;
                                        case "3":
                                            SpUtils.putString(getApplicationContext(), AppConstants.USER_DENGJI, "6999");
                                            break;
                                    }
                                    if (result.getData().getHighTime() == 1){
                                        Intent intent = new Intent(SplashActivity.this, KeepFitActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(SplashActivity.this, CgPriceActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("form","pay");
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        finish();
                                    }


                                } else if (result.getData().getStatus() == 0) {//失败
                                    Intent intent = new Intent(SplashActivity.this, CheckStatusFailActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if (result.getData().getStatus() == -2||result.getData().getStatus() == 4) {//没有提交过
                                    Intent intent = new Intent(SplashActivity.this, SubmitInformationSelectActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onError(String error) {
                                Intent intent = new Intent(SplashActivity.this, Login2Activity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, this, false));
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private static final long DELAY = 3000;
    private final Runnable mRegularAction = new Runnable() {
        @Override
        public void run() {
            //每隔3秒请求一次
            if (panduan_net()) {//如果联网了，跳转
                Intent intent = new Intent(SplashActivity.this, KeepFitActivity.class);
                startActivity(intent);
                finish();
            } else {//继续查看是否有网络
                mHandler.postDelayed(this, DELAY);
            }
        }

    };

    /**
     * 判断是否有网络连接
     *
     * @return
     */
    private boolean panduan_net() {
        boolean isAvailable = false ;
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isAvailable()){
            isAvailable = true;
        }
        return isAvailable;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != mHandler) {
            mHandler.removeCallbacks(mRegularAction);   //把runable移除队列
            mHandler = null;
        }
    }


}
