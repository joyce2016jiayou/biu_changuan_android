package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.AddClass01Adapter;
import com.noplugins.keepfit.android.adapter.AddClassAdapter;
import com.noplugins.keepfit.android.adapter.DateWhatchAdapter;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.ClassEntity;
import com.noplugins.keepfit.android.entity.DateViewEntity;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddClassActivity extends BaseActivity {
    @BindView(R.id.xrefreshview)
    SmartRefreshLayout xrefreshview;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.add_class_btn)
    LinearLayout add_class_btn;
    @BindView(R.id.back_btn)
    ImageView back_btn;

    private LinearLayoutManager layoutManager;
    private AddClass01Adapter addClassAdapter;
    private int page = 1;
    private List<ClassEntity.DataBean> dataBeans = new ArrayList<>();

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_add_class);
        ButterKnife.bind(this);
        set_list_resource();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void doBusiness(Context mContext) {


        add_class_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddClassActivity.this, AddClassItemActivity.class);
                startActivity(intent);
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void init_class_date() {
        dataBeans.clear();
        Map<String, Object> params = new HashMap<>();

        params.put("gymAreaNum", SpUtils.getString(getApplicationContext(), AppConstants.CHANGGUAN_NUM));//场馆编号
        params.put("page", page);
        subscription = Network.getInstance("课程列表", this)
                .class_list(params, new ProgressSubscriber<>("", new SubscriberOnNextListener<Bean<ClassEntity>>() {
                    @Override
                    public void onNext(Bean<ClassEntity> classEntityBean) {
                        dataBeans.addAll(classEntityBean.getData().getData());
                        addClassAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String error) {

                    }
                }, this, true));
    }

    private void set_list_resource() {
        //设置上拉刷新下拉加载
        layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);

        addClassAdapter = new AddClass01Adapter(dataBeans);
        View view = LayoutInflater.from(this).inflate(R.layout.enpty_view, recycler_view, false);
        addClassAdapter.setEmptyView(view);
        addClassAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(AddClassActivity.this, YaoQingZhongDetailActivity.class);
                Bundle bundle = new Bundle();
                Log.e("健康是福",dataBeans.get(position).getCourse_num());
                bundle.putString("gymCourseNum",dataBeans.get(position).getCourse_num());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recycler_view.setAdapter(addClassAdapter);

        xrefreshview.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(2000);
        });

        xrefreshview.setOnLoadMoreListener(refreshLayout -> {
            refreshLayout.finishRefresh(2000);
        });

        init_class_date();
    }

}
