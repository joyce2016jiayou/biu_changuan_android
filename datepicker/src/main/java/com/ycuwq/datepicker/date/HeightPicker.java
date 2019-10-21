package com.ycuwq.datepicker.date;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import com.ycuwq.datepicker.WheelPicker;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 日期选择
 * Created by ycuwq on 17-12-28.
 */
public class HeightPicker extends WheelPicker<Integer>{

    private int mMinDay, mMaxDay;

    private int mSelectedDay;
    private long mMaxDate, mMinDate;
    private  boolean mIsSetMaxDate;

    private OnDaySelectedListener mOnDaySelectedListener;

    public HeightPicker(Context context) {
        this(context, null);
    }

    public HeightPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeightPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
	    setItemMaximumWidthText("00");
	    NumberFormat numberFormat = NumberFormat.getNumberInstance();
	    numberFormat.setMinimumIntegerDigits(2);
	    setDataFormat(numberFormat);

        mMinDay = 100;
        mMaxDay = 230;
        updateDay();
        mSelectedDay = 150;
        setSelectedDay(mSelectedDay, false);
        setOnWheelChangeListener(new OnWheelChangeListener<Integer>() {
	        @Override
	        public void onWheelSelected(Integer item, int position) {
	        	mSelectedDay = item;
		        if (mOnDaySelectedListener != null) {
		        	mOnDaySelectedListener.onDaySelected(item);
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
    	void onDaySelected(int day);
    }
}
