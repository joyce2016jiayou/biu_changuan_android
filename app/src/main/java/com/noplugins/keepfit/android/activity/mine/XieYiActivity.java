package com.noplugins.keepfit.android.activity.mine;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.callback.OnclickCallBack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XieYiActivity extends BaseActivity {
    @BindView(R.id.content_layout)
    WebView webView;
    int type = 0;

    @Override
    public void initBundle(Bundle parms) {
        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_xie_yi);
        ButterKnife.bind(this);
        isShowTitle(true);
        if (type == 1) {
            setTitleView(R.string.tv186);
        } else if (type == 2) {
            setTitleView(R.string.tv182);
        }
        title_left_button_onclick_listen(new OnclickCallBack() {
            @Override
            public void onclick() {
                finish();
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        //自适应屏幕
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        if (type == 1) {//隐私政策
            webView.loadUrl("http://www.noplugins.com/doc/changguan_xieyi.html");
        } else if (type == 2) {//合作协议
            webView.loadUrl("http://www.noplugins.com/doc/changguan_qianyue.html");
        }


    }


}
