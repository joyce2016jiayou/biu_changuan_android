package com.noplugins.keepfit.android.activity.mine.roles

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.adapter.mine.RoleV11Adapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.entity.RoleBean
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.pop.base.CenterPopupView
import com.noplugins.keepfit.android.util.ui.pop.inteface.ViewCallBack
import kotlinx.android.synthetic.main.activity_roles_manage.*
import kotlinx.android.synthetic.main.title_activity.*
import lib.demo.spinner.MaterialSpinner
import java.util.HashMap

/**
 * 1.1版本 角色管理界面
 * @author
 */
class RolesManageActivity : BaseActivity() {
    var adapter:RoleV11Adapter ?= null
    var data:MutableList<RoleBean.RoleEntity> = ArrayList()
    override fun initBundle(parms: Bundle?) {

    }

    override fun initView() {
        setContentView(R.layout.activity_roles_manage)
        title_tv.text = getString(R.string.authority_management)
        add_btn.visibility = View.VISIBLE
        initAdapter()

        getBindingUserList()

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


    /**
     * 添加的popWindow
     */
    private fun addPop() {
        XPopup.Builder(this)
                .autoOpenSoftInput(false)
                .autoFocusEditText(false)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .asCustom(CenterPopupView(this,R.layout.dialog_to_roles,
                        ViewCallBack { view, popup ->

                            val name = view.findViewById<EditText>(R.id.et_username)
                            val phone = view.findViewById<EditText>(R.id.et_phone)
                            val role = view.findViewById<MaterialSpinner>(R.id.et_role)

                            val typeArrays =
                                    resources.getStringArray(R.array.zhiwei_types).toList()
                            role.setItems(typeArrays)
                            role.selectedIndex = 0
                            role.setOnItemSelectedListener { _, position, _, _ ->
                                role.text = typeArrays[position]
                            }
                            role.setOnNothingSelectedListener { spinner -> spinner.selectedIndex }


                            view.findViewById<TextView>(R.id.tv_cancel)
                                    .setOnClickListener {
                                        popup.dismiss()
                                    }

                            view.findViewById<TextView>(R.id.tv_add)
                                    .setOnClickListener {
                                        addRoles(name.text.toString(),phone.text.toString(),role.text.toString(),popup)
                                    }

                        })).show()
    }

    /**
     * 获取当前已绑定的用户
     */
    private fun getBindingUserList() {
        data.clear()
        val params = HashMap<String, Any>()
        params["area_num"] = SpUtils.getString(applicationContext, AppConstants.CHANGGUAN_NUM)

        subscription = Network.getInstance("获取已绑定用户", applicationContext)
                .findBindingRoles(params, ProgressSubscriber("获取已绑定用户",
                        object : SubscriberOnNextListener<Bean<RoleBean>> {
                            override fun onNext(roleBeanBean: Bean<RoleBean>) {
                                if (roleBeanBean.data.userList != null) {
                                    data.addAll(roleBeanBean.data.userList)
                                    data.removeAt(0)
                                    tv_boos_name.text = roleBeanBean.data.userList[0].name
                                    tv_boos_phone.text = roleBeanBean.data.userList[0].phone
                                    adapter!!.notifyDataSetChanged()
                                }
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
                            }
                        }, this, true))

    }

    /**
     * 删除用户操作
     */

    /**
     * 新增用户操作
     */

    private fun addRoles(name: String, phone: String, roles: String, popup: BasePopupView){


        if (TextUtils.isEmpty(name)) {
            Toast.makeText(applicationContext, "姓名不能为空！", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(applicationContext, "手机号不能为空！", Toast.LENGTH_SHORT).show()
            return
        }
        val role:Int = when(roles){
            "经理"-> {
                1
            }
            "前台"-> {
                2
            }
            else -> {
                Toast.makeText(applicationContext, "职位不能为空！", Toast.LENGTH_SHORT).show()
                return
            }
        }
        val roleBean = RoleBean.RoleEntity()
        roleBean.userName = name
        roleBean.phone = phone
        roleBean.userType = role
        roleBean.type = 1
        roleBean.gymAreaNum = SpUtils.getString(applicationContext,AppConstants.CHANGGUAN_NUM)


        popup.dismiss()

    }


}
