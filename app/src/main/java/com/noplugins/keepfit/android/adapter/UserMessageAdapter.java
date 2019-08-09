package com.noplugins.keepfit.android.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.MessageEntity;
import com.noplugins.keepfit.android.util.data.DateHelper;
import com.noplugins.keepfit.android.util.ui.message_icon.DragBubbleView;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserMessageAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private List<MessageEntity.MessageBean> list;
    private Activity context;
    private static final int EMPTY_VIEW = 2;
    private static final int TYPE_YOUTANG = 1;
    private TextView yaoqing_number_tv;
    private int select_num;
    private int max_selectnum = 5;
    private List<MessageEntity.NoReadBean> readBeans;

    public UserMessageAdapter(List<MessageEntity.MessageBean> mlist, List<MessageEntity.NoReadBean> mreadBeans, Activity mcontext) {
        list = mlist;
        context = mcontext;
        readBeans = mreadBeans;

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
            item_view = LayoutInflater.from(context).inflate(R.layout.user_message_item, parent, false);
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
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, position);
                    }
                }
            });
            //5超时提醒6进场提醒7课程购买8场馆预约9场馆取消10订单取消11教练进馆12用户进馆13用户离馆14教练离馆
            if (messageBean.getType() == 5) {
                holder.user_type.setText("超时提醒");
            } else if (messageBean.getType() == 6) {
                holder.user_type.setText("进场提醒");
            } else if (messageBean.getType() == 7) {
                holder.user_type.setText("课程购买");
            } else if (messageBean.getType() == 8) {
                holder.user_type.setText("场馆预约");
            } else if (messageBean.getType() == 9) {
                holder.user_type.setText("场馆取消");
            } else if (messageBean.getType() == 10) {
                holder.user_type.setText("订单取消");
            } else if (messageBean.getType() == 11) {
                holder.user_type.setText("教练进馆");
            } else if (messageBean.getType() == 12) {
                holder.user_type.setText("用户进馆");
            } else if (messageBean.getType() == 13) {
                holder.user_type.setText("教练离馆");
            }
            //设置日期
            long time = messageBean.getWithdrawTime();
            Date date = DateHelper.transForDate(time);
            holder.tv_date_time.setText((date.getYear() + 1900) + "." + (date.getMonth() + 1) + "." + date.getDate() + " " + date.getHours() + ":" + date.getMinutes());
            //设置内容
            holder.content_tv.setText(messageBean.getMessageCon());
            //设置消息数目
            if (readBeans.size() > 0) {//显示消息气泡
                for (int i = 0; i < readBeans.size(); i++) {
                    if (readBeans.get(i).getType() == messageBean.getType()) {
                        if (readBeans.get(i).getNum() > 99) {
                            holder.tv_count.setText("99+");
                            holder.message_layout.setVisibility(View.VISIBLE);
                        } else {
                            holder.message_layout.setVisibility(View.VISIBLE);
                            holder.tv_count.setText(readBeans.get(i).getNum()+"");
                        }
                    }
                }
            } else {
                holder.message_layout.setVisibility(View.INVISIBLE);
            }
        }
    }

    DragBubbleView.OnBubbleStateListener onBubbleStateListener = new DragBubbleView.OnBubbleStateListener() {
        @Override
        public void onDrag() {//拖拽气泡

        }

        @Override
        public void onMove() {//移动气泡

        }

        @Override
        public void onRestore() {//气泡恢复原来位置

        }

        @Override
        public void onDismiss() {//气泡消失

        }
    };
    private SystemMessageAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(SystemMessageAdapter.OnItemClickListener onItemClickListener) {
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
        public CircleImageView touxiang_image;
        public TextView title_tv, user_type, content_tv, tv_date_time, tv_count;
        public LinearLayout message_layout;

        public YouYangViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                touxiang_image = view.findViewById(R.id.touxiang_image);
                user_type = view.findViewById(R.id.user_type);
                content_tv = view.findViewById(R.id.content_tv);
                tv_date_time = view.findViewById(R.id.tv_date_time);
                tv_count = view.findViewById(R.id.tv_count);
                message_layout = view.findViewById(R.id.message_layout);
            }
        }
    }
}
