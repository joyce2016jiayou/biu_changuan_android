package com.noplugins.keepfit.android.util;

import android.content.Context;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.util.Calendar;
import java.util.List;

import cn.qqtheme.framework.wheelpicker.DatePicker;
import cn.qqtheme.framework.wheelpicker.TimePicker;
import cn.qqtheme.framework.wheelview.annotation.DateMode;
import cn.qqtheme.framework.wheelview.annotation.TimeMode;
import cn.qqtheme.framework.wheelview.contract.OnDateSelectedListener;
import cn.qqtheme.framework.wheelview.contract.OnTimeSelectedListener;
import cn.qqtheme.framework.wheelview.entity.DateEntity;
import cn.qqtheme.framework.wheelview.entity.TimeEntity;

public class TimePickerUtils {

    public static void time_check(FragmentActivity activity, TimePicker picker, TextView textView,
                                  List<com.noplugins.keepfit.android.entity.TimeEntity> timeEntities,
                                  int position, int type) {
        picker = new TimePicker(activity, TimeMode.HOUR_24);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        picker.setDefaultValue(new TimeEntity(hour, minute));
        picker.showAtBottom();
        picker.setOnTimeSelectedListener(new OnTimeSelectedListener() {
            @Override
            public void onItemSelected(int hour, int minute, int second) {
                if (type == 0){
                    com.noplugins.keepfit.android.entity.TimeEntity timeEntity =
                            new com.noplugins.keepfit.android.entity.TimeEntity();
                    timeEntity.setPosition(position);
                    timeEntity.setStartTimeHour(hour);
                    timeEntity.setStartTimeMinute(minute);
                    timeEntities.add(timeEntity);
                }
                if (type == 1){
                    timeEntities.get(position).setEndTimeHour(hour);
                    timeEntities.get(position).setEndTimeMinute(minute);
                }
                if (minute <= 9) {
                    textView.setText(hour + ":0" + minute);
                } else {
                    textView.setText(hour + ":" + minute);
                }
            }
        });
    }

    /**
     * 选择时间 时分
     * @param activity
     * @param picker
     * @param textView
     */
    public static void time_check(FragmentActivity activity, TimePicker picker, TextView textView) {
        picker = new TimePicker(activity, TimeMode.HOUR_24);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        picker.setDefaultValue(new TimeEntity(hour, minute));
        picker.showAtBottom();
        picker.setOnTimeSelectedListener(new OnTimeSelectedListener() {
            @Override
            public void onItemSelected(int hour, int minute, int second) {

                if (minute <= 9) {
                    textView.setText(hour + ":0" + minute);
                } else {
                    textView.setText(hour + ":" + minute);
                }
            }
        });
    }

    public static void select_date(FragmentActivity activity, DatePicker datePicker, TextView textView){
        DateEntity today = DateEntity.today();
        Calendar calendar = Calendar.getInstance();
        datePicker = new DatePicker(activity, DateMode.YEAR_MONTH);
        datePicker.setRange(today, new DateEntity(calendar.get(Calendar.YEAR)+3, 12, 31));
        datePicker.setDefaultValue(today);
        datePicker.showAtBottom();
        datePicker.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onItemSelected(int year, int month, int day) {
                if (month <= 9) {
                    textView.setText(year+"年0" + month+"月");
                } else {
                    textView.setText(year+"年" + month+"月");
                }
            }
        });

    }
}
