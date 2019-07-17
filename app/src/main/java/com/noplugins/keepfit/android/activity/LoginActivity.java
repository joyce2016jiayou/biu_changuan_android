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

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.util.data.StringsHelper;
import com.noplugins.keepfit.android.util.ui.NoScrollViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    protected final String TAG = this.getClass().getSimpleName();//是否输出日志信息


    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_login);
        ButterKnife.bind(this);
        isShowTitle(false);
        StringsHelper.setEditTextHintSize(edit_phone_number,"请输入手机号",15);
        StringsHelper.setEditTextHintSize(edit_password,"请输入密码",15);
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
            if(editable.length()==11){
                if(!StringsHelper.isMobileNO(edit_phone_number.getText().toString())){//校验手机格式
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
