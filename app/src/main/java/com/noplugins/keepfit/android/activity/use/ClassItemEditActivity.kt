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
import com.noplugins.keepfit.android.bean.ChangguanBean
import com.noplugins.keepfit.android.bean.DictionaryeBean
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
import java.util.ArrayList
import java.util.Calendar
import java.util.HashMap

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
import com.noplugins.keepfit.android.entity.*
import com.noplugins.keepfit.android.util.GlideRoundTransform
import com.noplugins.keepfit.android.util.data.DateHelper
import com.noplugins.keepfit.android.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.activity_class_item_edit.*
import kotlinx.android.synthetic.main.add_class_item_baseinformation_layout.*
import kotlinx.android.synthetic.main.add_class_item_baseinformation_layout2.*
import kotlinx.android.synthetic.main.add_class_item_baseinformation_layout3.*
import kotlinx.android.synthetic.main.title_activity.*
import okhttp3.RequestBody

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
    private var enable_max_people: Int = 0
    private val class_room_types = ArrayList<ClassTypeEntity>()
    internal var tuanke_types: MutableList<DictionaryeBean> = ArrayList()
    internal var class_difficultys: MutableList<DictionaryeBean> = ArrayList()
    internal var tatget_types: MutableList<DictionaryeBean> = ArrayList()
    internal var start = ""
    internal var end = ""
    private var type = ""
    private var icon_image_path = ""
    private var max_num = 0
    private val strings = ArrayList<String>()


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

        //获取历史数据
        requestData()
        //设置营业时间
//        getYinyeTime()

//        select_time()

        //选择团课类型
//        get_tuanke_type()
        select_tuanke_type_btn.setOnClickListener(View.OnClickListener { select_class_type_pop() })

        //获取房间类型
//        get_class_room_type()
        select_room_type_btn.setOnClickListener(View.OnClickListener { select_room_type_pop() })
        //获取课程难度
//        get_class_leavel()
        select_class_difficulty_btn.setOnClickListener(View.OnClickListener { select_class_difficulty_pop() })
        //获取训练目标
//        get_class_target()
        select_class_target_btn.setOnClickListener(View.OnClickListener { select_class_target_pop() })

        //设置循环
        select_xunhuan_type_btn.setOnClickListener(View.OnClickListener { select_xunhuan_pop() })

        //设置课程详情
        input_class_detail_btn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ClassItemEditActivity, EditClassDetaiActivity::class.java)
            intent.putExtra("type", "class_content")
            startActivity(intent)
        })

        //适合人群
        input_shihe_renqun_btn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ClassItemEditActivity, EditClassDetaiActivity::class.java)
            intent.putExtra("type", "shihe_renqun")
            startActivity(intent)
        })
        //注意事项
        input_zhuyishixiang_btn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ClassItemEditActivity, EditClassDetaiActivity::class.java)
            intent.putExtra("type", "zhuyi_shixiang")
            startActivity(intent)
        })

        //添加封面图
        set_icon_image()
        //设置九宫格
        //设置九宫格控件
