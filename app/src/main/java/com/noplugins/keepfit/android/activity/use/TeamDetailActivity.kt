package com.noplugins.keepfit.android.activity.use

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.ms.banner.BannerConfig
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.use.TeamDetailBean
import com.noplugins.keepfit.android.customize.CustomViewHolder
import com.noplugins.keepfit.android.util.GlideRoundTransform
import kotlinx.android.synthetic.main.activity_team_detail.*

class TeamDetailActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
    }

    override fun doBusiness(mContext: Context?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
    }

    private fun setting(code: TeamDetailBean) {
        //简单使用
        banner
                .setBannerStyle(BannerConfig.NUM_INDICATOR)
                .setPages(code.course.urls, CustomViewHolder())
                .start()

        tv_class_duction.text = code.course.courseDes
        tv_class_mans.text = code.course.suitPerson
        tv_class_tips.text = code.course.tips


        Glide.with(this).load(code.teacher.logoUrl)
                .transform( CenterCrop(this), GlideRoundTransform(this,10))
                .placeholder(R.drawable.logo_gray)
                .into(logo_image)



    }
}
