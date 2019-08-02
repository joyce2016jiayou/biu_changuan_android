package com.noplugins.keepfit.android.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.ClassEntity;
import com.noplugins.keepfit.android.util.data.DateHelper;

import java.util.Date;
import java.util.List;

public class AddClassAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private List<ClassEntity.DataBean> list;
    private Activity context;
    private static final int EMPTY_VIEW = 2;
    private static final int TYPE_YOUTANG = 1;



    public AddClassAdapter(List<ClassEntity.DataBean> mlist, Activity mcontext) {
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
            holder = new EmptyViewHolder(item_view, false);
        } else if (viewType == TYPE_YOUTANG) {
            item_view = LayoutInflater.from(context).inflate(R.layout.class_item, parent, false);
            holder = new YouYangViewHolder(item_view, true);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder view_holder, int position, boolean isItem) {
        if (view_holder instanceof YouYangViewHolder) {
            YouYangViewHolder holder = (YouYangViewHolder) view_holder;
            ClassEntity.DataBean dataBean = list.get(position);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, position);
                    }
                }
            });
            //课程名称+课程类型+教练名字
            holder.class_title_tv.setText(dataBean.getCourse_name()+"·"+dataBean.getType()+"·"+dataBean.getTeacherName());
            holder.class_changdi.setText(dataBean.getPlaceName()+"");
            holder.class_people_number.setText(dataBean.getMax_num()+"");
            holder.class_minute.setText(dataBean.getCourse_time()+"");
            holder.class_xunhuan.setText(dataBean.getLoop_cycle()+"周循环");
            Date create_date = DateHelper.transForDate(dataBean.getCreate_date());
            holder.create_date.setText(create_date.getYear()+"."+create_date.getMonth()+"."+create_date.getDate());
            holder.create_time.setText(create_date.getHours()+":"+create_date.getMinutes());
            Date end_date = DateHelper.transForDate(dataBean.getEnd_time());
            holder.end_date.setText(end_date.getYear()+"."+end_date.getMonth()+"."+end_date.getDate());
            holder.end_time.setText(end_date.getHours()+":"+end_date.getMinutes());
        }
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public int getAdapterItemViewType(int position) {
        if (list.size() == 0) {
            return EMPTY_VIEW;
        } else {
            return TYPE_YOUTANG;
        }
    }




    @Override
    public int getAdapterItemCount() {
        return list.size() > 0 ? list.size() : 1;
    }


    public void setData(List<ClassEntity.DataBean> list) {
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
        public TextView class_title_tv,class_status,class_changdi,
                class_people_number,class_minute,class_xunhuan,create_date,end_date,
                create_time,end_time;

        public YouYangViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                class_title_tv = view.findViewById(R.id.class_title_tv);
                class_status = view.findViewById(R.id.class_status);
                class_changdi = view.findViewById(R.id.class_changdi);
                class_people_number = view.findViewById(R.id.class_people_number);
                class_minute = view.findViewById(R.id.class_minute);
                class_xunhuan = view.findViewById(R.id.class_xunhuan);
                create_date = view.findViewById(R.id.create_date);
                end_date = view.findViewById(R.id.end_date);
                create_time = view.findViewById(R.id.create_time);
                end_time = view.findViewById(R.id.end_time);
            }
        }
    }

}