//        mPhotosSnpl.setDelegate(this)


    }

    private fun set_icon_image() {
        logo_image.setOnClickListener(View.OnClickListener //添加图片
        {
            EasyPhotos.createAlbum(this@ClassItemEditActivity, true, GlideEngine.getInstance())
                    .setFileProviderAuthority("com.noplugins.keepfit.android.fileprovider")
                    .setPuzzleMenu(false)
                    .setCount(1)
                    .setOriginalMenu(false, true, null)
                    .start(102)
        })
        delete_icon_btn.setOnClickListener(View.OnClickListener //删除图片
        {
            icon_image_path = ""
            delete_icon_btn.setVisibility(View.INVISIBLE)
            Glide.with(applicationContext).load(R.drawable.jia_image).into(logo_image)
        })
    }


    override fun onResume() {
        super.onResume()
        if (class_jianjie_tv.length == 0) {
            edit_class_jieshao.setHint(resources.getText(R.string.edit_hint23))
        } else {
            edit_class_jieshao.setText(class_jianjie_tv)
        }

        if (shihe_renqun_tv.length == 0) {
            edit_shihe_renqun.setHint(resources.getText(R.string.edit_hint24))
        } else {
            edit_shihe_renqun.setText(shihe_renqun_tv)
        }

        if (zhuyi_shixiang_tv.length == 0) {
            edit_zhuyi_shixiang.setHint(resources.getText(R.string.edit_hint25))
        } else {
            edit_zhuyi_shixiang.setText(zhuyi_shixiang_tv)
        }

    }

    override fun doBusiness(mContext: Context) {
        back_btn.setOnClickListener(View.OnClickListener {
            //弹出是否退出创建的提示
            back_pop()
        })
        add_class_teacher_btn.setOnClickListener(View.OnClickListener {
            val startHour = Integer.parseInt(time1_edit.getText().toString().split(":")[0])
            val startMin = Integer.parseInt(time1_edit.getText().toString().split(":")[1])

            val endHour = Integer.parseInt(time2_edit.getText().toString().split(":")[0])
            val endMin = Integer.parseInt(time2_edit.getText().toString().split(":")[1])
            if (startHour > endHour) {
                Toast.makeText(this@ClassItemEditActivity,
                        "开始时间不能大于结束时间", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (startHour == endHour && startMin > endMin) {
                Toast.makeText(this@ClassItemEditActivity,
                        "开始时间不能大于结束时间", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            val yinyeStartH = Integer.parseInt(start.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])
            val yinyeStartM = Integer.parseInt(start.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1])

            val yinyeEndH = Integer.parseInt(end.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])
            val yinyeEndM = Integer.parseInt(end.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1])

            if (startHour < yinyeStartH) {
                Toast.makeText(this@ClassItemEditActivity,
                        "该时间段场馆未营业", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (startHour == yinyeStartH && startMin < yinyeStartM) {
                Toast.makeText(this@ClassItemEditActivity,
                        "该时间段场馆未营业", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if (endHour > yinyeEndH) {
                Toast.makeText(this@ClassItemEditActivity,
                        "该时间段场馆未营业", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (endHour == yinyeEndH && endMin > yinyeEndM) {
                Toast.makeText(this@ClassItemEditActivity,
                        "该时间段场馆未营业", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if (edit_price_number.getText().toString().equals("")) {
                Toast.makeText(this@ClassItemEditActivity,
                        "价格不能为空", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if (check_value()) {//如果所有参数不为空，请求网络接口
                add_class()
            } else {
                return@OnClickListener
            }
        })

        //        center.setOnTouchListener(new View.OnTouchListener() {
        //            @Override
        //            public boolean onTouch(View v, MotionEvent event) {
        //                InputMethodManager inputMethodManager =
        //                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //                inputMethodManager.hideSoftInputFromWindow(edit_class_jieshao.getWindowToken(), 0);
        //                inputMethodManager.hideSoftInputFromWindow(edit_zhuyi_shixiang.getWindowToken(), 0);
        //                inputMethodManager.hideSoftInputFromWindow(edit_shihe_renqun.getWindowToken(), 0);
        //                edit_class_jieshao.clearFocus();
        //                edit_class_name.clearFocus();
        //                edit_price_number.clearFocus();
        //                edit_shihe_renqun.clearFocus();
        //                edit_tuanke_renshu_number.clearFocus();
        //                edit_zhuyi_shixiang.clearFocus();
        //                return false;
        //            }
        //        });
        //        select_date.setOnTouchListener(new View.OnTouchListener() {
        //            @Override
        //            public boolean onTouch(View v, MotionEvent event) {
        //
        //                InputMethodManager inputMethodManager =
        //                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //                inputMethodManager.hideSoftInputFromWindow(edit_class_jieshao.getWindowToken(), 0);
        //                inputMethodManager.hideSoftInputFromWindow(edit_zhuyi_shixiang.getWindowToken(), 0);
        //                inputMethodManager.hideSoftInputFromWindow(edit_shihe_renqun.getWindowToken(), 0);
        //                edit_class_jieshao.clearFocus();
        //                edit_class_name.clearFocus();
        //                edit_price_number.clearFocus();
        //                edit_shihe_renqun.clearFocus();
        //                edit_tuanke_renshu_number.clearFocus();
        //                edit_zhuyi_shixiang.clearFocus();
        //
        //                return false;
        //            }
        //        });
        //        time1_edit.setOnTouchListener(new View.OnTouchListener() {
        //            @Override
        //            public boolean onTouch(View v, MotionEvent event) {
        //                InputMethodManager inputMethodManager =
        //                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //                inputMethodManager.hideSoftInputFromWindow(edit_class_jieshao.getWindowToken(), 0);
        //                inputMethodManager.hideSoftInputFromWindow(edit_zhuyi_shixiang.getWindowToken(), 0);
        //                inputMethodManager.hideSoftInputFromWindow(edit_shihe_renqun.getWindowToken(), 0);
        //                edit_class_jieshao.clearFocus();
        //                edit_class_name.clearFocus();
        //                edit_price_number.clearFocus();
        //                edit_shihe_renqun.clearFocus();
        //                edit_tuanke_renshu_number.clearFocus();
        //                edit_zhuyi_shixiang.clearFocus();
        //                return false;
        //            }
        //        });
        //        time2_edit.setOnTouchListener(new View.OnTouchListener() {
        //            @Override
        //            public boolean onTouch(View v, MotionEvent event) {
        //                InputMethodManager inputMethodManager =
        //                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //                inputMethodManager.hideSoftInputFromWindow(edit_class_jieshao.getWindowToken(), 0);
        //                inputMethodManager.hideSoftInputFromWindow(edit_zhuyi_shixiang.getWindowToken(), 0);
        //                inputMethodManager.hideSoftInputFromWindow(edit_shihe_renqun.getWindowToken(), 0);
        //                edit_class_jieshao.clearFocus();
        //                edit_class_name.clearFocus();
        //                edit_price_number.clearFocus();
        //                edit_shihe_renqun.clearFocus();
        //                edit_tuanke_renshu_number.clearFocus();
        //                edit_zhuyi_shixiang.clearFocus();
        //                return false;
        //            }
        //        });
    }


    private fun setting(code: ClassDetailEntity) {
//        select_tuanke_type_tv.text = code.course.courseStatus
//        select_class_target_tv.text = code.course.target
//        select_class_difficulty_tv.text = code.course.difficulty

        edit_class_name.setText(code.course.courseName)
        edit_price_number.setText(code.course.price)
        year_tv.text = ""
        month_tv.text = ""
        date_tv.text = ""
        time1_edit.text = DateHelper.getDateByLong(code.course.startTime)
        date_tv.text = DateHelper.getDateByLong(code.course.endTime)

        select_room_tv.text = ""
        select_room_name_tv.text = ""
        edit_tuanke_renshu_number.text ="${ code.course.maxNum}人"
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
                                search_room_people(0)
                                type = class_room_types[0].key.toString() + ""


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
                .setWidthAndHeight(select_tuanke_type_btn.getWidth(),
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
            select_tuanke_type_tv.setText(strings[i])
            select_class_type = tuanke_types[i].value
            popupWindow.dismiss()
        }
    }

    private fun select_class_difficulty_pop() {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_class_difficulty_btn.getWidth(),
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
            select_class_difficulty_tv.setText(strings[i])
            select_nandu_type = class_difficultys[i].value
            popupWindow.dismiss()
        }
    }

    private fun select_room_type_pop() {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_room_type_btn.getWidth(),
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(select_room_type_btn)
        /**设置逻辑 */
        val view = popupWindow.contentView
        val strings = ArrayList<String>()
        for (i in class_room_types.indices) {
            strings.add(class_room_types[i].value)
        }
        val typeAdapter = TypeAdapter(strings, applicationContext)
        val listView = view.findViewById<ListView>(R.id.listview)
        listView.adapter = typeAdapter
        listView.setOnItemClickListener { adapterView, view1, i, l ->
            select_room_tv.setText(class_room_types[i].value)
            //查询每个房间最大能容纳的人数
            type = class_room_types[i].key.toString() + ""
            search_room_people(i)

            popupWindow.dismiss()
        }
    }


    private fun select_class_target_pop() {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_class_target_btn.getWidth(),
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
            select_class_target_tv.setText(strings[i])
            select_target_type = tatget_types[i].value
            popupWindow.dismiss()
        }
    }

    private fun select_xunhuan_pop() {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_xunhuan_type_btn.getWidth(),
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
            select_xunhuan_type_tv.setText(strings[i])
            select_xunhuan_type = strings[i]
            popupWindow.dismiss()
        }
    }


    override fun onBackPressed() {
        back_pop()
    }

    private fun back_pop() {
        PopWindowHelper.public_tishi_pop(this@ClassItemEditActivity, "提示", "是否退出团课创建？", "取消", "确定", object : DialogCallBack {
            override fun save() {
                finish()
            }

            override fun cancel() {

            }
        })
    }

    private fun add_class() {
        val params = HashMap<String, Any>()
        params["gym_area_num"] = SpUtils.getString(applicationContext, AppConstants.CHANGGUAN_NUM)//场馆编号
        params["course_name"] = edit_class_name.getText().toString()//团课名称
        params["target"] = select_target_type
        params["difficulty"] = select_nandu_type
        params["type"] = type
        params["class_type"] = select_class_type//团课类型：1单车2瑜伽3普拉提4拳击5舞蹈6功能性7儿童
        params["course_type"] = "1"//1团课，2私教，3健身
        params["max_num"] = edit_tuanke_renshu_number.getText().toString()//人数限制
        params["start_time"] = (year_tv.getText().toString() + "-" + month_tv.getText().toString() + "-" + date_tv.getText().toString() + " "
                + time1_edit.getText().toString())//开始时间
        params["end_time"] = (year_tv.getText().toString() + "-" + month_tv.getText().toString() + "-" + date_tv.getText().toString() + " "
                + time2_edit.getText().toString())//结束时间
        if (select_xunhuan_type == "单次") {
            params["loop_cycle"] = ""//循环周数
        }
        params["course_des"] = edit_class_jieshao.getText().toString()//课程介绍
        params["tips"] = edit_zhuyi_shixiang.getText().toString()//注意事项
        params["price"] = edit_price_number.getText().toString()//注意事项
        params["suit_person"] = edit_shihe_renqun.getText().toString()//适合人群
        subscription = Network.getInstance("添加课程", this)
                .add_class(params,
                        ProgressSubscriber("添加课程", object : SubscriberOnNextListener<Bean<AddClassEntity>> {
                            override fun onNext(result: Bean<AddClassEntity>) {
                                val intent = Intent(this@ClassItemEditActivity, YaoQingTeacherActivity::class.java)
                                val bundle = Bundle()
                                bundle.putString("create_time", result.data.startTime)
                                bundle.putString("gym_course_num", result.data.gym_course_num)
                                intent.putExtras(bundle)
                                startActivity(intent)
                                finish()
                            }

                            override fun onError(error: String) {
                                Toast.makeText(this@ClassItemEditActivity, error, Toast.LENGTH_SHORT).show()
                            }
                        }, this, true))
    }

    private fun check_value(): Boolean {
        if (TextUtils.isEmpty(edit_class_name.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi16, Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(edit_tuanke_renshu_number.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi17, Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(edit_class_jieshao.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi18, Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(edit_shihe_renqun.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi19, Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(edit_zhuyi_shixiang.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi20, Toast.LENGTH_SHORT).show()
            return false
        } else if (Integer.valueOf(edit_tuanke_renshu_number.getText().toString()) > enable_max_people) {
            Log.e("最大人数", enable_max_people.toString() + "")
            Toast.makeText(this, R.string.alert_dialog_tishi21, Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }

    }

    private fun select_time() {
        year_tv.setText(cDate!![0].toString())
        if (cDate!![1] <= 9) {
            month_tv.setText("0" + cDate!![1])
        } else {
            month_tv.setText(cDate!![1].toString())
        }
        if (cDate!![2] <= 9) {
            date_tv.setText("0" + cDate!![2])
        } else {
            date_tv.setText(cDate!![2].toString())
        }

        select_date_layout.setOnClickListener(View.OnClickListener { selectDate() })
        time1_edit.setOnClickListener(View.OnClickListener { time_check(time1_edit) })

        time2_edit.setOnClickListener(View.OnClickListener { time_check(time2_edit) })
    }

    private fun selectDate() {
        val today = DateEntity.to3day()
        datePicker = DatePicker(this, DateMode.YEAR_MONTH_DAY)
        datePicker!!.setRange(today, DateEntity.to30day())
        datePicker!!.setDefaultValue(today)
        datePicker!!.showAtBottom()
        datePicker!!.setOnDateSelectedListener { year, month, day ->
            year_tv.setText(year.toString())
            if (month <= 9) {
                month_tv.setText("0$month")
            } else {
                month_tv.setText(month.toString())
            }
            if (day <= 9) {
                date_tv.setText("0$day")
            } else {
                date_tv.setText(day.toString())

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
        picker!!.setOnTimeSelectedListener { hour, minute, second ->
            if (minute <= 9) {
                textView.text = "$hour:0$minute"
            } else {
                textView.text = "$hour:$minute"
            }
        }
    }


    private fun search_room_people(position: Int) {
        val params = HashMap<String, Any>()
        params["gymAreaNum"] = SpUtils.getString(applicationContext, AppConstants.CHANGGUAN_NUM)//场馆编号
        if (class_room_types.size > 0) {
            params["PlaceType"] = class_room_types[position].key
        }
        val gson = Gson()
        val json_params = gson.toJson(params)
        Log.e(TAG, "获取最大人数参数：$json_params")
        val json = Gson().toJson(params)//要传递的json
        val requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json)
        subscription = Network.getInstance("获取最大人数", this)
                .get_max_num(requestBody, ProgressSubscriberNew(MaxPeopleEntity::class.java, GsonSubscriberOnNextListener { entity, s ->
                    Log.e("获取最大人数成功", entity.data.toString() + "获取最大人数成功" + s)
                    enable_max_people = entity.data
                    edit_tuanke_renshu_number.setText("" + enable_max_people)
                }, object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {}

                    override fun onError(error: String) {
                        Log.e("获取最大人数失败", "获取最大人数失败:$error")
                    }
                }, this, true))
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
//        mPhotosSnpl.removeItem(position)
//        AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE = AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE - 1
//        select_numbers_tv.setText(AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE.toString() + "/9")
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
                strings.addAll(resultPaths!!)
//                mPhotosSnpl.setData(strings)//设置九宫格
//                AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE = strings.size
//                select_numbers_tv.setTextext(AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE.toString() + "/9")
                return
            } else if (requestCode == 102) {//添加icon,上传icon
                val resultPaths = data!!.getStringArrayListExtra(EasyPhotos.RESULT_PATHS)
                if (resultPaths!!.size > 0) {
                    icon_image_path = resultPaths[0]
                    gotoClipActivity(Uri.fromFile(File(icon_image_path)))
                }
            } else if (requestCode == 103) {
                val uri = data!!.data ?: return
                val cropImagePath = FileUtil.getRealFilePathFromUri(applicationContext, uri)
                //Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                val icon_iamge_file = File(cropImagePath)
                Glide.with(applicationContext).load(icon_iamge_file).into(logo_image)
                delete_icon_btn.setVisibility(View.VISIBLE)
            }
        } else if (Activity.RESULT_CANCELED == resultCode) {
            //Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
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

    companion object {
        var class_jianjie_tv = ""
        var shihe_renqun_tv = ""
        var zhuyi_shixiang_tv = ""
    }
}
