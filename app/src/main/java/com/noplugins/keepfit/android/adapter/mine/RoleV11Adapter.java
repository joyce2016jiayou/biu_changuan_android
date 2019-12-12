package com.noplugins.keepfit.android.adapter.mine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.android.R;

import java.util.List;

public class RoleV11Adapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public RoleV11Adapter(@Nullable List<String> data) {
        super(R.layout.item_role_v11,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.iv_delete);
        helper.setText(R.id.tv_name,"张三");
        helper.setText(R.id.tv_phone,"1777777777");
        helper.setText(R.id.tv_role,"前台");
    }
}
