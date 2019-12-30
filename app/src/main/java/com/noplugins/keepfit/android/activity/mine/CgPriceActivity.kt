package com.noplugins.keepfit.android.activity.mine

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.noplugins.keepfit.android.KeepFitActivity
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.adapter.DatePriceTestAdapter
import com.noplugins.keepfit.android.adapter.HighAdapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.HighBean
import com.noplugins.keepfit.android.bean.HightList11Bean
import com.noplugins.keepfit.android.bean.HightListBean
import com.noplugins.keepfit.android.bean.PriceBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.ActivityCollectorUtil
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.TimeCheckUtil
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow
import com.noplugins.keepfit.android.util.ui.pop.base.CenterPopupView
import com.noplugins.keepfit.android.util.ui.pop.inteface.ViewCallBack
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
        setContentLayout(R.layout.activity_cg_price)
        isShowTitle(true)
        setTitleView(R.string.tv188)
        val data: MutableList<PriceBean> = ArrayList()
        val price1 = PriceBean("6:00-9:00", "¥19.9")
        val price2 = PriceBean("9:00-12:00", "¥39.9")
        val price3 = PriceBean("12:00-14:00", "¥69.9")
        val price4 = PriceBean("14:00-17:00", "¥39.9")
        val price5 = PriceBean("17:00-22:00", "¥99.9")
        val price6 = PriceBean("22:00-6:00", "¥29.9")
        data.add(price1)
        data.add(price2)
        data.add(price3)
        data.add(price4)
        data.add(price5)
        data.add(price6)
        val adapter = DatePriceTestAdapter(this, data)

        grid_view.adapter = adapter

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

        title_left_button_onclick_listen {
            back()
        }
        iv_tips.setOnClickListener {
            viewPop()
        }

        ll_tips.setOnClickListener {
            viewMenshiPop()
        }
        et_price.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (et_price.text.toString() != "") {
                    tv_price.text = "场馆门市价格：¥${et_price.text.toString()}元/时"
                    list.forEach {
                        it.normal_price = et_price.text.toString()
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        tv_add_price.setOnClickListener {
            if (BaseUtils.isFastClick()){
                addPricePop()
            }
        }


        fl_hesuan.setOnClickListener {
            val intent = Intent(this, CostAccountingActivity::class.java)
            val bundle = Bundle()
            bundle.putString("form", "pay")
            intent.putExtras(bundle)
            startActivityForResult(intent, 1)
        }

        tv_shangjia.setOnClickListener {
            if (et_price.text.toString().isEmpty()){
                Toast.makeText(applicationContext, "价格不能为空", Toast.LENGTH_SHORT).show()
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

    private fun viewMenshiPop() {
        val popupWindow = CommonPopupWindow.Builder(this)
                .setView(R.layout.pop_menshi_tips)
                .setBackGroundLevel(1f)//0.5f
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(ll_tips, 0, -15)
    }

    private fun addPricePop(){
        XPopup.Builder(this)
                .autoOpenSoftInput(false)
                .autoFocusEditText(false)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .asCustom(CenterPopupView(this,R.layout.dialog_to_prices,
                        ViewCallBack { view, popup ->

                            val time = view.findViewById<TextView>(R.id.tv_select_time)
                            val price = view.findViewById<EditText>(R.id.et_high_price)
                            selectOnClick(time)

                            view.findViewById<TextView>(R.id.tv_cancel)
                                    .setOnClickListener {
                                        popup.dismiss()
                                    }

                            view.findViewById<TextView>(R.id.tv_add)
                                    .setOnClickListener {
                                        if (time.text == "选择时间"){
                                            Toast.makeText(applicationContext, "请选择时间段", Toast.LENGTH_SHORT).show()
                                            return@setOnClickListener
                                        }

                                        if (price.text.isEmpty()){
                                            Toast.makeText(applicationContext, "请输入价格", Toast.LENGTH_SHORT).show()
                                            return@setOnClickListener
                                        }
                                        val highBean = HighBean()
                                        highBean.high_time_start = startTime
                                        highBean.high_time_end = endTime
                                        highBean.high_time_price = price.text.toString()
                                        highBean.normal_price = et_price.text.toString()
                                        highBean.gym_area_num = SpUtils.getString(applicationContext, AppConstants.CHANGGUAN_NUM)
                                        list.add(highBean)
                                        adapter!!.notifyDataSetChanged()
                                        popup.dismiss()
                                    }

                        })).show()
    }

    private fun selectOnClick(view:TextView){
        view.setOnClickListener {
            val hour = HourAndMinDialogFragment()
            hour.setOnDateChooseListener { startHour, startMinute, endHour, endMinute ->
                //
                if (endHour < startHour) {
                    Toast.makeText(applicationContext,"开始时间不能大于结束时间",
                            Toast.LENGTH_SHORT).show()
                    return@setOnDateChooseListener
                }
                if (endHour == startHour && endMinute <= startMinute) {
                    Toast.makeText(applicationContext,"开始时间不能大于结束时间",
                            Toast.LENGTH_SHORT).show()
                    return@setOnDateChooseListener
                }

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
                view.text = "$startH:$startM - $endH:$endM"
                startTime = "$startH:$startM"
                endTime = "$endH:$endM"
            }

            hour.show(supportFragmentManager, "HourAndMinDialogFragment")
        }
    }

    private fun request() {
        val params = HashMap<String, Any>()
        if (list.size > 0){
            params["time"] = list
        }
        params["normalPrice"] = et_price.text.toString()
        params["areaNum"] = SpUtils.getString(applicationContext, AppConstants.CHANGGUAN_NUM)
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
                        ProgressSubscriber("获取精准化时间", object : SubscriberOnNextListener<Bean<HightList11Bean>> {
                            override fun onNext(result: Bean<HightList11Bean>) {
                                //
                                //HighBean
                                if (result.data.gymTimes.isNotEmpty()) {
                                    for (i in 0 until result.data.gymTimes.size) {
                                        val highBean = HighBean()
                                        highBean.gym_area_num = result.data.gymTimes[i].gymAreaNum
                                        highBean.high_time_start = TimeCheckUtil.removeSecond(result.data.gymTimes[i].highTimeStart)
                                        highBean.high_time_end = TimeCheckUtil.removeSecond(result.data.gymTimes[i].highTimeEnd)
                                        highBean.high_time_price = result.data.gymTimes[i].finalHighPrice.toString()
                                        highBean.normal_price = result.data.gymTimes[i].finalNormalPrice.toString()
                                        list.add(highBean)
                                    }
                                    adapter!!.notifyDataSetChanged()
                                }
                                if (result.data.cost!=null){
                                    tv_hesuan.text = "${result.data.cost}/人/次"
                                }
                                tv_hesuan.text = "${result.data.cost}/人/次"
                                et_price.setText("${result.data.normalPrice}")
                                tv_price.text = "场馆全天时段价格：¥${result.data.normalPrice}元/时"
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
        back()
    }

    private fun back(){
        Log.d("form", form)
        if (form == "pay") {
            finish()
            ActivityCollectorUtil.finishAllActivity()
            return
        }
//        val intent = Intent(this, KeepFitActivity::class.java)
//        startActivity(intent,3)

        setResult(SpUtils.getInt(applicationContext,AppConstants.FRAGMENT_SIZE)-1)
//        startActivity(intent)
        finish()
    }
}
