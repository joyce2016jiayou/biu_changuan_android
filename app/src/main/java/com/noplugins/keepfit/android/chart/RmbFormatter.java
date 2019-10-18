package com.noplugins.keepfit.android.chart;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

/**
 * This IValueFormatter is just for convenience and simply puts a "%" sign after
 * each value. (Recommeded for PieChart)
 *
 * @author Philipp Jahoda
 */
public class RmbFormatter extends ValueFormatter
{

    public DecimalFormat mFormat;
    private PieChart pieChart;

    public RmbFormatter() {
        mFormat = new DecimalFormat("###,###,##0.00");
    }

    // Can be used to remove percent signs if the chart isn't in percent mode
    public RmbFormatter(PieChart pieChart) {
        this();
        this.pieChart = pieChart;
    }

    @Override
    public String getFormattedValue(float value) {
        return "¥"+mFormat.format(value) ;
    }

    @Override
    public String getPieLabel(float value, PieEntry pieEntry) {
        if (pieChart != null && pieChart.isUsePercentValuesEnabled()) {
            // Converted to percent
            return getFormattedValue(value);
        } else {
            // raw value, skip percent sign
            return mFormat.format(value);
        }
    }

}
