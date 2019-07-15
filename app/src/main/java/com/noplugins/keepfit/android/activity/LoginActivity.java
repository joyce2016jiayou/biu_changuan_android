package com.noplugins.keepfit.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
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


    protected final String TAG = this.getClass().getSimpleName();//是否输出日志信息



    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_login);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        xieyi_check_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean is_check) {
                if(is_check){
                    Log.e("dsdd","选中了");
                }else{
                    Log.e("dsdd","没选中");

                }
            }
        });
        checkbox_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(xieyi_check_btn.isChecked()){
                   xieyi_check_btn.setChecked(false);
               }else{
                   xieyi_check_btn.setChecked(true);
               }
            }
        });

        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean is_check) {
            if(is_check){
                Log.e(TAG,"选中了");
            }else{
                Log.e(TAG,"没选中");

            }
        }
    };

}
