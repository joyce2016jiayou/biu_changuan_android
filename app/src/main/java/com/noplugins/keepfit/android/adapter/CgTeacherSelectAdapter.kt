package com.noplugins.keepfit.android.adapter

import android.app.Activity
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.bean.TeacherBean
import com.noplugins.keepfit.android.util.GlideRoundTransform
import com.noplugins.keepfit.android.util.ui.ZFlowLayout

class CgTeacherSelectAdapter(data: List<TeacherBean>?) : BaseQuickAdapter<TeacherBean, BaseViewHolder>(R.layout.item_select_teacher, data) {

    override fun convert(helper: BaseViewHolder, item: TeacherBean) {
        Glide.with(mContext)
                .load(item.logoUrl)
                .transform(CenterCrop(mContext), GlideRoundTransform(mContext, 8))
                .into(helper.getView<ImageView>(R.id.iv_cg_logo))
        helper.addOnClickListener(R.id.rl_detail)
                .addOnClickListener(R.id.ck_select)
        (helper.getView<CheckBox>(R.id.ck_select)).isChecked = false
        helper.setText(R.id.tv_teacher_name, item.teacherName)
        helper.setText(R.id.tv_service_time, "累计服务时长:" + item.serviceDur)
        //fl_private_skill

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
