package com.noplugins.keepfit.android.activity;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.TeacherDetailEntity;
import com.noplugins.keepfit.android.entity.TeacherEntity;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.FlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;

public class TeacherDetailActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    ImageView back_btn;
    @BindView(R.id.biaoqian_view)
    FlowLayout biaoqian_view;
    @BindView(R.id.yitie_biaoqian_view)
    FlowLayout yitie_biaoqian_view;
    @BindView(R.id.teacher_name)
    TextView teacher_name;
    @BindView(R.id.teacher_jieshao)
    TextView teacher_jieshao;
    @BindView(R.id.teacher_time)
    TextView teacher_time;

    String genTeacherNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {
        genTeacherNum = parms.getString("genTeacherNum");
    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_teacher_detail);
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
        init_teacher_detail();

    }

    private void init_teacher_detail() {
        Map<String, Object> params = new HashMap<>();
        params.put("genTeacherNum", genTeacherNum);//场馆编号
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Log.e(TAG, "教练详情参数：" + json_params);

//        subscription = Network.getInstance("教练详情", this)
//                .teacherDetail(requestBody, new ProgressSubscriberNew<>(TeacherDetailEntity.class, new GsonSubscriberOnNextListener<TeacherDetailEntity>() {
//                    @Override
//                    public void on_post_entity(TeacherDetailEntity entity, String s) {
//                        Log.e("教练详情成功", "教练详情成功:");
//                        set_detail_value(entity);
//                    }
//                }, new SubscriberOnNextListener<Bean<Object>>() {
//                    @Override
//                    public void onNext(Bean<Object> result) {
//
//                    }
//
//                    @Override
//                    public void onError(String error) {
//                        Log.e("课程列表失败", "课程列表失败:" + error);
//                    }
//                }, this, true));

    }

    private void set_detail_value(TeacherDetailEntity entity) {
        teacher_name.setText(entity.getTeacherName());
        teacher_jieshao.setText(entity.getTips());
        if (null != entity.getServiceDur()) {
            teacher_time.setText("0小时");
        } else {
            teacher_time.setText(entity.getServiceDur() + "小时");
        }

        setFlowlayout1(entity.getSkillList());
        setFlowlayout2(entity.getLabelList());

    }

    private void setFlowlayout2(List<String> list) {

        // 设置文字大小
        yitie_biaoqian_view.setTextSize(13);
        // 设置文字颜色
        yitie_biaoqian_view.setTextColor(getResources().getColor(R.color.color_6D7278));
        // 设置文字背景
        yitie_biaoqian_view.setBackgroundResource(R.drawable.biaoqian_bg);
        // 设置文字水平margin
        yitie_biaoqian_view.setHorizontalSpacing(15);
        // 设置文字垂直margin
        yitie_biaoqian_view.setVerticalSpacing(15);
        // 设置文字水平padding
        yitie_biaoqian_view.setTextPaddingH(8);
        // 设置文字垂直padding
        yitie_biaoqian_view.setTextPaddingH(4);
        // 设置UI与点击事件监听
        // 最后调用setViews方法
        yitie_biaoqian_view.setViews(list, new FlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(String content) {
                Toast.makeText(TeacherDetailActivity.this, content, Toast.LENGTH_SHORT).show();
            }
        });

        // 增加关键字
//        yitie_biaoqian_view.addView("关键字六", new FlowLayout.OnItemClickListener() {
//            @Override
//            public void onItemClick(String content) {
//                Toast.makeText(TeacherDetailActivity.this, content, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void setFlowlayout1(List<String> skills) {
        // 设置文字大小
        biaoqian_view.setTextSize(12);
        // 设置文字颜色
        biaoqian_view.setTextColor(Color.BLACK);
        // 设置文字背景
        biaoqian_view.setBackgroundResource(R.drawable.bg_frame);
        // 设置文字水平margin
        biaoqian_view.setHorizontalSpacing(15);
        // 设置文字垂直margin
        biaoqian_view.setVerticalSpacing(15);
        // 设置文字水平padding
        biaoqian_view.setTextPaddingH(8);
        // 设置文字垂直padding
        biaoqian_view.setTextPaddingH(4);
        // 设置UI与点击事件监听
        // 最后调用setViews方法
        biaoqian_view.setViews(skills, new FlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(String content) {
                Toast.makeText(TeacherDetailActivity.this, content, Toast.LENGTH_SHORT).show();
            }
        });

//        // 增加关键字
//        biaoqian_view.addView("关键字六", new FlowLayout.OnItemClickListener() {
//            @Override
//            public void onItemClick(String content) {
//                Toast.makeText(TeacherDetailActivity.this, content, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
