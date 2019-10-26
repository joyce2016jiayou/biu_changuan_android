package com.noplugins.keepfit.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.RegisterEntity;
import com.noplugins.keepfit.android.entity.TeacherEntity;
import com.noplugins.keepfit.android.util.data.StringsHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.entity.Token;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.screen.ScreenUtilsHelper;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.login_tv)
    TextView login_tv;
    @BindView(R.id.yanzhengma_btn)
    TextView yanzhengma_btn;
    @BindView(R.id.edit_phone_number)
    EditText edit_phone_number;
    @BindView(R.id.edit_yanzhengma)
    EditText edit_yanzhengma;
    @BindView(R.id.edit_new_password)
    EditText edit_new_password;
    @BindView(R.id.edit_sure_password)
    EditText edit_sure_password;
    @BindView(R.id.clear_password_btn)
    ImageView clear_password_btn;
    @BindView(R.id.register_tv)
    LinearLayout register_tv;
    @BindView(R.id.check_layout)
    LinearLayout check_layout;
    @BindView(R.id.xieyi_check_btn)
    CheckBox xieyi_check_btn;

    private boolean is_enable_register = false;
    private String message_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_register2);
        ButterKnife.bind(this);
        isShowTitle(false);
        StringsHelper.setEditTextHintSize(edit_phone_number, "请输入手机号", 15);
        StringsHelper.setEditTextHintSize(edit_yanzhengma, "请输入验证码", 15);
        StringsHelper.setEditTextHintSize(edit_new_password, "请输入新密码", 15);
        StringsHelper.setEditTextHintSize(edit_sure_password, "请再次确认密码", 15);
        edit_phone_number.addTextChangedListener(phone_number_jiaoyan);
        edit_phone_number.setKeyListener(DigitsKeyListener.getInstance("0123456789"));//设置输入数字

        clear_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_yanzhengma.setText("");
            }
        });
    }


    @Override
    public void doBusiness(Context mContext) {
        xieyi_check_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean is_check) {
                if (is_check) {//可以注册
                    is_enable_register = true;
                } else {//不可以注册
                    is_enable_register = false;
                }
            }
        });
        login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        yanzhengma_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(edit_phone_number.getText())) {
                    Toast.makeText(getApplicationContext(), "电话号码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!StringsHelper.isMobileOne(edit_phone_number.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "电话号码格式不正确！", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    yanzhengma_btn.setEnabled(false);//设置不可点击，等待60秒过后可以点击
                    timer.start();
                    //获取验证码接口
                    Get_YanZhengMa();
                }
            }
        });
        check_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xieyi_check_btn.isChecked()) {
                    xieyi_check_btn.setChecked(false);
                } else {
                    xieyi_check_btn.setChecked(true);
                }
            }
        });

        //注册
        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!is_enable_register) {
                    Toast.makeText(getApplicationContext(), "请先勾选用户协议！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(edit_phone_number.getText())) {
                    Toast.makeText(getApplicationContext(), "电话号码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!StringsHelper.isMobileOne(edit_phone_number.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "电话号码格式不正确！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(edit_yanzhengma.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "验证码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(edit_new_password.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "新密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(edit_sure_password.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "确认密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!edit_new_password.getText().toString().equals(edit_sure_password.getText().toString())) {
                    edit_sure_password.setError("两次输入不一致");
                    return;
                } else {
                    //验证验证码
                    Check_YanZhengMa();

                }
            }


        });

        edit_yanzhengma.addTextChangedListener(yanzhengma);


    }

    TextWatcher yanzhengma = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0) {
                clear_password_btn.setVisibility(View.VISIBLE);
            } else {
                clear_password_btn.setVisibility(View.INVISIBLE);
            }
        }
    };

    private void Check_YanZhengMa() {
//        Map<String, Object> params = new HashMap<>();
//        params.put("code", edit_yanzhengma.getText().toString());
//        params.put("messageid", message_id);
//        params.put("phone",edit_phone_number.getText().toString());
//        subscription = Network.getInstance("验证验证码", this)
//                .check_yanzhengma(params,
//                        new ProgressSubscriber<>("验证验证码", new SubscriberOnNextListener<Bean<String>>() {
//                            @Override
//                            public void onNext(Bean<String> result) {
//                                register();
//
//                            }
//
//                            @Override
//                            public void onError(String error) {
//                                Toast.makeText(getApplicationContext(), "验证码输入不正确！", Toast.LENGTH_SHORT).show();
//
//                            }
//                        }, this, false));
    }

    private void register() {
        Map<String, String> params = new HashMap<>();
        params.put("password", edit_sure_password.getText().toString());
        params.put("phone", edit_phone_number.getText().toString());
        subscription = Network.getInstance("注册", getApplicationContext())
                .register(params,
                        new ProgressSubscriberNew<>(String.class, new GsonSubscriberOnNextListener<String>() {
                            @Override
                            public void on_post_entity(String code, String Message_id) {
                                Log.e(TAG, "注册成功：");
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, new SubscriberOnNextListener<Bean<Object>>() {
                            @Override
                            public void onNext(Bean<Object> result) {

                            }

                            @Override
                            public void onError(String error) {
                                //Logger.e(TAG, "注册报错：" + error);
                                Log.e(TAG, "注册报错：" + error);
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                            }
                        }, this, true));

    }


    private void Get_YanZhengMa() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", edit_phone_number.getText().toString());
//        subscription = Network.getInstance("接收验证码", getApplicationContext())
//                .get_yanzhengma(params,
//                        new ProgressSubscriberNew<>(String.class, new GsonSubscriberOnNextListener<String>() {
//                            @Override
//                            public void on_post_entity(String code, String get_message_id) {
//                                message_id = get_message_id;
//                                Log.e(TAG, "接收验证码成功get_message_id：" + message_id);
//                                Log.e(TAG, "接收验证码成功：" + message_id);
//
//                            }
//                        }, new SubscriberOnNextListener<Bean<Object>>() {
//                            @Override
//                            public void onNext(Bean<Object> result) {
//
//                            }
//
//                            @Override
//                            public void onError(String error) {
//                                Logger.e(TAG, "接收验证码报错：" + error);
//                                Toast.makeText(getApplicationContext(), "接收验证码失败！", Toast.LENGTH_SHORT).show();
//                            }
//                        }, this, true));

    }


    TextWatcher phone_number_jiaoyan = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() == 11) {
                if (!StringsHelper.isMobileOne(edit_phone_number.getText().toString())) {//校验手机格式
                    edit_phone_number.setError("手机号码格式不正确！");
                }
            }
        }
    };


    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            yanzhengma_btn.setTextColor(Color.parseColor("#292C31"));
            yanzhengma_btn.setText("已发送(" + millisUntilFinished / 1000 + ")");

        }

        @Override
        public void onFinish() {
            yanzhengma_btn.setTextColor(Color.parseColor("#FFBA02"));
            yanzhengma_btn.setText("重新获取");
            yanzhengma_btn.setEnabled(true);
        }
    };


}
