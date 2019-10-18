package com.noplugins.keepfit.android.util.ui.chart;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.highlight.BarHighlighter;

public class MyBarChart extends BarChart {

    private float one = 0.25f;
    private float two = 0.25f;
    private float three = 0.5f;
    public MyBarChart(Context context) {
        super(context);
    }

    public MyBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        mRenderer = new MyBarChartRenderer(this, mAnimator, mViewPortHandler);

        setHighlighter(new BarHighlighter(this));

        getXAxis().setSpaceMin(0.5f);
        getXAxis().setSpaceMax(0.5f);
    }

    

}