package com.noplugins.keepfit.android.activity.mine

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.adapter.CgTeacherSelectAdapter
import com.noplugins.keepfit.android.adapter.PopUpAdapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.BindingCgBean
import com.noplugins.keepfit.android.bean.CgBindingBean
import com.noplugins.keepfit.android.bean.TeacherBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.pop.SpinnerPopWindow
import com.umeng.socialize.utils.DeviceConfigInternal.context
import kotlinx.android.synthetic.main.activity_teacher_select.*
import java.util.HashMap

class TeacherSelectActivity : BaseActivity() {

    private var page = 1
    var yaerSelect = -1
    var sexSelect = -1

    lateinit var adapter: CgTeacherSelectAdapter
    lateinit var layoutManager:LinearLayoutManager
    private var data: MutableList<TeacherBean> = ArrayList()
    private var submitList: MutableList<CgBindingBean.TeacherNumBean> = ArrayList()
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentView(R.layout.activity_teacher_select)
        init()
        initAdapter()
        agreeCourse()
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.setOnClickListener {
            finish()
        }
        queren_btn.setOnClickListener {
            binding()
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
                    page = 1
                    agreeCourse()
                    popWindow2!!.dismiss()
                })
        teacher_year.setOnClickListener {
            showPopwindow(popWindow2!!,teacher_year)

        }

        val listSex = resources.getStringArray(R.array.private_sex_types).toMutableList()
        popWindow3 = SpinnerPopWindow(context,
                listSex,
                PopUpAdapter.OnItemClickListener { _, _, position ->
                    tv_sex_select.text = listSex[position]
                    popWindow3!!.dismiss()
                    sexSelect = if (position == listSex.size -1){
                        -1
                    } else {
                        position
                    }
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
                    } else {
                        Log.d("item","取消了")
                        for (i in 0 until submitList.size){
                            if (submitList[i].num == data[position].teacherNum){
                                submitList.removeAt(i)
                                return@setOnItemChildClickListener
                            }
                        }

                    }
                }
            }
        }

        refresh_layout.setEnableRefresh(false)
        refresh_layout.setEnableLoadMore(false)
//        refresh_layout.setOnRefreshListener {
//            //下拉刷新
//            refresh_layout.finishRefresh(2000/*,false*/)
//        }
        refresh_layout.setOnLoadMoreListener {
            //上拉加载

            refresh_layout.finishLoadMore(2000/*,false*/)
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


    private fun binding(){
//        val params = HashMap<String, Any>()
        val cgBinding = CgBindingBean()
        cgBinding.areaNum = SpUtils.getString(applicationContext,AppConstants.CHANGGUAN_NUM)
        cgBinding.teacherNum = submitList
        val subscription = Network.getInstance("场馆绑定教练", this)
                .areaInviteTeacher(
                        cgBinding,
                        ProgressSubscriber("场馆绑定教练", object : SubscriberOnNextListener<Bean<String>> {
                            override fun onNext(result: Bean<String>) {
                                Toast.makeText(applicationContext,result.message,Toast.LENGTH_SHORT).show()
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext,error,Toast.LENGTH_SHORT).show()
                            }
                        }, this, false)
                )
    }
}
