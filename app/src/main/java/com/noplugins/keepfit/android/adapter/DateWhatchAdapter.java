package com.noplugins.keepfit.android.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
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
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.DateViewEntity;
import com.noplugins.keepfit.android.util.data.DateHelper;

import java.util.Date;
import java.util.List;

public class DateWhatchAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private List<DateViewEntity.DateBean> list;
    private Activity context;
    private static final int EMPTY_VIEW = 2;
    private static final int TYPE_YOUTANG = 1;


    public DateWhatchAdapter(List<DateViewEntity.DateBean> mlist, Activity mcontext) {
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
            holder = new EmptyViewHolder(item_view, true);
        } else if (viewType == TYPE_YOUTANG) {
            item_view = LayoutInflater.from(context).inflate(R.layout.daywhatch_youyang_view, parent, false);
            holder = new YouYangViewHolder(item_view, true);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder view_holder, int position, boolean isItem) {
        if (view_holder instanceof YouYangViewHolder) {
            DateViewEntity.DateBean dayWhatch = list.get(position);
            YouYangViewHolder holder = (YouYangViewHolder) view_holder;
            //holder.lin_view.setMarkerSize(ScreenUtilsHelper.dip2px(context,15));

            Date date = DateHelper.transForDate(dayWhatch.getStart_time());
            String hour;
            String minute;
            if (date.getHours() <= 9) {
                hour = "0" + date.getHours();
            } else {
                hour = date.getHours() + "";
            }
            if (date.getMinutes() <= 9) {
                minute = "0" + date.getMinutes();
            } else {
                minute = date.getMinutes() + "";
            }
            holder.time_tv.setText(hour + ":" + minute);
            Date create_date = DateHelper.transForDate(dayWhatch.getCreate_date());

            Log.e("接口连接可怜的萨达", dayWhatch.getStart_time() + "");
            if (dayWhatch.getType().equals("1")) {//表示有氧
                holder.yujia_bg.setVisibility(View.INVISIBLE);
                holder.dance_bg.setVisibility(View.INVISIBLE);

                holder.youyang_title_tv.setText(dayWhatch.getCourse_name());
                holder.youyang_date_tv.setText("(" + create_date.getMonth() + "/" + create_date.getDate() + ")");
                holder.youyang_user_tv.setText(dayWhatch.getTeacher_name());
                holder.money_tv.setText("￥" + dayWhatch.getPrice() + "/人");
                /**设置有没有过期*/
                if (dayWhatch.getPast() == 1) {//表示过期
                    holder.youyang_bg.setBackgroundResource(R.drawable.kapian_hui);
                    //set_red(holder);
                } else {
                    holder.youyang_bg.setBackgroundResource(R.drawable.kapian_bai);
                    //set_green(holder);
                }
            } else if (dayWhatch.getType().equals("2")) {//表示瑜伽
                holder.youyang_bg.setVisibility(View.INVISIBLE);
                holder.dance_bg.setVisibility(View.INVISIBLE);

                holder.yujia_title_tv.setText(dayWhatch.getCourse_name());
                holder.yujia_date_tv.setText("(" + create_date.getMonth() + "/" + create_date.getDate() + ")");
                holder.yujia_user_tv.setText(dayWhatch.getTeacher_name());
                holder.yujia_money_tv.setText("￥" + dayWhatch.getPrice() + "/人");
                /**设置有没有过期*/
                if (dayWhatch.getPast() == 1) {
                    holder.yujia_bg.setBackgroundResource(R.drawable.kapian_hui);
                } else {
                    holder.yujia_bg.setBackgroundResource(R.drawable.kapian_bai);
                }

            } else {//表示单车
                holder.youyang_bg.setVisibility(View.INVISIBLE);
                holder.yujia_bg.setVisibility(View.INVISIBLE);

                holder.dance_title_tv.setText(dayWhatch.getCourse_name());
                holder.dance_date_tv.setText("(" + create_date.getMonth() + "/" + create_date.getDate() + ")");
                holder.dance_user_tv.setText(dayWhatch.getTeacher_name());
                holder.dance_money_tv.setText("￥" + dayWhatch.getPrice() + "/人");
                /**设置有没有过期*/
                if (dayWhatch.getPast() == 1) {
                    holder.dance_bg.setBackgroundResource(R.drawable.kapian_hui);
                } else {
                    holder.dance_bg.setBackgroundResource(R.drawable.kapian_bai);
                }
            }

            holder.youyang_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    select_more_popwindow(dayWhatch);
                }
            });
            holder.yujia_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    select_more_popwindow(dayWhatch);

                }
            });
            holder.dance_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    select_more_popwindow(dayWhatch);

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

    private void select_more_popwindow(DateViewEntity.DateBean dayWhatch) {
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
        TextView class_title_tv = view.findViewById(R.id.class_title_tv);
        TextView people_number_tv = view.findViewById(R.id.people_number_tv);
        TextView teacher_name = view.findViewById(R.id.teacher_name);
        TextView price_tv = view.findViewById(R.id.price_tv);
        TextView class_name = view.findViewById(R.id.class_name);
        TextView teacher_name_tv = view.findViewById(R.id.teacher_name_tv);
        TextView class_room = view.findViewById(R.id.class_room);
        TextView price = view.findViewById(R.id.price);
        TextView people_xianzhi = view.findViewById(R.id.people_xianzhi);
        TextView class_time = view.findViewById(R.id.class_time);
        TextView class_jieshao = view.findViewById(R.id.class_jieshao);
        TextView tips_tv = view.findViewById(R.id.tips_tv);
        class_title_tv.setText(dayWhatch.getCourse_name());
        people_number_tv.setText(dayWhatch.getCome_num() + "/" + dayWhatch.getMax_num());
        teacher_name.setText(dayWhatch.getTeacher_name());
        price_tv.setText(dayWhatch.getPrice() + "元/人");
        price.setText(dayWhatch.getPrice() + "元/人");
        class_name.setText(dayWhatch.getCourse_name());
        teacher_name_tv.setText(dayWhatch.getTeacher_name());
        if (dayWhatch.getPlaceType() == 1) {
            class_room.setText("有氧操房");
        } else if (dayWhatch.getPlaceType() == 2) {
            class_room.setText("动感单车");
        } else if (dayWhatch.getPlaceType() == 3) {
            class_room.setText("瑜伽房");
        }
        people_xianzhi.setText(dayWhatch.getMax_num() + "人");

        class_time.setText(DateHelper.get_Date_str(dayWhatch.getStart_time(),dayWhatch.getEnd_time()));

        class_jieshao.setText(dayWhatch.getCourse_des());

        tips_tv.setText(dayWhatch.getTips());
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

    public void setData(List<DateViewEntity.DateBean> list) {
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
        public TextView time_tv, youyang_title_tv, youyang_date_tv, youyang_user_tv, money_tv, yujia_title_tv, yujia_date_tv, yujia_user_tv, yujia_money_tv, dance_title_tv, dance_date_tv, dance_user_tv, dance_money_tv;
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
                youyang_title_tv = view.findViewById(R.id.youyang_title_tv);
                youyang_date_tv = view.findViewById(R.id.youyang_date_tv);
                youyang_user_tv = view.findViewById(R.id.youyang_user_tv);
                money_tv = view.findViewById(R.id.money_tv);
                yujia_title_tv = view.findViewById(R.id.yujia_title_tv);
                yujia_date_tv = view.findViewById(R.id.yujia_date_tv);
                yujia_user_tv = view.findViewById(R.id.yujia_user_tv);
                yujia_money_tv = view.findViewById(R.id.yujia_money_tv);
                dance_title_tv = view.findViewById(R.id.dance_title_tv);
                dance_date_tv = view.findViewById(R.id.dance_date_tv);
                dance_user_tv = view.findViewById(R.id.dance_user_tv);
                dance_money_tv = view.findViewById(R.id.dance_money_tv);
            }
        }
    }


}
