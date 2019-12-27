package com.noplugins.keepfit.android.activity.mine.setting

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import kotlinx.android.synthetic.main.activity_input_new_phone.*

class InputNewPhoneActivity : BaseActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var inputNewPhoneActivity: InputNewPhoneActivity
    }
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentLayout(R.layout.activity_input_new_phone)
        isShowTitle(true)
        setTitleView(R.string.tv_input_new_phone)
        inputNewPhoneActivity = this
        val phone = SpUtils.getString(applicationContext,AppConstants.PHONE)
        tv_now_phone.text = "您目前的手机号为：$phone，您想要变更为？"
    }

    override fun doBusiness(mContext: Context?) {
       title_left_button_onclick_listen {
           finish()
       }
        et_new_phone

        tv_send_code.setOnClickListener {
            if (BaseUtils.isFastClick()){
                if (et_new_phone.text.toString() == ""||et_new_phone.text.toString().length<11){
                    Toast.makeText(applicationContext,"请输入正确的手机号",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val intent = Intent(this, SMSCodeActivity::class.java)
                val bundle = Bundle()
                bundle.putString("newPhone",et_new_phone.text.toString())
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
