package com.noplugins.keepfit.android.adapter.mine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.entity.RoleBean;

import java.util.List;

public class RoleV11Adapter extends BaseQuickAdapter<RoleBean.RoleEntity, BaseViewHolder> {
    public RoleV11Adapter(@Nullable List<RoleBean.RoleEntity> data) {
        super(R.layout.item_role_v11,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RoleBean.RoleEntity item) {
        helper.addOnClickListener(R.id.iv_delete);
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_phone,item.getPhone());
        helper.setText(R.id.tv_role,""+item.getUserType());
    }
}
