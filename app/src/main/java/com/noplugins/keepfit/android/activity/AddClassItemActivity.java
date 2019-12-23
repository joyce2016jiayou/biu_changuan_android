package com.noplugins.keepfit.android.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.activity.mine.TeacherSelectActivity;
import com.noplugins.keepfit.android.adapter.InviteTeacherAdapter;
import com.noplugins.keepfit.android.adapter.TypeAdapter;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.bean.CgBindingBean;
import com.noplugins.keepfit.android.bean.ChangguanBean;
import com.noplugins.keepfit.android.bean.DictionaryeBean;
import com.noplugins.keepfit.android.bean.TeacherBean;
import com.noplugins.keepfit.android.callback.DialogCallBack;
import com.noplugins.keepfit.android.entity.AddClassEntity;
import com.noplugins.keepfit.android.entity.ClassEntity;
import com.noplugins.keepfit.android.entity.ClassTypeEntity;
import com.noplugins.keepfit.android.entity.MaxPeopleEntity;
import com.noplugins.keepfit.android.entity.TeacherEntity;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.GlideEngine;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.TimeCheckUtil;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.PopWindowHelper;
import com.noplugins.keepfit.android.util.ui.cropimg.ClipImageActivity;
import com.noplugins.keepfit.android.util.ui.cropimg.FileUtil;
import com.noplugins.keepfit.android.util.ui.gallery_recycleview.GalleryLayoutManager;
import com.noplugins.keepfit.android.util.ui.gallery_recycleview.RecyclerViewAdapter;
import com.noplugins.keepfit.android.util.ui.gallery_recycleview.Transformer;
import com.noplugins.keepfit.android.util.ui.jiugongge.CCRSortableNinePhotoLayout;
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow;
import com.noplugins.keepfit.android.util.ui.pop.base.CenterPopupView;
import com.noplugins.keepfit.android.util.ui.pop.inteface.ViewCallBack;
import com.noplugins.keepfit.android.util.ui.speed_recyclerview.CardScaleHelper;
import com.noplugins.keepfit.android.util.ui.speed_recyclerview.SpeedRecyclerView;
import com.othershe.calendarview.utils.CalendarUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.wheelpicker.DatePicker;
import cn.qqtheme.framework.wheelpicker.TimePicker;
import cn.qqtheme.framework.wheelview.annotation.DateMode;
import cn.qqtheme.framework.wheelview.annotation.TimeMode;
import cn.qqtheme.framework.wheelview.contract.OnDateSelectedListener;
import cn.qqtheme.framework.wheelview.contract.OnTimeSelectedListener;
import cn.qqtheme.framework.wheelview.entity.DateEntity;
import cn.qqtheme.framework.wheelview.entity.TimeEntity;
import lib.demo.spinner.MaterialSpinner;
import okhttp3.RequestBody;

