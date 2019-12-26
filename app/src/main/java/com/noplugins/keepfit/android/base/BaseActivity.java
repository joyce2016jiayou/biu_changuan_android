package com.noplugins.keepfit.android.base;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.noplugins.keepfit.android.KeepFitActivity;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.callback.OnclickCallBack;
import com.noplugins.keepfit.android.util.ActivityCollectorUtil;
import com.noplugins.keepfit.android.util.ToolbarControl;
import com.noplugins.keepfit.android.util.permission.EasyPermissions;
import com.noplugins.keepfit.android.util.permission.PermissionActivity;
import com.noplugins.keepfit.android.util.screen.AndroidWorkaround;
import com.orhanobut.logger.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import cn.jpush.android.api.JPushInterface;
import rx.Subscription;

import static android.webkit.WebView.enableSlowWholeDocumentDraw;

public abstract class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private View mContextView = null;//当前Activity渲染的视图View
    protected final String TAG = this.getClass().getSimpleName();//是否输出日志信息
    protected Subscription subscription;//Rxjava
    ToolbarControl toolbar;//自定义title
    RelativeLayout ly_content;//封装布局
    private boolean isSetStatusBar = true;//是否沉浸状态栏
    private final List<BaseActivity> mActivities = new LinkedList<>();//记录所有活动的Activity
    private boolean isShowTitle;
    protected static final int RC_PERM = 123;

    private boolean isRegistered = false;
    private NetWorkChangReceiver netWorkChangReceiver;

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁用横屏
        ActivityCollectorUtil.addActivity(this);

        //注册网络状态监听广播
        netWorkChangReceiver = new NetWorkChangReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkChangReceiver, filter);
        isRegistered = true;
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
        View decor = getWindow().getDecorView();
        int ui = decor.getSystemUiVisibility();
        ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; //设置状态栏中字体的颜色为黑色
        decor.setSystemUiVisibility(ui);
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


    public void setTitleView(int title_content, int left_btn_id) {
        this.isShowTitle = true;
        title = title_content;
        left_btn_id_resource = left_btn_id;
    }

    public void setTitleView(int title_content) {
        this.isShowTitle = true;
        title = title_content;
    }



    /**
     * @param title_content  标题文字
     * @param left_btn_id    左边按钮图片资源
     * @param right_btn_id   右边按钮图片资源
     * @param is_tv_resource 右边按钮资源是否是文字
     */
    public void setTitleView(int title_content, int left_btn_id, int right_btn_id, boolean is_tv_resource) {
        this.isShowTitle = true;
        title = title_content;
        left_btn_id_resource = left_btn_id;
        right_btn_id_resource = right_btn_id;
        is_tv_resource_sure = is_tv_resource;
    }

    public void setTitleViewBg(int bg_corlor) {
        this.isShowTitle = true;
        bg_corlor_resource = bg_corlor;
    }

    public void isShowTitle(boolean isShowTitle) {
        this.isShowTitle = isShowTitle;
    }

    public void title_left_button_onclick_listen(OnclickCallBack onclickCallBack) {
        m_onclickCallBack = onclickCallBack;
    }

    public void title_right_button_onclick_listen(OnclickCallBack onclickCallBack) {
        m_onclickCallBack = onclickCallBack;
    }

    public int title;
    public int left_btn_id_resource, right_btn_id_resource, bg_corlor_resource;
    public boolean is_tv_resource_sure;
    public OnclickCallBack m_onclickCallBack;

    private void initToolBar() {
        if (isShowTitle) {
            toolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
            toolbar.setTitle(getResources().getString(title));
            if (bg_corlor_resource != 0) {
                toolbar.setBgColor(bg_corlor_resource);
            }

            if (left_btn_id_resource != 0 && right_btn_id_resource != 0) {//左右两边都有按钮
                toolbar.showLeft();
                if (is_tv_resource_sure) {//右边按钮是文字资源
                    toolbar.showRightTextView();
                } else {//右边按钮是图片资源
                    toolbar.showRightImageView();
                }
            } else if (left_btn_id_resource != 0) {//只有左边有按钮
                toolbar.showLeft();
                toolbar.hideRight();
            } else if (right_btn_id_resource != 0) {//只有右边有按钮
                toolbar.hideLeft();
                if (is_tv_resource_sure) {//右边按钮是文字资源
                    toolbar.showRightTextView();
                } else {//右边按钮是图片资源
                    toolbar.showRightImageView();
                }
            }
            toolbar.setBackButtonOnClickListerner(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != m_onclickCallBack) {
                        m_onclickCallBack.onclick();
                    }
                }
            });

            toolbar.setRightButtonOnClickListerner(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != m_onclickCallBack) {
                        m_onclickCallBack.onclick();
                    }
                }
            });

        } else {
            toolbar.setVisibility(View.GONE);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑
        if (isRegistered) {
            unregisterReceiver(netWorkChangReceiver);
        }
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
     *
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


    public void toMain() {
        Intent intent = new Intent(this, KeepFitActivity.class);
        startActivity(intent);
        this.finish();
    }

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
     *
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

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //拦截返回键
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //判断触摸UP事件才会进行返回事件处理
            if (event.getAction() == KeyEvent.ACTION_UP) {
                onBackPressed();
            }
            //只要是返回事件，直接返回true，表示消费掉
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
