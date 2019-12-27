package com.noplugins.keepfit.android.activity.mine.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.mine.UpdatePasswordActivity
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
        setContentLayout(R.layout.activity_account_security)
        isShowTitle(true)
        setTitleView(R.string.tv_anquan)
        tv_now_phone.text = SpUtils.getString(applicationContext, AppConstants.PHONE)
    }

    override fun doBusiness(mContext: Context?) {
        title_left_button_onclick_listen {
            finish()
        }
        rl_update_phone.setOnClickListener{
            val intent = Intent(this,UpdatePhoneActivity::class.java)
            startActivity(intent)
        }
        rl_setting_pwd.setOnClickListener {
            val intent = Intent(this, SMSCodeActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("setPwd",1)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        rl_update_pwd.setOnClickListener {
            val intent = Intent(this, SMSCodeActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("setPwd",2)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        tv_now_phone.text = SpUtils.getString(applicationContext, AppConstants.PHONE)
    }

}
