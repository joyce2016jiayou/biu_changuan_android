package com.noplugins.keepfit.android.util.ui.gallery_recycleview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.bean.TeacherBean;
import com.noplugins.keepfit.android.entity.ClassDetailEntity;
import com.noplugins.keepfit.android.util.GlideRoundTransform;

import java.util.List;

public class RecyclerView1Adapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<ClassDetailEntity.TeacherListBean> submit_tescher_list;

    public RecyclerView1Adapter(List<ClassDetailEntity.TeacherListBean> m_submit_tescher_list, Context mContext) {
        submit_tescher_list = m_submit_tescher_list;
        this.mContext = mContext;
    }

    @NonNull
    @Override

    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.invite_teacher_item, null);
        // 自定义view的宽度,控制一屏显示的个数
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int width = mContext.getResources().getDisplayMetrics().widthPixels;
        params.width = width / 3;   //这里每屏显示3个<将屏幕平均分为3份
        view.setLayoutParams(params);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Log.e("金石可镂", "就是开荆防颗粒");
        TextView pingfen_tv = holder.itemView.findViewById(R.id.pingfen_tv);
        TextView teacher_name_tv = holder.itemView.findViewById(R.id.teacher_name_tv);
        TextView invite_status_tv = holder.itemView.findViewById(R.id.invite_status_tv);
        ImageView teacher_img = holder.itemView.findViewById(R.id.teacher_img);
        TextView tv_tips = holder.itemView.findViewById(R.id.tv_tips);

        ClassDetailEntity.TeacherListBean teacherBean = submit_tescher_list.get(position);
        pingfen_tv.setText(teacherBean.getFinalGrade() + "分");
        teacher_name_tv.setText(teacherBean.getTeacherName());
        //设置图片圆角角度


        Glide.with(mContext).load(teacherBean.getLogoUrl())
                .transform(new CenterCrop(mContext), new GlideRoundTransform(mContext, 10))
                .into(teacher_img);

        holder.itemView.setTag(position);
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                recycler.smoothScrollToPosition(position); 支持点击每一项滑动切换
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取数据
                    mOnItemClickListener.onItemClick(v, v.getTag().toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return submit_tescher_list.size();
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

}
