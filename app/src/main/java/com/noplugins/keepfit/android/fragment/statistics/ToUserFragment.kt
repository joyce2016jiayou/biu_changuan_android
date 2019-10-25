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
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.noplugins.keepfit.android.bean.UserStatisticsBean
import com.noplugins.keepfit.android.chart.RmbFormatter
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.screen.KeyboardUtils
import com.noplugins.keepfit.android.util.ui.chart.MyBarChart
import kotlin.collections.ArrayList


class ToUserFragment : BaseFragment() {

    var selectDate = "2019-10"
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
        initAllPieChart(false)
        initPieChart(true)
        initMorePieChart()

        select_time.setOnClickListener {
            select_time_pop()
        }
    }

    override fun onFragmentVisibleChange(isVisible: Boolean) {
        super.onFragmentVisibleChange(isVisible)
        requestUser(false)
    }

    private fun requestUser(isBoolean:Boolean) {

        val params = HashMap<String, Any>()
        params["type"] = 1
        params["date"] = selectDate
        params["areaNum"] = "GYM19091283573448"
        val subscription = Network.getInstance("精准化时间", activity)
                .statistics(
                        params,
                        ProgressSubscriber("精准化时间", object : SubscriberOnNextListener<Bean<UserStatisticsBean>> {
                            override fun onNext(result: Bean<UserStatisticsBean>) {
                                setting(result.data)
                            }

                            override fun onError(error: String) {
                                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                            }
                        }, activity, isBoolean)
                )
    }

    val timeStrings = ArrayList<PieEntry>()
    val ageStrings = ArrayList<PieEntry>()
    val sexStrings = ArrayList<PieEntry>()
    val colors = ArrayList<Int>()

    private fun setting(bean: UserStatisticsBean) {
        timeStrings.clear()
        ageStrings.clear()
        sexStrings.clear()

        bean.time.forEach {
            timeStrings.add(PieEntry(it.num.toFloat(), it.value))
        }

        bean.age.forEach {
            if (it.num!=0){
                ageStrings.add(PieEntry(it.num.toFloat(), it.value))
            }
        }

        bean.sex.forEach {
            sexStrings.add(PieEntry(it.num.toFloat(), it.value))
        }

        colors.add(parseColor("#707BCC"))
        colors.add(parseColor("#5CCEFF"))
        colors.add(parseColor("#828AD3"))
        colors.add(parseColor("#FFB963"))
        colors.add(parseColor("#EE7C61"))
        colors.add(parseColor("#588B4C"))
        colors.add(parseColor("#8ED06D"))
        colors.add(parseColor("#9B9791"))


        val dataSet = PieDataSet(timeStrings, "")
        dataSet.colors = colors
        val pieData = PieData(dataSet)
        pieData.setDrawValues(true)
        pieData.setValueFormatter(com.noplugins.keepfit.android.chart.PercentFormatter(picChart))
        pieData.setValueTextSize(9f)
        picChart.data = pieData
        picChart.invalidate()
//        val legend = picChart.legend
//        legend.setCustom(getEntries(timeStrings, colors))

        val dataSet1 = PieDataSet(ageStrings, "")
        dataSet1.colors = colors
        dataSet1.valueLineWidth = 1f
        dataSet1.valueLinePart1OffsetPercentage = 100f
        dataSet1.valueLinePart1Length = 0.5f
        dataSet1.valueLinePart2Length = 0.6f
        dataSet1.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataSet1.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        val pieData1 = PieData(dataSet1)
        pieData1.setDrawValues(true)
        pieData1.setValueFormatter(com.noplugins.keepfit.android.chart.PercentFormatter(picChart))
        pieData1.setValueTextSize(9f)
        picAgeChart.data = pieData1
        picAgeChart.invalidate()

        val dataSet2 = PieDataSet(sexStrings, "")
        dataSet2.colors = colors
        val pieData2 = PieData(dataSet2)
        pieData2.setDrawValues(true)
        pieData2.setValueFormatter(com.noplugins.keepfit.android.chart.PercentFormatter(picSexChart))
        pieData2.setValueTextSize(9f)
        picSexChart.data = pieData2
        picSexChart.invalidate()
        val legend2 = picSexChart.legend
        legend2.setCustom(getEntries(sexStrings, colors))
    }

    private fun initAllPieChart(isLabel: Boolean) {
        val dataSet = PieDataSet(timeStrings, "")
        dataSet.colors = colors
        val pieData = PieData(dataSet)
        pieData.setDrawValues(true)
        picChart.setUsePercentValues(true)
        pieData.setValueFormatter(com.noplugins.keepfit.android.chart.PercentFormatter(picChart))
        pieData.setValueTextSize(9f)
        picChart.data = pieData
        picChart.invalidate()
        // 不显示图例
        val legend = picChart.legend
        legend.yOffset = 50f
//        legend.xOffset = -420f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
//        legend.direction = Legend.LegendDirection.RIGHT_TO_LEFT
//        legend.direction = Legend.LegendDirection.LEFT_TO_RIGHT

//        legend.setEnabled(false)
        picChart.isRotationEnabled = false
        picChart.setEntryLabelColor(Color.BLACK)
        picChart.setDrawEntryLabels(isLabel)
        val description = Description()
        description.text = ""
        picChart.description = description
        picChart.holeRadius = 0f
        picChart.x = -120f
        picChart.transparentCircleRadius = 0f
        picChart.extraTopOffset = 20f
        picChart.extraBottomOffset = 20f


    }

    private fun initPieChart(isLine: Boolean) {
        val dataSet = PieDataSet(ageStrings, "")

        dataSet.colors = colors
        if (isLine) {
            dataSet.valueLineWidth = 1f
            dataSet.valueLinePart1OffsetPercentage = 100f
            dataSet.valueLinePart1Length = 0.5f
            dataSet.valueLinePart2Length = 0.6f
        }
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        val pieData = PieData(dataSet)
        picAgeChart.setUsePercentValues(true)
        pieData.setValueFormatter(com.noplugins.keepfit.android.chart.PercentFormatter(picAgeChart))
        pieData.setValueTextSize(12f)
        picAgeChart.data = pieData
        picAgeChart.setEntryLabelColor(Color.BLACK)
        picAgeChart.invalidate()
        // 不显示图例
        val legend = picAgeChart.legend
        legend.isEnabled = false
//
        picAgeChart.isRotationEnabled = false
        picAgeChart.setDrawEntryLabels(true)


        val description = Description()
        description.text = ""
        picAgeChart.description = description
        picAgeChart.holeRadius = 80f
        picAgeChart.transparentCircleRadius = 0f
        picAgeChart.extraTopOffset = 20f
        picAgeChart.extraBottomOffset = 20f


    }

    private fun initMorePieChart() {
        val dataSet = PieDataSet(sexStrings, "总人数：1000")
        dataSet.colors = colors
        val pieData = PieData(dataSet)
//        pieData.setDrawValues(true)
        picSexChart.setUsePercentValues(true)
        pieData.setValueFormatter(PercentFormatter(picSexChart))
        pieData.setValueTextSize(12f)
        picSexChart.data = pieData
        picSexChart.invalidate()
        // 不显示图例
        val legend = picSexChart.legend
        legend.yOffset = 40f
//        legend.xOffset = -50f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.direction = Legend.LegendDirection.LEFT_TO_RIGHT

        legend.setCustom(getEntries(sexStrings, colors))

//        legend.setEnabled(false)
        picSexChart.isRotationEnabled = false
        picSexChart.setDrawEntryLabels(true)
        picSexChart.setEntryLabelColor(Color.BLACK)
        val description = Description()
        description.text = ""
        picSexChart.description = description
        picSexChart.holeRadius = 0f
        picSexChart.x = -100f
        picSexChart.transparentCircleRadius = 0f
        picSexChart.extraTopOffset = 20f
        picSexChart.extraBottomOffset = 20f


    }


    private fun getEntries(strings: List<PieEntry>, colors: List<Int>): List<LegendEntry> {
        val entries = ArrayList<LegendEntry>()

        val legendEntry = LegendEntry()
        var sum = 0f
        strings.forEach {
            sum += it.value
        }
        legendEntry.label = "总人数：${sum.toInt()}"
        legendEntry.form = Legend.LegendForm.NONE
        entries.add(legendEntry)
        for (i in 0 until strings.size) {
            entries.add(LegendEntry(
                    strings[i].label + ":" + strings[i].value.toInt(),
                    Legend.LegendForm.CIRCLE,
                    10f,
                    9f,
                    null,
                    colors[i]))
        }

        return entries
    }


    lateinit var pvCustomTime: TimePickerView
    private fun select_time_pop() {

        val selectedDate = Calendar.getInstance()//系统当前时间
        val startDate = Calendar.getInstance()
        startDate.set(2014, 1, 23)
        val endDate = Calendar.getInstance()
        endDate.set(2027, 2, 28)
        //时间选择器 ，自定义布局
        pvCustomTime = TimePickerBuilder(activity, OnTimeSelectListener { date, v ->
            //选中事件回调
            Log.e("选择的时间", date.toString())
            val select_year = date.year + 1900
            var select_month = date.month.toString() + ""
            if (Integer.valueOf(select_month) < 9) {
                select_month = "0" + (date.month + 1)
            } else {
                select_month = "" + (date.month + 1)
            }
            tv_select_time.text = select_year.toString() + "年" + select_month + "月"
            selectDate = "$select_year-$select_month"

            requestUser(true)
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time) { v ->
                    val quxiao_btn = v.findViewById<View>(R.id.quxiao_btn) as TextView
                    val sure_btn = v.findViewById<View>(R.id.sure_btn) as TextView
                    sure_btn.setOnClickListener {
                        pvCustomTime.returnData()
                        pvCustomTime.dismiss()
                    }
                    quxiao_btn.setOnClickListener { pvCustomTime.dismiss() }
                }

                .setContentTextSize(20)
                .setType(booleanArrayOf(true, true, false, false, false, false))
                .setLabel("  年", "  月", "  日", "  时", "  分", "  秒")
                .setLineSpacingMultiplier(1.5f)
                .setTextXOffset(0, 0, 0, 60, 0, -60)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.parseColor("#00000000"))
                .build()
        pvCustomTime.show()

        //影藏键盘
        KeyboardUtils.hideSoftKeyboard(activity)
    }

}

