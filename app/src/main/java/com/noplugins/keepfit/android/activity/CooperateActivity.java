package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.activity.mine.XieYiActivity;
import com.noplugins.keepfit.android.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CooperateActivity extends BaseActivity {
    @BindView(R.id.yinsizhengce_btn)
    RelativeLayout yinsizhengce_btn;
    @BindView(R.id.changguan_hezuo_btn)
    RelativeLayout changguan_hezuo_btn;
    @BindView(R.id.back_btn)
    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_cooperate);
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
        yinsizhengce_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CooperateActivity.this, XieYiActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });

        changguan_hezuo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CooperateActivity.this, XieYiActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
            }
        });
    }
}
