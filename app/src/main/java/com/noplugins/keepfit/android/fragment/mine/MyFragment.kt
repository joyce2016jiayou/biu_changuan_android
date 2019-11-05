package com.noplugins.keepfit.android.fragment.mine

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.*
import com.noplugins.keepfit.android.activity.mine.CgPriceActivity
import com.noplugins.keepfit.android.activity.mine.CostAccountingActivity
import com.noplugins.keepfit.android.activity.mine.TeacherManagerActivity
import com.noplugins.keepfit.android.activity.mine.WalletActivity
import com.noplugins.keepfit.android.adapter.mine.FunctionAdapter
import com.noplugins.keepfit.android.base.BaseFragment
import com.noplugins.keepfit.android.bean.ChangguanBean
import com.noplugins.keepfit.android.bean.mine.MineFunctionBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.TimeCheckUtil
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow
import kotlinx.android.synthetic.main.fragment_mine.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.HashMap

class MyFragment : BaseFragment(), EasyPermissions.PermissionCallbacks {
    companion object {
        fun newInstance(title: String): MyFragment {
            val fragment = MyFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }

    var newView: View? = null
    val PERMISSION_STORAGE_CODE = 10001
    val PERMISSION_STORAGE_MSG = "需要电话权限才能联系客服哦"
    val PERMISSION_STORAGE = arrayOf(Manifest.permission.CALL_PHONE)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_mine, container, false)
        }
        return newView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requestArea()
        setting()
        ll_info.setOnClickListener {
            if (BaseUtils.isFastClick()){
                val intent = Intent(activity, ChangGuandetailActivity::class.java)
                activity!!.startActivityForResult(intent, 1)
            }

        }
    }

    private fun setting() {
        val fuctionBean: MutableList<MineFunctionBean> = ArrayList()

        if (SpUtils.getInt(activity, AppConstants.USER_TYPE) != 3) {
            val min1 = MineFunctionBean("钱包", R.drawable.icon_wallet)
            val min2 = MineFunctionBean("权限管理", R.drawable.icon_quanxian)
            val min3 = MineFunctionBean("场馆价格", R.drawable.icon_price)

            val min4 = MineFunctionBean("成本核算", R.drawable.icon_count)
            val min5 = MineFunctionBean("教练管理", R.drawable.icon_trainer)
            fuctionBean.add(min1)
            fuctionBean.add(min2)
            fuctionBean.add(min3)
            fuctionBean.add(min4)
            fuctionBean.add(min5)
        }

        val min6 = MineFunctionBean("问题反馈", R.drawable.iconfeedback)
        val min7 = MineFunctionBean("账号安全", R.drawable.icon_account_number)
        val min8 = MineFunctionBean("关于", R.drawable.icon_about)
        val min9 = MineFunctionBean("客服帮助", R.drawable.icon_customer_service)

        fuctionBean.add(min6)
        fuctionBean.add(min7)
        fuctionBean.add(min8)
        fuctionBean.add(min9)
        val functionAdapter = FunctionAdapter(activity, fuctionBean)
        gv_function.adapter = functionAdapter

        //
        gv_function.setOnItemClickListener { parent, view, position, id ->
            if (BaseUtils.isFastClick()) {
                when (fuctionBean[position].name) {
                    "钱包" -> {
                        val intent = Intent(activity, WalletActivity::class.java)
                        activity!!.startActivityForResult(intent, 1)
                    }
                    "权限管理" -> {
                        val intent = Intent(activity, RoleActivity::class.java)
                        activity!!.startActivityForResult(intent, 1)
                    }
                    "场馆价格" -> {
                        val intent = Intent(activity, CgPriceActivity::class.java)
                        activity!!.startActivityForResult(intent, 1)
                    }
                    "成本核算" -> {
                        val intent = Intent(activity, CostAccountingActivity::class.java)
                        activity!!.startActivityForResult(intent, 1)
                    }
                    "教练管理" -> {
                        val intent = Intent(activity, TeacherManagerActivity::class.java)
                        activity!!.startActivityForResult(intent, 1)
                    }
                    "问题反馈" -> {
                        val intent = Intent(activity, ProductAdviceActivity::class.java)
                        activity!!.startActivityForResult(intent, 1)
                    }
                    "账号安全" -> {
                        val intent = Intent(activity, ZhangHaoSafeActivity::class.java)
                        activity!!.startActivityForResult(intent, 1)
                    }
                    "关于" -> {
                        val intent = Intent(activity, AboutActivity::class.java)
                        activity!!.startActivityForResult(intent, 1)
                    }
                    "客服帮助" -> {
                        call_pop("10010")

                    }
                }
            }
        }
    }

    private fun call_pop(phone_number: String) {
        val popupWindow = CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.call_pop)
                .setBackGroundLevel(0.5f)//0.5f
                .setAnimationStyle(R.style.main_menu_animstyle)
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT)
                .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(gv_function)

        /**设置逻辑 */
        val view = popupWindow.contentView
        val cancel_layout = view.findViewById<LinearLayout>(R.id.cancel_layout)
        val sure_layout = view.findViewById<LinearLayout>(R.id.sure_layout)
        val content_tv = view.findViewById<TextView>(R.id.content_tv)
        content_tv.text = "确认拨打 $phone_number?"
        cancel_layout.setOnClickListener { popupWindow.dismiss() }
        sure_layout.setOnClickListener {
            initSimple(phone_number)
            popupWindow.dismiss()
        }


    }

    //@AfterPermissionGranted(PERMISSION_STORAGE_CODE)
    private fun initSimple(phone_number: String) {
        if (hasStoragePermission(activity!!)) {
            //有权限
            callPhone(phone_number)
        } else {
            //申请权限
            EasyPermissions.requestPermissions(activity!!, PERMISSION_STORAGE_MSG, PERMISSION_STORAGE_CODE, *PERMISSION_STORAGE)
        }
    }

    @SuppressLint("MissingPermission")
    fun callPhone(phoneNum: String) {
        val intent1 = Intent(Intent.ACTION_CALL)
        val data = Uri.parse("tel:$phoneNum")
        intent1.data = data
        activity!!.startActivity(intent1)
    }

    fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        return EasyPermissions.hasPermissions(context, *permissions)
    }

    fun hasStoragePermission(context: Context?): Boolean {
        return hasPermissions(context, *PERMISSION_STORAGE)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(activity!!, perms)) {
            AppSettingsDialog.Builder(activity!!)
                    .setTitle("提醒")
                    .setRationale("需要电话权限才能联系客服哦")
                    .build()
                    .show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, context)

    }


    private fun requestArea() {
        val params = HashMap<String, Any>()
//        params["userNum"] = SpUtils.getString(this, AppConstants.USER_NAME)
        params["areaNum"] = SpUtils.getString(activity, AppConstants.CHANGGUAN_NUM)

        val subscription = Network.getInstance("我的", activity)
                .myArea(
                        params,
                        ProgressSubscriber("我的", object : SubscriberOnNextListener<Bean<ChangguanBean>> {
                            override fun onNext(result: Bean<ChangguanBean>) {
                                setting(result.data)
                            }

                            override fun onError(error: String) {


                            }
                        }, activity, false)
                )
    }

    private fun setting(cg: ChangguanBean) {
        Glide.with(activity)
                .load(cg.area.logo)
                .into(iv_logo)
        tv_user_Name.text = cg.area.areaName
        tv_score.text = "${cg.area.finalGradle}分"
        tv_cg_time.text = "营业时间：${TimeCheckUtil.removeSecond(cg.area.businessStart)}-${TimeCheckUtil.removeSecond(cg.area.businessEnd)}"
        tv_cg_address.text = cg.area.address
    }

}
