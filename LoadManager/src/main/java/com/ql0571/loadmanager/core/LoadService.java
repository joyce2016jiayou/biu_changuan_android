package com.ql0571.loadmanager.core;

import android.content.Context;
import android.view.View;

import com.ql0571.loadmanager.callback.LoadCallback;
import com.ql0571.loadmanager.callback.SuccessCallback;
import com.ql0571.loadmanager.view.LoadLayout;

import java.util.List;

/**
 * desc:
 * <p>
 * author: qiulie
 * date: 2017/9/29
 */

public class LoadService {
    private LoadLayout loadLayout;

    LoadService(TargetContext targetContext, LoadCallback.OnReloadListener onReloadListener, LoadManager.Builder builder) {
        Context context = targetContext.getContext();
        View oldContent = targetContext.getOldContent();
        loadLayout = new LoadLayout(context, onReloadListener);
        loadLayout.addCallback(new SuccessCallback(oldContent, context, onReloadListener));
        if (targetContext.getParentView() != null) {
            targetContext
                    .getParentView()
                    .addView(loadLayout, targetContext.getChildIndex(), oldContent.getLayoutParams());
        }
        initCallback(builder);
    }

    private void initCallback(LoadManager.Builder builder) {
        List<LoadCallback> callbacks = builder.getCallbacks();
        Class<? extends LoadCallback> defalutCallback = builder.getDefaultCallback();
        if (callbacks != null && callbacks.size() > 0) {
            for (LoadCallback callback : callbacks) {
                loadLayout.setupCallback(callback);
            }
        }
        if (defalutCallback != null) {
            loadLayout.showCallback(defalutCallback);
        }
    }

    public void showSuccess() {
        loadLayout.showCallback(SuccessCallback.class);
    }

    public void showCallback(Class<? extends LoadCallback> callback) {
        loadLayout.showCallback(callback);
    }

//    public void showWithConvertor(T t) {
//        if (convertor == null) {
//            throw new IllegalArgumentException("You haven't set the Convertor.");
//        }
//        loadLayout.showCallback(convertor.map(t));
//    }

    public LoadLayout getLoadLayout() {
        return loadLayout;
    }
}
