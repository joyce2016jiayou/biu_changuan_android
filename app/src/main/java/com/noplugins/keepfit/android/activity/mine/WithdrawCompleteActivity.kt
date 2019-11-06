package com.noplugins.keepfit.android.activity.mine

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseActivity
import kotlinx.android.synthetic.main.activity_withdraw_complete.*
import org.greenrobot.eventbus.EventBus

class WithdrawCompleteActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_withdraw_complete)
    }

    override fun doBusiness(mContext: Context?) {
        tv_complete.setOnClickListener {
            killMe()
        }
    }

    override fun onBackPressed() {
        killMe()
    }

    private fun killMe(){
        EventBus.getDefault().post("提现了金额")
        finish()
    }
}
