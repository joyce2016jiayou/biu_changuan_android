package com.noplugins.keepfit.android.fragment.use.team

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.adapter.ManagerTeamClassAdapter
import com.noplugins.keepfit.android.base.BaseFragment
import com.noplugins.keepfit.android.bean.use.ManagerBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.fragment_manager_teacher_1.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.HashMap

class HistoryFragment : BaseFragment()  {
    companion object {
        fun newInstance(title: String): HistoryFragment {
            val fragment = HistoryFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }
    var  datas:MutableList<ManagerBean.CourseListBean> = ArrayList()
    lateinit var adapterManager : ManagerTeamClassAdapter
    var newView: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_manager_teacher_1, container, false)
            EventBus.getDefault().register(this)
        }
        return newView
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun upadate(messageEvent:String ) {
        if (AppConstants.TEAM_YQ_REFUSE == messageEvent){
            requestData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        requestData()
    }
    override fun onFragmentVisibleChange(isVisible: Boolean) {
        super.onFragmentVisibleChange(isVisible)
        if (isVisible){
//            requestData()
        }
    }

    private fun initAdapter(){
        rv_list.layoutManager = LinearLayoutManager(context)
        adapterManager = ManagerTeamClassAdapter(datas)
        val view = LayoutInflater.from(context).inflate(R.layout.enpty_view, rv_list, false)
        adapterManager.emptyView = view
        rv_list.adapter = adapterManager
        adapterManager.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.rl_jump -> {
                    // 跳转到详情页 需要携带状态
//                    val toInfo = Intent(activity, TeamInfoActivity::class.java)
//                    val bundle = Bundle()
//                    bundle.putInt("type",3)
//                    bundle.putString("courseNum",datas[position].courseNum)
//                    bundle.putInt("status",datas[position].status)
//                    toInfo.putExtras(bundle)
//                    startActivity(toInfo)
                }
                R.id.tv_yaoqin_edit -> {

                }
            }
        }

//        refresh_layout.setEnableRefresh(false)
        refresh_layout.setEnableLoadMore(false)
        refresh_layout.setOnRefreshListener {
            //下拉刷新
            requestData()
            refresh_layout.finishRefresh(2000/*,false*/)
        }
        refresh_layout.setOnLoadMoreListener {
            //上拉加载
            refresh_layout.finishLoadMore(2000/*,false*/)
        }

    }

    private fun requestData(){
        val params = HashMap<String, Any>()
        params["areaNum"] = SpUtils.getString(activity, AppConstants.CHANGGUAN_NUM)
        params["type"] = 3
        val subscription = Network.getInstance("课程管理历史", activity)
                .courseManagerByArea(params,
                        ProgressSubscriber("课程管理历史", object : SubscriberOnNextListener<Bean<ManagerBean>> {
                            override fun onNext(result: Bean<ManagerBean>) {
                                datas.clear()
                                datas.addAll(result.data.courseList)
                                adapterManager.notifyDataSetChanged()
                            }

                            override fun onError(error: String) {
                                SuperCustomToast.getInstance(activity)
                                        .show(error)

                            }
                        }, activity, false)
                )
    }


}