package com.noplugins.keepfit.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.callback.DialogCallBack;
import com.noplugins.keepfit.android.util.ActivityCollectorUtil;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.ui.PopWindowHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhangHaoSafeActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    ImageView back_btn;
    @BindView(R.id.exit_btn)
    LinearLayout exit_btn;
    @BindView(R.id.jujue_btn)
    LinearLayout jujue_btn;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_zhang_hao_safe);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //修改密码
        jujue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ZhangHaoSafeActivity.this,UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });

        //退出登录
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopWindowHelper.public_tishi_pop(ZhangHaoSafeActivity.this, "提示", "是否退出登录？", "取消", "确定", new DialogCallBack() {
                    @Override
                    public void save() {

                    }

                    @Override
                    public void cancel() {
                        toLogin();
                    }
                });
            }
        });


    }

    private void toLogin(){
        Intent intent = new Intent(ZhangHaoSafeActivity.this, LoginActivity.class);
        SharedPreferencesHelper.put(getApplicationContext(), "login_token", "");
        SharedPreferencesHelper.put(getApplicationContext(), "phone_number", "");
        SharedPreferencesHelper.put(getApplicationContext(), "changguan_number", "");
        startActivity(intent);
        ActivityCollectorUtil.finishAllActivity();

    }
}
