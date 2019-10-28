package com.noplugins.keepfit.android.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.bean.CgListBean
import com.noplugins.keepfit.android.bean.TeacherBean
import com.noplugins.keepfit.android.util.GlideRoundTransform
import com.noplugins.keepfit.android.util.ui.ZFlowLayout

class ShoukeCgAdapter(data: List<TeacherBean>?) : BaseQuickAdapter<TeacherBean, BaseViewHolder>(R.layout.item_shouke_cg, data) {

    override fun convert(helper: BaseViewHolder, item: TeacherBean) {

        Glide.with(mContext)
                .load(item.logoUrl)
                .transform(CenterCrop(mContext), GlideRoundTransform(mContext, 8))
                .into(helper.getView<View>(R.id.iv_cg_logo) as ImageView)

        helper
                .addOnClickListener(R.id.rl_detail)
                .addOnClickListener(R.id.tv_jujue)
                .addOnClickListener(R.id.rl_jump)
                .addOnClickListener(R.id.tv_jieshou)
        helper.setText(R.id.tv_cg_name, item.teacherName)

        if (item.status == 1) {
            helper.setText(R.id.tv_item, "已绑定")
        } else if (item.status == 2) {
            helper.setText(R.id.tv_item, "已拒绝")
        } else if (item.status == 4) {
            helper.setText(R.id.tv_item, "申请中")
            helper.getView<View>(R.id.ll_caozuo).visibility = View.VISIBLE
        } else if (item.status == 3) {
            helper.setText(R.id.tv_item, "邀请中")
        }

        val skill = (helper.getView<ZFlowLayout>(R.id.fl_private_skill))

        val arr = item.skillList
        val layoutParams =
                ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(30, 30, 30, 30)
        for (i in 0 until arr.size){
            val paramItemView = (mContext as Activity).layoutInflater.inflate(R.layout.adapter_search_histroy, skill,false)
            val keyWordTv = paramItemView.findViewById<TextView>(R.id.tv_content)
            keyWordTv.setPadding(35,5,35,5)
            keyWordTv.text = arr[i]
            skill.addView(paramItemView, layoutParams)
        }

    }
}
