package com.noplugins.keepfit.android.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.bean.HighBean;

import java.util.List;

public class HighAdapter extends BaseQuickAdapter<HighBean, BaseViewHolder> {
    public HighAdapter(@Nullable List<HighBean> data) {
        super(R.layout.item_high,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HighBean item) {
        helper.setText(R.id.tv_time_part,"时段：  "+item.getHigh_time_start()+"-"+item.getHigh_time_end());
        helper.setText(R.id.tv_price,"价格：  ¥"+item.getHigh_time_price());
        helper.addOnClickListener(R.id.iv_delete);
    }
}
