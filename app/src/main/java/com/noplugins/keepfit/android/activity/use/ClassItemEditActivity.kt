package com.noplugins.keepfit.android.activity.use

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.models.album.entity.Photo
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.EditClassDetaiActivity
import com.noplugins.keepfit.android.activity.YaoQingTeacherActivity
import com.noplugins.keepfit.android.adapter.TypeAdapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.callback.DialogCallBack
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.GlideEngine
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.TimeCheckUtil
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.PopWindowHelper
import com.noplugins.keepfit.android.util.ui.cropimg.ClipImageActivity
import com.noplugins.keepfit.android.util.ui.cropimg.FileUtil
import com.noplugins.keepfit.android.util.ui.jiugongge.CCRSortableNinePhotoLayout
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow
import com.othershe.calendarview.utils.CalendarUtil

import java.io.File

import butterknife.BindView
import butterknife.ButterKnife
import cn.qqtheme.framework.wheelpicker.DatePicker
import cn.qqtheme.framework.wheelpicker.TimePicker
import cn.qqtheme.framework.wheelview.annotation.DateMode
import cn.qqtheme.framework.wheelview.annotation.TimeMode
import cn.qqtheme.framework.wheelview.contract.OnDateSelectedListener
import cn.qqtheme.framework.wheelview.contract.OnTimeSelectedListener
import cn.qqtheme.framework.wheelview.entity.DateEntity
import cn.qqtheme.framework.wheelview.entity.TimeEntity
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.noplugins.keepfit.android.activity.mine.TeacherSelectActivity
import com.noplugins.keepfit.android.base.MyApplication
import com.noplugins.keepfit.android.bean.*
import com.noplugins.keepfit.android.bean.TeacherBean
import com.noplugins.keepfit.android.callback.ImageCompressCallBack
import com.noplugins.keepfit.android.entity.*
import com.noplugins.keepfit.android.fragment.BaseInformationFragment.getCompressJpgFileAbsolutePath
import com.noplugins.keepfit.android.util.GlideRoundTransform
import com.noplugins.keepfit.android.util.data.DateHelper
import com.noplugins.keepfit.android.util.ui.ProgressUtil
import com.noplugins.keepfit.android.util.ui.gallery_recycleview.GalleryLayoutManager
import com.noplugins.keepfit.android.util.ui.gallery_recycleview.RecyclerViewAdapter
import com.noplugins.keepfit.android.util.ui.gallery_recycleview.Transformer
import com.noplugins.keepfit.android.util.ui.pop.base.CenterPopupView
import com.noplugins.keepfit.android.util.ui.pop.inteface.ViewCallBack
import com.noplugins.keepfit.android.util.ui.toast.SuperCustomToast
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager
import com.qiniu.android.storage.UploadOptions
import kotlinx.android.synthetic.main.activity_class_item_edit.*
import kotlinx.android.synthetic.main.add_class_item_baseinformation_layout.*
import kotlinx.android.synthetic.main.add_class_item_baseinformation_layout2.*
import kotlinx.android.synthetic.main.add_class_item_baseinformation_layout3.*
import kotlinx.android.synthetic.main.title_activity.*
import kotlinx.android.synthetic.main.title_activity.back_btn
import kotlinx.android.synthetic.main.title_activity.title_tv
import okhttp3.RequestBody
import org.json.JSONObject
import top.zibin.luban.CompressionPredicate
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ClassItemEditActivity : BaseActivity(), CCRSortableNinePhotoLayout.Delegate {

    private var select_target_type = "1"
    private var select_class_type = "1"
    private var select_nandu_type = "容易"
    private var select_xunhuan_type = "单次"
    private var picker: TimePicker? = null
    private var datePicker: DatePicker? = null
    //获取当前的日期
    private var cDate: IntArray? = null
    private val page = 1
    private val dataBeans = ArrayList<ClassEntity.DataBean>()
    private val enable_max_people: Int = 0
    private val class_room_types = ArrayList<ClassTypeEntity>()
    internal var tuanke_types: MutableList<DictionaryeBean> = ArrayList()
    internal var class_difficultys: MutableList<DictionaryeBean> = ArrayList()
    internal var tatget_types: MutableList<DictionaryeBean> = ArrayList()
    internal var start = ""
    internal var end = ""
    private var room_type = ""
    var class_jianjie_tv = ""
    var shihe_renqun_tv = ""
    var zhuyi_shixiang_tv = ""
    private var icon_image_path = ""
    private var max_num = 0
    private val strings = ArrayList<String>()

    internal var inviteTeacherAdapter: RecyclerViewAdapter?=null
    internal var room_lists: MutableList<SelectRoomBean> = ArrayList()
    private var select_room_name = ""
    private var select_room_name_id: String = ""
    private var progress_upload: ProgressUtil? = null
    /**
     * 七牛云
     */
    //指定upToken, 强烈建议从服务端提供get请求获取
    private var uptoken = "xxxxxxxxx:xxxxxxx:xxxxxxxxxx"
    private var sdf: SimpleDateFormat? = null
    private var qiniu_key: String = ""
    private var uploadManager: UploadManager? = null
    private var icon_net_path = ""


    companion object {
        var submit_tescher_list_edit: MutableList<TeacherBean> = ArrayList()
        var is_refresh_teacher_list_edit: Boolean = false
    }

    var courseNum = ""
    override fun initBundle(parms: Bundle?) {
        if (parms != null) {

            courseNum = parms.getString("courseNum").toString()
        }
    }


    override fun initView() {
        setContentLayout(R.layout.activity_class_item_edit)
        title_tv.text = "编辑"

        cDate = CalendarUtil.getCurrent3Date()
        /**七牛云 */
        uploadManager = MyApplication.uploadManager
        sdf = SimpleDateFormat("yyyyMMddHHmmss")
        qiniu_key = "icon_" + sdf!!.format(Date())
        getToken()
        /**七牛云 */

        //设置营业时间
        getYinyeTime()

        select_time()

        //选择团课类型
        get_tuanke_type()
        select_tuanke_type_btn.setOnClickListener { select_class_type_pop() }

        //获取房间类型
        get_class_room_type()
        select_room_type_btn.setOnClickListener { select_room_type_pop() }
        //获取房间数量
        select_room_name_btn.setOnClickListener { select_room_list_pop() }
        //获取课程难度
        get_class_leavel()
        select_class_difficulty_btn.setOnClickListener { select_class_difficulty_pop() }
        //获取训练目标
        get_class_target()
        select_class_target_btn.setOnClickListener { select_class_target_pop() }

        //设置循环
        select_xunhuan_type_btn.setOnClickListener { select_xunhuan_pop() }

        //设置课程详情
        input_class_detail_btn.setOnClickListener {
            val intent = Intent(this@ClassItemEditActivity, EditClassDetaiActivity::class.java)
            intent.putExtra("type", "class_content")
            startActivity(intent)
        }

        //适合人群
        input_shihe_renqun_btn.setOnClickListener {
            val intent = Intent(this@ClassItemEditActivity, EditClassDetaiActivity::class.java)
            intent.putExtra("type", "shihe_renqun")
            startActivity(intent)
        }
        //注意事项
        input_zhuyishixiang_btn.setOnClickListener {
            val intent = Intent(this@ClassItemEditActivity, EditClassDetaiActivity::class.java)
            intent.putExtra("type", "zhuyi_shixiang")
            startActivity(intent)
        }

        //添加封面图
        set_icon_image()
        //设置九宫格
        snpl_moment_add_photos.setDelegate(this)

        //邀请团课老师
        invite_teacher_btn.setOnClickListener {
            val intent = Intent(this@ClassItemEditActivity, TeacherSelectActivity::class.java)
            val bundle = Bundle()
            bundle.putString("enter_type", "edit_page")
            intent.putExtras(bundle)
            startActivity(intent)
        }

        //设置邀请教练视图
        val manager = GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL)
        manager.attach(speed_recyclerview, 1000000)
        // 设置滑动缩放效果
        manager.setItemTransformer(Transformer())
        inviteTeacherAdapter = RecyclerViewAdapter(submit_tescher_list_edit, this)
        speed_recyclerview.adapter = inviteTeacherAdapter
        manager.setOnItemSelectedListener { recyclerView, item, position ->

        }
        inviteTeacherAdapter!!.setmOnItemClickListener { view, data ->
            // 支持手动点击滑动切换
            speed_recyclerview.smoothScrollToPosition(Integer.valueOf(data))
        }

        requestData()
    }

    private fun getToken() {
        subscription = Network.getInstance("登录", this)
                .get_qiniu_token(HashMap(), ProgressSubscriberNew(QiNiuToken::class.java, GsonSubscriberOnNextListener { qiNiuToken, s ->
                    Log.e("获取到的token", "获取到的token" + qiNiuToken.token)
                    uptoken = qiNiuToken.token
                }, object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {}

                    override fun onError(error: String) {
                        Log.e("获取到的token失败", error)
                    }
                }, this, true))

    }


    private fun set_icon_image() {
        logo_image.setOnClickListener {
            EasyPhotos.createAlbum(this@ClassItemEditActivity, true, GlideEngine.getInstance())
                    .setFileProviderAuthority("com.noplugins.keepfit.android.fileprovider")
                    .setPuzzleMenu(false)
                    .setCount(1)
                    .setOriginalMenu(false, true, null)
                    .start(102)
        }
        delete_icon_btn.setOnClickListener {
            icon_image_path = ""
            delete_icon_btn.visibility = View.INVISIBLE
            Glide.with(applicationContext).load<Any>(R.drawable.jia_image).into(logo_image)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        submit_tescher_list_edit.clear()
    }

    override fun onResume() {
        super.onResume()
        if (class_jianjie_tv.length == 0) {
            edit_class_jieshao.hint = resources.getText(R.string.edit_hint23)
        } else {
            edit_class_jieshao.text = class_jianjie_tv
        }

        if (shihe_renqun_tv.length == 0) {
            edit_shihe_renqun.hint = resources.getText(R.string.edit_hint24)
        } else {
            edit_shihe_renqun.text = shihe_renqun_tv
        }

        if (zhuyi_shixiang_tv.length == 0) {
            edit_zhuyi_shixiang.hint = resources.getText(R.string.edit_hint25)
        } else {
            edit_zhuyi_shixiang.text = zhuyi_shixiang_tv
        }
        //判断是否刷新教练邀请列表
        if (is_refresh_teacher_list_edit) {
            if (submit_tescher_list_edit.size > 0) {
                inviteTeacherAdapter!!.notifyDataSetChanged()
            }
            is_refresh_teacher_list_edit = false
        }


    }

    override fun doBusiness(mContext: Context) {
        back_btn.setOnClickListener {
            //弹出是否退出创建的提示
            pop(false)
        }
        add_class_teacher_btn.setOnClickListener(View.OnClickListener {
            if (check_value()) {//如果所有参数不为空，请求网络接口
                add_class()
            } else {
                return@OnClickListener
            }
        })
    }

    private fun get_class_room_type() {
        val params = HashMap<String, Any>()
        params["areaNum"] = SpUtils.getString(applicationContext, AppConstants.CHANGGUAN_NUM)//场馆编号
        subscription = Network.getInstance("获取房间类型", this)
                .get_class_type(params,
                        ProgressSubscriber("获取房间类型", object : SubscriberOnNextListener<Bean<List<ClassTypeEntity>>> {
                            override fun onNext(result: Bean<List<ClassTypeEntity>>) {
                                if (class_room_types.size > 0) {
                                    class_room_types.clear()
                                }
                                class_room_types.addAll(result.data)
                                //获取最大人数
                                room_type = class_room_types[0].key.toString() + ""


                            }

                            override fun onError(error: String) {

                            }
                        }, this, false))
    }


    private fun select_class_type_pop() {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_tuanke_type_btn.width,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(select_tuanke_type_btn)
        /**设置逻辑 */
        val view = popupWindow.contentView
        val strings = ArrayList<String>()
        for (i in tuanke_types.indices) {
            strings.add(tuanke_types[i].name)
        }
        val typeAdapter = TypeAdapter(strings, applicationContext)
        val listView = view.findViewById<ListView>(R.id.listview)
        listView.adapter = typeAdapter
        listView.setOnItemClickListener { adapterView, view1, i, l ->
            select_tuanke_type_tv.text = strings[i]
            select_class_type = tuanke_types[i].value
            popupWindow.dismiss()
        }
    }

    private fun select_class_difficulty_pop() {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_class_difficulty_btn.width,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(select_class_difficulty_btn)
        /**设置逻辑 */
        val view = popupWindow.contentView
        val strings = ArrayList<String>()
        for (i in class_difficultys.indices) {
            strings.add(class_difficultys[i].name)
        }
        val typeAdapter = TypeAdapter(strings, applicationContext)
        val listView = view.findViewById<ListView>(R.id.listview)
        listView.adapter = typeAdapter
        listView.setOnItemClickListener { adapterView, view1, i, l ->
            select_class_difficulty_tv.text = strings[i]
            select_nandu_type = class_difficultys[i].value
            popupWindow.dismiss()
        }
    }

    private fun select_room_type_pop() {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_room_type_btn.width,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(select_room_type_btn)
        /**设置逻辑 */
        val view = popupWindow.contentView
        val strings = ArrayList<String>()
        for (i in class_room_types.indices) {
            strings.add(class_room_types[i].value)
            Log.e("坚实的开发接口数量的", class_room_types[i].value)
        }
        val typeAdapter = TypeAdapter(strings, applicationContext)
        val listView = view.findViewById<ListView>(R.id.listview)
        listView.adapter = typeAdapter
        listView.setOnItemClickListener { adapterView, view1, i, l ->
            select_room_tv.text = class_room_types[i].value
            //查询每个房间最大能容纳的人数
            room_type = class_room_types[i].key.toString() + ""
            //获取房间列表
            get_room_list()

            popupWindow.dismiss()
        }
    }


    private fun select_room_list_pop() {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_room_name_btn.width,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(select_room_name_btn)
        /**设置逻辑 */
        val view = popupWindow.contentView
        val strings = ArrayList<String>()
        for (i in room_lists.indices) {
            strings.add(room_lists[i].placeName)
        }
        val typeAdapter = TypeAdapter(strings, applicationContext)
        val listView = view.findViewById<ListView>(R.id.listview)
        listView.adapter = typeAdapter
        listView.setOnItemClickListener { adapterView, view1, i, l ->
            select_room_name_tv.text = room_lists[i].placeName
            select_room_name = room_lists[i].placeName
            select_room_name_id = room_lists[i].placeNum
            edit_tuanke_renshu_number.text = room_lists[i].maxNum.toString() + "人"
            popupWindow.dismiss()
        }
    }


    private fun get_room_list() {
        val params = HashMap<String, Any>()
        params["areaNum"] = SpUtils.getString(applicationContext, AppConstants.CHANGGUAN_NUM)//场馆编号
        params["placeType"] = room_type
        subscription = Network.getInstance("获取房间列表", this)
                .get_class_type1(params,
                        ProgressSubscriber("获取房间列表", object : SubscriberOnNextListener<Bean<List<SelectRoomBean>>> {
                            override fun onNext(result: Bean<List<SelectRoomBean>>) {
                                if (room_lists.size > 0) {
                                    room_lists.clear()
                                }
                                room_lists.addAll(result.data)
                            }

                            override fun onError(error: String) {

                            }
                        }, this, false))
    }


    private fun select_class_target_pop() {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_class_target_btn.width,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(select_class_target_btn)
        /**设置逻辑 */
        val view = popupWindow.contentView
        val strings = ArrayList<String>()
        for (i in tatget_types.indices) {
            strings.add(tatget_types[i].name)
        }
        val typeAdapter = TypeAdapter(strings, applicationContext)
        val listView = view.findViewById<ListView>(R.id.listview)
        listView.adapter = typeAdapter
        listView.setOnItemClickListener { adapterView, view1, i, l ->
            select_class_target_tv.text = strings[i]
            select_target_type = tatget_types[i].value
            popupWindow.dismiss()
        }
    }

    private fun select_xunhuan_pop() {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_xunhuan_type_btn.width,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(select_xunhuan_type_btn)
        /**设置逻辑 */
        val view = popupWindow.contentView
        val strings = ArrayList<String>()
        strings.add("单次")
        val typeAdapter = TypeAdapter(strings, applicationContext)
        val listView = view.findViewById<ListView>(R.id.listview)
        listView.adapter = typeAdapter
        listView.setOnItemClickListener { adapterView, view1, i, l ->
            select_xunhuan_type_tv.text = strings[i]
            select_xunhuan_type = strings[i]
            popupWindow.dismiss()
        }
    }


    override fun onBackPressed() {
        pop(false)
    }

    private fun pop(is_no_invite_teacher: Boolean) {
        XPopup.Builder(this)
                .autoOpenSoftInput(true)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .asCustom(CenterPopupView(this, R.layout.back_invite_teacher_pop, ViewCallBack { view, popup ->
                    val pop_title = view.findViewById<TextView>(R.id.pop_title)
                    val pop_content = view.findViewById<TextView>(R.id.pop_content)
                    if (is_no_invite_teacher) {//弹出"选择教练"提示
                        pop_title.setText(R.string.tv178)
                        pop_content.setText(R.string.tv179)
                    }
                    view.findViewById<View>(R.id.cancel_btn).setOnClickListener { popup.dismiss() }
                    view.findViewById<View>(R.id.sure_btn).setOnClickListener {
                        if (is_no_invite_teacher) {
                            //这边调用新增团课的接口
                            add_class()

                        } else {
                            finish()
                        }
                        popup.dismiss()
                    }
                })).show()
    }

    private fun calculate_time(time1_edit: TextView, time2_edit: TextView): Boolean {
        val startHour = Integer.parseInt(time1_edit.text.toString().split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])
        val startMin = Integer.parseInt(time1_edit.text.toString().split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1])
        val endHour = Integer.parseInt(time2_edit.text.toString().split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])
        val endMin = Integer.parseInt(time2_edit.text.toString().split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1])
        if (startHour > endHour) {
            Toast.makeText(this@ClassItemEditActivity,
                    "开始时间不能大于结束时间", Toast.LENGTH_SHORT).show()
            return false
        }
        if (startHour == endHour && startMin > endMin) {
            Toast.makeText(this@ClassItemEditActivity,
                    "开始时间不能大于结束时间", Toast.LENGTH_SHORT).show()
            return false
        }
        val yinyeStartH = Integer.parseInt(start.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])
        val yinyeStartM = Integer.parseInt(start.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1])
        val yinyeEndH = Integer.parseInt(end.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])
        val yinyeEndM = Integer.parseInt(end.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1])
        if (startHour < yinyeStartH) {
            Toast.makeText(this@ClassItemEditActivity,
                    "该时间段场馆未营业", Toast.LENGTH_SHORT).show()
            return false
        }
        if (startHour == yinyeStartH && startMin < yinyeStartM) {
            Toast.makeText(this@ClassItemEditActivity,
                    "该时间段场馆未营业", Toast.LENGTH_SHORT).show()
            return false
        }
        if (endHour > yinyeEndH) {
            Toast.makeText(this@ClassItemEditActivity,
                    "该时间段场馆未营业", Toast.LENGTH_SHORT).show()
            return false
        }
        if (endHour == yinyeEndH && endMin > yinyeEndM) {
            Toast.makeText(this@ClassItemEditActivity,
                    "该时间段场馆未营业", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun add_class() {
        if (!calculate_time(time1_edit, time2_edit)) {//判断时间是否正确
            return
        }

        val params = HashMap<String, Any>()
        params["gym_area_num"] = SpUtils.getString(applicationContext, AppConstants.CHANGGUAN_NUM)//场馆编号
        params["course_name"] = edit_class_name.text.toString()//团课名称
        params["target"] = select_target_type//训练目标
        params["difficulty"] = select_nandu_type//训练难度
        params["type"] = room_type//房间类型
        params["gymPlaceNum"] = select_room_name_id//选择的房间名称编号
        params["placeName"] = select_room_name//选择的房间名称
        params["logo"] = icon_net_path
        val teacherBeanList = ArrayList<CgBindingBean.TeacherNumBean>()
        for (teacherBean in submit_tescher_list_edit) {
            val teacherNumBean = CgBindingBean.TeacherNumBean()
            teacherNumBean.num = teacherBean.teacherNum
            teacherBeanList.add(teacherNumBean)
        }
        params["teacherNum"] = teacherBeanList//选择的教练列表
        params["class_type"] = select_class_type//团课类型：1单车2瑜伽3普拉提4拳击5舞蹈6功能性7儿童
        params["max_num"] = edit_tuanke_renshu_number.text.toString().replace("人", "")//人数限制
        params["start_time"] = (year_tv.text.toString() + "-" + month_tv.text.toString() + "-" + date_tv.text.toString() + " "
                + time1_edit.text.toString())//开始时间
        params["end_time"] = (year_tv.text.toString() + "-" + month_tv.text.toString() + "-" + date_tv.text.toString() + " "
                + time2_edit.text.toString())//结束时间
        if (select_xunhuan_type == "单次") {
            params["loop_cycle"] = ""//循环周数
        }
        params["course_des"] = edit_class_jieshao.text.toString()//课程介绍
        params["tips"] = edit_zhuyi_shixiang.text.toString()//注意事项
        params["price"] = edit_price_number.text.toString()//注意事项
        params["suit_person"] = edit_shihe_renqun.text.toString()//适合人群

        subscription = Network.getInstance("添加课程", this)
                .add_class(params,
                        ProgressSubscriber("添加课程", object : SubscriberOnNextListener<Bean<AddClassEntity>> {
                            override fun onNext(result: Bean<AddClassEntity>) {
                                //                                Intent intent = new Intent(ClassItemEditActivity.this, TeamInfoActivity.class);
                                //                                Bundle bundle = new Bundle();
                                //                                bundle.putString("create_time", result.getData().getStartTime());
                                //                                bundle.putString("gym_course_num", result.getData().getGym_course_num());
                                //                                intent.putExtras(bundle);
                                //                                startActivity(intent);
                                finish()

                            }

                            override fun onError(error: String) {
                                Toast.makeText(this@ClassItemEditActivity, error, Toast.LENGTH_SHORT).show()
                            }
                        }, this, true))
    }

    private fun check_value(): Boolean {
        if (TextUtils.isEmpty(edit_class_name.text)) {
            Toast.makeText(this, R.string.alert_dialog_tishi16, Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(edit_tuanke_renshu_number.text)) {
            Toast.makeText(this, R.string.alert_dialog_tishi17, Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(edit_class_jieshao.text)) {
            Toast.makeText(this, R.string.alert_dialog_tishi18, Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(edit_shihe_renqun.text)) {
            Toast.makeText(this, R.string.alert_dialog_tishi19, Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(edit_zhuyi_shixiang.text)) {
            Toast.makeText(this, R.string.alert_dialog_tishi20, Toast.LENGTH_SHORT).show()
            return false
        } else if (icon_net_path.length == 0) {//logo
            Toast.makeText(this, R.string.alert_dialog_tishi36, Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(select_room_name_tv.text)) {
            Toast.makeText(this, R.string.alert_dialog_tishi37, Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(edit_price_number.text)) {
            Toast.makeText(this, R.string.alert_dialog_tishi38, Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(time1_edit.text)) {
            Toast.makeText(this, R.string.alert_dialog_tishi39, Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(time2_edit.text)) {
            Toast.makeText(this, R.string.alert_dialog_tishi40, Toast.LENGTH_SHORT).show()
            return false
        } else {
            if (submit_tescher_list_edit.size == 0) {//邀请老师列表
                pop(true)
                return false
            }
            return true
        }


    }

    private fun select_time() {
        year_tv.text = cDate!![0].toString()
        if (cDate!![1] <= 9) {
            month_tv.text = "0" + cDate!![1]
        } else {
            month_tv.text = cDate!![1].toString()
        }
        if (cDate!![2] <= 9) {
            date_tv.text = "0" + cDate!![2]
        } else {
            date_tv.text = cDate!![2].toString()
        }

        select_date_layout.setOnClickListener(View.OnClickListener { select_date() })
        time1_edit.setOnClickListener { time_check(time1_edit) }

        time2_edit.setOnClickListener { time_check(time2_edit) }
    }

    private fun select_date() {
        val today = DateEntity.to3day()
        datePicker = DatePicker(this, DateMode.YEAR_MONTH_DAY)
        datePicker!!.setRange(today, DateEntity.to30day())
        datePicker!!.setDefaultValue(today)
        datePicker!!.showAtBottom()
        datePicker!!.setOnDateSelectedListener { year, month, day ->
            year_tv.text = year.toString()
            if (month <= 9) {
                month_tv.text = "0$month"
            } else {
                month_tv.text = month.toString()
            }
            if (day <= 9) {
                date_tv.text = "0$day"
            } else {
                date_tv.text = day.toString()

            }
        }
    }

    private fun time_check(textView: TextView) {
        picker = TimePicker(this, TimeMode.HOUR_24)
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        picker!!.setDefaultValue(TimeEntity(hour, minute))
        picker!!.showAtBottom()
        picker!!.setOnTimeSelectedListener(OnTimeSelectedListener { hour, minute, second ->
            if (minute <= 9) {
                textView.text = "$hour:0$minute"
            } else {
                textView.text = "$hour:$minute"
            }

            if (!TextUtils.isEmpty(time1_edit.text) && !TextUtils.isEmpty(time2_edit.text)) {
                //判断结束时候是否大于当前时间
                if (!calculate_time(time1_edit, time2_edit)) {//判断时间是否正确
                    return@OnTimeSelectedListener
                } else {
                    val start_time = "2019/01/01 " + time1_edit.text.toString()
                    val end_time = "2019/01/01 " + time2_edit.text.toString()
                    //Log.e("接口少房间数量", start_time + "\n" + end_time);
                    //Log.e("计算出来的时间", getTimeExpend(start_time, end_time));
                    jisuan_time_tv.text = getTimeExpend(start_time, end_time)
                }
            }
        })
    }

    private fun getTimeExpend(startTime: String, endTime: String): String {
        //传入字串类型 2016/06/28 08:30
        val longStart = getTimeMillis(startTime) //获取开始时间毫秒数
        val longEnd = getTimeMillis(endTime)  //获取结束时间毫秒数
        val longExpend = longEnd - longStart  //获取时间差

        val longHours = longExpend / (60 * 60 * 1000) //根据时间差来计算小时数
        val longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000)   //根据时间差来计算分钟数

        return "$longHours:$longMinutes"
    }

    private fun getTimeMillis(strTime: String): Long {
        var returnMillis: Long = 0
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
        var d: Date? = null
        try {
            d = sdf.parse(strTime)
            returnMillis = d!!.time
        } catch (e: ParseException) {
            Toast.makeText(this@ClassItemEditActivity, e.toString(), Toast.LENGTH_SHORT).show()
        }

        return returnMillis
    }


    private fun get_class_leavel() {
        val params = HashMap<String, Any>()
        params["object"] = "difficulty"
        subscription = Network.getInstance("获取课程难度", this)
                .get_types(params,
                        ProgressSubscriber("获取课程难度", object : SubscriberOnNextListener<Bean<List<DictionaryeBean>>> {
                            override fun onNext(addClassEntityBean: Bean<List<DictionaryeBean>>) {
                                if (class_difficultys.size > 0) {
                                    class_difficultys.clear()
                                }
                                class_difficultys.addAll(addClassEntityBean.data)
                            }

                            override fun onError(error: String) {

                            }
                        }, this, false))
    }

    private fun get_tuanke_type() {
        val params = HashMap<String, Any>()
        params["object"] = 6
        subscription = Network.getInstance("获取团课类型", this)
                .get_types(params,
                        ProgressSubscriber("获取团课类型", object : SubscriberOnNextListener<Bean<List<DictionaryeBean>>> {
                            override fun onNext(addClassEntityBean: Bean<List<DictionaryeBean>>) {
                                if (tuanke_types.size > 0) {
                                    tuanke_types.clear()
                                }
                                tuanke_types.addAll(addClassEntityBean.data)
                            }

                            override fun onError(error: String) {

                            }
                        }, this, false))


    }

    private fun get_class_target() {
        val params = HashMap<String, Any>()
        params["object"] = "target"
        subscription = Network.getInstance("获取训练目标", this)
                .get_types(params,
                        ProgressSubscriber("获取训练目标", object : SubscriberOnNextListener<Bean<List<DictionaryeBean>>> {
                            override fun onNext(addClassEntityBean: Bean<List<DictionaryeBean>>) {
                                if (tatget_types.size > 0) {
                                    tatget_types.clear()
                                }
                                tatget_types.addAll(addClassEntityBean.data)
                            }

                            override fun onError(error: String) {

                            }
                        }, this, false))


    }


    private fun getYinyeTime() {
        val params = HashMap<String, Any>()
        params["areaNum"] = SpUtils.getString(applicationContext, AppConstants.CHANGGUAN_NUM)
        subscription = Network.getInstance("我的", this)
                .myArea(params,
                        ProgressSubscriber("我的", object : SubscriberOnNextListener<Bean<ChangguanBean>> {
                            override fun onNext(addClassEntityBean: Bean<ChangguanBean>) {
                                start = TimeCheckUtil.removeSecond(addClassEntityBean.data
                                        .area.businessStart)
                                end = TimeCheckUtil.removeSecond(addClassEntityBean.data
                                        .area.businessEnd)
                            }

                            override fun onError(error: String) {

                            }
                        }, this, false))


    }

    override fun onClickAddNinePhotoItem(sortableNinePhotoLayout: CCRSortableNinePhotoLayout, view: View, position: Int, models: ArrayList<String>) {
        //设置最多只能上传9张图片
        if (AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE >= 9) {
            Toast.makeText(applicationContext, "只能上传9张图片哦～", Toast.LENGTH_SHORT).show()
        } else {
            max_num = 9 - AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE
            EasyPhotos.createAlbum(this, true, GlideEngine.getInstance())
                    .setFileProviderAuthority("com.noplugins.keepfit.android.fileprovider")
                    .setPuzzleMenu(false)
                    .setCount(max_num)
                    .setOriginalMenu(false, true, null)
                    .start(101)
        }
    }

    override fun onClickDeleteNinePhotoItem(sortableNinePhotoLayout: CCRSortableNinePhotoLayout, view: View, position: Int, model: String, models: ArrayList<String>) {
        snpl_moment_add_photos.removeItem(position)
        AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE = AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE - 1
        select_numbers_tv.text = AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE.toString() + "/9"
    }

    override fun onClickNinePhotoItem(sortableNinePhotoLayout: CCRSortableNinePhotoLayout, view: View, position: Int, model: String, models: ArrayList<String>) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode) {
            //相机或相册回调
            if (requestCode == 101) {
                //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
                val resultPhotos = data!!.getParcelableArrayListExtra<Photo>(EasyPhotos.RESULT_PHOTOS)
                //                for (int i = 0; i < resultPhotos.size(); i++) {
                //                    Log.e("图片地址", resultPhotos.get(i).path);
                //                }
                //返回图片地址集合：如果你只需要获取图片的地址，可以用这个
                val resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS)
                //返回图片地址集合时如果你需要知道用户选择图片时是否选择了原图选项，用如下方法获取
                val selectedOriginal = data.getBooleanExtra(EasyPhotos.RESULT_SELECTED_ORIGINAL, false)
                strings.addAll(resultPaths)
                snpl_moment_add_photos.setData(strings)//设置九宫格
                AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE = strings.size
                select_numbers_tv.text = AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE.toString() + "/9"
                return
            } else if (requestCode == 102) {//添加icon,上传icon
                val resultPaths = data!!.getStringArrayListExtra(EasyPhotos.RESULT_PATHS)
                if (resultPaths.size > 0) {
                    icon_image_path = resultPaths[0]
                    gotoClipActivity(Uri.fromFile(File(icon_image_path)))
                }
            } else if (requestCode == 103) {
                val uri = data!!.data ?: return
                val cropImagePath = FileUtil.getRealFilePathFromUri(applicationContext, uri)
                //Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                val icon_iamge_file = File(cropImagePath)
                Glide.with(applicationContext).load(icon_iamge_file).into(logo_image)
                delete_icon_btn.visibility = View.VISIBLE
                //上传七牛云
                progress_upload = ProgressUtil()
                progress_upload!!.showProgressDialog(this, "上传中...")
                //上传icon
                upload_icon_image(cropImagePath, false)

            }
        } else if (Activity.RESULT_CANCELED == resultCode) {
            //Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
    }


    private fun upload_icon_image(image_path: String, is_jiugognge: Boolean) {
        Luban.with(this)
                .load(image_path)
                .ignoreBy(100)
                .setTargetDir(getCompressJpgFileAbsolutePath())
                .filter { path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")) }.setCompressListener(object : OnCompressListener {
                    override fun onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    override fun onSuccess(file: File) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        if (is_jiugognge) {//九宫格上传
                            //jiugonggeCallBack.onSucceed(file.getAbsolutePath());
                        } else {
                            compressCallBack.onSucceed(file.absolutePath)
                        }
                    }

                    override fun onError(e: Throwable) {
                        compressCallBack.onFailure(e.message)
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch()
    }

    internal var compressCallBack: ImageCompressCallBack = object : ImageCompressCallBack {
        override fun onSucceed(data: String) {
            //            Log.e("压缩过的",data);
            //            File file = new File(data);
            //            Log.e("压缩后的大小", FileSizeUtil.getFileOrFilesSize(file.getAbsolutePath(), 2) + "");
            upload_icon_work(data)
        }

        override fun onFailure(msg: String) {
            Log.e("压缩失败的", msg)
        }
    }

    private fun upload_icon_work(img_path: String) {
        Log.e("上传icon", img_path)
        uploadManager!!.put(img_path, qiniu_key, uptoken,
                { key, info, response ->
                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                    if (info.isOK) {
                        icon_net_path = key
                        //测试资料上传的
                        //getUrlTest(icon_net_path);
                        //Log.e("qiniu", "Upload Success");
                        Log.e("上传icon成功：", icon_net_path)
                        progress_upload!!.dismissProgressDialog()
                    } else {
                        Log.e("qiniu", "Upload Fail")
                        progress_upload!!.dismissProgressDialog()
                        progress_upload = null
                        Toast.makeText(applicationContext, R.string.tv185, Toast.LENGTH_SHORT).show()
                        //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                    }
                    //Log.e("qiniu", key + ",\r\n " + info.path + ",\r\n " + response);
                }, UploadOptions(null, "test-type", true, null, null))
    }


    /**
     * 打开截图界面
     */
    fun gotoClipActivity(uri: Uri?) {
        if (uri == null) {
            return
        }
        val intent = Intent()
        intent.setClass(applicationContext, ClipImageActivity::class.java)
        intent.putExtra("type", 2)//1:圆形 2:方形
        intent.data = uri
        startActivityForResult(intent, 103)
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
    private fun setting(code: ClassDetailEntity) {
        select_tuanke_type_tv.text = classType(code.course.classType)
        select_class_target_tv.text = goalType(code.course.target)
        select_class_difficulty_tv.text = difficultyType(code.course.difficulty)

        edit_class_name.setText(code.course.courseName)
        if (code.course.price != null){
            edit_price_number.setText(""+code.course.price)
        }

        year_tv.text = DateHelper.getDateDayByLong(code.course.startTime).split("-")[0]
        month_tv.text = DateHelper.getDateDayByLong(code.course.startTime).split("-")[1]
        date_tv.text = DateHelper.getDateDayByLong(code.course.startTime).split("-")[2]

        time1_edit.text = DateHelper.getDateByLong(code.course.startTime)
        time2_edit.text = DateHelper.getDateByLong(code.course.endTime)

        select_room_tv.text = roomType(code.course.type.toInt())
        select_room_name_tv.text = ""
        edit_tuanke_renshu_number.text ="${code.course.maxNum}人"
        select_xunhuan_type_tv.text = "单次"

        edit_class_jieshao.text = code.course.courseDes
        edit_shihe_renqun.text = code.course.suitPerson
        edit_zhuyi_shixiang.text = code.course.tips


        Glide.with(this).load(code.course.imgUrl)
                .transform( CenterCrop(this), GlideRoundTransform(this,10))
                .placeholder(R.drawable.logo_gray)
                .into(logo_image)

        delete_icon_btn.visibility = View.VISIBLE
    }
    private fun requestData(){
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
