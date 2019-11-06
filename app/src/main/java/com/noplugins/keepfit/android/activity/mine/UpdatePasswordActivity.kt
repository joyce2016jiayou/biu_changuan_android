package com.noplugins.keepfit.android.activity.mine

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.google.gson.Gson
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.LoginActivity
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.ActivityCollectorUtil
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.data.PwdCheckUtil
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import kotlinx.android.synthetic.main.activity_update_tixian.*
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus

import java.util.HashMap

class UpdatePasswordActivity : BaseActivity() {


    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentLayout(R.layout.activity_update_tixian)
    }



    override fun doBusiness(mContext: Context) {
        back_btn.setOnClickListener(View.OnClickListener { finish() })
        login_btn.setOnClickListener(View.OnClickListener { updatePassword() })
    }

    private fun updatePassword() {
        if (!PwdCheckUtil.isPayPassWord(edit_new_password1.getText().toString())) {
            Toast.makeText(this, "密码不符合规则！", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(edit_new_password1.getText().toString())) {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(edit_password2.getText().toString())) {
            Toast.makeText(this, "确认密码不能为空！", Toast.LENGTH_SHORT).show()
            return
        }
        if (edit_new_password1.getText().toString() != edit_password2.getText().toString()){
            Toast.makeText(this, "两次输入的密码不一致！", Toast.LENGTH_SHORT).show()
            return
        }
        val params = HashMap<String, Any>()
        params["userNum"] = SpUtils.getString(applicationContext, AppConstants.USER_NAME)
        params["paypassword"] = edit_new_password1.text.toString()
        subscription = Network.getInstance("设置提现密码", this)
            .settingPayPassword(params,
                ProgressSubscriber("设置提现密码", object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {
//                        toLogin()
                        SpUtils.putInt(getApplicationContext(), AppConstants.IS_TX,1)
                        EventBus.getDefault().post("提现了金额")
                        finish()
                    }

                    override fun onError(error: String) {

                    }
                }, this, false)
            )
    }

    private fun toLogin() {
        val intent = Intent(this@UpdatePasswordActivity, LoginActivity::class.java)
        //退出
        SpUtils.putString(applicationContext, AppConstants.TOKEN, "")
        SpUtils.putString(applicationContext, AppConstants.PHONE, "")
        SpUtils.putString(applicationContext, AppConstants.USER_NAME, "")
        startActivity(intent)
        ActivityCollectorUtil.finishAllActivity()

    }
}
