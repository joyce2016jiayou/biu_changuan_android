package com.noplugins.keepfit.android.activity.use

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.adapter.RoomInfoAdapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.DictionaryeBean
import com.noplugins.keepfit.android.bean.use.RoomBean
import com.noplugins.keepfit.android.bean.use.RoomDelBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.pop.base.CenterPopupView
import com.noplugins.keepfit.android.util.ui.pop.inteface.ViewCallBack
import kotlinx.android.synthetic.main.activity_room_info.*
import kotlinx.android.synthetic.main.title_activity.*
import java.util.HashMap

class RoomInfoActivity : BaseActivity() {
    var type = -1
    var typeName = ""
    var adapter: RoomInfoAdapter?= null
    var data:MutableList<RoomBean> = ArrayList()
    override fun initBundle(parms: Bundle?) {
        if (parms!=null){
            type = parms.getInt("type")
            typeName = parms.getString("name","")
        }
    }

    override fun initView() {
        setContentView(R.layout.activity_room_info)
        title_tv.text = typeName

        adapter = RoomInfoAdapter(data)
        rv_room_info.layoutManager = GridLayoutManager(this,2)
        rv_room_info.adapter = adapter
        adapter!!.setOnItemChildClickListener { _, _, position ->
            //删除
            if (BaseUtils.isFastClick()){
                deletePop(position)
            }
        }

        requestRoom()
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.setOnClickListener {
            finish()
        }
    }

    /**
     * 获取房间
     */
    private fun requestRoom() {
        val params = HashMap<String, Any>()
        params["areaNum"] = SpUtils.getString(applicationContext,AppConstants.CHANGGUAN_NUM)
        params["placeType"] = type

        subscription = Network.getInstance("获取房间", applicationContext)
                .getAreaPlace(params, ProgressSubscriber("获取房间",
                        object : SubscriberOnNextListener<Bean<List<RoomBean>>> {
                            override fun onNext(bean: Bean<List<RoomBean>>) {
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


    private fun deletePop(position:Int) {
<<<<<<< HEAD
        XPopup.Builder(this)
                .autoOpenSoftInput(false)
                .autoFocusEditText(false)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .asCustom(CenterPopupView(this,R.layout.dialog_to_room_delete,
                        ViewCallBack { view, popup ->

                            view.findViewById<TextView>(R.id.tv_cancel)
                                    .setOnClickListener {
                                        popup.dismiss()
                                    }

                            view.findViewById<TextView>(R.id.tv_add)
                                    .setOnClickListener {deleteRoom(position)}

                        })).show()
=======
        PublicPopControl.alert_dialog_center(this) { view, popup ->
            val content = view.findViewById<TextView>(R.id.pop_content)
            val title = view.findViewById<TextView>(R.id.pop_title)
            content.setText(R.string.tv_delete_info)
            title.setText(R.string.tv_delete_room)
            view.findViewById<LinearLayout>(R.id.cancel_btn)
                    .setOnClickListener {
                        popup.dismiss()
                    }
            view.findViewById<LinearLayout>(R.id.sure_btn)
                    .setOnClickListener {
                        popup.dismiss()
                        deleteRoom(position)}
        }
>>>>>>> origin/develop
    }

    /**
     * 删除房间
     */
    private fun deleteRoom(position: Int){
        val params = HashMap<String, Any>()
        params["place_num"] = data[position].placeNum

        subscription = Network.getInstance("删除房间", applicationContext)
                .deleteAreaPlace(params, ProgressSubscriber("删除房间",
                        object : SubscriberOnNextListener<Bean<RoomDelBean>> {
                            override fun onNext(bean: Bean<RoomDelBean>) {
                                if (bean.data.delete == 0){
                                    knowPop()
                                }
                                if (bean.data.delete == 1){
                                    data.removeAt(position)
                                    adapter!!.notifyItemRemoved(position)//刷新被删除的地方
                                    adapter!!.notifyItemRangeChanged(position, adapter!!.itemCount)
                                }
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
                            }
                        }, this, true))
    }

    private fun knowPop() {
        XPopup.Builder(this)
                .autoOpenSoftInput(false)
                .autoFocusEditText(false)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .asCustom(CenterPopupView(this,R.layout.dialog_to_room_tips,
                        ViewCallBack { view, popup ->

                            view.findViewById<TextView>(R.id.tv_complete)
                                    .setOnClickListener {
                                        popup.dismiss()
                                    }

                        })).show()
    }

}
