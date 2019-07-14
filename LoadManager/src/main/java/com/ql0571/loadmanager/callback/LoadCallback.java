package com.ql0571.loadmanager.callback;

import android.content.Context;
import android.view.View;

import java.io.Serializable;

/**
 * desc:
 * <p>
 * author: qiulie
 * date: 2017/9/29
 */

public abstract class LoadCallback implements Serializable, Cloneable {

    private View rootView;
    private Context context;
    private OnReloadListener onReloadListener;

    public LoadCallback() {
    }

    LoadCallback(View view, Context context, OnReloadListener onReloadListener) {
        this.rootView = view;
        this.context = context;
        this.onReloadListener = onReloadListener;
    }

    public LoadCallback setCallback(View view, Context context, OnReloadListener onReloadListener) {
        this.rootView = view;
        this.context = context;
        this.onReloadListener = onReloadListener;
        return this;
    }

    public View getRootView() {
        int resId = onCreateView();
        if (resId == 0 && rootView != null) {
            return rootView;
        }
        rootView = View.inflate(context, onCreateView(), null);

        View reloadView = setReloadView(rootView);
        if (reloadView != null) {
            reloadView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onReloadListener != null) {
                        onReloadListener.onReload(v);
                    }
                }
            });
        }
        View backView = setBackView(rootView);
        if (backView != null) {
            backView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onReloadListener != null) {
                        onReloadListener.onClose(v);
                    }
                }
            });
        }
        return rootView;
    }

    protected View setReloadView(View rootView) {
        return null;
    }

    protected View setBackView(View rootView) {
        return null;
    }

    public interface OnReloadListener {
        void onReload(View v);
        void onClose(View v);
    }

    protected abstract int onCreateView();

    @Override
    public LoadCallback clone() throws CloneNotSupportedException {
        return (LoadCallback) super.clone();
    }
}
