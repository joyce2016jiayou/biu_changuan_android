package com.noplugins.keepfit.android;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.activity.BuyActivity;
import com.noplugins.keepfit.android.activity.CheckStatusFailActivity;
import com.noplugins.keepfit.android.activity.UserPermissionSelectActivity;
import com.noplugins.keepfit.android.adapter.ContentPagerAdapterMy;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.base.MyApplication;
import com.noplugins.keepfit.android.entity.CheckEntity;
import com.noplugins.keepfit.android.entity.MaxMessageEntity;
import com.noplugins.keepfit.android.fragment.StatisticsFragment;
import com.noplugins.keepfit.android.fragment.ViewFragment;
import com.noplugins.keepfit.android.fragment.MineFragment;
import com.noplugins.keepfit.android.fragment.MessageFragment;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.eventbus.MessageEvent;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.NoScrollViewPager;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;

public class KeepFitActivity extends BaseActivity {
    @BindView(R.id.viewpager_content)
    NoScrollViewPager viewpager_content;
    @BindView(R.id.message_view)
    LinearLayout message_view;
    @BindView(R.id.message_num_tv)
    TextView message_num_tv;

    @BindViews({R.id.home_img, R.id.shipu_img, R.id.movie_img, R.id.mine_img})
    List<ImageView> bottom_iamge_views;
    private SoundPool sp;//声明一个SoundPool
    private int music;//定义一个整型用load（）；来设置suondID
    private List<Fragment> tabFragments = new ArrayList<>();

    @Override
    public void initBundle(Bundle bundle) {
    }


