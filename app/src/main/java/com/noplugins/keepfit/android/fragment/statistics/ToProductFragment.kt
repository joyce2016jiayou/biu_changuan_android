package com.noplugins.keepfit.android.fragment.statistics

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseFragment
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.StackedValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.noplugins.keepfit.android.chart.RmbFormatter
import kotlinx.android.synthetic.main.fragment_statistics_product.*
import java.util.*
import kotlin.collections.ArrayList
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.Legend
import com.noplugins.keepfit.android.bean.UserStatisticsBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.screen.KeyboardUtils

class ToProductFragment : BaseFragment() {

    val canler = Calendar.getInstance()
    val year = canler.get(Calendar.YEAR)
    val month = if (canler.get(Calendar.MONTH) + 1 > 9) "${canler.get(Calendar.MONTH) + 1}"
    else "0${canler.get(Calendar.MONTH) + 1}"
    var selectDate = "$year-$month"

    companion object {
        fun newInstance(title: String): ToProductFragment {
            val fragment = ToProductFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }

    var newView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_statistics_product, container, false)
        }
        return newView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_select_time.text = selectDate

        select_time.setOnClickListener {
            select_time_pop()
        }


    }

    override fun onFragmentFirstVisible() {
        super.onFragmentFirstVisible()
        colors.add(Color.parseColor("#707BCC"))
        colors.add(Color.parseColor("#5CCEFF"))
        colors.add(Color.parseColor("#828AD3"))
        initAllPieChart(true)
        initLineChart()
        initBarChart()
        requestUser(false)
    }
