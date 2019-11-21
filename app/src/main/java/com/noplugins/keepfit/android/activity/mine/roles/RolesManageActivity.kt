package com.noplugins.keepfit.android.activity.mine.roles

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.base.BaseActivity
import kotlinx.android.synthetic.main.title_activity.*

/**
 * 1.1版本 角色管理界面
 * @author
 */
class RolesManageActivity : BaseActivity() {
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_roles_manage)
        title_tv.text = getString(R.string.authority_management)
    }


    override fun doBusiness(mContext: Context?) {
        back_btn.setOnClickListener {
            finish()
        }

    }

}
