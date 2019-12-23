package com.noplugins.keepfit.android.util.ui.gallery_recycleview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private View view;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }

    public View getView() {
        return view;
    }
}
