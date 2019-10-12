package com.noplugins.keepfit.android.util.net.progress;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.entity.Contacts;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressHUD;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;


import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;

import rx.Subscriber;

/**
 * Created by shiyujia02 on 2018/4/12.
 */

public class ProgressSubscriberNew<T> extends Subscriber<Bean<Object>> implements DialogInterface.OnCancelListener {
    private SubscriberOnNextListener<Bean<Object>> mListener;
    private Context mContext;
    private ProgressHUD mProgressHUD;
    private String message;
    private boolean mIsProgress;
    Class<T> entity_class;
    GsonSubscriberOnNextListener<T> gsonSubscriberOnNextListener;

    /**
     * 没有加载框 用于列表刷新加载
     *
     * @param tokenClass
     * @param listener   请求成功 逻辑处理
     * @param context    上下文
     */
    public ProgressSubscriberNew(Class<T> tokenClass, GsonSubscriberOnNextListener<T> mgsonSubscriberOnNextListener,
                                 SubscriberOnNextListener<Bean<Object>> listener, Context context) {
        this.gsonSubscriberOnNextListener = mgsonSubscriberOnNextListener;
        this.mListener = listener;
        this.entity_class = tokenClass;
        this.mContext = context;
        this.mIsProgress = false;
    }

    /**
     * 可设置有加载框 内容显示为 加载中...
     *
     * @param listener   请求成功 逻辑处理
     * @param context    上下文
     * @param isProgress 是否显示进度条
     */
    public ProgressSubscriberNew(Class<T> tClass, GsonSubscriberOnNextListener<T> mgsonSubscriberOnNextListener,
                                 SubscriberOnNextListener<Bean<Object>> listener, Context context, boolean isProgress) {
        // TODO: 2019-09-05
        this.gsonSubscriberOnNextListener = mgsonSubscriberOnNextListener;
        this.entity_class = tClass;
        this.mListener = listener;
        this.mContext = context;
        this.mIsProgress = isProgress;
    }


    /**
     * 可设置有加载框 设置加载内容显示
     *
     * @param listener   请求成功 逻辑处理
     * @param context    上下文
     * @param isProgress 是否显示进度条
     * @param message    进度条内容显示
     */
    public ProgressSubscriberNew(Class<T> tClass, GsonSubscriberOnNextListener<T> mgsonSubscriberOnNextListener, SubscriberOnNextListener<Bean<Object>> listener, Context context, boolean isProgress, String message) {
        this.entity_class = tClass;
        this.gsonSubscriberOnNextListener = mgsonSubscriberOnNextListener;
        this.mListener = listener;
        this.mContext = context;
        this.mIsProgress = isProgress;
        this.message = message;
    }

    private void showProgressDialog() {
        if (mProgressHUD == null) {
            mProgressHUD = new ProgressHUD(mContext);
        }
        mProgressHUD = ProgressHUD.show(mContext, "加载中", false, this);

    }

    private void showProgressDialog(String message) {
        if (mProgressHUD == null) {
            mProgressHUD = new ProgressHUD(mContext);
        }
        mProgressHUD = ProgressHUD.show(mContext, message, false, this);

    }

    private void dismissProgressDialog() {
        if (mProgressHUD != null) {
            mProgressHUD.dismiss();
            mProgressHUD = null;
        }
    }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        super.onStart();
        if (mIsProgress) {
            if (message != null && !message.equals("")) {
                showProgressDialog(message);
                return;
            }

            showProgressDialog();
        }
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onNext(Bean<Object> t) {
        if (t != null) {
            int code = t.getCode();
            if (code == 0) {
                Log.d("http_network", "code:" + code);
                if (null != t.getData()) {
                    Log.d("http_network", "data:object");
                    Object obj = t.getData();
                    Gson gson = new GsonBuilder().
                            registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                                @Override
                                public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                                    if (src == src.longValue())
                                        return new JsonPrimitive(src.longValue());
                                    return new JsonPrimitive(src);
                                }
                            }).create();
                    String myJson = gson.toJson(obj);//将gson转化为json
                    T jsonObject = gson.fromJson(myJson, entity_class);//把JSON字符串转为对象
                    gsonSubscriberOnNextListener.on_post_entity(jsonObject, jsonObject.toString());
                } else {
                    Log.d("http_network", "data:null");
                    String string = "success";
                    gsonSubscriberOnNextListener.on_post_entity((T) string, string);
                }
            } else {
                Log.d("http_network", "code:" + code);
                mListener.onError(t.getMessage());
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        mListener.onError(e.getMessage());
        dismissProgressDialog();

    }


    @Override
    public void onCancel(DialogInterface dialogInterface) {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
