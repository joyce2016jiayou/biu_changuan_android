package com.noplugins.keepfit.android.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.ClassEntity;
import com.noplugins.keepfit.android.util.data.DateHelper;

import java.util.Date;
import java.util.List;

public class AddClass01Adapter extends BaseQuickAdapter<ClassEntity.DataBean, BaseViewHolder> {


    public AddClass01Adapter(@Nullable List<ClassEntity.DataBean> data) {
        super(R.layout.class_item,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ClassEntity.DataBean item) {
        //课程名称+课程类型+教练名字
        helper.setText(R.id.class_title_tv,item.getCourse_name()+"·"+getString(Integer.parseInt(item.getType())));
        helper.setText(R.id.class_changdi,item.getGym_place_num()+"");
        helper.setText(R.id.class_people_number,item.getMax_num()+"");
        helper.setText(R.id.class_minute,item.getCourse_time()+"");
        helper.setText(R.id.class_xunhuan,item.getLoop_cycle()+"周循环");

        String create_date = DateHelper.timeDay(item.getStart_time());
        Log.d("tag","create_date:"+create_date);
        String create_date_time = DateHelper.timeHourAndMinite(item.getStart_time());
        Log.d("tag","create_date:"+create_date_time);
        helper.setText(R.id.create_date,create_date);
        helper.setText(R.id.create_time,create_date_time);

        String end_date = DateHelper.timeDay(item.getEnd_time());
        String end_date_time = DateHelper.timeHourAndMinite(item.getEnd_time());
        helper.setText(R.id.end_date,end_date);
        helper.setText(R.id.end_time,end_date_time);

        //1 邀请成功，2邀请失败，3邀请中 ，4已过期
        String status ="";
       switch (item.getStatus()){
           case 1:
               status = "邀请成功";
               break;
           case 2:
               status = "邀请失败";
               break;
           case 3:
               status = "邀请中";
               break;
           case 4:
               status = "已过期";
               break;
       }

        helper.setText(R.id.class_status,status);

        helper.addOnClickListener(R.id.ll_item);
    }

    private String getString(int type){
        String [] array = mContext.getResources().getStringArray(R.array.team_xlmb);
        return array[type-1];
    }
}
