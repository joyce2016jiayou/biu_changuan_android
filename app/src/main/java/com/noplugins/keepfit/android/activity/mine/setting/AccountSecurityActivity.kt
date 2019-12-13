package com.noplugins.keepfit.android.activity.mine.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.mine.VerificationPhoneActivity
import com.noplugins.keepfit.android.activity.user.SettingPwdActivity
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.SpUtils
import kotlinx.android.synthetic.main.activity_account_security.*
import kotlinx.android.synthetic.main.title_activity.*

class AccountSecurityActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_account_security)
        title_tv.text = "安全管理"
        tv_now_phone.text = SpUtils.getString(applicationContext, AppConstants.PHONE)
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.setOnClickListener {
            finish()
        }
        rl_update_phone.setOnClickListener{
            val intent = Intent(this,UpdatePhoneActivity::class.java)
            startActivity(intent)
        }
        rl_setting_pwd.setOnClickListener {
            val intent = Intent(this, SettingPwdActivity::class.java)
            startActivity(intent)
        }
        rl_update_pwd.setOnClickListener {
            val intent = Intent(this, VerificationPhoneActivity::class.java)
            intent.putExtra("update", true)
            startActivity(intent)
        }
    }

}
