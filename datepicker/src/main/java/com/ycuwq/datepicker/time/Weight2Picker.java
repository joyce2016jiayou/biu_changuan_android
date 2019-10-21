package com.ycuwq.datepicker.time;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import com.ycuwq.datepicker.WheelPicker;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * MinutePicker
 * Created by ycuwq on 2018/1/22.
 */
public class Weight2Picker extends WheelPicker<Integer> {
    private int mMinDay, mMaxDay;

    private int mSelectedDay;
    private long mMaxDate, mMinDate;
    private  boolean mIsSetMaxDate;

    private OnDaySelectedListener mOnDaySelectedListener;

    public Weight2Picker(Context context) {
        this(context, null);
    }

    public Weight2Picker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Weight2Picker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setItemMaximumWidthText("0");
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumIntegerDigits(1);
        setDataFormat(numberFormat);

        mMinDay = 0;
        mMaxDay = 9;
        updateDay();
        mSelectedDay = 0;
        setSelectedDay(mSelectedDay, false);
        setOnWheelChangeListener(new OnWheelChangeListener<Integer>() {
            @Override
            public void onWheelSelected(Integer item, int position) {
                mSelectedDay = item;
                if (mOnDaySelectedListener != null) {
                    mOnDaySelectedListener.onMinuteSelected(item);
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
        void onMinuteSelected(int day);
    }
}
