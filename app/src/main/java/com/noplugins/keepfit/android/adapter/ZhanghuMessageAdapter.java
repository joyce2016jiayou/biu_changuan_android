package com.noplugins.keepfit.android.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.MessageEntity;
import com.noplugins.keepfit.android.util.data.DateHelper;

import java.util.Date;
import java.util.List;

public class ZhanghuMessageAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {

    private List<MessageEntity.MessageBean> list;
    private Activity context;
    private static final int EMPTY_VIEW = 2;
    private static final int TYPE_YOUTANG = 1;
    private TextView yaoqing_number_tv;
    private int select_num;
    private int max_selectnum = 5;

    public ZhanghuMessageAdapter(List<MessageEntity.MessageBean> mlist, Activity mcontext) {
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
            item_view = LayoutInflater.from(context).inflate(R.layout.enpty_view, parent, false);
            holder = new EmptyViewHolder(item_view, false);
        } else if (viewType == TYPE_YOUTANG) {
            item_view = LayoutInflater.from(context).inflate(R.layout.zhanghu_message_item, parent, false);
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

            if (messageBean.getType() == 1) {//微信
                Glide.with(context).load(R.drawable.wx_icon).into(holder.left_icon);
                holder.title_tv.setText("微信转出");
                holder.shouzhi_money_tv.setTextColor(context.getResources().getColor(R.color.result_points));

            } else if (messageBean.getType() == 2) {//支付宝
                Glide.with(context).load(R.drawable.ali_icon).into(holder.left_icon);
                holder.title_tv.setText("支付宝转出");
                holder.shouzhi_money_tv.setTextColor(context.getResources().getColor(R.color.result_points));

            } else if (messageBean.getType() == 3) {
                Glide.with(context).load(R.drawable.band_icon).into(holder.left_icon);
                holder.title_tv.setText("银行卡转出");
                holder.shouzhi_money_tv.setTextColor(context.getResources().getColor(R.color.result_points));

            }

            holder.shouzhi_money_tv.setText("+" + messageBean.getFinalWithdrawMoney());//转出金额
            long time = messageBean.getWithdrawTime();
            Date date = DateHelper.transForDate(time);
            holder.tv_date_time.setText((date.getYear() + 1900) + "." + (date.getMonth() + 1) + "." + date.getDate() + " " + date.getHours() + ":" + date.getMinutes());
            holder.money_tv.setText("余额 " + messageBean.getFinalWithdrawBalance());


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
        public TextView title_tv, money_tv, tv_date_time, shouzhi_money_tv;

        public YouYangViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                left_icon = view.findViewById(R.id.left_icon);
                title_tv = view.findViewById(R.id.title_tv);
                money_tv = view.findViewById(R.id.money_tv);
                tv_date_time = view.findViewById(R.id.tv_date_time);
                shouzhi_money_tv = view.findViewById(R.id.shouzhi_money_tv);
            }
        }
    }

}
