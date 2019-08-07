package com.noplugins.keepfit.android.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.TabItemAdapter;
import com.noplugins.keepfit.android.entity.PieBean;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;
import com.openxu.cview.chart.bean.ChartLable;
import com.openxu.cview.chart.piechart.PieChartLayout;
import com.openxu.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 发现页
 */
public class StatisticsFragment extends ViewPagerFragment {
    private View view;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.tv_user)
    TextView tv_user;
    @BindView(R.id.tv_product)
    TextView tv_product;

    private ArrayList<Fragment> mFragments;


    public static StatisticsFragment newInstance(String title) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.discovers_fragment, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();
        }


        return view;
    }

    private void initView() {
        mFragments = new ArrayList<>();
        mFragments.add(StatisticsUserFragment.newInstance("用户统计"));
        mFragments.add(StatisticsProductFragment.newInstance("产品统计"));

        tv_product.setOnClickListener(onClickListener);
        tv_user.setOnClickListener(onClickListener);

        TabItemAdapter myAdapter = new TabItemAdapter(getActivity().getSupportFragmentManager(), mFragments);// 初始化adapter
        view_pager.setAdapter(myAdapter); // 设置adapter
        setTabTextColorAndImageView(0);// 更改text的颜色还有图片

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPs) {
            }

            @Override
            public void onPageSelected(int position) {
                setTabTextColorAndImageView(position);// 更改text的颜色还有图片
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTabTextColorAndImageView(int position) {
        switch (position) {
            case 0:
                tv_user.setText("用户统计");
                tv_user.setTextColor(getActivity().getResources().getColor(R.color.contents_text));
                tv_user.setTextSize(32);
                tv_product.setText("产品");
                tv_product.setTextColor(getActivity().getResources().getColor(R.color.color_ACACAC));
                tv_product.setTextSize(18);
                break;
            case 1:
                tv_product.setText("产品统计");
                tv_product.setTextColor(getActivity().getResources().getColor(R.color.contents_text));
                tv_product.setTextSize(32);
                tv_user.setText("用户");
                tv_user.setTextColor(getActivity().getResources().getColor(R.color.color_ACACAC));
                tv_user.setTextSize(18);
                break;
        }
    }


    @Override
    public void fetchData() {

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_user:
                    view_pager.setCurrentItem(0);
                    break;
                case R.id.tv_product:
                    view_pager.setCurrentItem(1);
                    break;
            }
        }
    };
}
