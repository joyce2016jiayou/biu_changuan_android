package com.noplugins.keepfit.android.activity.mine

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.CustomListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.adapter.mine.BillDetailAdapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.mine.BalanceListBean
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.screen.KeyboardUtils
import kotlinx.android.synthetic.main.activity_bill_detail.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max

class BillDetailActivity : BaseActivity() {
    val data:MutableList<BalanceListBean.ListBean> = ArrayList()
    var adapter: BillDetailAdapter ?= null
    var walletNum = ""
    var page = 1
    var maxPage = 1
    val canler = Calendar.getInstance()
    val year = canler.get(Calendar.YEAR)
    val month = if (canler.get(Calendar.MONTH) + 1 > 9) "${canler.get(Calendar.MONTH) + 1}"
    else "0${canler.get(Calendar.MONTH) + 1}"
    var selectDate = "$year-$month"
    override fun initBundle(parms: Bundle?) {
        if (intent.getStringExtra("walletNum")!=""){
            walletNum = intent.getStringExtra("walletNum")
            requestData()
        }
    }

    override fun initView() {
        setContentLayout(R.layout.activity_bill_detail)
        isShowTitle(true)
        setTitleView(R.string.tv_mingxi)
        tv_select_time.text = "${year}年${month}月"
        initAdapter()

    }

    override fun doBusiness(mContext: Context?) {
        title_left_button_onclick_listen {
            finish()
        }
        tv_select_time.setOnClickListener {
            select_time_pop()
        }
    }
    private lateinit var layoutManager: LinearLayoutManager
    private fun initAdapter(){
        adapter = BillDetailAdapter(data)
        layoutManager = LinearLayoutManager(this)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter
        val view = LayoutInflater.from(this).inflate(R.layout.enpty_view, rv_list, false)
        adapter!!.emptyView = view
        adapter!!.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.ll_item -> {
                    // 跳转到账单详情
                    val intent = Intent(this,BillDetailInfoActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("walletDetailNum",data[position].walletDetailNum)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }
        }

        refresh_layout.setOnRefreshListener {
            //下拉刷新
            page = 1
            refresh_layout.setEnableLoadMore(true)
            requestData()
            refresh_layout.finishRefresh(2000/*,false*/)
        }
        refresh_layout.setOnLoadMoreListener {
            //上拉加载
            page++
            requestData()
            refresh_layout.finishLoadMore(2000/*,false*/)
        }
    }
    lateinit var pvCustomTime: TimePickerView
    private fun select_time_pop() {

        val selectedDate = Calendar.getInstance()//系统当前时间
        val startDate = Calendar.getInstance()
        startDate.set(2014, 1, 23)
        val endDate = Calendar.getInstance()
        endDate.set(2027, 2, 28)
        //时间选择器 ，自定义布局
        pvCustomTime = TimePickerBuilder(this, OnTimeSelectListener { date, v ->
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

            requestData()
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
        KeyboardUtils.hideSoftKeyboard(this)
    }

    private fun requestData() {
        val params = HashMap<String, Any>()
//        params["teacherNum"] = SpUtils.getString(activity, AppConstants.USER_NAME)
        params["walletNum"] = walletNum
        params["date"] = selectDate
        params["page"] = page
        subscription = Network.getInstance("我的账户", this)
            .myBalanceList(
                params,
                ProgressSubscriber("我的账户", object : SubscriberOnNextListener<Bean<BalanceListBean>> {
                    override fun onNext(result: Bean<BalanceListBean>) {
                        tv_income.text = "收入：${result.data.monthIncome}"
                        tv_withdraw.text = "转出：${result.data.monthWithDraw}"
                        maxPage = result.data.maxPage
                        if (page == 1){
                            data.clear()
                            data.addAll(result.data.list)
                            adapter!!.notifyDataSetChanged()
                        } else {
                            data.addAll(result.data.list)
                            adapter!!.notifyDataSetChanged()
                        }

                        if (maxPage == page){
                            refresh_layout.setEnableLoadMore(false)
                        }
                    }

                    override fun onError(error: String) {


                    }
                }, this, false)
            )
    }

}
