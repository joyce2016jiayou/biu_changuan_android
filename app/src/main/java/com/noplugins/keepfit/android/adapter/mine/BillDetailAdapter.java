package com.noplugins.keepfit.android.adapter.mine;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.bean.mine.BalanceListBean;

import java.util.List;

public class BillDetailAdapter extends BaseQuickAdapter<BalanceListBean.ListBean, BaseViewHolder> {
    public BillDetailAdapter(@Nullable List<BalanceListBean.ListBean> data) {
        super(R.layout.item_bill_detail, data);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(@NonNull BaseViewHolder helper, BalanceListBean.ListBean item) {
        helper.setText(R.id.tv_service_type, typeToString(item.getType()));
        helper.setText(R.id.tv_time, item.getCreateDate());
        if (item.getType() != 1) {
            helper.setText(R.id.tv_money, "+"+item.getFinalMoney());
            ((TextView) helper.getView(R.id.tv_money)).setTextColor(R.color.color_6DD400);
            helper.getView(R.id.tv_tips).setVisibility(View.GONE);
        } else {
            helper.setText(R.id.tv_money, "-"+item.getFinalMoney());
            ((TextView) helper.getView(R.id.tv_money)).setTextColor(R.color.color_F5502F);
            helper.getView(R.id.tv_tips).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_tips, statusToString(item.getStatus()));
        }

        helper.addOnClickListener(R.id.ll_item);


        Glide.with(mContext)
                .load(typeToDrawble(item.getType()))
                .placeholder(R.drawable.logo_gray)
                .into((ImageView) helper.getView(R.id.iv_logo));


    }


    private int typeToDrawble(int type) {
        switch (type) {
            case 1:
                return R.drawable.yinlian;
            case 3:
                return R.drawable.sijiao_logo;
            case 4:
                return R.drawable.team_logo;
            default:
                return R.drawable.yinlian;
        }
    }
    private String typeToString(int type) {
        switch (type) {
            case 1:
                return "银行卡提现";
            case 3:
                return "私教服务";
            case 4:
                return "团课服务";
            default:
                return "未知错误";
        }
    }

    private String statusToString(int type){
        switch (type) {
            case 1:
                return "成功";
            case 2:
                return "失败";
            case 3:
                return "处理中";
            case 4:
                return "已分成";
            default:
                return "未知错误";
        }
    }
}
