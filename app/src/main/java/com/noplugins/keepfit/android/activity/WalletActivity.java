package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletActivity extends BaseActivity {

    @BindView(R.id.back_btn)
    ImageView back_btn;

    @BindView(R.id.tv_withdraw)
    TextView tv_withdraw;
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.tv_sum_withdraw)
    TextView tv_sum_withdraw;
    @BindView(R.id.tv_now_withdraw)
    TextView tv_now_withdraw;
    @BindView(R.id.rl_money_details)
    RelativeLayout rl_money_details;
    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        back_btn.setOnClickListener(view -> finish());
        tv_withdraw.setOnClickListener(view -> {
            Intent intentWallet = new Intent(WalletActivity.this, WithdrawActivity.class);
            startActivity(intentWallet);
        });
        rl_money_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentWallet = new Intent(WalletActivity.this, BillActivity.class);
                startActivity(intentWallet);
            }
        });
    }
}
