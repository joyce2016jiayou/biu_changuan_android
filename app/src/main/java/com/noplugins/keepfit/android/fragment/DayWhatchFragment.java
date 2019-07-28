package com.noplugins.keepfit.android.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.DateWhatchAdapter;
import com.noplugins.keepfit.android.entity.DayWhatch;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DayWhatchFragment extends ViewPagerFragment {
    @BindView(R.id.xrefreshview)
    XRefreshView xrefreshview;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;


    private View view;
    private LinearLayoutManager layoutManager;
    DateWhatchAdapter dateWhatchAdapter;

    public static DayWhatchFragment newInstance(String title) {
        DayWhatchFragment fragment = new DayWhatchFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void fetchData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_day_whatch, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();
        }
        return view;
    }

    private void initView() {
        List<DayWhatch> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DayWhatch dayWhatch = new DayWhatch();
            if(i==1||i==6||i==3){
                dayWhatch.setIs_out_date(true);
            }else{
                dayWhatch.setIs_out_date(false);
            }
            if(i==1||i==6||i==3){
                dayWhatch.setKecheng_type("1");//有氧
            }else if(i==2||i==5||i==8){
                dayWhatch.setKecheng_type("3");//单车
            }else{
                dayWhatch.setKecheng_type("2");//瑜伽

            }

            strings.add(dayWhatch);
        }
        set_list_resource(strings);

    }

    private void set_list_resource(final List<DayWhatch> dates) {
        //设置上拉刷新下拉加载
        recycler_view.setHasFixedSize(true);
        recycler_view.setItemAnimator(null);
        layoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(layoutManager);
        dateWhatchAdapter = new DateWhatchAdapter(dates, getActivity());
        recycler_view.setAdapter(dateWhatchAdapter);
        // 静默加载模式不能设置footerview
        // 设置静默加载模式
        xrefreshview.setSilenceLoadMore(true);
        //设置刷新完成以后，headerview固定的时间
        xrefreshview.setPinnedTime(1000);
        xrefreshview.setMoveForHorizontal(true);
        xrefreshview.setPullRefreshEnable(true);
        xrefreshview.setPullLoadEnable(true);
        xrefreshview.setAutoLoadMore(false);
        xrefreshview.enableRecyclerViewPullUp(true);
        xrefreshview.enablePullUpWhenLoadCompleted(true);
        //给recycler_view设置底部加载布局
        xrefreshview.enableReleaseToLoadMore(true);
        xrefreshview.enableRecyclerViewPullUp(true);
        xrefreshview.enablePullUpWhenLoadCompleted(true);
        xrefreshview.setPreLoadCount(10);
        if (dates.size() > 9) {
            xrefreshview.enableReleaseToLoadMore(true);
//            guanZhuWoRenAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));//加载更多
            xrefreshview.setLoadComplete(false);//显示底部
        } else {
            xrefreshview.enableReleaseToLoadMore(false);
            xrefreshview.setLoadComplete(true);//隐藏底部
        }
        //设置静默加载时提前加载的item个数
//        xefreshView1.setPreLoadCount(4);

        xrefreshview.setOnRecyclerViewScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        xrefreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        PAGE = 1;
//                        //填写刷新数据的网络请求，一般page=1，List集合清空操作
//                        init_net_source(false);
                        xrefreshview.stopRefresh();//刷新停止


                    }
                }, 1000);//2000是刷新的延时，使得有个动画效果
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
//                        PAGE = PAGE + 1;
//                        //填写加载更多的网络请求，一般page++
//                        init_net_source(false);
//
//                        //没有更多数据时候
//                        if (is_not_more) {
//                            xrefreshview.setLoadComplete(true);
//                        } else {
//                            //刷新完成必须调用此方法停止加载
                        xrefreshview.stopLoadMore(true);
//                        }


                    }
                }, 1000);//1000是加载的延时，使得有个动画效果


            }
        });
    }


}
