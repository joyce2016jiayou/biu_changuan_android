package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.HightLowTimeAdapter;
import com.noplugins.keepfit.android.adapter.RoleAdapter;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.HightLowTimeEntity;
import com.noplugins.keepfit.android.entity.ItemBean;
import com.noplugins.keepfit.android.entity.RoleBean;
import com.noplugins.keepfit.android.entity.TimeEntity;
import com.noplugins.keepfit.android.util.TimePickerUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.wheelpicker.TimePicker;

public class HightLowTImeActivity extends BaseActivity {

    @BindView(R.id.rc_view)
    RecyclerView rc_view;
    @BindView(R.id.back_btn)
    ImageView back_btn;

    @BindView(R.id.tv_complete)
    TextView tv_complete;

    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ItemBean> datas;
    private HightLowTimeAdapter hightLowTimeAdapter;
    private List<HightLowTimeEntity> completeDatas;
    private List<TimeEntity> timeEntities;
    //日期选择
    private TimePicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_hight_low_time);
        ButterKnife.bind(this);
        isShowTitle(false);
        completeDatas = new ArrayList<>();
        timeEntities = new ArrayList<>();
    }

    private void setAdapter() {
        //设置视图添加
        linearLayoutManager = new LinearLayoutManager(this);
        rc_view.setLayoutManager(linearLayoutManager);
        rc_view.setNestedScrollingEnabled(false);//禁止滑动
        datas = new ArrayList<>();
        hightLowTimeAdapter = new HightLowTimeAdapter(this, datas, R.layout.item_hight_low_time);
        hightLowTimeAdapter.addData(new ItemBean());
        rc_view.setAdapter(hightLowTimeAdapter);
        hightLowTimeAdapter.setOnItemClickListener(new HightLowTimeAdapter.onItemClick() {
            @Override
            public void onItemClick(int tag, View view, int position) {
                TimePickerUtils.time_check(HightLowTImeActivity.this,picker,(TextView) view);

            }
        });

    }
    @Override
    public void doBusiness(Context mContext) {
        setAdapter();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toComplete();
            }
        });
    }

    /**
     * 点击完成
     */
    private void toComplete(){
        completeDatas.clear();
        for (int i = 0; i < rc_view.getChildCount(); i++) {
            RelativeLayout layout = (RelativeLayout) rc_view.getChildAt(i);
            TextView tvStartTime = layout.findViewById(R.id.tvStartTime);
            TextView tvEndTime = layout.findViewById(R.id.tvEndTime);

            if (TextUtils.isEmpty(tvStartTime.getText().toString())){
                return;
            }
            if (TextUtils.isEmpty(tvEndTime.getText().toString())){
                return;
            }
            HightLowTimeEntity hightLowTimeEntity = new HightLowTimeEntity();
            hightLowTimeEntity.setHigh_time_start(tvStartTime.getText().toString());
            hightLowTimeEntity.setHigh_time_end(tvEndTime.getText().toString());
            completeDatas.add(hightLowTimeEntity);
        }


        Gson gson = new Gson();
        String objJson = gson.toJson(completeDatas);
        Logger.d(objJson);
    }
}
