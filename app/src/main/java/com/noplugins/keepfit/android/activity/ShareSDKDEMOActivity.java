package com.noplugins.keepfit.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.noplugins.keepfit.android.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;

public class ShareSDKDEMOActivity extends AppCompatActivity {
    Button fengxiang_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_sdkdemo);
        fengxiang_btn = findViewById(R.id.fengxiang_btn);
        fengxiang_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //带面板
                new ShareAction(ShareSDKDEMOActivity.this).withText("hello").setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                        .setCallback(shareListener).open();
                //不带面板
//                new ShareAction(ShareSDKDEMOActivity.this)
//                        .setPlatform(SHARE_MEDIA.QQ)//传入平台
//                        .withText("hello")//分享内容
//                        .setCallback(shareListener)//回调监听器
//                        .share();
            }
        });


    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @param platform 平台类型
         * @descrption 分享开始的回调
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @param platform 平台类型
         * @descrption 分享成功的回调
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(ShareSDKDEMOActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @param platform 平台类型
         * @param t        错误原因
         * @descrption 分享失败的回调
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ShareSDKDEMOActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @param platform 平台类型
         * @descrption 分享取消的回调
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ShareSDKDEMOActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };

}