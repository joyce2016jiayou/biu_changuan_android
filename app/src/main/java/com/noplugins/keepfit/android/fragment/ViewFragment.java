package com.noplugins.keepfit.android.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.ContentPagerAdapterMy;
import com.noplugins.keepfit.android.util.data.DateHelper;
import com.noplugins.keepfit.android.util.ui.NoScrollViewPager;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;
import com.noplugins.keepfit.android.util.ui.erweima.android.CaptureActivity;
import com.noplugins.keepfit.android.util.ui.erweima.bean.ZxingConfig;
import com.noplugins.keepfit.android.util.ui.erweima.common.Constant;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.weiget.CalendarView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * 首页
 */
public class ViewFragment extends ViewPagerFragment {

    @BindView(R.id.saoma_btn)
    ImageView saoma_btn;
    @BindView(R.id.fenxiang_btn)
    ImageView fenxiang_btn;
    @BindView(R.id.viewpager_content)
    NoScrollViewPager viewpager_content;
    @BindView(R.id.shijiao_qiehuan_btn)
    TextView shijiao_qiehuan_btn;
    @BindView(R.id.xiala_icon)
    ImageView xiala_icon;
    @BindView(R.id.date_tv)
    TextView date_tv;
    @BindView(R.id.rili_xiala)
    LinearLayout rili_xiala;

    private View view;
    private int REQUEST_CODE = 110;
    private int REQUEST_CODE_SCAN = 111;
    private List<Fragment> tabFragments = new ArrayList<>();
    private PopupWindow popupWindow;
    //获取当前的日期
    private int[] cDate = CalendarUtil.getCurrentDate();

    public static ViewFragment homeInstance(String title) {
        ViewFragment fragment = new ViewFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.home_fragment, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();

            //注册广播接收器
            registerReceiver();

        }
        return view;
    }


    private void initView() {
        //初始化viewpager
        tabFragments.add(DayWhatchFragment.newInstance("日视角"));
        tabFragments.add(MonWhatchFragment.newInstance("月视角"));
        ContentPagerAdapterMy contentAdapter = new ContentPagerAdapterMy(getChildFragmentManager(), tabFragments);
        viewpager_content.setAdapter(contentAdapter);
        viewpager_content.setCurrentItem(0);
        //切换视角按钮
        shijiao_qiehuan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shijiao_qiehuan_btn.getText().equals("日")) {//切换月视角
                    shijiao_qiehuan_btn.setText("月");
                    viewpager_content.setCurrentItem(0);
                    xiala_icon.setVisibility(View.VISIBLE);


                } else {//切换月视角
                    shijiao_qiehuan_btn.setText("日");
                    viewpager_content.setCurrentItem(1);
                    xiala_icon.setVisibility(View.INVISIBLE);
                    if(cDate[1]<=9){
                        date_tv.setText("0"+cDate[1]+"月");//显示当前月份
                    }else{
                        date_tv.setText(cDate[1]+"月");//显示当前月份
                    }


                }
            }
        });
        date_tv.setText(DateHelper.get_date2(cDate[1],cDate[2]));
        //扫码按钮
        saoma_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndPermission.with(getActivity())
                        .permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                                /*ZxingConfig是配置类
                                 *可以设置是否显示底部布局，闪光灯，相册，
                                 * 是否播放提示音  震动
                                 * 设置扫描框颜色等
                                 * 也可以不传这个参数
                                 * */
                                ZxingConfig config = new ZxingConfig();
                                // config.setPlayBeep(false);//是否播放扫描声音 默认为true
                                //  config.setShake(false);//是否震动  默认为true
                                // config.setDecodeBarCode(false);//是否扫描条形码 默认为true
//                                config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色
//                                config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
//                                config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
                                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                                startActivityForResult(intent, REQUEST_CODE_SCAN);
                            }
                        })
                        .onDenied(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Uri packageURI = Uri.parse("package:" + getPackageName());
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);

                                Toast.makeText(getActivity(), "没有权限无法扫描呦", Toast.LENGTH_LONG).show();
                            }
                        }).start();
            }
        });

        //分享按钮
        fenxiang_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //带面板
                new ShareAction(getActivity()).withText("hello").setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(shareListener).open();
                //不带面板
