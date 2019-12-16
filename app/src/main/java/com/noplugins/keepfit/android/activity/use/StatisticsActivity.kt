package com.noplugins.keepfit.android.activity.use

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.fragment.statistics.ToProductFragment
import com.noplugins.keepfit.android.fragment.statistics.ToUserFragment
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.SpUtils
import kotlinx.android.synthetic.main.activity_statistics.*
import kotlinx.android.synthetic.main.title_activity.*

class StatisticsActivity :BaseActivity() {
    private var fragments: MutableList<Fragment>?=null
    private var currentFragment = Fragment()
    private var currentIndex = 0
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {

        setContentView(R.layout.activity_statistics)
        if (fragments == null){
            fragments = ArrayList()
            fragments!!.add(ToUserFragment.newInstance("用户"))
            fragments!!.add(ToProductFragment.newInstance("产品"))
            showFragment()
        }
        title_tv.text = "统计"
    }

    override fun onBackPressed() {
        setResult(SpUtils.getInt(applicationContext, AppConstants.FRAGMENT_SIZE)-1)
        finish()
    }

    override fun doBusiness(mContext: Context?) {
        back_btn.setOnClickListener {
            setResult(SpUtils.getInt(applicationContext, AppConstants.FRAGMENT_SIZE)-1)
            finish()
        }
        rl_user.setOnClickListener {

            changeBtn(1)
            currentIndex = 0
            showFragment()
        }
        rl_product.setOnClickListener {

            changeBtn(2)
            currentIndex = 1
            showFragment()
        }
    }

    private fun showFragment() {

        val transaction = supportFragmentManager.beginTransaction()

        //如果之前没有添加过
        if (!fragments!![currentIndex].isAdded) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.main_container_content, fragments!![currentIndex], "" + currentIndex)  //第三个参数为添加当前的fragment时绑定一个tag

        } else {
            transaction
                    .hide(currentFragment)
                    .show(fragments!![currentIndex])
        }

        currentFragment = fragments!![currentIndex]

        transaction.commit()

    }


    private fun changeBtn(select:Int){
        when(select){
            1 -> {
                tv1.setTextColor(Color.parseColor("#F9CE0F"))
                lin1.visibility = View.VISIBLE
                tv2.setTextColor(Color.parseColor("#181818"))
                lin2.visibility = View.INVISIBLE
            }
            2 -> {
                tv2.setTextColor(Color.parseColor("#F9CE0F"))
                lin2.visibility = View.VISIBLE
                tv1.setTextColor(Color.parseColor("#181818"))
                lin1.visibility = View.INVISIBLE
            }
        }
    }
}