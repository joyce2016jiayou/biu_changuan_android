package com.noplugins.keepfit.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.TeacherEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PermissionActivity extends BaseActivity {
    @BindView(R.id.jeuse_layout)
    RelativeLayout jeuse_layout;
    @BindView(R.id.jiaolian_layout)
    RelativeLayout jiaolian_layout;
    @BindView(R.id.back_btn)
    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.permission_layout);
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
        jeuse_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PermissionActivity.this,RoleActivity.class);
                startActivity(intent);
            }
        });
        jiaolian_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PermissionActivity.this, TeacherActivity.class);
                startActivity(intent);
            }
        });
    }
}
