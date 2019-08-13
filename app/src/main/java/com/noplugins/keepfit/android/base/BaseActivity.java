package com.noplugins.keepfit.android.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.jpush.TagAliasOperatorHelper;
import com.noplugins.keepfit.android.util.ActivityCollectorUtil;
import com.noplugins.keepfit.android.util.SoftHideKeyBoardUtil;
import com.noplugins.keepfit.android.util.ToolbarControl;
import com.noplugins.keepfit.android.util.permission.EasyPermissions;
import com.noplugins.keepfit.android.util.permission.PermissionActivity;
import com.noplugins.keepfit.android.util.screen.AndroidWorkaround;
import com.orhanobut.logger.Logger;
import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import rx.Subscription;

import static android.webkit.WebView.enableSlowWholeDocumentDraw;
import static com.noplugins.keepfit.android.jpush.TagAliasOperatorHelper.sequence;

public abstract class BaseActivity  extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    private View mContextView = null;//当前Activity渲染的视图View
    protected final String TAG = this.getClass().getSimpleName();//是否输出日志信息
    protected Subscription subscription;//Rxjava
    ToolbarControl toolbar;//自定义title
    RelativeLayout ly_content;//封装布局
    private boolean isSetStatusBar = true;//是否沉浸状态栏
    private final List<BaseActivity> mActivities = new LinkedList<>();//记录所有活动的Activity
    private boolean isShowTitle;
    protected static final int RC_PERM = 123;

    /**
     * 设置 app 不随着系统字体的调整而变化
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectorUtil.addActivity(this);
        //设置沉浸栏
        set_status_bar();


        //初始化Bundle对象
        Bundle bundle = getIntent().getExtras();
        initBundle(bundle);


        //初始化布局
        mContextView = LayoutInflater.from(this).inflate(R.layout.ll_basetitle, null);
        ly_content = mContextView.findViewById(R.id.layout_base);
        setContentView(mContextView);


        //为了适配屏幕软键盘
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
            //mContextView.setPadding(0,0,0, ScreenUtilsHelper.dip2px(this,50));
        }


        toolbar = mContextView.findViewById(R.id.about_me_toolbar);

        //初始化控件
        initView();

        if (isShowTitle) {
            //初始化title
            initToolBar();
        } else {
            toolbar.setVisibility(View.GONE);
        }

        //极光
        JPushInterface.init(getApplicationContext());

        doBusiness(getApplicationContext());

    }

    private void set_status_bar() {
        if (Build.VERSION.SDK_INT >= 21) {
            enableSlowWholeDocumentDraw();
        }
        if (isSetStatusBar) {
            steepStatusBar();
        }

    }

    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            getWindow().getDecorView().setSystemUiVisibility(option);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.parseColor("#00000000"));
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            StatusBarUtil.setStatusBarColor(this, R.color.transparent);
//        } else {
//            StatusBarUtil.transparencyBar(this);
//        }
    }


    public void isShowTitle(boolean isShowTitle) {
        this.isShowTitle = isShowTitle;
    }

    /***
     * 设置内容区域
     *
     * @param resId 资源文件ID
     */
    public void setContentLayout(int resId) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContextView = inflater.inflate(resId, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContextView.setLayoutParams(layoutParams);
        mContextView.setBackgroundDrawable(null);
        if (null != ly_content) {
            ly_content.addView(mContextView);
        }

    }

    public String title;

    public void setTopTitleContent(String str) {
        title = str;
    }

    private void initToolBar() {
        if (isShowTitle) {
            toolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setTitle(title);

            toolbar.setBackButtonOnClickListerner(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });


        } else {
            Logger.e("不显示title");
        }

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribe();//取消订阅
        ActivityCollectorUtil.removeActivity(this);
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


    /**
     * [初始化Bundle参数]
     * @param parms
     */
    public abstract void initBundle(Bundle parms);


    /**
     * [初始化控件]
     */
    public abstract void initView();


    /**
     * [业务操作]
     *
     * @param mContext
     */
    public abstract void doBusiness(Context mContext);



    /**
     * 权限回调接口
     */
    private PermissionActivity.CheckPermListener mListener;

    public interface CheckPermListener {
        //权限通过后的回调方法
        void superPermission();
    }

    public void checkPermission(PermissionActivity.CheckPermListener listener, int resString, String... mPerms) {
        mListener = listener;
        if (EasyPermissions.hasPermissions(this, mPerms)) {
            if (mListener != null)
                mListener.superPermission();
        } else {
            EasyPermissions.requestPermissions(this, getString(resString),
                    RC_PERM, mPerms);
        }
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EasyPermissions.SETTINGS_REQ_CODE) {
            //设置返回
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //同意了某些权限可能不是全部
    }

    @Override
    public void onPermissionsAllGranted() {
        if (mListener != null)
            mListener.superPermission();//同意了全部权限的回调
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.perm_tip),
                R.string.setting, R.string.cancel, null, perms);
    }
}
