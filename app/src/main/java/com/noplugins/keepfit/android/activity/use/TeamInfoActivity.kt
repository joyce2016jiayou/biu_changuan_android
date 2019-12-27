package com.noplugins.keepfit.android.activity.use

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.mine.TeacherDetailActivity
import com.noplugins.keepfit.android.activity.mine.TeacherSelectActivity
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.DictionaryeBean
import com.noplugins.keepfit.android.bean.TeacherBean
import com.noplugins.keepfit.android.bean.use.ManagerTeamBean
import com.noplugins.keepfit.android.bean.use.RoomBean
import com.noplugins.keepfit.android.entity.ClassDetailEntity
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.GlideRoundTransform
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.data.DateHelper
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.gallery_recycleview.GalleryLayoutManager
import com.noplugins.keepfit.android.util.ui.gallery_recycleview.RecyclerView1Adapter
import com.noplugins.keepfit.android.util.ui.gallery_recycleview.RecyclerViewAdapter
import com.noplugins.keepfit.android.util.ui.gallery_recycleview.Transformer
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow
import com.noplugins.keepfit.android.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.activity_team_info.*
import kotlinx.android.synthetic.main.title_activity.*
import lib.demo.spinner.MaterialSpinner
import org.greenrobot.eventbus.EventBus
import java.util.HashMap

class TeamInfoActivity : BaseActivity() {
    var courseNum = ""
    var status = -1
    var type = -1
    var isEdit = -1

    var teacherNum = ""
    var statusMsg = ""

    var teacherList: MutableList<ClassDetailEntity.TeacherListBean> = java.util.ArrayList()

    var isRefreshTeacherList: Boolean = false
    var inviteTeacherAdapter: RecyclerView1Adapter? = null

    override fun initBundle(parms: Bundle?) {
        if (parms != null) {

            courseNum = parms.getString("courseNum").toString()
            statusMsg = parms.getString("statusMsg").toString()
            status = parms.getInt("status", -1)
            type = parms.getInt("type", -1)
            isEdit = parms.getInt("isEdit", -1)
            requestData(courseNum)
        }


    }

