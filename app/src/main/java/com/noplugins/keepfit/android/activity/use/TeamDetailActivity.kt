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
import com.noplugins.keepfit.android.entity.ClassDetailEntity
import com.noplugins.keepfit.android.util.GlideRoundTransform
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.android.synthetic.main.title_activity.*
import java.util.HashMap

class TeamDetailActivity : BaseActivity() {
    var courseNum = ""
    override fun initBundle(parms: Bundle?) { if (parms != null) {

        courseNum = parms.getString("courseNum").toString()
    }
    }

    override fun initView() {
        setContentView(R.layout.activity_team_detail)
        requestData(courseNum)
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.setOnClickListener {
            finish()
        }
    }

    private fun setting(code: ClassDetailEntity) {
        title_tv.text = code.course.courseName
        val urlStr = ArrayList<String>()
        urlStr.add(code.course.imgUrl)
        //简单使用
        banner
                .setBannerStyle(BannerConfig.NUM_INDICATOR)
                .setPages(urlStr, CustomViewHolder())
                .start()

        tv_class_duction.text = code.course.courseDes
        tv_class_mans.text = code.course.suitPerson
        tv_class_tips.text = code.course.tips


        Glide.with(this).load(code.course.imgUrl)
                .transform( CenterCrop(this), GlideRoundTransform(this,10))
                .placeholder(R.drawable.logo_gray)
                .into(logo_image)



    }

    private fun requestData(courseNum:String){
        val params = HashMap<String, Any>()
        params["gymCourseNum"] = courseNum
        subscription = Network.getInstance("课程详情", this)
                .class_detail(params,
                        ProgressSubscriber("课程详情", object : SubscriberOnNextListener<Bean<ClassDetailEntity>> {
                            override fun onNext(result: Bean<ClassDetailEntity>) {
                                setting(result.data)
                            }
                            override fun onError(error: String) {
                                SuperCustomToast.getInstance(applicationContext)
                                        .show(error)
                            }
                        }, this, false)
                )
    }
}
