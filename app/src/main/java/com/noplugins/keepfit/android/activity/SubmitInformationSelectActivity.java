package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubmitInformationSelectActivity extends BaseActivity {
    @BindView(R.id.submit_btn)
    LinearLayout submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_submit_information_select);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubmitInformationSelectActivity.this, InformationCheckActivity.class);
                startActivity(intent);
            }
        });
    }
}