    @Override
    public void initView() {
        setContentLayout(R.layout.activity_keepfit);
        ButterKnife.bind(this);
        isShowTitle(false);
        MyApplication.addDestoryActivity(this, "KeepFitActivity");
        //注册eventbus
        //EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != getIntent().getExtras()) {
            Bundle parms = getIntent().getExtras();
            if (parms.getString("jpush_enter").equals("jpush_enter1")) {

                viewpager_content.setCurrentItem(2);
                xianshi_three();
                //设置跳转到消息tab1
                MessageEvent messageEvent = new MessageEvent("jpush_main_enter1");
                EventBus.getDefault().postSticky(messageEvent);

            } else if (parms.getString("jpush_enter").equals("jpush_enter2")) {
                viewpager_content.setCurrentItem(2);

                xianshi_three();
                //设置跳转到消息tab2
                MessageEvent messageEvent = new MessageEvent("jpush_main_enter2");
                EventBus.getDefault().postSticky(messageEvent);

            } else if (parms.getString("jpush_enter").equals("jpush_enter3")) {

                viewpager_content.setCurrentItem(2);
                xianshi_three();

                //设置跳转到消息tab3
                MessageEvent messageEvent = new MessageEvent("jpush_main_enter3");
                EventBus.getDefault().postSticky(messageEvent);

            } else if (parms.getString("jpush_enter").equals("jpush_enter4")) {

                viewpager_content.setCurrentItem(2);
                xianshi_three();
                //设置跳转到消息tab4
                MessageEvent messageEvent = new MessageEvent("jpush_main_enter4");
                EventBus.getDefault().postSticky(messageEvent);
            }
        } else {
            //初始化首页
            viewpager_content.setCurrentItem(0);

        }
    }

    @Override
    public void doBusiness(Context mContext) {
        //初始化页面
        tabFragments.add(ViewFragment.homeInstance("第一页"));
        tabFragments.add(StatisticsFragment.newInstance("第二页"));
        tabFragments.add(MessageFragment.newInstance("第三页"));
        tabFragments.add(MineFragment.myInstance("第四页"));
        //初始化viewpager
        ContentPagerAdapterMy contentAdapter = new ContentPagerAdapterMy(getSupportFragmentManager(), tabFragments);
        viewpager_content.setAdapter(contentAdapter);


        //初始化音效
        sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = sp.load(this, R.raw.button, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级


        //获取审核状态
        get_check_status();

        //获取消息总数，设置消息总数
        get_message_all();


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void upadate(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("update_message_num")) {//获取消息总数，设置消息总数
            get_message_all();
        }
    }


    private void get_message_all() {
        Map<String, String> params = new HashMap<>();
        params.put("gymAreaNum", "GYM19072138381319");
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Log.e(TAG, "获取场馆消息总数参数：" + json_params);
        subscription = Network.getInstance("获取场馆消息总数", getApplicationContext())
                .get_message_all(requestBody,
                        new ProgressSubscriberNew<>(MaxMessageEntity.class, new GsonSubscriberOnNextListener<MaxMessageEntity>() {
                            @Override
                            public void on_post_entity(MaxMessageEntity maxMessageEntity, String get_message_id) {
                                Log.e(TAG, "获取场馆消息总数成功：" + maxMessageEntity.getMessageCount());
                                //设置消息总数
                                if (maxMessageEntity.getMessageCount() > 0) {
                                    message_view.setVisibility(View.VISIBLE);
                                    if (maxMessageEntity.getMessageCount() > 99) {
                                        message_num_tv.setText("99+");
                                    } else {
                                        message_num_tv.setText(maxMessageEntity.getMessageCount() + "");
                                    }
                                } else {
                                    message_view.setVisibility(View.GONE);
                                }

                            }
                        }, new SubscriberOnNextListener<Bean<Object>>() {
                            @Override
                            public void onNext(Bean<Object> result) {

                            }

                            @Override
                            public void onError(String error) {
                                Logger.e(TAG, "获取获取场馆消息总数报错：" + error);
                                //Toast.makeText(getApplicationContext(), "获取审核状态失败！", Toast.LENGTH_SHORT).show();
                            }
                        }, this, true));
    }


    private void get_check_status() {
        String token;
        Map<String, String> params = new HashMap<>();
        if ("".equals(SharedPreferencesHelper.get(getApplicationContext(), Network.login_token, "").toString())) {
            token = "";
        } else {
            token = SharedPreferencesHelper.get(getApplicationContext(), Network.login_token, "").toString();
        }
        params.put("token", token);
        subscription = Network.getInstance("获取审核状态", getApplicationContext())
                .get_check_status(params,
                        new ProgressSubscriberNew<>(CheckEntity.class, new GsonSubscriberOnNextListener<CheckEntity>() {
                            @Override
                            public void on_post_entity(CheckEntity checkEntity, String get_message_id) {
                                Log.e(TAG, "获取审核状态成功：" + checkEntity.getStatus());
                                //成功1，失败0，没有提交过资料-2
                                if (checkEntity.getStatus() == 1) {
                                    if ("".equals(SharedPreferencesHelper.get(getApplicationContext(), Network.get_examine_result, ""))) {
                                        SharedPreferencesHelper.put(getApplicationContext(), Network.get_examine_result, "true");
                                        Intent intent = new Intent(KeepFitActivity.this, BuyActivity.class);
                                        startActivity(intent);
                                    }
                                } else if (checkEntity.getStatus() == 0) {
                                    Intent intent = new Intent(KeepFitActivity.this, CheckStatusFailActivity.class);
                                    startActivity(intent);
                                } else if (checkEntity.getStatus() == -2) {
                                    Intent intent = new Intent(KeepFitActivity.this, UserPermissionSelectActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }, new SubscriberOnNextListener<Bean<Object>>() {
                            @Override
                            public void onNext(Bean<Object> result) {

                            }

                            @Override
                            public void onError(String error) {
                                Log.e(TAG, "获取审核状态报错：" + error);
                            }
                        }, this, false));
    }


    @OnClick({R.id.btn_home, R.id.btn_shipu, R.id.btn_movie, R.id.btn_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                //按钮提示音
                sp.play(music, 0.2f, 0.2f, 0, 0, 1);
                viewpager_content.setCurrentItem(0);
                xianshi_one();
                break;
            case R.id.btn_shipu:
                //按钮提示音
                sp.play(music, 0.2f, 0.2f, 0, 0, 1);
                viewpager_content.setCurrentItem(1);
                xianshi_two();
                break;
            case R.id.btn_movie:
                sp.play(music, 0.2f, 0.2f, 0, 0, 1);
                viewpager_content.setCurrentItem(2);
                xianshi_three();
                break;
            case R.id.btn_mine:
                sp.play(music, 0.2f, 0.2f, 0, 0, 1);
                viewpager_content.setCurrentItem(3);
                xianshi_four();
                break;


        }
    }

    private void xianshi_one() {
        bottom_iamge_views.get(0).setImageResource(R.drawable.icon_home_on);
        bottom_iamge_views.get(1).setImageResource(R.drawable.icon_discover_off);
        bottom_iamge_views.get(2).setImageResource(R.drawable.icon_issue_off);
        bottom_iamge_views.get(3).setImageResource(R.drawable.icon_user_off);

    }

    private void xianshi_two() {
        bottom_iamge_views.get(0).setImageResource(R.drawable.icon_home_off);
        bottom_iamge_views.get(1).setImageResource(R.drawable.icon_discover_on);
        bottom_iamge_views.get(2).setImageResource(R.drawable.icon_issue_off);
        bottom_iamge_views.get(3).setImageResource(R.drawable.icon_user_off);
    }

    private void xianshi_three() {
        bottom_iamge_views.get(0).setImageResource(R.drawable.icon_home_off);
        bottom_iamge_views.get(1).setImageResource(R.drawable.icon_discover_off);
        bottom_iamge_views.get(2).setImageResource(R.drawable.icon_issue_on);
        bottom_iamge_views.get(3).setImageResource(R.drawable.icon_user_off);
    }

    private void xianshi_four() {
        bottom_iamge_views.get(0).setImageResource(R.drawable.icon_home_off);
        bottom_iamge_views.get(1).setImageResource(R.drawable.icon_discover_off);
        bottom_iamge_views.get(2).setImageResource(R.drawable.icon_issue_off);
        bottom_iamge_views.get(3).setImageResource(R.drawable.icon_user_on);
    }


}
