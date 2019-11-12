package com.noplugins.keepfit.android.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.activity.AddClassActivity;
import com.noplugins.keepfit.android.adapter.ClassAdapter;
import com.noplugins.keepfit.android.adapter.TypeAdapter;
import com.noplugins.keepfit.android.bean.CalenderEntity;
import com.noplugins.keepfit.android.bean.DateViewEntity;
import com.noplugins.keepfit.android.bean.DictionaryeBean;
import com.noplugins.keepfit.android.bean.RiChengBean;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.data.DateHelper;
import com.noplugins.keepfit.android.util.eventbus.MessageEvent;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;
import com.noplugins.keepfit.android.util.ui.erweima.android.CaptureActivity;
import com.noplugins.keepfit.android.util.ui.erweima.bean.ZxingConfig;
import com.noplugins.keepfit.android.util.ui.erweima.common.Constant;
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.weiget.CalendarView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.socialize.UMShareAPI;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import rx.Subscription;
import www.linwg.org.lib.LCardView;

import static android.app.Activity.RESULT_OK;
import static com.umeng.socialize.net.dplus.CommonNetImpl.TAG;
import static com.umeng.socialize.utils.ContextUtil.getPackageName;


public class RiChengFragment extends ViewPagerFragment {
    @BindView(R.id.select_store_type)
    RelativeLayout select_store_type;
    @BindView(R.id.store_type_tv)
    TextView store_type_tv;
    @BindView(R.id.clander_layout)
    LCardView clander_layout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.calendar)
    CalendarView calendar;
    @BindView(R.id.last_btn)
    LinearLayout last_btn;
    @BindView(R.id.next_btn)
    LinearLayout next_btn;
    @BindView(R.id.title_layout)
    LinearLayout title_layout;
    @BindView(R.id.xiala_img)
    ImageView xiala_img;
    @BindView(R.id.date_tv)
    TextView date_tv;
    @BindView(R.id.week_tv)
    TextView week_tv;
    @BindView(R.id.saoma_btn)
    ImageView saoma_btn;
    @BindView(R.id.more_btn)
    LinearLayout more_btn;
    @BindView(R.id.status_tv)
    TextView status_tv;
    @BindView(R.id.types_tv)
    TextView types_tv;
    @BindView(R.id.select_status_btn)
    RelativeLayout select_status_btn;
    @BindView(R.id.select_types_btn)
    RelativeLayout select_types_btn;
    @BindView(R.id.class_recycler_view)
    RecyclerView class_recycler_view;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private String select_date_str = "";
    private String select_coursetype_str = "";
    private String select_courcestatus_str = "";
    private View view;
    private int[] cDate;
    private int REQUEST_CODE_SCAN = 111;
    List<String> dates = new ArrayList<>();

    List<DictionaryeBean> select_types = new ArrayList<>();
    List<DictionaryeBean> select_status = new ArrayList<>();
    List<RiChengBean.ResultBean> class_list = new ArrayList<>();

    public static RiChengFragment homeInstance(String title) {
        RiChengFragment fragment = new RiChengFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_ri_cheng, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            cDate = CalendarUtil.getCurrentDate();
            EventBus.getDefault().register(this);

            initview();
        }
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void refresh(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("refresh_resource")) {
            refreshLayout.autoRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    private void initview() {
        select_date_str = DateHelper.get_date(cDate[0], cDate[1], cDate[2]);
        date_tv.setText(select_date_str);
        week_tv.setText(DateHelper.getWeek(select_date_str));
        //获取日历数据
        get_rili_resouce();
        //获取类型和状态
        get_types();
        //获取课程数据
        init_class_date_resource();


        select_store_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_store_pop();
            }
        });

        title_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clander_layout.getVisibility() == View.VISIBLE) {
                    xiala_img.animate().rotation(180);
                    clander_layout.setVisibility(View.GONE);
                    last_btn.setVisibility(View.INVISIBLE);
                    next_btn.setVisibility(View.INVISIBLE);
                } else {
                    xiala_img.animate().rotation(0);
                    clander_layout.setVisibility(View.VISIBLE);
                    last_btn.setVisibility(View.VISIBLE);
                    next_btn.setVisibility(View.VISIBLE);
                }
            }
        });
        //扫码进场
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
        more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddClassActivity.class);
                getActivity().startActivity(intent);
            }
        });
        //选择状态
        select_status_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_status_pop();
            }
        });
        //选择类型
        select_types_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_types_pop();
            }
        });

        //禁用加载
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //获取日历数据
                get_rili_resouce();
                //获取类型和状态
                get_types();
                //获取课程数据
                init_class_date_resource();

            }
        });
    }


    private void get_rili_resouce() {

        Map<String, Object> params1 = new HashMap<>();
        params1.put("areaNum", SpUtils.getString(getActivity(), AppConstants.CHANGGUAN_NUM));//todo替换场馆编号
        params1.put("date", select_date_str);
        if (select_coursetype_str.length() > 0) {
            params1.put("courseType", select_coursetype_str);
        }
        if (select_courcestatus_str.length() > 0) {
            params1.put("courseStatus", select_courcestatus_str);
        }
        Subscription subscription = Network.getInstance("获取日历数据", getActivity())
                .get_shouye_date(params1,
                        new ProgressSubscriber<>("获取日历数据", new SubscriberOnNextListener<Bean<RiChengBean>>() {
                            @Override
                            public void onNext(Bean<RiChengBean> result) {
                                //设置日历数据
                                if (dates.size() > 0) {
                                    dates.clear();
                                }
                                for (int i = 0; i < result.getData().getMonth().size(); i++) {
                                    if (result.getData().getMonth().get(i) == null) {
                                        continue;
                                    }
                                    dates.add(result.getData().getMonth().get(i).getDays());
                                }
                                init_clander_view(dates);
                            }

                            @Override
                            public void onError(String error) {
                                refreshLayout.finishRefresh(true);

                            }
                        }, getActivity(), false));
    }


    private void get_types() {
        Map<String, Object> params = new HashMap<>();
        params.put("object", "9");
        Subscription subscription = Network.getInstance("获取类型", getActivity())
                .get_types(params,
                        new ProgressSubscriber<>("获取类型", new SubscriberOnNextListener<Bean<List<DictionaryeBean>>>() {
                            @Override
                            public void onNext(Bean<List<DictionaryeBean>> result) {
                                if (select_types.size() > 0) {
                                    select_types.clear();
                                }
                                select_types.addAll(result.getData());
                                //然后获取状态
                                get_status();
                            }

                            @Override
                            public void onError(String error) {
                                refreshLayout.finishRefresh(true);

                            }
                        }, getActivity(), false));
    }

    private void get_status() {
        Map<String, Object> params = new HashMap<>();
        params.put("object", "8");
        Subscription subscription = Network.getInstance("获取状态", getActivity())
                .get_types(params,
                        new ProgressSubscriber<>("获取状态", new SubscriberOnNextListener<Bean<List<DictionaryeBean>>>() {
                            @Override
                            public void onNext(Bean<List<DictionaryeBean>> result) {
                                select_status.addAll(result.getData());
                                //首次进来默认选择全部

                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), false));
    }

    private void init_class_date_resource() {
        Map<String, Object> params1 = new HashMap<>();
        params1.put("areaNum", SpUtils.getString(getActivity(), AppConstants.CHANGGUAN_NUM));//todo替换场馆编号
        params1.put("date", select_date_str);
        if (select_coursetype_str.length() > 0) {
            params1.put("courseType", select_coursetype_str);
        }
        if (select_courcestatus_str.length() > 0) {
            params1.put("courseStatus", select_courcestatus_str);
        }
        Subscription subscription = Network.getInstance("获取课程数据", getActivity())
                .get_shouye_date(params1,
                        new ProgressSubscriber<>("获取课程数据", new SubscriberOnNextListener<Bean<RiChengBean>>() {
                            @Override
                            public void onNext(Bean<RiChengBean> result) {
                                Log.e("获取课程数据数量", result.getData().getResult().size() + "");
                                if (class_list.size() > 0) {
                                    class_list.clear();
                                }
                                class_list.addAll(result.getData().getResult());
                                //设置课程数据
                                LinearLayoutManager class_linearLayoutManager = new LinearLayoutManager(getActivity());
                                class_recycler_view.setLayoutManager(class_linearLayoutManager);
                                ClassAdapter classAdapter = new ClassAdapter(class_list, RiChengFragment.this);
                                class_recycler_view.setAdapter(classAdapter);
                                classAdapter.setOnItemClickListener(new ClassAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                });
                                refreshLayout.finishRefresh(true);


                            }

                            @Override
                            public void onError(String error) {
                                refreshLayout.finishRefresh(true);

                            }
                        }, getActivity(), false));


    }

    private void init_clander_view(List<String> strings) {
        title.setText(cDate[0] + "年" + cDate[1] + "月");
        calendar.setStartEndDate("2019.01", "2025.12")
                .setDisableStartEndDate("2019.01.01", "2025.12.30")
                .setInitDate(cDate[0] + "." + cDate[1])
                .setSingleDate(cDate[0] + "." + cDate[1] + "." + cDate[2])
                .init(strings);

        calendar.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                //date_tv.setText(DateHelper.get_date2(date.getSolar()[1],date.getSolar()[2]));
                //日视角刷新数据
                select_date_str = DateHelper.get_date(date.getSolar()[0], date.getSolar()[1], date.getSolar()[2]);
                //Log.e("选择的日期", select_date_str);
                date_tv.setText(select_date_str);
                week_tv.setText(DateHelper.getWeek(select_date_str));
                //刷新课程
                init_class_date_resource();
            }
        });
        calendar.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "年" + date[1] + "月");
            }
        });
        last_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.lastMonth();

            }
        });
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.nextMonth();

            }
        });
    }

    private void select_status_pop() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_status_btn.getWidth() - 20,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create();
        //popupWindow.showAsDropDown(select_zhengshu_type);
        popupWindow.showAsDropDown(select_status_btn, 15, -20);
        /**设置逻辑*/
        View view = popupWindow.getContentView();
        List<String> strings = new ArrayList<>();
        strings.add("全部状态");
        for (int i = 0; i < select_status.size(); i++) {
            strings.add(select_status.get(i).getName());
        }
        TypeAdapter typeAdapter = new TypeAdapter(strings, getActivity());
        ListView listView = view.findViewById(R.id.listview);
        listView.setAdapter(typeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                status_tv.setText(strings.get(i));
                if (i == 0) {
                    select_courcestatus_str = "";
                } else {
                    select_courcestatus_str = select_status.get(i - 1).getValue();
                }
                popupWindow.dismiss();
                init_class_date_resource();//重新请求课程
            }
        });
    }

    private void select_types_pop() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_types_btn.getWidth() - 20,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create();
        //popupWindow.showAsDropDown(select_zhengshu_type);
        popupWindow.showAsDropDown(select_types_btn, 15, -20);
        /**设置逻辑*/
        View view = popupWindow.getContentView();
        List<String> strings = new ArrayList<>();
        strings.add("全部类型");
        for (int i = 0; i < select_types.size(); i++) {
            strings.add(select_types.get(i).getName());
        }
        TypeAdapter typeAdapter = new TypeAdapter(strings, getActivity());
        ListView listView = view.findViewById(R.id.listview);
        listView.setAdapter(typeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                types_tv.setText(strings.get(i));
                if (i == 0) {
                    select_coursetype_str = "";
                } else {
                    select_coursetype_str = select_types.get(i - 1).getValue();
                }
                popupWindow.dismiss();
                init_class_date_resource();//重新请求课程
            }
        });
    }

    private void select_store_pop() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_store_type.getWidth() - 20,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create();
        //popupWindow.showAsDropDown(select_zhengshu_type);
        popupWindow.showAsDropDown(select_store_type, 15, -40);
        /**设置逻辑*/
        View view = popupWindow.getContentView();
        List<String> strings = new ArrayList<>();
        strings.add("威尔士");
        strings.add("龙湖天街");
        TypeAdapter typeAdapter = new TypeAdapter(strings, getActivity());
        ListView listView = view.findViewById(R.id.listview);
        listView.setAdapter(typeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                store_type_tv.setText(strings.get(i));
                popupWindow.dismiss();
            }
        });


    }

    @Override
    public void fetchData() {
        get_rili_resouce();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Toast.makeText(getActivity(), "扫码成功", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
