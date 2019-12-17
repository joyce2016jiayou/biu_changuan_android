package com.noplugins.keepfit.android.fragment.use

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.mine.TeacherManagerActivity
import com.noplugins.keepfit.android.activity.mine.WalletActivity
import com.noplugins.keepfit.android.activity.use.StatisticsActivity
import com.noplugins.keepfit.android.base.BaseFragment
import com.noplugins.keepfit.android.util.BaseUtils
import kotlinx.android.synthetic.main.fragment_use.*

class UseFragment: BaseFragment() {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_use, container, false)
        }
        return newView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setting()
        onClick()
    }

    private fun initView(){

    }

    private fun onClick(){
        ll_team_manager.setOnClickListener {
            if (BaseUtils.isFastClick()){

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
                val intent = Intent(activity,TeacherManagerActivity::class.java)
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
                val intent = Intent(activity, StatisticsActivity::class.java)
                startActivityForResult(intent, 2)
            }
        }


    }

    private fun setting(){
        tv_team_price.text = "¥1000.00"
        tv_team_order.text = "30"
        tv_teacher_price.text = "¥1000.00"
        tv_teacher_order.text = "30"
        tv_venue_price.text = "¥1000.00"
        tv_venue_order.text = "30"
    }



    private val xiaoshouStrings = ArrayList<PieEntry>()
    private val colors = ArrayList<Int>()
    private fun initAllPieChart(isLabel: Boolean) {
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

}