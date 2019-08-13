package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.HightLowTimeAdapter;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.HightLowTimeEntity;
import com.noplugins.keepfit.android.entity.TimeSelectEntity;
import com.noplugins.keepfit.android.util.TimePickerUtils;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.wheelpicker.TimePicker;
import okhttp3.RequestBody;

public class HightLowTImeActivity extends BaseActivity {

    @BindView(R.id.rc_view)
    RecyclerView rc_view;
    @BindView(R.id.back_btn)
    ImageView back_btn;

    @BindView(R.id.tv_complete)
    TextView tv_complete;

    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager1;
    private HightLowTimeAdapter hightLowTimeAdapter;
    private ArrayList<HightLowTimeEntity> completeDatas;
    private List<TimeSelectEntity> timeEntities;
    //日期选择
    private TimePicker picker;

    private String arr[];

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
        hightLowTimeAdapter = new HightLowTimeAdapter(this, completeDatas, R.layout.item_hight_low_time);
        hightLowTimeAdapter.addData(new HightLowTimeEntity());
        rc_view.setAdapter(hightLowTimeAdapter);
        hightLowTimeAdapter.setOnItemClickListener(new HightLowTimeAdapter.onItemClick() {
            @Override
            public void onItemClick(int tag, View view, TextView endView,int position) {

                getTimeData();

                TimePickerUtils.time_check(HightLowTImeActivity.this,picker,
                        (TextView) view,endView,timeEntities);

            }

            @Override
            public void onItemClick(int tag, TextView startView, View view, int position) {
                if ("请选择".equals(startView.getText().toString())){
                    Toast.makeText(HightLowTImeActivity.this,"请先选择开始时间！",Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("tag_onItemClick",startView.getText().toString());
                arr = startView.getText().toString().split(":");
                getTimeData();

                TimePickerUtils.time_check(HightLowTImeActivity.this,picker,(TextView) view,
                        arr[0],arr[1],timeEntities);
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
     * 获取当前时间段列表
     */
    private void getTimeData(){
        timeEntities.clear();
        if (rc_view.getChildCount() <=1){
            return;
        }
        for (int i = 0; i < rc_view.getChildCount(); i++) {
            RelativeLayout layout = (RelativeLayout) rc_view.getChildAt(i);
            TextView tvStartTime = layout.findViewById(R.id.tvStartTime);
            TextView tvEndTime = layout.findViewById(R.id.tvEndTime);

            if ("请选择".equals(tvStartTime.getText().toString())){
                return;
            }
            if ("请选择".equals(tvEndTime.getText().toString())){
                return;
            }
            TimeSelectEntity timeSelectEntity = new TimeSelectEntity();
            timeSelectEntity.setStartTimeHour(Integer.parseInt(tvStartTime.getText().toString().split(":")[0]));
            timeSelectEntity.setStartTimeMinute(Integer.parseInt(tvStartTime.getText().toString().split(":")[1]));
            timeSelectEntity.setEndTimeHour(Integer.parseInt(tvEndTime.getText().toString().split(":")[0]));
            timeSelectEntity.setEndTimeMinute(Integer.parseInt(tvEndTime.getText().toString().split(":")[1]));

            timeEntities.add(timeSelectEntity);
        }
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

            if ("请选择".equals(tvStartTime.getText().toString())){
                Toast.makeText(getApplicationContext(), "时间不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            if ("请选择".equals(tvEndTime.getText().toString())){
                Toast.makeText(getApplicationContext(), "时间不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            HightLowTimeEntity hightLowTimeEntity = new HightLowTimeEntity();
            hightLowTimeEntity.setGym_area_num(Network.place_number);
            hightLowTimeEntity.setHigh_time_start(tvStartTime.getText().toString());
            hightLowTimeEntity.setHigh_time_end(tvEndTime.getText().toString());
            hightLowTimeEntity.setNormal_price("38");
            completeDatas.add(hightLowTimeEntity);
        }


//        Gson gson = new Gson();
//        String objJson = gson.toJson(completeDatas);
//        Logger.d(objJson);

        upload();
    }

    private void upload(){
        Map<String, Object> params = new HashMap<>();
        params.put("list", completeDatas);
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(null, json);

        subscription = Network.getInstance("高低时峰", getApplicationContext())

                .setHighAndLowTime(requestBody,new ProgressSubscriberNew<>(String.class, new GsonSubscriberOnNextListener<String>() {
                    @Override
                    public void on_post_entity(String s, String message_id) {
                        Toast.makeText(getApplicationContext(), message_id, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> objectBean) {

                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "登录失败：" + error);
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                    }
                }, this, true));
    }
}
