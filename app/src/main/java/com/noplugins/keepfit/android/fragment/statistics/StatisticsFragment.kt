package com.noplugins.keepfit.android.fragment.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_statistics.*

class StatisticsFragment:BaseFragment() {
    companion object {
        fun newInstance(title: String): StatisticsFragment {
            val fragment = StatisticsFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }

    var newView: View? = null

    private lateinit var fragments: MutableList<Fragment>
    private var transition: FragmentTransaction? = null

    private var userFragment:ToUserFragment?=null
    private var productFragment:ToProductFragment?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_statistics, container, false)
        }
        return newView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragments = ArrayList()
        onClick()
    }


    private fun onClick(){
        userFragment = ToUserFragment.newInstance("用户")
        fragments.add(userFragment!!)
        hideOthersFragment(userFragment!!, true)

        rl_user.setOnClickListener {
            changeBtn(1)
            if (userFragment == null) {
                userFragment = ToUserFragment.newInstance("用户")
                fragments.add(userFragment!!)
                hideOthersFragment(userFragment!!, true)
            } else {
                hideOthersFragment(userFragment!!, false)
            }
        }
        rl_product.setOnClickListener {
            changeBtn(2)
            if (productFragment == null) {
                productFragment = ToProductFragment.newInstance("产品")
                fragments.add(productFragment!!)
                hideOthersFragment(productFragment!!, true)
            } else {
                hideOthersFragment(productFragment!!, false)
            }
        }
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
    /**
     * 动态显示Fragment
     *
     * @param showFragment 要增加的fragment
     * @param add          true：增加fragment；false：切换fragment
     */
    private fun hideOthersFragment(showFragment: Fragment, add: Boolean) {
        transition = this.childFragmentManager.beginTransaction()
        if (add)
            transition!!.add(R.id.main_container_content, showFragment)
        fragments.forEach { fragment ->
            if (showFragment == fragment) {
                transition!!.show(fragment)
            } else {
                transition!!.hide(fragment)
            }
        }
        transition!!.commit()
    }
}