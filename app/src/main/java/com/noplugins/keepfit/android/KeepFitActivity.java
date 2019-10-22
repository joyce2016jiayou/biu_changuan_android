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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.huantansheng.easyphotos.models.puzzle.Line;
import com.noplugins.keepfit.android.activity.BuyActivity;
import com.noplugins.keepfit.android.activity.CheckStatusFailActivity;
import com.noplugins.keepfit.android.activity.UserPermissionSelectActivity;
import com.noplugins.keepfit.android.adapter.ContentPagerAdapterMy;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.base.MyApplication;
import com.noplugins.keepfit.android.entity.CheckEntity;
import com.noplugins.keepfit.android.entity.MaxMessageEntity;
import com.noplugins.keepfit.android.fragment.RiChengFragment;
import com.noplugins.keepfit.android.fragment.ViewFragment;
import com.noplugins.keepfit.android.fragment.MineFragment;
import com.noplugins.keepfit.android.fragment.MessageFragment;
import com.noplugins.keepfit.android.fragment.mine.MyFragment;
import com.noplugins.keepfit.android.fragment.statistics.StatisticsFragment;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.SpUtils;
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
    @BindView(R.id.btn_home)
    LinearLayout btn_home;
    @BindView(R.id.btn_shipu)
    LinearLayout btn_shipu;
    @BindView(R.id.btn_movie)
    RelativeLayout btn_movie;
    @BindView(R.id.btn_mine)
    LinearLayout btn_mine;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;

    @BindViews({R.id.home_img, R.id.shipu_img, R.id.movie_img, R.id.mine_img})

    List<ImageView> bottom_iamge_views;


    private SoundPool sp;//声明一个SoundPool
    private int music;//定义一个整型用load（）；来设置suondID
    private List<Fragment> tabFragments = new ArrayList<>();
    ContentPagerAdapterMy contentAdapter;

    @Override
    public void initBundle(Bundle bundle) {
    }


    @Override
    public void initView() {
        setContentLayout(R.layout.activity_keepfit);
        ButterKnife.bind(this);
        if (SpUtils.getInt(getApplicationContext(), AppConstants.USER_TYPE) == 3) {
            btn_shipu.setVisibility(View.GONE);
        }
        isShowTitle(false);
        MyApplication.addDestoryActivity(this, "KeepFitActivity");
        //注册eventbus
        //EventBus.getDefault().register(this);
        //初始化页面
        tabFragments.add(RiChengFragment.homeInstance("第一页"));
        tabFragments.add(StatisticsFragment.Companion.newInstance("第二页"));
        tabFragments.add(MessageFragment.newInstance("第三页"));
        tabFragments.add(MyFragment.Companion.newInstance("第四页"));
        //初始化viewpager
        contentAdapter = new ContentPagerAdapterMy(getSupportFragmentManager(), tabFragments);
        viewpager_content.setAdapter(contentAdapter);


        //初始化音效
        sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = sp.load(this, R.raw.button, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级


        //获取消息总数，设置消息总数
//        get_message_all();
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
            if (parms.getString("jpush_enter", "").equals("")) {
                return;
            }
            if (parms.getString("jpush_enter", "").equals("jpush_enter1")) {

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
//            viewpager_content.setCurrentItem(0);

        }
    }

    @Override
    public void doBusiness(Context mContext) {
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("这是什么？", "这是我的锅吧");
        viewpager_content.setCurrentItem(3);
        xianshi_four();
    }

    private void xianshi_one() {
        bottom_iamge_views.get(0).setImageResource(R.drawable.icon_home_on);
        tv1.setTextColor(getResources().getColor(R.color.color_F6C82A));
        bottom_iamge_views.get(1).setImageResource(R.drawable.icon_discover_off);
        tv2.setTextColor(getResources().getColor(R.color.color_4A4A4A));
        bottom_iamge_views.get(2).setImageResource(R.drawable.icon_issue_off);
        tv3.setTextColor(getResources().getColor(R.color.color_4A4A4A));
        bottom_iamge_views.get(3).setImageResource(R.drawable.icon_user_off);
        tv4.setTextColor(getResources().getColor(R.color.color_4A4A4A));
    }

    private void xianshi_two() {
        bottom_iamge_views.get(0).setImageResource(R.drawable.icon_home_off);
        tv1.setTextColor(getResources().getColor(R.color.color_4A4A4A));
        bottom_iamge_views.get(1).setImageResource(R.drawable.icon_discover_on);
        tv2.setTextColor(getResources().getColor(R.color.color_F6C82A));
        bottom_iamge_views.get(2).setImageResource(R.drawable.icon_issue_off);
        tv3.setTextColor(getResources().getColor(R.color.color_4A4A4A));
        bottom_iamge_views.get(3).setImageResource(R.drawable.icon_user_off);
        tv4.setTextColor(getResources().getColor(R.color.color_4A4A4A));
    }

    private void xianshi_three() {
        bottom_iamge_views.get(0).setImageResource(R.drawable.icon_home_off);
        tv1.setTextColor(getResources().getColor(R.color.color_4A4A4A));
        bottom_iamge_views.get(1).setImageResource(R.drawable.icon_discover_off);
        tv2.setTextColor(getResources().getColor(R.color.color_4A4A4A));
        bottom_iamge_views.get(2).setImageResource(R.drawable.icon_issue_on);
        tv3.setTextColor(getResources().getColor(R.color.color_F6C82A));
        bottom_iamge_views.get(3).setImageResource(R.drawable.icon_user_off);
        tv4.setTextColor(getResources().getColor(R.color.color_4A4A4A));

    }

    private void xianshi_four() {
        bottom_iamge_views.get(0).setImageResource(R.drawable.icon_home_off);
        tv1.setTextColor(getResources().getColor(R.color.color_4A4A4A));
        bottom_iamge_views.get(1).setImageResource(R.drawable.icon_discover_off);
        tv2.setTextColor(getResources().getColor(R.color.color_4A4A4A));
        bottom_iamge_views.get(2).setImageResource(R.drawable.icon_issue_off);
        tv3.setTextColor(getResources().getColor(R.color.color_4A4A4A));
        bottom_iamge_views.get(3).setImageResource(R.drawable.icon_user_on);
        tv4.setTextColor(getResources().getColor(R.color.color_F6C82A));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // 在这设置选中你要显示的fragment
            if (resultCode == 0) {
                viewpager_content.setCurrentItem(0);
                xianshi_one();
            } else if (resultCode == 1) {
                viewpager_content.setCurrentItem(1);
                xianshi_two();
            } else if (resultCode == 2) {
                viewpager_content.setCurrentItem(2);
                xianshi_three();
            } else if (resultCode == 3) {
                viewpager_content.setCurrentItem(3);
                xianshi_four();
            }
        }
    }
}
