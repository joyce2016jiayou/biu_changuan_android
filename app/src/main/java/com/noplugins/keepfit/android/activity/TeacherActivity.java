package com.noplugins.keepfit.android.activity;

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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.RoleAdapter;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;


public class TeacherActivity extends BaseActivity {
    @BindView(R.id.rc_view)
    RecyclerView rc_view;
    @BindView(R.id.back_btn)
    ImageView back_btn;

    @BindView(R.id.tv_complete)
    TextView tv_complete;

    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ItemBean> datas;
    private RoleAdapter roleAdapter;
    private List<TeacherBean> completeDatas;

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

    }

    @Override
    public void doBusiness(Context mContext) {
        set_jiugongge_view();
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
    }

    private void set_jiugongge_view() {
        //设置视图添加
        linearLayoutManager = new LinearLayoutManager(this);
        rc_view.setLayoutManager(linearLayoutManager);
        rc_view.setNestedScrollingEnabled(false);//禁止滑动
        datas = new ArrayList<>();
        roleAdapter = new RoleAdapter(this, datas, R.layout.teacher_role_item);
        roleAdapter.addData(new ItemBean());
        rc_view.setAdapter(roleAdapter);

    }


    /**
     * 点击完成
     */
    private void toComplete(){
        completeDatas.clear();
        for (int i = 0; i < rc_view.getChildCount(); i++) {
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
            TeacherBean roleBean = new TeacherBean();
            roleBean.setUserName(et_name.getText().toString());
            roleBean.setPhone(edit_phone.getText().toString());
            roleBean.setDeleted(0);
//            roleBean.setGymAreaNum((String) SharedPreferencesHelper.get(getApplicationContext(),
//            "changguan_number", "GYM19072138381319"));
            roleBean.setGymAreaNum("GYM19072138381319");
            completeDatas.add(roleBean);
        }


        Gson gson = new Gson();
        String objJson = gson.toJson(completeDatas);
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
