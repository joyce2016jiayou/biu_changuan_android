package com.noplugins.keepfit.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.noplugins.keepfit.android.R;

import java.util.ArrayList;

public class HightLowTimeAdapter extends RecyclerView.Adapter<HightLowTimeAdapter.ViewHolder> {


    public HightLowTimeAdapter(Context context, ArrayList<Object> data) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAdd;
        private TextView tvStartTime;
        private TextView tvEndTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAdd = itemView.findViewById(R.id.ivAdd);
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
        }
    }
}
