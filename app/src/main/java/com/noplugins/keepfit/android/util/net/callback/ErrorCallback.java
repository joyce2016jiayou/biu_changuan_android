package com.noplugins.keepfit.android.util.net.callback;


import android.view.View;

import com.noplugins.keepfit.android.R;
import com.ql0571.loadmanager.callback.LoadCallback;

/**
 * desc:错误页面
 * <p>
 * author: qiulie
 * date: 2017/9/30
 */

public class ErrorCallback extends LoadCallback {

    @Override
    protected int onCreateView() {
        return R.layout.activity_net_error;
    }

    @Override
    protected View setReloadView(View rootView) {
        return rootView.findViewById(R.id.image_top);
    }

    @Override
    protected View setBackView(View rootView) {
        return rootView.findViewById(R.id.iamge_back);
    }
}
