package com.noplugins.keepfit.android.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.noplugins.keepfit.android.activity.use.TeamInfoActivity;
import com.noplugins.keepfit.android.adapter.SelectTypeAdapter;
import com.noplugins.keepfit.android.adapter.TypeAdapter;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.base.MyApplication;
import com.noplugins.keepfit.android.bean.CgBindingBean;
import com.noplugins.keepfit.android.bean.ChangguanBean;
import com.noplugins.keepfit.android.bean.DictionaryeBean;
import com.noplugins.keepfit.android.bean.SelectRoomBean;
import com.noplugins.keepfit.android.bean.TeacherBean;
import com.noplugins.keepfit.android.callback.ImageCompressCallBack;
import com.noplugins.keepfit.android.callback.PopViewCallBack;
import com.noplugins.keepfit.android.entity.AddClassEntity;
import com.noplugins.keepfit.android.entity.ClassEntity;
import com.noplugins.keepfit.android.entity.ClassTypeEntity;
import com.noplugins.keepfit.android.entity.MaxPeopleEntity;
import com.noplugins.keepfit.android.entity.QiNiuToken;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.global.PublicPopControl;
import com.noplugins.keepfit.android.util.GlideEngine;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.TimeCheckUtil;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.ProgressUtil;
import com.noplugins.keepfit.android.util.ui.cropimg.ClipImageActivity;
import com.noplugins.keepfit.android.util.ui.cropimg.FileUtil;
import com.noplugins.keepfit.android.util.ui.gallery_recycleview.GalleryLayoutManager;
import com.noplugins.keepfit.android.util.ui.gallery_recycleview.RecyclerViewAdapter;
import com.noplugins.keepfit.android.util.ui.gallery_recycleview.Transformer;
import com.noplugins.keepfit.android.util.ui.jiugongge.CCRSortableNinePhotoLayout;
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow;
import com.noplugins.keepfit.android.util.ui.pop.base.CenterPopupView;
import com.noplugins.keepfit.android.util.ui.pop.inteface.ViewCallBack;
import com.othershe.calendarview.utils.CalendarUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


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
    @BindView(R.id.select_room_name_btn)
    RelativeLayout select_room_name_btn;
    @BindView(R.id.select_room_name_tv)
    TextView select_room_name_tv;
    @BindView(R.id.jisuan_time_tv)
    TextView jisuan_time_tv;
    @BindView(R.id.invite_teacher_number_tv)
    TextView invite_teacher_number_tv;

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
    private String room_type = "";
    public static String class_jianjie_tv = "";
    public static String shihe_renqun_tv = "";
    public static String zhuyi_shixiang_tv = "";
    private String icon_image_path = "";
    private int max_num = 0;
    private List<String> strings = new ArrayList<>();
    public static List<TeacherBean> submit_tescher_list = new ArrayList<>();
    public static boolean is_refresh_teacher_list;
    RecyclerViewAdapter inviteTeacherAdapter;
    List<SelectRoomBean> room_lists = new ArrayList<>();
    private String select_room_name = "";
    private String select_room_name_id;
    private ProgressUtil progress_upload;
    /**
     * 七牛云
     **/
    //指定upToken, 强烈建议从服务端提供get请求获取
    private String uptoken = "xxxxxxxxx:xxxxxxx:xxxxxxxxxx";
    private SimpleDateFormat sdf;
    private String qiniu_key;
    private UploadManager uploadManager;
    private String icon_net_path = "";

    /**
     * 七牛云
     **/
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
        /**七牛云**/
        uploadManager = MyApplication.uploadManager;
        sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        qiniu_key = "icon_" + sdf.format(new Date());
        getToken();
        /**七牛云**/

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
        //获取房间数量
        select_room_name_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_room_list_pop();
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
                if (!TextUtils.isEmpty(edit_class_jieshao.getText())) {
                    intent.putExtra("class_content", edit_class_jieshao.getText().toString());
                }
                startActivity(intent);


            }
        });

        //适合人群
        input_shihe_renqun_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddClassItemActivity.this, EditClassDetaiActivity.class);
                intent.putExtra("type", "shihe_renqun");
                if (!TextUtils.isEmpty(edit_shihe_renqun.getText())) {
                    intent.putExtra("class_shihe_renqun", edit_shihe_renqun.getText().toString());
                }
                startActivity(intent);
            }
        });
        //注意事项
        input_zhuyishixiang_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddClassItemActivity.this, EditClassDetaiActivity.class);
                intent.putExtra("type", "zhuyi_shixiang");
                if (!TextUtils.isEmpty(edit_zhuyi_shixiang.getText())) {
                    intent.putExtra("class_zhuyi_shixiang", edit_zhuyi_shixiang.getText().toString());
                }
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

    private void getToken() {
        subscription = Network.getInstance("登录", this)
                .get_qiniu_token(new HashMap<>(), new ProgressSubscriberNew<>(QiNiuToken.class, new GsonSubscriberOnNextListener<QiNiuToken>() {
                    @Override
                    public void on_post_entity(QiNiuToken qiNiuToken, String s) {
                        Log.e("获取到的token", "获取到的token" + qiNiuToken.getToken());
                        uptoken = qiNiuToken.getToken();

                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("获取到的token失败", error);
                    }
                }, this, true));

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
                invite_teacher_number_tv.setText("(" + submit_tescher_list.size() + "/20)");
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
                if (check_value()) {//如果所有参数不为空，请求网络接口
                    add_class();
                } else {
                    return;
                }


            }
        });
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
                                room_type = class_room_types.get(0).getKey() + "";


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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        SelectTypeAdapter typeAdapter = new SelectTypeAdapter(strings, getApplicationContext());
        RecyclerView listView = view.findViewById(R.id.listview);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener(new SelectTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                select_tuanke_type_tv.setText(strings.get(position));
                select_class_type = tuanke_types.get(position).getValue();
                popupWindow.dismiss();
            }
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        SelectTypeAdapter typeAdapter = new SelectTypeAdapter(strings, getApplicationContext());
        RecyclerView listView = view.findViewById(R.id.listview);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener(new SelectTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                select_class_difficulty_tv.setText(strings.get(position));
                select_nandu_type = class_difficultys.get(position).getValue();
                popupWindow.dismiss();
            }
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        SelectTypeAdapter typeAdapter = new SelectTypeAdapter(strings, getApplicationContext());
        RecyclerView listView = view.findViewById(R.id.listview);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener(new SelectTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                select_room_tv.setText(class_room_types.get(position).getValue());
                //查询每个房间最大能容纳的人数
                room_type = class_room_types.get(position).getKey() + "";
                //获取房间列表
                get_room_list();

                popupWindow.dismiss();
            }
        });
    }


    private void select_room_list_pop() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_room_name_btn.getWidth(),
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(select_room_name_btn);
        /**设置逻辑*/
        View view = popupWindow.getContentView();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < room_lists.size(); i++) {
            strings.add(room_lists.get(i).getPlaceName());
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        SelectTypeAdapter typeAdapter = new SelectTypeAdapter(strings, getApplicationContext());
        RecyclerView listView = view.findViewById(R.id.listview);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener(new SelectTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                select_room_name_tv.setText(room_lists.get(position).getPlaceName());
                select_room_name = room_lists.get(position).getPlaceName();
                select_room_name_id = room_lists.get(position).getPlaceNum();
                edit_tuanke_renshu_number.setText(room_lists.get(position).getMaxNum() + "人");
                popupWindow.dismiss();
            }
        });
    }


    private void get_room_list() {
        Map<String, Object> params = new HashMap<>();
        params.put("areaNum", SpUtils.getString(getApplicationContext(), AppConstants.CHANGGUAN_NUM));//场馆编号
        params.put("placeType", room_type);
        subscription = Network.getInstance("获取房间列表", this)
                .get_class_type1(params,
                        new ProgressSubscriber<>("获取房间列表", new SubscriberOnNextListener<Bean<List<SelectRoomBean>>>() {
                            @Override
                            public void onNext(Bean<List<SelectRoomBean>> result) {
                                if (room_lists.size() > 0) {
                                    room_lists.clear();
                                }
                                room_lists.addAll(result.getData());
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        SelectTypeAdapter typeAdapter = new SelectTypeAdapter(strings, getApplicationContext());
        RecyclerView listView = view.findViewById(R.id.listview);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener(new SelectTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                select_class_target_tv.setText(strings.get(position));
                select_target_type = tatget_types.get(position).getValue();
                popupWindow.dismiss();
            }
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        SelectTypeAdapter typeAdapter = new SelectTypeAdapter(strings, getApplicationContext());
        RecyclerView listView = view.findViewById(R.id.listview);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener(new SelectTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                select_xunhuan_type_tv.setText(strings.get(position));
                select_xunhuan_type = strings.get(position);
                popupWindow.dismiss();
            }
        });
    }


    @Override
    public void onBackPressed() {
        pop(false);
    }

    private void pop(boolean is_no_invite_teacher) {
        PublicPopControl.alert_dialog_center(this, new PopViewCallBack() {
            @Override
            public void return_view(View view, BasePopupView popup) {
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
                        if (is_no_invite_teacher) {
                            //这边调用新增团课的接口
                            add_class();
                        } else {
                            finish();
                        }
                        popup.dismiss();
                    }
                });
            }
        });
    }

    private boolean calculate_time(TextView time1_edit, TextView time2_edit) {
        int startHour = Integer.parseInt(time1_edit.getText().toString().split(":")[0]);
        int startMin = Integer.parseInt(time1_edit.getText().toString().split(":")[1]);
        int endHour = Integer.parseInt(time2_edit.getText().toString().split(":")[0]);
        int endMin = Integer.parseInt(time2_edit.getText().toString().split(":")[1]);
        if (startHour > endHour) {
            Toast.makeText(AddClassItemActivity.this,
                    "开始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (startHour == endHour && startMin > endMin) {
            Toast.makeText(AddClassItemActivity.this,
                    "开始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        int yinyeStartH = Integer.parseInt(start.split(":")[0]);
        int yinyeStartM = Integer.parseInt(start.split(":")[1]);
        int yinyeEndH = Integer.parseInt(end.split(":")[0]);
        int yinyeEndM = Integer.parseInt(end.split(":")[1]);
        if (startHour < yinyeStartH) {
            Toast.makeText(AddClassItemActivity.this,
                    "该时间段场馆未营业", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (startHour == yinyeStartH && startMin < yinyeStartM) {
            Toast.makeText(AddClassItemActivity.this,
                    "该时间段场馆未营业", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (endHour > yinyeEndH) {
            Toast.makeText(AddClassItemActivity.this,
                    "该时间段场馆未营业", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (endHour == yinyeEndH && endMin > yinyeEndM) {
            Toast.makeText(AddClassItemActivity.this,
                    "该时间段场馆未营业", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void add_class() {
        if (!calculate_time(time1_edit, time2_edit)) {//判断时间是否正确
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("gym_area_num", SpUtils.getString(getApplicationContext(), AppConstants.CHANGGUAN_NUM));//场馆编号
        params.put("course_name", edit_class_name.getText().toString());//团课名称
        params.put("target", select_target_type);//训练目标
        params.put("difficulty", select_nandu_type);//训练难度
        params.put("type", room_type);//房间类型
        params.put("gymPlaceNum", select_room_name_id);//选择的房间名称编号
        params.put("placeName", select_room_name);//选择的房间名称
        params.put("logo", icon_net_path);
        List<CgBindingBean.TeacherNumBean> teacherBeanList = new ArrayList<>();
        for (TeacherBean teacherBean : submit_tescher_list) {
            CgBindingBean.TeacherNumBean teacherNumBean = new CgBindingBean.TeacherNumBean();
            teacherNumBean.setNum(teacherBean.getTeacherNum());
            teacherBeanList.add(teacherNumBean);
        }
        params.put("teacherNum", teacherBeanList);//选择的教练列表
        params.put("class_type", select_class_type);//团课类型：1单车2瑜伽3普拉提4拳击5舞蹈6功能性7儿童
        params.put("max_num", edit_tuanke_renshu_number.getText().toString().replace("人", ""));//人数限制
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
//                                Intent intent = new Intent(AddClassItemActivity.this, TeamInfoActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putString("create_time", result.getData().getStartTime());
//                                bundle.putString("gym_course_num", result.getData().getGym_course_num());
//                                intent.putExtras(bundle);
//                                startActivity(intent);
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
        } else if (icon_net_path.length() == 0) {//logo
            Toast.makeText(this, R.string.alert_dialog_tishi36, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(select_room_name_tv.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi37, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edit_price_number.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi38, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(time1_edit.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi39, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(time2_edit.getText())) {
            Toast.makeText(this, R.string.alert_dialog_tishi40, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (submit_tescher_list.size() == 0) {//邀请老师列表
                pop(true);
                return false;
            }
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

                if (!TextUtils.isEmpty(time1_edit.getText()) && !TextUtils.isEmpty(time2_edit.getText())) {
                    //判断结束时候是否大于当前时间
                    if (!calculate_time(time1_edit, time2_edit)) {//判断时间是否正确
                        return;
                    } else {
                        String start_time = "2019/01/01 " + time1_edit.getText().toString();
                        String end_time = "2019/01/01 " + time2_edit.getText().toString();
                        //Log.e("接口少房间数量", start_time + "\n" + end_time);
                        //Log.e("计算出来的时间", getTimeExpend(start_time, end_time));
                        jisuan_time_tv.setText(getTimeExpend(start_time, end_time));
                    }
                }
            }
        });
    }

    private String getTimeExpend(String startTime, String endTime) {
        //传入字串类型 2016/06/28 08:30
        long longStart = getTimeMillis(startTime); //获取开始时间毫秒数
        long longEnd = getTimeMillis(endTime);  //获取结束时间毫秒数
        long longExpend = longEnd - longStart;  //获取时间差

        long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数

        return longHours + ":" + longMinutes;
    }

    private long getTimeMillis(String strTime) {
        long returnMillis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date d = null;
        try {
            d = sdf.parse(strTime);
            returnMillis = d.getTime();
        } catch (ParseException e) {
            Toast.makeText(AddClassItemActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return returnMillis;
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
                //上传七牛云
                progress_upload = new ProgressUtil();
                progress_upload.showProgressDialog(this, "上传中...");
                //上传icon
                upload_icon_image(cropImagePath, false);

            }
        } else if (RESULT_CANCELED == resultCode) {
            //Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
    }


    private void upload_icon_image(String image_path, boolean is_jiugognge) {
        Luban.with(this)
                .load(image_path)
                .ignoreBy(100)
                .setTargetDir(getCompressJpgFileAbsolutePath())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                }).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
                // TODO 压缩开始前调用，可以在方法内启动 loading UI
            }

            @Override
            public void onSuccess(File file) {
                // TODO 压缩成功后调用，返回压缩后的图片文件
                if (is_jiugognge) {//九宫格上传
                    //jiugonggeCallBack.onSucceed(file.getAbsolutePath());
                } else {
                    compressCallBack.onSucceed(file.getAbsolutePath());
                }
            }

            @Override
            public void onError(Throwable e) {
                compressCallBack.onFailure(e.getMessage());
                // TODO 当压缩过程出现问题时调用
            }
        }).launch();
    }

    /**
     * 获取保存压缩图片文件的位置
     *
     * @return
     */
    private final static String PHOTO_COMPRESS_JPG_BASEPATH = "/" + "TakePhoto" + "/CompressImgs/";

    public static String getCompressJpgFileAbsolutePath() {
        String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + PHOTO_COMPRESS_JPG_BASEPATH;
        File file = new File(fileBasePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        return fileBasePath;
    }

    ImageCompressCallBack compressCallBack = new ImageCompressCallBack() {
        @Override
        public void onSucceed(String data) {
//            Log.e("压缩过的",data);
//            File file = new File(data);
//            Log.e("压缩后的大小", FileSizeUtil.getFileOrFilesSize(file.getAbsolutePath(), 2) + "");
            upload_icon_work(data);
        }

        @Override
        public void onFailure(String msg) {
            Log.e("压缩失败的", msg);
        }
    };

    private void upload_icon_work(String img_path) {
        Log.e("上传icon", img_path);
        uploadManager.put(img_path, qiniu_key, uptoken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            icon_net_path = key;
                            //测试资料上传的
                            //getUrlTest(icon_net_path);
                            //Log.e("qiniu", "Upload Success");
                            Log.e("上传icon成功：", icon_net_path);
                            progress_upload.dismissProgressDialog();
                        } else {
                            Log.e("qiniu", "Upload Fail");
                            progress_upload.dismissProgressDialog();
                            progress_upload = null;
                            Toast.makeText(getApplicationContext(), R.string.tv185, Toast.LENGTH_SHORT).show();
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        //Log.e("qiniu", key + ",\r\n " + info.path + ",\r\n " + response);
                    }
                }, new UploadOptions(null, "test-type", true, null, null));
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
