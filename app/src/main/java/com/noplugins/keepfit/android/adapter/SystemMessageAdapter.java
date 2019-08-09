package com.noplugins.keepfit.android.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.MessageEntity;
import com.noplugins.keepfit.android.util.data.DateHelper;

import java.util.Date;
import java.util.List;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;

public class SystemMessageAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private List<MessageEntity.MessageBean> list;
    private Activity context;
    private static final int EMPTY_VIEW = 2;
    private static final int TYPE_YOUTANG = 1;
    private TextView yaoqing_number_tv;
    private int select_num;
    private int max_selectnum = 5;
    private List<MessageEntity.NoReadBean> readBeans;

    public SystemMessageAdapter(List<MessageEntity.MessageBean> mlist, List<MessageEntity.NoReadBean> mreadBeans, Activity mcontext) {
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
            item_view = LayoutInflater.from(context).inflate(R.layout.system_message_item, parent, false);
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
                    Log.e(TAG, "111进来了吗：");
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, position);
                    }
                }
            });

            if (messageBean.getType() == 4) {//系统更新
                holder.title_tv.setText("系统更新");
            }

            holder.content_tv_msg.setText(messageBean.getMessageCon());
            long time = messageBean.getWithdrawTime();
            Date date = DateHelper.transForDate(time);
            holder.tv_date_time.setText((date.getYear() + 1900) + "." + (date.getMonth() + 1) + "." + date.getDate() + " " + date.getHours() + ":" + date.getMinutes());

            if (readBeans.size() > 0) {//显示消息气泡
                for (int i = 0; i < readBeans.size(); i++) {
                    if (readBeans.get(i).getType() == messageBean.getType()) {
                        if (readBeans.get(i).getNum() > 99) {
                            holder.message_count.setText("99+");
                            holder.message_layout.setVisibility(View.VISIBLE);
                        } else {
                            holder.message_layout.setVisibility(View.VISIBLE);
                            holder.message_count.setText(readBeans.get(i).getNum()+"");
                        }
                    }
                }
            } else {
                holder.message_layout.setVisibility(View.INVISIBLE);
            }
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
        public ImageView left_icon;
        public TextView title_tv, content_tv_msg, message_count, tv_date_time;
        public LinearLayout message_layout;
        public YouYangViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                title_tv = view.findViewById(R.id.title_tv);
                left_icon = view.findViewById(R.id.left_icon);
                content_tv_msg = view.findViewById(R.id.content_tv_msg);
                message_count = view.findViewById(R.id.message_count);
                tv_date_time = view.findViewById(R.id.tv_date_time);
                message_layout =  view.findViewById(R.id.message_layout);
            }
        }
    }
}
