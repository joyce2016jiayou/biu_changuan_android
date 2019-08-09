package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.BillAdapter;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.BillEntity;
import com.noplugins.keepfit.android.util.TimePickerUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.wheelpicker.DatePicker;
import cn.qqtheme.framework.wheelpicker.TimePicker;

public class BillActivity extends BaseActivity {
    @BindView(R.id.xrefreshview)
    XRefreshView xrefreshview;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.tv_select_time)
    TextView tv_select_time;
    @BindView(R.id.tv_filter)
    TextView tv_filter;

    DatePicker datePicker;

    private View view;
    private LinearLayoutManager layoutManager;
    private BillAdapter billAdapter;
    private int page = 1;
    private List<BillEntity.BillItemBean> billItemBeans = new ArrayList<>();
    private int maxPage;
    private boolean is_not_more;
    private String date;
    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_bill);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //筛选
            }
        });
        tv_select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //日期选择
                date = tv_select_time.getText().toString();
                TimePickerUtils.select_date(BillActivity.this,datePicker,tv_select_time);
                if (!date.equals(tv_select_time.getText().toString())){
                    //调用接口
                }
            }
        });
    }

    /**
     * 获取账单列表
     */
    private void initBill(String data,int type){
        //默认获取当月的数据


    }




    private void set_list_resource(List<BillEntity.BillItemBean> billItemBeans){
        //设置上拉刷新下拉加载
        recycler_view.setHasFixedSize(false);
        recycler_view.setItemAnimator(null);
        layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        billAdapter = new BillAdapter(billItemBeans, this);
        recycler_view.setAdapter(billAdapter);

        // 静默加载模式不能设置footerview
        // 设置静默加载模式
        //xrefreshview.setSilenceLoadMore(true);
        //设置刷新完成以后，headerview固定的时间
        xrefreshview.setPinnedTime(1000);
        xrefreshview.setMoveForHorizontal(true);
        //xrefreshview.setPullRefreshEnable(true);
        xrefreshview.setPullLoadEnable(true);//关闭加载更多
        xrefreshview.setAutoLoadMore(false);
        xrefreshview.enableRecyclerViewPullUp(true);
        xrefreshview.enablePullUpWhenLoadCompleted(true);
        //给recycler_view设置底部加载布局
        if (billItemBeans.size() > 9) {
            xrefreshview.enableReleaseToLoadMore(true);
            billAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));//加载更多
            xrefreshview.setLoadComplete(false);//显示底部
        } else {
            xrefreshview.enableReleaseToLoadMore(false);
            xrefreshview.setLoadComplete(true);//隐藏底部
        }

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
                        page = 1;
                        //填写刷新数据的网络请求，一般page=1，List集合清空操作
                        billItemBeans.clear();
//                        initMessageDate();
                        xrefreshview.stopRefresh();//刷新停止


                    }
                }, 1000);//2000是刷新的延时，使得有个动画效果
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page = page + 1;
//                        initMessageDate();
                        //填写加载更多的网络请求，一般page++
//                        //没有更多数据时候
                        if (is_not_more) {
                            xrefreshview.setLoadComplete(true);
                        } else {
                            //刷新完成必须调用此方法停止加载
                            xrefreshview.stopLoadMore(true);
                        }
                    }
                }, 1000);//1000是加载的延时，使得有个动画效果
            }
        });

    }
}
