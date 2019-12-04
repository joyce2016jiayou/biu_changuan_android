package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.noplugins.keepfit.android.KeepFitActivity;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckStatusFailActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    ImageView back_btn;
    @BindView(R.id.again_submit_btn)
    LinearLayout again_submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_check_status_fail);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        //返回
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //重新提交
        again_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckStatusFailActivity.this, InformationCheckActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
