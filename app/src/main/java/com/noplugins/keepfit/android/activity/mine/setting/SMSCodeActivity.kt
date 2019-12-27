package com.noplugins.keepfit.android.activity.mine.setting

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.mine.UpdatePasswordActivity
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.LoginBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.MD5
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import kotlinx.android.synthetic.main.activity_smscode.*
import java.util.HashMap

class SMSCodeActivity : BaseActivity() {
    private var bool = ""
    private var messageId = ""
    private var phone = ""
    private var setPwd = -1
    override fun initBundle(parms: Bundle?) {
        if (parms!=null){
            bool = parms.getString("newPhone","")
            setPwd = parms.getInt("setPwd",-1)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        setContentLayout(R.layout.activity_smscode)
        isShowTitle(true)
        setTitleView(R.string.tv_sms_code)
        tv_phone.text = "短信已发送至${SpUtils.getString(applicationContext,AppConstants.PHONE)}"
        phone = SpUtils.getString(applicationContext,AppConstants.PHONE)
        tv_send_code.isEnabled = false//设置不可点击，等待60秒过后可以点击

        if (bool.isNotEmpty()){
            tv_phone.text = "短信已发送至$bool"
            phone = bool
            tv_next.text = "完成"
        }

        send()
    }

    override fun doBusiness(mContext: Context?) {
        title_left_button_onclick_listen {
            finish()
        }

        tv_next.setOnClickListener {
            if (BaseUtils.isFastClick()){
                if (et_input.text.length != 6){
                    Toast.makeText(applicationContext,"请输入6位数的验证码",Toast.LENGTH_SHORT)
                            .show()
                }
                if(bool.isNotEmpty()){
                    //验证新手机号

                    updateUserPhone()

                    return@setOnClickListener
                }

                vercode()

            }
        }
    }

    /**
     * 验证 验证码
     */
    private fun vercode(){
        val params = HashMap<String, Any>()
        params["messageId"] = messageId
        params["code"] = et_input.text.toString()
        params["phone"] = phone
        subscription = Network.getInstance("验证验证码和登录", this)
                .verifyCodeLogin(params,ProgressSubscriber("验证验证码",
                        object : SubscriberOnNextListener<Bean<LoginBean>>{
                            override fun onNext(t: Bean<LoginBean>?) {
                                if (setPwd !=-1){
                                    val intent = Intent(this@SMSCodeActivity, UpdatePasswordActivity::class.java)
                                    if (setPwd == 1){
                                        intent.putExtra("form","loginPwd")
                                    }
                                    startActivity(intent)
                                    finish()
                                    return
                                }


                                val intent = Intent(this@SMSCodeActivity, InputNewPhoneActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                            override fun onError(error: String?) {
                                Toast.makeText(applicationContext,error,Toast.LENGTH_SHORT)
                                        .show()
                            }

                        },this,true))
    }

    /**
     * 获取验证码
     */
    private fun send() {
        timer.start()
        val params = HashMap<String, Any>()
        params["phone"] = phone
        params["sign"] = "${MD5.stringToMD5("MES$phone")}"
        params["time"] = System.currentTimeMillis()
        subscription = Network.getInstance("获取验证码", this)
                .get_yanzhengma(
                        params,
                        ProgressSubscriber("获取验证码", object : SubscriberOnNextListener<Bean<String>> {
                            override fun onNext(result: Bean<String>) {
                                messageId = result.data
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext,error,Toast.LENGTH_SHORT)
                                        .show()
                            }
                        }, this, false)
                )
    }

    /**
     * 修改手机号
     */
    private fun updateUserPhone() {
        val params = HashMap<String, Any>()
        params["newPhone"] = phone
        params["userNum"] = SpUtils.getString(applicationContext,AppConstants.USER_NAME)
        params["messageId"] =messageId
        params["code"] =et_input.text.toString()
        subscription = Network.getInstance("获取验证码", this)
                .updateUserPhone(
                        params,
                        ProgressSubscriber("获取验证码", object : SubscriberOnNextListener<Bean<Any>> {
                            override fun onNext(result: Bean<Any>) {
                                Toast.makeText(applicationContext,result.message,Toast.LENGTH_SHORT)
                                        .show()
                                SpUtils.putString(applicationContext,AppConstants.PHONE,phone)
                                InputNewPhoneActivity.inputNewPhoneActivity.finish()
                                finish()
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext,error,Toast.LENGTH_SHORT)
                                        .show()
                            }
                        }, this, false)
                )
    }
    internal var timer: CountDownTimer = object : CountDownTimer(60000, 1000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {
            tv_send_code.setTextColor(Color.parseColor("#181818"))
            tv_send_code.text = "${millisUntilFinished / 1000}秒后可重新发送"

        }

        override fun onFinish() {
            tv_send_code.setTextColor(Color.parseColor("#929292"))
            tv_send_code.text = "重新发送验证码"
            tv_send_code.isEnabled = true
        }
    }


}
