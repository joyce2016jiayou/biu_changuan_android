package com.ql0571.loadmanager.callback;

import android.content.Context;
import android.view.View;

/**
 * desc:
 * <p>
 * author: qiulie
 * date: 2017/9/29
 */

public class SuccessCallback extends LoadCallback {

    public SuccessCallback(View view, Context context, OnReloadListener onReloadListener) {
        super(view, context, onReloadListener);
    }

    @Override
    protected int onCreateView() {
        return 0;
    }
}
