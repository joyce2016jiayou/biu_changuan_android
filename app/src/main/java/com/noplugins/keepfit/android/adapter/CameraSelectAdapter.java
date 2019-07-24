package com.noplugins.keepfit.android.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.noplugins.keepfit.android.R;

import java.io.File;
import java.util.List;

public class CameraSelectAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder> {
    private List<String> list;
    private Context context;
    public static int select_positonn=-1;

    public CameraSelectAdapter(List<String> mlist, Context mcontext) {
        list = mlist;
        context = mcontext;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        PurchaseViewHolder purchaseViewHolder = new PurchaseViewHolder(view, false);
        return purchaseViewHolder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        RecyclerView.ViewHolder holder = getViewHolderByViewType(parent);
        return holder;
    }

    private RecyclerView.ViewHolder getViewHolderByViewType(ViewGroup parent) {
        View shidan_view = LayoutInflater.from(context).inflate(R.layout.camare_select_item, parent, false);
        RecyclerView.ViewHolder holder = new PurchaseViewHolder(shidan_view, true);
        return holder;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder view_holder, final int position, boolean isItem) {
        if (view_holder instanceof PurchaseViewHolder) {
            PurchaseViewHolder holder = (PurchaseViewHolder) view_holder;
            ((PurchaseViewHolder) view_holder).view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, position);
                    }
                }
            });
            if(list.get(position).contains("https://syjapppic.oss")){//如果是网络图片
                Glide.with(context).load(list.get(position))
                        .placeholder(R.drawable.morenbg)
                        .error(R.drawable.morenbg)
                        .into(holder.item_img);
            }else{//如果是本地图片
                File file = new File(list.get(position));
                Uri uri=Uri.parse(file.getPath());
                holder.item_img.setImageURI(uri);
//                Glide.with(context).load(file)
//                        .centerCrop()
//                        .placeholder(R.drawable.morenbg)
//                        .error(R.drawable.morenbg)
//                        .into(holder.item_img);
            }

            //设置选中状态
            if(select_positonn==position){
                holder.biankuang_layout.setVisibility(View.VISIBLE);
            }else{
                holder.biankuang_layout.setVisibility(View.INVISIBLE);
            }
        }
    }


    public void setData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getAdapterItemCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    public class PurchaseViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private ImageView item_img;
        private LinearLayout biankuang_layout;
        public PurchaseViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                this.view = itemView;
                item_img = (ImageView) itemView.findViewById(R.id.item_img);
                biankuang_layout = (LinearLayout) itemView.findViewById(R.id.biankuang_layout);
            }
        }
    }
}
