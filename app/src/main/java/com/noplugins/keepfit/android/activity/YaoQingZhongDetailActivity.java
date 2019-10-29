package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.YaoQiTeacherAdapter;
import com.noplugins.keepfit.android.adapter.YaoQingZhongDetailAdapter;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.ClassDetailEntity;
import com.noplugins.keepfit.android.entity.ClassEntity;
import com.noplugins.keepfit.android.util.data.DateHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;

public class YaoQingZhongDetailActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    ImageView back_btn;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.yaoqing_number_tv)
    TextView yaoqing_number_tv;
    @BindView(R.id.xianzhi_number_tv)
    TextView xianzhi_number_tv;
    @BindView(R.id.class_name)
    TextView class_name;
    @BindView(R.id.teacher_name)
    TextView teacher_name;
    @BindView(R.id.price_tv)
    TextView price_tv;
    @BindView(R.id.class_name1)
    TextView class_name1;
    @BindView(R.id.teacher_name_tv)
    TextView teacher_name_tv;
    @BindView(R.id.class_room)
    TextView class_room;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.people_xianzhi)
    TextView people_xianzhi;
    @BindView(R.id.class_xunhuan)
    TextView class_xunhuan;
    @BindView(R.id.create_time)
    TextView create_time;
    @BindView(R.id.end_time)
    TextView end_time;
    @BindView(R.id.jieshao_tv)
    TextView jieshao_tv;
    @BindView(R.id.class_status)
    TextView class_status;
    @BindView(R.id.shihe_people)
    TextView shihe_people;
    @BindView(R.id.tips_tv)
    TextView tips_tv;
    @BindView(R.id.add_class_btn)
    LinearLayout add_class_btn;

    private LinearLayoutManager layoutManager;
    private YaoQingZhongDetailAdapter yaoQingZhongDetailAdapter;
    private String gymCourseNum;
    private int number = 0;

    @Override
    public void initBundle(Bundle parms) {
        gymCourseNum = parms.getString("gymCourseNum");
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        init_class_detail();
    }

    private void init_class_detail() {
        Map<String, Object> params = new HashMap<>();
        params.put("gymCourseNum", gymCourseNum);//场馆编号
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Log.e("课程详情",json_params);
        subscription = Network.getInstance("课程详情", this)
                .class_detail(requestBody, new ProgressSubscriberNew<>(ClassDetailEntity.class, new GsonSubscriberOnNextListener<ClassDetailEntity>() {
                    @Override
                    public void on_post_entity(ClassDetailEntity entity, String s) {
                        Log.e("课程详情","课程详情");
                        List<ClassDetailEntity.TeacherListBean> teacherListBeans = entity.getTeacherList();
                        if(teacherListBeans.size()>0){
                            recycler_view.setVisibility(View.VISIBLE);
                            init_recycle(teacherListBeans);
                        }else{
                            recycler_view.setVisibility(View.GONE);
                            //设置信息
                        }

                        set_information(entity);

                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {

                    }

                    @Override
                    public void onError(String error) {
                        Log.e("课程详情失败", "课程详情失败:" + error);
                    }
                }, this, true));

    }

    private void set_information(ClassDetailEntity entity) {
        xianzhi_number_tv.setText("("+entity.getCourse().getComeNum() + "/" + entity.getCourse().getMaxNum()+")");
        class_name.setText(entity.getCourse().getCourseName());
        teacher_name.setText(entity.getCourse().getGenTeacherNum());
        price_tv.setText("￥"+entity.getCourse().getFinalPrice()+"/人");
        class_name1.setText(entity.getCourse().getCourseName());
        teacher_name_tv.setText(entity.getCourse().getGenTeacherNum());
        if(entity.getCourse().getClassType()==1){
            class_room.setText("有氧操房");
        }else if(entity.getCourse().getClassType()==2){
            class_room.setText("动感单车");
        }else if(entity.getCourse().getClassType()==3){
            class_room.setText("瑜伽房");
        }

        price.setText("￥"+entity.getCourse().getFinalPrice()+"/人");
        people_xianzhi.setText(entity.getCourse().getMaxNum()+"人");
        if(entity.getCourse().isLoop()){//有循环
            class_xunhuan.setVisibility(View.VISIBLE);
            if(entity.getCourse().getLoopCycle()==1){
                class_xunhuan.setText("一周");
            }else if(entity.getCourse().getLoopCycle()==2){
                class_xunhuan.setText("二周");
            }else if(entity.getCourse().getLoopCycle()==3){
                class_xunhuan.setText("三周");
            }else if(entity.getCourse().getLoopCycle()==4){
                class_xunhuan.setText("四周");
            }

        }else{//没有循环
            class_xunhuan.setVisibility(View.GONE);
        }

        String status ="";
        switch (entity.getCourse().getStatus()){
            case 1:
                status = "邀请成功";
                break;
            case 2:
                status = "邀请失败";
                break;
            case 3:
                status = "邀请中";
                add_class_btn.setVisibility(View.VISIBLE);
                break;
            case 4:
                status = "已过期";
                break;
        }
        class_status.setText(status);
        if(entity.getTeacherList().size()>0){
            yaoqing_number_tv.setText(entity.getTeacherList().size()+"/5");
            number = entity.getTeacherList().size();
        }else{
            yaoqing_number_tv.setVisibility(View.GONE);
        }

        create_time.setText(DateHelper.get_Date_str(entity.getCourse().getStartTime(),entity.getCourse().getEndTime()));
        jieshao_tv.setText(entity.getCourse().getCourseDes());
        shihe_people.setText(entity.getCourse().getSuitPerson());
        tips_tv.setText(entity.getCourse().getTips());
        add_class_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YaoQingZhongDetailActivity.this,YaoQingTeacherActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("create_time",entity.getCourse().getCreateDate());
                bundle.putString("gym_course_num",entity.getCourse().getCourseNum());
                bundle.putInt("number",number);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void init_recycle(final List<ClassDetailEntity.TeacherListBean> dates) {
        recycler_view.setNestedScrollingEnabled(false);//禁止滑动
        recycler_view.setHasFixedSize(true);
        recycler_view.setItemAnimator(null);
        layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        yaoQingZhongDetailAdapter = new YaoQingZhongDetailAdapter(dates, this, yaoqing_number_tv,gymCourseNum);
        recycler_view.setAdapter(yaoQingZhongDetailAdapter);
    }


}
