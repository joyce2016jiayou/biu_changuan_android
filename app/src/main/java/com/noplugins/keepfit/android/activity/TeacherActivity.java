package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.TeacherRoleAdapter;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.ItemBean;
import com.noplugins.keepfit.android.entity.RoleBean;
import com.noplugins.keepfit.android.entity.TeacherBean;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;


public class TeacherActivity extends BaseActivity {
    @BindView(R.id.rc_view)
    RecyclerView rc_view;

    @BindView(R.id.rc_view_add)
    RecyclerView rc_view_add;

    @BindView(R.id.back_btn)
    ImageView back_btn;

    @BindView(R.id.btn_add)
    Button btn_add;

    @BindView(R.id.tv_complete)
    TextView tv_complete;

    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager1;
    private TeacherRoleAdapter roleAdapter;
    private TeacherRoleAdapter roleAdapter_add;

    private List<TeacherBean.TeacherEntity> completeDatas;
    private List<TeacherBean.TeacherEntity> completeDatas_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_teacher);
        ButterKnife.bind(this);
        isShowTitle(false);
        completeDatas = new ArrayList<>();
        completeDatas_add = new ArrayList<>();
        set_jiugongge_view();
        getBindingUserList();

    }

    @Override
    public void doBusiness(Context mContext) {

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toComplete();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roleAdapter_add.addData(new TeacherBean.TeacherEntity());
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
        roleAdapter = new TeacherRoleAdapter(this, completeDatas, R.layout.teacher_role_item);
        rc_view.setAdapter(roleAdapter);
        roleAdapter.setOnItemDeleteClickListener(new TeacherRoleAdapter.onItemClick() {
            @Override
            public void onItemClick(int position) {
                delete(position);
            }
        });

        roleAdapter_add = new TeacherRoleAdapter(this, completeDatas_add, R.layout.teacher_role_item);
        rc_view_add.setAdapter(roleAdapter_add);
        roleAdapter_add.setOnItemDeleteClickListener(new TeacherRoleAdapter.onItemClick() {
            @Override
            public void onItemClick(int position) {
                completeDatas_add.remove(position);
                roleAdapter_add.notifyItemRemoved(position);
                if (position != completeDatas_add.size()) {
                    roleAdapter_add.notifyItemRangeChanged(position, roleAdapter_add.getItemCount());
                }
            }
        });


    }

    private void delete(int position){
        completeDatas.get(position).setType(1);

        TeacherBean roleBean = new TeacherBean();
        roleBean.setTeacherList(completeDatas);
        Gson gson = new Gson();
        String objJson = gson.toJson(roleBean);
        Logger.d(objJson);

        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), objJson);
//
//        subscription = Network.getInstance("绑定用户", getApplicationContext())
//                .binding_role(requestBody,new ProgressSubscriberNew<>(Object.class, (o, message_id) -> {
//                    if (message_id.equals("success")){
//                        //绑定成功！
//                        Toast.makeText(getApplicationContext(), "删除成功！", Toast.LENGTH_SHORT).show();
//                        completeDatas.remove(position);
//                        roleAdapter.notifyItemRemoved(position);
//                        roleAdapter.notifyDataSetChanged();
//                    }
//                }, new SubscriberOnNextListener<Bean<Object>>() {
//                    @Override
//                    public void onNext(Bean<Object> result) {
//                    }
//
//                    @Override
//                    public void onError(String error) {
//                        Log.e(TAG, "修改失败：" + error);
//                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
//                    }
//                }, this, true));
    }

    private void getBindingUserList(){
        completeDatas.clear();
        Map<String, Object> params = new HashMap<>();
        params.put("area_num", "GYM19080921749396");
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(null, json);

        subscription = Network.getInstance("获取已绑定用户", getApplicationContext())
                .findBindingTeachers(requestBody,new ProgressSubscriberNew<>(TeacherBean.class, (o, message_id) -> {
                    completeDatas.addAll(o.getTeacherList());
                    roleAdapter.notifyDataSetChanged();
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {
                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "获取失败：" + error);
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                    }
                }, this, true));

    }

    /**
     * 点击完成
     */
    private void toComplete(){
        completeDatas_add.clear();
        for (int i = 0; i < rc_view_add.getChildCount(); i++) {
            LinearLayout layout = (LinearLayout) rc_view.getChildAt(i);
            EditText et_name = layout.findViewById(R.id.edit_name);
            EditText edit_phone = layout.findViewById(R.id.edit_phone);

            if (TextUtils.isEmpty(et_name.getText().toString())){
                Toast.makeText(getApplicationContext(), "姓名不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(edit_phone.getText().toString())){
                Toast.makeText(getApplicationContext(), "手机号不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            TeacherBean.TeacherEntity roleBean = new TeacherBean.TeacherEntity();
            roleBean.setUserName(et_name.getText().toString());
            roleBean.setPhone(edit_phone.getText().toString());
            roleBean.setDeleted(0);
//            roleBean.setGymAreaNum((String) SharedPreferencesHelper.get(getApplicationContext(),
//            "changguan_number", "GYM19072138381319"));
            roleBean.setGymAreaNum("GYM19072138381319");
            completeDatas_add.add(roleBean);
        }


        Gson gson = new Gson();
        String objJson = gson.toJson(completeDatas_add);
        Logger.d(objJson);

        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), objJson);

        subscription = Network.getInstance("绑定教练", getApplicationContext())
                .binding_teacher(requestBody,new ProgressSubscriberNew<>(Object.class, (o, message_id) -> {
                    if (message_id.equals("success")){
                        //绑定成功！
                        Toast.makeText(getApplicationContext(), "绑定成功", Toast.LENGTH_SHORT).show();
                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {
                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "修改失败：" + error);
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                    }
                }, this, true));
    }
}
