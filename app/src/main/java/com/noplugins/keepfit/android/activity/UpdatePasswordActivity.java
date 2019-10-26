package com.noplugins.keepfit.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.ActivityCollectorUtil;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;

public class UpdatePasswordActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    ImageView back_btn;

    @BindView(R.id.edit_old_password)
    EditText edit_old_password;
    @BindView(R.id.edit_new_password1)
    EditText edit_new_password1;
    @BindView(R.id.edit_password2)
    EditText edit_password2;
    @BindView(R.id.login_btn)
    LinearLayout login_btn;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_update_password);
        ButterKnife.bind(this);
        isShowTitle(false);
    }


    private void updatePassword(){
        if (TextUtils.isEmpty(edit_old_password.getText().toString())){
            Toast.makeText(this, "旧密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(edit_new_password1.getText().toString())){
            Toast.makeText(this, "新密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(edit_password2.getText().toString())){
            Toast.makeText(this, "确认密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("gymAreaNum", SpUtils.getString(getApplicationContext(), AppConstants.USER_NAME));
        params.put("oldPassWord", edit_old_password.getText().toString());
        params.put("phone", (String)SharedPreferencesHelper.get(getApplicationContext(), Network.phone_number, ""));
        params.put("newPass1", edit_new_password1.getText().toString());
        params.put("newPass2", edit_password2.getText().toString());
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        Log.e(TAG, "修改密码的参数：" + json_params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(null, json);

        subscription = Network.getInstance("修改密码", getApplicationContext())

                .update_my_password(requestBody,new ProgressSubscriberNew<>(String.class, new GsonSubscriberOnNextListener<String>() {
                    @Override
                    public void on_post_entity(String s, String message_id) {
                        Toast.makeText(getApplicationContext(), message_id, Toast.LENGTH_SHORT).show();
                        toLogin();
                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> objectBean) {

                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "登录失败：" + error);
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                    }
                }, this, true));
    }
    @Override
    public void doBusiness(Context mContext) {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();
            }
        });
    }

    private void toLogin(){
        Intent intent = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
        SpUtils.putString(getApplicationContext(), AppConstants.TOKEN, "");
        SpUtils.putString(getApplicationContext(),AppConstants.PHONE, "");
        SpUtils.putString(getApplicationContext(), AppConstants.CHANGGUAN_NUM, "");
        startActivity(intent);
        ActivityCollectorUtil.finishAllActivity();

    }
}
