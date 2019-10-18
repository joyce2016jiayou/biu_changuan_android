package com.noplugins.keepfit.android.util;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class PercentFormatter extends ValueFormatter {

    protected DecimalFormat mFormat;

    public PercentFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0");
    }

    /**
     * Allow a custom decimalformat
     *
     * @param format
     */
    public PercentFormatter(DecimalFormat format) {
        this.mFormat = format;
    }

    // ValueFormatter
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex,
                                    ViewPortHandler viewPortHandler) {
        return mFormat.format(value) + " %";
    }

    // YAxisValueFormatter
//    @Override
//    public String getFormattedValue(float value, YAxis yAxis) {
//        return mFormat.format(value) + " %";
//    }
}