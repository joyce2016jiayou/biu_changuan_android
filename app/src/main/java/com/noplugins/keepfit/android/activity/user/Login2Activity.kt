package com.noplugins.keepfit.android.activity.user

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.text.*
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import butterknife.ButterKnife
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.mine.CgPriceActivity
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.data.StringsHelper
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow
import com.noplugins.keepfit.android.wxapi.WXPayEntryActivity
import kotlinx.android.synthetic.main.activity_login2.*
import java.util.HashMap

class Login2Activity : BaseActivity() {

    private var is_yanzhengma_logon = true
    private var message_id = ""
    private var is_check_fuwu = false

    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentLayout(R.layout.activity_login2)
        ButterKnife.bind(this)
        isShowTitle(false)
    }

    override fun doBusiness(mContext: Context) {
        yanzhengma_tv.setOnClickListener(View.OnClickListener {
            if (yanzhengma_tv.text.toString() == "密码登录") {
                Log.e("登录方式", "密码登录")
                is_yanzhengma_logon = false
                yanzhengma_tv.text = "验证码登录"
                edit_password.inputType = InputType.TYPE_CLASS_TEXT
                edit_password.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(18)) //最大输入长度

                val s = SpannableString("请输入密码")//这里输入自己想要的提示文字
                edit_password.hint = s
                img_password.setImageResource(R.drawable.password_icon)
                tv_send.visibility = View.GONE

                forget_password_btn.visibility = View.VISIBLE


            } else {
                Log.e("登录方式", "验证码登录")

                is_yanzhengma_logon = true
                yanzhengma_tv.text = "密码登录"
                edit_password.inputType = InputType.TYPE_CLASS_NUMBER
                edit_password.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(6)) //最大输入长度

                val s = SpannableString("请输入验证码")//这里输入自己想要的提示文字
                edit_password.hint = s
                img_password.setImageResource(R.drawable.yanzhengma_icon)
                tv_send.visibility = View.VISIBLE

                forget_password_btn.visibility = View.GONE


            }
        })
        //发送验证码
        tv_send.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(edit_phone_number.text)) {
                Toast.makeText(applicationContext, "电话号码不能为空！", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            } else if (!StringsHelper.isMobileOne(edit_phone_number.getText().toString())) {
                Toast.makeText(applicationContext, "电话号码格式不正确！", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            } else {
                tv_send.isEnabled = false//设置不可点击，等待60秒过后可以点击
                timer.start()
                //获取验证码接口
                Get_YanZhengMa()
            }
        })
        edit_phone_number.addTextChangedListener(textWatcher)

        iv_delete_edit.setOnClickListener(View.OnClickListener { edit_phone_number.setText("") })
        tv_user_protocol.setOnClickListener(View.OnClickListener { xieyi_pop() })
        login_btn.setBtnOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                if (!is_check_fuwu) {
                    Toast.makeText(this@Login2Activity, "请先勾选用户协议！", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(edit_phone_number.getText())) {
                    Toast.makeText(applicationContext, "电话号码不能为空！", Toast.LENGTH_SHORT).show()
                    return
                } else if (!StringsHelper.isMobileOne(edit_phone_number.getText().toString())) {
                    Toast.makeText(applicationContext, "电话号码格式不正确！", Toast.LENGTH_SHORT).show()
                    return
                } else if (TextUtils.isEmpty(edit_password.getText())) {
                    if (is_yanzhengma_logon) {
                        Toast.makeText(applicationContext, "验证码不能为空！", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, "密码不能为空！", Toast.LENGTH_SHORT).show()
                    }
                    return
                }
                run {
                    login_btn.startLoading()
                    if (is_yanzhengma_logon) {//如果是验证码登录，则让它设置密码
                        yanzheng_yanzhengma()
                    } else {
                        password_login()
                    }
                }


            }
        })
        xieyi_check_btn.setOnCheckedChangeListener(onCheckedChangeListener)

        //忘记密码
        forget_password_btn.setOnClickListener(View.OnClickListener {

        })
    }

    private fun yanzheng_yanzhengma() {
        val params = HashMap<String, Any>()
        params["messageId"] = message_id
        params["code"] = edit_password.text.toString()
        params["phone"] = edit_phone_number.text.toString()
//        subscription = Network.getInstance("验证验证码和登录", this)
//                .yanzheng_yanzhengma(params,
//                        ProgressSubscriber("验证验证码和登录", object : SubscriberOnNextListener<Bean<YanZhengMaBean>>() {
//                            override fun onNext(result: Bean<YanZhengMaBean>) {
//                                login_btn.loadingComplete()
//                                if (result.getData().getHavePassword() === 0) {//没有设置过密码
//                                    val intent = Intent(this@Login2Activity, SetPasswordActivity::class.java)
//                                    startActivity(intent)
//                                } else {//设置过密码
//                                    val intent = Intent(this@Login2Activity, SelectRoleActivity::class.java)
//                                    startActivity(intent)
//                                }
//                                save_resource(result.getData().getToken(),
//                                        result.getData().getUserNum(),
//                                        result.getData().getTeacherType(),
//                                        result.getData().getUserNum())
//                            }
//
//                            override fun onError(error: String) {
//                                login_btn.loadingComplete()
//
//                            }
//                        }, this, false))
        val intent = Intent(this@Login2Activity, SetPasswordActivity::class.java)
        startActivity(intent)
    }

    private fun password_login() {
        val params = HashMap<String, Any>()
        params["phone"] = edit_phone_number.text.toString()
        params["password"] = edit_password.text.toString()

//        subscription = Network.getInstance("密码登录", this)
//                .password_login(params,
//                        ProgressSubscriber("密码登录", object : SubscriberOnNextListener<Bean<LoginBean>>() {
//                            override fun onNext(result: Bean<LoginBean>) {
//                                login_btn.loadingComplete()
//
//                                save_resource(result.getData().getToken(),
//                                        result.getData().getUserNum(),
//                                        result.getData().getTeacherType(),
//                                        result.getData().getUserNum())
//                                if (null != SpUtils.getString(applicationContext, AppConstants.TEACHER_TYPE)) {
//                                    if (SpUtils.getString(applicationContext, AppConstants.TEACHER_TYPE).length > 0) {//已经审核过了
//                                        val intent = Intent(this@Login2Activity, KeepFitActivity::class.java)
//                                        startActivity(intent)
//                                    } else {//未审核
//                                        val intent = Intent(this@Login2Activity, SelectRoleActivity::class.java)
//                                        startActivity(intent)
//                                    }
//                                }
//                            }
//
//                            override fun onError(error: String) {
//                                login_btn.loadingComplete()
//
//                            }
//                        }, this, false))
    }

    private fun Get_YanZhengMa() {
        val params = HashMap<String, String>()
        params["phone"] = edit_phone_number.text.toString()
        val subscription = Network.getInstance("获取验证码", this)
                .get_yanzhengma(params,
                        ProgressSubscriber("获取验证码", object : SubscriberOnNextListener<Bean<String>> {
                            override fun onNext(result: Bean<String>) {
                                message_id = result.data
                            }

                            override fun onError(error: String) {

                            }
                        }, this, false))
    }

    private fun xieyi_pop() {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.xieyi_pop_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.main_menu_animstyle)
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT)
                .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(tv_user_protocol)

        /**设置逻辑 */
        val view = popupWindow.contentView
        val agree_btn = view.findViewById<TextView>(R.id.agree_btn)
        agree_btn.setOnClickListener(View.OnClickListener {
            popupWindow.dismiss()
            is_check_fuwu = true
            xieyi_check_btn.isChecked = true
        })
        val no_agree_btn = view.findViewById<TextView>(R.id.no_agree_btn)
        no_agree_btn.setOnClickListener(View.OnClickListener { popupWindow.dismiss() })
    }

    private fun save_resource(token: String, user_number: String, teacher_type: String, teacher_number: String) {
        SpUtils.putString(applicationContext, AppConstants.TOKEN, token)
        SpUtils.putString(applicationContext, AppConstants.USER_NAME, user_number)
        SpUtils.putString(applicationContext, AppConstants.PHONE, edit_phone_number.getText().toString())
        SpUtils.putString(applicationContext, AppConstants.TEACHER_TYPE, teacher_type)
        SpUtils.putString(applicationContext, AppConstants.SELECT_TEACHER_NUMBER, teacher_number)
    }

    internal var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun afterTextChanged(editable: Editable) {
            if (editable.isNotEmpty()) {
                iv_delete_edit.visibility = View.VISIBLE
            } else {
                iv_delete_edit.visibility = View.GONE

            }
        }
    }

    internal var timer: CountDownTimer = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            tv_send.setTextColor(Color.parseColor("#7B7B7B"))
            tv_send.text = "重新发送(" + millisUntilFinished / 1000 + "s)"

        }

        override fun onFinish() {
            tv_send.setTextColor(Color.parseColor("#292C31"))
            tv_send.text = "重新发送"
            tv_send.isEnabled = true
        }
    }


    internal var onCheckedChangeListener: CompoundButton.OnCheckedChangeListener = CompoundButton.OnCheckedChangeListener { compoundButton, is_check ->
        if (is_check) {
            Log.e(TAG, "选中了")
            is_check_fuwu = true
        } else {
            Log.e(TAG, "没选中")
            is_check_fuwu = false
        }
    }
}
