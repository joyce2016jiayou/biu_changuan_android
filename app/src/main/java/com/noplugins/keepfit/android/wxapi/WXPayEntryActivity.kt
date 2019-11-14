package com.noplugins.keepfit.android.wxapi


import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.alipay.sdk.app.PayTask
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.mine.CgPriceActivity
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.OrderResultBean
import com.noplugins.keepfit.android.bean.WxPayBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.pay.PayResult
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.ProgressUtil
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import kotlinx.android.synthetic.main.activity_order_confirm.*
import java.util.*

class WXPayEntryActivity : BaseActivity(), IWXAPIEventHandler {
    private var type = -1

    private var orderNum: String = ""
    private var price = ""

    private var aliPayInfo = ""
    private var wxPayInfo = ""
    private var dialogType = -1
    private var cgName = ""
    private var privcetType = ""
    private var logo = ""
    private val progress_upload = ProgressUtil()
    override fun initBundle(parms: Bundle?) {
        /*
         bundle.putString("order_number", result.getData());//订单编号
         bundle.putString("type", select_order_type);//支付类型
         bundle.putString("money", select_order_money);//钱
         bundle.putString("changguan_name", changuan_name_tv.getText().toString());//场馆名字
         bundle.putString("img_url", changuan_name_tv.getText().toString());//图片地址
         */

    }
    private var api: IWXAPI? = null
    override fun initView() {
        setContentView(R.layout.activity_order_confirm)
        if (intent!=null){
            orderNum = intent.getStringExtra("order_number")
            price = intent.getStringExtra("money")
            cgName = intent.getStringExtra("changguan_name")
            privcetType = intent.getStringExtra("type")
            Log.d("price","privcetType:"+privcetType)
            logo = intent.getStringExtra("img_url")

        }
        tv_cg_name.text = cgName

        when(privcetType){
            "1" -> {
                //2999
                tv_vip.text = "终身会员"
            }
            "2" -> {
                //3999
                tv_vip.text = "超值终身会员"
            }
            "3" -> {
                //6999
                tv_vip.text = "豪华终身会员"
            }
        }
        Glide.with(this)
                .load(logo)
                .placeholder(R.drawable.logo_gray)
                .into(iv_logo)
        tv_vip_price.text = "¥$price"

        api = WXAPIFactory.createWXAPI(this, "wx5460a045edfffc8c")
        api!!.registerApp("wx5460a045edfffc8c")
        api!!.handleIntent(intent, this)
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.setOnClickListener {
            //
            finish()
        }
        cb_product_suggest.setOnClickListener {
            if (cb_product_suggest.isChecked()) {
                type = 1
                cb_fault_feedback.setChecked(false)
                cb_other.setChecked(false)
            } else {
                type = 0
            }
        }
        cb_fault_feedback.setOnClickListener {
            if (cb_fault_feedback.isChecked()) {
                type = 2
                cb_product_suggest.setChecked(false)
                cb_other.setChecked(false)
            } else {
                type = 0
            }
        }
        cb_other.setOnClickListener {
            if (cb_other.isChecked()) {
                type = 3
                cb_fault_feedback.setChecked(false)
                cb_product_suggest.setChecked(false)
            } else {
                type = 0
            }
        }

        tv_pay.setOnClickListener {
            if (type == 2) {
                requestAliPayInfo()

            }
            if (type == 3) {
                testWxRequest()
            }

        }


    }




    private val SDK_PAY_FLAG = 1
    private val SDK_PAY_WECHAT = 2

    companion object {
        private val TAG = "MicroMsg.SDKSample.WXPayEntryActivity"
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        api!!.handleIntent(intent, this)
    }

    override fun onReq(req: BaseReq) {}


