package com.noplugins.keepfit.android.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast

import com.google.gson.Gson
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener

import java.util.HashMap

import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_product_advice.*
import kotlinx.android.synthetic.main.title_activity.*
import okhttp3.RequestBody

class ProductAdviceActivity : BaseActivity() {
    private var type = 1

    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentLayout(R.layout.activity_product_advice)
        title_tv.text = "问题反馈"
    }

    override fun doBusiness(mContext: Context) {
        cb_product_suggest.setOnClickListener(View.OnClickListener {
            if (cb_product_suggest.isChecked) {
                type = 1
                cb_fault_feedback.isChecked = false
                cb_other.isChecked = false
            } else {
                type = 0
            }
        })
        cb_fault_feedback.setOnClickListener(View.OnClickListener {
            if (cb_fault_feedback.isChecked) {
                type = 2
                cb_product_suggest.isChecked = false
                cb_other.isChecked = false
            } else {
                type = 0
            }
        })
        cb_other.setOnClickListener(View.OnClickListener {
            if (cb_other.isChecked) {
                type = 3
                cb_fault_feedback.isChecked = false
                cb_product_suggest.isChecked = false
            } else {
                type = 0
            }
        })


        back_btn.setOnClickListener(View.OnClickListener {
            setResult(SpUtils.getInt(applicationContext, AppConstants.FRAGMENT_SIZE) - 1)
            finish()
        })

        add_class_teacher_btn.setOnClickListener(View.OnClickListener {
            //
            if (type == 0) {
                Toast.makeText(applicationContext, "请选择反馈类型", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            submit()
        })
    }

    private fun submit() {
        //
        if (edit_content.text.toString().length < 10) {
            Toast.makeText(applicationContext, "请输入10个字以上的问题描述", Toast.LENGTH_SHORT)
                    .show()
            return
        }
        val params = HashMap<String, Any>()
        params["gymAreaNum"] = SpUtils.getString(applicationContext, AppConstants.CHANGGUAN_NUM)
        params["gymUserNum"] = SpUtils.getString(applicationContext, AppConstants.USER_NAME)
        params["feedbackType"] = "" + type
        params["feedbackDes"] = edit_content.getText().toString()

        subscription = Network.getInstance("产品反馈", applicationContext)

                .feedback(params, ProgressSubscriber("产品反馈", object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(objectBean: Bean<Any>) {
                        Toast.makeText(applicationContext, "提交成功", Toast.LENGTH_SHORT).show()
                        setResult(SpUtils.getInt(applicationContext, AppConstants.FRAGMENT_SIZE) - 1)
                        finish()
                    }

                    override fun onError(error: String) {
                        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
                    }
                }, this, true))
    }

    override fun onBackPressed() {
        setResult(SpUtils.getInt(applicationContext, AppConstants.FRAGMENT_SIZE) - 1)
        finish()
    }
}
