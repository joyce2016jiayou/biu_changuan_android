package com.noplugins.keepfit.android;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadingDialogListener;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.google.gson.Gson;
import com.huantansheng.easyphotos.models.puzzle.Line;
import com.noplugins.keepfit.android.activity.BuyActivity;
import com.noplugins.keepfit.android.activity.BuyHuiYuanActivity;
import com.noplugins.keepfit.android.activity.CheckStatusFailActivity;
import com.noplugins.keepfit.android.activity.UserPermissionSelectActivity;
import com.noplugins.keepfit.android.adapter.ContentPagerAdapterMy;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.base.MyApplication;
import com.noplugins.keepfit.android.entity.CheckEntity;
import com.noplugins.keepfit.android.entity.MaxMessageEntity;
import com.noplugins.keepfit.android.entity.VersionEntity;
import com.noplugins.keepfit.android.fragment.RiChengFragment;
import com.noplugins.keepfit.android.fragment.ViewFragment;
import com.noplugins.keepfit.android.fragment.MineFragment;
import com.noplugins.keepfit.android.fragment.MessageFragment;
import com.noplugins.keepfit.android.fragment.mine.MyFragment;
import com.noplugins.keepfit.android.fragment.statistics.StatisticsFragment;
import com.noplugins.keepfit.android.fragment.teacher.Is2999Fragment;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.jpush.TagAliasOperatorHelper;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.VersionUtils;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.eventbus.MessageEvent;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.BaseDialog;
import com.noplugins.keepfit.android.util.ui.NoScrollViewPager;
import com.noplugins.keepfit.android.wxapi.WXPayEntryActivity;
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
import rx.Subscription;

