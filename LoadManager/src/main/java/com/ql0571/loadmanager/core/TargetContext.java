package com.ql0571.loadmanager.core;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * desc:
 * <p>
 * author: qiulie
 * date: 2017/9/29
 */

public class TargetContext {
    private Context context;
    private ViewGroup parentView;
    private View oldContent;
    private int childIndex;

    public TargetContext(Context context, ViewGroup parentView, View oldContent, int childIndex) {
        this.context = context;
        this.parentView = parentView;
        this.oldContent = oldContent;
        this.childIndex = childIndex;
    }

    public Context getContext() {
        return context;
    }

    View getOldContent() {
        return oldContent;
    }

    int getChildIndex() {
        return childIndex;
    }

    ViewGroup getParentView() {
        return parentView;
    }
}
