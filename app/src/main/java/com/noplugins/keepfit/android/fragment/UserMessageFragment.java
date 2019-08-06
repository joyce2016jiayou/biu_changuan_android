package com.noplugins.keepfit.android.fragment;

import android.content.Context;
import android.content.Intent;
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

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.SystemMessageAdapter;
import com.noplugins.keepfit.android.adapter.UserMessageAdapter;
import com.noplugins.keepfit.android.entity.MessageEntity;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import rx.Subscription;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;


public class UserMessageFragment extends Fragment {
    @BindView(R.id.xrefreshview)
    XRefreshView xrefreshview;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private View view;
    private LinearLayoutManager layoutManager;
    private UserMessageAdapter userMessageAdapter;
    private int page = 1;
    private List<MessageEntity.MessageBean> messageBeans = new ArrayList<>();
    private int maxPage;
    private boolean is_not_more;

    public static UserMessageFragment newInstance(String title) {
        UserMessageFragment fragment = new UserMessageFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_user_message, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();
        }
        return view;
    }

    private void initView() {
        initMessageDate();//获取消息列表
    }

    private void initMessageDate() {
        Map<String, Object> params = new HashMap<>();
        params.put("gymAreaNum", "GYM19072138381319");//场馆编号
        params.put("page", page);//场馆编号
        params.put("type", "3");//场馆编号
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Log.e(TAG, "消息列表参数：" + json_params);
        Subscription subscription = Network.getInstance("消息列表", getActivity())
                .zhanghu_message_list(requestBody, new ProgressSubscriberNew<>(MessageEntity.class, new GsonSubscriberOnNextListener<MessageEntity>() {
                    @Override
                    public void on_post_entity(MessageEntity entity, String s) {
                        Log.e("用户消息列表成功", entity + "用户消息列表成功" + entity.getMessage().size());

                        maxPage = entity.getMaxPage();
                        if (page == 1) {//表示刷新
                            messageBeans.addAll(entity.getMessage());
                            set_list_resource(messageBeans);
                        } else {
                            if (page <= maxPage) {//表示加载还有数据
                                is_not_more = false;
                                messageBeans.addAll(entity.getMessage());
                                userMessageAdapter.notifyDataSetChanged();

                            } else {//表示没有更多数据了
                                is_not_more = true;
                                messageBeans.addAll(entity.getMessage());
                                userMessageAdapter.notifyDataSetChanged();
                            }
                        }

                        boolean is_read = entity.isRead();//有已读消息，false是未读消息，通知MessageFragment更新红点信息
                        Intent intent = new Intent("update_message_read_status");
                        intent.putExtra("is_read", "" + is_read);
                        intent.putExtra("type_number", "3");
                        LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).sendBroadcast(intent);
                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {

                    }

                    @Override
                    public void onError(String error) {
                        Log.e("消息列表失败", "消息列表失败:" + error);
                    }
                }, getActivity(), true));
    }

    private void set_list_resource(final List<MessageEntity.MessageBean> dates) {
        //设置上拉刷新下拉加载
        recycler_view.setHasFixedSize(false);
        recycler_view.setItemAnimator(null);
        layoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(layoutManager);
        userMessageAdapter = new UserMessageAdapter(dates, getActivity());
        recycler_view.setAdapter(userMessageAdapter);
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
            userMessageAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getActivity()));//加载更多
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
                        dates.clear();
                        initMessageDate();
                        xrefreshview.stopRefresh();//刷新停止


                    }
                }, 1000);//2000是刷新的延时，使得有个动画效果
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page = page + 1;
                        initMessageDate();
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
