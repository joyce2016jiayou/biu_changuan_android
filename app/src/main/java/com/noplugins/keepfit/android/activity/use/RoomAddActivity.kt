package com.noplugins.keepfit.android.activity.use

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.adapter.TypeAdapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.DictionaryeBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.FlowLayout
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow
import kotlinx.android.synthetic.main.activity_room_add.*
import kotlinx.android.synthetic.main.title_activity.*
import java.util.HashMap

class RoomAddActivity : BaseActivity() {

    var data: ArrayList<String> = ArrayList()
    var list: List<DictionaryeBean> = ArrayList()
    var selectType = 1
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentLayout(R.layout.activity_room_add)
        isShowTitle(true)
        setTitleView(R.string.room_add)
        requestRoomType()
    }

    override fun doBusiness(mContext: Context?) {

        title_left_button_onclick_listen {
            finish()
        }
        tv_complete.setOnClickListener {
            if (BaseUtils.isFastClick()){
                addRoom()
            }
        }

        ms_room_type.setOnClickListener {
            initMs()
        }
    }

    override fun onBackPressed() {
        finish()
    }


    private fun initMs() {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(ms_room_type.width,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(ms_room_type)
        /**设置逻辑 */
        val view = popupWindow.contentView
        val strings = java.util.ArrayList<String>()
        for (i in list.indices) {
            strings.add(list[i].name)
        }
        val typeAdapter = TypeAdapter(strings, applicationContext)
        val listView = view.findViewById<ListView>(R.id.listview)
        listView.adapter = typeAdapter
        listView.setOnItemClickListener { adapterView, view1, i, l ->
            ms_room_type.text = strings[i]
            selectType = list[i].value.toInt()
            popupWindow.dismiss()
        }
    }

    /**
     * 获取所有的房间类型
     */
    private fun requestRoomType() {
        val params = HashMap<String, Any>()
        params["object"] = "room_type"

        subscription = Network.getInstance("获取所有的房间类型", applicationContext)
                .get_types(params, ProgressSubscriber("获取所有的房间类型",
                        object : SubscriberOnNextListener<Bean<List<DictionaryeBean>>> {
                            override fun onNext(bean: Bean<List<DictionaryeBean>>) {

                                if (bean.data != null) {
                                    list = bean.data
                                }
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
                            }
                        }, this, true))

    }

    /**
     * 添加房间
     */

    private fun addRoom(){
        if (et_room_name.text.toString().isEmpty()){
            Toast.makeText(applicationContext, "房间名称不可为空", Toast.LENGTH_SHORT).show()
            return
        }
        if (et_people_number.text.toString().isEmpty()||et_people_number.text.toString().toInt() == 0){
            Toast.makeText(applicationContext, "可容纳人数不可为空", Toast.LENGTH_SHORT).show()
            return
        }
        val params = HashMap<String, Any>()
        params["place_name"] = et_room_name.text.toString()
        params["gym_area_num"] = SpUtils.getString(applicationContext,AppConstants.CHANGGUAN_NUM)
        params["place_type"] = selectType
        params["max_num"] = et_people_number.text.toString().toInt()

        subscription = Network.getInstance("新增房间", applicationContext)
                .addAreaPlace(params, ProgressSubscriber("新增房间",
                        object : SubscriberOnNextListener<Bean<Any>> {
                            override fun onNext(bean: Bean<Any>) {
                                Toast.makeText(applicationContext, "新增房间成功", Toast.LENGTH_SHORT).show()
                                finish()
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
                            }
                        }, this, true))
    }

}
