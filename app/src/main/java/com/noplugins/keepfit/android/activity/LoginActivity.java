package com.noplugins.keepfit.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.RegisterEntity;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.data.StringsHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.entity.Contacts;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.permission.PermissionActivity;
import com.noplugins.keepfit.android.util.ui.NoScrollViewPager;
import com.umeng.socialize.utils.CommonUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.xieyi_check_btn)
    CheckBox xieyi_check_btn;
    @BindView(R.id.checkbox_layout)
    LinearLayout checkbox_layout;
    @BindView(R.id.register_tv)
    TextView register_tv;
    @BindView(R.id.forget_password_btn)
    TextView forget_password_btn;
    @BindView(R.id.edit_phone_number)
    EditText edit_phone_number;
    @BindView(R.id.edit_password)
    EditText edit_password;
    @BindView(R.id.login_btn)
    LinearLayout login_btn;

    protected final String TAG = this.getClass().getSimpleName();//是否输出日志信息


    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_login);
        ButterKnife.bind(this);
        isShowTitle(false);
        StringsHelper.setEditTextHintSize(edit_phone_number, "请输入手机号", 15);
        StringsHelper.setEditTextHintSize(edit_password, "请输入密码", 15);
        edit_phone_number.addTextChangedListener(phone_number_jiaoyan);
        edit_phone_number.setKeyListener(DigitsKeyListener.getInstance("0123456789"));//设置输入数字

    }

    @Override
    public void doBusiness(Context mContext) {
        xieyi_check_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean is_check) {
                if (is_check) {
                    Log.e("dsdd", "选中了");
                } else {
                    Log.e("dsdd", "没选中");

                }
            }
        });
        checkbox_layout.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //忘记密码
        forget_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edit_phone_number.getText())) {
                    Toast.makeText(getApplicationContext(), "电话号码不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    Login();
                }

            }
        });

    }

    private void Login() {
        Map<String, String> params = new HashMap<>();
        params.put("password", edit_password.getText().toString());
        params.put("phone", edit_phone_number.getText().toString());
        Gson gson = new Gson();
        String json_params=gson.toJson(params);
        Log.e(TAG, "登录参数：" + json_params);
        String json= new Gson().toJson(params);//要传递的json
        RequestBody requestBody=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);

        subscription = Network.getInstance("登录", getApplicationContext())
                .login(requestBody, new ProgressSubscriberNew<>(String.class, new GsonSubscriberOnNextListener<String>() {
                    @Override
                    public void on_post_entity(String o,String s) {
                        Log.e(TAG, "登录成功：" + s);
                        if ("".equals(SharedPreferencesHelper.get(getApplicationContext(), "login_token", ""))) {
                            SharedPreferencesHelper.put(getApplicationContext(), "login_token", o);
                            SharedPreferencesHelper.put(getApplicationContext(), "phone_number", edit_phone_number.getText().toString());
                        } else {
                            SharedPreferencesHelper.remove(getApplicationContext(), "login_token");
                            SharedPreferencesHelper.put(getApplicationContext(), "login_token", o);
                            SharedPreferencesHelper.put(getApplicationContext(), "phone_number", edit_phone_number.getText().toString());
                        }
                        Intent intent = new Intent(LoginActivity.this, UserPermissionSelectActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {
                    }
                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "登录失败：" + error);
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                    }
                }, this, true));

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
                if (!StringsHelper.isMobileNO(edit_phone_number.getText().toString())) {//校验手机格式
                    edit_phone_number.setError("手机号码格式不正确！");
                }
            }
        }
    };

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean is_check) {
            if (is_check) {
                Log.e(TAG, "选中了");
            } else {
                Log.e(TAG, "没选中");

            }
        }
    };


}
