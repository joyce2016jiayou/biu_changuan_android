package com.noplugins.keepfit.android.activity.mine.roles

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.adapter.mine.RoleV11Adapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.util.BaseUtils
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

    }


    override fun doBusiness(mContext: Context?) {
        back_btn.setOnClickListener {
            finish()
        }

        add_btn.setOnClickListener {
            if (BaseUtils.isFastClick()){
                //添加
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

}
