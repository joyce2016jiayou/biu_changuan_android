package com.noplugins.keepfit.android.activity.use


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import cn.jpush.android.cache.Sp
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.noplugins.keepfit.android.R
//import com.noplugins.keepfit.android.activity.ClassShouquanActivity
import com.noplugins.keepfit.android.adapter.TabItemAdapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.fragment.teacher.BindingFragment
import com.noplugins.keepfit.android.fragment.teacher.JujueFragment
import com.noplugins.keepfit.android.fragment.teacher.SqAndYaoqinFragment
import com.noplugins.keepfit.android.fragment.teacher.YaoqinFragment
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.SpUtils
import kotlinx.android.synthetic.main.activity_teacher_manager.*
import kotlinx.android.synthetic.main.activity_teacher_manager.back_btn
import kotlinx.android.synthetic.main.activity_teacher_manager.title_tv
import kotlinx.android.synthetic.main.title_activity.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.util.ArrayList

class TeamClassManagerActivity : BaseActivity(){

//    override fun onLocationChanged(amapLocation: AMapLocation?) {
//        if (amapLocation != null) {
//            if (amapLocation.errorCode == 0) {
//                //定位成功回调信息，设置相关消息
//                amapLocation.locationType//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                val latitude = amapLocation.latitude//获取纬度
//                val longitude = amapLocation.longitude//获取经度
//                //                        latLonPoint = new LatLonPoint(currentLat, currentLon);  // latlng形式的
//                /*currentLatLng = new LatLng(currentLat, currentLon);*/   //latlng形式的
//                SpUtils.putString(applicationContext, AppConstants.LON, "" + longitude)
//                SpUtils.putString(applicationContext, AppConstants.LAT, "" + latitude)
//
//            } else {
//                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
//                Log.e(
//                        "AmapError", "location Error, ErrCode:"
//                        + amapLocation.errorCode + ", errInfo:"
//                        + amapLocation.errorInfo
//                )
//            }
//
//            initFragment()
//        }
//    }

    private val mFragments = ArrayList<Fragment>()
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
        rbOnClick()
        tv_complete.setOnClickListener {
//            val intent = Intent(this, TeacherSelectActivity::class.java)
//            startActivity(intent)
        }
        back_btn.setOnClickListener {
            setResult(SpUtils.getInt(applicationContext,AppConstants.FRAGMENT_SIZE)-1)
            finish()
        }
    }

    private fun rbOnClick() {
//        rb_1.setOnClickListener {
//            Log.d("单选", "点击了1")
//            view_pager.currentItem = 0
//        }
//        rb_2.setOnClickListener {
//            Log.d("单选", "点击了2")
//            view_pager.currentItem = 1
//        }
//        rb_3.setOnClickListener {
//            Log.d("单选", "点击了3")
//            view_pager.currentItem = 2
//        }
//        rb_4.setOnClickListener {
//            Log.d("单选", "点击了3")
//            view_pager.currentItem = 3
//        }
    }

    private fun initFragment() {
        mFragments.add(BindingFragment.newInstance("已绑定"))
        mFragments.add(SqAndYaoqinFragment.newInstance("申请中"))
        mFragments.add(YaoqinFragment.newInstance("邀请中"))
        mFragments.add(JujueFragment.newInstance("已拒绝"))

        val myAdapter = TabItemAdapter(supportFragmentManager, mFragments)// 初始化adapter
        view_pager.adapter = myAdapter // 设置adapter
        view_pager.currentItem = 0
        setTabTextColorAndImageView(0)// 更改text的颜色还有图片
        view_pager.offscreenPageLimit = 2
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPs: Int) {}

            override fun onPageSelected(position: Int) {
                setTabTextColorAndImageView(position)// 更改text的颜色还有图片
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun setTabTextColorAndImageView(position: Int) {
        when (position) {
//            0 -> rb_1.isChecked = true
//            1 -> rb_2.isChecked = true
//            2 -> rb_3.isChecked = true
//            3 -> rb_4.isChecked = true
        }
    }

//    private fun initGaode() {
//        //初始化定位
//        mLocationClient = AMapLocationClient(this)
//        //设置定位回调监听
//        mLocationClient!!.setLocationListener(this)
//        //初始化AMapLocationClientOption对象
//        mLocationOption = AMapLocationClientOption()
//
//        mLocationOption!!.isOnceLocation = true
//        //        mLocationOption.setOnceLocationLatest(true);
//        // 同时使用网络定位和GPS定位,优先返回最高精度的定位结果,以及对应的地址描述信息
//        mLocationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
//        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。默认连续定位 切最低时间间隔为1000ms
//        //        mLocationOption.setInterval(3500);
//        //给定位客户端对象设置定位参数
//        mLocationClient!!.setLocationOption(mLocationOption)
//        //启动定位
//        mLocationClient!!.startLocation()
//    }

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