package com.noplugins.keepfit.android.activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.ContentPagerAdapterMy;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.callback.DialogCallBack;
import com.noplugins.keepfit.android.entity.InformationEntity;
import com.noplugins.keepfit.android.fragment.BaseInformationFragment;
import com.noplugins.keepfit.android.fragment.BusinessInformationFragment;
import com.noplugins.keepfit.android.fragment.InformationResultFragement;
import com.noplugins.keepfit.android.util.ui.NoScrollViewPager;
import com.noplugins.keepfit.android.util.ui.PopWindowHelper;
import com.noplugins.keepfit.android.util.ui.StepView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InformationCheckActivity extends BaseActivity {
    @BindView(R.id.sv)
    StepView stepView;
    @BindView(R.id.viewpager_content)
    NoScrollViewPager viewpager_content;
    @BindView(R.id.back_btn_img)
    ImageView back_btn_img;
    @BindView(R.id.top_title_tv)
    TextView top_title_tv;

    public int select_index = 0;
    private List<Fragment> tabFragments = new ArrayList<>();
    public InformationEntity informationEntity;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_information_check);
        ButterKnife.bind(this);
        isShowTitle(false);

        back_btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (select_index == 0) {
                    show_advice_pop();
                } else if (select_index == 1) {
                    top_title_tv.setText("基础资料");
                    viewpager_content.setCurrentItem(select_index - 1);
                    int step = stepView.getCurrentStep();
                    stepView.setCurrentStep(Math.max((step - 1) % stepView.getStepNum(), 0));
                    select_index = select_index - 1;
                }
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        String[] titles = new String[]{"基础资料", "营业资料", "待审核"};
        //设置进度标题
        stepView.setTitles(titles);
        /*//设置前进
        int step = stepView.getCurrentStep();
        //设置进度
        stepView.setCurrentStep(Math.max((step - 1) % stepView.getStepNum(), 0));*/
      /*  //设置后退
      int step = stepView.getCurrentStep();
        //设置进度
        stepView.setCurrentStep((step + 1) % stepView.getStepNum());*/


        //设置视图
        tabFragments.add(BaseInformationFragment.homeInstance("第一页"));
        tabFragments.add(BusinessInformationFragment.homeInstance("第二页"));
        tabFragments.add(InformationResultFragement.homeInstance("第三页"));
        ContentPagerAdapterMy contentAdapter = new ContentPagerAdapterMy(getSupportFragmentManager(), tabFragments);
        viewpager_content.setAdapter(contentAdapter);
        viewpager_content.setCurrentItem(0);
    }

    @Override
    public void onBackPressed() {
        if (select_index == 0) {
            show_advice_pop();
        } else if (select_index == 1) {
            top_title_tv.setText("基础资料");
            viewpager_content.setCurrentItem(select_index - 1);
            int step = stepView.getCurrentStep();
            stepView.setCurrentStep(Math.max((step - 1) % stepView.getStepNum(), 0));
            select_index = select_index - 1;
        }
    }


    private void show_advice_pop() {
        PopWindowHelper.public_tishi_pop(InformationCheckActivity.this, "温馨提示", "是否退出资料提交？", "取消", "确定", new DialogCallBack() {
            @Override
            public void save() {
                finish();
            }

            @Override
            public void cancel() {

            }
        });
    }


}