public class AddClassItemActivity extends BaseActivity implements CCRSortableNinePhotoLayout.Delegate {
    @BindView(R.id.back_btn)
    ImageView back_btn;
    @BindView(R.id.add_class_teacher_btn)
    LinearLayout add_class_teacher_btn;
    @BindView(R.id.time1_edit)
    TextView time1_edit;
    @BindView(R.id.time2_edit)
    TextView time2_edit;
    @BindView(R.id.select_date_layout)
    LinearLayout select_date;
    @BindView(R.id.year_tv)
    TextView year_tv;
    @BindView(R.id.month_tv)
    TextView month_tv;
    @BindView(R.id.date_tv)
    TextView date_tv;
    @BindView(R.id.edit_class_name)
    EditText edit_class_name;
    @BindView(R.id.edit_tuanke_renshu_number)
    TextView edit_tuanke_renshu_number;
    @BindView(R.id.edit_class_jieshao)
    TextView edit_class_jieshao;
    @BindView(R.id.edit_shihe_renqun)
    TextView edit_shihe_renqun;
    @BindView(R.id.edit_zhuyi_shixiang)
    TextView edit_zhuyi_shixiang;
    @BindView(R.id.edit_price_number)
    EditText edit_price_number;
    @BindView(R.id.select_tuanke_type_btn)
    RelativeLayout select_tuanke_type_btn;
    @BindView(R.id.select_tuanke_type_tv)
    TextView select_tuanke_type_tv;
    @BindView(R.id.select_class_difficulty_btn)
    RelativeLayout select_class_difficulty_btn;
    @BindView(R.id.select_class_difficulty_tv)
    TextView select_class_difficulty_tv;
    @BindView(R.id.select_class_target_btn)
    RelativeLayout select_class_target_btn;
    @BindView(R.id.select_class_target_tv)
    TextView select_class_target_tv;
    @BindView(R.id.select_room_type_btn)
    RelativeLayout select_room_type_btn;
    @BindView(R.id.select_room_tv)
    TextView select_room_tv;
    @BindView(R.id.select_xunhuan_type_btn)
    RelativeLayout select_xunhuan_type_btn;
    @BindView(R.id.select_xunhuan_type_tv)
    TextView select_xunhuan_type_tv;
    @BindView(R.id.input_class_detail_btn)
    LinearLayout input_class_detail_btn;
    @BindView(R.id.center)
    LinearLayout center;
    @BindView(R.id.ll_time)
    LinearLayout ll_time;
    @BindView(R.id.input_shihe_renqun_btn)
    LinearLayout input_shihe_renqun_btn;
    @BindView(R.id.input_zhuyishixiang_btn)
    LinearLayout input_zhuyishixiang_btn;
    @BindView(R.id.logo_image)
    ImageView logo_image;
    @BindView(R.id.delete_icon_btn)
    ImageView delete_icon_btn;
    @BindView(R.id.snpl_moment_add_photos)
    CCRSortableNinePhotoLayout mPhotosSnpl;
    @BindView(R.id.select_numbers_tv)
    TextView select_numbers_tv;
    @BindView(R.id.invite_teacher_btn)
    LinearLayout invite_teacher_btn;
    @BindView(R.id.speed_recyclerview)
    RecyclerView speed_recyclerview;

