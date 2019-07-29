package com.noplugins.keepfit.android.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bt.mylibrary.TimeLineMarkerView;
import com.bumptech.glide.Glide;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.DayWhatch;
import com.noplugins.keepfit.android.util.screen.ScreenUtilsHelper;

import java.util.List;
import java.util.Properties;

public class DateWhatchAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private List<DayWhatch> list;
    private Activity context;
    private static final int EMPTY_VIEW = 2;
    private static final int TYPE_YOUTANG = 1;


    public DateWhatchAdapter(List<DayWhatch> mlist, Activity mcontext) {
        list = mlist;
        context = mcontext;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {

        YouYangViewHolder youYangViewHolder = new YouYangViewHolder(view, false);
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
            item_view = LayoutInflater.from(context).inflate(R.layout.daywhatch_empty_view, parent, false);
            ;
            holder = new EmptyViewHolder(item_view, true);
        } else if (viewType == TYPE_YOUTANG) {
            item_view = LayoutInflater.from(context).inflate(R.layout.daywhatch_youyang_view, parent, false);
        }
        holder = new YouYangViewHolder(item_view, true);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder view_holder, int position, boolean isItem) {
        DayWhatch dayWhatch = list.get(position);
        if (view_holder instanceof YouYangViewHolder) {
            YouYangViewHolder holder = (YouYangViewHolder) view_holder;
            //holder.lin_view.setMarkerSize(ScreenUtilsHelper.dip2px(context,15));


            if (dayWhatch.getKecheng_type().equals("1")) {//表示有氧
                holder.yujia_bg.setVisibility(View.INVISIBLE);
                holder.dance_bg.setVisibility(View.INVISIBLE);
                if (dayWhatch.isIs_out_date()) {
                    holder.youyang_bg.setBackgroundResource(R.drawable.kapian_hui);
                    set_red(holder);
                } else {
                    holder.youyang_bg.setBackgroundResource(R.drawable.kapian_bai);
                    set_green(holder);
                }
            } else if (dayWhatch.getKecheng_type().equals("2")) {//表示瑜伽
                holder.youyang_bg.setVisibility(View.INVISIBLE);
                holder.dance_bg.setVisibility(View.INVISIBLE);
                if (dayWhatch.isIs_out_date()) {
                    holder.yujia_bg.setBackgroundResource(R.drawable.kapian_hui);
                } else {
                    holder.yujia_bg.setBackgroundResource(R.drawable.kapian_bai);
                }
            } else {//表示单车
                holder.youyang_bg.setVisibility(View.INVISIBLE);
                holder.yujia_bg.setVisibility(View.INVISIBLE);
                if (dayWhatch.isIs_out_date()) {
                    holder.dance_bg.setBackgroundResource(R.drawable.kapian_hui);
                } else {
                    holder.dance_bg.setBackgroundResource(R.drawable.kapian_bai);
                }
            }

            holder.youyang_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    select_more_popwindow();
                }
            });
            holder.yujia_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    select_more_popwindow();

                }
            });
            holder.dance_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    select_more_popwindow();

                }
            });

        }
    }

    private void set_red(YouYangViewHolder holder) {
        int mycolor = context.getResources().getColor(R.color.bottom_color);
        ColorDrawable colorDrawable = new ColorDrawable(mycolor);
        holder.lin_view.setEndLine(colorDrawable);
    }

    private void set_green(YouYangViewHolder holder) {
        int mycolor = context.getResources().getColor(R.color.result_points);
        ColorDrawable colorDrawable = new ColorDrawable(mycolor);
        holder.lin_view.setEndLine(colorDrawable);
    }

    @Override
    public int getAdapterItemViewType(int position) {
        if (list.size() == 0) {
            return EMPTY_VIEW;
        } else {
            return TYPE_YOUTANG;
        }
    }


    public static Dialog m_dialog;
    private void select_more_popwindow() {
        LayoutInflater factory = LayoutInflater.from(context);
        View view = factory.inflate(R.layout.daywhatch_item_detial, null);
        m_dialog = new Dialog(context, R.style.transparentFrameWindowStyle2);
        m_dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = m_dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        m_dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        m_dialog.setCanceledOnTouchOutside(true);
        m_dialog.show();
        /**操作*/
        ImageView close_btn = view.findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_dialog.dismiss();
            }
        });

    }


    @Override
    public int getAdapterItemCount() {
        return list.size() > 0 ? list.size() : 1;
    }

//    public interface OnItemClickListener {
//        void onItemClick(View view, int position);
//    }
//
//
//    private OnItemClickListener mOnItemClickListener;
//
//    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
//        this.mOnItemClickListener = mOnItemClickListener;
//    }

    public void setData(List<DayWhatch> list) {
        this.list = list;
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


    public class YouYangViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public LinearLayout youyang_bg, yujia_bg, dance_bg;
        public TextView time_tv;
        public TimeLineMarkerView lin_view;

        public YouYangViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                youyang_bg = view.findViewById(R.id.youyang_bg);
                yujia_bg = view.findViewById(R.id.yujia_bg);
                dance_bg = view.findViewById(R.id.dance_bg);
                time_tv = view.findViewById(R.id.time_tv);
                lin_view = view.findViewById(R.id.lin_view);
            }
        }
    }


}
