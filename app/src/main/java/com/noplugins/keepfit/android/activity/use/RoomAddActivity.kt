package com.noplugins.keepfit.android.activity.use

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.DictionaryeBean
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
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
        setContentView(R.layout.activity_room_add)
        title_tv.text = getString(R.string.room_add)
        requestRoomType()
    }

    override fun doBusiness(mContext: Context?) {

        back_btn.setOnClickListener {
            finish()
        }
        tv_complete.setOnClickListener {
            if (BaseUtils.isFastClick()){
                addRoom()
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }


    private fun initMs() {
        ms_room_type.setItems(data)
//        ms_room_type.selectedIndex = 0
        ms_room_type.setOnItemSelectedListener { _, position, _, _ ->
            ms_room_type.text = list[position].name
            selectType = list[position].value.toInt()
        }
        ms_room_type.setOnNothingSelectedListener { spinner -> spinner.selectedIndex }
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
                                    bean.data.forEach {
                                        data.add(it.name)
                                    }
                                    initMs()
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
            return
        }
        if (et_people_number.text.toString().isEmpty()){
            return
        }
        val params = HashMap<String, Any>()
        params["place_name"] = et_room_name.text.toString()
        params["place_type"] = selectType
        params["max_num"] = et_people_number.text.toString().toInt()

        subscription = Network.getInstance("新增房间", applicationContext)
                .addAreaPlace(params, ProgressSubscriber("新增房间",
                        object : SubscriberOnNextListener<Bean<String>> {
                            override fun onNext(bean: Bean<String>) {
                                finish()
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
                            }
                        }, this, true))
    }

}
