package com.noplugins.keepfit.android.activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.ContentPagerAdapterMy;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.fragment.BaseInformationFragment;
import com.noplugins.keepfit.android.fragment.BusinessInformationFragment;
import com.noplugins.keepfit.android.util.ui.NoScrollViewPager;
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
    private List<Fragment> tabFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_information_check);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        String[] titles = new String[]{"基础资料", "营业资料", "提交审核"};
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
        tabFragments.add(BaseInformationFragment.homeInstance("第二页"));
        ContentPagerAdapterMy contentAdapter = new ContentPagerAdapterMy(getSupportFragmentManager(), tabFragments);
        viewpager_content.setAdapter(contentAdapter);
        viewpager_content.setCurrentItem(0);

    }


}