//                new ShareAction(ShareSDKDEMOActivity.this)
//                        .setPlatform(SHARE_MEDIA.QQ)//传入平台
//                        .withText("hello")//分享内容
//                        .setCallback(shareListener)//回调监听器
//                        .share();

            }
        });
        //日期下拉按钮
        rili_xiala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //select_data_popwindow();
                select_data_popwindow_view();
            }
        });

    }

    @Override
    public void onResume() {
        int id = getActivity().getIntent().getIntExtra("id", 0);
        if(id==2){
            viewpager_content.setCurrentItem(0);
        }
        super.onResume();
    }

    /**
     * 日历弹窗
     */
    private void select_data_popwindow_view() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.daywhatch_rili_view, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //popupWindow
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(null);

        popupWindow.getContentView().setFocusable(true); // 这个很重要
        popupWindow.getContentView().setFocusableInTouchMode(true);
        popupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        popupWindow.showAsDropDown(rili_xiala, 0, 10);

        //设置逻辑
        CalendarView calendarView = view.findViewById(R.id.calendar);
        TextView title_tv = (TextView) view.findViewById(R.id.title);

        calendarView
                .setStartEndDate("2019.01", "2025.12")
                .setDisableStartEndDate("2019.01.01", "2025.12.30")
                .setInitDate(cDate[0] + "." + cDate[1])
                .setSingleDate(cDate[0] + "." + cDate[1] + "." + cDate[2])
                .init();

        //设置title
        title_tv.setText(cDate[0] + "年" + cDate[1] + "月");
        LinearLayout last_btn = view.findViewById(R.id.last_btn);
        last_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.lastMonth();
            }
        });
        LinearLayout next_btn = view.findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.nextMonth();
            }
        });
        calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                date_tv.setText(DateHelper.get_date2(date.getSolar()[1],date.getSolar()[2]));
                popupWindow.dismiss();
                //日视角刷新数据
                String select_date = DateHelper.get_date(date.getSolar()[0], date.getSolar()[1], date.getSolar()[2]);
                Log.e("选择的日期", select_date);

                Intent intent = new Intent("zachary");
                intent.putExtra("refreshInfo", "yes");
                intent.putExtra("select_date", select_date);

                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);


            }
        });


        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title_tv.setText(date[0] + "年" + date[1] + "月");
            }
        });


    }

    //注册广播接收器
    LocalBroadcastManager broadcastManager;
    private void registerReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("update_refresh");
        broadcastManager.registerReceiver(mRefreshReceiver, intentFilter);
    }

    private BroadcastReceiver mRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String refresh = intent.getStringExtra("enter_of_month_view");
            if ("yes".equals(refresh)) {//表示从月视角进来的
                String select_year = intent.getStringExtra("select_year");
                String select_month = intent.getStringExtra("select_month");
                String select_date = intent.getStringExtra("select_date");
                String month_select_date = intent.getStringExtra("mselect_date");
                Log.e("月视角选择的日期",month_select_date);
                // 在主线程中刷新UI，用Handler来实现
                new Handler().post(new Runnable() {
                    public void run() {
                        Log.e("表示从月视角进来的", "选择日期"+month_select_date);
                        //切换日视角需要更新的view
                        viewpager_content.setCurrentItem(0);
                        shijiao_qiehuan_btn.setText("月");
                        xiala_icon.setVisibility(View.VISIBLE);
                        String select_data_tv= DateHelper.get_date2(Integer.valueOf(select_month),Integer.valueOf(select_date));
                        date_tv.setText(select_data_tv);
                        //更新日视角数据
                        Intent intent = new Intent("zachary");
                        intent.putExtra("refreshInfo", "yes");
                        intent.putExtra("select_date", month_select_date);
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

                    }
                });
            }else if("upadte_month".equals(refresh)) {//切换月份
                String change_month = intent.getStringExtra("change_month");
                if(Integer.valueOf(change_month)<=9){
                    date_tv.setText("0"+change_month+"月");
                }else{
                    date_tv.setText(change_month+"月");
                }
            }
        }
    };
    @Override
    public void onDetach() {
        super.onDetach();
        broadcastManager.unregisterReceiver(mRefreshReceiver);

    }



    /*public static Dialog m_dialog;
    private void select_data_popwindow() {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        View view = factory.inflate(R.layout.daywhatch_rili_view, null);
        m_dialog = new Dialog(getActivity(), R.style.transparentFrameWindowStyle2);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        m_dialog.setContentView(view, params);
        Window window = m_dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams params1 = window.getAttributes();
        params1.x = 0;
        params1.y = -((ScreenUtilsHelper.getScreenHeight(getActivity())/4));
//        params1.width = ScreenUtilsHelper.getScreenWidth(getActivity());
//        params1.height = ScreenUtilsHelper.getScreenHeight(getActivity());
        window.setAttributes(params1);


        // 设置点击外围解散
        m_dialog.setCanceledOnTouchOutside(true);
        m_dialog.show();
        */
    /**
     * 操作
     *//*
        CalendarView calendarView = view.findViewById(R.id.calendar);
        TextView title = (TextView) view.findViewById(R.id.title);
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList<>();
        //list.add("2017.11.11");
        calendarView
                .setStartEndDate("2017.1", "2019.12")
                .setDisableStartEndDate("2017.10.7", "2019.10.7")
                .setInitDate("2017.11")
                //.setMultiDate(list)
                .init();
        title.setText(2017 + "年" + 11 + "月");
        LinearLayout last_btn = view.findViewById(R.id.last_btn);
        last_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.lastMonth();
            }
        });
        LinearLayout next_btn = view.findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.nextMonth();
            }
        });
        calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                m_dialog.dismiss();

            }
        });
        calendarView.setOnMultiChooseListener(new OnMultiChooseListener() {
            @Override
            public void onMultiChoose(View view, DateBean date, boolean flag) {
                String d = date.getSolar()[0] + "." + date.getSolar()[1] + "." + date.getSolar()[2] + ".";
                if (flag) {//选中
                    sb.append("选中：" + d + "\n");

                } else {//取消选中
                    sb.append("取消：" + d + "\n");
                }
                //chooseDate.setText(sb.toString());

                //test
                if (flag) {
                    for (DateBean db : calendarView.getMultiDate()) {
                        Log.e("date:", "" + db.getSolar()[0] + db.getSolar()[1] + db.getSolar()[2]);
                    }
                }
            }
        });

        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "年" + date[1] + "月");
            }
        });

    }*/


    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @param platform 平台类型
         * @descrption 分享开始的回调
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @param platform 平台类型
         * @descrption 分享成功的回调
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), "分享成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @param platform 平台类型
         * @param t        错误原因
         * @descrption 分享失败的回调
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(), "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @param platform 平台类型
         * @descrption 分享取消的回调
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), "取消了", Toast.LENGTH_LONG).show();

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void fetchData() {

    }
}