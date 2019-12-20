package com.noplugins.keepfit.android.activity.mine

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.PrivateDetailBean
import com.noplugins.keepfit.android.bean.TeacherDetailBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow
import kotlinx.android.synthetic.main.activity_private_detail.*
import org.greenrobot.eventbus.EventBus
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.HashMap

class TeacherDetailActivity : BaseActivity() {
    private var type = -1
    private var listItem = -1
    private var cgNum = ""
    private var phone = ""
    val PERMISSION_STORAGE_CODE = 10001
    val PERMISSION_STORAGE_MSG = "需要电话权限才能联系客服哦"
    val PERMISSION_STORAGE = arrayOf(Manifest.permission.CALL_PHONE)
    override fun initBundle(parms: Bundle?) {
        if (parms != null) {
            listItem = parms.getInt("listItem")
            cgNum = parms.getString("cgNum").toString()
            type = parms.getInt("type", -1)

            requestPrivateData()
        }
    }

    override fun initView() {
        setContentView(R.layout.activity_private_detail)
        if (type == 1) {
            tv_yaoqing.text = "解 绑"
            iv_teacher_call.visibility = View.VISIBLE
        } else if (type != -1){
            tv_yaoqing.visibility = View.GONE
        }
    }

    override fun doBusiness(mContext: Context?) {
        tv_yaoqing.setOnClickListener {
            if (type == -1) {
                val mIntent = Intent()//没有任何参数（意图），只是用来传递数据
                mIntent.putExtra("item", listItem)
                setResult(RESULT_OK, mIntent)
                finish()
                return@setOnClickListener
            }

            if (type == 1){
                unBinding()
            }
        }
        back_btn.setOnClickListener {
            finish()
        }

        iv_teacher_call.setOnClickListener {
            if(BaseUtils.isFastClick()){
                call_pop()
            }
        }
    }

    private fun call_pop() {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.call_pop)
                .setBackGroundLevel(0.5f)//0.5f
                .setAnimationStyle(R.style.main_menu_animstyle)
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT)
                .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(iv_teacher_call)

        /**设置逻辑 */
        val view = popupWindow.contentView
        val cancel_layout = view.findViewById<LinearLayout>(R.id.cancel_layout)
        val sure_layout = view.findViewById<LinearLayout>(R.id.sure_layout)
        val content_tv = view.findViewById<TextView>(R.id.content_tv)
        content_tv.text = "确认拨打 $phone?"
        cancel_layout.setOnClickListener { popupWindow.dismiss() }
        sure_layout.setOnClickListener {
            initSimple(phone)
            popupWindow.dismiss()
        }

    }

    private fun unBinding(){
        val params = HashMap<String, Any>()
        params["teacherNum"] = cgNum
        params["areaNum"] = SpUtils.getString(applicationContext,AppConstants.CHANGGUAN_NUM)
        val subscription = Network.getInstance("教练解绑", this)
                .deleteTeacherBinding(params,
                        ProgressSubscriber("教练解绑", object : SubscriberOnNextListener<Bean<Any>> {
                            override fun onNext(result: Bean<Any>) {
                                EventBus.getDefault().post("解绑")
                                Toast.makeText(applicationContext,"解绑成功！",Toast.LENGTH_SHORT)
                                        .show()
                                finish()
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext,error,Toast.LENGTH_SHORT)
                                        .show()
                            }
                        }, this, false))
    }

    private fun requestPrivateData() {

        val params = HashMap<String, Any>()
        params["genTeacherNum"] = cgNum

        val subscription = Network.getInstance("教练详情", this)
                .teacherDetail(params,
                        ProgressSubscriber("教练详情", object : SubscriberOnNextListener<Bean<TeacherDetailBean>> {
                            override fun onNext(result: Bean<TeacherDetailBean>) {
                                setting(result.data)
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext,error,Toast.LENGTH_SHORT)
                                        .show()
                            }
                        }, this, false))
    }

    private fun setting(code: TeacherDetailBean) {
        phone = code.phone
        tips.text = code.tips
        val layoutParams =
                ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(30, 0, 30, 30)
        for (i in 0 until code.labelList.size) {
            val paramItemView = layoutInflater.inflate(R.layout.adapter_search_histroy, zf_label, false)
            val keyWordTv = paramItemView.findViewById<TextView>(R.id.tv_content)
            keyWordTv.setPadding(35, 5, 35, 5)
            keyWordTv.text = "# " + code.labelList[i]
            zf_label.addView(paramItemView, layoutParams)
        }


        val layoutParams1 =
                ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams1.setMargins(30, 0, 30, 30)
        for (i in 0 until code.skillList.size) {
            val paramItemView = layoutInflater.inflate(R.layout.adapter_search_histroy, zf_skill, false)
            val keyWordTv = paramItemView.findViewById<TextView>(R.id.tv_content)
            keyWordTv.setPadding(35, 5, 35, 5)
            keyWordTv.text = code.skillList[i]
            zf_skill.addView(paramItemView, layoutParams1)
        }

        tv_teacher_name.text = code.teacherName

        tv_sum_time.text = "累计服务时长：${code.serviceDur}小时"
        tv_teacher_pinfen.text = "${code.finalGrade}分"
        Glide.with(this).load(code.logoUrl)
                .placeholder(R.drawable.logo_gray)
                .into(banner)
        if (code.sex == 1) {
            iv_sex.setImageResource(R.drawable.man_icon)
        } else {
            iv_sex.setImageResource(R.drawable.women_icon)
        }

    }


    private fun initSimple(phone_number: String) {
        if (hasStoragePermission(this)) {
            //有权限
            callPhone(phone_number)
        } else {
            //申请权限
            EasyPermissions.requestPermissions(this, PERMISSION_STORAGE_MSG, PERMISSION_STORAGE_CODE, *PERMISSION_STORAGE)
        }
    }

    @SuppressLint("MissingPermission")
    fun callPhone(phoneNum: String) {
        val intent1 = Intent(Intent.ACTION_CALL)
        val data = Uri.parse("tel:$phoneNum")
        intent1.data = data
        startActivity(intent1)
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
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this)
                    .setTitle("提醒")
                    .setRationale("需要电话权限才能联系客服哦")
                    .build()
                    .show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)

    }

}
