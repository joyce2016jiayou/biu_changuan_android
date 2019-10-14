package com.noplugins.keepfit.android.util.ui.pop;

import android.view.View;

/**
 * Created by ThinkPad on 2017/12/8.
 */

public class CommonUtil {
    public static void measureWidthAndHeight(View view) {
        int widthMode = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMode = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMode, heightMode);
    }
}
