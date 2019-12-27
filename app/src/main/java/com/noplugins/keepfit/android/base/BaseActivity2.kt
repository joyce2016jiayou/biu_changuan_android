package com.noplugins.keepfit.android.base

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import cn.jpush.android.api.JPushInterface
import com.noplugins.keepfit.android.KeepFitActivity
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.callback.OnclickCallBack
import com.noplugins.keepfit.android.util.ActivityCollectorUtil
import com.noplugins.keepfit.android.util.ToolbarControl
import com.noplugins.keepfit.android.util.permission.EasyPermissions
import com.noplugins.keepfit.android.util.permission.PermissionActivity
import com.noplugins.keepfit.android.util.screen.AndroidWorkaround
import rx.Subscription
import java.util.*

abstract class BaseActivity2 : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private var mContextView: View? = null //当前Activity渲染的视图View
    protected val TAG = this.javaClass.simpleName //是否输出日志信息
    protected var subscription //Rxjava
            : Subscription? = null
    var toolbar //自定义title
            : ToolbarControl? = null
    var ly_content //封装布局
            : FrameLayout? = null
    private val isSetStatusBar = true //是否沉浸状态栏
    private val mActivities: List<BaseActivity2> = LinkedList() //记录所有活动的Activity
    private var isShowTitle = false
    private var isRegistered = false
    private var netWorkChangReceiver: NetWorkChangReceiver? = null
    /**
     * 设置 app 不随着系统字体的调整而变化
     */
    override fun getResources(): Resources {
        val res = super.getResources()
        val config = Configuration()
        config.setToDefaults()
        res.updateConfiguration(config, res.displayMetrics)
        return res
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // 禁用横屏
        ActivityCollectorUtil.addActivity(this)
        //注册网络状态监听广播
        netWorkChangReceiver = NetWorkChangReceiver()
        val filter = IntentFilter()
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(netWorkChangReceiver, filter)
        isRegistered = true
        //设置沉浸栏
        set_status_bar()
        //初始化Bundle对象
        val bundle = intent.extras
        initBundle(bundle)
        //初始化布局
        mContextView = LayoutInflater.from(this).inflate(R.layout.ll_basetitle, null)
        ly_content = mContextView!!.findViewById(R.id.layout_base)
        setContentView(mContextView)
        toolbar = mContextView!!.findViewById(R.id.about_me_toolbar)
        //为了适配屏幕软键盘
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content))
            //mContextView.setPadding(0,0,0, ScreenUtilsHelper.dip2px(this,50));
        }
        //初始化控件
        initView()
        if (isShowTitle) { //初始化title
            initToolBar()
        } else {
            toolbar!!.setVisibility(View.GONE)
        }
        //极光
        JPushInterface.init(applicationContext)
        doBusiness(applicationContext)
    }

    private fun set_status_bar() {
        if (Build.VERSION.SDK_INT >= 21) {
            WebView.enableSlowWholeDocumentDraw()
        }
        if (isSetStatusBar) {
            steepStatusBar()
        }
    }

    /**
     * [沉浸状态栏]
     */
    private fun steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.decorView.systemUiVisibility = option
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor("#00000000")
        }
        val decor = window.decorView
        var ui = decor.systemUiVisibility
        ui = ui or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //设置状态栏中字体的颜色为黑色
        decor.systemUiVisibility = ui
    }

    /***
     * 设置内容区域
     *
     * @param resId 资源文件ID
     */
    fun setContentLayout(resId: Int) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mContextView = inflater.inflate(resId, null)
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        mContextView!!.setLayoutParams(layoutParams)
        //mContextView.setBackgroundDrawable(null);
        if (null != ly_content) {
            ly_content!!.addView(mContextView)
        }
    }

    fun setTitleView(title_content: Int, left_btn_id: Int) {
        isShowTitle = true
        title_content_str = title_content
        left_btn_id_resource = left_btn_id
    }

    fun setTitleView(title_content: Int) { //this.isShowTitle = true;
        title_content_str = title_content
    }

    /**
     * @param title_content  标题文字
     * @param left_btn_id    左边按钮图片资源
     * @param right_btn_id   右边按钮图片资源
     * @param is_tv_resource 右边按钮资源是否是文字
     */
    fun setTitleView(title_content: Int, left_btn_id: Int, right_btn_id: Int, is_tv_resource: Boolean) {
        isShowTitle = true
        title_content_str = title_content
        left_btn_id_resource = left_btn_id
        right_btn_id_resource = right_btn_id
        is_tv_resource_sure = is_tv_resource
    }

    fun setTitleViewBg(bg_corlor: Int) {
        isShowTitle = true
        bg_corlor_resource = bg_corlor
    }

    fun isShowTitle(isShowTitle: Boolean) {
        this.isShowTitle = isShowTitle
    }

    fun title_left_button_onclick_listen(onclickCallBack: OnclickCallBack?) {
        m_onclickCallBack = onclickCallBack
    }

    fun title_right_button_onclick_listen(onclickCallBack: OnclickCallBack?) {
        m_onclickCallBack = onclickCallBack
    }

    var title_content_str = 0
    var left_btn_id_resource = 0
    var right_btn_id_resource = 0
    var bg_corlor_resource = 0
    var is_tv_resource_sure = false
    var m_onclickCallBack: OnclickCallBack? = null
    private fun initToolBar() {
        if (isShowTitle) {
            Log.e("222222", "显示title")
            toolbar!!.visibility = View.VISIBLE
            setSupportActionBar(toolbar)
            Objects.requireNonNull(supportActionBar)!!.setDisplayShowTitleEnabled(false)
            if (title_content_str != 0) {
                toolbar!!.setTitle(resources.getString(title_content_str))
            }
            if (bg_corlor_resource != 0) {
                toolbar!!.setBgColor(bg_corlor_resource)
            }
            if (left_btn_id_resource != 0 && right_btn_id_resource != 0) { //左右两边都有按钮
                toolbar!!.showLeft()
                if (is_tv_resource_sure) { //右边按钮是文字资源
                    toolbar!!.showRightTextView()
                } else { //右边按钮是图片资源
                    toolbar!!.showRightImageView()
                }
            } else if (left_btn_id_resource != 0) { //只有左边有按钮
                toolbar!!.showLeft()
                toolbar!!.hideRight()
            } else if (right_btn_id_resource != 0) { //只有右边有按钮
                toolbar!!.hideLeft()
                if (is_tv_resource_sure) { //右边按钮是文字资源
                    toolbar!!.showRightTextView()
                } else { //右边按钮是图片资源
                    toolbar!!.showRightImageView()
                }
            }
            toolbar!!.setBackButtonOnClickListerner {
                if (null != m_onclickCallBack) {
                    m_onclickCallBack!!.onclick()
                }
            }
            toolbar!!.setRightButtonOnClickListerner {
                if (null != m_onclickCallBack) {
                    m_onclickCallBack!!.onclick()
                }
            }
        } else {
            Log.e("222222", "隐藏title")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //解绑
        if (isRegistered) {
            unregisterReceiver(netWorkChangReceiver)
        }
        unsubscribe() //取消订阅
        ActivityCollectorUtil.removeActivity(this)
    }

    protected fun unsubscribe() {
        if (subscription != null && !subscription!!.isUnsubscribed) {
            subscription!!.unsubscribe()
        }
    }

    /**
     * [初始化Bundle参数]
     *
     * @param parms
     */
    abstract fun initBundle(parms: Bundle?)

    /**
     * [初始化控件]
     */
    abstract fun initView()

    /**
     * [业务操作]
     *
     * @param mContext
     */
    abstract fun doBusiness(mContext: Context?)

    fun toMain() {
        val intent = Intent(this, KeepFitActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * 权限回调接口
     */
    private var mListener: PermissionActivity.CheckPermListener? = null

    interface CheckPermListener {
        //权限通过后的回调方法
        fun superPermission()
    }

    fun checkPermission(listener: PermissionActivity.CheckPermListener?, resString: Int, vararg mPerms: String?) {
        mListener = listener
        if (EasyPermissions.hasPermissions(this, *mPerms)) {
            if (mListener != null) mListener!!.superPermission()
        } else {
            EasyPermissions.requestPermissions(this, getString(resString),
                    RC_PERM, *mPerms)
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
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EasyPermissions.SETTINGS_REQ_CODE) { //设置返回
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) { //同意了某些权限可能不是全部
    }

    override fun onPermissionsAllGranted() {
        if (mListener != null) mListener!!.superPermission() //同意了全部权限的回调
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.perm_tip),
                R.string.setting, R.string.cancel, null, perms)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean { //拦截返回键
        if (event.keyCode == KeyEvent.KEYCODE_BACK) { //判断触摸UP事件才会进行返回事件处理
            if (event.action == KeyEvent.ACTION_UP) {
                onBackPressed()
            }
            //只要是返回事件，直接返回true，表示消费掉
            return true
        }
        return super.dispatchKeyEvent(event)
    }

    companion object {
        protected const val RC_PERM = 123
    }
}