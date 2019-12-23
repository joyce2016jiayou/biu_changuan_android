package com.noplugins.keepfit.android.fragment.use.team

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.use.TeamInfoActivity
import com.noplugins.keepfit.android.adapter.ManagerTeamClassAdapter
import com.noplugins.keepfit.android.base.BaseFragment
import com.noplugins.keepfit.android.bean.DictionaryeBean
import com.noplugins.keepfit.android.bean.use.ManagerBean
import com.noplugins.keepfit.android.bean.use.RoomBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow
import com.noplugins.keepfit.android.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.fragment_manager_teacher_1.*
import lib.demo.spinner.MaterialSpinner
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.HashMap

class ApplyFragment : BaseFragment() {
    companion object {
        fun newInstance(title: String): ApplyFragment {
            val fragment = ApplyFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }

    var datas: MutableList<ManagerBean.CourseListBean> = ArrayList()
    lateinit var adapterManager: ManagerTeamClassAdapter
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
        if (AppConstants.TEAM_YQ_AGREE == messageEvent||AppConstants.TEAM_YQ_REFUSE == messageEvent){
            requestData()
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        requestData()
    }

    override fun onFragmentVisibleChange(isVisible: Boolean) {
        super.onFragmentVisibleChange(isVisible)
        if (isVisible) {

        }
    }

    private fun initAdapter() {
        rv_list.layoutManager = LinearLayoutManager(context)
        adapterManager = ManagerTeamClassAdapter(datas)
        val view = LayoutInflater.from(context).inflate(R.layout.enpty_view, null, false)
        adapterManager.emptyView = view
        rv_list.adapter = adapterManager

        adapterManager.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.rl_jump -> {
                    //跳转到详情 需要携带状态
                    val toInfo = Intent(activity, TeamInfoActivity::class.java)
                    val bundle = Bundle()
                    bundle.putInt("type", 4)
                    bundle.putString("courseNum", datas[position].courseNum)
                    bundle.putInt("status",datas[position].status)
                    toInfo.putExtras(bundle)
                    this.startActivity(toInfo)
                }
                R.id.tv_jujue -> {
                    if (datas[position].refuseType == 1){
                        toJujue(view as TextView, position)
                    } else{
                        //不能拒绝
                    }
                }
                R.id.tv_jieshou -> {
                    //接受
                    toAgree(view as TextView, position)
//                    agreeCourse(position, 1, "")
                }
            }
        }
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

