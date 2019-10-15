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


import com.noplugins.keepfit.android.activity.BuyActivity;
import com.noplugins.keepfit.android.activity.CheckStatusFailActivity;
import com.noplugins.keepfit.android.activity.LoginActivity;
import com.noplugins.keepfit.android.activity.UserPermissionSelectActivity;
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
            if ("".equals(SharedPreferencesHelper.get(getApplicationContext(), Network.login_token, ""))) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                //防止在第一次，选择角色的时候退出了，导致，第二次进来直接进主页
                //获取审核状态
                get_check_status();
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

        subscription = Network.getInstance("教练列表", this)
                .get_check_status(params,
                        new ProgressSubscriber<>("教练列表", new SubscriberOnNextListener<Bean<CheckEntity>>() {
                            @Override
                            public void onNext(Bean<CheckEntity> result) {
                                Log.e(TAG, "获取审核状态成功：" + result.getData().getStatus());
                                //成功1，失败0，没有提交过资料-2
                                if (result.getData().getStatus() == 1) {
                                    Intent intent = new Intent(SplashActivity.this, KeepFitActivity.class);
                                    startActivity(intent);
                                } else if (result.getData().getStatus() == 0) {
                                    Intent intent = new Intent(SplashActivity.this, CheckStatusFailActivity.class);
                                    startActivity(intent);
                                } else if (result.getData().getStatus() == -2) {
                                    Intent intent = new Intent(SplashActivity.this, UserPermissionSelectActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, true));



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
        boolean is_net_true;
        //判断是不是用户断网了
        ConnectivityManager connMgr = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        if (isMobileConn || isWifiConn) {

            is_net_true = true;
        } else {
            is_net_true = false;
        }

        return is_net_true;
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
