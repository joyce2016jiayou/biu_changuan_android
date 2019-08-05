package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.YaoQiTeacherAdapter;
import com.noplugins.keepfit.android.adapter.YaoQingZhongDetailAdapter;
import com.noplugins.keepfit.android.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YaoQingZhongDetailActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    ImageView back_btn;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.yaoqing_number_tv)
    TextView yaoqing_number_tv;

    private LinearLayoutManager layoutManager;
    private YaoQingZhongDetailAdapter yaoQingZhongDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_yao_qing_zhong_detail);
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
        List<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");

        init_recycle(strings);


    }

    private void init_recycle(final List<String> dates) {
        recycler_view.setNestedScrollingEnabled(false);//禁止滑动
        recycler_view.setHasFixedSize(true);
        recycler_view.setItemAnimator(null);
        layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        yaoQingZhongDetailAdapter = new YaoQingZhongDetailAdapter(dates, this,yaoqing_number_tv);
        recycler_view.setAdapter(yaoQingZhongDetailAdapter);
    }


}
