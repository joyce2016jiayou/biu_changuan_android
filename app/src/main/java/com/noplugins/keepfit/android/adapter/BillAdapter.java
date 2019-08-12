package com.noplugins.keepfit.android.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.BillEntity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BillAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private List<BillEntity.BillItemBean> list;
    private Activity context;
    private static final int EMPTY_VIEW = 2;
    private static final int TYPE_YOUTANG = 1;
    public BillAdapter(List<BillEntity.BillItemBean> mlist, Activity mcontext) {
        list = mlist;
        context = mcontext;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        BillViewHolder billViewHolder = new BillViewHolder(view, false);
        return billViewHolder;
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
            item_view = LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false);
            holder = new BillViewHolder(item_view, true);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder view_holder, int position, boolean isItem) {
        if (view_holder instanceof BillViewHolder) {
            BillViewHolder holder = (BillViewHolder) view_holder;
            BillEntity.BillItemBean billItemBean = list.get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.user_type.setText(billItemBean.getProjectName());
            holder.tv_date_time.setText(billItemBean.getTime());
            //设置内容
            holder.content_tv.setText(billItemBean.getProjectContent());

            //提现账单
            if (billItemBean.getType() == 4){
                holder.tv_count.setTextColor(Color.RED);
            } else {
                holder.tv_count.setTextColor(Color.GREEN);
            }
            //设置消息数目
            holder.tv_count.setText(billItemBean.getMoney());

        }
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


    public void setData(List<BillEntity.BillItemBean> list) {
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


    public class BillViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public CircleImageView touxiang_image;
        public TextView title_tv, user_type, content_tv, tv_date_time, tv_count;

        public BillViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                touxiang_image = view.findViewById(R.id.touxiang_image);
                user_type = view.findViewById(R.id.user_type);
                content_tv = view.findViewById(R.id.content_tv);
                tv_date_time = view.findViewById(R.id.tv_date_time);
                tv_count = view.findViewById(R.id.tv_count);
            }
        }
    }
}
