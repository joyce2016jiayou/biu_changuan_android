package com.noplugins.keepfit.android.activity.mine.cg

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.adapter.mine.cg.VenueLayout2Adapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.DictionaryeBean
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import kotlinx.android.synthetic.main.activity_venue_detail.*
import kotlinx.android.synthetic.main.venue_item_1.*
import kotlinx.android.synthetic.main.venue_item_2.*
import kotlinx.android.synthetic.main.venue_item_6.*
import java.util.HashMap

class VenueDetailActivity : BaseActivity() {

    private var nowSelect = 1
    private lateinit var docList:MutableList<DictionaryeBean>
    //2设备
    private var layout2Adapter:VenueLayout2Adapter?=null
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_venue_detail)
        docList = ArrayList()
        layout2Adapter = VenueLayout2Adapter(docList)
        layout2Adapter!!.setOnItemChildClickListener { adapter, view, position ->
            //checkbox 逻辑处理
        }
        requestDoc()
        //todo 请求到数据之后再进行 addView的操作，否则页面无数据
        changeLayout1(0)

        //获取场馆设施 doc

    }

    private fun requestDoc() {
        val params = HashMap<String, Any>()
        params["object"] = 1
        subscription = Network.getInstance("获取设施字典", this)
                .get_types(params,
                        ProgressSubscriber("获取设施字典",
                                object : SubscriberOnNextListener<Bean<List<DictionaryeBean>>> {
                                    override fun onNext(result: Bean<List<DictionaryeBean>>) {
                                        docList.addAll(result.data)
                                    }

                                    override fun onError(error: String) {

                                    }
                                }, this, false))
    }

    override fun doBusiness(mContext: Context?) {
//        val layoutInflater = LayoutInflater.from(this)
        rb_base_info.setOnClickListener {
            if (nowSelect != 1) {
                nowSelect = 1
                changeLayout1(1)
            }

        }
        rb_venue_facilities.setOnClickListener {
            if (nowSelect != 2) {
                changeLayout2()
            }
        }
        rb_business_info.setOnClickListener {
            if (nowSelect != 3) {
                changeLayout3()
            }
        }
        rb_account_info.setOnClickListener {
            if (nowSelect != 4) {
                changeLayout4()
            }
        }
        rb_album_manager.setOnClickListener {
            nowSelect = 5
            rec_right.removeViewAt(0)
            val view = layoutInflater.inflate(R.layout.venue_item_6, null, false)
            rec_right.addView(view, 0)
        }
        rb_coach_notes.setOnClickListener {
            if (nowSelect != 6) {
                changeLayout6()
            }
        }

    }

    //layout_1
    private fun changeLayout1(type: Int) {
        if (type == 1) {
            rec_right.removeViewAt(0)
        }
        val view = layoutInflater.inflate(R.layout.venue_item_1, null, false)
        rec_right.addView(view, 0)
        val save1 = view.findViewById<TextView>(R.id.tv_save_1)
        save1.setOnClickListener {

        }

    }

    //layout_2 场馆设施
    @SuppressLint("WrongConstant")
    private fun changeLayout2() {
        nowSelect = 2
        rec_right.removeViewAt(0)
        val view = layoutInflater.inflate(R.layout.venue_item_2, null, false)
        rec_right.addView(view, 0)
        val rvFacilities = view.findViewById<RecyclerView>(R.id.rv_venue_facilities)
        val save2 = view.findViewById<TextView>(R.id.tv_save_2)
        rvFacilities.layoutManager = GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false)
        rvFacilities.adapter = layout2Adapter



        save2.setOnClickListener {
            Toast.makeText(applicationContext,"sava 2 info",Toast.LENGTH_SHORT).show()
        }

    }

    //layout_3
    private fun changeLayout3() {
        nowSelect = 3
        rec_right.removeViewAt(0)
        val view = layoutInflater.inflate(R.layout.venue_item_3, null, false)
        rec_right.addView(view, 0)

    }

    //layout_3
    private fun changeLayout4() {
        nowSelect = 4
        rec_right.removeViewAt(0)
        val view = layoutInflater.inflate(R.layout.venue_item_4, null, false)
        rec_right.addView(view, 0)

    }

    //layout_6 教练须知
    private fun changeLayout6() {
        nowSelect = 6
        rec_right.removeViewAt(0)
        val view = layoutInflater.inflate(R.layout.venue_item_6, null, false)
        rec_right.addView(view, 0)
        val save6 = view.findViewById<TextView>(R.id.tv_save_6)
        val etCoachNotes = view.findViewById<TextInputEditText>(R.id.et_coach_notes)
        save6.setOnClickListener {
            //保存教练须知
            if (etCoachNotes.text.toString().isEmpty()) {
                return@setOnClickListener
            }
            Toast.makeText(applicationContext,"sava 6 info",Toast.LENGTH_SHORT).show()
            //todo 调用保存接口
        }


    }
}
