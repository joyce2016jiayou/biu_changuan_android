package com.noplugins.keepfit.android.activity.mine

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.user.Login2Activity
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.LoginBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.ActivityCollectorUtil
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.MD5
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import kotlinx.android.synthetic.main.activity_verification_phone.*
import java.util.*

class VerificationPhoneActivity : BaseActivity() {
    var messageId = ""
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentLayout(R.layout.activity_verification_phone)
        isShowTitle(true)
        setTitleView(R.string.tv_verification)
//        tv_phone.text = intent.getStringExtra("newPhone")
//        if (intent.getStringExtra("newPhone") == ""){
//
//        }
        tv_phone.text = SpUtils.getString(applicationContext, AppConstants.PHONE)
        if (intent.getBooleanExtra("update", false)) {
            tv_btn_text.text = "完成"
        } else {
            tv_btn_text.text = "下一步"
        }

    }

    override fun doBusiness(mContext: Context?) {
        tv_send.setOnClickListener {
            tv_send.isEnabled = false//设置不可点击，等待60秒过后可以点击
            timer.start()
            //获取验证码接口
            send()
        }
        btn_ToLogin.setOnClickListener {
            //            if (intent.getBooleanExtra("update", false)) {
//                updatePhone()
//            } else {
//
//            }

           if (BaseUtils.isFastClick()){
               verficationCode()
           }


        }
        title_left_button_onclick_listen {
            finish()
        }
    }

    private fun send() {
        val params = HashMap<String, Any>()
        params["phone"] = tv_phone.text.toString()
        params["sign"] = "${MD5.stringToMD5("MES"+tv_phone.text.toString())}"
        params["time"] = System.currentTimeMillis()
        subscription = Network.getInstance("获取验证码", this)
                .get_yanzhengma(
                        params,
                        ProgressSubscriber("获取验证码", object : SubscriberOnNextListener<Bean<String>> {
                            override fun onNext(result: Bean<String>) {
                                messageId = result.data
                            }

                            override fun onError(error: String) {

                            }
                        }, this, false)
                )
    }


    private fun verficationCode() {
        val params = HashMap<String, Any>()
        params["messageId"] = messageId
        params["code"] = edit_yzm.text.toString()
        params["phone"] = tv_phone.text.toString()
        subscription = Network.getInstance("验证验证码和登录", this)
                .verifyCodeLogin(params, ProgressSubscriber("验证验证码和登录",
                        object : SubscriberOnNextListener<Bean<LoginBean>> {
                            override fun onNext(t: Bean<LoginBean>?) {
                                val intent = Intent(this@VerificationPhoneActivity, UpdatePasswordActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                            override fun onError(error: String) {
                                Log.e(TAG, "修改失败：$error")
                                Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
                            }
                        },
                        this,
                        false
                )
                )
    }

    private fun updatePhone() {
        if (tv_send.text.toString() == "") {
            Toast.makeText(applicationContext, "验证码不能为空！", Toast.LENGTH_SHORT).show()
            return
        }
        val params = HashMap<String, Any>()
        params["phone"] = tv_phone.text.toString()
        params["messageId"] = messageId
        params["code"] = edit_yzm.text.toString()
        params["userNum"] = SpUtils.getString(applicationContext, AppConstants.USER_NAME)
        subscription = Network.getInstance("修改手机号", this)
                .updatePhone(
                        params,
                        ProgressSubscriber<Any>(
                                "修改手机号",
                                object : SubscriberOnNextListener<Bean<Any>> {
                                    override fun onNext(result: Bean<Any>) {
//                            toLogin()
                                    }

                                    override fun onError(error: String) {
                                        Log.e(TAG, "修改失败：$error")
                                        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
                                    }
                                },
                                this,
                                false
                        )
                )
    }


    private fun toLogin() {
        val intent = Intent(this, Login2Activity::class.java)
        //退出
        SpUtils.putString(applicationContext, AppConstants.TOKEN, "")
        SpUtils.putString(applicationContext, AppConstants.PHONE, "")
        SpUtils.putString(applicationContext, AppConstants.USER_NAME, "")
        startActivity(intent)
        ActivityCollectorUtil.finishAllActivity()

    }

    internal var timer: CountDownTimer = object : CountDownTimer(60000, 1000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {
            tv_send.setTextColor(Color.parseColor("#292C31"))
            tv_send.text = "已发送(${millisUntilFinished / 1000})"

        }

        override fun onFinish() {
            tv_send.setTextColor(Color.parseColor("#FFBA02"))
            tv_send.text = "重新获取"
            tv_send.isEnabled = true
        }
    }
}
