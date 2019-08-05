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

import com.noplugins.keepfit.android.KeepFitActivity;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.util.data.DateHelper;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;
import com.othershe.calendarview.weiget.MonthCalenDarView1;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MonWhatchFragment extends ViewPagerFragment {

    private View view;
    @BindView(R.id.calendar)
    MonthCalenDarView1 calendarView;
    @BindView(R.id.title)
    TextView title_tv;
    @BindView(R.id.next_btn)
    LinearLayout next_btn;
    @BindView(R.id.last_btn)
    LinearLayout last_btn;

    private int[] cDate = CalendarUtil.getCurrentDate();

    private String change_month;

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

    private void initView() {
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
