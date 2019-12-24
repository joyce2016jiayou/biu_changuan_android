package com.noplugins.keepfit.android.activity.mine

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.google.gson.Gson
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.AddClassItemActivity
import com.noplugins.keepfit.android.activity.use.ClassItemEditActivity
import com.noplugins.keepfit.android.adapter.CgTeacherSelectAdapter
import com.noplugins.keepfit.android.adapter.PopUpAdapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.*
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.pop.SpinnerPopWindow
import com.umeng.socialize.utils.DeviceConfigInternal.context
import kotlinx.android.synthetic.main.activity_teacher_select.*
import org.greenrobot.eventbus.EventBus
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.util.HashMap

class TeacherSelectActivity : BaseActivity(), AMapLocationListener {

    var areaLIst:MutableList<GetQuCode.AreaBean> = ArrayList()

    var teamTypeList:MutableList<DictionaryeBean> = ArrayList()

    override fun onLocationChanged(amapLocation: AMapLocation?) {
        if (amapLocation != null) {
            if (amapLocation.errorCode == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.locationType//获取当前定位结果来源，如网络定位结果，详见定位类型表
                //                        latLonPoint = new LatLonPoint(currentLat, currentLon);  // latlng形式的
                /*currentLatLng = new LatLng(currentLat, currentLon);*/   //latlng形式的
//                amapLocation.accuracy//获取精度信息

                Log.d("LogInfo","getCity():"+amapLocation.city)
                Log.d("LogInfo","district():"+amapLocation.district)
                tv_location.text = amapLocation.district
                val code = amapLocation.adCode.toString().substring(0,4)+"00"
//                province = amapLocation.adCode.toString().substring(0,2)+"0000"
//                city = code
//                district = amapLocation.adCode
                initAdapter()
                getCity2Dis(code)
                agreeCourse()

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e(
                        "AmapError", "location Error, ErrCode:"
                        + amapLocation.errorCode + ", errInfo:"
                        + amapLocation.errorInfo
                )
                initAdapter()
                getCity2Dis("310100")
                agreeCourse()

            }
        }
    }

    private var page = 1
    var yaerSelect = -1
    var sexSelect = -1
    var areaSelect = -1
    var typeSelect = -1
    lateinit var adapter: CgTeacherSelectAdapter
    lateinit var layoutManager:LinearLayoutManager
    private var data: MutableList<TeacherBean> = ArrayList()
    private var submitList: MutableList<CgBindingBean.TeacherNumBean> = ArrayList()
    private var bind_teacher_list: MutableList<TeacherBean> = ArrayList()
    var courseNum = ""
    var enter_type = ""
    override fun initBundle(parms: Bundle?) {
        if (parms != null) {
            courseNum = parms.getString("courseNum", "")
            enter_type = parms.getString("enter_type", "")
        }
    }

