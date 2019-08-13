package com.noplugins.keepfit.android.util;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.noplugins.keepfit.android.entity.TimeSelectEntity;

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
                                  List<TimeSelectEntity> timeEntities,
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
                    TimeSelectEntity timeEntity =
                            new TimeSelectEntity();
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
     * 选择开始时间 时分
     * @param activity
     * @param picker
     * @param textView
     */
    public static void time_check(FragmentActivity activity, TimePicker picker,
                                  TextView textView,TextView endView,List<TimeSelectEntity> timeEntities) {
        picker = new TimePicker(activity, TimeMode.HOUR_24);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        picker.setDefaultValue(new TimeEntity(hour, minute));
        picker.showAtBottom();
        picker.setOnTimeSelectedListener(new OnTimeSelectedListener() {
            @Override
            public void onItemSelected(int hour, int minute, int second) {
                if ( TimeCheckUtil.isCoincide(timeEntities,hour + ":" + minute)){
                    Toast.makeText(activity,"时间不能重复！",Toast.LENGTH_SHORT).show();
                } else {
                    if (minute <= 9) {
                        textView.setText(hour + ":0" + minute);
                    } else {
                        textView.setText(hour + ":" + minute);
                    }
                    endView.setText("请选择");
                }
            }
        });
    }

    /**
     * 选择结束时间 时分
     * @param activity
     * @param picker
     * @param textView
     * @param startHour
     * @param startMinute
     */
    public static void time_check(FragmentActivity activity, TimePicker picker, TextView textView,
                                  String startHour,String startMinute,List<TimeSelectEntity> timeEntities) {
        Log.d("tag_onItemClick",startHour +"  "+startMinute);
        picker = new TimePicker(activity, TimeMode.HOUR_24);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        picker.setRange(new TimeEntity(Integer.parseInt(startHour),Integer.parseInt(startMinute)),
                new TimeEntity(23,59));
        picker.setDefaultValue(new TimeEntity(hour, minute));
        picker.showAtBottom();
        picker.setOnTimeSelectedListener(new OnTimeSelectedListener() {
            @Override
            public void onItemSelected(int hour, int minute, int second) {

                if (TimeCheckUtil.isCoincide(timeEntities,startHour+":"+startMinute,
                        hour + ":" + minute)){
                    Toast.makeText(activity,"时间不能重复！",Toast.LENGTH_SHORT).show();
                } else {
                    if (minute <= 9) {
                        textView.setText(hour + ":0" + minute);
                    } else {
                        textView.setText(hour + ":" + minute);
                    }
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

    public static void select_month(FragmentActivity activity, DatePicker datePicker, TextView textView){
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
                    textView.setText(year+"-0" + month);
                } else {
                    textView.setText(year+"-" + month);
                }
            }
        });

    }
}
