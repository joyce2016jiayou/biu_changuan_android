package com.ycuwq.datepicker.time;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import com.ycuwq.datepicker.WheelPicker;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * HourPicker
 * Created by ycuwq on 2018/1/22.
 */
public class Weight1Picker extends WheelPicker<Integer>{

    private int mMinDay, mMaxDay;

    private int mSelectedDay;
    private long mMaxDate, mMinDate;
    private  boolean mIsSetMaxDate;

    private OnDaySelectedListener mOnDaySelectedListener;

    public Weight1Picker(Context context) {
        this(context, null);
    }

    public Weight1Picker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Weight1Picker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setItemMaximumWidthText("00");
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumIntegerDigits(2);
        setDataFormat(numberFormat);

        mMinDay = 20;
        mMaxDay = 200;
        updateDay();
        mSelectedDay = 40;
        setSelectedDay(mSelectedDay, false);
        setOnWheelChangeListener(new OnWheelChangeListener<Integer>() {
            @Override
            public void onWheelSelected(Integer item, int position) {
                mSelectedDay = item;
                if (mOnDaySelectedListener != null) {
                    mOnDaySelectedListener.onHourSelected(item);
                }
            }
        });
    }


    public int getSelectedDay() {
        return mSelectedDay;
    }

    public void setSelectedDay(int selectedDay) {
        setSelectedDay(selectedDay, true);
    }

    public void setSelectedDay(int selectedDay, boolean smoothScroll) {
        setCurrentPosition(selectedDay - mMinDay, smoothScroll);
    }

    public void setMaxDate(long date) {
        mMaxDate = date;
        mIsSetMaxDate = true;
    }

    public void setMinDate(long date) {
        mMinDate = date;
    }

    public void setOnDaySelectedListener(OnDaySelectedListener onDaySelectedListener) {
        mOnDaySelectedListener = onDaySelectedListener;
    }

    private void updateDay() {
        List<Integer> list = new ArrayList<>();
        for (int i = mMinDay; i <= mMaxDay; i++) {
            list.add(i);
        }
        setDataList(list);
    }

    public interface OnDaySelectedListener {
        void onHourSelected(int day);
    }
}
