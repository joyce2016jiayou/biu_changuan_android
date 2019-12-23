package com.noplugins.keepfit.android.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.bean.CgBindingBean;
import com.noplugins.keepfit.android.bean.TeacherBean;
import com.noplugins.keepfit.android.entity.TeacherEntity;
import com.noplugins.keepfit.android.util.ui.speed_recyclerview.AdapterMeasureHelper;

import java.util.List;


public class InviteTeacherAdapter extends RecyclerView.Adapter<InviteTeacherAdapter.ViewHolder> {
    private List<TeacherBean> list;
    private Activity context;
    private AdapterMeasureHelper mCardAdapterHelper = new AdapterMeasureHelper();

    public InviteTeacherAdapter(List<TeacherBean> mlist, Activity mcontext) {
        list = mlist;
        context = mcontext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_teacher_item, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolder(itemView);


    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pingfen_tv, teacher_name_tv, invite_status_tv;
        public ImageView teacher_img;

        public ViewHolder(View itemView) {
            super(itemView);
            pingfen_tv = itemView.findViewById(R.id.pingfen_tv);
            teacher_name_tv = itemView.findViewById(R.id.teacher_name_tv);
            invite_status_tv = itemView.findViewById(R.id.invite_status_tv);
            teacher_img = itemView.findViewById(R.id.teacher_img);

        }
    }
}
