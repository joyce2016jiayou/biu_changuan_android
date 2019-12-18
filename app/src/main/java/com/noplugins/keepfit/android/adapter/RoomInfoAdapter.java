package com.noplugins.keepfit.android.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.bean.DictionaryeBean;
import com.noplugins.keepfit.android.bean.use.RoomBean;

import java.util.List;

public class RoomInfoAdapter extends BaseQuickAdapter<RoomBean, BaseViewHolder> {
    public RoomInfoAdapter(@Nullable List<RoomBean> data) {
        super(R.layout.item_room_info,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RoomBean item) {
        helper.addOnClickListener(R.id.tv_room_delete);
        helper.setText(R.id.tv_room_name,item.getPlaceName());

        helper.setText(R.id.tv_room_max,"容纳人数："+item.getMaxNum()+"人");
    }
}
