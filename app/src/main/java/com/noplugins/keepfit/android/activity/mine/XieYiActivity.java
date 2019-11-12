package com.noplugins.keepfit.android.activity.mine;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class XieYiActivity extends BaseActivity {

    TextView textView;
    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_xie_yi);
        ImageView imageView = findViewById(R.id.back_btn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void doBusiness(Context mContext) {
        textView = findViewById(R.id.tv_content);

        getFromAssets();
    }

    public void getFromAssets(){
        try {
            InputStream is = getResources().getAssets().open("xieyi.txt");
            InputStreamReader isr = new InputStreamReader(is,"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String str = "";
            while((str = br.readLine()) != null){
                textView.append(str+"\n");  //把test文档中的内容显示在tv中
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
