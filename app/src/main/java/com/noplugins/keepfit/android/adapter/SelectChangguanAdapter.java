package com.noplugins.keepfit.android.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bumptech.glide.load.resource.file.StreamFileDataLoadProvider;
import com.noplugins.keepfit.android.KeepFitActivity;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.activity.SelectChangGuanActivity;
import com.noplugins.keepfit.android.base.MyApplication;
import com.noplugins.keepfit.android.bean.DictionaryeBean;
import com.noplugins.keepfit.android.bean.SelectChangGuanBean;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.ActivityCollectorUtil;
import com.noplugins.keepfit.android.util.BaseUtils;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

public class SelectChangguanAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private List<SelectChangGuanBean> list;
    private Context context;
    private static final int EMPTY_VIEW = 2;
    private static final int RESOURCE_VIEW = 1;
    SelectChangGuanActivity selectChangGuanActivity;

    public SelectChangguanAdapter(List<SelectChangGuanBean> mlist, Context mcontext, SelectChangGuanActivity m_selectChangGuanActivity) {
        list = mlist;
        selectChangGuanActivity = m_selectChangGuanActivity;
        context = mcontext;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        ViewHolder youYangViewHolder = new ViewHolder(view, false);
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
            item_view = LayoutInflater.from(context).inflate(R.layout.select_date_empty_view, parent, false);
            holder = new EmptyViewHolder(item_view, false);
        } else if (viewType == RESOURCE_VIEW) {
            item_view = LayoutInflater.from(context).inflate(R.layout.select_changguan_item, parent, false);
            holder = new ViewHolder(item_view, true);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder view_holder, int position, boolean isItem) {
        if (view_holder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) view_holder;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, position);
                    }
                }
            });
            SelectChangGuanBean selectChangGuanBean = list.get(position);
            holder.changguan_name.setText(selectChangGuanBean.getAreaName());
            holder.address_tv.setText(selectChangGuanBean.getAddress());
            holder.time_tv.setText(selectChangGuanBean.getStart() + "-" + selectChangGuanBean.getEnd());
            if (selectChangGuanBean.isIsFront()) {
                holder.select_bg.setBackgroundResource(R.drawable.select_changguan_bg);
            } else {
                holder.select_bg.setBackground(null);
            }
            holder.select_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (BaseUtils.isFastClick()) {

                        //切换场馆
                        qiehuan_changguan(selectChangGuanBean.getAreaNum());
                    }

                }

            });
        }
    }

    private void qiehuan_changguan(String areaNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("userNum", SpUtils.getString(selectChangGuanActivity, AppConstants.USER_NAME));
        params.put("areaNum", areaNum);
        Subscription subscription = Network.getInstance("切换场馆", context)
                .qiehuan_changguans(params,
                        new ProgressSubscriber<>("切换场馆", new SubscriberOnNextListener<Bean<Object>>() {
                            @Override
                            public void onNext(Bean<Object> result) {
                                MyApplication.destoryActivity("KeepFitActivity");
                                Intent intent = new Intent(context, KeepFitActivity.class);
                                SpUtils.putString(context, AppConstants.CHANGGUAN_NUM, areaNum);
                                selectChangGuanActivity.startActivity(intent);
                                selectChangGuanActivity.finish();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, selectChangGuanActivity, true));
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
            return RESOURCE_VIEW;
        }
    }


    @Override
    public int getAdapterItemCount() {
        return list.size() > 0 ? list.size() : 1;
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


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView changguan_name, time_tv, address_tv;
        public LinearLayout select_bg;

        public ViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                changguan_name = view.findViewById(R.id.changguan_name);
                time_tv = view.findViewById(R.id.time_tv);
                address_tv = view.findViewById(R.id.address_tv);
                select_bg = view.findViewById(R.id.select_bg);
            }
        }
    }
}