    @SuppressLint("LongLogTag")
    override fun onResp(resp: BaseResp) {
//        Log.d(TAG, "onPayFinish, errCode = " + resp.errStr)

        val gson = Gson()
        val str = gson.toJson(resp)
        Log.d("GSON", "onPayFinish:$str")


        if (resp.type == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            val builder = AlertDialog.Builder(this)
//            builder.setTitle("为啥")
//            builder.setMessage(getString(R.string.pay_result_callback_msg, resp.errCode.toString()))
//            builder.show()

            when(resp.errCode){
//            progress_upload.showProgressDialog(applicationContext, "")
                0 -> {
                    //
                    Log.d("GSSSSSSSS","jinlai le")
                    twoYanzhen()
                }

                -2 -> {
                    Toast.makeText(applicationContext,"支付失败",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    /**
     * 支付结果反馈
     */
    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                SDK_PAY_FLAG -> {
                    Log.d("tag", "msg:" + msg.obj)
                    val payResult = PayResult(msg.obj as String)
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    val resultInfo = payResult.getResult()// 同步返回需要验证的信息
                    val resultStatus = payResult.getResultStatus().toString()
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        progress_upload.showProgressDialog(applicationContext, "载入中...")
                        twoYanzhen()
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        toPayError()
                        Toast.makeText(applicationContext,"支付失败",Toast.LENGTH_SHORT).show()
                    }
                }
                SDK_PAY_WECHAT -> {
//                    val req = msg.obj as PayReq
//                    api!!.sendReq(req)
                }
            }
        }
    }

    /**
     * 支付宝支付
     */
    private fun zhifubaoPay() {
        val authRunnable = Runnable {
            // 构造AuthTask 对象
            val payTask = PayTask(this@WXPayEntryActivity)
            // 调用支付接口
            val result = payTask.pay(aliPayInfo, true)

            val msg = Message()
            msg.what = SDK_PAY_FLAG
            msg.obj = result
            mHandler.sendMessage(msg)
        }

        // 必须异步调用
        val authThread = Thread(authRunnable)
        authThread.start()

    }

    /**
     * 微信支付
     */
    private fun weChatPay(wxPayBean: WxPayBean) {
//        api = WXAPIFactory.createWXAPI(this,"wx5460a045edfffc8c")
//        api!!.registerApp("wx5460a045edfffc8c")

        val payRunnable = Runnable {
            val req = PayReq()
            req.appId = wxPayBean.appid
            req.partnerId = wxPayBean.mch_id
            req.prepayId = wxPayBean.prepay_id
            req.nonceStr = wxPayBean.noncestr
            req.timeStamp = wxPayBean.timestamp
            req.packageValue = "Sign=WXPay"
            req.sign = wxPayBean.sign

            api!!.sendReq(req)//发送调起微信的请求
        }
        // 必须异步调用
        val payThread = Thread(payRunnable)
        payThread.start()
    }


    private fun testWxRequest() {
        val params = HashMap<String, Any>()
        params["ordNum"] = orderNum
        params["payType"] = 2
        //memberOrderPay
        val subscription = Network.getInstance("微信支付", this)
                .memberOrderPayWx(params,
                        ProgressSubscriber("微信支付", object : SubscriberOnNextListener<Bean<WxPayBean>> {
                            override fun onNext(code: Bean<WxPayBean>) {
                                weChatPay(code.data)
                            }

                            override fun onError(error: String) {

                            }
                        }, this, false))

    }

    private fun requestAliPayInfo() {
        val params = HashMap<String, Any>()
        params["ordNum"] = orderNum
        params["payType"] = 1
        //memberOrderPay
        val subscription = Network.getInstance("支付宝支付", this)
                .memberOrderPay(params,
                        ProgressSubscriber("支付宝支付", object : SubscriberOnNextListener<Bean<String>> {
                            override fun onNext(code: Bean<String>) {
                                aliPayInfo = code.data
                                zhifubaoPay()
                            }

                            override fun onError(error: String) {

                            }
                        }, this, false))

    }


    private fun showAlert(ctx: Context, info: String) {
        showAlert(ctx, info, null)
    }

    private fun showAlert(ctx: Context, info: String, onDismiss: DialogInterface.OnDismissListener?) {
        androidx.appcompat.app.AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton("确认", null)
                .setOnDismissListener(onDismiss)
                .show()
    }

    private fun showToast(ctx: Context, msg: String) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show()
    }

    private fun bundleToString(bundle: Bundle?): String {
        if (bundle == null) {
            return "null"
        }
        val sb = StringBuilder()
        for (key in bundle.keySet()) {
            sb.append(key).append("=>").append(bundle.get(key)).append("\n")
        }
        return sb.toString()
    }


    override fun onBackPressed() {
//        super.onBackPressed()
    }


    /**
     * 二次验证
     */
    private fun twoYanzhen() {
        Thread.sleep(2000)
        val params = HashMap<String, Any>()
        params["orderNum"] = orderNum
        subscription = Network.getInstance("支付二次验证", this)
            .getPayResult(
                    params,
                ProgressSubscriber<OrderResultBean>(
                    "支付二次验证",
                    object : SubscriberOnNextListener<Bean<OrderResultBean>> {
                        override fun onNext(result: Bean<OrderResultBean>) {
                            progress_upload.dismissProgressDialog()
                            if (result.data.payResult == 1) {
//                                toPayOK()
                                var sf = ""
                                when(privcetType){
                                    "1"-> {
                                        sf = "2999"
                                        SpUtils.putString(applicationContext,AppConstants.USER_DENGJI,"2999")
                                    }
                                    "2"-> {
                                        sf = "3999"
                                        SpUtils.putString(applicationContext,AppConstants.USER_DENGJI,"3999")
                                    }
                                    "3"-> {
                                        sf = "6999"
                                        SpUtils.putString(applicationContext,AppConstants.USER_DENGJI,"6999")
                                    }
                                }

                                Toast.makeText(applicationContext,"支付成功",Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@WXPayEntryActivity, CgPriceActivity::class.java)
                                val bundle = Bundle()
                                bundle.putString("form", "pay")
                                intent.putExtras(bundle)
                                startActivity(intent)
                                finish()
                            } else {
//                                toPayError()
                                progress_upload.dismissProgressDialog()
                                Toast.makeText(applicationContext,"支付失败",Toast.LENGTH_SHORT)
                                        .show()
                            }

                        }

                        override fun onError(error: String) {
                            Log.e(TAG, "支付二次验证：" + error)
                            Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
                        }
                    },
                    this,
                    false
                )
            )
    }

}