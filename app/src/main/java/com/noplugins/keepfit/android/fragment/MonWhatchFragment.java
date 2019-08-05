package com.noplugins.keepfit.android.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.ClassEntity;
import com.noplugins.keepfit.android.util.data.DateHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;
import com.othershe.calendarview.bean.MothEntity;
import com.othershe.calendarview.weiget.CalenDarView2;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import rx.Subscription;

import static com.umeng.socialize.net.dplus.CommonNetImpl.TAG;


public class MonWhatchFragment extends ViewPagerFragment {

    private View view;
    @BindView(R.id.calendar)
    CalenDarView2 calendarView;
    @BindView(R.id.title)
    TextView title_tv;
    @BindView(R.id.next_btn)
    LinearLayout next_btn;
    @BindView(R.id.last_btn)
    LinearLayout last_btn;

    private int[] cDate = CalendarUtil.getCurrentDate();
    private String change_month;
    List<MothEntity.DataBean> dataBeans = new ArrayList<>();

    public static MonWhatchFragment newInstance(String title) {
        MonWhatchFragment fragment = new MonWhatchFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mon_whatch, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();

        }

        return view;
    }

    private void getDates() {
        Map<String, Object> params = new HashMap<>();
        //当前的月份
        if(cDate[1]<=9){
            params.put("month",cDate[0]+"0"+cDate[1]);
        }else{
            params.put("month",""+cDate[0]+cDate[1]);
        }
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Log.e(TAG, "获取月视角参数：" + json_params);

        Subscription subscription = Network.getInstance("获取月视角", getActivity())
                .get_month_view(requestBody, new ProgressSubscriberNew<>(MothEntity.class, new GsonSubscriberOnNextListener<MothEntity>() {
                    @Override
                    public void on_post_entity(MothEntity entity, String s) {
                        dataBeans = entity.getData();
                        Log.e("获取月视角成功", entity.getData().size() + "获取月视角成功" + s);
                        for(int i=0;i<entity.getData().size();i++){
                            Log.e("MothEntity数据Days",entity.getData().get(i).getDays()+"");
                            Log.e("MothEntity数据Count",entity.getData().get(i).getCount()+"");
                            Log.e("MothEntity数据Num",entity.getData().get(i).getNum()+"");
                        }
                        calendarView
                                .setStartEndDate("2019.01", "2025.12")
                                .setDisableStartEndDate("2019.01.01", "2025.12.30")
                                .setInitDate(cDate[0] + "." + cDate[1])
                                .setSingleDate(cDate[0] + "." + cDate[1] + "." + cDate[2])
                                .init(dataBeans);
                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {

                    }

                    @Override
                    public void onError(String error) {
                        Log.e("获取月视角失败", "获取月视角失败:" + error);
                    }
                }, getActivity(), true));
    }

    private void initView() {
        getDates();

        //设置title
        title_tv.setText(cDate[0] + "年" + cDate[1] + "月");
        LinearLayout last_btn = view.findViewById(R.id.last_btn);
        last_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.lastMonth();
                //通知主fragement更新选择的月份
               /* Intent intent = new Intent("update_refresh");
                intent.putExtra("enter_of_month_view", "upadte_month");
                intent.putExtra("change_month",change_month);
                LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).sendBroadcast(intent);*/
            }
        });
        LinearLayout next_btn = view.findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.nextMonth();
                //通知主fragement更新选择的月份
            /*    Intent intent = new Intent("update_refresh");
                intent.putExtra("enter_of_month_view", "upadte_month");
                intent.putExtra("change_month",change_month);
                LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).sendBroadcast(intent);*/
            }
        });
        calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                //日视角刷新数据
                String select_date = DateHelper.get_date(date.getSolar()[0], date.getSolar()[1], date.getSolar()[2]);
                Log.e("选择的日期", select_date);
                Intent intent = new Intent("update_refresh");
                intent.putExtra("enter_of_month_view", "yes");
                intent.putExtra("mselect_date", select_date);
                intent.putExtra("select_year", date.getSolar()[0] + "");
                intent.putExtra("select_month", date.getSolar()[1] + "");
                intent.putExtra("select_date", date.getSolar()[2] + "");

                LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).sendBroadcast(intent);


            }
        });


        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title_tv.setText(date[0] + "年" + date[1] + "月");
                change_month = date[1] + "";
            }
        });


    }


    @Override
    public void fetchData() {

    }
}
