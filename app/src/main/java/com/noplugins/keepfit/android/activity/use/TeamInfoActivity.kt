package com.noplugins.keepfit.android.activity.use

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.mine.TeacherDetailActivity
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.use.ManagerTeamBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.GlideRoundTransform
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.data.DateHelper
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow
import com.noplugins.keepfit.android.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.activity_team_info.*
import kotlinx.android.synthetic.main.title_activity.*
import org.greenrobot.eventbus.EventBus
import java.util.HashMap

class TeamInfoActivity : BaseActivity() {
    var courseNum = ""
    var status = -1
    override fun initBundle(parms: Bundle?) {
        if (parms != null) {

            courseNum = parms.getString("courseNum").toString()
            status = parms.getInt("status")
            requestData(courseNum)
        }


    }

    override fun initView() {
        setContentView(R.layout.activity_team_info)
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.setOnClickListener {
            finish()
        }
        jujue.setOnClickListener {
            toJujue(title_tv)
        }

        jieshou.setOnClickListener {
            agreeCourse(1,"")
        }

        jump_team_detail.setOnClickListener {
            if (BaseUtils.isFastClick()){
                val intent = Intent(this,TeamDetailActivity::class.java)
                startActivity(intent)
            }
        }
        edit_team_teacher.setOnClickListener {
            if (BaseUtils.isFastClick()){
                val intent = Intent(this,TeacherDetailActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun requestData(courseNum:String){
        val params = HashMap<String, Any>()
        params["courseNum"] = courseNum
//        subscription = Network.getInstance("课程管理", this)
//            .courseDetail(params,
//                ProgressSubscriber("课程管理", object : SubscriberOnNextListener<Bean<ManagerTeamBean>> {
//                    override fun onNext(result: Bean<ManagerTeamBean>) {
//                        setting(result.data)
//                    }
//                    override fun onError(error: String) {
//                        SuperCustomToast.getInstance(applicationContext)
//                            .show(error)
//                    }
//                }, this, false)
//            )
    }

    @SuppressLint("SetTextI18n")
    private fun setting(managerTeamBean: ManagerTeamBean) {

        tv_class_type.text = statusType(status)
        if (status == 3){
            ll_caozuo.visibility = View.VISIBLE
        }
        if (status == 2){
        }

        title_tv.text = managerTeamBean.courseList.courseName
        tv_cg_name.text = managerTeamBean.courseList.areaName
        edit_class_room.text = roomType(managerTeamBean.courseList.type.toInt())+
                "|"+managerTeamBean.courseList.maxNum+"人"
        edit_class_name.text = managerTeamBean.courseList.courseName
        tv_select_type.text =classType(managerTeamBean.courseList.classType)
        tv_team_length.text = "${managerTeamBean.courseList.min}min"
        edit_price.text = "¥"+managerTeamBean.courseList.finalPrice

        if (managerTeamBean.courseList.isLoop){
            edit_cycle.text = ""+managerTeamBean.courseList.loopCycle+"周"
        } else {
            edit_cycle.text = "单次"
        }


        val startHour = DateHelper.getDateByLong(managerTeamBean.courseList.startTime)
        val startDay  = DateHelper.getDateDayByLong(managerTeamBean.courseList.startTime)
        val endHour = DateHelper.getDateByLong(managerTeamBean.courseList.endTime)
        edit_date.text = "$startDay $startHour-$endHour"
//        edit_jieshao.text = ""+managerTeamBean.courseList.courseDes
//        edit_shihe.text = ""+managerTeamBean.courseList.suitPerson
//        edit_zhuyi.text = ""+managerTeamBean.courseList.tips

//        Glide.with(this)
//            .load(managerTeamBean.courseList.imgUrl)
//            .transform(CenterCrop(this), GlideRoundTransform(this,8))
//            .into(iv_team_logo)
    }

    private fun roomType(roomType: Int): String {
        val listClass = resources.getStringArray(R.array.gongneng_types)
        return listClass[roomType - 1]
    }

    private fun classType(classType: Int): String {
        val listClass = resources.getStringArray(R.array.tuanke_types)
        return listClass[classType - 1]
    }

    private fun statusType(type: Int): String {
        val listClass = arrayOf("邀请成功", "邀请失败", "邀请中", "已过期","邀请失败","邀请失败","邀请失败")
        return listClass[type - 1]
    }



    private fun toJujue(view1: TextView) {
        val popupWindow = CommonPopupWindow.Builder(this)
            .setView(R.layout.dialog_to_jujue)
            .setBackGroundLevel(0.5f)//0.5f
            .setAnimationStyle(R.style.main_menu_animstyle)
            .setWidthAndHeight(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(view1)

        /**设置逻辑 */
        val view = popupWindow.contentView
        val cancel = view.findViewById<LinearLayout>(R.id.cancel_layout)
        val sure = view.findViewById<LinearLayout>(R.id.sure_layout)
        val edit = view.findViewById<EditText>(R.id.et_content)
        cancel.setOnClickListener {
            popupWindow.dismiss()
        }
        sure.setOnClickListener {
            popupWindow.dismiss()
            //去申请
            agreeCourse(0,edit.text.toString())

        }
    }

    private fun agreeCourse(type:Int,str:String){
        val params = HashMap<String, Any>()
//        params["teacherNum"] = SpUtils.getString(activity, AppConstants.USER_NAME)
        params["teacherNum"] = SpUtils.getString(applicationContext, AppConstants.USER_NAME)
        params["courseNum"] = courseNum
        params["agree"] = type
        if (type == 0){
            params["refuse"] = str
        }
//        subscription = Network.getInstance("团课同意/拒绝", this)
//            .agreeCourse(params,
//                ProgressSubscriber("团课同意/拒绝", object : SubscriberOnNextListener<Bean<Any>> {
//                    override fun onNext(result: Bean<Any>) {
////                        requestData(courseNum)
//                        if (type == 1){
//                            EventBus.getDefault().post(AppConstants.TEAM_YQ_AGREE)
//                        } else {
//                            EventBus.getDefault().post(AppConstants.TEAM_YQ_REFUSE)
//                        }
//                        finish()
//                    }
//
//                    override fun onError(error: String) {
//                        SuperCustomToast.getInstance(applicationContext)
//                            .show(error)
//                    }
//                }, this, false)
//            )
    }
}
