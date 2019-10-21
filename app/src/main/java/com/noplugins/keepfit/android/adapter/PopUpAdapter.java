package com.noplugins.keepfit.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.util.BaseUtils;

import java.util.ArrayList;
import java.util.List;

public class PopUpAdapter extends RecyclerView.Adapter<PopUpAdapter.ViewHolder>  {
    private List<Boolean> isClicks;
    private LayoutInflater mInflater;
    private List<String> datas;
//    @Override
//    protected void convert(@NonNull BaseViewHolder helper, String item){
//

//    }

    public PopUpAdapter(Context context, @Nullable List<String> data) {

        mInflater = LayoutInflater.from(context);
        datas = data;
        isClicks = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            isClicks.add(false);

        }
    }

    private OnItemClickListener mOnItemClickListener = null;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Object object, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.spinner_item_drop, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.textView = (TextView) view.findViewById(R.id.tv_spinner_item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (isClicks.get(position)){
            holder.textView.setTextColor(Color.parseColor("#76CEE1"));
        } else {
            holder.textView.setTextColor(Color.parseColor("#6D7278"));
        }

        holder.textView.setText(datas.get(position));

        if (mOnItemClickListener != null) {
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (BaseUtils.isFastClick()){
                        for (int i = 0; i < isClicks.size(); i++) {
                            isClicks.set(i, false);
                        }
                        isClicks.set(position, true);
                        notifyDataSetChanged();
                        mOnItemClickListener.onItemClick(holder.textView, datas.get(position), position);
                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }
        TextView textView;
    }
}