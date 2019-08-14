package com.noplugins.keepfit.android;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;


import com.noplugins.keepfit.android.activity.LoginActivity;
import com.noplugins.keepfit.android.activity.UserPermissionSelectActivity;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.eventbus.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.splash_image)
    ImageView splash_image;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
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
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if ("".equals(SharedPreferencesHelper.get(getApplicationContext(), "login_token", ""))) {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, KeepFitActivity.class);
                        startActivity(intent);
                        finish();
                    }


                }
            }, 2000);
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
