package com.noplugins.keepfit.android.activity.use


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.google.android.material.tabs.TabLayout
import com.noplugins.keepfit.android.R
//import com.noplugins.keepfit.android.activity.ClassShouquanActivity
import com.noplugins.keepfit.android.adapter.TabItemAdapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.customize.indicatorX
import com.noplugins.keepfit.android.fragment.teacher.JujueFragment
import com.noplugins.keepfit.android.fragment.teacher.YaoqinFragment
import com.noplugins.keepfit.android.fragment.use.team.ApplyFragment
import com.noplugins.keepfit.android.fragment.use.team.HistoryFragment
import com.noplugins.keepfit.android.fragment.use.team.InviteFragment
import com.noplugins.keepfit.android.fragment.use.team.ShelvesFragment
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.SpUtils
import kotlinx.android.synthetic.main.activity_teacher_manager.back_btn
import kotlinx.android.synthetic.main.activity_teacher_manager.title_tv
import kotlinx.android.synthetic.main.activity_teacher_manager.view_pager
import kotlinx.android.synthetic.main.activity_team_class_manager.*
import kotlinx.android.synthetic.main.discovers_fragment.*
import kotlinx.android.synthetic.main.title_activity.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.util.ArrayList

class TeamClassManagerActivity : BaseActivity(){

    private val mFragments = ArrayList<Fragment>()
    private val titleList = ArrayList<String>()
    //声明AMapLocationClient类对象
    internal var mLocationClient: AMapLocationClient? = null
    //声明AMapLocationClientOption对象
    var mLocationOption: AMapLocationClientOption? = null

    private val mPerms = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentView(R.layout.activity_team_class_manager)
        title_tv.text = "团课管理"
        add_btn.visibility = View.VISIBLE
        initFragment()
        requestPermission()
    }

    override fun doBusiness(mContext: Context?) {
        add_btn.setOnClickListener {
//            val intent = Intent(this, TeacherSelectActivity::class.java)
//            startActivity(intent)
        }
        back_btn.setOnClickListener {
            setResult(SpUtils.getInt(applicationContext,AppConstants.FRAGMENT_SIZE)-1)
            finish()
        }
    }

    private fun initFragment() {
        mFragments.add(ShelvesFragment.newInstance("已上架"))
        mFragments.add(ApplyFragment.newInstance("申请中"))
        mFragments.add(InviteFragment.newInstance("邀请中"))
        mFragments.add(HistoryFragment.newInstance("历史"))

        titleList.add("已上架")
        titleList.add("申请中")
        titleList.add("邀请中")
        titleList.add("历史")

        titleList.forEach {
            enhance_tab_layout.addTab(it)
        }
        enhance_tab_layout
        val myAdapter = TabItemAdapter(supportFragmentManager,titleList, mFragments)// 初始化adapter
        view_pager.adapter = myAdapter // 设置adapter
        view_pager.currentItem = 0
//        setTabTextColorAndImageView(0)// 更改text的颜色还有图片
        view_pager.offscreenPageLimit = 2
        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(enhance_tab_layout.tabLayout))
        enhance_tab_layout.setupWithViewPager(view_pager)
    }

    @AfterPermissionGranted(PERMISSIONS)
    private fun requestPermission() {
        if (EasyPermissions.hasPermissions(this, *mPerms)) {
            //Log.d(TAG, "onClick: 获取读写内存权限,Camera权限和wifi权限");
//            initGaode()

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
//                initGaode()
            } else {
                //                    showGPSContacts();
            }
            else -> {
//                initFragment()
            }
        }
        Log.i("permission", "quan xian fan kui")
        //如果用户取消，permissions可能为null.

    }

    companion object {

        private const val PERMISSIONS = 100//请求码
    }

    override fun onBackPressed() {
        setResult(SpUtils.getInt(applicationContext,AppConstants.FRAGMENT_SIZE)-1)
        finish()
    }

}