    private String select_target_type = "1";
    private String select_class_type = "1";
    private String select_nandu_type = "容易";
    private String select_xunhuan_type = "单次";
    private TimePicker picker;
    private DatePicker datePicker;
    //获取当前的日期
    private int[] cDate;
    private int page = 1;
    private List<ClassEntity.DataBean> dataBeans = new ArrayList<>();
    private int enable_max_people;
    private List<ClassTypeEntity> class_room_types = new ArrayList<>();
    List<DictionaryeBean> tuanke_types = new ArrayList<>();
    List<DictionaryeBean> class_difficultys = new ArrayList<>();
    List<DictionaryeBean> tatget_types = new ArrayList<>();
    String start = "";
    String end = "";
    private String type = "";
    public static String class_jianjie_tv = "";
    public static String shihe_renqun_tv = "";
    public static String zhuyi_shixiang_tv = "";
    private String icon_image_path = "";
    private int max_num = 0;
    private List<String> strings = new ArrayList<>();
    public static List<TeacherBean> submit_tescher_list = new ArrayList<>();
    public static boolean is_refresh_teacher_list;
    RecyclerViewAdapter inviteTeacherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_add_class_item);
        ButterKnife.bind(this);
        isShowTitle(false);

        cDate = CalendarUtil.getCurrent3Date();

        //设置营业时间
        getYinyeTime();

        select_time();

        //选择团课类型
        get_tuanke_type();
        select_tuanke_type_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_class_type_pop();
            }
        });

        //获取房间类型
        get_class_room_type();
        select_room_type_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_room_type_pop();
            }
        });
        //获取课程难度
        get_class_leavel();
        select_class_difficulty_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_class_difficulty_pop();
            }
        });
        //获取训练目标
        get_class_target();
        select_class_target_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_class_target_pop();
            }
        });

        //设置循环
        select_xunhuan_type_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_xunhuan_pop();
            }
        });

        //设置课程详情
        input_class_detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddClassItemActivity.this, EditClassDetaiActivity.class);
                intent.putExtra("type", "class_content");
                startActivity(intent);
            }
        });

        //适合人群
        input_shihe_renqun_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddClassItemActivity.this, EditClassDetaiActivity.class);
                intent.putExtra("type", "shihe_renqun");
                startActivity(intent);
            }
        });
        //注意事项
        input_zhuyishixiang_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddClassItemActivity.this, EditClassDetaiActivity.class);
                intent.putExtra("type", "zhuyi_shixiang");
                startActivity(intent);
            }
        });

        //添加封面图
        set_icon_image();
        //设置九宫格
        mPhotosSnpl.setDelegate(this);

        //邀请团课老师
        invite_teacher_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddClassItemActivity.this, TeacherSelectActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("enter_type", "add_page");
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });

        //设置邀请教练视图
        GalleryLayoutManager manager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        manager.attach(speed_recyclerview, 1000000);
        // 设置滑动缩放效果
        manager.setItemTransformer(new Transformer());
        inviteTeacherAdapter = new RecyclerViewAdapter(submit_tescher_list, this);
        speed_recyclerview.setAdapter(inviteTeacherAdapter);
        manager.setOnItemSelectedListener((recyclerView, item, position) -> {

        });
        inviteTeacherAdapter.setmOnItemClickListener((view, data) -> {
            // 支持手动点击滑动切换
            speed_recyclerview.smoothScrollToPosition(Integer.valueOf(data));
        });


    }



    private void set_icon_image() {
        logo_image.setOnClickListener(new View.OnClickListener() {//添加图片
            @Override
            public void onClick(View view) {
                EasyPhotos.createAlbum(AddClassItemActivity.this, true, GlideEngine.getInstance())
                        .setFileProviderAuthority("com.noplugins.keepfit.android.fileprovider")
                        .setPuzzleMenu(false)
                        .setCount(1)
                        .setOriginalMenu(false, true, null)
                        .start(102);

            }
        });
        delete_icon_btn.setOnClickListener(new View.OnClickListener() {//删除图片
            @Override
            public void onClick(View view) {
                icon_image_path = "";
                delete_icon_btn.setVisibility(View.INVISIBLE);
                Glide.with(getApplicationContext()).load(R.drawable.jia_image).into(logo_image);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        submit_tescher_list.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (class_jianjie_tv.length() == 0) {
            edit_class_jieshao.setHint(getResources().getText(R.string.edit_hint23));
        } else {
            edit_class_jieshao.setText(class_jianjie_tv);
        }

        if (shihe_renqun_tv.length() == 0) {
            edit_shihe_renqun.setHint(getResources().getText(R.string.edit_hint24));
        } else {
            edit_shihe_renqun.setText(shihe_renqun_tv);
        }

        if (zhuyi_shixiang_tv.length() == 0) {
            edit_zhuyi_shixiang.setHint(getResources().getText(R.string.edit_hint25));
        } else {
            edit_zhuyi_shixiang.setText(zhuyi_shixiang_tv);
        }
        //判断是否刷新教练邀请列表
        if (is_refresh_teacher_list) {
            if (submit_tescher_list.size() > 0) {
                inviteTeacherAdapter.notifyDataSetChanged();
            }
            is_refresh_teacher_list = false;
        }


    }

    @Override
    public void doBusiness(Context mContext) {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出是否退出创建的提示
                pop(false);
            }
        });
        add_class_teacher_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int startHour = Integer.parseInt(time1_edit.getText().toString().split(":")[0]);
                int startMin = Integer.parseInt(time1_edit.getText().toString().split(":")[1]);

                int endHour = Integer.parseInt(time2_edit.getText().toString().split(":")[0]);
                int endMin = Integer.parseInt(time2_edit.getText().toString().split(":")[1]);
                if (startHour > endHour) {
                    Toast.makeText(AddClassItemActivity.this,
                            "开始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (startHour == endHour && startMin > endMin) {
                    Toast.makeText(AddClassItemActivity.this,
                            "开始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
                    return;
                }

                int yinyeStartH = Integer.parseInt(start.split(":")[0]);
                int yinyeStartM = Integer.parseInt(start.split(":")[1]);

                int yinyeEndH = Integer.parseInt(end.split(":")[0]);
                int yinyeEndM = Integer.parseInt(end.split(":")[1]);

                if (startHour < yinyeStartH) {
                    Toast.makeText(AddClassItemActivity.this,
                            "该时间段场馆未营业", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (startHour == yinyeStartH && startMin < yinyeStartM) {
                    Toast.makeText(AddClassItemActivity.this,
                            "该时间段场馆未营业", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (endHour > yinyeEndH) {
                    Toast.makeText(AddClassItemActivity.this,
                            "该时间段场馆未营业", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (endHour == yinyeEndH && endMin > yinyeEndM) {
                    Toast.makeText(AddClassItemActivity.this,
                            "该时间段场馆未营业", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edit_price_number.getText().toString().equals("")) {
                    Toast.makeText(AddClassItemActivity.this,
                            "价格不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (check_value()) {//如果所有参数不为空，请求网络接口
                    add_class();
                } else {
                    return;
                }


            }
        });

//        center.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                InputMethodManager inputMethodManager =
//                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(edit_class_jieshao.getWindowToken(), 0);
//                inputMethodManager.hideSoftInputFromWindow(edit_zhuyi_shixiang.getWindowToken(), 0);
//                inputMethodManager.hideSoftInputFromWindow(edit_shihe_renqun.getWindowToken(), 0);
//                edit_class_jieshao.clearFocus();
//                edit_class_name.clearFocus();
//                edit_price_number.clearFocus();
//                edit_shihe_renqun.clearFocus();
//                edit_tuanke_renshu_number.clearFocus();
//                edit_zhuyi_shixiang.clearFocus();
//                return false;
//            }
//        });
//        select_date.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                InputMethodManager inputMethodManager =
//                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(edit_class_jieshao.getWindowToken(), 0);
//                inputMethodManager.hideSoftInputFromWindow(edit_zhuyi_shixiang.getWindowToken(), 0);
//                inputMethodManager.hideSoftInputFromWindow(edit_shihe_renqun.getWindowToken(), 0);
//                edit_class_jieshao.clearFocus();
//                edit_class_name.clearFocus();
//                edit_price_number.clearFocus();
//                edit_shihe_renqun.clearFocus();
//                edit_tuanke_renshu_number.clearFocus();
//                edit_zhuyi_shixiang.clearFocus();
//
//                return false;
//            }
//        });
//        time1_edit.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                InputMethodManager inputMethodManager =
//                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(edit_class_jieshao.getWindowToken(), 0);
//                inputMethodManager.hideSoftInputFromWindow(edit_zhuyi_shixiang.getWindowToken(), 0);
//                inputMethodManager.hideSoftInputFromWindow(edit_shihe_renqun.getWindowToken(), 0);
//                edit_class_jieshao.clearFocus();
//                edit_class_name.clearFocus();
//                edit_price_number.clearFocus();
//                edit_shihe_renqun.clearFocus();
//                edit_tuanke_renshu_number.clearFocus();
//                edit_zhuyi_shixiang.clearFocus();
//                return false;
//            }
//        });
//        time2_edit.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                InputMethodManager inputMethodManager =
//                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(edit_class_jieshao.getWindowToken(), 0);
//                inputMethodManager.hideSoftInputFromWindow(edit_zhuyi_shixiang.getWindowToken(), 0);
//                inputMethodManager.hideSoftInputFromWindow(edit_shihe_renqun.getWindowToken(), 0);
//                edit_class_jieshao.clearFocus();
//                edit_class_name.clearFocus();
//                edit_price_number.clearFocus();
//                edit_shihe_renqun.clearFocus();
//                edit_tuanke_renshu_number.clearFocus();
//                edit_zhuyi_shixiang.clearFocus();
//                return false;
//            }
//        });
    }

    private void get_class_room_type() {
        Map<String, Object> params = new HashMap<>();
        params.put("areaNum", SpUtils.getString(getApplicationContext(), AppConstants.CHANGGUAN_NUM));//场馆编号
        subscription = Network.getInstance("获取房间类型", this)
                .get_class_type(params,
                        new ProgressSubscriber<>("获取房间类型", new SubscriberOnNextListener<Bean<List<ClassTypeEntity>>>() {
                            @Override
                            public void onNext(Bean<List<ClassTypeEntity>> result) {
                                if (class_room_types.size() > 0) {
                                    class_room_types.clear();
                                }
                                class_room_types.addAll(result.getData());
                                //获取最大人数
                                search_room_people(0);
                                type = class_room_types.get(0).getKey() + "";


                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));
    }


    private void select_class_type_pop() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_tuanke_type_btn.getWidth(),
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(select_tuanke_type_btn);
        /**设置逻辑*/
        View view = popupWindow.getContentView();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < tuanke_types.size(); i++) {
            strings.add(tuanke_types.get(i).getName());
        }
        TypeAdapter typeAdapter = new TypeAdapter(strings, getApplicationContext());
        ListView listView = view.findViewById(R.id.listview);
        listView.setAdapter(typeAdapter);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            select_tuanke_type_tv.setText(strings.get(i));
            select_class_type = tuanke_types.get(i).getValue();
            popupWindow.dismiss();
        });
    }

    private void select_class_difficulty_pop() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_class_difficulty_btn.getWidth(),
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(select_class_difficulty_btn);
        /**设置逻辑*/
        View view = popupWindow.getContentView();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < class_difficultys.size(); i++) {
            strings.add(class_difficultys.get(i).getName());
        }
        TypeAdapter typeAdapter = new TypeAdapter(strings, getApplicationContext());
        ListView listView = view.findViewById(R.id.listview);
        listView.setAdapter(typeAdapter);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            select_class_difficulty_tv.setText(strings.get(i));
            select_nandu_type = class_difficultys.get(i).getValue();
            popupWindow.dismiss();
        });
    }

    private void select_room_type_pop() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_room_type_btn.getWidth(),
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(select_room_type_btn);
        /**设置逻辑*/
        View view = popupWindow.getContentView();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < class_room_types.size(); i++) {
            strings.add(class_room_types.get(i).getValue());
        }
        TypeAdapter typeAdapter = new TypeAdapter(strings, getApplicationContext());
        ListView listView = view.findViewById(R.id.listview);
        listView.setAdapter(typeAdapter);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            select_room_tv.setText(class_room_types.get(i).getValue());
            //查询每个房间最大能容纳的人数
            type = class_room_types.get(i).getKey() + "";
            search_room_people(i);

            popupWindow.dismiss();
        });
    }


    private void select_class_target_pop() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_class_target_btn.getWidth(),
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(select_class_target_btn);
        /**设置逻辑*/
        View view = popupWindow.getContentView();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < tatget_types.size(); i++) {
            strings.add(tatget_types.get(i).getName());
        }
        TypeAdapter typeAdapter = new TypeAdapter(strings, getApplicationContext());
        ListView listView = view.findViewById(R.id.listview);
        listView.setAdapter(typeAdapter);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            select_class_target_tv.setText(strings.get(i));
            select_target_type = tatget_types.get(i).getValue();
            popupWindow.dismiss();
        });
    }

    private void select_xunhuan_pop() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_xunhuan_type_btn.getWidth(),
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(select_xunhuan_type_btn);
        /**设置逻辑*/
        View view = popupWindow.getContentView();
        List<String> strings = new ArrayList<>();
        strings.add("单次");
        TypeAdapter typeAdapter = new TypeAdapter(strings, getApplicationContext());
        ListView listView = view.findViewById(R.id.listview);
        listView.setAdapter(typeAdapter);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            select_xunhuan_type_tv.setText(strings.get(i));
            select_xunhuan_type = strings.get(i);
            popupWindow.dismiss();
        });
    }


    @Override
    public void onBackPressed() {
        pop(false);
    }

    private void pop(boolean is_no_invite_teacher) {
        new XPopup.Builder(this)
                .autoOpenSoftInput(true)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .asCustom(new CenterPopupView(this, R.layout.back_invite_teacher_pop, new ViewCallBack() {
                    @Override
                    public void onReturnView(View view, BasePopupView popup) {
                        TextView pop_title = view.findViewById(R.id.pop_title);
                        TextView pop_content = view.findViewById(R.id.pop_content);
                        if (is_no_invite_teacher) {//弹出"选择教练"提示
                            pop_title.setText(R.string.tv178);
                            pop_content.setText(R.string.tv179);
                        }
                        view.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popup.dismiss();
                            }
                        });
                        view.findViewById(R.id.sure_btn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                                popup.dismiss();
                            }
                        });
                    }

                })).show();
    }


    private void add_class() {
        Map<String, Object> params = new HashMap<>();
        params.put("gym_area_num", SpUtils.getString(getApplicationContext(), AppConstants.CHANGGUAN_NUM));//场馆编号
        params.put("course_name", edit_class_name.getText().toString());//团课名称
        params.put("target", select_target_type);
        params.put("difficulty", select_nandu_type);
        params.put("type", type);
        params.put("class_type", select_class_type);//团课类型：1单车2瑜伽3普拉提4拳击5舞蹈6功能性7儿童
        params.put("course_type", "1");//1团课，2私教，3健身
        params.put("max_num", edit_tuanke_renshu_number.getText().toString());//人数限制
        params.put("start_time",
                year_tv.getText().toString() + "-" + month_tv.getText().toString() + "-" + date_tv.getText().toString() + " "
                        + time1_edit.getText().toString());//开始时间
        params.put("end_time", year_tv.getText().toString() + "-" + month_tv.getText().toString() + "-" + date_tv.getText().toString() + " "
                + time2_edit.getText().toString());//结束时间
        if (select_xunhuan_type.equals("单次")) {
            params.put("loop_cycle", "");//循环周数
        }
        params.put("course_des", edit_class_jieshao.getText().toString());//课程介绍
        params.put("tips", edit_zhuyi_shixiang.getText().toString());//注意事项
        params.put("price", edit_price_number.getText().toString());//注意事项
        params.put("suit_person", edit_shihe_renqun.getText().toString());//适合人群
        subscription = Network.getInstance("添加课程", this)
                .add_class(params,
                        new ProgressSubscriber<>("添加课程", new SubscriberOnNextListener<Bean<AddClassEntity>>() {
                            @Override
                            public void onNext(Bean<AddClassEntity> result) {
                                Intent intent = new Intent(AddClassItemActivity.this, YaoQingTeacherActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("create_time", result.getData().getStartTime());
                                bundle.putString("gym_course_num", result.getData().getGym_course_num());
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onError(String error) {
                                Toast.makeText(AddClassItemActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }, this, true));
    }

    private boolean check_value() {
        if (TextUtils.isEmpty(edit_class_name.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi16, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edit_tuanke_renshu_number.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi17, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edit_class_jieshao.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi18, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edit_shihe_renqun.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi19, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edit_zhuyi_shixiang.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi20, Toast.LENGTH_SHORT).show();
            return false;
        } else if (Integer.valueOf(edit_tuanke_renshu_number.getText().toString()) > enable_max_people) {
            Log.e("最大人数", enable_max_people + "");
            Toast.makeText(this, R.string.alert_dialog_tishi21, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private void select_time() {
        year_tv.setText(String.valueOf(cDate[0]));
        if (cDate[1] <= 9) {
            month_tv.setText("0" + cDate[1]);
        } else {
            month_tv.setText(String.valueOf(cDate[1]));
        }
        if (cDate[2] <= 9) {
            date_tv.setText("0" + cDate[2]);
        } else {
            date_tv.setText(String.valueOf(cDate[2]));
        }

        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_date();
            }
        });
        time1_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_check(time1_edit);
            }
        });

        time2_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                time_check(time2_edit);
            }
        });
    }

    private void select_date() {
        DateEntity today = DateEntity.to3day();
        datePicker = new DatePicker(this, DateMode.YEAR_MONTH_DAY);
        datePicker.setRange(today, DateEntity.to30day());
        datePicker.setDefaultValue(today);
        datePicker.showAtBottom();
        datePicker.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onItemSelected(int year, int month, int day) {
                year_tv.setText(String.valueOf(year));
                if (month <= 9) {
                    month_tv.setText("0" + month);
                } else {
                    month_tv.setText(String.valueOf(month));
                }
                if (day <= 9) {
                    date_tv.setText("0" + day);
                } else {
                    date_tv.setText(String.valueOf(day));

                }
            }
        });
    }

    private void time_check(TextView textView) {
        picker = new TimePicker(this, TimeMode.HOUR_24);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        picker.setDefaultValue(new TimeEntity(hour, minute));
        picker.showAtBottom();
        picker.setOnTimeSelectedListener(new OnTimeSelectedListener() {
            @Override
            public void onItemSelected(int hour, int minute, int second) {

                if (minute <= 9) {
                    textView.setText(hour + ":0" + minute);
                } else {
                    textView.setText(hour + ":" + minute);
                }
            }
        });
    }


    private void search_room_people(int position) {
        Map<String, Object> params = new HashMap<>();
        params.put("gymAreaNum", SpUtils.getString(getApplicationContext(), AppConstants.CHANGGUAN_NUM));//场馆编号
        if (class_room_types.size() > 0) {
            params.put("PlaceType", class_room_types.get(position).getKey());
        }
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        Log.e(TAG, "获取最大人数参数：" + json_params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        subscription = Network.getInstance("获取最大人数", this)
                .get_max_num(requestBody, new ProgressSubscriberNew<>(MaxPeopleEntity.class, new GsonSubscriberOnNextListener<MaxPeopleEntity>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void on_post_entity(MaxPeopleEntity entity, String s) {
                        Log.e("获取最大人数成功", entity.getData() + "获取最大人数成功" + s);
                        enable_max_people = entity.getData();
                        edit_tuanke_renshu_number.setText("" + enable_max_people);
                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("获取最大人数失败", "获取最大人数失败:" + error);
                    }
                }, this, true));
    }


    private void get_class_leavel() {
        Map<String, Object> params = new HashMap<>();
        params.put("object", "difficulty");
        subscription = Network.getInstance("获取课程难度", this)
                .get_types(params,
                        new ProgressSubscriber<>("获取课程难度", new SubscriberOnNextListener<Bean<List<DictionaryeBean>>>() {
                            @Override
                            public void onNext(Bean<List<DictionaryeBean>> addClassEntityBean) {
                                if (class_difficultys.size() > 0) {
                                    class_difficultys.clear();
                                }
                                class_difficultys.addAll(addClassEntityBean.getData());
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));
    }

    private void get_tuanke_type() {
        Map<String, Object> params = new HashMap<>();
        params.put("object", 6);
        subscription = Network.getInstance("获取团课类型", this)
                .get_types(params,
                        new ProgressSubscriber<>("获取团课类型", new SubscriberOnNextListener<Bean<List<DictionaryeBean>>>() {
                            @Override
                            public void onNext(Bean<List<DictionaryeBean>> addClassEntityBean) {
                                if (tuanke_types.size() > 0) {
                                    tuanke_types.clear();
                                }
                                tuanke_types.addAll(addClassEntityBean.getData());
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));


    }

    private void get_class_target() {
        Map<String, Object> params = new HashMap<>();
        params.put("object", "target");
        subscription = Network.getInstance("获取训练目标", this)
                .get_types(params,
                        new ProgressSubscriber<>("获取训练目标", new SubscriberOnNextListener<Bean<List<DictionaryeBean>>>() {
                            @Override
                            public void onNext(Bean<List<DictionaryeBean>> addClassEntityBean) {
                                if (tatget_types.size() > 0) {
                                    tatget_types.clear();
                                }
                                tatget_types.addAll(addClassEntityBean.getData());
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));


    }


    private void getYinyeTime() {
        Map<String, Object> params = new HashMap<>();
        params.put("areaNum", SpUtils.getString(getApplicationContext(), AppConstants.CHANGGUAN_NUM));
        subscription = Network.getInstance("我的", this)
                .myArea(params,
                        new ProgressSubscriber<>("我的", new SubscriberOnNextListener<Bean<ChangguanBean>>() {
                            @Override
                            public void onNext(Bean<ChangguanBean> addClassEntityBean) {
                                start = TimeCheckUtil.removeSecond(addClassEntityBean.getData()
                                        .getArea().getBusinessStart());
                                end = TimeCheckUtil.removeSecond(addClassEntityBean.getData()
                                        .getArea().getBusinessEnd());
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));


    }

    @Override
    public void onClickAddNinePhotoItem(CCRSortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        //设置最多只能上传9张图片
        if (AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE >= 9) {
            Toast.makeText(getApplicationContext(), "只能上传9张图片哦～", Toast.LENGTH_SHORT).show();
        } else {
            max_num = 9 - AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE;
            EasyPhotos.createAlbum(this, true, GlideEngine.getInstance())
                    .setFileProviderAuthority("com.noplugins.keepfit.android.fileprovider")
                    .setPuzzleMenu(false)
                    .setCount(max_num)
                    .setOriginalMenu(false, true, null)
                    .start(101);
        }
    }

    @Override
    public void onClickDeleteNinePhotoItem(CCRSortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
        AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE = AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE - 1;
        select_numbers_tv.setText(AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE + "/9");
    }

    @Override
    public void onClickNinePhotoItem(CCRSortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            //相机或相册回调
            if (requestCode == 101) {
                //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
                ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
//                for (int i = 0; i < resultPhotos.size(); i++) {
//                    Log.e("图片地址", resultPhotos.get(i).path);
//                }
                //返回图片地址集合：如果你只需要获取图片的地址，可以用这个
                ArrayList<String> resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
                //返回图片地址集合时如果你需要知道用户选择图片时是否选择了原图选项，用如下方法获取
                boolean selectedOriginal = data.getBooleanExtra(EasyPhotos.RESULT_SELECTED_ORIGINAL, false);
                strings.addAll(resultPaths);
                mPhotosSnpl.setData(strings);//设置九宫格
                AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE = strings.size();
                select_numbers_tv.setText(AppConstants.ADD_CLASS_SELECT_IMAGES_SIZE + "/9");
                return;
            } else if (requestCode == 102) {//添加icon,上传icon
                ArrayList<String> resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
                if (resultPaths.size() > 0) {
                    icon_image_path = resultPaths.get(0);
                    gotoClipActivity(Uri.fromFile(new File(icon_image_path)));
                }
            } else if (requestCode == 103) {
                final Uri uri = data.getData();
                if (uri == null) {
                    return;
                }
                String cropImagePath = FileUtil.getRealFilePathFromUri(getApplicationContext(), uri);
                //Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                File icon_iamge_file = new File(cropImagePath);
                Glide.with(getApplicationContext()).load(icon_iamge_file).into(logo_image);
                delete_icon_btn.setVisibility(View.VISIBLE);
            }
        } else if (RESULT_CANCELED == resultCode) {
            //Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ClipImageActivity.class);
        intent.putExtra("type", 2);//1:圆形 2:方形
        intent.setData(uri);
        startActivityForResult(intent, 103);
    }
}
