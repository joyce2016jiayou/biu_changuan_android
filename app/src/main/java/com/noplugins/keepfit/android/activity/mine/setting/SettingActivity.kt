package com.noplugins.keepfit.android.activity.mine.setting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.AboutActivity
import com.noplugins.keepfit.android.activity.ProductAdviceActivity
import com.noplugins.keepfit.android.activity.user.Login2Activity
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.global.PublicPopControl
import com.noplugins.keepfit.android.util.ActivityCollectorUtil
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.ui.pop.base.CenterPopupView
import com.noplugins.keepfit.android.util.ui.pop.inteface.ViewCallBack
import kotlinx.android.synthetic.main.title_activity.*
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_setting)
        title_tv.text = "设置"

        isShowTitle(true)
        setTitleView(R.string.vunue_info)

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
                checkOut()
            }
        }
    }

    private fun checkOut() {
        PublicPopControl.alert_dialog_center(this) { view, popup ->
            val content = view.findViewById<TextView>(R.id.pop_content)
            val title = view.findViewById<TextView>(R.id.pop_title)
            content.setText("确定登出哔呦账户吗？")
            title.setText("登出账户")
            view.findViewById<LinearLayout>(R.id.cancel_btn)
                    .setOnClickListener {
                        popup.dismiss()
                    }
            view.findViewById<LinearLayout>(R.id.sure_btn)
                    .setOnClickListener {
                        popup.dismiss()
                        toLogin()}
        }
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
}

