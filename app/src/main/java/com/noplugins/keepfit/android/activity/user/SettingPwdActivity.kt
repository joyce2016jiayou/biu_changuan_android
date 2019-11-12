package com.noplugins.keepfit.android.activity.user

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.google.gson.Gson
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.util.MD5Utils
import com.noplugins.keepfit.android.util.data.PwdCheckUtil
import com.noplugins.keepfit.android.util.data.StringsHelper
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_setting_pwd.*
import okhttp3.RequestBody
import java.util.*

class SettingPwdActivity : BaseActivity() {
    private var messageId = ""
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_setting_pwd)
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.setOnClickListener {
            finish()
        }
        btn_pwd_update.setOnClickListener {
            if (edit_phone.text.toString() == "") {
                Toast.makeText(applicationContext, "电话号码不能为空！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!StringsHelper.isMobileNO(edit_phone.text.toString())) {
                Toast.makeText(applicationContext, "电话号码格式不正确！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (edit_yzm.text.toString() == "") {
                Toast.makeText(applicationContext, "验证码不能空！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (edit_new_password.text.toString() == "") {
                Toast.makeText(applicationContext, "密码不能为空！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!PwdCheckUtil.isLetterDigit(edit_new_password.text.toString())) {
                Toast.makeText(applicationContext, "密码不符合规则！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (edit_new_password1.text.toString() == "") {
                Toast.makeText(applicationContext, "确认密码不能为空！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (edit_new_password1.text.toString() != edit_new_password.text.toString()) {
                Toast.makeText(applicationContext, "两次输入的密码不一致！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            settingPwd()
        }

        tv_send.setOnClickListener {
            if (edit_phone.text.toString() == "") {
                Toast.makeText(applicationContext, "电话号码不能为空！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!StringsHelper.isMobileNO(edit_phone.text.toString())) {
                Toast.makeText(applicationContext, "电话号码格式不正确！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            tv_send.isEnabled = false//设置不可点击，等待60秒过后可以点击
            timer.start()
            //获取验证码接口
            send()
        }
    }


    private fun settingPwd() {
        val params = HashMap<String, Any>()
        params["phone"] = edit_phone.text.toString()
        params["messageId"] = messageId
        params["code"] = edit_yzm.text.toString()
        params["password"] = edit_new_password.text.toString()
        subscription = Network.getInstance("设置密码", this)
            .forgetPassword(params,
                ProgressSubscriber("设置密码", object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {
                        finish()
                    }

                    override fun onError(error: String) {

                    }
                }, this, false)
            )
    }

    private fun send() {
        val params = HashMap<String, Any>()
        params["phone"] = edit_phone.text.toString()
        params["sign"] = "MES${MD5Utils.stringToMD5(edit_phone.text.toString())}"
        params["time"] = System.currentTimeMillis()
        subscription = Network.getInstance("获取验证码", this)
            .get_yanzhengma(params,
                ProgressSubscriber("获取验证码", object : SubscriberOnNextListener<Bean<String>> {
                    override fun onNext(result: Bean<String>) {
                        messageId = result.data
                    }

                    override fun onError(error: String) {

                    }
                }, this, false)
            )
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
