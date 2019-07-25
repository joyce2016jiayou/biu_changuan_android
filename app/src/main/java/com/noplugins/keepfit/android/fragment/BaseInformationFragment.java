package com.noplugins.keepfit.android.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.activity.InformationCheckActivity;
import com.noplugins.keepfit.android.activity.LoginActivity;
import com.noplugins.keepfit.android.activity.UserPermissionSelectActivity;
import com.noplugins.keepfit.android.adapter.CameraSelectAdapter;
import com.noplugins.keepfit.android.adapter.ExRecyclerAdapter;
import com.noplugins.keepfit.android.base.MyApplication;
import com.noplugins.keepfit.android.entity.BiaoqianEntity;
import com.noplugins.keepfit.android.entity.InformationEntity;
import com.noplugins.keepfit.android.entity.ItemBean;
import com.noplugins.keepfit.android.entity.LoginEntity;
import com.noplugins.keepfit.android.entity.QiNiuToken;
import com.noplugins.keepfit.android.entity.TypeItemEntity;
import com.noplugins.keepfit.android.entity.UrlEntity;
import com.noplugins.keepfit.android.resource.ValueResources;
import com.noplugins.keepfit.android.util.GlideEngine;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.RxUtils;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressHUD;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.NoScrollViewPager;
import com.noplugins.keepfit.android.util.ui.StepView;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;
import com.noplugins.keepfit.android.util.ui.jiugongge.CCRSortableNinePhotoLayout;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.wheelpicker.TimePicker;
import cn.qqtheme.framework.wheelview.annotation.TimeMode;
import cn.qqtheme.framework.wheelview.contract.OnTimeSelectedListener;
import cn.qqtheme.framework.wheelview.entity.TimeEntity;
import lib.demo.spinner.MaterialSpinner;
import okhttp3.RequestBody;
import rx.Subscription;
import rx.functions.Action1;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class BaseInformationFragment extends ViewPagerFragment implements CCRSortableNinePhotoLayout.Delegate {
    /**
     * 拖拽排序九宫格控件
     */
    @BindView(R.id.snpl_moment_add_photos)
    CCRSortableNinePhotoLayout mPhotosSnpl;
    @BindView(R.id.spinner_type)
    MaterialSpinner spinner_type;
    @BindView(R.id.time1_edit)
    TextView time1_edit;
    @BindView(R.id.time2_edit)
    TextView time2_edit;
    @BindView(R.id.rc_view)
    RecyclerView rc_view;
    @BindView(R.id.select_numbers_tv)
    TextView select_numbers_tv;
    @BindView(R.id.delete_icon_btn)
    ImageView delete_icon_btn;
    @BindView(R.id.logo_image)
    ImageView logo_image;
    @BindView(R.id.next_btn)
    TextView next_btn;
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
    @BindView(R.id.checkbox_youyang)
    CheckBox checkbox_youyang;
    @BindView(R.id.checkbox_wuyang)
    CheckBox checkbox_wuyang;
    @BindView(R.id.checkbox_tuancao)
    CheckBox checkbox_tuancao;
    @BindView(R.id.checkbox_danche)
    CheckBox checkbox_danche;
    @BindView(R.id.checkbox_youyong)
    CheckBox checkbox_youyong;
    @BindView(R.id.checkbox_wifi)
    CheckBox checkbox_wifi;
    @BindView(R.id.checkbox_genyi)
    CheckBox checkbox_genyi;
    @BindView(R.id.checkbox_linyu)
    CheckBox checkbox_linyu;
    @BindView(R.id.checkbox_cesuo)
    CheckBox checkbox_cesuo;
    private View view;
    private StepView stepView;
    private LinearLayoutManager linearLayoutManager;
    private TimePicker picker;
    private ExRecyclerAdapter exRecyclerAdapter;
    private ArrayList<ItemBean> datas;
    private int max_num = 0;
    private String icon_image_path;
    private List<String> strings = new ArrayList<>();
    private NoScrollViewPager viewpager_content;
    private String changguan_type = "";
    private Subscription subscription;//Rxjava
    private String icon_net_path = "";
    List<BiaoqianEntity> biaoqianEntities = new ArrayList<>();
    List<String> jiugongge_iamges = new ArrayList<>();

    /**
     * 七牛云
     **/
    //指定upToken, 强烈建议从服务端提供get请求获取, 这里为了掩饰直接指定key
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
            select_biaoqian();


        }
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ValueResources.select_iamges_size = 0;

    }

    private void initView() {
        String[] typeArrays = getResources().getStringArray(R.array.identify_types);
        spinner_type.setItems(typeArrays);
        spinner_type.setSelectedIndex(0);
        spinner_type.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                changguan_type = item;
            }
        });
        spinner_type.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                spinner.getSelectedIndex();
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

        //设置视图添加
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rc_view.setLayoutManager(linearLayoutManager);
        rc_view.setNestedScrollingEnabled(false);//禁止滑动
        datas = new ArrayList<>();
        exRecyclerAdapter = new ExRecyclerAdapter(getActivity(), datas, R.layout.item);
        exRecyclerAdapter.addData(new ItemBean());
        rc_view.setAdapter(exRecyclerAdapter);
        //设置九宫格控件
        //设置拖拽排序控件的代理
        mPhotosSnpl.setDelegate(this);
        //添加场馆icon
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

        //点击下一步
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                //跳转下一个页面
//                viewpager_content.setCurrentItem(1);
//                //设置进度条
//                int step = stepView.getCurrentStep();
//                stepView.setCurrentStep((step + 1) % stepView.getStepNum());


                /**七牛云**/
                Log.e("qiniutest", "starting......");

                for (int i = 0; i < strings.size(); i++) {
                    String expectKey = UUID.randomUUID().toString();
                    uploadManager.put(strings.get(i), expectKey, uptoken, new UpCompletionHandler() {
                        public void complete(String k, ResponseInfo rinfo, JSONObject response) {
                            if (rinfo.isOK()) {
                                Log.e("qiniu", "Upload Success");
//                                String key = getKey(k, response);
                                // String s = k + ", "+ rinfo + ", " + response;
                                Log.e("获取到的key", "获取到的key:" + k);
                                jiugongge_iamges.add(k);
                                if (jiugongge_iamges.size() == strings.size()) {

                                }
                            } else {
                                Log.e("qiniu", "Upload Fail");
                                //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                            }
                        }
                    }, new UploadOptions(null, "test-type", true, null, null));
                }
                /**七牛云**/

                //上传icon
//                uploadManager.put(icon_image_path, qiniu_key, uptoken,
//                        new UpCompletionHandler() {
//                            @Override
//                            public void complete(String key, ResponseInfo info, JSONObject response) {
//                                //res包含hash、key等信息，具体字段取决于上传策略的设置
//                                if(info.isOK()) {
//                                    Log.e("qiniu", "Upload Success");
//                                    icon_net_path = key;
//                                    Log.e("打印key：",icon_net_path);
//                                    //测试资料上传的
//                                    //getUrlTest(icon_net_path);
//                                    String headpicPath = "http://upload.qiniup.com/" + key;
//                                    Log.e("返回的地址", headpicPath);
//                                } else {
//                                    Log.e("qiniu", "Upload Fail");
//                                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
//                                }
//                                //Log.e("qiniu", key + ",\r\n " + info.path + ",\r\n " + response);
//                            }
//                        }, new UploadOptions(null, "test-type", true, null, null));


                //上传进度
                /*uploadManager.put(icon_image_path, "img", uptoken,handler,
                        new UploadOptions(null, null, false,
                                new UpProgressHandler(){
                                    public void progress(String key, double percent){
                                        Log.i("qiniu", key + ": " + percent);
                                    }
                                }, null));
                //取消上传
                private volatile boolean isCancelled = false;
                uploadManager.put(icon_image_path, "img", uptoken,handler,
                        new UploadOptions(null, null, false, progressHandler,
                                new UpCancellationSignal(){
                                    public boolean isCancelled(){
                                        return isCancelled;
                                    }
                                }));
                // 点击取消按钮，让UpCancellationSignal##isCancelled()方法返回true，以停止上传
                private void cancell() {
                    isCancelled = true;
                }*/


            }
        });

        getToken();
    }

    private String get_selete_biaoqian() {
        StringBuffer type_buffer = new StringBuffer();
        for (int i = 0; i < biaoqianEntities.size(); i++) {
            if (i == biaoqianEntities.size() - 1) {
                type_buffer.append(biaoqianEntities.get(i).getNumber());
            } else {
                type_buffer.append(biaoqianEntities.get(i).getNumber()).append(",");
            }
        }
        //Log.e("选择的标签编号", image_buffer.toString() + "");
        return type_buffer.toString();
    }

    private BiaoqianEntity biaoqianEntity = new BiaoqianEntity();
    private BiaoqianEntity biaoqianEntity1 = new BiaoqianEntity();
    private BiaoqianEntity biaoqianEntity2 = new BiaoqianEntity();
    private BiaoqianEntity biaoqianEntity3 = new BiaoqianEntity();
    private BiaoqianEntity biaoqianEntity4 = new BiaoqianEntity();
    private BiaoqianEntity biaoqianEntity5 = new BiaoqianEntity();
    private BiaoqianEntity biaoqianEntity6 = new BiaoqianEntity();
    private BiaoqianEntity biaoqianEntity7 = new BiaoqianEntity();
    private BiaoqianEntity biaoqianEntity8 = new BiaoqianEntity();

    private void select_biaoqian() {
        checkbox_youyang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    biaoqianEntity.setIndex(1);
                    biaoqianEntity.setNumber(1);
                    biaoqianEntities.add(biaoqianEntity);
                } else {

                    biaoqianEntities.remove(biaoqianEntity);

                }
            }
        });
        checkbox_wuyang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    biaoqianEntity1.setIndex(2);
                    biaoqianEntity1.setNumber(2);
                    biaoqianEntities.add(biaoqianEntity1);
                } else {
                    biaoqianEntities.remove(biaoqianEntity1);
                }
            }
        });
        checkbox_tuancao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    biaoqianEntity2.setIndex(3);
                    biaoqianEntity2.setNumber(3);
                    biaoqianEntities.add(biaoqianEntity2);
                } else {
                    biaoqianEntities.remove(biaoqianEntity2);
                }
            }
        });
        checkbox_danche.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    biaoqianEntity3.setIndex(4);
                    biaoqianEntity3.setNumber(4);
                    biaoqianEntities.add(biaoqianEntity3);
                } else {
                    biaoqianEntities.remove(biaoqianEntity3);
                }
            }
        });
        checkbox_youyong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    biaoqianEntity4.setIndex(5);
                    biaoqianEntity4.setNumber(5);
                    biaoqianEntities.add(biaoqianEntity4);
                } else {
                    biaoqianEntities.remove(biaoqianEntity4);
                }
            }
        });
        checkbox_wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    biaoqianEntity5.setIndex(6);
                    biaoqianEntity5.setNumber(6);
                    biaoqianEntities.add(biaoqianEntity5);
                } else {
                    biaoqianEntities.remove(biaoqianEntity5);
                }
            }
        });
        checkbox_genyi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    biaoqianEntity6.setIndex(7);
                    biaoqianEntity6.setNumber(7);
                    biaoqianEntities.add(biaoqianEntity6);
                } else {
                    biaoqianEntities.remove(biaoqianEntity6);
                }
            }
        });
        checkbox_linyu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    biaoqianEntity7.setIndex(8);
                    biaoqianEntity7.setNumber(8);
                    biaoqianEntities.add(biaoqianEntity7);
                } else {
                    biaoqianEntities.remove(biaoqianEntity7);
                }
            }
        });
        checkbox_cesuo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    biaoqianEntity8.setIndex(9);
                    biaoqianEntity8.setNumber(9);
                    biaoqianEntities.add(biaoqianEntity8);
                } else {
                    biaoqianEntities.remove(biaoqianEntity8);
                }
            }
        });


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

    private void getUrlTest(String key_value) {
        Map<String, String> params = new HashMap<>();
        params.put("key", key_value);
        subscription = Network.getInstance("获取url", getActivity())
                .get_qiniu_url(params, new ProgressSubscriberNew<>(UrlEntity.class, new GsonSubscriberOnNextListener<UrlEntity>() {
                    @Override
                    public void on_post_entity(UrlEntity urlEntity, String s) {
                        Log.e("获取到的url", "获取到的url" + urlEntity.getUrl());
                        uptoken = urlEntity.getUrl();

                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("获取到的url失败", error);
                    }
                }, getActivity(), true));
    }


    public InformationEntity getDates() {
        InformationEntity informationEntity = new InformationEntity();
        informationEntity.setArea_name(changguan_name.getText().toString());//场馆名称
        if (changguan_type.equals("综合会所")) {//场馆类型
            informationEntity.setType(1);
        } else {//工作室
            informationEntity.setType(2);
        }
        informationEntity.setArea(Integer.valueOf(edittext_area.getText().toString()));//场馆面积
        informationEntity.setPhone(Integer.valueOf(tell_edit.getText().toString()));//电话号码
        informationEntity.setEmail(edit_email.getText().toString());//邮箱
        informationEntity.setBusiness_start(time1_edit.getText().toString());//营业开始时间
        informationEntity.setBusiness_end(time2_edit.getText().toString());//营业结束时间
        informationEntity.setAddress(edit_address.getText().toString());//地址
        //获取选择的功能性场所类型
        List<InformationEntity.GymPlacesBean> gymPlacesBeans = new ArrayList<>();
        ArrayList<ItemBean> itemBeans = exRecyclerAdapter.getData();
        for (int i = 0; i < itemBeans.size(); i++) {
            InformationEntity.GymPlacesBean gymPlacesBean = new InformationEntity.GymPlacesBean();
            gymPlacesBean.setMax_num(Integer.valueOf(itemBeans.get(i).getPlace()));
            gymPlacesBean.setPlace_name(itemBeans.get(i).getType_name());
            gymPlacesBeans.add(gymPlacesBean);
//            Log.e("获取到的人数", "获取到的人数: " + itemBeans.get(i).getPlace());
//            Log.e("获取到的type", "获取到的type: " + itemBeans.get(i).getType_name());

        }
        informationEntity.setGymPlaces(gymPlacesBeans);//功能性场所
        informationEntity.setFacility(get_selete_biaoqian());//标签

        return informationEntity;
    }


    private void time_check(TextView textView) {
        picker = new TimePicker(getActivity(), TimeMode.HOUR_24);
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


    @Override
    public void onAttach(Context activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if (activity instanceof InformationCheckActivity) {
            InformationCheckActivity mainActivity = (InformationCheckActivity) activity;
            stepView = (StepView) mainActivity.findViewById(R.id.sv);
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
        if (ValueResources.select_iamges_size >= 9) {
            Toast.makeText(getActivity(), "只能上传9张图片哦～", Toast.LENGTH_SHORT).show();
        } else if (ValueResources.select_iamges_size < 9) {
            max_num = 9 - ValueResources.select_iamges_size;
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
        ValueResources.select_iamges_size = ValueResources.select_iamges_size - 1;

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
                ValueResources.select_iamges_size = strings.size();
                select_numbers_tv.setText(ValueResources.select_iamges_size + "/9");
                return;
            } else {//添加icon,上传icon
                ArrayList<String> resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
                if (resultPaths.size() > 0) {
                    icon_image_path = resultPaths.get(0);
                    File icon_iamge_file = new File(icon_image_path);
                    Glide.with(getActivity()).load(icon_iamge_file).into(logo_image);
                    delete_icon_btn.setVisibility(View.VISIBLE);
                }
            }


        } else if (RESULT_CANCELED == resultCode) {
            Log.e("关闭了相册", "关闭了相册");
            //Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
    }
}
