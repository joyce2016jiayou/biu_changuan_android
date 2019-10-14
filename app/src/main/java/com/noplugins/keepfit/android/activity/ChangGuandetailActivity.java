package com.noplugins.keepfit.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.EasyPhotos;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.ExRecyclerAdapter;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.BiaoqianEntity;
import com.noplugins.keepfit.android.entity.ItemBean;
import com.noplugins.keepfit.android.resource.ValueResources;
import com.noplugins.keepfit.android.util.GlideEngine;
import com.noplugins.keepfit.android.util.ui.NoScrollViewPager;
import com.noplugins.keepfit.android.util.ui.ProgressUtil;
import com.noplugins.keepfit.android.util.ui.StepView;
import com.noplugins.keepfit.android.util.ui.jiugongge.CCRSortableNinePhotoLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.wheelpicker.TimePicker;
import cn.qqtheme.framework.wheelview.annotation.TimeMode;
import cn.qqtheme.framework.wheelview.contract.OnTimeSelectedListener;
import cn.qqtheme.framework.wheelview.entity.TimeEntity;
import lib.demo.spinner.MaterialSpinner;
import rx.Subscription;

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
    private ProgressUtil progress_upload;
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
    }

    @Override
    public void doBusiness(Context mContext) {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void requestData(){

    }

    private void setting(){
        changguan_name.setText("");
        edit_address.setText("");
        tell_edit.setText("");
        edit_email.setText("");
        faren_name.setText("");
        icon_id_card.setText("");
        qiye_name.setText("");
        qiye_zhucehao.setText("");
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
        mPhotosSnpl.removeItem(position);
        ValueResources.select_iamges_size = ValueResources.select_iamges_size - 1;
    }

    @Override
    public void onClickNinePhotoItem(CCRSortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {

    }
}
