package com.ycuwq.datepicker.time;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import com.ycuwq.datepicker.R;


/**
 * HourAndMinutePicker
 * Created by ycuwq on 2018/1/22.
 */
public class WeightPicker extends LinearLayout implements
        Weight1Picker.OnDaySelectedListener, Weight2Picker.OnDaySelectedListener {

    private Weight1Picker mWeight1Picker;
    private Weight2Picker mWeight2Picker;
    private OnTimeSelectedListener mOnTimeSelectedListener;

    public WeightPicker(Context context) {
        this(context, null);
    }

    public WeightPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeightPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_time, this);
        initChild();
        initAttrs(context, attrs);
        mWeight1Picker.setTextPosition(0);
        mWeight2Picker.setTextPosition(2);
        mWeight1Picker.setBackgroundDrawable(getBackground());
        mWeight2Picker.setBackgroundDrawable(getBackground());
    }

    @Override
    public void onHourSelected(int hour) {
        onTimeSelected();
    }

    @Override
    public void onMinuteSelected(int hour) {
        onTimeSelected();
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HourAndMinutePicker);
        int textSize = a.getDimensionPixelSize(R.styleable.HourAndMinutePicker_itemTextSize,
                getResources().getDimensionPixelSize(R.dimen.WheelItemTextSize));
        int textColor = a.getColor(R.styleable.HourAndMinutePicker_itemTextColor,
                Color.BLACK);
        boolean isTextGradual = a.getBoolean(R.styleable.HourAndMinutePicker_textGradual, true);
        boolean isCyclic = a.getBoolean(R.styleable.HourAndMinutePicker_wheelCyclic, true);
        int halfVisibleItemCount = a.getInteger(R.styleable.HourAndMinutePicker_halfVisibleItemCount, 2);
        int selectedItemTextColor = a.getColor(R.styleable.HourAndMinutePicker_selectedTextColor,
                getResources().getColor(R.color.com_ycuwq_datepicker_selectedTextColor));
        int selectedItemTextSize = a.getDimensionPixelSize(R.styleable.HourAndMinutePicker_selectedTextSize,
                getResources().getDimensionPixelSize(R.dimen.WheelSelectedItemTextSize));
        int itemWidthSpace = a.getDimensionPixelSize(R.styleable.HourAndMinutePicker_itemWidthSpace,
                getResources().getDimensionPixelOffset(R.dimen.WheelItemWidthSpace));
        int itemHeightSpace = a.getDimensionPixelSize(R.styleable.HourAndMinutePicker_itemHeightSpace,
                getResources().getDimensionPixelOffset(R.dimen.WheelItemHeightSpace));
        boolean isZoomInSelectedItem = a.getBoolean(R.styleable.HourAndMinutePicker_zoomInSelectedItem, true);
        boolean isShowCurtain = a.getBoolean(R.styleable.HourAndMinutePicker_wheelCurtain, true);
        int curtainColor = a.getColor(R.styleable.HourAndMinutePicker_wheelCurtainColor, Color.WHITE);
        boolean isShowCurtainBorder = a.getBoolean(R.styleable.HourAndMinutePicker_wheelCurtainBorder, true);
        int curtainBorderColor = a.getColor(R.styleable.HourAndMinutePicker_wheelCurtainBorderColor,
                getResources().getColor(R.color.com_ycuwq_datepicker_divider));
        a.recycle();

        setTextSize(textSize);
        setTextColor(textColor);
        setTextGradual(isTextGradual);
        setCyclic(isCyclic);
        setHalfVisibleItemCount(halfVisibleItemCount);
        setSelectedItemTextColor(selectedItemTextColor);
        setSelectedItemTextSize(selectedItemTextSize);
        setItemWidthSpace(itemWidthSpace);
        setItemHeightSpace(itemHeightSpace);
        setZoomInSelectedItem(isZoomInSelectedItem);
        setShowCurtain(isShowCurtain);
        setCurtainColor(curtainColor);
        setShowCurtainBorder(isShowCurtainBorder);
        setCurtainBorderColor(curtainBorderColor);
    }
    private void initChild() {
        mWeight1Picker = findViewById(R.id.hourPicker_layout_time);
        mWeight1Picker.setOnDaySelectedListener(this);
        mWeight2Picker = findViewById(R.id.minutePicker_layout_time);
        mWeight2Picker.setOnDaySelectedListener(this);

    }

    private void onTimeSelected() {
        if (mOnTimeSelectedListener != null) {
            mOnTimeSelectedListener.onTimeSelected(getHour(), getMinute());
        }
    }


    /**
     * Sets time.
     *
     * @param hour         the year
     * @param minute        the month
     */
    public void setTime(int hour, int minute) {
        setTime(hour, minute, true);
    }

    /**
     * Sets time.
     *
     * @param hour         the year
     * @param minute        the month
     * @param smoothScroll the smooth scroll
     */
    public void setTime(int hour, int minute, boolean smoothScroll) {
        mWeight1Picker.setSelectedDay(hour, smoothScroll);
        mWeight2Picker.setSelectedDay(minute, smoothScroll);
    }

    /**
     * Gets hour.
     *
     * @return the hour
     */
    public int getHour() {
        return mWeight1Picker.getSelectedDay();
    }


    /**
     * Gets minuute.
     *
     * @return the minute
     */
    public int getMinute() {
        return mWeight2Picker.getSelectedDay();
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        if (mWeight1Picker != null) {
            mWeight1Picker.setBackgroundColor(color);
        }
        if (mWeight2Picker != null) {
            mWeight2Picker.setBackgroundColor(color);
        }
    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
        if (mWeight1Picker != null) {
            mWeight1Picker.setBackgroundResource(resid);
        }
        if (mWeight2Picker != null) {
            mWeight2Picker.setBackgroundResource(resid);
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        if (mWeight1Picker != null) {
            mWeight1Picker.setBackgroundDrawable(background);
        }
        if (mWeight2Picker != null) {
            mWeight2Picker.setBackgroundDrawable(background);
        }
    }

    public Weight1Picker getHourPicker() {
        return mWeight1Picker;
    }

    public Weight2Picker getMinutePicker() {
        return mWeight2Picker;
    }

    /**
     * 一般列表的文本颜色
     *
     * @param textColor 文本颜色
     */
    public void setTextColor(@ColorInt int textColor) {
        mWeight1Picker.setTextColor(textColor);
        mWeight2Picker.setTextColor(textColor);
    }

    /**
     * 一般列表的文本大小
     *
     * @param textSize 文字大小
     */
    public void setTextSize(int textSize) {
        mWeight1Picker.setTextSize(textSize);
        mWeight2Picker.setTextSize(textSize);
    }

    /**
     * 设置被选中时候的文本颜色
     *
     * @param selectedItemTextColor 文本颜色
     */
    public void setSelectedItemTextColor(@ColorInt int selectedItemTextColor) {
        mWeight1Picker.setSelectedItemTextColor(selectedItemTextColor);
        mWeight2Picker.setSelectedItemTextColor(selectedItemTextColor);
    }

    /**
     * 设置被选中时候的文本大小
     *
     * @param selectedItemTextSize 文字大小
     */
    public void setSelectedItemTextSize(int selectedItemTextSize) {
        mWeight1Picker.setSelectedItemTextSize(selectedItemTextSize);
        mWeight2Picker.setSelectedItemTextSize(selectedItemTextSize);
    }


    /**
     * 设置显示数据量的个数的一半。
     * 为保证总显示个数为奇数,这里将总数拆分，itemCount = mHalfVisibleItemCount * 2 + 1
     *
     * @param halfVisibleItemCount 总数量的一半
     */
    public void setHalfVisibleItemCount(int halfVisibleItemCount) {
        mWeight1Picker.setHalfVisibleItemCount(halfVisibleItemCount);
        mWeight2Picker.setHalfVisibleItemCount(halfVisibleItemCount);
    }

    /**
     * Sets item width space.
     *
     * @param itemWidthSpace the item width space
     */
    public void setItemWidthSpace(int itemWidthSpace) {
        mWeight1Picker.setItemWidthSpace(itemWidthSpace);
        mWeight2Picker.setItemWidthSpace(itemWidthSpace);
    }

    /**
     * 设置两个Item之间的间隔
     *
     * @param itemHeightSpace 间隔值
     */
    public void setItemHeightSpace(int itemHeightSpace) {
        mWeight1Picker.setItemHeightSpace(itemHeightSpace);
        mWeight2Picker.setItemHeightSpace(itemHeightSpace);
    }


    /**
     * Set zoom in center item.
     *
     * @param zoomInSelectedItem the zoom in center item
     */
    public void setZoomInSelectedItem(boolean zoomInSelectedItem) {
        mWeight1Picker.setZoomInSelectedItem(zoomInSelectedItem);
        mWeight2Picker.setZoomInSelectedItem(zoomInSelectedItem);
    }

    /**
     * 设置是否循环滚动。
     * set wheel cyclic
     * @param cyclic 上下边界是否相邻
     */
    public void setCyclic(boolean cyclic) {
        mWeight1Picker.setCyclic(cyclic);
        mWeight2Picker.setCyclic(cyclic);
    }

    /**
     * 设置文字渐变，离中心越远越淡。
     * Set the text color gradient
     * @param textGradual 是否渐变
     */
    public void setTextGradual(boolean textGradual) {
        mWeight1Picker.setTextGradual(textGradual);
        mWeight2Picker.setTextGradual(textGradual);
    }


    /**
     * 设置中心Item是否有幕布遮盖
     * set the center item curtain cover
     * @param showCurtain 是否有幕布
     */
    public void setShowCurtain(boolean showCurtain) {
        mWeight1Picker.setShowCurtain(showCurtain);
        mWeight2Picker.setShowCurtain(showCurtain);
    }

    /**
     * 设置幕布颜色
     * set curtain color
     * @param curtainColor 幕布颜色
     */
    public void setCurtainColor(@ColorInt int curtainColor) {
        mWeight1Picker.setCurtainColor(curtainColor);
        mWeight2Picker.setCurtainColor(curtainColor);
    }

    /**
     * 设置幕布是否显示边框
     * set curtain border
     * @param showCurtainBorder 是否有幕布边框
     */
    public void setShowCurtainBorder(boolean showCurtainBorder) {
        mWeight1Picker.setShowCurtainBorder(showCurtainBorder);
        mWeight2Picker.setShowCurtainBorder(showCurtainBorder);
    }

    /**
     * 幕布边框的颜色
     * curtain border color
     * @param curtainBorderColor 幕布边框颜色
     */
    public void setCurtainBorderColor(@ColorInt int curtainBorderColor) {
        mWeight1Picker.setCurtainBorderColor(curtainBorderColor);
        mWeight2Picker.setCurtainBorderColor(curtainBorderColor);
    }

    /**
     * 设置选择器的指示器文本
     * set indicator text
     * @param hourText  小时指示器文本
     * @param minuteText 分钟指示器文本

     */
    public void setIndicatorText(String hourText, String minuteText) {
        mWeight1Picker.setIndicatorText(hourText);
        mWeight2Picker.setIndicatorText(minuteText);
    }

    /**
     * 设置指示器文字的颜色
     * set indicator text color
     * @param textColor 文本颜色
     */
    public void setIndicatorTextColor(@ColorInt int textColor) {
        mWeight1Picker.setIndicatorTextColor(textColor);
        mWeight2Picker.setIndicatorTextColor(textColor);
    }

    /**
     * 设置指示器文字的大小
     *  indicator text size
     * @param textSize 文本大小
     */
    public void setIndicatorTextSize(int textSize) {
        mWeight1Picker.setTextSize(textSize);
        mWeight2Picker.setTextSize(textSize);
    }

    /**
     * Sets on date selected listener.
     *
     * @param onTimeSelectedListener the on time selected listener
     */
    public void setOnTimeSelectedListener(OnTimeSelectedListener onTimeSelectedListener) {
        mOnTimeSelectedListener = onTimeSelectedListener;
    }

    /**
     * The interface On date selected listener.
     */
    public interface OnTimeSelectedListener {
        /**
         * On time selected.
         *
         * @param hour  the hour
         * @param minute the minute
         */
        void onTimeSelected(int hour, int minute);
    }
}
