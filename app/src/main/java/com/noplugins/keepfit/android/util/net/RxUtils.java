package com.noplugins.keepfit.android.util.net;

import android.view.View;

import androidx.annotation.NonNull;

import rx.Observable;
import rx.Subscriber;

/**
 * Rxjava按钮防抖Util
 * Created by joyce on 2017/8/16.
 */

public class RxUtils {
    public static Observable<Void> clickView(@NonNull View view) {
        checkNoNull(view);
        return Observable.create(new ViewClickOnSubscribe(view));
    }

    /**
     * 查空
     */
    private static <T> void checkNoNull(T value) {
        if (value == null) {
            throw new NullPointerException("generic value here is null");
        }
    }

    private static class ViewClickOnSubscribe implements Observable.OnSubscribe<Void> {
        private View view;

        public ViewClickOnSubscribe(View view) {
            this.view = view;
        }

        @Override
        public void call(final Subscriber<? super Void> subscriber) {
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //订阅没取消
                    if (!subscriber.isUnsubscribed()) {
                        //发送消息
                        subscriber.onNext(null);
                    }
                }
            };
            view.setOnClickListener(onClickListener);
        }
    }
}
