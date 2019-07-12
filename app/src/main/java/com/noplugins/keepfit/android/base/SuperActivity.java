package com.noplugins.keepfit.android.base;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.util.screen.StatusBarUtil;

import java.util.LinkedList;
import java.util.List;

public class SuperActivity extends AppCompatActivity {
    /**
     * 是否沉浸状态栏
     **/
    private boolean isSetStatusBar = true;
    /**
     * 是否允许全屏
     **/
    private boolean mAllowFullScreen = true;
    /**
     * 是否禁止旋转屏幕
     **/
    private boolean isAllowScreenRoate = false;
    /**
     * 记录所有活动的Activity
     **/
    private final List<BaseActivity> mActivities = new LinkedList<>();

    /**
     * 记录处于前台的Activity
     */
    private SuperActivity mForegroundActivity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mForegroundActivity = this;

        //设置沉浸栏
        if (isSetStatusBar) {
            steepStatusBar();
        }
        //设置状态栏颜色
        //StatusBarCompat.setStatusBarColor(this, Color.parseColor("#ffffff"),true);

        //设置是否全屏
        if (mAllowFullScreen) {
//            this.getWindow().setFlags(
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        //设置屏幕方向
        if (!isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        }


    }

    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setStatusBarColor(mForegroundActivity, R.color.white);
        }else{
            StatusBarUtil.transparencyBar(mForegroundActivity);
        }
    }
}
