package com.noplugins.keepfit.android.fragment.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseFragment
import com.noplugins.keepfit.android.entity.PieBean
import com.openxu.cview.chart.bean.ChartLable
import kotlinx.android.synthetic.main.fragment_statistics_user.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import android.graphics.Color.parseColor
import com.github.mikephil.charting.data.*
import java.util.*
import com.github.mikephil.charting.data.LineData
import android.R.attr.entries
import android.graphics.Matrix
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.noplugins.keepfit.android.chart.RmbFormatter
import com.noplugins.keepfit.android.util.ui.chart.MyBarChart
import kotlin.collections.ArrayList


class ToUserFragment : BaseFragment() {
    companion object {
        fun newInstance(title: String): ToUserFragment {
            val fragment = ToUserFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }

    var newView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_statistics_user, container, false)
        }
        return newView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setting()
    }
    private val colors: IntArray? = null//颜色集合
    private val labels: Array<String>? = null//标签文本
    private val datas = floatArrayOf(16912f, 2488f, 600f)
    private fun setting(){

        val strings = ArrayList<PieEntry>()
        strings.add(PieEntry(40f, "aaa"))
        strings.add(PieEntry(160f, "bbb"))
        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#707BCC"))
        colors.add(Color.parseColor("#5CCEFF"))
        initAllPieChart(picChart,strings,colors,true)
        initPieChart(picAgeChart,strings,colors,true)
        initMorePieChart(picSexChart,strings,colors)
    }

    private fun initAllPieChart(picChart: PieChart, strings:List<PieEntry>,colors:List<Int>,isLabel: Boolean){
        val dataSet = PieDataSet(strings, "")
        dataSet.colors = colors
        val pieData = PieData(dataSet)
//        pieData.setDrawValues(true)
        picChart.setUsePercentValues(true)
        pieData.setValueFormatter(PercentFormatter(picChart))
        pieData.setValueTextSize(12f)
        picChart.data = pieData
        picChart.invalidate()
        // 不显示图例
        val legend = picChart.legend

        legend.yOffset = 5f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.form = Legend.LegendForm.CIRCLE

//        legend.setEnabled(false)
        picChart.isRotationEnabled = false
        picChart.setEntryLabelColor(Color.BLACK)
        picChart.setDrawEntryLabels(isLabel)
        val description = Description()
        description.text = ""
        picChart.description = description
        picChart.holeRadius = 0f
        picChart.x = -100f
        picChart.transparentCircleRadius = 0f
        picChart.extraTopOffset = 20f
        picChart.extraBottomOffset = 20f


    }

    private fun initPieChart(picChart: PieChart, strings:List<PieEntry>,colors:List<Int>,isLine:Boolean){
        val dataSet = PieDataSet(strings, "")

        dataSet.colors = colors
        if (isLine){
            dataSet.valueLineWidth = 1f
            dataSet.valueLinePart1OffsetPercentage= 100f
            dataSet.valueLinePart1Length= 0.5f
            dataSet.valueLinePart2Length= 0.6f
        }
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        val pieData = PieData(dataSet)
        picChart.setUsePercentValues(true)
        pieData.setValueFormatter(PercentFormatter(picChart))
        pieData.setValueTextSize(12f)
        picChart.data = pieData
        picChart.setEntryLabelColor(Color.BLACK)
        picChart.invalidate()
        // 不显示图例
        val legend = picChart.legend
        legend.isEnabled = false
//
        picChart.isRotationEnabled = false
        picChart.setDrawEntryLabels(true)


        val description = Description()
        description.text = ""
        picChart.description = description
        picChart.holeRadius = 80f
        picChart.transparentCircleRadius = 0f
        picChart.extraTopOffset = 20f
        picChart.extraBottomOffset = 20f


    }

    private fun initMorePieChart(picChart: PieChart, strings:List<PieEntry>,colors:List<Int>){
        val dataSet = PieDataSet(strings, "总人数：1000")
        dataSet.colors = colors
        val pieData = PieData(dataSet)
//        pieData.setDrawValues(true)
        picChart.setUsePercentValues(true)
        pieData.setValueFormatter(PercentFormatter(picChart))
        pieData.setValueTextSize(12f)
        picChart.data = pieData
        picChart.invalidate()
        // 不显示图例
        val legend = picChart.legend
        legend.yOffset = 5f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.direction = Legend.LegendDirection.LEFT_TO_RIGHT

        legend.setCustom(getEntries(strings,colors))

//        legend.setEnabled(false)
        picChart.isRotationEnabled = false
        picChart.setDrawEntryLabels(true)
        picChart.setEntryLabelColor(Color.BLACK)
        val description = Description()
        description.text = ""
        picChart.description = description
        picChart.holeRadius = 0f
        picChart.x = -100f
        picChart.transparentCircleRadius = 0f
        picChart.extraTopOffset = 20f
        picChart.extraBottomOffset = 20f


    }


    private fun getEntries( strings:List<PieEntry>,colors:List<Int>):List<LegendEntry>{
        val entries = ArrayList<LegendEntry>()

        val legendEntry = LegendEntry()
        legendEntry.label = "总人数：500"
        legendEntry.form =  Legend.LegendForm.NONE
        entries.add(legendEntry)
       for (i in 0 until strings.size){
           entries.add(LegendEntry(
                   strings[i].label+":500",
                   Legend.LegendForm.CIRCLE,
                   10f,
                   9f,
                   null,
                   colors[i]))
       }

        return entries
    }



}

