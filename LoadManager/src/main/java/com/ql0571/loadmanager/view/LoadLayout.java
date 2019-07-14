package com.ql0571.loadmanager.view;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.ql0571.loadmanager.callback.LoadCallback;
import com.ql0571.loadmanager.core.LoadManagerException;
import com.ql0571.loadmanager.utils.LoadUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * desc:
 * <p>
 * author: qiulie
 * date: 2017/9/29
 */

public class LoadLayout extends FrameLayout {

    private Map<Class<? extends LoadCallback>, LoadCallback> callbacks = new HashMap<>();
    private Context context;
    private LoadCallback.OnReloadListener onReloadListener;

    public LoadLayout(@NonNull Context context) {
        super(context);
    }

    public LoadLayout(@NonNull Context context, LoadCallback.OnReloadListener onReloadListener) {
        this(context);
        this.context = context;
        this.onReloadListener = onReloadListener;
    }

    public void setupCallback(LoadCallback callback) {

        try {
            LoadCallback cloneCallback = callback.clone();
            cloneCallback.setCallback(null, context, onReloadListener);
            addCallback(cloneCallback);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }

    public void addCallback(LoadCallback callback) {
        if (!callbacks.containsKey(callback.getClass())) {
            callbacks.put(callback.getClass(), callback);
        }
    }

    public void showCallback(final Class<? extends LoadCallback> callback) {
        if (!callbacks.containsKey(callback)) {
            throw new LoadManagerException("callback 未找到："+callback.getName());
        }
        if (LoadUtil.isMainThread()) {
            showCallbackView(callback);
        } else {
            postToMainThread(callback);
        }
    }

    private void postToMainThread(final Class<? extends LoadCallback> status) {
        post(new Runnable() {
            @Override
            public void run() {
                showCallbackView(status);
            }
        });
    }

    private void showCallbackView(Class<? extends LoadCallback> status) {
        if (getChildCount() > 0) {
            removeAllViews();
        }
        for (Class key : callbacks.keySet()) {
            if (key == status) {
                addView(callbacks.get(key).getRootView());
            }
        }
    }
}
