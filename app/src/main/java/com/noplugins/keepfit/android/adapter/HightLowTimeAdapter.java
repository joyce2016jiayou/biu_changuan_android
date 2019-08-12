package com.noplugins.keepfit.android.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.HightLowTimeEntity;
import com.noplugins.keepfit.android.entity.ItemBean;
import com.noplugins.keepfit.android.entity.TypeItemEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.qqtheme.framework.wheelpicker.DatePicker;
import cn.qqtheme.framework.wheelpicker.TimePicker;

public class HightLowTimeAdapter extends RecyclerView.Adapter<HightLowTimeAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private int mLayoutId;
    private Context mcontext;
    private List<HightLowTimeEntity> completeDatas;
    private String[] typeArrays;
    //定义一个HashMap，用来存放EditText的值，Key是position
    HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();



    public HightLowTimeAdapter(Context context, ArrayList<HightLowTimeEntity> data, int layoutId) {
        this.completeDatas = data;
        mInflater = LayoutInflater.from(context);
        mcontext = context;
        typeArrays = mcontext.getResources().getStringArray(R.array.gongneng_types);
        mLayoutId = layoutId;
    }

    /**
     * 声明接口变量
     */
    private onItemClick onItemClick;

    /**
     * 定义监听接口tag是区分点击的什么，position是位置，要想获取position还需要在重新设置下tag
     */
    public static interface onItemClick {

        void onItemClick(int tag, View view,TextView endView, int position);
        void onItemClick(int tag, TextView startView,View view, int position);
    }

    /**
     * 声明给外界的方法
     *
     * @param listener
     */
    public void setOnItemClickListener(onItemClick listener) {
        this.onItemClick = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(mLayoutId, parent, false);
        ViewHolder holder = new HightLowTimeAdapter.ViewHolder(view);
        holder.tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(-55, view,holder.tvEndTime, (Integer) view.getTag());
            }
        });

        holder.tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(-66, holder.tvStartTime,view, (Integer) view.getTag());
            }
        });
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //第0 不能有删除功能
        if (position == 0) {
            holder.ivAdd.setImageResource(R.drawable.role_add_icon);
        } else {
            holder.ivAdd.setImageResource(R.drawable.role_jian_btn);
        }

        holder.tvEndTime.setTag(position);
        holder.tvStartTime.setTag(position);

        //添加
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {//表示添加
                    addData(new HightLowTimeEntity());
                } else {//表示删除
                    // TODO: 2019-08-11 删除有bug
                    Log.d("Hight_Adapter","del:"+position);
                    completeDatas.remove(position);
                    notifyItemRemoved(position);
                    if (position != completeDatas.size()) {
                        notifyItemRangeChanged(position, completeDatas.size() - position);
                    }
//                    completeDatas.remove(position);
//                    notifyItemRemoved(position);
//                    if (position != completeDatas.size()) {
//                        notifyItemRangeChanged(position, getItemCount());
//                    }
////                    datas.remove(position);
//                    notifyItemChanged(position);
                }
            }
        });

        //设置下拉选择框
        hashMap.put(position, 0);

    }

    //  添加数据
    public void addData(HightLowTimeEntity itemBean) {

//      在list中添加数据，并通知条目加入一条
        completeDatas.add(completeDatas.size(), itemBean);
        //添加动画
        notifyItemInserted(completeDatas.size());



    }


    @Override
    public int getItemCount() {
        return completeDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAdd;
        private TextView tvStartTime;
        private TextView tvEndTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAdd = itemView.findViewById(R.id.ivAdd);
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
        }
    }
}
