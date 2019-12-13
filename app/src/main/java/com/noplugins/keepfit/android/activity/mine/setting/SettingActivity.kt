package com.noplugins.keepfit.android.activity.mine.setting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.AboutActivity
import com.noplugins.keepfit.android.activity.ProductAdviceActivity
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.util.BaseUtils
import kotlinx.android.synthetic.main.title_activity.*
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_setting)
        title_tv.text = "设置"
    }

    override fun onBackPressed() {
        back()
    }
    private fun back(){
        setResult(3)
        finish()
    }
    override fun doBusiness(mContext: Context?) {
//        val layoutInflater = LayoutInflater.from(this)
        back_btn.setOnClickListener {
            back()
        }

        rl_account.setOnClickListener {
            if(BaseUtils.isFastClick()){
                val intent = Intent(this, AccountSecurityActivity::class.java)
                startActivity(intent)
            }
        }
        rl_about.setOnClickListener {
            if(BaseUtils.isFastClick()){
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }
        }
        rl_wenti.setOnClickListener {
            if(BaseUtils.isFastClick()){
                val intent = Intent(this, ProductAdviceActivity::class.java)
                startActivity(intent)
            }
        }
        rl_quit.setOnClickListener {
            if(BaseUtils.isFastClick()){

            }
        }
    }




}
