package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeTongActivity extends BaseActivity {
    @BindView(R.id.kaiqi_huiyuan_btn)
    LinearLayout kaiqi_huiyuan_btn;
    @BindView(R.id.xieyi_check_btn)
    CheckBox xieyi_check_btn;

    private boolean is_check;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_he_tong);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        xieyi_check_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    is_check = true;
                } else {
                    is_check = false;
                }
            }
        });
        kaiqi_huiyuan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_check) {
                    Intent intent = new Intent(HeTongActivity.this, BuyHuiYuanActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "请先勾选协议", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
