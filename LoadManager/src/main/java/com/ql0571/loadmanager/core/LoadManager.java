package com.ql0571.loadmanager.core;

import com.ql0571.loadmanager.callback.LoadCallback;
import com.ql0571.loadmanager.utils.LoadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * <p>
 * author: qiulie
 * date: 2017/9/29
 */

public class LoadManager {

    private Builder builder;
    private static volatile LoadManager instance;

    public static LoadManager getDefault() {
        if (instance == null) {
            synchronized (LoadManager.class) {
                if (instance == null) {
                    instance = new LoadManager();
                }
            }
        }
        return instance;
    }

    private LoadManager() {
        this.builder = new Builder();
    }

    private LoadManager(Builder builder) {
        this.builder = builder;
    }

    public LoadService register(Object target, LoadCallback.OnReloadListener onReloadListener) {
        TargetContext targetContext = LoadUtil.getTargetContext(target);
        return new LoadService(targetContext, onReloadListener, builder);
    }

    public LoadService register(Object target) {
        return register(target, null);
    }

    public static Builder beginBuilder() {
        return new Builder();
    }

    private void setBuilder(Builder builder) {
        this.builder = builder;
    }

    public static class Builder {
        private List<LoadCallback> callbacks = new ArrayList<>();
        private Class<? extends LoadCallback> defaultCallback;

        public Builder addCallback(LoadCallback callback) {
            callbacks.add(callback);
            return this;
        }

        public Builder setDefaultCallback(Class<? extends LoadCallback> defaultCallback) {
            this.defaultCallback = defaultCallback;
            return this;
        }

        List<LoadCallback> getCallbacks() {
            return callbacks;
        }

        Class<? extends LoadCallback> getDefaultCallback() {
            return defaultCallback;
        }

        public void commit() {
            getDefault().setBuilder(this);
        }

        public LoadManager build() {
            return new LoadManager(this);
        }

    }
}
