package com.noplugins.keepfit.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.activity.AboutActivity;
import com.noplugins.keepfit.android.activity.ChangGuandetailActivity;
import com.noplugins.keepfit.android.activity.HightLowTImeActivity;
import com.noplugins.keepfit.android.activity.PermissionActivity;
import com.noplugins.keepfit.android.activity.ProductAdviceActivity;
import com.noplugins.keepfit.android.activity.ZhangHaoSafeActivity;
import com.noplugins.keepfit.android.activity.mine.WalletActivity;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineFragment extends ViewPagerFragment implements View.OnClickListener {


    private View view;
    @BindView(R.id.user_item)
    RelativeLayout user_item;
    @BindView(R.id.zhangdan_layout)
    RelativeLayout zhangdan_layout;
    @BindView(R.id.quanxian_layout)
    RelativeLayout quanxian_layout;
    @BindView(R.id.gaodi_layout)
    RelativeLayout gaodi_layout;
    @BindView(R.id.product_fankui_layout)
    RelativeLayout product_fankui_layout;
    @BindView(R.id.about_layout)
    RelativeLayout about_layout;
    @BindView(R.id.zhanghao_layout)
    RelativeLayout zhanghao_layout;

    public static MineFragment myInstance(String title) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.my_page_fragment, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();
        }
        return view;
    }


    private void initView() {
        user_item.setOnClickListener(this);
        zhangdan_layout.setOnClickListener(this);
        quanxian_layout.setOnClickListener(this);
        gaodi_layout.setOnClickListener(this);
        zhanghao_layout.setOnClickListener(this);
        product_fankui_layout.setOnClickListener(this);
        about_layout.setOnClickListener(this);
    }


    @Override
    public void fetchData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_item://场馆详情
                Intent intent5 = new Intent(getActivity(), ChangGuandetailActivity.class);
                startActivity(intent5);

                break;
            case R.id.zhangdan_layout://账单余额
                Intent intentWallet = new Intent(getActivity(), WalletActivity.class);
                startActivity(intentWallet);
                break;
            case R.id.quanxian_layout://权限管理
                Intent intent = new Intent(getActivity(), PermissionActivity.class);
                startActivity(intent);
                break;

            case R.id.gaodi_layout://高低时峰
                Intent intent4 = new Intent(getActivity(), HightLowTImeActivity.class);
                startActivity(intent4);

                break;
            case R.id.zhanghao_layout://账号安全
                Intent intent3 = new Intent(getActivity(), ZhangHaoSafeActivity.class);
                startActivity(intent3);

                break;
            case R.id.product_fankui_layout://产品反馈
                Intent intent2 = new Intent(getActivity(), ProductAdviceActivity.class);
                startActivity(intent2);

                break;
            case R.id.about_layout://关于
                Intent intent1 = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent1);

                break;
        }

    }
}
