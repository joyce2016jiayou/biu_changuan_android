package com.noplugins.keepfit.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.ExRecyclerAdapter;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.base.MyApplication;
import com.noplugins.keepfit.android.bean.ChangguanBean;
import com.noplugins.keepfit.android.bean.UpPicBean;
import com.noplugins.keepfit.android.entity.BiaoqianEntity;
import com.noplugins.keepfit.android.entity.InformationEntity;
import com.noplugins.keepfit.android.entity.ItemBean;
import com.noplugins.keepfit.android.entity.QiNiuToken;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.resource.ValueResources;
import com.noplugins.keepfit.android.util.GlideEngine;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.TimeCheckUtil;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.NoScrollViewPager;
import com.noplugins.keepfit.android.util.ui.ProgressUtil;
import com.noplugins.keepfit.android.util.ui.StepView;
import com.noplugins.keepfit.android.util.ui.jiugongge.CCRSortableNinePhotoLayout;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.wheelpicker.TimePicker;
import cn.qqtheme.framework.wheelview.annotation.TimeMode;
import cn.qqtheme.framework.wheelview.contract.OnTimeSelectedListener;
import cn.qqtheme.framework.wheelview.entity.TimeEntity;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import lib.demo.spinner.MaterialSpinner;
import rx.Subscription;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class ChangGuandetailActivity extends BaseActivity implements CCRSortableNinePhotoLayout.Delegate {
    @BindView(R.id.back_btn)
    ImageView back_btn;
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
    @BindView(R.id.delete_icon_btn)
    ImageView delete_icon_btn;
    @BindView(R.id.logo_image)
    ImageView logo_image;
    @BindView(R.id.select_numbers_tv)
    TextView select_numbers_tv;
    @BindView(R.id.tv_complete)
    TextView tv_complete;


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
    @BindView(R.id.faren_name)
    EditText faren_name;
    @BindView(R.id.icon_id_card)
    EditText icon_id_card;
    @BindView(R.id.qiye_name)
    EditText qiye_name;
    @BindView(R.id.qiye_zhucehao)
    EditText qiye_zhucehao;

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
    private String icon_image_path = "";
    private List<String> strings = new ArrayList<>();
    private NoScrollViewPager viewpager_content;
    private String changguan_type = "";
    private Subscription subscription;//Rxjava
    private String icon_net_path = "";
    private List<BiaoqianEntity> biaoqianEntities = new ArrayList<>();
    private List<String> jiugongge_iamges = new ArrayList<>();
    private List<InformationEntity.GymPicBean> upList_iamges = new ArrayList<>();
    private ProgressUtil progress_upload;

    private String old_logo = "";

    /**
     * 七牛云
     **/
    //指定upToken, 强烈建议从服务端提供get请求获取
    private String uptoken = "xxxxxxxxx:xxxxxxx:xxxxxxxxxx";
    private SimpleDateFormat sdf;
    private String qiniu_key;
    private UploadManager uploadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_chang_guandetail);
        /**七牛云**/
        uploadManager = MyApplication.uploadManager;
        sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        qiniu_key = "icon_" + sdf.format(new Date());
        ButterKnife.bind(this);
        isShowTitle(false);

        //设置下拉选择框
        set_xiala_select();

        //设置时间选择器
        set_time_select();

        //设置九宫格控件
        set_jiugongge_view();

        //添加场馆icon
        set_icon_image();

        select_biaoqian();
        getToken();//获取七牛云token

        requestData();
    }

    @Override
    public void doBusiness(Context mContext) {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(3);
                finish();
            }
        });

        tv_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withLs();
            }
        });

    }

    private void requestData() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("areaNum", SpUtils.getString(getApplicationContext(), AppConstants.CHANGGUAN_NUM));
        subscription = Network.getInstance("场馆信息", this)
                .myArea(params, new ProgressSubscriber<ChangguanBean>("场馆信息",
                        new SubscriberOnNextListener<Bean<ChangguanBean>>() {
                            @Override
                            public void onNext(Bean<ChangguanBean> changguanBeanBean) {
                                setting(changguanBeanBean.getData());
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));
    }

    private void setting(ChangguanBean cg) {
        changguan_name.setText(cg.getArea().getAreaName());
        edit_address.setText(cg.getArea().getAddress());
        tell_edit.setText(cg.getArea().getPhone());
        edit_email.setText(cg.getArea().getEmail());

        faren_name.setText(cg.getArea().getLegalPerson());
        icon_id_card.setText(cg.getArea().getCardNum());
        qiye_name.setText(cg.getArea().getCompanyName());
        qiye_zhucehao.setText(cg.getArea().getCompanyCode());

        edittext_area.setText(cg.getArea().getArea() + "");
        spinner_type.setSelectedIndex(cg.getArea().getType()-1);
        spinner_type.setClickable(false);
        time1_edit.setText(TimeCheckUtil.removeSecond(cg.getArea().getBusinessStart()));
        time2_edit.setText(TimeCheckUtil.removeSecond(cg.getArea().getBusinessEnd()));

        datas.clear();
        for (int i = 0; i < cg.getPlace().size(); i++) {
            ItemBean itemBean = new ItemBean();
            itemBean.setPlace(cg.getPlace().get(i).getMaxNum() + "");
            itemBean.setType(cg.getPlace().get(i).getPlaceType());
            itemBean.setPlace_num(cg.getPlace().get(i).getPlaceNum());
            datas.add(itemBean);
        }
        exRecyclerAdapter.notifyDataSetChanged();

        old_logo = cg.getArea().getLogo();
        Glide.with(this)
                .load(cg.getArea().getLogo())
                .placeholder(R.drawable.logo_gray)
                .into(logo_image);
        delete_icon_btn.setVisibility(View.VISIBLE);

        for (int i = 0; i < cg.getPic().size(); i++) {
            strings.add(cg.getPic().get(i).getUrl());
            InformationEntity.GymPicBean bean = new InformationEntity.GymPicBean();
            bean.setQiniu_key(cg.getPic().get(i).getQiniuKey());
            bean.setOrder_num(i+2);
            upList_iamges.add(bean);
        }
        mPhotosSnpl.setData(strings);
        ValueResources.select_iamges_size = strings.size();
        select_numbers_tv.setText(ValueResources.select_iamges_size + "/9");

        String[] biaoqian = cg.getArea().getFacility().split(",");
        for (int i = 0; i < biaoqian.length; i++) {
            switch (biaoqian[i]) {
                case "1":
                    checkbox_youyang.setChecked(true);
                    break;
                case "2":
                    checkbox_wuyang.setChecked(true);
                    break;
                case "3":
                    checkbox_tuancao.setChecked(true);
                    break;
                case "4":
                    checkbox_danche.setChecked(true);
                    break;
                case "5":
                    checkbox_youyong.setChecked(true);
                    break;
                case "6":
                    checkbox_wifi.setChecked(true);
                    break;
                case "7":
                    checkbox_genyi.setChecked(true);
                    break;
                case "8":
                    checkbox_cesuo.setChecked(true);
                    break;

            }
        }


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


    private void set_xiala_select() {
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
    }

    private void set_time_select() {
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

    private void set_jiugongge_view() {
        //设置视图添加
        linearLayoutManager = new LinearLayoutManager(this);
        rc_view.setLayoutManager(linearLayoutManager);
        rc_view.setNestedScrollingEnabled(false);//禁止滑动
        datas = new ArrayList<>();
        exRecyclerAdapter = new ExRecyclerAdapter(this, datas, R.layout.item);
        exRecyclerAdapter.addData(new ItemBean());
        rc_view.setAdapter(exRecyclerAdapter);
        //设置拖拽排序控件的代理
        mPhotosSnpl.setDelegate(this);
    }

    private void set_icon_image() {
        logo_image.setOnClickListener(new View.OnClickListener() {//添加图片
            @Override
            public void onClick(View view) {
                EasyPhotos.createAlbum(ChangGuandetailActivity.this, true, GlideEngine.getInstance())
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
                old_logo = "";
                delete_icon_btn.setVisibility(View.INVISIBLE);
                Glide.with(ChangGuandetailActivity.this).load(R.drawable.jia_image).into(logo_image);
            }
        });
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
            Toast.makeText(this, "只能上传9张图片哦～", Toast.LENGTH_SHORT).show();
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
//        strings.remove(position);
        mPhotosSnpl.removeItem(position);
        Log.d("remove","strings.size():"+strings.size());
        if (position <= upList_iamges.size()-1 && upList_iamges.size() !=0){
            upList_iamges.remove(position);
        } else {
            jiugongge_iamges.remove(position+upList_iamges.size());
        }


        ValueResources.select_iamges_size = ValueResources.select_iamges_size - 1;
        select_numbers_tv.setText(ValueResources.select_iamges_size + "/9");
    }

    @Override
    public void onClickNinePhotoItem(CCRSortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                jiugongge_iamges.addAll(resultPaths);
                mPhotosSnpl.setData(strings);//设置九宫格
                ValueResources.select_iamges_size = strings.size();
                select_numbers_tv.setText(ValueResources.select_iamges_size + "/9");

                return;
            } else {//添加icon,上传icon
                ArrayList<String> resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
                if (resultPaths.size() > 0) {
                    icon_image_path = resultPaths.get(0);
                    File icon_iamge_file = new File(icon_image_path);
                    Glide.with(getApplicationContext()).load(icon_iamge_file).into(logo_image);
                    delete_icon_btn.setVisibility(View.VISIBLE);
                }

            }


        } else if (RESULT_CANCELED == resultCode) {
            //Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取7牛 token
     */
    private void getToken() {
        subscription = Network.getInstance("获取七牛云token", this)
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


    int i = 0;
    @SuppressLint("CheckResult")
    private void withListLs() {

        if (jiugongge_iamges.size() == 0){
            upListToQiniu();
            return;
        }

        if (upList_iamges.size() == strings.size()){
            upListToQiniu();
            return;
        }
        Observable
                .fromIterable(jiugongge_iamges)
                .flatMap((Function<String, ObservableSource<File>>)
                        path -> Observable.create(emitter -> {
                            File file = new File(path);
                            Luban.with(this)
                                    .load(file)
                                    .ignoreBy(100)
                                    .setTargetDir(getPath())
                                    .setFocusAlpha(false)
                                    .setCompressListener(new OnCompressListener() {
                                        @Override
                                        public void onStart() {
                                            Log.d("luban","开始："+path);
                                        }

                                        @Override
                                        public void onSuccess(File file) {
                                            emitter.onNext(file);
                                            Log.d("luban","压缩成功:"+file.getPath());
                                            emitter.onComplete();
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            emitter.onError(e);
                                        }
                                    }).launch();
                        })
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    //todo
                    Log.d("luban","发射成功:"+response.getPath());
                    jiugongge_iamges.set(i, response.getPath());
                    i++;
                    // 如果全部完成，调用成功接口
                    if (i == jiugongge_iamges.size()) {
                        upListToQiniu();
                    }
                }, throwable -> {
                    Log.d("luban","异常了");
                });


    }

    /**
     * list上传到七牛云
     */
    @SuppressLint("CheckResult")
    private void upListToQiniu() {
        if (upList_iamges.size() == strings.size()){
            requestUpdate();
            return;
        }
        progress_upload = new ProgressUtil();
        progress_upload.showProgressDialog(this, "载入中...");
        Observable
                .fromIterable(jiugongge_iamges)
                .concatMap((Function<String, ObservableSource<String>>) path ->
                        Observable.create((ObservableOnSubscribe<String>) emitter -> {
                            Log.d("qiniu",path);
                           uploadManager.put(path, qiniu_key, uptoken,
                                    (key, info, response) -> {
                                        if (info.isOK()) {
                                            // 上传成功，发送这张图片的文件名
                                            emitter.onNext(key);
                                            emitter.onComplete();
                                        } else {
                                            // 上传失败，告辞
                                            emitter.onError(new IOException(info.error));
                                        }
                                    },  new UploadOptions(null, "test-type", true,
                                           null, null));


                        }).subscribeOn(Schedulers.io())
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    InformationEntity.GymPicBean bean = new InformationEntity.GymPicBean();
                    bean.setQiniu_key(response);
                    bean.setOrder_num(upList_iamges.size()+2);
                    upList_iamges.add(bean);
                    Log.d("qiniu","上传发射成功:"+response);
                    // 如果全部完成，调用成功接口
                    if (upList_iamges.size() == strings.size()) {
                        requestUpdate();
                        Log.d("qiniu","全部上传完成");
                        progress_upload.dismissProgressDialog();
                    }
                }, throwable -> {
                    //
                    progress_upload.dismissProgressDialog();
                });




    }

    /**
     * logo上传到七牛云
     */
    InformationEntity.GymPicBean logoBean;
    private void upLogoToQiniu() {
        if (!"".equals(icon_image_path)){
            progress_upload = new ProgressUtil();
            progress_upload.showProgressDialog(this, "载入中...");
            //上传icon
            uploadManager.put(icon_image_path, qiniu_key, uptoken,
                    (key, info, response) -> {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            Log.e("qiniu", "Upload Success");
                            logoBean = new InformationEntity.GymPicBean();
                            logoBean.setQiniu_key(key);
                            logoBean.setOrder_num(1);
                            //测试资料上传的
                            //getUrlTest(icon_net_path);
                            String headpicPath = "http://upload.qiniup.com/" + key;
                            Log.e("返回的地址", headpicPath);
                        } else {
                            Log.e("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        progress_upload.dismissProgressDialog();
                    }, new UploadOptions(null, "test-type", true, null, null));
        }

        withListLs();
    }

    private void withLs() {
        if (!"".equals(icon_image_path)){
            File file = new File(icon_image_path);
            Luban.with(this)
                    .load(file)
                    .ignoreBy(100)
                    .setTargetDir(getPath())
                    .setFocusAlpha(false)
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(File file) {
                            icon_image_path = file.getPath();
                            Log.d("Luban", "luban压缩 成功！原图:${photos.path}");
                            Log.d("Luban", "luban压缩 成功！imgurl:$icon_image_path");
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    }).launch();
        }
        upLogoToQiniu();
    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    /**
     * request
     */
    private void requestUpdate() {

        if ("".equals(changguan_name.getText().toString())){
            //
            Toast.makeText(getApplicationContext(), "场馆名称不可为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(tell_edit.getText().toString())){
            //
            Toast.makeText(getApplicationContext(), "电话不可为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(edit_email.getText().toString())){
            //
            Toast.makeText(getApplicationContext(), "邮箱不可为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(old_logo) && "".equals(icon_image_path)){
            Toast.makeText(getApplicationContext(), "场馆Logo不可为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (logoBean!= null){
            upList_iamges.add(logoBean);
        }
        InformationEntity informationEntity = new InformationEntity();
        informationEntity.setArea_num(SpUtils.getString(getApplicationContext(),AppConstants.CHANGGUAN_NUM));
        informationEntity.setArea_name(changguan_name.getText().toString());//场馆名称
        if (changguan_type.equals("综合会所")) {//场馆类型
            informationEntity.setType(1);
        } else {//工作室
            informationEntity.setType(2);
        }
        informationEntity.setArea(Integer.valueOf(edittext_area.getText().toString()));//场馆面积
        informationEntity.setPhone(tell_edit.getText().toString());//电话号码
        informationEntity.setEmail(edit_email.getText().toString());//邮箱
        informationEntity.setBusiness_start(time1_edit.getText().toString());//营业开始时间
        informationEntity.setBusiness_end(time2_edit.getText().toString());//营业结束时间
        informationEntity.setAddress(edit_address.getText().toString());//地址

        informationEntity.setLegal_person(faren_name.getText().toString().trim());
        informationEntity.setCard_num(icon_id_card.getText().toString().trim());
        informationEntity.setCompany_name(qiye_name.getText().toString().trim());
        informationEntity.setCompany_code(qiye_zhucehao.getText().toString().trim());

        informationEntity.setFacility(get_selete_biaoqian());
        //获取选择的功能性场所类型
        ArrayList<ItemBean> itemBeans = exRecyclerAdapter.getData();
        List<InformationEntity.GymPlacesBean> gymPlacesBeans = new ArrayList<>();
        for (int i = 0; i < itemBeans.size(); i++) {
            InformationEntity.GymPlacesBean gymPlacesBean = new InformationEntity.GymPlacesBean();
            if (null == itemBeans.get(i).getPlace()) {
                gymPlacesBean.setMax_num(0);
            } else {
                gymPlacesBean.setMax_num(Integer.valueOf(itemBeans.get(i).getPlace()));
            }
            if (itemBeans.get(i).getType_name().equals("有氧")) {
                gymPlacesBean.setPlace_type("1");
            } else if (itemBeans.get(i).getType_name().equals("瑜伽")) {
                gymPlacesBean.setPlace_type("2");
            } else {
                gymPlacesBean.setPlace_type("3");

            }
            gymPlacesBean.setPlace_num(itemBeans.get(i).getPlace_num());
            gymPlacesBeans.add(gymPlacesBean);

        }

        informationEntity.setGymPlaces(gymPlacesBeans);
        informationEntity.setGymPic(upList_iamges);


        subscription = Network.getInstance("场馆信息", this)
                .submitAudit(informationEntity, new ProgressSubscriber<Object>("场馆信息",
                        new SubscriberOnNextListener<Bean<Object>>() {
                            @Override
                            public void onNext(Bean<Object> changguanBeanBean) {
                                Toast.makeText(ChangGuandetailActivity.this, "当前场馆信息修改成功",
                                        Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));

    }

    @Override
    public void onBackPressed() {
        setResult(3);
        finish();
    }
}
