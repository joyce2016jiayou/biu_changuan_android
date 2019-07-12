package com.noplugins.keepfit.android.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;


import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.util.ToolbarControl;
import com.noplugins.keepfit.android.util.permission.EasyPermissions;
import com.noplugins.keepfit.android.util.permission.PermissionActivity;
import com.noplugins.keepfit.android.util.screen.AndroidWorkaround;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscription;

import static android.webkit.WebView.enableSlowWholeDocumentDraw;

public abstract class BaseActivity  extends SuperActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks{
    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;
    /**
     * 是否输出日志信息
     **/
    protected final String TAG = this.getClass().getSimpleName();
    /**
     * Rxjava
     */
    protected Subscription subscription;

    /**
     * 自定义title
     */
    ToolbarControl toolbar;

    /**
     * 封装布局
     */
    RelativeLayout ly_content;
    /***
     * 判断是否是有刘海
     */
    LinearLayout compensate_linear;

    BaseActivity activity;

    private boolean isShowTitle;


    //页面保持常亮
    private PowerManager powerManager = null;
    private PowerManager.WakeLock wakeLock = null;
    protected static final int RC_PERM = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            enableSlowWholeDocumentDraw();
        }
        activity = this;
        //初始化Bundle对象
        Bundle bundle = getIntent().getExtras();
        initParms(bundle);

        powerManager = (PowerManager) this.getSystemService(Service.POWER_SERVICE);
      //  wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Lock");
        //是否需计算锁的数量
      //  wakeLock.setReferenceCounted(false);

        //初始化布局
        mContextView = LayoutInflater.from(this).inflate(R.layout.ll_basetitle, null);
        ly_content = mContextView.findViewById(R.id.layout_base);

        setContentView(mContextView);

        /**为了适配华为屏幕软键盘*/
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
            //mContextView.setPadding(0,0,0,50);
        }

        toolbar = mContextView.findViewById(R.id.about_me_toolbar);
        compensate_linear = mContextView.findViewById(R.id.compensate_linear);

        //初始化控件
        initView();

        if (isShowTitle) {
            //初始化title
            initToolBar();
        } else {
            toolbar.setVisibility(View.GONE);
        }

        //初始化极光推送
        //JPushInterface.init(getApplicationContext());
        //registerMessageReceiver();  // used for receive msg

        doBusiness(getApplicationContext());

    }

    @Override
    protected void onResume() {
        initonResume();
        //请求屏幕常亮，onResume()方法中执行
      //  wakeLock.acquire();
//        JPushInterface.onResume(this);
//        JPushInterface.clearAllNotifications(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        initonPause();
        //取消屏幕常亮，onPause()方法中执行
       // wakeLock.release();
//        JPushInterface.onPause(this);
        super.onPause();
    }

    //private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

//    public void registerMessageReceiver() {
//        mMessageReceiver = new MessageReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
//        filter.addAction(MESSAGE_RECEIVED_ACTION);
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
//    }

//    public class MessageReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            try {
//                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
//                    String messge = intent.getStringExtra(KEY_MESSAGE);
//                    String extras = intent.getStringExtra(KEY_EXTRAS);
//                    StringBuilder showMsg = new StringBuilder();
//                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
//                    if (!ExampleUtil.isEmpty(extras)) {
//                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
//                    }
//                    //注册services返回的信息
//                    Log.e("极光注册services返回的信息", showMsg.toString());
//                }
//            } catch (Exception e) {
//            }
//        }
//    }

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


    public abstract void RightImgOnclick();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribe();//取消订阅
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
    public abstract void initParms(Bundle parms);


    /**
     * [初始化控件]
     */
    public abstract void initView();

    /***
     *
     */
    public abstract void initonResume();

    /***
     *
     */
    public abstract void initonPause();

    /**
     * [业务操作]
     *
     * @param mContext
     */
    public abstract void doBusiness(Context mContext);

    /**
     * View点击
     **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        if (fastClick())
            widgetClick(v);
    }

    /**
     * [防止快速点击]
     *
     * @return
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }


    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
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
}