    override fun initView() {
        setContentView(R.layout.activity_team_info)
        initTeacherAdapter()
        if (type == 2) {
            rl_yaoqin_layout.visibility = View.VISIBLE
        }
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.setOnClickListener {
            finish()
        }
        jujue.setOnClickListener {
            toJujue(title_tv)
        }

        jieshou.setOnClickListener {
            toAgree(title_tv)
        }

        jump_team_detail.setOnClickListener {
            if (BaseUtils.isFastClick()) {
                val intent = Intent(this, TeamDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putString("courseNum", courseNum)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

        edit_team_teacher.setOnClickListener {
            if (BaseUtils.isFastClick()) {
                //已上架 申请中 已下架  有教练姓名
                if (tv_class_type.text.toString() == "已上架" ||
                        tv_class_type.text.toString() == "申请成功" ||
                        tv_class_type.text.toString() == "申请中" ||
                        tv_class_type.text.toString() == "申请失败"||
                        tv_class_type.text.toString() == "邀请成功"||
                        tv_class_type.text.toString() == "拒绝申请") {
                    val intent = Intent(this, TeacherDetailActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("cgNum", teacherNum)
                    bundle.putInt("type", 100)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }

            }
        }

        tv_team_cancel.setOnClickListener {

            Log.d("tag", "weish ?")
            if (BaseUtils.isFastClick()) {
                if (type == 2) {
                    //取消邀请
                    toCancel(title_tv)
                } else {
                    //去编辑
                    val intent = Intent(this, ClassItemEditActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("courseNum", courseNum)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }

            }
        }

        invite_teacher_btn.setOnClickListener {
            if (BaseUtils.isFastClick()) {
                //去邀请
                val intent = Intent(this, TeacherSelectActivity::class.java)
                val bundle = Bundle()
                bundle.putString("courseNum", courseNum)
                intent.putExtras(bundle)
                startActivityForResult(intent, 1)
            }
        }

    }

    private fun initTeacherAdapter() {
        //设置邀请教练视图
        val manager = GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL)
        manager.attach(rv_item, 1000000)
        // 设置滑动缩放效果
        manager.setItemTransformer(Transformer())
        inviteTeacherAdapter = RecyclerView1Adapter(teacherList, this)
        rv_item.setAdapter(inviteTeacherAdapter)
        manager.setOnItemSelectedListener { recyclerView, item, position ->

        }
        inviteTeacherAdapter!!.setmOnItemClickListener { view, data ->
            // 支持手动点击滑动切换
            rv_item.smoothScrollToPosition(Integer.valueOf(data))
        }
    }

    private fun requestData(courseNum: String) {
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

    @SuppressLint("SetTextI18n")
    private fun setting(managerTeamBean: ClassDetailEntity) {
        teacherList.clear()
        //已上架 申请中 已下架  有教练姓名
        //邀请中 邀请失败 已取消  没有
        tv_class_type.text = statusMsg
        if (statusMsg == "申请中") {
            ll_caozuo.visibility = View.VISIBLE
        }
        //已上架 邀请失败 邀请中 已取消 申请中 申请成功 申请失败
        if (statusMsg == "邀请中" ||
                statusMsg == "已取消" ||
                statusMsg == "邀请失败") {
            edit_team_teacher.text = statusMsg
            edit_team_teacher.setTextColor(Color.RED)
            edit_team_teacher.setCompoundDrawables(null, null, null, null)
        }

        if (statusMsg == "已上架" ||
                statusMsg == "申请成功" ||
                statusMsg == "申请中" ||
                statusMsg == "申请失败" ||
                statusMsg == "邀请成功"||
                statusMsg == "拒绝申请") {
            edit_team_teacher.text = managerTeamBean.teacherList[0].teacherName
            teacherNum = managerTeamBean.teacherList[0].teacherNum
        }

        if (type == 2) {
            fl_cancel_edit.visibility = View.VISIBLE
            tv_team_cancel.text = "取消课程"
        }

        if (type == 3 && isEdit == 1) {
            fl_cancel_edit.visibility = View.VISIBLE
            tv_team_cancel.text = "编辑"
        }

        if (managerTeamBean.course.courseName.length > 10) {
            title_tv.text = managerTeamBean.course.courseName.substring(0, 10) + "..."
        } else {
            title_tv.text = managerTeamBean.course.courseName
        }
        tv_cg_name.text = SpUtils.getString(this, AppConstants.CG_NAME)
        edit_class_room.text = roomType(managerTeamBean.course.type.toInt()) +
                "|" + managerTeamBean.course.maxNum + "人"
        edit_class_name.text = managerTeamBean.course.courseName
        tv_select_type.text = classType(managerTeamBean.course.classType)

        tv_xunlian_goal.text = goalType(managerTeamBean.course.target)
        tv_xunlian_difficulty.text = difficultyType(managerTeamBean.course.difficulty)
        tv_team_length.text = "${(managerTeamBean.course.endTime - managerTeamBean.course.startTime) / 1000 / 60}min"
        edit_price.text = "¥" + managerTeamBean.course.finalPrice

        if (managerTeamBean.course.isLoop) {
            edit_cycle.text = "" + managerTeamBean.course.loopCycle + "周"
        } else {
            edit_cycle.text = "单次"
        }


        val startHour = DateHelper.getDateByLong(managerTeamBean.course.startTime)
        val startDay = DateHelper.getDateDayByLong(managerTeamBean.course.startTime)
        val endHour = DateHelper.getDateByLong(managerTeamBean.course.endTime)
        edit_date.text = "$startDay $startHour-$endHour"

        //需要判断 该团课 是场馆创建 还是 教练创建
        //仅在邀请中
        if (type == 2 && managerTeamBean.teacherList.size > 0) {
            rv_item.visibility = View.VISIBLE
            invite_teacher_number_tv.text = "(${managerTeamBean.teacherList.size}/20)"
            teacherList.addAll(managerTeamBean.teacherList)
            inviteTeacherAdapter!!.notifyDataSetChanged()
        }
    }

    private fun roomType(roomType: Int): String {
        val listClass = resources.getStringArray(R.array.gongneng_types)
        return listClass[roomType - 1]
    }

    private fun classType(classType: Int): String {
        val listClass = resources.getStringArray(R.array.tuanke_types)
        return listClass[classType - 1]
    }

    private fun difficultyType(difficulty: Int): String {
        val listClass = resources.getStringArray(R.array.nandu_types)
        return listClass[difficulty - 1]
    }

    private fun goalType(goal: Int): String {
        val listClass = resources.getStringArray(R.array.target_types)
        return listClass[goal - 1]
    }


    private var roomType = ""
    private var roomNumber = ""
    private var roomName = ""
    private var maxNum = ""
    private fun toAgree(view1: TextView) {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.dialog_to_agree)
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
        val cancel = view.findViewById<TextView>(R.id.tv_cancel)
        val sure = view.findViewById<TextView>(R.id.tv_add)
        val msRoomType = view.findViewById<MaterialSpinner>(R.id.ms_room_type)
        val msRoomName = view.findViewById<MaterialSpinner>(R.id.ms_room_name)
        val msRoomMax = view.findViewById<TextView>(R.id.tv_max_number)
        requestRoomType(msRoomType, msRoomName, msRoomMax)
        cancel.setOnClickListener {
            popupWindow.dismiss()
        }
        sure.setOnClickListener {
            popupWindow.dismiss()
            //去申请
            roomType = msRoomType.text.toString()
            roomName = msRoomName.text.toString()
            maxNum = msRoomMax.text.toString()
            agreeCourse(1, "")

        }
    }

    /**
     * 获取所有的房间类型
     */
    private fun requestRoomType(ms: MaterialSpinner, ms2: MaterialSpinner, max: TextView) {
        val params = HashMap<String, Any>()
        params["object"] = "room_type"

        val subscription = Network.getInstance("获取所有的房间类型", this)
                .get_types(params, ProgressSubscriber("获取所有的房间类型",
                        object : SubscriberOnNextListener<Bean<List<DictionaryeBean>>> {
                            override fun onNext(bean: Bean<List<DictionaryeBean>>) {

                                if (bean.data != null) {
                                    val list = bean.data
                                    val data: MutableList<String> = ArrayList()
                                    bean.data.forEach {
                                        data.add(it.name)
                                    }
                                    initMs(ms, ms2, max, list, data)
                                }
                            }

                            override fun onError(error: String) {
                                Toast.makeText(this@TeamInfoActivity, error, Toast.LENGTH_SHORT).show()
                            }
                        }, this, true))

    }

    private fun initMs(ms: MaterialSpinner, ms2: MaterialSpinner, max: TextView
                       , list: List<DictionaryeBean>, data: MutableList<String>) {
        ms.setItems(data)
        ms.selectedIndex = 0
        requestRoom(list[0].value, ms2, max)
        ms.setOnItemSelectedListener { _, position, _, _ ->
            ms.text = list[position].name
            requestRoom(list[position].value, ms2, max)
        }
        ms.setOnNothingSelectedListener { spinner -> spinner.selectedIndex }
    }


    private fun initMsRoomName(ms: MaterialSpinner, list: List<RoomBean>, data: MutableList<String>, max: TextView) {
        ms.setItems(data)
        ms.selectedIndex = 0
        roomNumber = list[0].placeNum
        ms.setOnItemSelectedListener { _, position, _, _ ->
            ms.text = list[position].placeName
            roomNumber = list[position].placeNum
            max.setText(list[position].maxNum)
        }
        ms.setOnNothingSelectedListener { spinner -> spinner.selectedIndex }
    }

    /**
     *根据类型查询所有房间
     */
    private fun requestRoom(type: String, ms: MaterialSpinner, max: TextView) {
        val params = HashMap<String, Any>()
        params["areaNum"] = SpUtils.getString(this, AppConstants.CHANGGUAN_NUM)
        params["placeType"] = type

        val subscription = Network.getInstance("获取房间", this)
                .getAreaPlace(params, ProgressSubscriber("获取房间",
                        object : SubscriberOnNextListener<Bean<List<RoomBean>>> {
                            override fun onNext(bean: Bean<List<RoomBean>>) {
                                if (bean.data != null) {
                                    val data: MutableList<String> = ArrayList()
                                    bean.data.forEach {
                                        data.add(it.placeName)
                                    }
                                    initMsRoomName(ms, bean.data, data, max)
                                }
                            }

                            override fun onError(error: String) {
                                Toast.makeText(this@TeamInfoActivity, error, Toast.LENGTH_SHORT).show()
                            }
                        }, this, false))
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
        val cancel = view.findViewById<TextView>(R.id.tv_cancel)
        val sure = view.findViewById<TextView>(R.id.tv_add)
        val edit = view.findViewById<EditText>(R.id.et_content)
        cancel.setOnClickListener {
            popupWindow.dismiss()
        }
        sure.setOnClickListener {
            popupWindow.dismiss()
            //去申请
            agreeCourse(0, edit.text.toString())

        }
    }

    private fun agreeCourse(type: Int, str: String) {
        val params = HashMap<String, Any>()
        params["areaNum"] = SpUtils.getString(this, AppConstants.CHANGGUAN_NUM)
        params["courseNum"] = courseNum
        params["agree"] = type
        if (type == 0) {
            params["refuse"] = str
        } else {
            params["place_num"] = roomNumber
            params["place_type"] = roomType
            params["place_name"] = roomName
            params["max_num"] = maxNum
        }
        val subscription = Network.getInstance("团课同意/拒绝", this)
                .agreeCourseByArea(
                        params,
                        ProgressSubscriber("团课同意/拒绝", object : SubscriberOnNextListener<Bean<Any>> {
                            override fun onNext(result: Bean<Any>) {
                                //上架成功！
                                if (result.code == 0) {
                                    when (type) {
                                        1 -> {
                                            EventBus.getDefault().post(AppConstants.TEAM_YQ_AGREE)
                                        }

                                        0 -> {
                                            EventBus.getDefault().post(AppConstants.TEAM_YQ_REFUSE)
                                        }
                                    }
                                }

                                SuperCustomToast.getInstance(this@TeamInfoActivity)
                                        .show(result.message)
                            }

                            override fun onError(error: String) {
                                SuperCustomToast.getInstance(this@TeamInfoActivity)
                                        .show(error)
                            }
                        }, this, false)
                )
    }


    /**
     * 取消
     */
    private fun toCancel(view1: TextView) {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.dialog_to_room_delete)
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
        val cancel = view.findViewById<TextView>(R.id.tv_cancel)
        val sure = view.findViewById<TextView>(R.id.tv_add)
        val tvInfo = view.findViewById<TextView>(R.id.tv_username)
        val title = view.findViewById<TextView>(R.id.label_delete_room)
        tvInfo.text = "确定取消邀请?"
        title.text = "取消邀请"
        cancel.setOnClickListener {
            popupWindow.dismiss()
        }
        sure.setOnClickListener {
            popupWindow.dismiss()
            //去申请
            cancelCourse()
//
        }
    }


    private fun cancelCourse() {
        val params = HashMap<String, Any>()
        params["courseNum"] = courseNum
        val subscription = Network.getInstance("团课取消邀请", this)
                .cancelCourseByArea(
                        params,
                        ProgressSubscriber("团课取消邀请", object : SubscriberOnNextListener<Bean<Any>> {
                            override fun onNext(result: Bean<Any>) {
                                //上架成功！
                                if (result.code == 0) {
                                    EventBus.getDefault().post(AppConstants.TEAM_YQ_REFUSE)
                                    finish()
                                }

                                SuperCustomToast.getInstance(this@TeamInfoActivity)
                                        .show(result.message)
                            }

                            override fun onError(error: String) {
                                SuperCustomToast.getInstance(this@TeamInfoActivity)
                                        .show(error)
                            }
                        }, this, false)
                )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data1: Intent?) {
        super.onActivityResult(requestCode, resultCode, data1)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                requestData(courseNum)
            }
        }
    }

}
