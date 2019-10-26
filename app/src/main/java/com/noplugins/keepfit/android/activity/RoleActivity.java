package com.noplugins.keepfit.android.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.RoleAdapter;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.RoleBean;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.demo.spinner.MaterialSpinner;
import okhttp3.RequestBody;

public class RoleActivity extends BaseActivity {
    @BindView(R.id.rc_view)
    RecyclerView rc_view;
    @BindView(R.id.rc_view_add)
    RecyclerView rc_view_add;
    @BindView(R.id.back_btn)
    ImageView back_btn;

    @BindView(R.id.tv_complete)
    TextView tv_complete;

    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_phone)
    TextView tv_user_phone;
    @BindView(R.id.tv_zhiwei_name)
    TextView tv_zhiwei_name;
    @BindView(R.id.iv_add)
    ImageView iv_add;

    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager1;
    private ArrayList<RoleBean.RoleEntity> datas;
    private RoleAdapter roleAdapter;
    private RoleAdapter roleAdapter_add;
    private ArrayList<RoleBean.RoleEntity> completeDatas;

    private List<Object> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_role);
        ButterKnife.bind(this);
        isShowTitle(false);
        posts = new ArrayList<>();
        set_jiugongge_view();

        getBindingUserList();


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
            public void onClick(View view) {
                toComplete();
            }
        });

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roleAdapter_add.addData(new RoleBean.RoleEntity());
            }
        });
    }

    private void set_jiugongge_view() {
        //设置视图添加
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager1 = new LinearLayoutManager(this);
        rc_view.setLayoutManager(linearLayoutManager);
        rc_view.setNestedScrollingEnabled(false);//禁止滑动
        rc_view_add.setLayoutManager(linearLayoutManager1);
        rc_view_add.setNestedScrollingEnabled(false);//禁止滑动
        datas = new ArrayList<>();
        completeDatas = new ArrayList<>();
        roleAdapter_add = new RoleAdapter(completeDatas);
        rc_view_add.setAdapter(roleAdapter_add);

        roleAdapter = new RoleAdapter(datas);
        rc_view.setAdapter(roleAdapter);
        roleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.Add_btn:
                        delete(position);
                        break;
                    case R.id.post_type_spinner:

                        break;
                }
            }
        });


        roleAdapter_add.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.Add_btn:
                        adapter.notifyItemRemoved(position);
                        if (position != completeDatas.size()) {
                            adapter.notifyItemRangeChanged(position, completeDatas.size() - position);
                        }
                        completeDatas.remove(position);
                        break;
                    case R.id.post_type_spinner:
                        break;
                }
            }
        });

    }


    private void getBindingUserList() {
        datas.clear();
        Map<String, Object> params = new HashMap<>();
        params.put("area_num", SpUtils.getString(getApplicationContext(), AppConstants.CHANGGUAN_NUM));
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(null, json);

        subscription = Network.getInstance("获取已绑定用户", getApplicationContext())
                .findBindingRoles(requestBody, new ProgressSubscriber<>("获取已绑定用户",
                        new SubscriberOnNextListener<Bean<RoleBean>>() {
                    @Override
                    public void onNext(Bean<RoleBean> roleBeanBean) {
                        if (roleBeanBean.getData().getUserList() != null) {
                            datas.addAll(roleBeanBean.getData().getUserList());
                            datas.remove(0);
                            tv_user_name.setText(roleBeanBean.getData().getUserList().get(0).getName());
                            tv_user_phone.setText(roleBeanBean.getData().getUserList().get(0).getPhone());
                            tv_zhiwei_name.setText("场馆主");
                            roleAdapter.notifyDataSetChanged();
                        }

                        Toast.makeText(getApplicationContext(),roleBeanBean.getMessage()
                                ,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(getApplicationContext(),error
                                ,Toast.LENGTH_SHORT).show();
                    }
                }, this, true));

    }

    /**
     * 删除
     */

    List<RoleBean.RoleEntity> list = new ArrayList<>();
    private void delete(int position) {
        list.clear();
        Log.d("delete","position:"+position);
        datas.get(position).setType(0);
        RoleBean roleBean = new RoleBean();
        list.add(datas.get(position));
        roleBean.setUserList(list);
        subscription = Network.getInstance("解除绑定用户", getApplicationContext())
                .binding_role(roleBean, new ProgressSubscriber<>("", new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> objectBean) {
                        if (objectBean.getMessage().equals("success")){
                            Toast.makeText(getApplicationContext(), "解除绑定用户成功！", Toast.LENGTH_SHORT).show();
                            datas.remove(position);
                            roleAdapter.notifyItemRemoved(position);
                            roleAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), objectBean.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                    }
                }, this, true));
    }


    /**
     * 完成只设置新增
     */
    private void toComplete() {
        Log.d("data", "---" + rc_view_add.getChildCount());

        completeDatas.clear();
        for (int i = 0; i < rc_view_add.getChildCount(); i++) {
            LinearLayout layout = (LinearLayout) rc_view_add.getChildAt(i);
            EditText et_name = layout.findViewById(R.id.edit_name);
            EditText edit_phone = layout.findViewById(R.id.edit_phone);
//            EditText edit_role = layout.findViewById(R.id.edit_role);
            MaterialSpinner post_type_spinner = layout.findViewById(R.id.post_type_spinner);

            if (TextUtils.isEmpty(et_name.getText().toString())) {
                Toast.makeText(getApplicationContext(), "姓名不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(edit_phone.getText().toString())) {
                Toast.makeText(getApplicationContext(), "手机号不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (post_type_spinner.getText().toString().equals("请选择")) {
                Toast.makeText(getApplicationContext(), "职位不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            RoleBean.RoleEntity roleBean = new RoleBean.RoleEntity();
            roleBean.setType(1);

            if (post_type_spinner.getText().toString().equals("经理")) {
                roleBean.setUserType(2);
            }
            if (post_type_spinner.getText().toString().equals("前台")) {
                roleBean.setUserType(3);
            }

            roleBean.setUserName(et_name.getText().toString());
            roleBean.setPhone(edit_phone.getText().toString());
            roleBean.setGymAreaNum(SpUtils.getString(getApplicationContext(), AppConstants.CHANGGUAN_NUM));
            completeDatas.add(roleBean);
        }

        RoleBean roleBean = new RoleBean();
        roleBean.setUserList(completeDatas);
        subscription = Network.getInstance("绑定用户", getApplicationContext())
                .binding_role(roleBean, new ProgressSubscriber<>("绑定用户", new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> objectBean) {
                        completeDatas.clear();
                        roleAdapter_add.notifyDataSetChanged();
                        getBindingUserList();
                        Toast.makeText(getApplicationContext(),objectBean.getMessage(),
                                Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(getApplicationContext(),error,
                                Toast.LENGTH_SHORT).show();
                    }
                }, this, true));

    }

    @Override
    public void onBackPressed() {
        setResult(3);
        finish();
    }
}
