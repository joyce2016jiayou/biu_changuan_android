package com.noplugins.keepfit.android.activity.mine

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.noplugins.keepfit.android.KeepFitActivity
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.adapter.DatePriceTestAdapter
import com.noplugins.keepfit.android.adapter.HighAdapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.HighBean
import com.noplugins.keepfit.android.bean.HightListBean
import com.noplugins.keepfit.android.bean.PriceBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.TimeCheckUtil
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow
import com.ycuwq.datepicker.time.HourAndMinDialogFragment
import kotlinx.android.synthetic.main.activity_cg_price.*
import java.util.*
import kotlin.collections.ArrayList

class CgPriceActivity : BaseActivity() {

    val list: MutableList<HighBean> = ArrayList()
    var adapter: HighAdapter? = null
    var form = "main"
    var startTime = ""
    var endTime = ""
    override fun initBundle(parms: Bundle?) {
        if (parms != null) {
            form = parms.getString("form", "main")
        }
    }

    override fun initView() {
        setContentView(R.layout.activity_cg_price)
        val data: MutableList<PriceBean> = ArrayList()
        val price1 = PriceBean("6:00-9:00", "¥15")
        val price2 = PriceBean("6:00-9:00", "¥15")
        val price3 = PriceBean("6:00-9:00", "¥15")
        val price4 = PriceBean("6:00-9:00", "¥15")
        val price5 = PriceBean("6:00-9:00", "¥15")
        val price6 = PriceBean("6:00-9:00", "¥15")
        data.add(price1)
        data.add(price2)
        data.add(price3)
        data.add(price4)
        data.add(price5)
        data.add(price6)
        val adapter = DatePriceTestAdapter(this, data)

        grid_view.adapter = adapter

        tv_hesuan.text = SpUtils.getString(applicationContext, AppConstants.COST)
        initAdapter()
        if (form == "main") {
            requestHightTime()
        }


    }

    private fun initAdapter() {
        rv_high.layoutManager = LinearLayoutManager(this)
        adapter = HighAdapter(list)
        rv_high.adapter = adapter
        adapter!!.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.iv_delete) {
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
            if (et_price.text.toString() != "") {
                tv_price.text = "场馆全天时段价格：¥${et_price.text.toString()}"
                list.forEach {
                    it.normal_price = et_price.text.toString()
                }
            }
        }
        tv_select_time.setOnClickListener {

            val hour = HourAndMinDialogFragment()

//            if (startH != "") {
//                hour.setSelectedDate(startH.toInt(), startM.toInt(), endH.toInt(), endM.toInt())
//            } else {
//
//            }

            hour.setOnDateChooseListener { startHour, startMinute, endHour, endMinute ->
                //
//                if (endHour < startHour) {
//                    return@setOnDateChooseListener
//                }
//                if (endHour == startHour && endMinute <= startMinute) {
//                    return@setOnDateChooseListener
//                }


                val startH = if (startHour > 9) {
                    "$startHour"
                } else {
                    "0$startHour"
                }
                val startM = if (startMinute > 9) {
                    "$startMinute"
                } else {
                    "0$startMinute"
                }
                val endH = if (endHour > 9) {
                    "$endHour"
                } else {
                    "0$endHour"
                }
                val endM = if (endMinute > 9) {
                    "$endMinute"
                } else {
                    "0$endMinute"
                }
                tv_select_time.text = "$startH:$startM - $endH:$endM"
                startTime = "$startH:$startM"
                endTime = "$endH:$endM"
            }

            hour.show(supportFragmentManager, "HourAndMinDialogFragment")
        }

        fl_hesuan.setOnClickListener {
            val intent = Intent(this, CostAccountingActivity::class.java)
            val bundle = Bundle()
            bundle.putString("form", "pay")
            intent.putExtras(bundle)
            startActivityForResult(intent, 1)
        }

        tv_high_add.setOnClickListener {
            //add high data
            val highBean = HighBean()
            highBean.high_time_start = startTime
            highBean.high_time_end = endTime
            highBean.high_time_price = et_high_price.text.toString()
            highBean.normal_price = et_price.text.toString()
            highBean.gym_area_num = SpUtils.getString(applicationContext, AppConstants.CHANGGUAN_NUM)
            list.add(highBean)
            adapter!!.notifyDataSetChanged()
            tv_select_time.text = "请选择"
            et_high_price.setText("")
        }

        tv_shangjia.setOnClickListener {
            if (list.size < 1) {
                Toast.makeText(applicationContext, "提交数据不可为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            request()
        }

    }

    private fun viewPop() {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.pop_price_tips)
                .setBackGroundLevel(1f)//0.5f
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(iv_tips, 0, -15)
    }

    private fun request() {
        val params = HashMap<String, Any>()
        params["time"] = list
        subscription = Network.getInstance("精准化时间", this)
                .setHighTime(
                        params,
                        ProgressSubscriber("精准化时间", object : SubscriberOnNextListener<Bean<Any>> {
                            override fun onNext(result: Bean<Any>) {
                                Toast.makeText(applicationContext, "设置成功", Toast.LENGTH_SHORT).show()
                                if (form == "pay") {
                                    val intent = Intent(this@CgPriceActivity, KeepFitActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
                            }
                        }, this, false)
                )
    }


    private fun requestHightTime() {
        val params = HashMap<String, Any>()
        params["areaNum"] = SpUtils.getString(applicationContext, AppConstants.CHANGGUAN_NUM)
        subscription = Network.getInstance("获取精准化时间", this)
                .findAreaPrice(
                        params,
                        ProgressSubscriber("获取精准化时间", object : SubscriberOnNextListener<Bean<List<HightListBean>>> {
                            override fun onNext(result: Bean<List<HightListBean>>) {
                                //
                                //HighBean
                                if (result.data.isNotEmpty()) {
                                    for (i in 0 until result.data.size) {
                                        val highBean = HighBean()
                                        highBean.gym_area_num = result.data[i].gymAreaNum
                                        highBean.high_time_start = TimeCheckUtil.removeSecond(result.data[i].highTimeStart)
                                        highBean.high_time_end = TimeCheckUtil.removeSecond(result.data[i].highTimeEnd)
                                        highBean.high_time_price = result.data[i].finalHighPrice.toString()
                                        highBean.normal_price = result.data[i].finalNormalPrice.toString()
                                        list.add(highBean)
                                    }

                                    et_price.setText("${result.data[0].finalNormalPrice}")
                                    tv_price.text = "场馆全天时段价格：¥${result.data[0].finalNormalPrice}"

                                    adapter!!.notifyDataSetChanged()
                                }
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
                            }
                        }, this, false)
                )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data1: Intent?) {
        super.onActivityResult(requestCode, resultCode, data1)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val item = data1!!.getStringExtra("cost")
                tv_hesuan.text = "￥$item/人/次"
            }
        }
    }

    override fun onBackPressed() {
        Log.d("form", form)
        if (form == "pay") {
            val intent = Intent(this, KeepFitActivity::class.java)
            startActivity(intent)
            finish()
            return
        }
//        val intent = Intent(this, KeepFitActivity::class.java)
//        startActivity(intent,3)
        setResult(3)
//        startActivity(intent)
        finish()
    }
}
