package com.noplugins.keepfit.android.activity.mine

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
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import kotlinx.android.synthetic.main.activity_private_detail.*
import java.util.HashMap

class TeacherDetailActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentView(R.layout.activity_private_detail)
    }

    override fun doBusiness(mContext: Context?) {
    }

    private fun requestPrivateData() {

        val params = HashMap<String, Any>()
        params["teacherNum"] = intent.getStringExtra("teacherNum")
        params["userNum"] = SpUtils.getString(applicationContext, AppConstants.USER_NAME)

        val subscription = Network.getInstance("教练详情", this)
                .teacherDetails(params,
                        ProgressSubscriber("教练详情", object : SubscriberOnNextListener<Bean<PrivateDetailBean>> {
                            override fun onNext(result: Bean<PrivateDetailBean>) {
                                setting(result.data)
                            }

                            override fun onError(error: String) {

                            }
                        }, this, false))
    }

    private fun setting(code: PrivateDetailBean) {
        val layoutParams =
                ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(30, 0, 30, 30)
        for (i in 0 until code.teacherDetail.labelList.size) {
            val paramItemView = layoutInflater.inflate(R.layout.adapter_search_histroy, zf_label, false)
            val keyWordTv = paramItemView.findViewById<TextView>(R.id.tv_content)
            keyWordTv.setPadding(35, 5, 35, 5)
            keyWordTv.text = "# " + code.teacherDetail.labelList[i]
            zf_label.addView(paramItemView, layoutParams)
        }


        val layoutParams1 =
                ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams1.setMargins(30, 0, 30, 30)
        for (i in 0 until code.teacherDetail.skillList.size) {
            val paramItemView = layoutInflater.inflate(R.layout.adapter_search_histroy, zf_skill, false)
            val keyWordTv = paramItemView.findViewById<TextView>(R.id.tv_content)
            keyWordTv.setPadding(35, 5, 35, 5)
            keyWordTv.text = code.teacherDetail.skillList[i]
            zf_skill.addView(paramItemView, layoutParams1)
        }

        tv_teacher_name.text = code.teacherDetail.teacherName

        tv_sum_time.text = "累计服务时长：${code.teacherDetail.serviceDur}小时"
        tv_teacher_pinfen.text = "${code.teacherDetail.card}分"
        Glide.with(this).load(code.teacherDetail.logoUrl).into(banner)
        if (code.teacherDetail.sex == 1) {
            iv_sex.setImageResource(R.drawable.man_icon)
        } else {
            iv_sex.setImageResource(R.drawable.women_icon)
        }

    }

}
