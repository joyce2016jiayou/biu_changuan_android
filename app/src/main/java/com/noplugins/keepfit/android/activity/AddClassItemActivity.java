package com.noplugins.keepfit.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.umeng.socialize.media.Base;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddClassItemActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    ImageView back_btn;
    @BindView(R.id.add_class_teacher_btn)
    LinearLayout add_class_teacher_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_add_class_item);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add_class_teacher_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddClassItemActivity.this,YaoQingTeacherActivity.class);
                startActivity(intent);
            }
        });
    }
}
