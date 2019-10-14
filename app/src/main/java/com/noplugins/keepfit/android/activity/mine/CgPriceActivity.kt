package com.noplugins.keepfit.android.activity.mine

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.adapter.DatePriceTestAdapter
import com.noplugins.keepfit.android.adapter.HighAdapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.HighBean
import com.noplugins.keepfit.android.bean.PriceBean
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow
import kotlinx.android.synthetic.main.activity_cg_price.*

class CgPriceActivity :BaseActivity() {

    val list:MutableList<HighBean> = ArrayList()
    var adapter: HighAdapter ?= null
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_cg_price)
        val data:MutableList<PriceBean> = ArrayList()
        val price1 = PriceBean("6:00-9:00","¥15")
        val price2 = PriceBean("6:00-9:00","¥15")
        val price3 = PriceBean("6:00-9:00","¥15")
        val price4 = PriceBean("6:00-9:00","¥15")
        val price5 = PriceBean("6:00-9:00","¥15")
        val price6 = PriceBean("6:00-9:00","¥15")
        data.add(price1)
        data.add(price2)
        data.add(price3)
        data.add(price4)
        data.add(price5)
        data.add(price6)
        val adapter = DatePriceTestAdapter(this,data)

        grid_view.adapter = adapter

        initAdapter()
    }

    private fun initAdapter(){
        rv_high.layoutManager  = LinearLayoutManager(this)
        adapter = HighAdapter(list)
        rv_high.adapter = adapter
        adapter!!.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.iv_delete){
                list.removeAt(position)//删除数据源,移除集合中当前下标的数据
                adapter.notifyItemRemoved(position)//刷新被删除的地方
                adapter.notifyItemRangeChanged(position, adapter.itemCount) //刷新被删除数据，以及其后面的数据
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun doBusiness(mContext: Context?) {
        iv_tips.setOnClickListener {
            viewPop()
        }
        tv_add_price.setOnClickListener {
            if (et_price.text.toString()!=""){
                tv_price.text = "场馆全天时段价格：¥${et_price.text.toString()}"
            }
        }
        tv_select_time.setOnClickListener {

        }
        fl_hesuan.setOnClickListener {
            val intent = Intent(this,CostAccountingActivity::class.java)
            startActivity(intent)
        }

        tv_high_add.setOnClickListener {
            //add high data
            val highBean = HighBean()
            highBean.startDate = "09:00"
            highBean.startDate = "13:00"
            highBean.price = et_high_price.text.toString()
            list.add(highBean)
            adapter!!.notifyDataSetChanged()
        }
    }

    private fun viewPop(){
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.pop_price_tips)
                .setBackGroundLevel(1f)//0.5f
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(iv_tips,0,-15)
    }


}
