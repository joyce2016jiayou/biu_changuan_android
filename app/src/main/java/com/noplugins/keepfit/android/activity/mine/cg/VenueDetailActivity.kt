package com.noplugins.keepfit.android.activity.mine.cg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.huantansheng.easyphotos.EasyPhotos
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.adapter.mine.cg.VenueLayout2Adapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.bean.DictionaryeBean
import com.noplugins.keepfit.android.entity.InformationEntity
import com.noplugins.keepfit.android.resource.ValueResources
import com.noplugins.keepfit.android.util.GlideEngine
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.jiugongge.CCRSortableNinePhotoLayout
import kotlinx.android.synthetic.main.activity_venue_detail.*
import kotlinx.android.synthetic.main.venue_item_1.*
import kotlinx.android.synthetic.main.venue_item_2.*
import kotlinx.android.synthetic.main.venue_item_6.*
import org.w3c.dom.Text
import java.io.File
import java.util.HashMap

class VenueDetailActivity : BaseActivity(),CCRSortableNinePhotoLayout.Delegate  {

    private var nowSelect = 1
    private lateinit var docList:MutableList<DictionaryeBean>
    //2设备
    private var layout2Adapter:VenueLayout2Adapter?=null
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentView(R.layout.activity_venue_detail)
        docList = ArrayList()

        requestDoc()
        //todo 请求到数据之后再进行 addView的操作，否则页面无数据
        changeLayout1(0)

