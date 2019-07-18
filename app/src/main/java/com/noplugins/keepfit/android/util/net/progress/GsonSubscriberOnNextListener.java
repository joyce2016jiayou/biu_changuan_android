package com.noplugins.keepfit.android.util.net.progress;

/**
 * Created by shiyujia02 on 2018/4/12.
 */

public interface GsonSubscriberOnNextListener<T> {
     void on_post_entity(T t,String message_id);
}
