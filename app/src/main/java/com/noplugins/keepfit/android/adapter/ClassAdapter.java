package com.noplugins.keepfit.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.bean.RiChengBean;

import java.util.ArrayList;
import java.util.List;

public class ClassAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private static final int EMPTY_VIEW = 2;
    private static final int ITEM_VIEW = 3;
    Context context;
    List<RiChengBean.ResultBean> classDateBeans;

    public ClassAdapter(List<RiChengBean.ResultBean> m_classDateBean, Context m_context) {
        classDateBeans = m_classDateBean;
        context = m_context;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {

        WeiJieShuViewHolder youYangViewHolder = new WeiJieShuViewHolder(view, false);
        return youYangViewHolder;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        RecyclerView.ViewHolder holder = getViewHolderByViewType(viewType, parent);
        return holder;
    }

    RecyclerView.ViewHolder holder = null;

    private RecyclerView.ViewHolder getViewHolderByViewType(int viewType, ViewGroup parent) {
        View item_view = null;
        if (viewType == EMPTY_VIEW) {
            item_view = LayoutInflater.from(context).inflate(R.layout.select_date_empty_view, parent, false);
            holder = new EmptyViewHolder(item_view, false);
        } else if (viewType == ITEM_VIEW) {
            item_view = LayoutInflater.from(context).inflate(R.layout.class_date_item, parent, false);
            holder = new WeiJieShuViewHolder(item_view, true);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder view_holder, int position, boolean isItem) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });
        if (view_holder instanceof WeiJieShuViewHolder) {
            WeiJieShuViewHolder holder = (WeiJieShuViewHolder) view_holder;
            RiChengBean.ResultBean resultBean = classDateBeans.get(position);
            //holder.coach_name.setText();
        }


    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    int RETUEN_CODE = 0;

    @Override
    public int getAdapterItemViewType(int position) {
        if (classDateBeans.size() == 0) {
            RETUEN_CODE = EMPTY_VIEW;
        } else {
            RETUEN_CODE = ITEM_VIEW;

        }
        return RETUEN_CODE;
    }


    @Override
    public int getAdapterItemCount() {
        return classDateBeans.size() > 0 ? classDateBeans.size() : 1;
    }


    public void setData(List<RiChengBean.ResultBean> list) {
        this.classDateBeans = list;
        notifyDataSetChanged();
    }


    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public EmptyViewHolder(View item_view, boolean isItem) {
            super(item_view);
            if (isItem) {
                this.view = item_view;
            }
        }
    }

    public class WeiJieShuViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView coach_name,status_tv,type_icon_tv,phone_or_name_tv,time_tv,class_type,money_tv;
        public ImageView phone_btn,status_img;
        public LinearLayout type_icon_bg;
        public WeiJieShuViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                coach_name = view.findViewById(R.id.coach_name);
                phone_btn = view.findViewById(R.id.phone_btn);
                status_tv = view.findViewById(R.id.status_tv);
                type_icon_tv= view.findViewById(R.id.type_icon_tv);
                type_icon_bg = view.findViewById(R.id.type_icon_bg);
                phone_or_name_tv = view.findViewById(R.id.phone_or_name_tv);
                status_img = view.findViewById(R.id.status_img);
                time_tv = view.findViewById(R.id.time_tv);
                class_type = view.findViewById(R.id.class_type);
                money_tv = view.findViewById(R.id.money_tv);
            }
        }
    }


}
