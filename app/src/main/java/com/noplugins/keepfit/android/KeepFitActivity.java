package com.noplugins.keepfit.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.provider.Contacts;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.activity.CheckStatusFailActivity;
import com.noplugins.keepfit.android.activity.LoginActivity;
import com.noplugins.keepfit.android.activity.UserPermissionSelectActivity;
import com.noplugins.keepfit.android.adapter.ContentPagerAdapterMy;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.LoginEntity;
import com.noplugins.keepfit.android.fragment.DiscoverFragment;
import com.noplugins.keepfit.android.fragment.HomeFragment;
import com.noplugins.keepfit.android.fragment.MyFragment;
import com.noplugins.keepfit.android.fragment.QuestionsAndAnswersFragment;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.entity.Token;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.NoScrollViewPager;
import com.orhanobut.logger.Logger;

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
    @BindViews({R.id.home_img, R.id.shipu_img, R.id.movie_img, R.id.mine_img})
    List<ImageView> bottom_iamge_views;

    private SoundPool sp;//声明一个SoundPool
    private int music;//定义一个整型用load（）；来设置suondID
    private List<Fragment> tabFragments = new ArrayList<>();


    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_keepfit);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        //初始化页面
        tabFragments.add(HomeFragment.homeInstance("第一页"));
        tabFragments.add(DiscoverFragment.newInstance("第二页"));
        tabFragments.add(QuestionsAndAnswersFragment.newInstance("第三页"));
        tabFragments.add(MyFragment.myInstance("第四页"));
        //初始化viewpager
        ContentPagerAdapterMy contentAdapter = new ContentPagerAdapterMy(getSupportFragmentManager(), tabFragments);
        viewpager_content.setAdapter(contentAdapter);
        viewpager_content.setCurrentItem(0);

        //初始化音效
        sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = sp.load(this, R.raw.button, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级


        //获取审核状态
        get_check_status();

    }

    private void get_check_status() {
        String token;
        Map<String, String> params = new HashMap<>();
        if ("".equals(SharedPreferencesHelper.get(getApplicationContext(), "login_token", "").toString())) {
            token = "";
        } else {
            token = SharedPreferencesHelper.get(getApplicationContext(), "login_token", "").toString();
        }
        params.put("token", token);
        subscription = Network.getInstance("获取审核状态", getApplicationContext())
                .get_check_status(params,
                        new ProgressSubscriberNew<>(List.class, new GsonSubscriberOnNextListener<List>() {
                            @Override
                            public void on_post_entity(List mlocation,String get_message_id) {
                                Log.e(TAG, "获取审核状态成功：" );
                            }
                        }, new SubscriberOnNextListener<Bean<Object>>() {
                            @Override
                            public void onNext(Bean<Object> result) {

                            }
                            @Override
                            public void onError(String error) {
//                                Intent intent = new Intent(KeepFitActivity.this, CheckStatusFailActivity.class);
//                                startActivity(intent);

                                Logger.e(TAG, "获取审核状态报错：" + error);
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