        //获取场馆设施 doc

    }

    private fun requestDoc() {
        val params = HashMap<String, Any>()
        params["object"] = 1
        subscription = Network.getInstance("获取设施字典", this)
                .get_types(params,
                        ProgressSubscriber("获取设施字典",
                                object : SubscriberOnNextListener<Bean<List<DictionaryeBean>>> {
                                    override fun onNext(result: Bean<List<DictionaryeBean>>) {
                                        docList.addAll(result.data)
                                    }

                                    override fun onError(error: String) {

                                    }
                                }, this, false))
    }

    override fun doBusiness(mContext: Context?) {
//        val layoutInflater = LayoutInflater.from(this)
        rb_base_info.setOnClickListener {
            if (nowSelect != 1) {
                nowSelect = 1
                changeLayout1(1)
            }

        }
        rb_venue_facilities.setOnClickListener {
            if (nowSelect != 2) {
                changeLayout2()
            }
        }
        rb_business_info.setOnClickListener {
            if (nowSelect != 3) {
                changeLayout3()
            }
        }
        rb_account_info.setOnClickListener {
            if (nowSelect != 4) {
                changeLayout4()
            }
        }
        rb_album_manager.setOnClickListener {
            if (nowSelect != 5) {
                changeLayout5()
            }
        }
        rb_coach_notes.setOnClickListener {
            if (nowSelect != 6) {
                changeLayout6()
            }
        }

    }

    //layout_1
    private fun changeLayout1(type: Int) {
        if (type == 1) {
            rec_right.removeViewAt(0)
        }
        val view = layoutInflater.inflate(R.layout.venue_item_1, null, false)
        rec_right.addView(view, 0)
        val save1 = view.findViewById<TextView>(R.id.tv_save_1)
        save1.setOnClickListener {

        }

    }

    //layout_2 场馆设施
    @SuppressLint("WrongConstant")
    private fun changeLayout2() {
        nowSelect = 2

        if (layout2Adapter==null){
            layout2Adapter = VenueLayout2Adapter(docList)
            layout2Adapter!!.setOnItemChildClickListener { adapter, view, position ->
                //checkbox 逻辑处理
            }
        }
        rec_right.removeViewAt(0)
        val view = layoutInflater.inflate(R.layout.venue_item_2, null, false)
        rec_right.addView(view, 0)
        val rvFacilities = view.findViewById<RecyclerView>(R.id.rv_venue_facilities)
        val save2 = view.findViewById<TextView>(R.id.tv_save_2)
        rvFacilities.layoutManager = GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false)
        rvFacilities.adapter = layout2Adapter

        save2.setOnClickListener {
            Toast.makeText(applicationContext,"sava 2 info",Toast.LENGTH_SHORT).show()
        }

    }

    //layout_3
    private fun changeLayout3() {
        nowSelect = 3
        rec_right.removeViewAt(0)
        val view = layoutInflater.inflate(R.layout.venue_item_3, null, false)
        rec_right.addView(view, 0)

    }

    //layout_4
    private fun changeLayout4() {
        nowSelect = 4
        rec_right.removeViewAt(0)
        val view = layoutInflater.inflate(R.layout.venue_item_4, null, false)
        rec_right.addView(view, 0)

    }

    //layout_5
    private var ivLogo:ImageView ?= null
    private var ivLogoPath = ""
    private var photos: CCRSortableNinePhotoLayout ? = null
    private var tvPhotoNum: TextView ? = null

    //当前列表上的图片 新增了图片或删除了图片后 该列表会随之改变
    private var strings:MutableList<String> = ArrayList()
    /*
        当前列表上的图片(需要提交的对象)
        初始化为该场馆当前的所有场馆介绍图。
        当删除(原本存在)之前的图片时:该list会改变
        当删除新选择的图片时:该list不会改变
        图片上传后该list需要增加
     */
    private var upList:MutableList<InformationEntity.GymPicBean> = ArrayList()

    /*
        当前列表为 人为选择的list
        已选择(未上架)的图片会增加该list
        删除已选择(未上架)的图片会减少该list
        当该list!=0 时，必然存在需要上传的图片
     */
    private var selectPhotos:MutableList<String> = ArrayList()

    private fun changeLayout5() {
        nowSelect = 5
        rec_right.removeViewAt(0)
        val view = layoutInflater.inflate(R.layout.venue_item_5, null, false)
        rec_right.addView(view, 0)

        ivLogo = view.findViewById<ImageView>(R.id.logo_image)
        photos = view.findViewById<CCRSortableNinePhotoLayout>(R.id.snpl_moment_add_photos)
        tvPhotoNum = view.findViewById<TextView>(R.id.tv_pic_num)

        photos!!.setData(strings)
        photos!!.setDelegate(this)
        ValueResources.select_iamges_size = strings.size//已选择的数量
        tvPhotoNum!!.text = "(${ValueResources.select_iamges_size}/9)"

        //logo
        ivLogo!!.setOnClickListener {
            EasyPhotos.createAlbum(this, true, GlideEngine.getInstance())
                    .setFileProviderAuthority("com.noplugins.keepfit.android.fileprovider")
                    .setPuzzleMenu(false)
                    .setCount(1)
                    .setOriginalMenu(false, true, null)
                    .start(102)
        }

    }

    //layout_6 教练须知
    private fun changeLayout6() {
        nowSelect = 6
        rec_right.removeViewAt(0)
        val view = layoutInflater.inflate(R.layout.venue_item_6, null, false)
        rec_right.addView(view, 0)
        val save6 = view.findViewById<TextView>(R.id.tv_save_6)
        val etCoachNotes = view.findViewById<TextInputEditText>(R.id.et_coach_notes)
        save6.setOnClickListener {
            //保存教练须知
            if (etCoachNotes.text.toString().isEmpty()) {
                return@setOnClickListener
            }
            Toast.makeText(applicationContext,"sava 6 info",Toast.LENGTH_SHORT).show()
            //todo 调用保存接口
        }


    }


    private var maxNum = 0
    override fun onClickAddNinePhotoItem(sortableNinePhotoLayout: CCRSortableNinePhotoLayout?, view: View?, position: Int, models: java.util.ArrayList<String>?) {
        //设置最多只能上传9张图片
        if (ValueResources.select_iamges_size >= 9) {
            Toast.makeText(this, "只能上传9张图片哦～", Toast.LENGTH_SHORT).show()
        } else if (ValueResources.select_iamges_size < 9) {
            //选择新的图片
            maxNum = 9 - ValueResources.select_iamges_size
            EasyPhotos.createAlbum(this, true, GlideEngine.getInstance())
                    .setFileProviderAuthority("com.noplugins.keepfit.android.fileprovider")
                    .setPuzzleMenu(false)
                    .setCount(maxNum)
                    .setOriginalMenu(false, true, null)
                    .start(101)
        }
    }

    override fun onClickDeleteNinePhotoItem(sortableNinePhotoLayout: CCRSortableNinePhotoLayout?, view: View?, position: Int, model: String?, models: java.util.ArrayList<String>?) {

        photos!!.removeItem(position)//控件中删除该图片
        if (position < upList.size && upList.size != 0) {
            upList.removeAt(position)
        } else {
            selectPhotos.removeAt(position - upList.size)
        }

        ValueResources.select_iamges_size = ValueResources.select_iamges_size - 1
        tvPhotoNum!!.text ="(${ValueResources.select_iamges_size}/9)"
    }

    override fun onClickNinePhotoItem(sortableNinePhotoLayout: CCRSortableNinePhotoLayout?, view: View?, position: Int, model: String?, models: java.util.ArrayList<String>?) {
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK == resultCode) {
            if (nowSelect == 5){
                if (requestCode == 101){
                    //不支持AndroidQ
                    val resultPaths = data!!.getStringArrayListExtra(EasyPhotos.RESULT_PATHS)
                    strings.addAll(resultPaths)
                    selectPhotos.addAll(resultPaths)
                    photos!!.setData(strings)//设置九宫格
                    ValueResources.select_iamges_size = strings.size
                    tvPhotoNum!!.text ="(${ValueResources.select_iamges_size}/9)"

                }
                else{
                    val resultPaths = data!!.getStringArrayListExtra(EasyPhotos.RESULT_PATHS)
                    if (resultPaths.size > 0) {
                        ivLogoPath = resultPaths[0]
                        val ivLogoFile = File(ivLogoPath)
                        Glide.with(applicationContext).load(ivLogoFile).into(ivLogo)
//                        delete_icon_btn.setVisibility(View.VISIBLE)
                    }
                }
            }
        }
    }

}
