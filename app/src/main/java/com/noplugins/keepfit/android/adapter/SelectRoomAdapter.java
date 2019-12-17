package com.noplugins.keepfit.android.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.ClassDetailEntity;
import com.noplugins.keepfit.android.entity.InformationEntity;

import java.util.List;


public class SelectRoomAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private List<InformationEntity.GymPlacesBean> list;
    private Activity context;
    private static final int EMPTY_VIEW = 2;
    private static final int TYPE_YOUTANG = 1;


    public SelectRoomAdapter(Activity mcontext) {
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
            item_view = LayoutInflater.from(context).inflate(R.layout.add_room_layout, parent, false);
            holder = new YouYangViewHolder(item_view, true);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder view_holder, int position, boolean isItem) {
        if (view_holder instanceof YouYangViewHolder) {
            YouYangViewHolder holder = (YouYangViewHolder) view_holder;
            InformationEntity.GymPlacesBean gymPlacesBean = list.get(position);
            holder.room_name_tv.setText(gymPlacesBean.getPlace_num() + "");
            holder.people_number_tv.setText(gymPlacesBean.getMax_num() + "");
            holder.remove_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(position);
                    notifyDataSetChanged();


                }
            });
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


    public void setData(List<InformationEntity.GymPlacesBean> list) {
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
        public TextView room_name_tv, people_number_tv;
        public ImageView remove_img;

        public YouYangViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                room_name_tv = view.findViewById(R.id.room_name_tv);
                people_number_tv = view.findViewById(R.id.people_number_tv);
                remove_img = view.findViewById(R.id.remove_img);
            }
        }
    }
}
