package com.noplugins.keepfit.android.activity.mine

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import kotlinx.android.synthetic.main.activity_cost_accounting.*
import java.util.HashMap
import com.noplugins.keepfit.android.util.CashierInputFilter
import java.math.BigDecimal
import java.math.RoundingMode


class CostAccountingActivity : BaseActivity() {
    var form = "main"
    var cost = 0.0
    override fun initBundle(parms: Bundle?) {
        if (parms != null) {
            form = parms.getString("form", "main")
            Log.d("tag__AAA",form)
        }
    }

    override fun initView() {
        setContentView(R.layout.activity_cost_accounting)
        val filters = arrayOf(CashierInputFilter())
        et_fangzu.filters = filters
        et_nengyuan.filters = filters
        et_renyuan.filters = filters
        et_yunying.filters = filters
    }

    override fun doBusiness(mContext: Context?) {
        tv_hesuan.setBtnOnClickListener {
            tv_hesuan.startLoading()
            Handler().postDelayed(Runnable {
                count()
                tv_hesuan.loadingComplete()

                tv_result.visibility = View.VISIBLE
            }, 2000)

        }
        back_btn.setOnClickListener {
            if (form == "pay" && cost != 0.0) {
                val mIntent = Intent()//没有任何参数（意图），只是用来传递数据
                mIntent.putExtra("cost", ""+cost)
                setResult(RESULT_OK, mIntent)
                Log.d("tag__BBB",form)
            } else {
                setResult(SpUtils.getInt(applicationContext,AppConstants.FRAGMENT_SIZE)-1)
            }


            finish()
        }

    }

    private fun count() {
        if (et_fangzu.text.toString() == ""||et_nengyuan.text.toString()==""||
                et_renyuan.text.toString()==""||et_yunying.text.toString()==""||
                et_renci.text.toString()=="") {
            tv_result.text = "计算错误，请输入正确的内容"
            return
        }
        val fangzu = et_fangzu.text.toString().toBigDecimal()
        val nengyuan = et_nengyuan.text.toString().toBigDecimal()
        val renyuan = et_renyuan.text.toString().toBigDecimal()
        val yunying = et_yunying.text.toString().toBigDecimal()
        val sum = et_renci.text.toString().toBigDecimal()
        cost = (fangzu.add(nengyuan).add(renyuan).add(yunying)).divide(sum, 2, RoundingMode.HALF_UP).toDouble()
        tv_result.text = "当前经营成本： $cost/人/次"

        request()
    }

    private fun request() {
        val params = HashMap<String, Any>()
        params["area_num"] = SpUtils.getString(applicationContext, AppConstants.CHANGGUAN_NUM)
        params["cost"] = cost
        subscription = Network.getInstance("成本核算", this)
                .updateCost(
                        params,
                        ProgressSubscriber("成本核算", object : SubscriberOnNextListener<Bean<Any>> {
                            override fun onNext(result: Bean<Any>) {
                                SpUtils.putString(applicationContext,AppConstants.COST,"$cost")
                                Toast.makeText(applicationContext, "上传成功", Toast.LENGTH_SHORT).show()
                            }

                            override fun onError(error: String) {

                            }
                        }, this, false)
                )
    }

    override fun onBackPressed() {
        Log.d("pay","pay:"+form)
        if (form == "pay" && cost!=0.0) {
            val mIntent = Intent()//没有任何参数（意图），只是用来传递数据
            mIntent.putExtra("cost", ""+cost)
            setResult(RESULT_OK, mIntent)
        } else {
            setResult(SpUtils.getInt(applicationContext,AppConstants.FRAGMENT_SIZE)-1)
        }


        finish()
    }
}
