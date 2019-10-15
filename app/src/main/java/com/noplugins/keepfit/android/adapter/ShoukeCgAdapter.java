package com.noplugins.keepfit.android.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.bean.CgListBean;
import com.noplugins.keepfit.android.util.GlideRoundTransform;

import java.util.List;

public class ShoukeCgAdapter extends BaseQuickAdapter<CgListBean.AreaListBean, BaseViewHolder> {
    public ShoukeCgAdapter(@Nullable List<CgListBean.AreaListBean> data) {
        super(R.layout.item_shouke_cg,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CgListBean.AreaListBean item) {

        Glide.with(mContext)
                .load(item.getLogo())
                .transform(new CenterCrop(mContext),new GlideRoundTransform(mContext,8))
                .into((ImageView) helper.getView(R.id.iv_cg_logo));

        helper
                .addOnClickListener(R.id.rl_detail)
                .addOnClickListener(R.id.tv_jujue)
                .addOnClickListener(R.id.rl_jump)
                .addOnClickListener(R.id.tv_jieshou);
        helper.setText(R.id.tv_cg_name,item.getAreaName());
        helper.setText(R.id.tv_cg_ar,item.getDistance()+"m");
        helper.setText(R.id.tv_address,item.getAddress());

        //

        if (item.getStatus() == 1){
            helper.setText(R.id.tv_item,"已绑定");
        } else if(item.getStatus() == 2){
            helper.setText(R.id.tv_item,"已拒绝");
        }else if(item.getStatus() == 4){
            helper.setText(R.id.tv_item,"申请中");
        }else if(item.getStatus() == 3){
            helper.setText(R.id.tv_item,"邀请中");
            helper.getView(R.id.ll_caozuo).setVisibility(View.VISIBLE);
        }

    }
}