//    override fun onFragmentVisibleChange(isVisible: Boolean) {
//        super.onFragmentVisibleChange(isVisible)
////        setting()
//
//    }

    val xiaoshouStrings = ArrayList<PieEntry>()
    val colors = ArrayList<Int>()
    val entries = ArrayList<Entry>()
    val entries1 = ArrayList<Entry>()
    val values = ArrayList<BarEntry>()
    val functionList = ArrayList<UserStatisticsBean.FunctionBean>()
    val string: MutableList<String> = ArrayList()
    private fun setting(user: UserStatisticsBean) {
        xiaoshouStrings.clear()
        entries.clear()
        entries1.clear()
        values.clear()
        functionList.clear()
        if (user.function != null && user.function.size > 0) {
            functionList.addAll(user.function)
            functionList.forEach {
                string.add(it.value)
            }
            user.function.forEachIndexed { index, functionBean ->
                values.add(BarEntry(index.toFloat(),
                        floatArrayOf(functionBean.prices[0].price.toFloat(),
                                functionBean.prices[1].price.toFloat(),
                                functionBean.prices[2].price.toFloat())))
            }

        } else {

        }

        if (user.sales.product != null && user.sales.product.size > 0) {
            user.sales.product.forEach {
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
        } else {
            picChart.visibility = View.INVISIBLE
        }

        //折线图不可能为空
        if (user.sales.y2y != null && user.sales.y2y.size > 0) {
            user.sales.y2y.forEachIndexed { index, y2yBean ->
                entries.add(Entry(index.toFloat(), y2yBean.percent.toFloat()))
            }
        }
        if (user.sales.m2m != null && user.sales.m2m.size > 0) {
            user.sales.m2m.forEachIndexed { index, m2mBean ->
                entries1.add(Entry(index.toFloat(), m2mBean.percent.toFloat()))
            }
        }


        val dataSet2 = LineDataSet(entries, "Label") // add entries to dataset
        dataSet2.color = colors[0]//线条颜色
        dataSet2.setCircleColor(colors[0])//圆点颜色
        dataSet2.lineWidth = 1f//线条宽度

        val dataSet1 = LineDataSet(entries1, "Label") // add entries to dataset
        dataSet1.color = colors[1]//线条颜色
        dataSet1.setCircleColor(colors[1])//圆点颜色
        dataSet1.lineWidth = 1f//线条宽度
        val dataSets = ArrayList<ILineDataSet>()
//        dataSet1.valueFormatter = PercentFormatter()
        dataSets.add(dataSet2)
        dataSets.add(dataSet1)
        //3.chart设置数据
        val lineData = LineData(dataSets)
        lineData.setDrawValues(false)
        lineChart.data = lineData
        lineChart.invalidate()

        ///柱状图
        val set1 = BarDataSet(values, "")
        set1.setDrawIcons(false)
        set1.colors = getColors().asList()
//      set1.setStackLabels(String[]{"Births", "Divorces", "Marriages"})
        set1.setStackLabels(string.toTypedArray())
        val dataBarSets: MutableList<IBarDataSet> = ArrayList()
        dataBarSets.add(set1)
        val data = BarData(dataBarSets)
        data.setValueFormatter(StackedValueFormatter(false, "", 1))
        data.setValueTextColor(Color.WHITE)
        barChart.data = data
        barChart.setFitBars(true)
        barChart.invalidate()
        val xLabels = barChart.xAxis
        xLabels.setValueFormatter(MyXFormatter(string))
        val l = barChart.legend
        l.xEntrySpace = 20f
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setCustom(getEntries())

    }

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

    private fun initLineChart() {
        val dataSet = LineDataSet(entries, "这是1") // add entries to dataset
        dataSet.color = colors[0]//线条颜色
        dataSet.setCircleColor(colors[0])//圆点颜色
        dataSet.lineWidth = 1f//线条宽度

        val dataSet1 = LineDataSet(entries1, "这是2") // add entries to dataset
        dataSet1.color = colors[1]//线条颜色
        dataSet1.setCircleColor(colors[1])//圆点颜色
        dataSet1.lineWidth = 1f//线条宽度
        val dataSets = ArrayList<ILineDataSet>()
        dataSet.valueFormatter = PercentFormatter()

        dataSets.add(dataSet)
        dataSets.add(dataSet1)
        //3.chart设置数据
        val lineData = LineData(dataSets)
        lineData.setDrawValues(false)
        lineChart.data = lineData
        lineChart.invalidate() // refresh

        val rightAxis = lineChart.axisRight
        //设置图表右边的y轴禁用
        rightAxis.isEnabled = false
        val leftAxis = lineChart.axisLeft
        leftAxis.isEnabled = true
//        leftAxis.setGridColor(Color.TRANSPARENT)
        leftAxis.valueFormatter = PercentFormatter()
        leftAxis.labelCount = 5
        leftAxis.enableGridDashedLine(10f, 10f, 0f)
        leftAxis.setDrawZeroLine(false)
        lineChart.isHighlightPerTapEnabled = false//是否点击高亮
        lineChart.isDragEnabled = true// 是否可以拖拽
        lineChart.setScaleEnabled(false)// 是否可以缩放

        val xAxis = lineChart.getXAxis()
        xAxis.labelCount = 12
        xAxis.textColor = Color.parseColor("#333333")
        xAxis.textSize = 11f
        xAxis.axisMinimum = 0f
        xAxis.gridColor = Color.TRANSPARENT
        xAxis.setDrawAxisLine(true)//是否绘制轴线
        xAxis.setDrawGridLines(false)//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true)//绘制标签  指x轴上的对应数值
        xAxis.position = XAxisPosition.BOTTOM//设置x轴的显示位置
        xAxis.granularity = 1f//禁止放大后x轴标签重绘
        val list = ArrayList<String>()
        for (i in 0 until 12) {
            list.add((i + 1).toString() + "月")
        }
        xAxis.valueFormatter = IndexAxisValueFormatter(list)

        //隐藏x轴描述
        val description = Description()
        description.isEnabled = false
        lineChart.description = description

        //透明化图例
        val l = lineChart.legend
        l.xEntrySpace = 20f
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setCustom(getLineEntries())

        lineChart.setBackgroundColor(Color.WHITE)
        //是否显示边界
        lineChart.setDrawBorders(false)
        //是否展示网格线
        lineChart.setDrawGridBackground(false)
        //高亮
        lineChart.isHighlightPerDragEnabled = false

        xAxis.setDrawGridLines(false)
        rightAxis.setDrawGridLines(false)
        leftAxis.setDrawGridLines(true)


    }

    private fun initBarChart() {

        val set1 = BarDataSet(values, "")
        set1.setDrawIcons(false)
        set1.colors = getColors().asList()
//        set1.setStackLabels(string.toTypedArray())
        val dataSets: MutableList<IBarDataSet> = ArrayList()
        dataSets.add(set1)

        val data = BarData(dataSets)
        data.setValueFormatter(StackedValueFormatter(false, "", 1))
        data.setValueTextColor(Color.WHITE)

        barChart.data = data

        barChart.setFitBars(true)
        barChart.invalidate()
//        barChart.setVisibleXRangeMaximum(10f)
        barChart.description.isEnabled = false
        //高亮
        barChart.isHighlightPerDragEnabled = false

        // if more than 60 entries are displayed in the barChart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(40)

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false)

        barChart.setDrawGridBackground(false)
        barChart.setDrawBarShadow(false)

        barChart.setDrawValueAboveBar(false)
        barChart.isHighlightPerTapEnabled = false//是否点击高亮

        barChart.isDragEnabled = true// 是否可以拖拽
        barChart.setScaleEnabled(false)// 是否可以缩放

        barChart.axisRight.isEnabled = false
        // change the position of the y-labels
        val leftAxis = barChart.axisLeft
//        leftAxis.setValueFormatter()
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        leftAxis.setDrawZeroLine(false)
        leftAxis.valueFormatter = RmbFormatter()
        leftAxis.enableGridDashedLine(10f, 10f, 0f)

        val xLabels = barChart.xAxis
//        xLabels.labelCount = 12
        xLabels.textColor = Color.parseColor("#333333")
//        xLabels.textSize = 11f
//        xLabels.axisMinimum = 0f
//        xLabels.gridColor = Color.TRANSPARENT
        xLabels.setDrawAxisLine(true)//是否绘制轴线
        xLabels.setDrawGridLines(false)//设置x轴上每个点对应的线
//        xLabels.setDrawLabels(true)//绘制标签  指x轴上的对应数值
        xLabels.position = XAxisPosition.BOTTOM//设置x轴的显示位置
        xLabels.granularity = 1f//禁止放大后x轴标签重绘

        // chart.setDrawXLabels(false)
        // chart.setDrawYLabels(false)

        // setting data


    }

    private fun getColors(): IntArray {

        // have as many colors as stack-values per entry
        return colors.toIntArray()
    }

    private fun getEntries(): List<LegendEntry> {
        val entries = ArrayList<LegendEntry>()
        for (i in 0 until values.size-1) {
            entries.add(LegendEntry(
                    "${functionList[0].prices[i].room}",
                    Legend.LegendForm.CIRCLE,
                    10f,
                    9f,
                    null,
                    getColors()[i]))
        }

        return entries
    }

    private fun getLineEntries(): List<LegendEntry> {
        val entries = ArrayList<LegendEntry>()
        entries.add(LegendEntry(
                "销售额同比",
                Legend.LegendForm.CIRCLE,
                10f,
                9f,
                null,
                getColors()[0]))

        entries.add(LegendEntry(
                "销售额环比",
                Legend.LegendForm.CIRCLE,
                10f,
                9f,
                null,
                getColors()[1]))

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

    private fun requestUser(isBoolean: Boolean) {

        val params = HashMap<String, Any>()
        params["type"] = 2
        params["date"] = selectDate
        params["areaNum"] = SpUtils.getString(activity, AppConstants.CHANGGUAN_NUM)
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
}