    private fun requestData() {
        val params = HashMap<String, Any>()
        params["areaNum"] = SpUtils.getString(activity, AppConstants.CHANGGUAN_NUM)
        params["type"] = 4
        val subscription = Network.getInstance("课程管理申请", activity)
                .courseManagerByArea(params,
                        ProgressSubscriber("课程管理申请", object : SubscriberOnNextListener<Bean<ManagerBean>> {
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


    private var roomType = ""
    private var roomNumber = ""
    private var roomName = ""
    private var maxNum = ""
    private fun toAgree(view1: TextView, position: Int){
        val popupWindow = CommonPopupWindow.Builder(activity)
                .setView(R.layout.dialog_to_agree)
                .setBackGroundLevel(0.5f)//0.5f
                .setAnimationStyle(R.style.main_menu_animstyle)
                .setWidthAndHeight(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT
                )
                .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(view1)

        /**设置逻辑 */
        val view = popupWindow.contentView
        val cancel = view.findViewById<TextView>(R.id.tv_cancel)
        val sure = view.findViewById<TextView>(R.id.tv_add)
        val msRoomType = view.findViewById<MaterialSpinner>(R.id.ms_room_type)
        val msRoomName = view.findViewById<MaterialSpinner>(R.id.ms_room_name)
        val msRoomMax = view.findViewById<TextView>(R.id.tv_max_number)
        requestRoomType(msRoomType,msRoomName,msRoomMax)
        cancel.setOnClickListener {
            popupWindow.dismiss()
        }
        sure.setOnClickListener {
            popupWindow.dismiss()
            //去申请
            roomType = msRoomType.text.toString()
            roomName = msRoomName.text.toString()
            maxNum = msRoomMax.text.toString()
            agreeCourse(position, 1,"")

        }
    }

    /**
     * 获取所有的房间类型
     */
    private fun requestRoomType(ms:MaterialSpinner,ms2: MaterialSpinner,max:TextView) {
        val params = HashMap<String, Any>()
        params["object"] = "room_type"

        val subscription = Network.getInstance("获取所有的房间类型", activity)
                .get_types(params, ProgressSubscriber("获取所有的房间类型",
                        object : SubscriberOnNextListener<Bean<List<DictionaryeBean>>> {
                            override fun onNext(bean: Bean<List<DictionaryeBean>>) {

                                if (bean.data != null) {
                                    val list = bean.data
                                    val data:MutableList<String> = ArrayList()
                                    bean.data.forEach {
                                        data.add(it.name)
                                    }
                                    initMs(ms,ms2,max, list, data)
                                }
                            }

                            override fun onError(error: String) {
                                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                            }
                        }, activity, true))

    }

    private fun initMs(ms: MaterialSpinner,ms2: MaterialSpinner,max: TextView
                       ,list:List<DictionaryeBean>,data:MutableList<String>) {
        ms.setItems(data)
        ms.selectedIndex = 0
        requestRoom(list[0].value,ms2,max)
        ms.setOnItemSelectedListener { _, position, _, _ ->
            ms.text = list[position].name
            requestRoom(list[position].value,ms2,max)
        }
        ms.setOnNothingSelectedListener { spinner -> spinner.selectedIndex }
    }


    private fun initMsRoomName(ms: MaterialSpinner,list:List<RoomBean>,data:MutableList<String>,max: TextView) {
        ms.setItems(data)
        ms.selectedIndex = 0
        roomNumber = list[0].placeNum
        ms.setOnItemSelectedListener { _, position, _, _ ->
            ms.text = list[position].placeName
            roomNumber = list[position].placeNum
            max.setText(list[position].maxNum)
        }
        ms.setOnNothingSelectedListener { spinner -> spinner.selectedIndex }
    }

    /**
     *根据类型查询所有房间
     */
    private fun requestRoom(type:String,ms: MaterialSpinner,max: TextView) {
        val params = HashMap<String, Any>()
        params["areaNum"] = SpUtils.getString(activity,AppConstants.CHANGGUAN_NUM)
        params["placeType"] = type

        val subscription = Network.getInstance("获取房间", activity)
                .getAreaPlace(params, ProgressSubscriber("获取房间",
                        object : SubscriberOnNextListener<Bean<List<RoomBean>>> {
                            override fun onNext(bean: Bean<List<RoomBean>>) {
                                if (bean.data != null) {
                                    val data:MutableList<String> = ArrayList()
                                    bean.data.forEach {
                                        data.add(it.placeName)
                                    }
                                    initMsRoomName(ms,bean.data,data,max)
                                }
                            }
                            override fun onError(error: String) {
                                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                            }
                        }, activity, true))
    }

    private fun toJujue(view1: TextView, position: Int) {
        val popupWindow = CommonPopupWindow.Builder(activity)
            .setView(R.layout.dialog_to_jujue)
            .setBackGroundLevel(0.5f)//0.5f
            .setAnimationStyle(R.style.main_menu_animstyle)
            .setWidthAndHeight(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            .setOutSideTouchable(true).create()
        popupWindow.showAsDropDown(view1)

        /**设置逻辑 */
        val view = popupWindow.contentView
        val cancel = view.findViewById<TextView>(R.id.tv_cancel)
        val sure = view.findViewById<TextView>(R.id.tv_add)
        val edit = view.findViewById<EditText>(R.id.et_content)
        cancel.setOnClickListener {
            popupWindow.dismiss()
        }
        sure.setOnClickListener {
            popupWindow.dismiss()
            //去申请
            agreeCourse(position, 0, edit.text.toString())

        }
    }

    private fun agreeCourse(position: Int, type: Int, str: String) {
        val params = HashMap<String, Any>()
        params["areaNum"] = SpUtils.getString(activity, AppConstants.CHANGGUAN_NUM)
        params["courseNum"] = datas[position].courseNum
        params["agree"] = type
        if (type == 0) {
            params["refuse"] = str
        } else {
            params["place_num"] = roomNumber
            params["place_type"] = roomType
            params["place_name"] = roomName
            params["max_num"] = maxNum
        }
        val subscription = Network.getInstance("团课同意/拒绝", activity)
            .agreeCourseByArea(
                params,
                ProgressSubscriber("团课同意/拒绝", object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {
                        //上架成功！
                       if (result.code == 0){
                           datas.removeAt(position)//删除数据源,移除集合中当前下标的数据
                           adapterManager.notifyItemRemoved(position)//刷新被删除的地方
                           adapterManager.notifyItemRangeChanged(position, adapterManager.itemCount) //刷新被删除数据，以及其后面的数据

                           when (type) {
                               1 -> {
                                   EventBus.getDefault().post(AppConstants.TEAM_YQ_AGREE)
                               }

                               0 -> {
                                   EventBus.getDefault().post(AppConstants.TEAM_YQ_REFUSE)
                               }
                           }
                       }

                        SuperCustomToast.getInstance(activity)
                            .show(result.message)
                    }

                    override fun onError(error: String) {
                        SuperCustomToast.getInstance(activity)
                            .show(error)
                    }
                }, activity, false)
            )
    }
    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }
}