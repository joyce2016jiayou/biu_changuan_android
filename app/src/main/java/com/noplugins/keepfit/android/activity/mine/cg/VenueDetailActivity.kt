package com.noplugins.keepfit.android.activity.mine.cg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.textfield.TextInputEditText
import com.huantansheng.easyphotos.EasyPhotos
import com.noplugins.keepfit.android.R
import com.noplugins.keepfit.android.adapter.mine.cg.VenueLayout2Adapter
import com.noplugins.keepfit.android.base.BaseActivity
import com.noplugins.keepfit.android.base.MyApplication
import com.noplugins.keepfit.android.bean.ChangguanBean
import com.noplugins.keepfit.android.bean.DictionaryeBean
import com.noplugins.keepfit.android.entity.InformationEntity
import com.noplugins.keepfit.android.entity.QiNiuToken
import com.noplugins.keepfit.android.global.AppConstants
import com.noplugins.keepfit.android.util.BaseUtils
import com.noplugins.keepfit.android.util.GlideEngine
import com.noplugins.keepfit.android.util.GlideRoundTransform
import com.noplugins.keepfit.android.util.SpUtils
import com.noplugins.keepfit.android.util.net.Network
import com.noplugins.keepfit.android.util.net.entity.Bean
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener
import com.noplugins.keepfit.android.util.ui.ProgressUtil
import com.noplugins.keepfit.android.util.ui.jiugongge.CCRSortableNinePhotoLayout
import com.qiniu.android.storage.UploadManager
import com.qiniu.android.storage.UploadOptions
import com.ycuwq.datepicker.time.HourAndMinDialogFragment
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_venue_detail.*
import kotlinx.android.synthetic.main.title_activity_yellow.*
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class VenueDetailActivity : BaseActivity(), CCRSortableNinePhotoLayout.Delegate {

    private var nowSelect = 1
    private lateinit var docList: MutableList<DictionaryeBean>
    //2设备
    private var layout2Adapter: VenueLayout2Adapter? = null

    private var cgBean: ChangguanBean? = null
    private var infoBean: InformationEntity? = null


    /**
     * 七牛云
     */
    //指定upToken, 强烈建议从服务端提供get请求获取
    private var uptoken = "xxxxxxxxx:xxxxxxx:xxxxxxxxxx"
    private var sdf: SimpleDateFormat? = null
    private var qiniu_key: String? = null
    private var uploadManager: UploadManager? = null
    override fun initBundle(parms: Bundle?) {
    }

    override fun initView() {
        setContentLayout(R.layout.activity_venue_detail)
        isShowTitle(true)
        setTitleView(R.string.vunue_info)


        /**七牛云**/
        uploadManager = MyApplication.uploadManager
        sdf = SimpleDateFormat("yyyyMMddHHmmss")
        qiniu_key = "icon_" + sdf!!.format(Date())
        docList = ArrayList()
//        title_tv.text = getString(R.string.vunue_info)
        getToken()
        requestDoc()
        requestData()
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

    private fun requestData() {
        val params = HashMap<String, Any>()
        params["areaNum"] = SpUtils.getString(applicationContext, AppConstants.CHANGGUAN_NUM)
        subscription = Network.getInstance("场馆信息", this)
                .myArea(params, ProgressSubscriber("场馆信息",
                        object : SubscriberOnNextListener<Bean<ChangguanBean>> {
                            override fun onNext(changguanBeanBean: Bean<ChangguanBean>) {
//                                setting(changguanBeanBean.data)
                                cgBean = changguanBeanBean.data
                                infoBean = InformationEntity()
                                infoBean?.let {
                                    it.area_num = cgBean!!.area.areaNum
                                    it.area_name = cgBean!!.area.areaName
                                    it.type = cgBean!!.area.type
                                    it.area = cgBean!!.area.area
                                    it.business_start = cgBean!!.area.businessStart
                                    it.business_end = cgBean!!.area.businessEnd
                                    it.address = cgBean!!.area.address
                                    it.phone = cgBean!!.area.phone
                                    it.email = cgBean!!.area.email
                                    it.facility = cgBean!!.area.facility
                                    it.legal_person = cgBean!!.area.legalPerson
                                    it.card_num = cgBean!!.area.cardNum
                                    it.company_name = cgBean!!.area.companyName
                                    it.company_code = cgBean!!.area.companyCode
                                    it.coach_notify = if (cgBean!!.area.coachNotify.isEmpty()) "" else cgBean!!.area.coachNotify
//                                    it.bankName = cgBean!!.area.bankName
//                                    it.bank_card_num = cgBean!!.area.bankCardNum

                                    it.city = cgBean!!.area.city
                                    it.district = cgBean!!.area.district
                                    it.province = cgBean!!.area.province

                                    it.latitude = cgBean!!.area.latitude
                                    it.longitude = cgBean!!.area.longitude

                                    val list = ArrayList<InformationEntity.GymPicBean>()
                                    cgBean!!.pic.forEach { bean->
                                        val info = InformationEntity.GymPicBean()
                                        info.url = bean.url
                                        info.qiniu_key = bean.qiniuKey
                                        info.order_num = bean.orderNum
                                        list.add(info)
                                    }

                                    it.gymPic = list
                                }

                                if (nowSelect == 5){
                                    changeLayout5()
                                } else {
                                    changeLayout1(0)
                                }
                            }

                            override fun onError(error: String) {

                            }
                        }, this, false))
    }

    override fun onBackPressed() {
        back()
    }

    private fun back() {
        setResult(3)
        finish()
    }

    override fun doBusiness(mContext: Context?) {
//        val layoutInflater = LayoutInflater.from(this)
//        back_btn.setOnClickListener {
//            back()
//        }
        title_left_button_onclick_listen {
            back()
        }
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
        val tvSelectTime = view.findViewById<TextView>(R.id.tv_business_hours)

        val cgName = view.findViewById<TextView>(R.id.tv_cg_name)
        val cgType = view.findViewById<TextView>(R.id.tv_cg_type)
        val cgArea = view.findViewById<TextView>(R.id.tv_cg_area)
        val cgAddress = view.findViewById<TextView>(R.id.tv_cg_address)
        //可修改的
        val cgHours = view.findViewById<TextView>(R.id.tv_business_hours)
        val cgPhone = view.findViewById<TextView>(R.id.tv_contact_phone)
        val cgEmail = view.findViewById<TextView>(R.id.tv_contact_email)

        val typeArrays =
                resources.getStringArray(R.array.identify_types).toList()
        cgName.text = infoBean!!.area_name
        cgType.text = typeArrays[infoBean!!.type - 1]
        cgArea.text = "${infoBean!!.area} m²"
        cgAddress.text = infoBean!!.address



        cgHours.text = "${BaseUtils.strSubEnd3(infoBean!!.business_start)}-${BaseUtils.strSubEnd3(infoBean!!.business_end)}"

        cgPhone.text = infoBean!!.phone
        cgEmail.text = infoBean!!.email

        //点击修改时间
        tvSelectTime.setOnClickListener {
            //?
            Log.d("tag", "why?")
            val time = HourAndMinDialogFragment()
            time.setSelectedDate(9, 0, 23, 0)
            time.setOnDateChooseListener { startHour, startMinute, endHour, endMinute ->

                if (endHour < startHour) {
                    Toast.makeText(applicationContext, "开始时间不能大于结束时间",
                            Toast.LENGTH_SHORT).show()
                    return@setOnDateChooseListener
                }
                if (endHour == startHour && endMinute <= startMinute) {
                    Toast.makeText(applicationContext, "开始时间不能大于结束时间",
                            Toast.LENGTH_SHORT).show()
                    return@setOnDateChooseListener
                }

                val startH = if (startHour > 9) {
                    "$startHour"
                } else {
                    "0$startHour"
                }
                val startM = if (startMinute > 9) {
                    "$startMinute"
                } else {
                    "0$startMinute"
                }
                val endH = if (endHour > 9) {
                    "$endHour"
                } else {
                    "0$endHour"
                }
                val endM = if (endMinute > 9) {
                    "$endMinute"
                } else {
                    "0$endMinute"
                }

                tvSelectTime.text = "$startH:$startM-$endH:$endM"

            }
            time.show(supportFragmentManager, "HourAndMinDialogFragment")
        }



        save1.setOnClickListener {
            infoBean!!.business_start = tvSelectTime.text.split("-")[0]
            infoBean!!.business_end = tvSelectTime.text.split("-")[1]
            infoBean!!.phone = cgPhone.text.toString()
            infoBean!!.email = cgEmail.text.toString()
            updateInfo()
        }

    }

    //layout_2 场馆设施
    @SuppressLint("WrongConstant")
    private fun changeLayout2() {
        nowSelect = 2

        val selectStr = ArrayList<String>()
        if (layout2Adapter == null) {
            layout2Adapter = VenueLayout2Adapter(docList)
        }

        rec_right.removeViewAt(0)
        val view = layoutInflater.inflate(R.layout.venue_item_2, null, false)
        rec_right.addView(view, 0)
        val rvFacilities = view.findViewById<RecyclerView>(R.id.rv_venue_facilities)
        val save2 = view.findViewById<TextView>(R.id.tv_save_2)
        val layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        rvFacilities.layoutManager = layoutManager
        rvFacilities.adapter = layout2Adapter

        layout2Adapter!!.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.cb_facilities -> {
                    if ((view as CheckBox).isChecked) {
                        selectStr.add("${position+1}")
                        Log.d("tag", "添加了该box")
                    } else {
                        selectStr.remove("${position+1}")
                        Log.d("tag", "移除了该box")
                    }
                }
            }
        }

        //View加载完成时回调
        rvFacilities.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                infoBean!!.facility.split(",").forEach {
                    val itemView = layoutManager.findViewByPosition(it.toInt() - 1)
                    if (itemView != null) {
                        val chabox = itemView.findViewById<CheckBox>(R.id.cb_facilities)
                        chabox.isChecked = true
                        selectStr.add(it)
                    }
                }

                //OnGlobalLayoutListener可能会被多次触发
                //所以完成了需求后需要移除OnGlobalLayoutListener
                rvFacilities.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        save2.setOnClickListener {
            var str = ""
            selectStr.forEach {
                str += "$it,"
            }
            infoBean!!.facility = str.substring(0,str.length-1)
            updateInfo()
        }

    }

    //layout_3
    private fun changeLayout3() {
        nowSelect = 3
        rec_right.removeViewAt(0)
        val view = layoutInflater.inflate(R.layout.venue_item_3, null, false)
        rec_right.addView(view, 0)

        val legalName = view.findViewById<TextView>(R.id.tv_legal_name)
        val card = view.findViewById<TextView>(R.id.tv_id_card)
        val companyName = view.findViewById<TextView>(R.id.tv_company_name)
        val business = view.findViewById<TextView>(R.id.tv_business_license)

        if (cgBean != null) {
            legalName.text = cgBean!!.area.legalPerson
            card.text = cgBean!!.area.cardNum
            companyName.text = cgBean!!.area.companyName
            business.text = cgBean!!.area.companyCode
        }

    }

    //layout_4
    private fun changeLayout4() {
        nowSelect = 4
        rec_right.removeViewAt(0)
        val view = layoutInflater.inflate(R.layout.venue_item_4, null, false)
        rec_right.addView(view, 0)

        val bankNumber = view.findViewById<TextView>(R.id.tv_company_bank_number)
        val bank = view.findViewById<TextView>(R.id.tv_company_bank)

        bank.text = cgBean!!.area.bankName
        bankNumber.text = cgBean!!.area.bankCardNum

    }

    //layout_5
    private var ivLogo: ImageView? = null
    private var ivLogoPath = ""
    private var photos: CCRSortableNinePhotoLayout? = null
    private var tvPhotoNum: TextView? = null


    //当前列表上的图片 新增了图片或删除了图片后 该列表会随之改变
    private var strings: MutableList<String> = ArrayList()
    /*
        当前列表上的图片(需要提交的对象)
        初始化为该场馆当前的所有场馆介绍图。
        当删除(原本存在)之前的图片时:该list会改变
        当删除新选择的图片时:该list不会改变
        图片上传后该list需要增加
     */
    private var upList: MutableList<InformationEntity.GymPicBean> = ArrayList()

    /*
        当前列表为 人为选择的list
        已选择(未上架)的图片会增加该list
        删除已选择(未上架)的图片会减少该list
        当该list!=0 时，必然存在需要上传的图片
     */
    private var selectPhotos: MutableList<String> = ArrayList()

    private fun changeLayout5() {
        nowSelect = 5
        strings.clear()//每次加载需要将此数组清空
        upList.clear()
        selectPhotos.clear()
        rec_right.removeViewAt(0)
        val view = layoutInflater.inflate(R.layout.venue_item_5, null, false)
        rec_right.addView(view, 0)

        ivLogo = view.findViewById<ImageView>(R.id.logo_image)
        photos = view.findViewById<CCRSortableNinePhotoLayout>(R.id.snpl_moment_add_photos)
        tvPhotoNum = view.findViewById<TextView>(R.id.tv_pic_num)
        val save5 = view.findViewById<TextView>(R.id.tv_save_5)

        //赋值操作
        Glide.with(this)
                .load(cgBean!!.area.logo)
                .transform( CenterCrop(this), GlideRoundTransform(this,10))
                .into(ivLogo)

        for (i in 0 until cgBean!!.pic.size) {
            strings.add(cgBean!!.pic[i].url)
            val bean = InformationEntity.GymPicBean()
            bean.qiniu_key = cgBean!!.getPic().get(i).getQiniuKey()
            bean.order_num = i + 2
            upList.add(bean)
        }


        photos!!.setData(strings)
        photos!!.setDelegate(this)
        AppConstants.SELECT_IMAGES_SIZE = strings.size//已选择的数量
        tvPhotoNum!!.text = "(${AppConstants.SELECT_IMAGES_SIZE}/9)"

        //logo
        ivLogo!!.setOnClickListener {
            EasyPhotos.createAlbum(this, true, GlideEngine.getInstance())
                    .setFileProviderAuthority("com.noplugins.keepfit.android.fileprovider")
                    .setPuzzleMenu(false)
                    .setCount(1)
                    .setOriginalMenu(false, true, null)
                    .start(102)
        }

        save5.setOnClickListener {
            if (BaseUtils.isFastClick()){
                withLs()
            }
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
        etCoachNotes.setText(infoBean!!.coach_notify)
        save6.setOnClickListener {
            //保存教练须知
            if (etCoachNotes.text.toString().isEmpty()) {
                return@setOnClickListener
            }
            infoBean!!.coach_notify = etCoachNotes.text.toString()
            updateInfo()
        }


    }


    private var maxNum = 0
    override fun onClickAddNinePhotoItem(sortableNinePhotoLayout: CCRSortableNinePhotoLayout?, view: View?, position: Int, models: java.util.ArrayList<String>?) {
        //设置最多只能上传9张图片
        if (AppConstants.SELECT_IMAGES_SIZE >= 9) {
            Toast.makeText(this, "只能上传9张图片哦～", Toast.LENGTH_SHORT).show()
        } else if (AppConstants.SELECT_IMAGES_SIZE < 9) {
            //选择新的图片
            maxNum = 9 - AppConstants.SELECT_IMAGES_SIZE
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

        AppConstants.SELECT_IMAGES_SIZE = AppConstants.SELECT_IMAGES_SIZE - 1
        tvPhotoNum!!.text = "(${AppConstants.SELECT_IMAGES_SIZE}/9)"
    }

    override fun onClickNinePhotoItem(sortableNinePhotoLayout: CCRSortableNinePhotoLayout?, view: View?, position: Int, model: String?, models: java.util.ArrayList<String>?) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK == resultCode) {
            if (nowSelect == 5) {
                if (requestCode == 101) {
                    //不支持AndroidQ
                    val resultPaths = data!!.getStringArrayListExtra(EasyPhotos.RESULT_PATHS)
                    strings.addAll(resultPaths)
                    selectPhotos.addAll(resultPaths)
                    photos!!.setData(strings)//设置九宫格
                    AppConstants.SELECT_IMAGES_SIZE = strings.size
                    tvPhotoNum!!.text = "(${AppConstants.SELECT_IMAGES_SIZE}/9)"

                } else {
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

    /**
     * 修改信息接口
     */
    private fun updateInfo() {
        if (logoBean != null) {
            upList.add(logoBean!!)
        }
        infoBean!!.gymPic = upList
        subscription = Network.getInstance("场馆信息", this)
                .submitAudit(infoBean, ProgressSubscriber("场馆信息",
                        object : SubscriberOnNextListener<Bean<Any>> {
                            override fun onNext(changguanBeanBean: Bean<Any>) {
                                Toast.makeText(this@VenueDetailActivity, "当前场馆信息修改成功",
                                        Toast.LENGTH_SHORT).show()
//                                setResult(SpUtils.getInt(applicationContext, AppConstants.FRAGMENT_SIZE) - 1)
//                                finish()
                                if (nowSelect == 5){
                                    requestData()
                                }
                            }

                            override fun onError(error: String) {
                                Toast.makeText(this@VenueDetailActivity, error,
                                        Toast.LENGTH_SHORT).show()

                            }
                        }, this, false))
    }

    private fun getPath(): String {
        val path = Environment.getExternalStorageDirectory().toString() + "/Luban/image/"
        val file = File(path)
        return if (file.mkdirs()) {
            path
        } else path
    }

    /**
     * 压缩图片
     */
    private fun withLs() {
        if ("" != ivLogoPath) {
            val file = File(ivLogoPath)
            Luban.with(this)
                    .load(file)
                    .ignoreBy(100)
                    .setTargetDir(getPath())
                    .setFocusAlpha(false)
                    .setCompressListener(object : OnCompressListener {
                        override fun onStart() {}

                        override fun onSuccess(file: File) {
                            ivLogoPath = file.path
                            upLogoToQiniu()
                        }

                        override fun onError(e: Throwable) {
                            ivLogoPath = file.path
                            upLogoToQiniu()

                        }
                    }).launch()
        } else {
            upLogoToQiniu()
        }

    }

    /**
     * logo上传到七牛云
     */
    private var logoBean: InformationEntity.GymPicBean? = null
    private var progress_upload: ProgressUtil? = null
    private fun upLogoToQiniu() {
        if ("" != ivLogoPath) {
            progress_upload = ProgressUtil()
            progress_upload!!.showProgressDialog(this, "载入中...")
            //上传icon
            val expectKey = UUID.randomUUID().toString()
            uploadManager!!.put(ivLogoPath, expectKey, uptoken,
                    { key, info, response ->
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            Log.e("qiniu", "Upload Success")
                            logoBean = InformationEntity.GymPicBean()
                            logoBean!!.qiniu_key = key
                            logoBean!!.order_num = 1
                            //测试资料上传的
                            //getUrlTest(icon_net_path);
                            val headpicPath = "http://upload.qiniup.com/$key"
                            Log.e("返回的地址", headpicPath)
                            withListLs()
                        } else {
                            Log.e("qiniu", "Upload Fail")
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                            withListLs()
                        }
                        progress_upload!!.dismissProgressDialog()
                    }, UploadOptions(null, "test-type", true, null, null))
        } else {
            withListLs()
        }


    }

    internal var i = 0

    /**
     * list 压缩
     */
    @SuppressLint("CheckResult")
    private fun withListLs() {
        i = 0
        if (selectPhotos.size == 0) {
            upListToQiniu()
            return
        }
        if (upList.size == strings.size) {
            upListToQiniu()
            return
        }
        Observable
                .fromIterable<String>(selectPhotos)
                .flatMap(Function<String, ObservableSource<File>> { path ->
                    Observable.create { emitter ->
                        val file = File(path)
                        Luban.with(this)
                                .load(file)
                                .ignoreBy(100)
                                .setTargetDir(getPath())
                                .setFocusAlpha(false)
                                .setCompressListener(object : OnCompressListener {
                                    override fun onStart() {
                                        Log.d("luban", "开始：$path")
                                    }

                                    override fun onSuccess(file: File) {
                                        emitter.onNext(file)
                                        Log.d("luban", "压缩成功:" + file.path)
                                        emitter.onComplete()
                                    }

                                    override fun onError(e: Throwable) {
                                        emitter.onError(e)
                                    }
                                }).launch()
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    Log.d("luban", "发射成功:" + response.path)
                    selectPhotos.set(i, response.path)
                    i++
                    // 如果全部完成，调用成功接口
                    if (i == selectPhotos.size) {
                        upListToQiniu()
                    }
                }, { Log.d("luban", "异常了") })

    }

    /**
     * list上传到七牛云
     */
    @SuppressLint("CheckResult")
    private fun upListToQiniu() {
        if (upList.size == strings.size) {
            updateInfo()
            return
        }
        progress_upload = ProgressUtil()
        progress_upload!!.showProgressDialog(this, "载入中...")
        Observable
                .fromIterable<String>(selectPhotos)
                .concatMap { path ->
                    Observable.create(ObservableOnSubscribe<String> { emitter ->
                        val expectKey = UUID.randomUUID().toString()
                        uploadManager!!.put(path, expectKey, uptoken,
                                { key, info, response ->
                                    if (info.isOK) {
                                        // 上传成功，发送这张图片的文件名
                                        emitter.onNext(key)
                                        emitter.onComplete()
                                    } else {
                                        // 上传失败，告辞
                                        emitter.onError(IOException(info.error))
                                    }
                                }, UploadOptions(null, "test-type", true, null, null))
                    }).subscribeOn(Schedulers.io())
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    val bean = InformationEntity.GymPicBean()
                    bean.qiniu_key = response
                    bean.order_num = upList.size + 2
                    upList.add(bean)
                    Log.d("qiniu", "上传发射成功:$response")
                    // 如果全部完成，调用成功接口
                    if (upList.size == strings.size) {
                        updateInfo()
                        Log.d("qiniu", "全部上传完成")
                        progress_upload!!.dismissProgressDialog()
                    }
                }, { throwable ->
                    Log.d("qiniu", "上传失败 $throwable")
                    updateInfo()
                    progress_upload!!.dismissProgressDialog()
                })
    }


    /**
     * 获取7牛 token
     */
    private fun getToken() {
        subscription = Network.getInstance("获取七牛云token", this)
                .get_qiniu_token(HashMap(), ProgressSubscriberNew(QiNiuToken::class.java, GsonSubscriberOnNextListener { qiNiuToken, s ->
                    Log.e("获取到的token", "获取到的token" + qiNiuToken.token)
                    uptoken = qiNiuToken.token
                }, object : SubscriberOnNextListener<Bean<Any>> {
                    override fun onNext(result: Bean<Any>) {}

                    override fun onError(error: String) {
                        Log.e("获取到的token失败", error)
                    }
                }, this, true))

    }

}
