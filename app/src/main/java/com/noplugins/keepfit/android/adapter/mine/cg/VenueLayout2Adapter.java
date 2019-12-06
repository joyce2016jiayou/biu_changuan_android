package com.noplugins.keepfit.android.adapter.mine.cg;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.bean.DictionaryeBean;

import java.util.List;

public class VenueLayout2Adapter extends BaseQuickAdapter<DictionaryeBean, BaseViewHolder> {

    public VenueLayout2Adapter(@Nullable List<DictionaryeBean> data) {
        super(R.layout.venue_item_2item,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DictionaryeBean item) {
        helper.addOnClickListener(R.id.cb_facilities)
        .addOnClickListener(R.id.ll_item_2);
        helper.setText(R.id.cb_facilities,item.getName());
    }

}
