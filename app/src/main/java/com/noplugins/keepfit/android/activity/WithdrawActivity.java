package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WithdrawActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    ImageView back_btn;

    @BindView(R.id.tv_all)
    TextView tv_all;
    @BindView(R.id.tv_withdraw_ok)
    TextView tv_withdraw_ok;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_card_name)
    TextView tv_card_name;
    @BindView(R.id.tv_card_type)
    TextView tv_card_type;
    @BindView(R.id.tv_card_number)
    TextView tv_card_number;
    @BindView(R.id.tv_now_money)
    TextView tv_now_money;
    @BindView(R.id.tv_prompt)
    TextView tv_prompt;

    @BindView(R.id.et_withdraw_money)
    EditText et_withdraw_money;

    private double withdraw;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_withdraw);
        ButterKnife.bind(this);
        isShowTitle(false);
        withdraw = getIntent().getDoubleExtra("withdraw",0);
        tv_now_money.setText("当前可提现余额 "+withdraw);
    }

    @Override
    public void doBusiness(Context mContext) {
        back_btn.setOnClickListener(view -> finish());
        tv_all.setOnClickListener(view -> {
            //全部提现
            et_withdraw_money.setText(""+withdraw);
        });
        tv_withdraw_ok.setOnClickListener(view -> {
            //确认提现
            if (Double.parseDouble(et_withdraw_money.getText().toString()) > withdraw){
                tv_prompt.setText("提现金额不能大于可提现金额");
                return;
            }
            Toast.makeText(getApplicationContext(), "该功能暂时无法使用", Toast.LENGTH_SHORT).show();
        });
    }
}
