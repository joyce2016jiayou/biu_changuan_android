package com.noplugins.keepfit.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.MediaMetadata;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.ExRecyclerAdapter;
import com.noplugins.keepfit.android.adapter.RoleAdapter;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.ItemBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoleActivity extends BaseActivity {
    @BindView(R.id.rc_view)
    RecyclerView rc_view;
    @BindView(R.id.back_btn)
    ImageView back_btn;

    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ItemBean> datas;
    private RoleAdapter roleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_role);
        ButterKnife.bind(this);
        isShowTitle(false);

    }

    @Override
    public void doBusiness(Context mContext) {
        set_jiugongge_view();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void set_jiugongge_view() {
        //设置视图添加
        linearLayoutManager = new LinearLayoutManager(this);
        rc_view.setLayoutManager(linearLayoutManager);
        rc_view.setNestedScrollingEnabled(false);//禁止滑动
        datas = new ArrayList<>();
        roleAdapter = new RoleAdapter(this, datas, R.layout.role_item);
        roleAdapter.addData(new ItemBean());
        rc_view.setAdapter(roleAdapter);

    }
}
