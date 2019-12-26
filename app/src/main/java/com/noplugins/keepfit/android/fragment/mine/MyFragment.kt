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
import com.noplugins.keepfit.android.activity.CooperateActivity
import com.noplugins.keepfit.android.activity.ProductAdviceActivity
import com.noplugins.keepfit.android.activity.SelectChangGuanActivity
import com.noplugins.keepfit.android.activity.ZhangHaoSafeActivity
import com.noplugins.keepfit.android.activity.mine.CgPriceActivity
import com.noplugins.keepfit.android.activity.mine.CostAccountingActivity
import com.noplugins.keepfit.android.activity.mine.WalletActivity
import com.noplugins.keepfit.android.activity.mine.cg.VenueDetailActivity
import com.noplugins.keepfit.android.activity.mine.roles.RolesManageActivity
import com.noplugins.keepfit.android.activity.mine.setting.SettingActivity
import com.noplugins.keepfit.android.adapter.mine.FunctionAdapter
import com.noplugins.keepfit.android.adapter.mine.cg.FunctionV11Adapter
import com.noplugins.keepfit.android.base.BaseFragment
import com.noplugins.keepfit.android.bean.ChangguanBean
import com.noplugins.keepfit.android.bean.mine.MineFunctionBean
import com.noplugins.keepfit.android.callback.PopViewCallBack
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.global.PublicPopControl
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.TimeCheckUtil
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow
import kotlinx.android.synthetic.main.fragment_mine.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.set

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
//        requestArea()
        setting()

        if (SpUtils.getInt(activity!!, AppConstants.USER_TYPE) == 2 || SpUtils.getInt(activity!!, AppConstants.USER_TYPE) == 3) {
            select_store_type.visibility = View.INVISIBLE
        } else {
            select_store_type.visibility = View.VISIBLE
            store_type_tv.text = SpUtils.getString(activity!!, AppConstants.CG_NAME)
        }
        select_store_type.setOnClickListener {
            if (SpUtils.getBoolean(activity, AppConstants.HAVE_MORE_AREA)) {//如果有更多场馆
                val intent = Intent(activity, SelectChangGuanActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requestArea()
    }

    private fun setting() {
        val fuctionBean: MutableList<MineFunctionBean> = ArrayList()
        val fuctionV11Bean: MutableList<MineFunctionBean> = ArrayList()

        if (SpUtils.getInt(activity, AppConstants.USER_TYPE) != 3) {
            val min1 = MineFunctionBean("场馆信息", R.drawable.tab_data)
            val min2 = MineFunctionBean("成本核算", R.drawable.tab_account)
            val min3 = MineFunctionBean("场馆价格", R.drawable.tab_price)
            val min4 = MineFunctionBean("合作信息", R.drawable.tab_cooperation)
            fuctionV11Bean.add(min1)
            fuctionV11Bean.add(min2)
            fuctionV11Bean.add(min3)
            fuctionV11Bean.add(min4)

            if (SpUtils.getInt(activity, AppConstants.USER_TYPE) == 1) {
                val min5 = MineFunctionBean("权限管理", R.drawable.icon_quanxian)
                fuctionBean.add(min5)
            }
        } else {
            gv_function_v11.visibility = View.GONE
        }

        val min6 = MineFunctionBean("设置", R.drawable.icon_about)
        val min7 = MineFunctionBean("联系客服", R.drawable.icon_customer_service)
        fuctionBean.add(min6)
        fuctionBean.add(min7)
        val functionAdapter = FunctionAdapter(activity, fuctionBean)
        gv_function.adapter = functionAdapter

        //
        gv_function.setOnItemClickListener { parent, view, position, id ->
            if (BaseUtils.isFastClick()) {
                when (fuctionBean[position].name) {
                    "账户" -> {
                        val intent = Intent(activity, WalletActivity::class.java)
                        activity!!.startActivityForResult(intent, 1)
                    }
                    "权限管理" -> {
                        val intent = Intent(activity, RolesManageActivity::class.java)
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
                    "设置" -> {
                        val intent = Intent(activity, SettingActivity::class.java)
                        activity!!.startActivityForResult(intent, 1)
                    }
                    "联系客服" -> {
                        call_pop("4006-836-895")

                    }
                }
            }
        }


        //======================
        // 1.1 FunctionV11Adapter
        //======================

        val functionV11Adapter = FunctionV11Adapter(activity, fuctionV11Bean)
        gv_function_v11.adapter = functionV11Adapter
        gv_function_v11.setOnItemClickListener { parent, view, position, id ->
            if (BaseUtils.isFastClick()) {
                when (fuctionV11Bean[position].name) {
                    "场馆价格" -> {
                        val intent = Intent(activity, CgPriceActivity::class.java)
                        activity!!.startActivityForResult(intent, 1)
                    }
                    "成本核算" -> {
                        val intent = Intent(activity, CostAccountingActivity::class.java)
                        activity!!.startActivityForResult(intent, 1)
                    }
                    "场馆信息" -> {
                        val intent = Intent(activity, VenueDetailActivity::class.java)
                        activity!!.startActivityForResult(intent, 1)
                    }
                    "合作信息" -> {
                        val intent = Intent(activity, CooperateActivity::class.java)
                        activity!!.startActivityForResult(intent, 1)
                    }
                }
            }
        }
    }

    private fun call_pop(phone_number: String) {
        PublicPopControl.alert_call_phone_dialog_center(context, PopViewCallBack { view, popup ->
            val cancel_layout = view.findViewById<LinearLayout>(R.id.cancel_layout)
            val sure_layout = view.findViewById<LinearLayout>(R.id.sure_layout)
            val content_tv = view.findViewById<TextView>(R.id.content_tv)
            content_tv.text = "确认拨打 $phone_number?"
            cancel_layout.setOnClickListener { popup.dismiss() }
            sure_layout.setOnClickListener {
                initSimple("4006836895")
                popup.dismiss()
            }
        })

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

        when (SpUtils.getInt(activity, AppConstants.USER_TYPE)) {
            1 -> {
                tv_type_name.text = "场馆主登陆:${cg.area.legalPerson}"
            }
            2 -> {
                tv_type_name.text = "经理登陆:${cg.area.legalPerson}"
            }
            3 -> {
                tv_type_name.text = "前台登陆:${cg.area.legalPerson}"
            }
        }
        Glide.with(activity)
                .load(cg.area.logo)
                .into(iv_logo)
        tv_user_Name.text = cg.area.areaName
        tv_score.text = "${cg.area.finalGradle}分"
        tv_cg_time.text = "营业时间：${TimeCheckUtil.removeSecond(cg.area.businessStart)}-${TimeCheckUtil.removeSecond(cg.area.businessEnd)}"
        tv_cg_address.text = cg.area.address
    }

}
