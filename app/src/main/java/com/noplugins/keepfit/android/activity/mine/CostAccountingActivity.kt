package com.noplugins.keepfit.android.activity.mine

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseActivity
import kotlinx.android.synthetic.main.activity_cost_accounting.*

class CostAccountingActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {
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

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
