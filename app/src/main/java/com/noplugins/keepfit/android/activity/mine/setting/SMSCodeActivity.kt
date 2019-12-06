package com.noplugins.keepfit.android.activity.mine.setting

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import kotlinx.android.synthetic.main.activity_smscode.*
import kotlinx.android.synthetic.main.title_activity.*

class SMSCodeActivity : BaseActivity() {
    var bool = false
    override fun initBundle(parms: Bundle?) {
        if (parms!=null){
            bool = parms.getBoolean("newPhone",false)
        }
    }

    override fun initView() {
        setContentView(R.layout.activity_smscode)
        tv_phone.text = "短信已发送至${SpUtils.getString(applicationContext,AppConstants.PHONE)}"
        title_tv.text = "短信验证码"

        tv_send_code.isEnabled = false//设置不可点击，等待60秒过后可以点击
        timer.start()

        if (bool){
            tv_next.text = "完成"
        }
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.setOnClickListener {
            finish()
        }

        tv_next.setOnClickListener {
            if (BaseUtils.isFastClick()){
                if(bool){
                    finish()
                    return@setOnClickListener
                }
                val intent = Intent(this, InputNewPhoneActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    internal var timer: CountDownTimer = object : CountDownTimer(60000, 1000) {
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
