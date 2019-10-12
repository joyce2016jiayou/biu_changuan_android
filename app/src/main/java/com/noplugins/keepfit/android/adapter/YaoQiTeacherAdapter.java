package com.noplugins.keepfit.android.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.activity.AddClassItemActivity;
import com.noplugins.keepfit.android.activity.YaoQingTeacherActivity;
import com.noplugins.keepfit.android.entity.AddClassEntity;
import com.noplugins.keepfit.android.entity.InViteEntity;
import com.noplugins.keepfit.android.entity.TeacherEntity;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import rx.Subscription;

import static com.umeng.socialize.net.dplus.CommonNetImpl.TAG;

public class YaoQiTeacherAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {

    private List<TeacherEntity.TeacherBean> list;
    private Activity context;
    private static final int EMPTY_VIEW = 2;
    private static final int TYPE_YOUTANG = 1;
    private TextView yaoqing_number_tv;
    private int select_num;
    private int max_selectnum = 5;
    private String gym_course_num;
    private String gymInviteNum;

    public YaoQiTeacherAdapter(List<TeacherEntity.TeacherBean> mlist, Activity mcontext, TextView myaoqing_number_tv, String m_gym_course_num) {
        list = mlist;
        yaoqing_number_tv = myaoqing_number_tv;
        gym_course_num = m_gym_course_num;
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
            item_view = LayoutInflater.from(context).inflate(R.layout.teacher_item, parent, false);
            holder = new YouYangViewHolder(item_view, true);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder view_holder, int position, boolean isItem) {
        if (view_holder instanceof YouYangViewHolder) {

            YouYangViewHolder holder = (YouYangViewHolder) view_holder;
            TeacherEntity.TeacherBean teacherBean = list.get(position);
            holder.teacher_name.setText(teacherBean.getTeacherName());
            Glide.with(context).load(teacherBean.getLogoUrl()).into(holder.touxiang_image);
            holder.tag_tv.setText(teacherBean.getSkill());
            if (teacherBean.getInviteStatus() == 0) {//获取是否邀请
                holder.yaoqing_tv.setText("取消邀请");
            } else if (teacherBean.getInviteStatus() == 1) {
                holder.yaoqing_tv.setText("已邀请");
            } else if (teacherBean.getInviteStatus() == 2) {
                holder.yaoqing_tv.setText("接受邀请");
            } else if (teacherBean.getInviteStatus() == 3) {
                holder.yaoqing_tv.setText("拒绝邀请");
            } else {
                holder.yaoqing_tv.setText("邀请");
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, position);
                    }
                }
            });


            //邀请和取消邀请按钮
            holder.yaoqing_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (select_num < max_selectnum) {
                        //判断是邀请还是取消邀请
                        if (holder.yaoqing_tv.getText().equals("邀请")) {
                            holder.yaoqing_tv.setText("取消邀请");
                            Log.e("邀请", "邀请");
                            select_num++;

                            //邀请
                            invite(teacherBean);
                        } else {
                            holder.yaoqing_tv.setText("邀请");
                            select_num--;

                            Log.e("取消邀请", "取消邀请");
                            //取消邀请
                            cancel_invite();
                        }
                        yaoqing_number_tv.setText("(" + select_num + "/5)");

                        /*if(teacherBean.getInviteType()==0){

                        }else if(teacherBean.getInviteType()==1){

                        }else if(teacherBean.getInviteType()==2){

                        }else if(teacherBean.getInviteType()==3){

                        }else{


                        }*/

                    } else {
                        Toast.makeText(context, R.string.tv82, Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }

    private void cancel_invite() {
        Map<String, Object> params = new HashMap<>();
        params.put("gymInviteNum", gymInviteNum);//老师编号
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Log.e(TAG, "取消邀请参数：" + json_params);
        Subscription subscription = Network.getInstance("取消邀请", context).
                cancel_invite(requestBody, new ProgressSubscriberNew<>(String.class, new GsonSubscriberOnNextListener<String>() {
                    @Override
                    public void on_post_entity(String entity, String s) {
                        Log.e("取消邀请成功", "取消邀请成功");


                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {

                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        Log.e("取消邀请失败", "取消邀请失败:" + error);
                    }
                }, context, true));
    }

    private void invite(TeacherEntity.TeacherBean teacherBean) {
        Map<String, Object> params = new HashMap<>();
        String gymAreaNum;
        if ("".equals(SharedPreferencesHelper.get(context, "changguan_number", "").toString())) {
            gymAreaNum = "";
        } else {
            gymAreaNum = SharedPreferencesHelper.get(context, "changguan_number", "").toString();
        }
        params.put("gym_area_num", gymAreaNum);//场馆编号
        params.put("gen_teacher_num", teacherBean.getTeacherNum());//场馆编号
        params.put("gym_course_num", gym_course_num);
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Log.e(TAG, "邀请参数：" + json_params);
        Subscription subscription = Network.getInstance("邀请", context).
                invite(requestBody, new ProgressSubscriberNew<>(InViteEntity.class, new GsonSubscriberOnNextListener<InViteEntity>() {
                    @Override
                    public void on_post_entity(InViteEntity entity, String s) {
                        Log.e("邀请成功", "邀请成功" + entity.getData());
                        gymInviteNum = entity.getData();
                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {

                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        Log.e("邀请失败", "邀请失败:" + error);
                    }
                }, context, true));

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


    public void setData(List<TeacherEntity.TeacherBean> list) {
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
        public LinearLayout yaoqing_btn;
        public TextView yaoqing_tv, teacher_name, tag_tv;
        public CircleImageView touxiang_image;

        public YouYangViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                yaoqing_btn = view.findViewById(R.id.yaoqing_btn);
                yaoqing_tv = view.findViewById(R.id.yaoqing_tv);
                teacher_name = view.findViewById(R.id.teacher_name);
                tag_tv = view.findViewById(R.id.tag_tv);
                touxiang_image = view.findViewById(R.id.touxiang_image);
            }
        }
    }
}
