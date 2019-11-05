package com.noplugins.keepfit.android.activity.mine

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.*
import android.text.style.AbsoluteSizeSpan
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lwjfork.code.CodeEditText
import com.nanchen.bankcardutil.BankInfoUtil
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.mine.VerificationPhoneActivity
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.BankCradBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.HideDataUtil
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow
import kotlinx.android.synthetic.main.activity_withdraw.*
import java.util.HashMap

class WithdrawActivity : BaseActivity() {
    var cardNumber = ""
    var selectCard = -1
    var finalCanWithdraw = 0.0
    override fun initBundle(parms: Bundle?) {
        if (parms!=null){
            finalCanWithdraw = parms.getDouble("finalCanWithdraw")
        }
    }

    override fun initView() {
        setContentView(R.layout.activity_withdraw)
        tv_name.text = "持卡人  "+SpUtils.getString(this, AppConstants.NAME)
        //超过1000元可以提现
        val ss = SpannableString("超过1000元可以提现")//定义hint的值
        val ass = AbsoluteSizeSpan(15, true)//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        et_withdraw_money.hint = SpannedString(ss)
        tv_withdraw_ok.isClickable = false

        requestBankCard()
    }

    override fun onResume() {
        super.onResume()
    }
    override fun doBusiness(mContext: Context?) {
        tv_now_money.text = "当前可提现余额$finalCanWithdraw"
        back_btn.setOnClickListener {
            finish()
        }
        rl_money_details.setOnClickListener {
            currentFocus?.let {
                val mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                mInputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                et_withdraw_money.clearFocus()
            }

        }
        iv_delete_edit.setOnClickListener {
            et_withdraw_money.setText("")
        }
        tv_all.setOnClickListener {
            et_withdraw_money.setText("$finalCanWithdraw")
        }

        tv_withdraw_ok.setOnClickListener {
            //提现操作
            if (et_withdraw_money.text.toString().toDouble() < 1000){
               Toast.makeText(applicationContext,"提现金额不能小于1000",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            toInputPwd(tv_withdraw_ok)
        }
        et_withdraw_money.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
             }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()){
                    iv_delete_edit.visibility = View.VISIBLE
                    if (s[0].toString() != "."){
                        if (s.toString().toDouble() > finalCanWithdraw){
                            tv_tips.visibility = View.VISIBLE
                            tv_withdraw_ok.setBackgroundResource(R.drawable.shape_btn_bg_gray_40)
                            tv_withdraw_ok.isClickable = false
                        }
                        else {
                            tv_tips.visibility = View.GONE
                            tv_withdraw_ok.setBackgroundResource(R.drawable.shape_btn_bg_40)
                            tv_withdraw_ok.isClickable = true
                        }

                    }
                } else {
                    iv_delete_edit.visibility = View.GONE
                    tv_tips.visibility = View.GONE
                    tv_withdraw_ok.setBackgroundResource(R.drawable.shape_btn_bg_40)
                    tv_withdraw_ok.isClickable = true
                }


            }


        })

        ll_content.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                currentFocus?.let {

                    val mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    mInputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                    et_withdraw_money.clearFocus()
                    return false

                }
                return false
            }

        })
    }


    private fun toInputPwd(view1: TextView) {
        val popupWindow = CommonPopupWindow.Builder(this)
            .setView(R.layout.dialog_to_input_pwd)
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
        val etPwd = view.findViewById<CodeEditText>(R.id.et_password)
        val tvWjPwd = view.findViewById<TextView>(R.id.tv_wj_pwd)
        val tv_money = view.findViewById<TextView>(R.id.tv_money)
        tv_money.text = "¥${et_withdraw_money.text.toString()}"
        cancel.setOnClickListener {
            popupWindow.dismiss()
        }
        tvWjPwd.setOnClickListener {
            val intent = Intent(this, VerificationPhoneActivity::class.java)
            startActivity(intent)
        }
        sure.setOnClickListener {
            if (etPwd.text.toString().length < 6){
                //请输入6位数密码
                return@setOnClickListener
            }
            Log.d("etPwd",etPwd.text.toString())
            request(etPwd.text.toString())
            popupWindow.dismiss()

        }
    }

    private fun requestBankCard(){

        val params = HashMap<String, Any>()
        params["areaNum"] = SpUtils.getString(this, AppConstants.CHANGGUAN_NUM)
        val subscription = Network.getInstance("银行卡", this)
                .bankCard(
                        params,
                        ProgressSubscriber("银行卡", object : SubscriberOnNextListener<Bean<BankCradBean>> {
                            override fun onNext(result: Bean<BankCradBean>) {
                                setting(result.data)
                            }

                            override fun onError(error: String) {


                            }
                        }, this, false)
                )
    }

    private fun setting(bankCradBean: BankCradBean){
        val card = BankInfoUtil(bankCradBean.bankCardNum)
        tv_bank_name.text = card.bankName
        tv_card_type.text = card.cardType
        tv_card_number.text = HideDataUtil.hideCardNo(bankCradBean.bankCardNum)
    }
    private fun request(pwd:String){
        //withdrawDeposit
        val params = HashMap<String, Any>()
        params["areaNum"] = SpUtils.getString(this, AppConstants.CHANGGUAN_NUM)
        params["money"] = et_withdraw_money.text.toString().trim()
        params["paypassword"] = pwd
        params["userNum"] = SpUtils.getString(this, AppConstants.USER_NAME)
        val subscription = Network.getInstance("提现", this)
                .areaWithdraw(
                        params,
                        ProgressSubscriber("提现", object : SubscriberOnNextListener<Bean<Any>> {
                            override fun onNext(result: Bean<Any>) {
                                toComplete()
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext,error,Toast.LENGTH_SHORT)
                                        .show()

                            }
                        }, this, false)
                )
    }

    private fun toComplete(){
        val intent = Intent(this, WithdrawCompleteActivity::class.java)
        startActivity(intent)
        this.finish()

    }
}