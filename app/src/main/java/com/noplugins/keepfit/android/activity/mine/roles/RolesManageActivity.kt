package com.noplugins.keepfit.android.activity.mine.roles

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.adapter.mine.RoleV11Adapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.ui.pop.base.CenterPopupView
import com.noplugins.keepfit.android.util.ui.pop.inteface.ViewCallBack
import kotlinx.android.synthetic.main.activity_roles_manage.*
import kotlinx.android.synthetic.main.title_activity.*

/**
 * 1.1版本 角色管理界面
 * @author
 */
class RolesManageActivity : BaseActivity() {
    var adapter:RoleV11Adapter ?= null
    var data:MutableList<String> = ArrayList()
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentView(R.layout.activity_roles_manage)
        title_tv.text = getString(R.string.authority_management)
        add_btn.visibility = View.GONE
        initAdapter()

    }


    override fun doBusiness(mContext: Context?) {
        back_btn.setOnClickListener {
            finish()
        }

        add_btn.setOnClickListener {
            if (BaseUtils.isFastClick()){
                //添加
                addPop()
            }
        }

    }

    private fun initAdapter(){
        adapter = RoleV11Adapter(data)
        rv_roles.layoutManager = LinearLayoutManager(this)
        rv_roles.adapter = adapter
        adapter!!.setOnItemChildClickListener { adapter, view, position ->

        }
    }



    private fun addPop() {
        XPopup.Builder(this)
                .autoOpenSoftInput(true)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .asCustom(CenterPopupView(this,R.layout.dialog_to_roles,
                        ViewCallBack { view, popup ->
                            view.findViewById<TextView>(R.id.tv_cancel)
                                    .setOnClickListener {
                                        popup.dismiss()
                                    }

                            view.findViewById<TextView>(R.id.tv_add)
                                    .setOnClickListener {
                                        popup.dismiss()
                                    }
                        })).show()
    }


}
