package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noplugins.keepfit.android.KeepFitActivity;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.SplashActivity;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.CheckEntity;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.wxapi.WXPayEntryActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

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
    @BindView(R.id.buy_btn)
    LinearLayout buy_btn;

    private String select_order_money = "";
    private String select_order_type = "";


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
        select_order_money = "3999";
        select_order_type = "2";
        lin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_order_money = "2999";
                select_order_type = "1";
                set_one_view();
            }
        });
        lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_order_money = "3999";
                select_order_type = "2";
                set_two_view();
            }
        });
        lin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_order_money = "6999";
                select_order_type = "3";
                set_three_view();
            }
        });

        buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1终身会员2超值终身会员3豪华终身会员0默认

                get_order_number();//生成订单
            }
        });
    }

    private void get_order_number() {
        Map<String, Object> params = new HashMap<>();
        params.put("gymAreaNum", "1111111");
        params.put("orderMoney", select_order_money);
        params.put("orderType", select_order_type);
        Subscription subscription = Network.getInstance("生成订单", this)
                .get_order(params,
                        new ProgressSubscriber<>("生成订单", new SubscriberOnNextListener<Bean<String>>() {
                            @Override
                            public void onNext(Bean<String> result) {
                                Intent intent = new Intent(BuyHuiYuanActivity.this, WXPayEntryActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("order_number", result.getData());//订单编号
                                bundle.putString("type", select_order_type);//支付类型
                                bundle.putString("money", select_order_money);//钱
                                bundle.putString("changguan_name", changuan_name_tv.getText().toString());//场馆名字
                                bundle.putString("img_url", changuan_name_tv.getText().toString());//图片地址
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));
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