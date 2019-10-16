package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.wxapi.WXPayEntryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuyHuiYuanActivity extends BaseActivity {
    @BindView(R.id.lin1)
    LinearLayout lin1;
    @BindView(R.id.lin2)
    LinearLayout lin2;
    @BindView(R.id.lin3)
    LinearLayout lin3;
    @BindView(R.id.quanxian1)
    LinearLayout quanxian1;
    @BindView(R.id.quanxian2)
    LinearLayout quanxian2;
    @BindView(R.id.quanxian3)
    LinearLayout quanxian3;
    @BindView(R.id.changuan_name_tv)
    TextView changuan_name_tv;

    private String type;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_buy_hui_yuan);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        lin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "1";
                set_one_view();
            }
        });
        lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "2";

                set_two_view();
            }
        });
        lin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "3";

                set_three_view();
            }
        });

        Intent intent = new Intent(BuyHuiYuanActivity.this, WXPayEntryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("changguan_name", changuan_name_tv.getText().toString());
        bundle.putString("img_url", changuan_name_tv.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);


    }

    private void set_three_view() {
        quanxian1.setVisibility(View.GONE);
        quanxian2.setVisibility(View.GONE);
        quanxian3.setVisibility(View.VISIBLE);


        lin1.setBackgroundResource(R.drawable.lin_bg1);
        lin2.setBackgroundResource(R.drawable.lin_bg1);
        lin3.setBackgroundResource(R.drawable.lin_select_bg);
    }

    private void set_two_view() {
        quanxian1.setVisibility(View.GONE);
        quanxian2.setVisibility(View.VISIBLE);
        quanxian3.setVisibility(View.GONE);

        lin1.setBackgroundResource(R.drawable.lin_bg1);
        lin2.setBackgroundResource(R.drawable.lin_select_bg);
        lin3.setBackgroundResource(R.drawable.lin_bg1);
    }

    private void set_one_view() {
        quanxian1.setVisibility(View.VISIBLE);
        quanxian2.setVisibility(View.GONE);
        quanxian3.setVisibility(View.GONE);

        lin1.setBackgroundResource(R.drawable.lin_select_bg);
        lin2.setBackgroundResource(R.drawable.lin_bg1);
        lin3.setBackgroundResource(R.drawable.lin_bg1);
    }
}
