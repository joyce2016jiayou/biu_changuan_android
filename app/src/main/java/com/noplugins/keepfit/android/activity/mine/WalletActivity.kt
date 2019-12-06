package com.noplugins.keepfit.android.activity.mine

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.mine.VerificationPhoneActivity
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.mine.WalletBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow
import kotlinx.android.synthetic.main.activity_wallet.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.HashMap

class WalletActivity : BaseActivity() {
    private var walletNum = ""
    private var finalCanWithdraw = 0.0
    private var havePayPassWord = -1
    override fun initBundle(parms: Bundle?) {
    }


    override fun initView() {
        setContentView(R.layout.activity_wallet)
        EventBus.getDefault().register(this)
        if (SpUtils.getInt(applicationContext,AppConstants.USER_TYPE) == 1){
            ll_tixian.visibility = View.VISIBLE
            tv_pay_mx.visibility = View.VISIBLE
        }
        requestData()
    }

    override fun doBusiness(mContext: Context?) {
        tv_pay_mx.setOnClickListener {
            //跳转到明细
            val intent = Intent(this, BillDetailActivity::class.java)
            intent.putExtra("walletNum",walletNum)
            startActivity(intent)
        }
        back_btn.setOnClickListener {
            setResult(SpUtils.getInt(applicationContext,AppConstants.FRAGMENT_SIZE)-1)
            finish()
        }
        btn_tixian.setOnClickListener {

//

            if (SpUtils.getInt(applicationContext,AppConstants.IS_TX) == 1){
                if (finalCanWithdraw < 1000){
                    Toast.makeText(applicationContext,"可提现金额必须大于1000",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val intent = Intent(this, WithdrawActivity::class.java)
                val bundle = Bundle()
                bundle.putDouble("finalCanWithdraw",finalCanWithdraw)
                intent.putExtras(bundle)
                startActivity(intent)
                return@setOnClickListener
            }
            //跳转到提现
            toQueren(btn_tixian)

        }
     }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun upadate(messageEvent:String ) {
        if ("提现了金额" == messageEvent){
            requestData()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    private fun toQueren(view1: TextView) {
        val popupWindow = CommonPopupWindow.Builder(this)
            .setView(R.layout.dialog_to_setting)
            .setBackGroundLevel(0.5f)//0.5f
            .setAnimationStyle(R.style.main_menu_animstyle)
            .setWidthAndHeight(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(view1)

        /**设置逻辑 */
        val view = popupWindow.contentView
        val cancel = view.findViewById<LinearLayout>(R.id.cancel_layout)
        val sure = view.findViewById<LinearLayout>(R.id.sure_layout)
        cancel.setOnClickListener {
            popupWindow.dismiss()
        }
        sure.setOnClickListener {
            popupWindow.dismiss()
            //去申请
            toSetting()
        }
    }

    private fun toSetting(){
        val intent = Intent(this,VerificationPhoneActivity::class.java)
        startActivity(intent)
    }

    private fun setting(bean:WalletBean){
        tv_balance.text = "¥ ${bean.finalBalance}"
        tv_sum_sr.text = "¥ ${bean.finalTotalIncome}"
        tv_day_sr.text = "¥ ${bean.finalTotalTodayIncome}"
        tv_month_sr.text = "¥ ${bean.finalTotalMonthIncome}"
        tv_sum_withdraw.text = "¥ ${bean.finalTotalWithdraw}"
        tv_now_withdraw.text = "¥ ${bean.finaTotalCanWithdraw}"
        havePayPassWord = bean.havePayPassWord
        walletNum = bean.walletNum
        finalCanWithdraw = bean.finaTotalCanWithdraw
    }

    //myBalance
    private fun requestData() {
        val params = HashMap<String, Any>()
//        params["userNum"] = SpUtils.getString(this, AppConstants.USER_NAME)
        params["gymAreaNum"] = SpUtils.getString(this,AppConstants.CHANGGUAN_NUM)
        params["gymUserNum"] = SpUtils.getString(this,AppConstants.USER_NAME)
        val subscription = Network.getInstance("我的钱包", this)
            .myBalance(
                params,
                ProgressSubscriber("我的钱包", object : SubscriberOnNextListener<Bean<WalletBean>> {
                    override fun onNext(result: Bean<WalletBean>) {
                        setting(result.data)
                    }

                    override fun onError(error: String) {

                    }
                }, this, false)
            )
    }

    override fun onBackPressed() {
        setResult(SpUtils.getInt(applicationContext,AppConstants.FRAGMENT_SIZE)-1)
        finish()
    }

}
