package com.noplugins.keepfit.android.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.activity.InformationCheckActivity;
import com.noplugins.keepfit.android.adapter.BaseInformationTagAdapter;
import com.noplugins.keepfit.android.adapter.ExRecyclerAdapter;
import com.noplugins.keepfit.android.adapter.SelectRoomAdapter;
import com.noplugins.keepfit.android.adapter.SelectTypeAdapter;
import com.noplugins.keepfit.android.adapter.TypeAdapter;
import com.noplugins.keepfit.android.base.MyApplication;
import com.noplugins.keepfit.android.bean.CityCode;
import com.noplugins.keepfit.android.bean.DictionaryeBean;
import com.noplugins.keepfit.android.bean.GetCityCode;
import com.noplugins.keepfit.android.bean.GetQuCode;
import com.noplugins.keepfit.android.bean.JsonBean;
import com.noplugins.keepfit.android.callback.ImageCompressCallBack;
import com.noplugins.keepfit.android.entity.BiaoqianEntity;
import com.noplugins.keepfit.android.entity.InformationEntity;
import com.noplugins.keepfit.android.entity.ItemBean;
import com.noplugins.keepfit.android.entity.QiNiuToken;
import com.noplugins.keepfit.android.entity.UrlEntity;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.BaseUtils;
import com.noplugins.keepfit.android.util.GlideEngine;
import com.noplugins.keepfit.android.util.data.StringsHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.screen.KeyboardUtils;
import com.noplugins.keepfit.android.util.ui.MyGridView;
import com.noplugins.keepfit.android.util.ui.NoScrollViewPager;
import com.noplugins.keepfit.android.util.ui.ProgressUtil;
import com.noplugins.keepfit.android.util.ui.StepView;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;
import com.noplugins.keepfit.android.util.ui.cropimg.ClipImageActivity;
import com.noplugins.keepfit.android.util.ui.cropimg.FileUtil;
import com.noplugins.keepfit.android.util.ui.jiugongge.CCRSortableNinePhotoLayout;
import com.noplugins.keepfit.android.util.ui.pop.CommonPopupWindow;
import com.noplugins.keepfit.android.util.ui.pop.base.CenterPopupView;
import com.noplugins.keepfit.android.util.ui.pop.inteface.ViewCallBack;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.wheelpicker.TimePicker;
import cn.qqtheme.framework.wheelview.annotation.TimeMode;
import cn.qqtheme.framework.wheelview.contract.OnTimeSelectedListener;
import cn.qqtheme.framework.wheelview.entity.TimeEntity;
import lib.demo.spinner.MaterialSpinner;
import rx.Subscription;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class BaseInformationFragment extends ViewPagerFragment implements CCRSortableNinePhotoLayout.Delegate {
    /**
     * 拖拽排序九宫格控件
     */
    @BindView(R.id.snpl_moment_add_photos)
    CCRSortableNinePhotoLayout mPhotosSnpl;
    @BindView(R.id.time1_edit)
    TextView time1_edit;
    @BindView(R.id.time2_edit)
    TextView time2_edit;
    @BindView(R.id.select_numbers_tv)
    TextView select_numbers_tv;
    @BindView(R.id.delete_icon_btn)
    ImageView delete_icon_btn;
    @BindView(R.id.logo_image)
    ImageView logo_image;
    @BindView(R.id.next_btn)
    LinearLayout next_btn;
    @BindView(R.id.changguan_name)
    EditText changguan_name;
    @BindView(R.id.edittext_area)
    EditText edittext_area;
    @BindView(R.id.tell_edit)
    EditText tell_edit;
    @BindView(R.id.edit_email)
    EditText edit_email;
    @BindView(R.id.edit_address)
    EditText edit_address;
    @BindView(R.id.sheng_tv)
    TextView sheng_tv;
    @BindView(R.id.shi_tv)
    TextView shi_tv;
    @BindView(R.id.qu_tv)
    TextView qu_tv;
    @BindView(R.id.select_changguan_type_btn)
    RelativeLayout select_changguan_type_btn;
    @BindView(R.id.select_type_tv)
    TextView select_type_tv;
    @BindView(R.id.tag_grid_view)
    MyGridView tag_grid_view;
    @BindView(R.id.add_room_btn)
    LinearLayout add_room_btn;
    @BindView(R.id.room_recyclerview)
    RecyclerView room_recyclerview;

    private View view;
    private StepView stepView;
    private LinearLayoutManager linearLayoutManager;
    private TimePicker picker;
    private ExRecyclerAdapter exRecyclerAdapter;
    private ArrayList<ItemBean> datas;
    private int max_num = 0;
    private String icon_image_path = "";
    private List<String> strings = new ArrayList<>();
    private NoScrollViewPager viewpager_content;
    private String changguan_type = "";
    private Subscription subscription;//Rxjava
    private static String icon_net_path = "";
    private List<BiaoqianEntity> biaoqianEntities = new ArrayList<>();
    private List<String> jiugongge_iamges = new ArrayList<>();
    private ProgressUtil progress_upload;
    private InformationCheckActivity mainActivity;
    OptionsPickerView select_city_pop;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<String> options2Items = new ArrayList<>();
    private ArrayList<JsonBean.CityBean> options2Items_codes = new ArrayList<>();
    private ArrayList<String> options3Items = new ArrayList<>();
    private ArrayList<JsonBean.QuBean> options3Items_codes = new ArrayList<>();
    private Thread thread;
    TextView top_title_tv;
    private String select_sheng_code = "";
    private String select_shi_code = "";
    private String select_qu_code = "";
    private int select_province_position = 0;
    private int select_city_position = 0;
    private List<DictionaryeBean> changguan_types = new ArrayList<>();
    private List<DictionaryeBean> information_tags = new ArrayList<>();
    private List<DictionaryeBean> room_types = new ArrayList<>();
    private List<InformationEntity.GymPlacesBean> select_rooms = new ArrayList<>();
    private BaseInformationTagAdapter tagAdapter;
    private String select_room_type_code = "";
    private SelectRoomAdapter selectRoomAdapter;
    private int startH = 9, startM = 0;
    private int endH = 23, endM = 59;
    private String crop_icon_path;
    /**
     * 七牛云
     **/
    //指定upToken, 强烈建议从服务端提供get请求获取
    private String uptoken = "xxxxxxxxx:xxxxxxx:xxxxxxxxxx";
    private SimpleDateFormat sdf;
    private String qiniu_key;
    private UploadManager uploadManager;

    /**
     * 七牛云
     **/

    public static BaseInformationFragment homeInstance(String title) {
        BaseInformationFragment fragment = new BaseInformationFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_base_information, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            /**七牛云**/
            uploadManager = MyApplication.uploadManager;
            sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            qiniu_key = "icon_" + sdf.format(new Date());
            initView();
            getToken();//获取七牛云token
        }
        return view;
    }

    private void initCityDate(boolean is_refresh_start) {
        Map<String, Object> params = new HashMap<>();
        params.put("province", "1");
        Subscription subscription = Network.getInstance("获取省", getActivity())
                .get_province(params,
                        new ProgressSubscriber<>("获取省", new SubscriberOnNextListener<Bean<CityCode>>() {
                            @Override
                            public void onNext(Bean<CityCode> result) {
                                for (int i = 0; i < result.getData().getProvince().size(); i++) {
                                    JsonBean jsonBean = new JsonBean();
                                    jsonBean.setName(result.getData().getProvince().get(i).getPrvncnm());
                                    jsonBean.setName_code(result.getData().getProvince().get(i).getPrvnccd());
                                    options1Items.add(jsonBean);
                                }
                                select_sheng_code = options1Items.get(0).getName_code();
                                initCityDate2(select_sheng_code, is_refresh_start);

                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), true));
    }

    private void initCityDate2(String select_sheng_code, boolean is_refresh_start) {
        if (options2Items_codes.size() > 0) {
            options2Items_codes.clear();
        }
        if (options2Items.size() > 0) {
            options2Items.clear();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("prvnccd", select_sheng_code);
        Subscription subscription = Network.getInstance("获取市", getActivity())
                .get_city(params,
                        new ProgressSubscriber<>("获取市", new SubscriberOnNextListener<Bean<GetCityCode>>() {
                            @Override
                            public void onNext(Bean<GetCityCode> result) {
                                for (int i = 0; i < result.getData().getCity().size(); i++) {
                                    JsonBean.CityBean jsonBean = new JsonBean.CityBean();
                                    jsonBean.setName(result.getData().getCity().get(i).getCitynm());
                                    jsonBean.setName_code(result.getData().getCity().get(i).getCitycd());
                                    options2Items_codes.add(jsonBean);
                                    options2Items.add(result.getData().getCity().get(i).getCitynm());
                                }
                                select_shi_code = options2Items_codes.get(0).getName_code();
                                select_city_position = 0;
                                if (null != select_city_pop) {
                                    select_city_pop.setSelectOptions(select_province_position, select_city_position);
                                    select_city_pop.setNPicker(options1Items, options2Items, options3Items);//二级选择器
                                }
                                initCityDate3(select_shi_code, is_refresh_start);
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), true));
    }

    private void initCityDate3(String select_shi_code, boolean is_refresh_start) {
        if (options3Items.size() > 0) {
            options3Items.clear();
        }
        if (options3Items_codes.size() > 0) {
            options3Items_codes.clear();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("citycd", select_shi_code);
        Subscription subscription = Network.getInstance("获取区", getActivity())
                .get_qu(params,
                        new ProgressSubscriber<>("获取区", new SubscriberOnNextListener<Bean<GetQuCode>>() {
                            @Override
                            public void onNext(Bean<GetQuCode> result) {

                                for (int i = 0; i < result.getData().getArea().size(); i++) {
                                    JsonBean.QuBean jsonBean = new JsonBean.QuBean();
                                    jsonBean.setName(result.getData().getArea().get(i).getDistnm());
                                    jsonBean.setName_code(result.getData().getArea().get(i).getDistcd());
                                    options3Items.add(result.getData().getArea().get(i).getDistnm());
                                    options3Items_codes.add(jsonBean);
                                }
                                if (null != select_city_pop) {
                                    select_city_pop.setSelectOptions(select_province_position, select_city_position);
                                    select_city_pop.setNPicker(options1Items, options2Items, options3Items);//二级选择器
                                }
                                if (is_refresh_start) {//如果是打开pop
                                    select_address_pop();
                                }


                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), true));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        AppConstants.SELECT_IMAGES_SIZE = 0;

    }

    private void initView() {
        //设置九宫格控件
        mPhotosSnpl.setDelegate(this);
        //选择场馆类型
        get_changguan_types();
        select_changguan_type_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_changguan_types_pop();
            }
        });

        //设置时间选择器
        set_time_select();

        //添加场馆icon
        set_icon_image();

        //获取标签
        get_tags();
        tag_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (information_tags.get(i).isCheck()) {
                    information_tags.get(i).setCheck(false);
                } else {
                    information_tags.get(i).setCheck(true);
                }
                tagAdapter.notifyDataSetChanged();
            }
        });


        //点击下一步
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jiugongge_iamges.clear();
                if (check_value()) {
                    //七牛云
                    progress_upload = new ProgressUtil();
                    progress_upload.showProgressDialog(getActivity(), "上传中...");
                    //上传icon
                    upload_icon_image(icon_image_path, false);
                    if (jiugongge_iamges.size() > 0) {
                        jiugongge_iamges.clear();
                    }
                    //上传九宫格
                    for (int i = 0; i < strings.size(); i++) {
                        upload_icon_image(strings.get(i), true);
                    }
                } else {
                    return;
                }


            }
        });

        sheng_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BaseUtils.isFastClick()) {
                    initCityDate(true);
                }
            }
        });
        shi_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BaseUtils.isFastClick()) {
                    initCityDate(true);
                }
            }
        });
        qu_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BaseUtils.isFastClick()) {
                    initCityDate(true);
                }
            }
        });


        //添加房间类型
        get_room_type();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        room_recyclerview.setLayoutManager(linearLayoutManager);
        selectRoomAdapter = new SelectRoomAdapter(getActivity());
        room_recyclerview.setAdapter(selectRoomAdapter);
        selectRoomAdapter.setData(select_rooms);
        add_room_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BaseUtils.isFastClick()) {
                    if (select_rooms.size() < 10) {
                        add_room_pop();
                    } else {
                        Toast.makeText(getActivity(), R.string.alert_dialog_tishi35, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void get_room_type() {
        Map<String, Object> params = new HashMap<>();
        params.put("object", "room_type");
        Subscription subscription = Network.getInstance("获取房间类型", getActivity())
                .get_types(params,
                        new ProgressSubscriber<>("获取房间类型", new SubscriberOnNextListener<Bean<List<DictionaryeBean>>>() {
                            @Override
                            public void onNext(Bean<List<DictionaryeBean>> result) {
                                if (room_types.size() > 0) {
                                    room_types.clear();
                                }
                                room_types.addAll(result.getData());

                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), false));
    }

    private void add_room_pop() {
        new XPopup.Builder(getActivity())
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .autoOpenSoftInput(false)
                .dismissOnTouchOutside(true)
                .asCustom(new CenterPopupView(getActivity(), R.layout.add_room_pop_layout, new ViewCallBack() {
                    @Override
                    public void onReturnView(View view, BasePopupView popup) {
                        RelativeLayout select_room_type_btn = view.findViewById(R.id.select_room_type_btn);
                        TextView select_room_type_tv = view.findViewById(R.id.select_room_type_tv);
                        EditText room_name = view.findViewById(R.id.room_name);
                        EditText room_people_number = view.findViewById(R.id.room_people_number);

                        view.findViewById(R.id.cancel_layout).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popup.dismiss();
                            }
                        });

                        InformationEntity.GymPlacesBean itemBean = new InformationEntity.GymPlacesBean();
                        view.findViewById(R.id.sure_layout).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (select_room_type_tv.getText().length() == 0) {
                                    Toast.makeText(getActivity(), R.string.alert_dialog_tishi32, Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (TextUtils.isEmpty(room_name.getText())) {
                                    Toast.makeText(getActivity(), R.string.alert_dialog_tishi33, Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (TextUtils.isEmpty(room_people_number.getText())) {
                                    Toast.makeText(getActivity(), R.string.alert_dialog_tishi34, Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    itemBean.setMax_num(Integer.valueOf(room_people_number.getText().toString()));
                                    itemBean.setPlace_type(select_room_type_code);//设置房间类型
                                    itemBean.setPlace_name(room_name.getText().toString());
                                    select_rooms.add(itemBean);
                                    selectRoomAdapter.notifyDataSetChanged();
                                    popup.dismiss();
                                }

                            }
                        });
                        select_room_type_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                select_room_type_pop(select_room_type_btn, select_room_type_tv);
                            }
                        });
                    }

                })).show();

    }


    private void select_room_type_pop(RelativeLayout select_room_type_btn, TextView select_room_type_tv) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(getActivity())
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
        for (int i = 0; i < room_types.size(); i++) {
            strings.add(room_types.get(i).getName());
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        SelectTypeAdapter typeAdapter = new SelectTypeAdapter(strings, getActivity());
        RecyclerView listView = view.findViewById(R.id.listview);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener(new SelectTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                select_room_type_tv.setText(strings.get(position));
                select_room_type_code = room_types.get(position).getValue();
                popupWindow.dismiss();
            }
        });
    }


    private void get_tags() {
        Map<String, Object> params = new HashMap<>();
        params.put("object", "1");
        Subscription subscription = Network.getInstance("获取标签类型", getActivity())
                .get_types(params,
                        new ProgressSubscriber<>("获取标签类型", new SubscriberOnNextListener<Bean<List<DictionaryeBean>>>() {
                            @Override
                            public void onNext(Bean<List<DictionaryeBean>> result) {
                                if (information_tags.size() > 0) {
                                    information_tags.clear();
                                }
                                information_tags.addAll(result.getData());
                                tagAdapter = new BaseInformationTagAdapter(getActivity(), information_tags);
                                tag_grid_view.setAdapter(tagAdapter);
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), false));
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

    private void upload_icon_image(String image_path, boolean is_jiugognge) {
        Luban.with(getActivity())
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
                    jiugonggeCallBack.onSucceed(file.getAbsolutePath());
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

    ImageCompressCallBack jiugonggeCallBack = new ImageCompressCallBack() {
        @Override
        public void onSucceed(String data) {
//            Log.e("压缩过的",data);
//            File file = new File(data);
//            Log.e("压缩后的大小", FileSizeUtil.getFileOrFilesSize(file.getAbsolutePath(), 2) + "");
            upload_jiugongge_work(data);
        }

        @Override
        public void onFailure(String msg) {
            Log.e("压缩失败的", msg);
            progress_upload.dismissProgressDialog();
            progress_upload = null;
        }
    };

    private void upload_jiugongge_work(String image_path) {
        String expectKey = UUID.randomUUID().toString();
        uploadManager.put(image_path, expectKey, uptoken, new UpCompletionHandler() {
            public void complete(String k, ResponseInfo rinfo, JSONObject response) {
                if (rinfo.isOK()) {
                    Log.e("qiniu", "九宫格上传成功");
                    //String key = getKey(k, response);
                    //String s = k + ", "+ rinfo + ", " + response;
                    //Log.e("获取到的key", "获取到的key:" + k);
                    jiugongge_iamges.add(k);
                    if (jiugongge_iamges.size() == strings.size()) {

                        progress_upload.dismissProgressDialog();
                        progress_upload = null;

                        mainActivity.informationEntity = getDates();
                        //跳转下一个页面
                        viewpager_content.setCurrentItem(1);
                        mainActivity.select_index = 1;
                        top_title_tv.setText("营业资料");
                        int step = stepView.getCurrentStep();//设置进度条
                        stepView.setCurrentStep((step + 1) % stepView.getStepNum());
                    }
                } else {
                    progress_upload.dismissProgressDialog();
                    progress_upload = null;
                    Log.e("qiniu", "上传九宫格失败");
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
            }
        }, new UploadOptions(null, "test-type", true, null, null));
    }


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
                        } else {
                            Log.e("qiniu", "Upload Fail");
                            progress_upload.dismissProgressDialog();
                            progress_upload = null;
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        //Log.e("qiniu", key + ",\r\n " + info.path + ",\r\n " + response);
                    }
                }, new UploadOptions(null, "test-type", true, null, null));
    }

    /**
     * 选择城市pop
     */
    private void select_address_pop() {
        showPickerView();
        //影藏键盘
        KeyboardUtils.hideSoftKeyboard(getActivity());
    }

    private void showPickerView() {
        //清除缓存
        select_city_pop = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.size() > 0 ?
                        options2Items.get(option2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.size() > 0
                        && options3Items.size() > 0 ?
                        options3Items.get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;
                sheng_tv.setText(opt1tx);
                shi_tv.setText(opt2tx);
                qu_tv.setText(opt3tx);

                //Log.e("选择的地址", tx);
                //Log.e("选择的地址编码", select_sheng_code + "-"+select_shi_code+"-"+select_qu_code);

            }
        })
                .setLayoutRes(R.layout.select_city_pop, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView quxiao_btn = (TextView) v.findViewById(R.id.quxiao_btn);
                        TextView sure_btn = (TextView) v.findViewById(R.id.sure_btn);
                        sure_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                select_city_pop.returnData();
                                select_city_pop.dismiss();
                            }
                        });
                        quxiao_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                select_city_pop.dismiss();
                            }
                        });
                    }
                })
                .setBgColor(Color.parseColor("#00000000"))
                .setDividerColor(Color.parseColor("#00000000"))
                .setContentTextSize(20)
                .setOutSideCancelable(true)
                .setLineSpacingMultiplier(2.0f)
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1) {
                        select_province_position = options1;
                        select_sheng_code = options1Items.get(options1).getName_code();
                        initCityDate2(select_sheng_code, false);


                    }

                    @Override
                    public void onOptionsSelectChanged2(int options2) {
                        select_city_position = options2;
                        select_shi_code = options2Items_codes.get(options2).getName_code();
                        initCityDate3(select_shi_code, false);
                    }

                    @Override
                    public void onOptionsSelectChanged3(int options3) {
                        select_qu_code = options3Items_codes.get(options3).getName_code();
                    }

                    @Override
                    public void onOptionsSelectChanged3(int options1, int options2, int options3) {

                    }

                })
                .build();
        select_city_pop.setNPicker(options1Items, options2Items, options3Items);//二级选择器
        select_city_pop.show();
    }

    public InformationEntity getDates() {
        InformationEntity informationEntity = new InformationEntity();
        informationEntity.setArea_name(changguan_name.getText().toString());//场馆名称
        if (null != changguan_type && changguan_type.length() > 0) {
            informationEntity.setType(Integer.valueOf(changguan_type));//场馆类型
        }
        informationEntity.setArea(Integer.valueOf(edittext_area.getText().toString()));//场馆面积
        informationEntity.setPhone(tell_edit.getText().toString());//电话号码
        informationEntity.setEmail(edit_email.getText().toString());//邮箱
        informationEntity.setBusiness_start(time1_edit.getText().toString());//营业开始时间
        informationEntity.setBusiness_end(time2_edit.getText().toString());//营业结束时间
        informationEntity.setGymPlaces(select_rooms);//功能性场所
        informationEntity.setFacility(get_selete_biaoqian());//营业标签
        //设置icon和九宫格图片
        List<InformationEntity.GymPicBean> gym_pic = new ArrayList<>();
        InformationEntity.GymPicBean icon_pic = new InformationEntity.GymPicBean();
        icon_pic.setOrder_num(1);
        icon_pic.setQiniu_key(icon_net_path);
        gym_pic.add(icon_pic);
        for (int i = 0; i < jiugongge_iamges.size(); i++) {
            InformationEntity.GymPicBean jiugongge_icon = new InformationEntity.GymPicBean();
            jiugongge_icon.setOrder_num((i + 2));
            jiugongge_icon.setQiniu_key(jiugongge_iamges.get(i));
            gym_pic.add(jiugongge_icon);
        }
        informationEntity.setGym_pic(gym_pic);//设置九宫格图片

        //设置省市区
        informationEntity.setAddress(edit_address.getText().toString());//地址
        informationEntity.setProvince(sheng_tv.getText().toString());
        informationEntity.setCity(shi_tv.getText().toString());
        informationEntity.setDistrict(qu_tv.getText().toString());
        informationEntity.setProvinceCode(select_sheng_code);
        informationEntity.setCityCode(select_shi_code);
        informationEntity.setDistrictCode(select_qu_code);

        //设置选择的房间名称
        return informationEntity;
    }


    private boolean check_value() {
        if (TextUtils.isEmpty(changguan_name.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi3, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edittext_area.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi4, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(select_type_tv.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi41, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(tell_edit.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi5, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edit_email.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi6, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!StringsHelper.isEmail(edit_email.getText().toString())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi30, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(sheng_tv.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi29, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edit_address.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi7, Toast.LENGTH_SHORT).show();
            return false;
        } else if (icon_image_path.length() == 0) {//icon地址
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi8, Toast.LENGTH_SHORT).show();
            return false;
        } else if (strings.size() == 0) {//场馆照片
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi9, Toast.LENGTH_SHORT).show();
            return false;
        } else if (get_selete_biaoqian().length() == 0) {//标签
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi10, Toast.LENGTH_SHORT).show();
            return false;
        } else if (select_rooms.size() == 0) {//功能性场所
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi11, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }


    }

    private void set_icon_image() {
        logo_image.setOnClickListener(new View.OnClickListener() {//添加图片
            @Override
            public void onClick(View view) {
                EasyPhotos.createAlbum(BaseInformationFragment.this, true, GlideEngine.getInstance())
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
                Glide.with(getActivity()).load(R.drawable.jia_image).into(logo_image);
            }
        });
    }

    private void set_time_select() {
        time1_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_check(time1_edit, 1);
            }
        });

        time2_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_check(time2_edit, 2);
            }
        });
    }

    private void get_changguan_types() {
        Map<String, Object> params = new HashMap<>();
        params.put("object", "gymtype");
        Subscription subscription = Network.getInstance("获取场馆类型", getActivity())
                .get_types(params,
                        new ProgressSubscriber<>("获取场馆类型", new SubscriberOnNextListener<Bean<List<DictionaryeBean>>>() {
                            @Override
                            public void onNext(Bean<List<DictionaryeBean>> result) {
                                if (changguan_types.size() > 0) {
                                    changguan_types.clear();
                                }
                                changguan_types.addAll(result.getData());
                                //首次进来默认选择综合场馆

                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), false));
    }


    private void select_changguan_types_pop() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_changguan_type_btn.getWidth(),
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(select_changguan_type_btn);
        /**设置逻辑*/
        View view = popupWindow.getContentView();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < changguan_types.size(); i++) {
            strings.add(changguan_types.get(i).getName());
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        SelectTypeAdapter typeAdapter = new SelectTypeAdapter(strings, getActivity());
        RecyclerView listView = view.findViewById(R.id.listview);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener(new SelectTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                select_type_tv.setText(strings.get(position));
                changguan_type = changguan_types.get(position).getValue();
                //Log.e("选择的场馆类型ID", changguan_type + "");
                popupWindow.dismiss();
            }
        });
    }

    private String get_selete_biaoqian() {
        StringBuffer type_buffer = new StringBuffer();
        for (int i = 0; i < information_tags.size(); i++) {
            if (information_tags.get(i).isCheck()) {
                type_buffer.append(information_tags.get(i).getValue()).append(",");
            }
        }
        //Log.e("选择的标签编号", type_buffer.substring(0, type_buffer.length() - 1) + "");
        return type_buffer.substring(0, type_buffer.length() - 1);
    }

    private void getToken() {
        subscription = Network.getInstance("登录", getActivity())
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
                }, getActivity(), true));

    }


    private void time_check(TextView textView, int type) {
        picker = new TimePicker(getActivity(), TimeMode.HOUR_24);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        picker.setDefaultValue(new TimeEntity(hour, minute));
        picker.showAtBottom();
        picker.setOnTimeSelectedListener(new OnTimeSelectedListener() {
            @Override
            public void onItemSelected(int hour, int minute, int second) {
                if (type == 1) {
                    startH = hour;
                    startM = minute;
                    if (hour > endH) {
                        Toast.makeText(getActivity(), "开始时间不能大于结束时间",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (hour == endH && minute > endM) {
                        Toast.makeText(getActivity(), "开始时间不能大于结束时间",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (type == 2) {
                    if (hour < startH) {
                        Toast.makeText(getActivity(), "开始时间不能大于结束时间",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (hour == startH && minute < startM) {
                        Toast.makeText(getActivity(), "开始时间不能大于结束时间",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    endH = hour;
                    endM = minute;
                }
                if (minute <= 9) {
                    textView.setText(hour + ":0" + minute);
                } else {
                    textView.setText(hour + ":" + minute);
                }
            }
        });
    }


    @Override
    public void onAttach(Context activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if (activity instanceof InformationCheckActivity) {
            mainActivity = (InformationCheckActivity) activity;
            top_title_tv = mainActivity.findViewById(R.id.top_title_tv);
            stepView = mainActivity.findViewById(R.id.sv);
            viewpager_content = mainActivity.findViewById(R.id.viewpager_content);
        }
    }


    @Override
    public void fetchData() {

    }

    /**
     * 点击控件的加号按钮
     *
     * @param sortableNinePhotoLayout
     * @param view
     * @param position
     * @param models
     */
    @Override
    public void onClickAddNinePhotoItem(CCRSortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        //设置最多只能上传9张图片
        if (AppConstants.SELECT_IMAGES_SIZE >= 9) {
            Toast.makeText(getActivity(), "只能上传9张图片哦～", Toast.LENGTH_SHORT).show();
        } else {
            max_num = 9 - AppConstants.SELECT_IMAGES_SIZE;
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
        AppConstants.SELECT_IMAGES_SIZE = AppConstants.SELECT_IMAGES_SIZE - 1;
        select_numbers_tv.setText(AppConstants.SELECT_IMAGES_SIZE + "/9");
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
                AppConstants.SELECT_IMAGES_SIZE = strings.size();
                select_numbers_tv.setText(AppConstants.SELECT_IMAGES_SIZE + "/9");
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
                String cropImagePath = FileUtil.getRealFilePathFromUri(getActivity(), uri);
                //Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                File icon_iamge_file = new File(cropImagePath);
                Glide.with(getActivity()).load(icon_iamge_file).into(logo_image);
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
        intent.setClass(getActivity(), ClipImageActivity.class);
        intent.putExtra("type", 2);//1:圆形 2:方形
        intent.setData(uri);
        startActivityForResult(intent, 103);
    }
}
