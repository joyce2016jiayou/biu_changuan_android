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
        setContentLayout(R.layout.activity_withdraw_complete)
        isShowTitle(true)
        setTitleView(R.string.tv_tixian,0,0,true,R.string.tv123)
    }

    override fun doBusiness(mContext: Context?) {
        title_right_button_onclick_listen {
            killMe()
        }
    }

    override fun onBackPressed() {
        killMe()
    }

    private fun killMe(){
        EventBus.getDefault().post("转出了金额")
        finish()
    }
}
