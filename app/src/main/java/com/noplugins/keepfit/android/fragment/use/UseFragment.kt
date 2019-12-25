package com.noplugins.keepfit.android.fragment.use

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.SelectChangGuanActivity
import com.noplugins.keepfit.android.activity.mine.TeacherManagerActivity
import com.noplugins.keepfit.android.activity.mine.WalletActivity
import com.noplugins.keepfit.android.activity.use.RoomManagerActivity
import com.noplugins.keepfit.android.activity.use.StatisticsActivity
import com.noplugins.keepfit.android.activity.use.TeamClassManagerActivity
import com.noplugins.keepfit.android.base.BaseFragment
import com.noplugins.keepfit.android.bean.use.UseBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment
import kotlinx.android.synthetic.main.fragment_use.*
import java.util.HashMap

class UseFragment: ViewPagerFragment() {

    override fun fetchData() {
        if (tvCGName!=null){
            tvCGName!!.text = SpUtils.getString(activity!!, AppConstants.CG_NAME)
        }

        if (newView != null){
            colors.add(Color.parseColor("#707BCC"))
            colors.add(Color.parseColor("#5CCEFF"))
            colors.add(Color.parseColor("#828AD3"))
            requestUse(1)
        }
    }

    companion object {
        fun newInstance(title: String): UseFragment {
            val fragment = UseFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }

    var newView: View? = null
    var type  = 1

    private var haveMoreArea: Boolean = false
    private var tvCGName: TextView?= null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_use, container, false)
            tvCGName = newView!!.findViewById(R.id.store_type_tv)
        }
        return newView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        setting()
        onClick()
        initAllPieChart()

    }

    private fun initView(){

    }


    private fun onClick(){
        haveMoreArea = SpUtils.getBoolean(activity,AppConstants.HAVE_MORE_AREA)
        if (SpUtils.getInt(activity!!, AppConstants.USER_TYPE) == 2 || SpUtils.getInt(activity!!, AppConstants.USER_TYPE) == 3) {
            select_store_type.visibility = View.INVISIBLE
        } else {
            select_store_type.visibility = View.VISIBLE
        }
        select_store_type.setOnClickListener {
            if (haveMoreArea) {//如果有更多场馆
                val intent = Intent(activity, SelectChangGuanActivity::class.java)
                startActivity(intent)
            }
        }

        ll_team_manager.setOnClickListener {
            if (BaseUtils.isFastClick()){
                val intent = Intent(activity, TeamClassManagerActivity::class.java)
                startActivityForResult(intent, 2)
            }
        }
        ll_teacher_manager.setOnClickListener {
            if (BaseUtils.isFastClick()){
                val intent = Intent(activity,TeacherManagerActivity::class.java)
                startActivityForResult(intent, 2)
            }
        }
        ll_room_manager.setOnClickListener {
            if (BaseUtils.isFastClick()){
                val intent = Intent(activity, RoomManagerActivity::class.java)
                startActivityForResult(intent, 2)
            }
        }
        ll_wallet_manager.setOnClickListener {
            if (BaseUtils.isFastClick()){
                val intent = Intent(activity,WalletActivity::class.java)
                startActivityForResult(intent, 2)
            }
        }

        tv_statistics.setOnClickListener {
            if (BaseUtils.isFastClick()){

                if (SpUtils.getString(activity, AppConstants.USER_DENGJI) == "2999"){
                    Toast.makeText(activity,"会员等级不够～",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val intent = Intent(activity, StatisticsActivity::class.java)
                startActivityForResult(intent, 2)
            }
        }

        //****************
        rb_today.setOnClickListener {
            if (type == 1){
                return@setOnClickListener
            }
            type = 1
            requestUse(type)
        }
        rb_yesterday.setOnClickListener {
            if (type == 2){
                return@setOnClickListener
            }
            type = 2
            requestUse(type)
        }
        rb_week.setOnClickListener {
            if (type == 3){
                return@setOnClickListener
            }
            type = 3
            requestUse(type)
        }
        rb_1month.setOnClickListener {
            if (type == 4){
                return@setOnClickListener
            }
            type = 4
            requestUse(type)
        }
        //**************


    }

    private fun setting(bean: UseBean){
        tv_team_price.text = "¥${bean.roll.price}"
        tv_team_order.text = "${bean.roll.count}"
        tv_teacher_price.text = "¥${bean.privateX.price}"
        tv_teacher_order.text = "${bean.privateX.count}"
        tv_venue_price.text = "¥${bean.area.price}"
        tv_venue_order.text = "${bean.area.count}"

        tv_sum_income.text = "¥${bean.total.price}"
        tv_sum_order.text = "${bean.total.count}"

        xiaoshouStrings.clear()

        if (bean.product!=null){
            bean.product.forEach {
                xiaoshouStrings.add(PieEntry(it.percent.toFloat(), it.type))
            }

            val dataSet = PieDataSet(xiaoshouStrings, "")
            dataSet.colors = colors
            val pieData = PieData(dataSet)
            pieData.setDrawValues(true)
            pieData.setValueFormatter(com.noplugins.keepfit.android.chart.PercentFormatter(picChart))
            pieData.setValueTextSize(9f)
            picChart.data = pieData
            picChart.invalidate()
        } else{
            picChart.visibility = View.INVISIBLE
        }

    }



    private val xiaoshouStrings = ArrayList<PieEntry>()
    private val colors = ArrayList<Int>()
    private fun initAllPieChart() {
        val dataSet = PieDataSet(xiaoshouStrings, "")
        dataSet.colors = colors
        val pieData = PieData(dataSet)
//        pieData.setDrawValues(true)
//        pieData.setDrawValues(true)
        picChart.setUsePercentValues(true)
        pieData.setValueFormatter(com.noplugins.keepfit.android.chart.PercentFormatter(picChart))
        pieData.setValueTextSize(12f)
        picChart.data = pieData
        picChart.setEntryLabelColor(Color.BLACK)
        picChart.invalidate()

        picChart.isRotationEnabled = false
        picChart.setDrawEntryLabels(false)//显示lable
        // 不显示图例
        val legend = picChart.legend
        legend.yOffset = 5f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.form = Legend.LegendForm.CIRCLE

//        legend.setEnabled(false)
//        picChart.isRotationEnabled = false
        picChart.setEntryLabelColor(Color.BLACK)
//        picChart.setDrawEntryLabels(isLabel)
        val description = Description()
        description.text = ""
        picChart.description = description
        picChart.holeRadius = 0f
        picChart.x = -120f
        picChart.transparentCircleRadius = 0f
        picChart.extraTopOffset = 20f
        picChart.extraBottomOffset = 20f


    }


    private fun requestUse(type:Int) {

        val params = HashMap<String, Any>()

        params["searchType"] = type//1当天 2昨天 3七天 4一个月
        params["areaNum"] = SpUtils.getString(activity, AppConstants.CHANGGUAN_NUM)
        val subscription = Network.getInstance("运营首页", activity)
                .operationData(
                        params,
                        ProgressSubscriber("运营首页", object : SubscriberOnNextListener<Bean<UseBean>> {
                            override fun onNext(result: Bean<UseBean>) {
                                setting(result.data)
                            }

                            override fun onError(error: String) {
                                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                            }
                        }, activity, false)
                )
    }

}