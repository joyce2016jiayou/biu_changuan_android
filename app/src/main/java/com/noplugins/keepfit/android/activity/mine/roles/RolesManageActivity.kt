package com.noplugins.keepfit.android.activity.mine.roles

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
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
import com.noplugins.keepfit.android.global.PublicPopControl
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.data.StringsHelper
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.pop.base.CenterPopupView
import com.noplugins.keepfit.android.util.ui.pop.inteface.ViewCallBack
import com.noplugins.keepfit.android.util.ui.toast.SuperCustomToast
import kotlinx.android.synthetic.main.activity_roles_manage.*
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
        setContentLayout(R.layout.activity_roles_manage)
        isShowTitle(true)
        setTitleView(R.string.authority_management,R.drawable.icon_back,R.drawable.icon_add,false,0)
//        title_tv.text = getString(R.string.authority_management)
//        add_btn.visibility = View.VISIBLE
        initAdapter()
        getBindingUserList()
    }


    override fun doBusiness(mContext: Context?) {

        title_left_button_onclick_listen {
            setResult(SpUtils.getInt(applicationContext,AppConstants.FRAGMENT_SIZE)-1)
            finish()
        }

        title_right_button_onclick_listen {
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
            deletePop(position)
        }
    }

    override fun onBackPressed() {
        setResult(SpUtils.getInt(applicationContext,AppConstants.FRAGMENT_SIZE)-1)
        finish()
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

                            val name = view.findViewById<EditText>(R.id.ms_room_type)
                            val phone = view.findViewById<EditText>(R.id.ms_room_name)
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
                                        if (BaseUtils.isFastClick()){
                                            addRoles(name.text.toString(),phone.text.toString(),role.text.toString(),popup)
                                        }
                                    }

                        })).show()
    }


    private fun deletePop(position: Int){

        PublicPopControl.alert_dialog_center(this) { view, popup ->
            val content = view.findViewById<TextView>(R.id.pop_content)
            val title = view.findViewById<TextView>(R.id.pop_title)
            content.setText(R.string.label_role_del)
            title.setText("确认删除")
            view.findViewById<LinearLayout>(R.id.cancel_btn)
                    .setOnClickListener {
                        popup.dismiss()
                    }
            view.findViewById<LinearLayout>(R.id.sure_btn)
                    .setOnClickListener {
                        popup.dismiss()
                        deleteRoles(popup,position)}
        }
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
    private fun deleteRoles(popup: BasePopupView,position:Int){
        val deleteData:MutableList<RoleBean.RoleEntity> = ArrayList()
        data[position].type = 0
        data[position].gymAreaNum = SpUtils.getString(applicationContext,AppConstants.CHANGGUAN_NUM)
        data[position].userName = data[position].name
        deleteData.add(data[position])
        val submit = RoleBean()
        submit.userList = deleteData

        subscription = Network.getInstance("删除用户", applicationContext)
                .binding_role(submit, ProgressSubscriber("删除用户",
                        object : SubscriberOnNextListener<Bean<Any>> {
                            override fun onNext(roleBeanBean: Bean<Any>) {
                                data.removeAt(position)
                                adapter!!.notifyItemRemoved(position)//刷新被删除的地方
                                adapter!!.notifyItemRangeChanged(position, adapter!!.itemCount) //刷新被删除数据，以及其后面的数据
                                Toast.makeText(applicationContext,roleBeanBean.message,Toast.LENGTH_SHORT).show()
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
                            }
                        }, this, true))
        popup.dismiss()
    }

    /**
     * 新增用户操作
     */

    private fun addRoles(name: String, phone: String, roles: String, popup: BasePopupView){


        if (TextUtils.isEmpty(name)) {
            Toast.makeText(applicationContext, "姓名不能为空！", Toast.LENGTH_SHORT).show()
            return
        }
        if (!StringsHelper.isMobileOne(phone)){
            Toast.makeText(applicationContext, "手机号格式不正确！", Toast.LENGTH_SHORT).show()
            return
        }

        val role:Int = when(roles){
            "经理"-> {
                2
            }
            "前台"-> {
                3
            }
            else -> {
                Toast.makeText(applicationContext, "职位不能为空！", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val addData:MutableList<RoleBean.RoleEntity> = ArrayList()
        val roleBean = RoleBean.RoleEntity()
        roleBean.userName = name
        roleBean.name = name
        roleBean.phone = phone
        roleBean.userType = role
        roleBean.type = 1
        roleBean.gymAreaNum = SpUtils.getString(applicationContext,AppConstants.CHANGGUAN_NUM)

        addData.add(roleBean)
//        adapter!!.notifyDataSetChanged()

        val submit = RoleBean()
        submit.userList = addData

        subscription = Network.getInstance("新增用户", applicationContext)
                .binding_role(submit, ProgressSubscriber("新增用户",
                        object : SubscriberOnNextListener<Bean<Any>> {
                            override fun onNext(roleBeanBean: Bean<Any>) {
                                data.add(roleBean)
                                adapter!!.notifyDataSetChanged()
                            }

                            override fun onError(error: String) {
                                Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
                            }
                        }, this, true))
        popup.dismiss()

    }


}