import static com.tencent.bugly.Bugly.applicationContext;

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
    private DownloadBuilder builder;
    private boolean is_qiangzhi_update;
    private String update_url = "";

    @Override
    public void initBundle(Bundle bundle) {
    }


    @Override
    public void initView() {
        setContentLayout(R.layout.activity_keepfit);
        ButterKnife.bind(this);

        if (SpUtils.getString(getApplicationContext(), AppConstants.USER_DENGJI).equals("2999")) {
            btn_shipu.setVisibility(View.GONE);
        } else {
            if (SpUtils.getInt(getApplicationContext(), AppConstants.USER_TYPE) == 1 ||
                    SpUtils.getInt(getApplicationContext(), AppConstants.USER_TYPE) == 2) {
                btn_shipu.setVisibility(View.VISIBLE);
            }
        }

        isShowTitle(false);
        MyApplication.addDestoryActivity(this, "KeepFitActivity");
        //注册eventbus
        //EventBus.getDefault().register(this);
        //初始化页面
        tabFragments.add(RiChengFragment.homeInstance("第一页"));
        if (SpUtils.getString(getApplicationContext(), AppConstants.USER_DENGJI).equals("2999")) {
            tabFragments.add(Is2999Fragment.Companion.newInstance("第二页"));
        } else {
            tabFragments.add(StatisticsFragment.Companion.newInstance("第二页"));
        }

        tabFragments.add(MessageFragment.newInstance("第三页"));
        tabFragments.add(MyFragment.Companion.newInstance("第四页"));
        //初始化viewpager
        contentAdapter = new ContentPagerAdapterMy(getSupportFragmentManager(), tabFragments);
        viewpager_content.setScroll(false);
        viewpager_content.setAdapter(contentAdapter);


        //初始化音效
        sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = sp.load(this, R.raw.button, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级


        //获取消息总数，设置消息总数
        get_message_all();

        //设置alias
        loginSuccess();

        //更新app
        update_app();

    }

    private void update_app() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "gym");
        params.put("code", VersionUtils.getAppVersionCode(getApplicationContext()));
        params.put("phoneType", "2");
        Subscription subscription = Network.getInstance("升级版本", this)
                .update_version(params,
                        new ProgressSubscriber<>("升级版本", new SubscriberOnNextListener<Bean<VersionEntity>>() {
                            @Override
                            public void onNext(Bean<VersionEntity> result) {
                                update_url = result.getData().getUrl();
                                //是否需要强制升级1强制升级 2不升级 3可升级可不升级
                                if (result.getData().getUp() == 1) {
                                    is_qiangzhi_update = true;
                                    update_app_pop();
                                } else {
                                    update_app_pop();
                                    is_qiangzhi_update = false;
                                }

                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));
    }

    private void update_app_pop() {
        builder = AllenVersionChecker
                .getInstance()
                .downloadOnly(crateUIData());
        builder.setCustomVersionDialogListener(createCustomDialogTwo());//设置更新弹窗样式
        builder.setCustomDownloadingDialogListener(createCustomDownloadingDialog());//设置下载样式
        builder.setForceRedownload(true);//强制重新下载apk（无论本地是否缓存）
        builder.setShowNotification(true);//显示下载通知栏
        builder.setShowDownloadingDialog(true);//显示下载中对话框
        builder.setShowDownloadFailDialog(true);//显示下载失败对话框
        builder.setDownloadAPKPath(Environment.getExternalStorageDirectory() + "/noplugins/apkpath/");//自定义下载路径
        builder.setOnCancelListener(() -> {
            if (is_qiangzhi_update) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(KeepFitActivity.this, "已关闭更新", Toast.LENGTH_SHORT).show();
            }
        });
        builder.executeMission(this);
    }
    /**
     * 自定义下载中对话框，下载中会连续回调此方法 updateUI
     * 务必用库传回来的context 实例化你的dialog
     *
     * @return
     */
    private CustomDownloadingDialogListener createCustomDownloadingDialog() {
        return new CustomDownloadingDialogListener() {
            @Override
            public Dialog getCustomDownloadingDialog(Context context, int progress, UIData versionBundle) {
                BaseDialog baseDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.custom_download_layout);
                return baseDialog;
            }

            @Override
            public void updateUI(Dialog dialog, int progress, UIData versionBundle) {
                TextView tvProgress = dialog.findViewById(R.id.tv_progress);
                ProgressBar progressBar = dialog.findViewById(R.id.pb);
                progressBar.setProgress(progress);
                tvProgress.setText(getString(R.string.versionchecklib_progress, progress));
            }
        };
    }
    /**
     * 更新弹窗样式
     *
     * @return
     */
    private CustomVersionDialogListener createCustomDialogTwo() {
        return (context, versionBundle) -> {
            BaseDialog baseDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.custom_dialog_two_layout);
            TextView textView = baseDialog.findViewById(R.id.tv_msg);
            textView.setText(versionBundle.getContent());
            baseDialog.setCanceledOnTouchOutside(true);
            return baseDialog;
        };
    }
    /**
     * @return
     * @important 使用请求版本功能，可以在这里设置downloadUrl
     * 这里可以构造UI需要显示的数据
     * UIData 内部是一个Bundle
     */
    private UIData crateUIData() {
        UIData uiData = UIData.create();
        uiData.setTitle(getString(R.string.update_title));
        uiData.setDownloadUrl(update_url);
        if(is_qiangzhi_update){
            uiData.setContent(getString(R.string.updatecontent2));
        }else{
            uiData.setContent(getString(R.string.updatecontent));
        }
        return uiData;
    }

    private void loginSuccess() {
        //如果没有缓存的别名，重新获取
        if ("".equals(SpUtils.getString(getApplicationContext(), AppConstants.IS_SET_ALIAS))) {
            //设置别名
            TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
            TagAliasOperatorHelper.sequence++;
            //设置用户编号为别名
            if (null == SpUtils.getString(getApplicationContext(), AppConstants.CHANGGUAN_NUM)) {
                tagAliasBean.alias = "null_user_id";
            } else {
                String user_id = SpUtils.getString(getApplicationContext(), AppConstants.CHANGGUAN_NUM);
                tagAliasBean.alias = user_id;
            }
            tagAliasBean.isAliasAction = true;
            tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
            TagAliasOperatorHelper.getInstance().handleAction(
                    getApplicationContext(),
                    TagAliasOperatorHelper.sequence, tagAliasBean
            );
            Log.e("设置的alias", "" + SpUtils.getString(getApplicationContext(), AppConstants.IS_SET_ALIAS));
        } else {
            Log.e("已经缓存alias", "" + SpUtils.getString(getApplicationContext(), AppConstants.IS_SET_ALIAS));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
        AllenVersionChecker.getInstance().cancelAllMission();


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

    private static final int TIME_EXIT = 2000;
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_EXIT > System.currentTimeMillis()) {
            //super.onBackPressed();
            Intent intent = new Intent();
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            startActivity(intent);
            return;
        } else {
            Toast.makeText(this, "再点击一次返回退出程序", Toast.LENGTH_SHORT).show();
            mBackPressed = System.currentTimeMillis();

        }
    }
}
