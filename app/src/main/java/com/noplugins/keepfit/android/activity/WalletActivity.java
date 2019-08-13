package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.WalletEntity;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;

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

    private double withdraw;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        isShowTitle(false);
        getData();
    }

    @Override
    public void doBusiness(Context mContext) {
        back_btn.setOnClickListener(view -> finish());
        tv_withdraw.setOnClickListener(view -> {
            Intent intentWallet = new Intent(WalletActivity.this, WithdrawActivity.class);
            intentWallet.putExtra("withdraw",withdraw);
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


    private void getData(){
        Map<String, String> params = new HashMap<>();
        params.put("gymAreaNum", "GYM19073183130511");
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        Log.e(TAG, "修改密码的参数：" + json_params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(null, json);

        subscription = Network.getInstance("我的账户", getApplicationContext())

                .searchWallet(requestBody,new ProgressSubscriberNew<>(WalletEntity.class, (wallet, message_id) -> {
                    updateView(wallet);
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> objectBean) {

                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "登录失败：" + error);
                        Toast.makeText(getApplicationContext(), "暂无该用户钱包信息", Toast.LENGTH_SHORT).show();
                    }
                }, this, true));
    }

    private void updateView(WalletEntity walletEntity){
        tv_balance.setText("¥ "+walletEntity.getFinalBalance());
        tv_sum_withdraw.setText("累计提现 ¥"+walletEntity.getFinalTotalWithdraw());
        tv_now_withdraw.setText("当前可提现 ¥"+walletEntity.getFinaTotalCanWithdraw());
        withdraw = walletEntity.getFinaTotalCanWithdraw();
    }
}
