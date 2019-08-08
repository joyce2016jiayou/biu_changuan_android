package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.AddClassAdapter;
import com.noplugins.keepfit.android.adapter.YaoQiTeacherAdapter;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.ClassEntity;
import com.noplugins.keepfit.android.entity.TeacherEntity;
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
import okhttp3.RequestBody;

public class YaoQingTeacherActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    ImageView back_btn;
    @BindView(R.id.xrefreshview)
    XRefreshView xrefreshview;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.yaoqing_number_tv)
    TextView yaoqing_number_tv;
    private int maxPage;
    private boolean is_not_more;
    private LinearLayoutManager layoutManager;
    private YaoQiTeacherAdapter yaoQiTeacherAdapter;
    private String create_time;
    private String gym_course_num;
    private List<TeacherEntity.TeacherBean> dataBeans = new ArrayList<>();
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initBundle(Bundle parms) {
        create_time = parms.getString("create_time");
        gym_course_num = parms.getString("gym_course_num");
        Log.e("创建的时间",create_time);
    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_yao_qing_teacher);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        init_teacher_resource();
    }

    private void init_teacher_resource() {
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", create_time);//场馆编号
        params.put("gymCourse", gym_course_num);
        params.put("page", page);
        Log.e(TAG, "教练列表参数：" + params);
        subscription = Network.getInstance("教练列表", this)
                .get_teacher_list(params, new ProgressSubscriberNew<>(TeacherEntity.class, new GsonSubscriberOnNextListener<TeacherEntity>() {
                    @Override
                    public void on_post_entity(TeacherEntity entity, String s) {
                        maxPage = entity.getMaxPage();
                        if (page == 1) {//表示刷新
                            dataBeans.addAll(entity.getTeacher());
                            set_list_resource(dataBeans);
                        } else {
                            if (page <= maxPage) {//表示加载还有数据
                                is_not_more = false;
                                dataBeans.addAll(entity.getTeacher());
                                yaoQiTeacherAdapter.notifyDataSetChanged();

                            } else {//表示没有更多数据了
                                is_not_more = true;
                                dataBeans.addAll(entity.getTeacher());
                                yaoQiTeacherAdapter.notifyDataSetChanged();
                            }
                        }
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

    private void set_list_resource(final List<TeacherEntity.TeacherBean> dates) {
        //设置上拉刷新下拉加载
        recycler_view.setHasFixedSize(true);
        recycler_view.setItemAnimator(null);
        layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        yaoQiTeacherAdapter = new YaoQiTeacherAdapter(dates, this,yaoqing_number_tv,gym_course_num);
        yaoQiTeacherAdapter.setOnItemClickListener(new YaoQiTeacherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(YaoQingTeacherActivity.this, TeacherDetailActivity.class);
                startActivity(intent);
            }
        });
        recycler_view.setAdapter(yaoQiTeacherAdapter);
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
        if (dates.size() > 9) {
            xrefreshview.enableReleaseToLoadMore(true);
            yaoQiTeacherAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));//加载更多
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
                        page = 1;
                        //填写刷新数据的网络请求，一般page=1，List集合清空操作
                        dates.clear();
                        init_teacher_resource();
                        xrefreshview.stopRefresh();//刷新停止
                    }
                }, 1000);//2000是刷新的延时，使得有个动画效果
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page = page + 1;
                        init_teacher_resource();
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