    override fun initView() {
        setContentView(R.layout.activity_teacher_select)
        init()
        requestPermission()
        requestTeamType()
        initAdapter()
//        agreeCourse()
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.setOnClickListener {
            finish()
        }
        queren_btn.setOnClickListener {
            if (submitList.size <1){
                Toast.makeText(applicationContext,"选择教练不可为空哦",Toast.LENGTH_SHORT)
                        .show()
                return@setOnClickListener
            }
            if (enter_type.equals("add_page")) {
                AddClassItemActivity.submit_tescher_list.addAll(bind_teacher_list)

                AddClassItemActivity.is_refresh_teacher_list = true
                finish()
            } else if (enter_type.equals("edit_page")){
                ClassItemEditActivity.submit_tescher_list_edit.addAll(bind_teacher_list)

                ClassItemEditActivity.is_refresh_teacher_list_edit = true
                finish()
            }
            else {
                //除了绑定教练 ， 场馆的团课还可以选择教练来上课
                if (courseNum != "") {
                    inviteTeachers()
                } else {
                    binding()
                }
            }
        }
        iv_delete_edit.setOnClickListener {
            edit_search.setText("")
        }

        edit_search.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                //获取焦点
                ll_sousuo.visibility = View.GONE
                iv_delete_edit.visibility = View.VISIBLE
                iv_search.visibility = View.VISIBLE
            } else {//失去焦点
                if (edit_search.text.toString() == "") {
                    ll_sousuo.visibility = View.VISIBLE
                    iv_delete_edit.visibility = View.GONE
                    iv_search.visibility = View.GONE
                }
            }
        }
        ll_content.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                currentFocus?.let {

                    val mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    mInputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                    edit_search.clearFocus()
                    return false

                }
                return false
            }

        })
        rv_list.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                currentFocus?.let {

                    val mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    mInputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                    edit_search.clearFocus()
                    return false

                }
                return false
            }

        })
        edit_search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                    Log.d("EditorInfo", "当前点击了")
                    agreeCourse()
                    return false
                }
                Log.d("EditorInfo", "当前点击了qita")
                return true
            }
        })
    }

    private var popWindow2: SpinnerPopWindow<String>? = null
    private var popWindow3: SpinnerPopWindow<String>? = null
    private var popWindow1: SpinnerPopWindow<String>? = null
    private var popWindow4: SpinnerPopWindow<String>? = null

    private fun init(){
        val listYear = resources.getStringArray(R.array.private_year_types).toMutableList()
        popWindow2 = SpinnerPopWindow(applicationContext,
                listYear,
                PopUpAdapter.OnItemClickListener { _, _, position ->
                    tv_year_select.text = listYear[position]

                    popWindow2!!.dismiss()
                    yaerSelect = if (position == listYear.size -1){
                        -1
                    } else {
                        position + 1
                    }
                    data.clear()
                    page = 1
                    agreeCourse()
                    popWindow2!!.dismiss()
                })
        teacher_year.setOnClickListener {
            showPopwindow(popWindow2!!,teacher_year)

        }

        val listSex = resources.getStringArray(R.array.private_sex_types).toMutableList()
        popWindow3 = SpinnerPopWindow(applicationContext,
                listSex,
                PopUpAdapter.OnItemClickListener { _, _, position ->
                    tv_sex_select.text = listSex[position]
                    popWindow3!!.dismiss()
                    sexSelect = if (position == listSex.size -1){
                        -1
                    } else {
                        position
                    }
                    data.clear()
                    page = 1
                    agreeCourse()
                    popWindow3!!.dismiss()
                })
        teacher_sex.setOnClickListener {
            showPopwindow(popWindow3!!,teacher_sex)

        }
    }

    private fun showPopwindow(pop: SpinnerPopWindow<String>, view:View){
        pop.width = view.width
        pop.showAsDropDown(view)

    }

    private fun initAdapter(){
        adapter = CgTeacherSelectAdapter(data)
        layoutManager = LinearLayoutManager(this)
//        val view = LayoutInflater.from(context).inflate(R.layout.enpty_view, rv_list, false)
//        adapter.emptyView = view
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter

        adapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.rl_detail -> {
                    //ClassShouquanActivity
                    val intent = Intent(this, TeacherDetailActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("cgNum", data[position].teacherNum)
                    bundle.putInt("listItem", position)
                    intent.putExtras(bundle)
                    startActivityForResult(intent, 1)
                }

                R.id.ck_select -> {
                    //选中或者取消选中
                    if ((view as CheckBox).isChecked){
                        Log.d("item","点击了")
                        val bindingCgBean = CgBindingBean.TeacherNumBean()
                        bindingCgBean.num = data[position].teacherNum
                        submitList.add(bindingCgBean)
                        bind_teacher_list.add(data[position])
                    } else {
                        Log.d("item","取消了")
                        for (i in 0 until submitList.size){
                            if (submitList[i].num == data[position].teacherNum){
                                submitList.removeAt(i)
                                bind_teacher_list.removeAt(i)
                                return@setOnItemChildClickListener
                            }
                        }

                    }
                }
            }
        }

        refresh_layout.setEnableRefresh(true)
        refresh_layout.setEnableLoadMore(true)
        refresh_layout.setOnRefreshListener {
            //下拉刷新
            page = 1
            agreeCourse()
            refresh_layout.finishRefresh(1500/*,false*/)
        }
        refresh_layout.setOnLoadMoreListener {
            //上拉加载
            page++
            agreeCourse()
            refresh_layout.finishLoadMore(1500/*,false*/)
        }
    }

    private fun agreeCourse() {
        val params = HashMap<String, Any>()
        params["areaNum"] = SpUtils.getString(applicationContext, AppConstants.CHANGGUAN_NUM)
        params["page"] = page
        if (sexSelect > -1){
            params["sex"] = sexSelect
        }
        if(yaerSelect >-1){
            params["year"] = yaerSelect
        }
        if (edit_search.text.toString().isNotEmpty()){
            params["data"] = edit_search.text.toString().trim()
        }
        if (areaSelect != -1){
            params["districtcode"] = areaLIst[areaSelect].distcd
        }

        if (typeSelect != -1){
            params["skill"] = typeSelect
        }
        val subscription = Network.getInstance("场馆列表", this)
                .teacherMannerList(
                        params,
                        ProgressSubscriber("场馆列表", object : SubscriberOnNextListener<Bean<List<TeacherBean>>> {
                            override fun onNext(result: Bean<List<TeacherBean>>) {
                                submitList.clear()
                                if (page == 1){
                                    data.clear()
                                    data.addAll(result.data)
                                } else{
                                    data.addAll(result.data)
                                }
                                adapter.notifyDataSetChanged()
                            }

                            override fun onError(error: String) {


                            }
                        }, this, false)
                )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data1: Intent?) {
        super.onActivityResult(requestCode, resultCode, data1)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //接受回来对数据的处理
                val item = data1!!.getIntExtra("item", -1)
//                Log.e("1", "run:---------> $dataStringExtra2")
                if (item > -1) {
                    val childAt = layoutManager.findViewByPosition(item)
                    if (childAt != null) {
                        val chabox = childAt.findViewById<CheckBox>(R.id.ck_select)
                        chabox.isChecked = true
                    }

                    val bindingCgBean = CgBindingBean.TeacherNumBean()
                    bindingCgBean.num = data[item].teacherNum
                    submitList.add(bindingCgBean)
                }

            }
        }
    }

    /**
     * 团课邀请教练
     */

    private fun inviteTeachers(){
        val params = HashMap<String,Any>()
        params["gym_area_num"] = SpUtils.getString(this,AppConstants.CHANGGUAN_NUM)
        params["gen_teacher_num"] = submitList
        params["gym_course_num"] = courseNum

        val subscriber = Network.getInstance("团课邀请教练",this)
                .inviteTeachers(params,
                        ProgressSubscriber("团课邀请教练", object : SubscriberOnNextListener<Bean<Any>> {
                            override fun onNext(result: Bean<Any>) {
                                Toast.makeText(applicationContext,result.message,Toast.LENGTH_SHORT).show()
                                val mIntent = Intent()
                                setResult(RESULT_OK, mIntent)
                                finish()
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext,error,Toast.LENGTH_SHORT).show()
                            }
                        }, this, false))
    }


    /**
     * 场馆添加教练～
     */
    private fun binding(){
//        val params = HashMap<String, Any>()
        val cgBinding = CgBindingBean()
        cgBinding.areaNum = SpUtils.getString(applicationContext,AppConstants.CHANGGUAN_NUM)
        cgBinding.teacherNum = submitList
        val subscription = Network.getInstance("场馆绑定教练", this)
                .areaInviteTeacher(
                        cgBinding,
                        ProgressSubscriber("场馆绑定教练", object : SubscriberOnNextListener<Bean<Any>> {
                            override fun onNext(result: Bean<Any>) {
                                Toast.makeText(applicationContext,result.message,Toast.LENGTH_SHORT).show()
                                EventBus.getDefault().post("选择教练")
                                finish()
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext,error,Toast.LENGTH_SHORT).show()
                            }
                        }, this, false)
                )
    }

    /**
     * 获取课程类型
     */
    private fun requestTeamType() {
        val params = HashMap<String, Any>()
        params["object"] = "6"

        subscription = Network.getInstance("获取课程类型", applicationContext)
                .get_types(params, ProgressSubscriber("获取课程类型",
                        object : SubscriberOnNextListener<Bean<List<DictionaryeBean>>> {
                            override fun onNext(bean: Bean<List<DictionaryeBean>>) {
                                if (bean.data != null) {
                                    teamTypeList.addAll(bean.data)
                                    initTeamType(bean.data)
                                }
                            }
                            override fun onError(error: String) {
                                Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
                            }
                        }, this, true))

    }

    private fun initTeamType(list:List<DictionaryeBean>){
        val listString  = ArrayList<String>()
        listString.add("全部")
        list.forEach {
            listString.add(it.name)
        }

        popWindow4 = SpinnerPopWindow(applicationContext,
                listString,
                PopUpAdapter.OnItemClickListener { _, _, position ->
                    tv_class_select.text = listString[position]

                    popWindow4!!.dismiss()
                    typeSelect= if (position == 0){
                        -1
                    } else {
                        list[position-1].value.toInt()
                    }
                    data.clear()
                    page = 1
                    agreeCourse()
                    popWindow4!!.dismiss()
                })
        class_eat.setOnClickListener {
            showPopwindow(popWindow4!!,class_eat)

        }
    }
    /**
     * 地区选择
     */
    private fun getCity2Dis(citycd:String){
        val params = HashMap<String, Any>()
        params["citycd"] = citycd
        val subscription = Network.getInstance("获取区", this)
                .get_qu(
                        params,
                        ProgressSubscriber("获取区", object : SubscriberOnNextListener<Bean<GetQuCode>> {
                            override fun onNext(result: Bean<GetQuCode>) {
                                val list:MutableList<String> = ArrayList()
                                areaLIst = result.data.area
                                list.add("全部")
                                for (i in 0 until result.data.area.size){
                                    list.add(result.data.area[i].distnm)
                                }
                                //申请成功
                                initArea(list)
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext,error,Toast.LENGTH_SHORT).show()
                            }
                        }, this, false)
                )
    }

    private fun initArea(list: MutableList<String>) {
        popWindow1 = SpinnerPopWindow(applicationContext,
                list,
                PopUpAdapter.OnItemClickListener { _, _, position ->
                    tv_location.text = list[position]

                    popWindow1!!.dismiss()
                    areaSelect = if (position == 0){
                        -1
                    } else {
                        position-1
                    }
                    data.clear()
                    page = 1
                    agreeCourse()
                    popWindow1!!.dismiss()
                })
        ll_location.setOnClickListener {
            showPopwindow(popWindow1!!,ll_location)

        }
    }


    //声明AMapLocationClient类对象
    internal var mLocationClient: AMapLocationClient? = null
    //声明AMapLocationClientOption对象
    var mLocationOption: AMapLocationClientOption? = null

    private val mPerms = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
    private fun initGaode() {
        //初始化定位
        mLocationClient = AMapLocationClient(this)
        //设置定位回调监听
        mLocationClient!!.setLocationListener(this)
        //初始化AMapLocationClientOption对象
        mLocationOption = AMapLocationClientOption()

        mLocationOption!!.isOnceLocation = true
        //        mLocationOption.setOnceLocationLatest(true);
        // 同时使用网络定位和GPS定位,优先返回最高精度的定位结果,以及对应的地址描述信息
        mLocationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。默认连续定位 切最低时间间隔为1000ms
        //        mLocationOption.setInterval(3500);
        //给定位客户端对象设置定位参数
        mLocationClient!!.setLocationOption(mLocationOption)
        //启动定位
        mLocationClient!!.startLocation()
    }


    @AfterPermissionGranted(PERMISSIONS)
    private fun requestPermission() {
        if (EasyPermissions.hasPermissions(this, *mPerms)) {
            //Log.d(TAG, "onClick: 获取读写内存权限,Camera权限和wifi权限");
            initGaode()

        } else {
            EasyPermissions.requestPermissions(this, "获取读写内存权限,Camera权限和wifi权限", PERMISSIONS, *mPerms)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.size > 0) {  //有权限
                // 获取到权限，作相应处理
                initGaode()
            } else {
                //                    showGPSContacts();
            }
            else -> {
                agreeCourse()
            }
        }
        Log.i("permission", "quan xian fan kui")
        //如果用户取消，permissions可能为null.

    }

    companion object {
        private const val PERMISSIONS = 100//请求码
    }

}
