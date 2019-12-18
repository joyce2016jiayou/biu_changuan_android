package com.noplugins.keepfit.android.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.bean.DictionaryeBean;

import java.util.List;

public class RoomManagerAdapter extends BaseQuickAdapter<DictionaryeBean, BaseViewHolder> {
    public RoomManagerAdapter(@Nullable List<DictionaryeBean> data) {
        super(R.layout.item_room_manager,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DictionaryeBean item) {
        helper.addOnClickListener(R.id.fl_room);
        helper.setText(R.id.tv_room_type,item.getName());
    }
}
