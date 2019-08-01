package com.noplugins.keepfit.android.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.activity.AddClassActivity;
import com.noplugins.keepfit.android.adapter.DateWhatchAdapter;
import com.noplugins.keepfit.android.entity.DateViewEntity;
import com.noplugins.keepfit.android.entity.DayWhatch;
import com.noplugins.keepfit.android.util.data.DateHelper;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

import static com.umeng.socialize.net.dplus.CommonNetImpl.TAG;


public class DayWhatchFragment extends ViewPagerFragment {
    @BindView(R.id.xrefreshview)
    XRefreshView xrefreshview;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.add_class_btn)
    ImageView add_class_btn;

    private View view;
    private LinearLayoutManager layoutManager;
    private DateWhatchAdapter dateWhatchAdapter;
    private List<DateViewEntity.DateBean> dateBeans = new ArrayList<>();
//    private int PAGE = 1;
//    private boolean is_not_more;

    public static DayWhatchFragment newInstance(String title) {
        DayWhatchFragment fragment = new DayWhatchFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    //注册广播接收器
    LocalBroadcastManager broadcastManager;
    private void registerReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("zachary");
        broadcastManager.registerReceiver(mRefreshReceiver, intentFilter);
    }

    private BroadcastReceiver mRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String refresh = intent.getStringExtra("refreshInfo");
            String select_date = intent.getStringExtra("select_date");
            if ("yes".equals(refresh)) {
                // 在主线程中刷新UI，用Handler来实现
                new Handler().post(new Runnable() {
                    public void run() {
                        Log.e("打开就结束啦", "接口数量的");
                        //在这里来写你需要刷新的地方
                        get_date_class_resource(select_date);
                    }
                });
            }
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        broadcastManager.unregisterReceiver(mRefreshReceiver);

    }

    @Override
    public void fetchData() {
        //获取日视角课程接口
        get_date_class_resource("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_day_whatch, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();
            registerReceiver();
        }
        return view;
    }

    private void initView() {

        add_class_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddClassActivity.class);
                startActivity(intent);
            }
        });

    }

    public void get_date_class_resource(String selectdate) {
        Map<String, String> params = new HashMap<>();
        //获取当前的时间
        String crrent_date = DateHelper.get_current_date();//获取当前的时间
        //params.put("date", crrent_date);
        //Log.e("获取当前的时间",DateHelper.get_current_date());
        if (selectdate.length() > 0) {
            params.put("date", selectdate);
        } else {
            params.put("date", "2019-08-01");
        }
        String gymAreaNum;
        if ("".equals(SharedPreferencesHelper.get(getActivity(), "changguan_number", "").toString())) {
            gymAreaNum = "";
        } else {
            gymAreaNum = SharedPreferencesHelper.get(getActivity(), "changguan_number", "").toString();
        }
        //params.put("gymAreaNum", gymAreaNum);//场馆编号

        params.put("gymAreaNum", "GYM19072138381319");//场馆编号
        Subscription subscription = Network.getInstance("获取课程", getActivity())
                .get_class_resource(params,
                        new ProgressSubscriberNew<>(DateViewEntity.class, new GsonSubscriberOnNextListener<DateViewEntity>() {
                            @Override
                            public void on_post_entity(DateViewEntity dateViewEntity, String get_message_id) {
                                dateBeans = dateViewEntity.getDayView();

                                Log.e(TAG, "获取课程成功：" + dateBeans.size());
                                set_list_resource(dateBeans);

                            }
                        }, new SubscriberOnNextListener<Bean<Object>>() {
                            @Override
                            public void onNext(Bean<Object> result) {

                            }

                            @Override
                            public void onError(String error) {
                                Log.e(TAG, "获取课程报错：" + error);
                                Toast.makeText(getActivity(), "获取课程失败！", Toast.LENGTH_SHORT).show();
                            }
                        }, getActivity(), true));

    }


    private void set_list_resource(final List<DateViewEntity.DateBean> dates) {
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
                        get_date_class_resource("");
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
