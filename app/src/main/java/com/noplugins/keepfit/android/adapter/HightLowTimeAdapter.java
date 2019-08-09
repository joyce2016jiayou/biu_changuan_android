package com.noplugins.keepfit.android.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.noplugins.keepfit.android.entity.ItemBean;
import com.noplugins.keepfit.android.entity.TypeItemEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.qqtheme.framework.wheelpicker.DatePicker;
import cn.qqtheme.framework.wheelpicker.TimePicker;

public class HightLowTimeAdapter extends RecyclerView.Adapter<HightLowTimeAdapter.ViewHolder> {
    private ArrayList<ItemBean> datas;
    private LayoutInflater mInflater;
    private int mLayoutId;
    private Context mcontext;
    private List<TypeItemEntity> typeItemEntities = new ArrayList<>();
    private String[] typeArrays;
    //定义一个HashMap，用来存放EditText的值，Key是position
    HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();



    public HightLowTimeAdapter(Context context, ArrayList<ItemBean> data, int layoutId) {
        this.datas = data;
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

        void onItemClick(int tag, View view, int position);
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
                onItemClick.onItemClick(-55, view, (Integer) view.getTag());
            }
        });

        holder.tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(-66, view, (Integer) view.getTag());
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
                    addData(new ItemBean());
                } else {//表示删除
                    if (position != 0) {
                        datas.remove(position);
                        notifyItemRemoved(position);
                        if (position != datas.size()) {
                            notifyItemRangeChanged(position, getItemCount());
                        }
//                    datas.remove(position);
//                    notifyDataSetChanged();
                    }
                }
            }
        });

        //设置下拉选择框
        hashMap.put(position, 0);


    }

    //  添加数据
    public void addData(ItemBean itemBean) {

        itemBean.setFocus(true);
//      在list中添加数据，并通知条目加入一条
        datas.add(datas.size(), itemBean);
        //添加动画
        notifyItemInserted(datas.size());
//        if (position != mData.size()) {
//            otifyItemRangeChanged(position, mData.size() - position);
//        }


    }

    public ArrayList<ItemBean> getData() {
        for (int i = 0; i < datas.size(); i++) {
            ItemBean itemBean = datas.get(i);
            itemBean.setType_name(typeArrays[hashMap.get(i)]);
        }
        return datas;
    }

    //设置状态
    private void check(int position) {
        for (ItemBean l : datas) {
            l.setFocus(false);
        }
        datas.get(position).setFocus(true);
    }

    @Override
    public int getItemCount() {
        return datas.size();
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
