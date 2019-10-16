package com.noplugins.keepfit.android.activity.mine

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.util.BaseUtils
import kotlinx.android.synthetic.main.activity_cost_accounting.*

class CostAccountingActivity : BaseActivity() {
    var form = "main"
    override fun initBundle(parms: Bundle?) {
        if (parms!=null){
            form = parms.getString("form","main")
        }
    }
    override fun initView() {
        setContentView(R.layout.activity_cost_accounting)
    }

    override fun doBusiness(mContext: Context?) {
        tv_hesuan.setBtnOnClickListener {
            tv_hesuan.startLoading()
            Handler().postDelayed(Runnable {
                tv_hesuan.loadingComplete()
                tv_result.visibility = View.VISIBLE
            }, 2000)

        }
        back_btn.setOnClickListener {
            setResult(3)
            finish()
        }

    }


    override fun onBackPressed() {
        setResult(3)
        finish()
    }
}
