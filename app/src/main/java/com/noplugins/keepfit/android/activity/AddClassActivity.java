package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;
import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.AddClassAdapter;
import com.noplugins.keepfit.android.adapter.DateWhatchAdapter;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.ClassEntity;
import com.noplugins.keepfit.android.entity.DateViewEntity;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddClassActivity extends BaseActivity {
    @BindView(R.id.xrefreshview)
    XRefreshView xrefreshview;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.add_class_btn)
    LinearLayout add_class_btn;
    @BindView(R.id.back_btn)
    ImageView back_btn;

    private LinearLayoutManager layoutManager;
    private AddClassAdapter addClassAdapter;
    private int page = 1;
    private List<ClassEntity.DataBean> dataBeans = new ArrayList<>();

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_add_class);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init_class_date();

    }

    @Override
    public void doBusiness(Context mContext) {


        add_class_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddClassActivity.this, AddClassItemActivity.class);
                startActivity(intent);
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void init_class_date() {
        Map<String, Object> params = new HashMap<>();
        String gymAreaNum;
        if ("".equals(SharedPreferencesHelper.get(this, Network.changguan_number, "").toString())) {
            gymAreaNum = "";
        } else {
            gymAreaNum = SharedPreferencesHelper.get(this, Network.changguan_number, "").toString();
        }
        params.put("gymAreaNum", gymAreaNum);//场馆编号
        params.put("page", page);
        subscription = Network.getInstance("课程列表", this)
                .class_list(params, new ProgressSubscriberNew<>(ClassEntity.class, new GsonSubscriberOnNextListener<ClassEntity>() {
                    @Override
                    public void on_post_entity(ClassEntity entity, String s) {
                        dataBeans = entity.getData();
                        Log.e("课程列表成功", entity + "课程列表成功" + s);
                        set_list_resource(dataBeans);
                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {

                    }

                    @Override
                    public void onError(String error) {
                        Log.e("课程列表失败", "课程列表失败:" + error);
                    }
                }, this, true));
    }

    private void set_list_resource(final List<ClassEntity.DataBean> dates) {
        //设置上拉刷新下拉加载
        recycler_view.setHasFixedSize(true);
        recycler_view.setItemAnimator(null);
        layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        addClassAdapter = new AddClassAdapter(dates, this);
        addClassAdapter.setOnItemClickListener(new AddClassAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                if (dataBeans.get(position).getStatus() == 1) {
//                    Intent intent = new Intent(AddClassActivity.this, YaoQingZhongDetailActivity.class);
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(AddClassActivity.this, AddClassDetailActivity.class);
//                    startActivity(intent);
//                }
                Intent intent = new Intent(AddClassActivity.this, YaoQingZhongDetailActivity.class);
                Bundle bundle = new Bundle();
                Log.e("健康是福",dates.get(position).getCourse_num());
                bundle.putString("gymCourseNum",dates.get(position).getCourse_num());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        recycler_view.setAdapter(addClassAdapter);
        // 静默加载模式不能设置footerview
        // 设置静默加载模式
        xrefreshview.setSilenceLoadMore(true);
        //设置刷新完成以后，headerview固定的时间
        xrefreshview.setPinnedTime(1000);
        xrefreshview.setMoveForHorizontal(true);
        xrefreshview.setPullRefreshEnable(true);
        xrefreshview.setPullLoadEnable(false);//关闭加载更多
        xrefreshview.setAutoLoadMore(false);
        xrefreshview.enableRecyclerViewPullUp(true);
        xrefreshview.enablePullUpWhenLoadCompleted(true);
        //给recycler_view设置底部加载布局
        xrefreshview.enableReleaseToLoadMore(true);
        xrefreshview.enableRecyclerViewPullUp(true);
        xrefreshview.enablePullUpWhenLoadCompleted(true);
        xrefreshview.setPreLoadCount(10);
        xrefreshview.enableReleaseToLoadMore(false);
        xrefreshview.setLoadComplete(true);//隐藏底部
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
                        //PAGE = 1;
                        //填写刷新数据的网络请求，一般page=1，List集合清空操作
                        //get_date_class_resource("");
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
//                        get_date_class_resource();
//
//                        //没有更多数据时候
//                        if (is_not_more) {
//                            xrefreshview.setLoadComplete(true);
//                        } else {
//                            //刷新完成必须调用此方法停止加载
//                            xrefreshview.stopLoadMore(true);
//                        }


                    }
                }, 1000);//1000是加载的延时，使得有个动画效果


            }
        });
    }

}
