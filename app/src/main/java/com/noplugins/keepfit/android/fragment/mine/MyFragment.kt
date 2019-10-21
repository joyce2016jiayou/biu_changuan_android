package com.noplugins.keepfit.android.fragment.mine

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.activity.*
import com.noplugins.keepfit.android.activity.mine.CgPriceActivity
import com.noplugins.keepfit.android.activity.mine.CostAccountingActivity
import com.noplugins.keepfit.android.activity.mine.TeacherManagerActivity
import com.noplugins.keepfit.android.activity.mine.WalletActivity
import com.noplugins.keepfit.android.adapter.mine.FunctionAdapter
import com.noplugins.keepfit.android.base.BaseFragment
import com.noplugins.keepfit.android.bean.mine.MineFunctionBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import kotlinx.android.synthetic.main.fragment_mine.*

class MyFragment:BaseFragment(){
    companion object {
        fun newInstance(title: String): MyFragment {
            val fragment = MyFragment()
            val args = Bundle()
            args.putString("home_fragment_title", title)
            fragment.arguments = args
            return fragment
        }
    }

    var newView: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (newView == null) {
            newView = inflater.inflate(R.layout.fragment_mine, container, false)
        }
        return newView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setting()
        ll_info.setOnClickListener {
            val intent = Intent(activity, ChangGuandetailActivity::class.java)
            activity!!.startActivityForResult(intent, 1)
        }
    }

    private fun setting() {
        val fuctionBean: MutableList<MineFunctionBean> = ArrayList()

        if (SpUtils.getInt(activity,AppConstants.USER_TYPE) != 3){
            val min1 = MineFunctionBean("钱包", R.drawable.icon_wallet)
            val min2 = MineFunctionBean("权限管理", R.drawable.icon_quanxian)
            val min3 = MineFunctionBean("场馆价格", R.drawable.icon_price)

            val min4 = MineFunctionBean("成本核算", R.drawable.icon_count)
            val min5 = MineFunctionBean("教练管理", R.drawable.icon_trainer)
            fuctionBean.add(min1)
            fuctionBean.add(min2)
            fuctionBean.add(min3)
            fuctionBean.add(min4)
            fuctionBean.add(min5)
        }

        val min6 = MineFunctionBean("问题反馈", R.drawable.iconfeedback)
        val min7 = MineFunctionBean("账号安全", R.drawable.icon_account_number)
        val min8 = MineFunctionBean("关于", R.drawable.icon_about)
        val min9 = MineFunctionBean("客服帮助", R.drawable.icon_customer_service)

        fuctionBean.add(min6)
        fuctionBean.add(min7)
        fuctionBean.add(min8)
        fuctionBean.add(min9)
        val functionAdapter = FunctionAdapter(activity, fuctionBean)
        gv_function.adapter = functionAdapter

        //
        gv_function.setOnItemClickListener { parent, view, position, id ->
            if (BaseUtils.isFastClick()) {
                when (fuctionBean[position].name) {
                    "钱包" -> {
                        val intent = Intent(activity, WalletActivity::class.java)
                         activity!!.startActivityForResult(intent, 1)
                    }
                    "权限管理" -> {
                        val intent = Intent(activity, RoleActivity::class.java)
                         activity!!.startActivityForResult(intent, 1)
                    }
                    "场馆价格" -> {
                        val intent = Intent(activity, CgPriceActivity::class.java)
                         activity!!.startActivityForResult(intent, 1)
                    }
                    "成本核算" -> {
                        val intent = Intent(activity, CostAccountingActivity::class.java)
                         activity!!.startActivityForResult(intent, 1)
                    }
                    "教练管理" -> {
                        val intent = Intent(activity, TeacherManagerActivity::class.java)
                         activity!!.startActivityForResult(intent, 1)
                    }
                    "问题反馈" -> {
                        val intent = Intent(activity, ProductAdviceActivity::class.java)
                        activity!!.startActivityForResult(intent, 1)
                    }
                    "账号安全" -> {
                        val intent = Intent(activity, ZhangHaoSafeActivity::class.java)
                        activity!!.startActivityForResult(intent, 1)
                    }
                    "关于" -> {
                        val intent = Intent(activity, AboutActivity::class.java)
                         activity!!.startActivityForResult(intent, 1)
                    }
                    "客服帮助" -> {
                    }
                }
            }
        }
    }

}
