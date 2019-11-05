package com.noplugins.keepfit.android.fragment.statistics;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

public class MyXFormatter extends ValueFormatter {
    private List<String> mValues;

    public MyXFormatter(List<String> values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value) {
        if (((int) value >= 0 && (int) value < mValues.size()))
            return mValues.get((int) value);
        else return "";
    }

    @Override
    public String getPieLabel(float value, PieEntry pieEntry) {
        return getFormattedValue(value);
    }
}