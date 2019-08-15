package com.noplugins.keepfit.android.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.MessageDetailEntity;
import com.noplugins.keepfit.android.entity.MessageEntity;
import com.noplugins.keepfit.android.util.data.DateHelper;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.toast.SuperCustomToast;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import rx.Subscription;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;

public class AreaSubmitAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private List<MessageEntity.MessageBean> list;
    private Activity context;
    private static final int EMPTY_VIEW = 2;
    private static final int TYPE_YOUTANG = 1;
    private TextView yaoqing_number_tv;
    private int select_num;
    private int max_selectnum = 5;

    public AreaSubmitAdapter(List<MessageEntity.MessageBean> mlist, Activity mcontext) {
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
            item_view = LayoutInflater.from(context).inflate(R.layout.area_submit_item, parent, false);
            holder = new YouYangViewHolder(item_view, true);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder view_holder, int position, boolean isItem) {
        if (view_holder instanceof YouYangViewHolder) {
            YouYangViewHolder holder = (YouYangViewHolder) view_holder;
            MessageEntity.MessageBean messageBean = list.get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (onItemClickListener != null) {
//                        onItemClickListener.onItemClick(v, position);
//                    }
                    get_detail_resouce(holder, messageBean, messageBean.getType());

                }
            });

            // 15操房申请16申请接受17申请拒绝18邀请接收19邀请拒绝
            if (messageBean.getType() == 15) {
                holder.area_type_tv.setText("操房申请");

            } else if (messageBean.getType() == 16) {
                holder.area_type_tv.setText("申请接受");
                holder.submit_layout_center.setVisibility(View.GONE);//隐藏拒绝、同意按钮
                holder.agree_icon.setVisibility(View.VISIBLE);//显示已拒绝
            } else if (messageBean.getType() == 17) {
                holder.area_type_tv.setText("申请拒绝");
                holder.submit_layout_center.setVisibility(View.GONE);//隐藏拒绝、同意按钮
                holder.jujue_icon.setVisibility(View.VISIBLE);//显示已拒绝

            } else if (messageBean.getType() == 18) {
                holder.area_type_tv.setText("邀请接收");
                holder.submit_layout_center.setVisibility(View.GONE);//隐藏拒绝、同意按钮

            } else if (messageBean.getType() == 19) {
                holder.area_type_tv.setText("邀请拒绝");
                holder.submit_layout_center.setVisibility(View.GONE);//隐藏拒绝、同意按钮
            }


            holder.content_tv.setText(messageBean.getMessageCon());
            long time = messageBean.getWithdrawTime();
            Date date = DateHelper.transForDate(time);
            holder.tv_date_time.setText((date.getYear() + 1900) + "." + (date.getMonth() + 1) + "." + date.getDate() + " " + date.getHours() + ":" + date.getMinutes());

            // 同意按钮
            holder.agree_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    post_jujue(messageBean, 1, "", holder);

                }
            });

            //拒绝按钮
            holder.jujue_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.jujue_edit_layout.setVisibility(View.VISIBLE);
                    holder.submit_layout_center.setVisibility(View.GONE);
                }
            });
            //拒绝原因提交按钮
            holder.submit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (TextUtils.isEmpty(holder.edit_reason_tv.getText())) {
                        Toast.makeText(context, "请填写拒绝原因！", Toast.LENGTH_SHORT).show();
                        return;
                    } else {

                        //提交"拒绝"请求
                        post_jujue(messageBean, 0, holder.edit_reason_tv.getText().toString(), holder);
                    }


                }
            });


        }
    }

    /**
     * 获取课程详情
     */
    private void get_detail_resouce(YouYangViewHolder holder, MessageEntity.MessageBean m_messageBean, int type) {
        Map<String, Object> params = new HashMap<>();
        params.put("gymCourseCheckNum", m_messageBean.getGymCourseCheckNum());//场馆编号

        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Log.e(TAG, "详情参数：" + json_params);
        Subscription subscription = Network.getInstance("详情列表", context)
                .get_shenqing_detail(requestBody, new ProgressSubscriberNew<>(MessageDetailEntity.class, new GsonSubscriberOnNextListener<MessageDetailEntity>() {
                    @Override
                    public void on_post_entity(MessageDetailEntity entity, String s) {
                        Log.e("申请详情成功", "申请详情成功:" + entity.getData().getCourseDes());
                        MessageDetailEntity.DataBean dataBean = entity.getData();
                        //设置详情页数据
                        submit_jujue_popwindow(holder, m_messageBean, dataBean, type);


                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("申请详情失败", "申请详情失败:" + error);
                    }
                }, context, true));
    }


    /**
     * 拒绝提交
     */
    private void post_jujue(MessageEntity.MessageBean messageBean, int select_type, String reason, YouYangViewHolder holder) {
        Map<String, Object> params = new HashMap<>();
        String gymAreaNum;
        if ("".equals(SharedPreferencesHelper.get(context, Network.changguan_number, "").toString())) {
            gymAreaNum = "";
        } else {
            gymAreaNum = SharedPreferencesHelper.get(context, Network.changguan_number, "").toString();
        }
        params.put("gymAreaNum", gymAreaNum);//场馆编号
        params.put("gymCourseCheckNum", messageBean.getGymCourseCheckNum());//numner
        params.put("gymMessageNum", messageBean.getMessageNum());//消息numner
        params.put("agreeType", select_type);//0是拒绝，1是同意
        if (select_type == 0) {//表示拒绝，需要填写原因
            params.put("reason", reason);//消息numner
        }
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        Log.e(TAG, "拒绝参数：" + json_params);
        Subscription subscription = Network.getInstance("拒绝列表", context)
                .agreeApply(requestBody, new ProgressSubscriberNew<>(String.class, new GsonSubscriberOnNextListener<String>() {
                    @Override
                    public void on_post_entity(String entity, String s) {
                        Log.e("申请状态成功", "申请状态成功:" + entity);

                        if (select_type == 0) {//表示拒绝
                            //弹窗提示拒绝成功
                            SuperCustomToast toast = SuperCustomToast.getInstance(context);
                            toast.setDefaultTextColor(context.getResources().getColor(R.color.top_heiziti));
                            toast.show("提交成功！", R.layout.success_toast, R.id.content_toast, context);
                            holder.agree_icon.setVisibility(View.GONE);
                            holder.jujue_icon.setVisibility(View.VISIBLE);
                            holder.jujue_edit_layout.setVisibility(View.GONE);
                            holder.submit_layout_center.setVisibility(View.GONE);

                            messageBean.setType(17);//已拒绝状态
                            notifyDataSetChanged();

                        } else {//表示同意
                            holder.agree_icon.setVisibility(View.VISIBLE);
                            holder.jujue_icon.setVisibility(View.GONE);
                            holder.submit_layout_center.setVisibility(View.GONE);
                            //弹窗提示同意成功
                            SuperCustomToast toast = SuperCustomToast.getInstance(context);
                            toast.setDefaultTextColor(context.getResources().getColor(R.color.top_heiziti));
                            toast.show("提交成功！", R.layout.success_toast, R.id.content_toast, context);
                            messageBean.setType(16);//已拒绝状态
                            notifyDataSetChanged();


                        }


                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("申请状态失败", "申请状态失败:" + error);
                    }
                }, context, true));
    }

    public static Dialog jujue_dialog;

    private void submit_jujue_popwindow(YouYangViewHolder holder, MessageEntity.MessageBean m_messageBean, MessageDetailEntity.DataBean messageBean, int type) {
        LayoutInflater factory = LayoutInflater.from(context);
        View view = factory.inflate(R.layout.jujue_pop_detial, null);
        jujue_dialog = new Dialog(context, R.style.transparentFrameWindowStyle2);
        jujue_dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = jujue_dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        jujue_dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        jujue_dialog.setCanceledOnTouchOutside(true);
        jujue_dialog.show();
        /**操作*/

        LinearLayout jujue_btn = view.findViewById(R.id.jujue_btn);
        LinearLayout submit_layout_center = view.findViewById(R.id.submit_layout_center);
        LinearLayout jujue_edit_layout = view.findViewById(R.id.jujue_edit_layout);
        LinearLayout submit_btn = view.findViewById(R.id.submit_btn);
        ImageView close_btn = view.findViewById(R.id.close_btn);
        TextView type_tv = view.findViewById(R.id.type_tv);


        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jujue_dialog.dismiss();
            }
        });
        //同意按钮
        LinearLayout agree_btn = view.findViewById(R.id.agree_btn);
        agree_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_jujue(m_messageBean, 1, "", holder);

                jujue_dialog.dismiss();

            }
        });
        //拒绝按钮
        jujue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit_layout_center.setVisibility(View.GONE);
                jujue_edit_layout.setVisibility(View.VISIBLE);

            }
        });

        EditText edit_reason_tv = view.findViewById(R.id.edit_reason_tv);
        //拒绝提交按钮
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edit_reason_tv.getText())) {
                    Toast.makeText(context, "请填写拒绝原因！", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //提交"拒绝"请求
                    post_jujue(m_messageBean, 0, holder.edit_reason_tv.getText().toString(), holder);
                    jujue_dialog.dismiss();

                }

            }
        });

        //显示数据
        TextView class_name = view.findViewById(R.id.class_name);
        TextView teacher_name = view.findViewById(R.id.teacher_name);
        TextView class_room = view.findViewById(R.id.class_room);
        TextView price_tv = view.findViewById(R.id.price_tv);
        TextView people_number_xianzhi = view.findViewById(R.id.people_number_xianzhi);
        TextView class_jieshao_content = view.findViewById(R.id.class_jieshao_content);
        TextView zhuyi_shixiang_tv = view.findViewById(R.id.zhuyi_shixiang_tv);
        // TODO: 2019-08-06
        // 15操房申请16申请接受17申请拒绝18邀请接收19邀请拒绝
        if (type == 15) {
            type_tv.setText("操房申请");
        } else if (type == 16) {
            type_tv.setText("已同意");
            submit_layout_center.setVisibility(View.GONE);//隐藏拒绝、同意按钮
        } else if (type == 17) {
            type_tv.setText("已拒绝");
            submit_layout_center.setVisibility(View.GONE);//隐藏拒绝、同意按钮

        } else if (type == 18) {
            type_tv.setText("已接受");
            submit_layout_center.setVisibility(View.GONE);//隐藏拒绝、同意按钮

        } else if (type == 19) {
            type_tv.setText("已拒绝");
            submit_layout_center.setVisibility(View.GONE);//隐藏拒绝、同意按钮
        }
        class_name.setText(messageBean.getCourseName());
        teacher_name.setText(messageBean.getTeacherName());
        if (messageBean.getPlaceType() == 1) {
            class_room.setText("有氧操房");
        } else if (messageBean.getPlaceType() == 2) {
            class_room.setText("动感单车");
        } else {
            class_room.setText("瑜伽房");
        }
        price_tv.setText(messageBean.getPrice() + "/人");
        people_number_xianzhi.setText(messageBean.getMaxPerson() + "人");
        class_jieshao_content.setText(messageBean.getCourseDes());
        zhuyi_shixiang_tv.setText(messageBean.getTips());

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


    public void setData(List<MessageEntity.MessageBean> list) {
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
        public LinearLayout agree_btn, jujue_btn, agree_icon, jujue_icon, jujue_edit_layout, submit_layout_center, submit_btn;
        public TextView title_tv, tv_date_time, area_type_tv, content_tv;
        public CircleImageView touxiang_image;
        public EditText edit_reason_tv;

        public YouYangViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                agree_btn = view.findViewById(R.id.agree_btn);
                title_tv = view.findViewById(R.id.title_tv);
                tv_date_time = view.findViewById(R.id.tv_date_time);
                touxiang_image = view.findViewById(R.id.touxiang_image);
                jujue_btn = view.findViewById(R.id.jujue_btn);
                agree_icon = view.findViewById(R.id.agree_icon);
                jujue_icon = view.findViewById(R.id.jujue_icon);
                jujue_edit_layout = view.findViewById(R.id.jujue_edit_layout);
                submit_layout_center = view.findViewById(R.id.submit_layout_center);
                submit_btn = view.findViewById(R.id.submit_btn);
                area_type_tv = view.findViewById(R.id.area_type_tv);
                content_tv = view.findViewById(R.id.content_tv);
                edit_reason_tv = view.findViewById(R.id.edit_reason_tv);
            }
        }
    }
}
