package com.noplugins.keepfit.android.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.bean.use.ManagerBean;

import java.util.List;

public class ManagerTeamClassAdapter extends BaseQuickAdapter<ManagerBean.CourseListBean, BaseViewHolder> {
    public ManagerTeamClassAdapter(@Nullable List<ManagerBean.CourseListBean> data) {
        super(R.layout.item_manager_team, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ManagerBean.CourseListBean item) {

//        String startHour = DateHelper.getDateByLong(item.getStartTime());
//        String startDay  = DateHelper.getDateDayByLong(item.getStartTime());
//        String endHour = DateHelper.getDateByLong(item.getEndTime());
//        String week = DateHelper.getDayFromWeek(startDay);
        helper.setText(R.id.tv_time_day, item.getDay());
        helper.setText(R.id.tv_time_week, item.getWeek());
        helper.setText(R.id.tv_time_hour, item.getTime());

        helper.setText(R.id.tv_team_name, item.getCourseName());
        helper.setText(R.id.tv_team_room, roomType(Integer.parseInt(item.getType())));
        helper.setText(R.id.tv_team_price, "¥"+item.getFinalPrice());

        //

//        int timeLength = DateHelper.getOffectMinutes(item.getStartTime(),item.getEndTime());
        helper.setText(R.id.tv_team_man, item.getMaxNum()+"人");
        if (!item.isLoop()) {
            helper.setText(R.id.tv_team_time, "单次");
            helper.setText(R.id.tv_team_date, item.getMin()+"min");
            helper.getView(R.id.tv_team_tips).setVisibility(View.INVISIBLE);
        } else {
            helper.setText(R.id.tv_team_time, "第"+item.getNowWeek()+"周");
            helper.setText(R.id.tv_team_tips, "循环"+item.getLoopCycle()+"周：每周"+item.getWeek());
            helper.getView(R.id.tv_team_date).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_item, item.getStatusMsg());
        switch (item.getSearchType()) {
            case 1: //已上架
                helper.setText(R.id.tv_cg_name, item.getTeacherName());
                helper.getView(R.id.tv_team_price).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_cg_name).setVisibility(View.VISIBLE);
                helper.getView(R.id.ll_yaoqin_edit).setVisibility(View.GONE);
                helper.getView(R.id.tv_agin).setVisibility(View.GONE);
                helper.getView(R.id.ll_shenqin).setVisibility(View.GONE);
                break;
            case 2: //邀请中
                helper.getView(R.id.ll_yaoqin_edit).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_cg_name).setVisibility(View.GONE);
                helper.getView(R.id.ll_shenqin).setVisibility(View.GONE);
                helper.setText(R.id.tv_yaoqin_edit,"取消");
                break;
            case 3: //历史
                if (item.getTeacherName() != null){
                    helper.getView(R.id.tv_cg_name).setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_cg_name, item.getTeacherName());
                }
                helper.getView(R.id.tv_team_date).setVisibility(View.VISIBLE);

                if (item.getIsEdit() == 0) {
                    helper.getView(R.id.ll_yaoqin_edit).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.ll_yaoqin_edit).setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_yaoqin_edit,"编辑");
                }
                helper.getView(R.id.ll_shenqin).setVisibility(View.GONE);
                break;
            case 4: //申请中
                helper.getView(R.id.tv_cg_name).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_cg_name, item.getTeacherName());
                helper.getView(R.id.tv_team_price).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_cg_name).setVisibility(View.VISIBLE);
                helper.getView(R.id.ll_yaoqin_edit).setVisibility(View.GONE);
                helper.getView(R.id.tv_agin).setVisibility(View.GONE);
                helper.getView(R.id.ll_shenqin).setVisibility(View.VISIBLE);
                break;
        }


        helper.addOnClickListener(R.id.tv_agin);
        helper.addOnClickListener(R.id.tv_jujue);
        helper.addOnClickListener(R.id.tv_jieshou);
        helper.addOnClickListener(R.id.tv_yaoqin_edit);
        helper.addOnClickListener(R.id.rl_jump);

    }


    private String roomType(int roomType){
//        String[] listClass = mContext.getResources().getStringArray(R.array.team_class_room);
//        return listClass[roomType -1];
        return "123";
    }

    private String statusType(int type){
        String[] listClass = {"邀请成功","邀请失败","邀请中","已过期","邀请失败","邀请失败","邀请失败","取消邀请","取消申请"};
        return listClass[type-1];
    }


}
