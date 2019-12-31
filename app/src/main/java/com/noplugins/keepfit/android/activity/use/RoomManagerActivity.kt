package com.noplugins.keepfit.android.activity.use

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.adapter.RoomManagerAdapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.DictionaryeBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import kotlinx.android.synthetic.main.activity_room_manager.*
import kotlinx.android.synthetic.main.title_activity.*
import java.util.HashMap

class RoomManagerActivity : BaseActivity() {

    var adapter: RoomManagerAdapter ?= null
    var data:MutableList<DictionaryeBean> = ArrayList()
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentLayout(R.layout.activity_room_manager)
        isShowTitle(true)
        setTitleView(R.string.room_manager,R.drawable.icon_back,R.drawable.icon_add,false,0)
        //add_btn.visibility = View.VISIBLE

        adapter = RoomManagerAdapter(data)
        rv_room_manager.layoutManager = LinearLayoutManager(this)
        rv_room_manager.adapter = adapter
        adapter!!.setOnItemChildClickListener { _, _, position ->
            val intent = Intent(this@RoomManagerActivity,RoomInfoActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("type",data[position].value.toInt())
            bundle.putString("name",data[position].name)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        requestRoomType()
    }

    override fun doBusiness(mContext: Context?) {

        title_left_button_onclick_listen {
            setResult(SpUtils.getInt(applicationContext, AppConstants.FRAGMENT_SIZE)-1)
            finish()
        }

        title_right_button_onclick_listen {
            if (BaseUtils.isFastClick()){
                val intent = Intent(this,RoomAddActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        setResult(SpUtils.getInt(applicationContext, AppConstants.FRAGMENT_SIZE)-1)
        finish()
    }

    //查询所有类型
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
                                    data.addAll(bean.data)
                                    adapter!!.notifyDataSetChanged()
                                }
                            }
                            override fun onError(error: String) {
                                Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
                            }
                        }, this, true))

    }


}
