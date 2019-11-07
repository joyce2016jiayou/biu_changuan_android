package com.noplugins.keepfit.android.util.net.progress;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.Toast;

import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.orhanobut.logger.Logger;

import rx.Subscriber;

public class ProgressSubscriber<T> extends Subscriber<Bean<T>> implements DialogInterface.OnCancelListener {
    private SubscriberOnNextListener<Bean<T>> mListener;
    private Context mContext;
    private ProgressHUD mProgressHUD;
    private String message;
    private boolean mIsProgress;
    private String Method_Tag;

    /**
     * 没有加载框 用于列表刷新加载
     *
     * @param listener 请求成功 逻辑处理
     * @param context  上下文
     */
    public ProgressSubscriber(SubscriberOnNextListener<Bean<T>> listener, Context context) {
        this.mListener = listener;
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
    public ProgressSubscriber(String method_tag, SubscriberOnNextListener<Bean<T>> listener, Context context, boolean isProgress) {
        this.Method_Tag = method_tag;
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
    public ProgressSubscriber(SubscriberOnNextListener<Bean<T>> listener, Context context, boolean isProgress, String message) {
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
    public void onNext(Bean<T> t) {
        if (t != null) {
            int code = t.getCode();
            Logger.e(Method_Tag + "返回的code:" + code);
            if (code == 0) {
                Logger.e(Method_Tag + "请求Success:");
                if (null != t.getData()) {
                    mListener.onNext(t);
                }
            } else {
                Logger.e(Method_Tag + "请求Fail:" + t.getMessage());
                if (t.getCode() == -1) {
                    mListener.onError(t.getMessage());
                    if (!TextUtils.isEmpty(t.getMessage())) {
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else if (t.getCode() == -2) {
                    //没有授权
                    mListener.onError("-2");
                } else if (t.getCode() == -3) {
                    //没有授权
                    mListener.onError("-3");
                }
            }


        }

    }

    @Override
    public void onError(Throwable e) {
        Logger.e(Method_Tag + "请求Fail:" + e.getMessage());
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
