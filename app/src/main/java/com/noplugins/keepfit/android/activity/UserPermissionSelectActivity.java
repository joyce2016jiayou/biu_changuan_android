package com.noplugins.keepfit.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.PopWindowHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserPermissionSelectActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    ImageView back_btn;
    @BindView(R.id.guanzhu_btn)
    RelativeLayout guanzhu_btn;
    @BindView(R.id.yuangong_btn)
    LinearLayout yuangong_btn;
    @BindView(R.id.guanzhu_enter)
    LinearLayout guanzhu_enter;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_user_permission_select);
        ButterKnife.bind(this);
        isShowTitle(false);

    }

    @Override
    public void doBusiness(Context mContext) {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //展开馆主资料
        guanzhu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                public_pop(UserPermissionSelectActivity.this,R.layout.guanzhu_information_layout,true);
            }
        });
        //展开员工资料
        yuangong_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                public_pop(UserPermissionSelectActivity.this,R.layout.yuangong_information_layout,false);

            }
        });

        guanzhu_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selete_role("1");
            }
        });
        yuangong_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selete_role("2");

            }
        });

    }


    private void selete_role(String type) {

        String token = SharedPreferencesHelper.get(getApplicationContext(), "login_token", "").toString();
        String phone_number = SharedPreferencesHelper.get(getApplicationContext(), "phone_number", "").toString();
        Map<String, String> params = new HashMap<>();
        params.put("token",token);
        params.put("phone", phone_number);
        params.put("type", type);
        Log.e(TAG, "选择参数：" + params.toString());

        subscription = Network.getInstance("选择角色", getApplicationContext())
                .select_role(params,
                        new ProgressSubscriberNew<>(String.class, new GsonSubscriberOnNextListener<String>() {
                            @Override
                            public void on_post_entity(String code,String Message_id) {
                                Log.e(TAG,"选择成功：");
                                Toast.makeText(getApplicationContext(), "选择成功！", Toast.LENGTH_SHORT).show();

                            }
                        }, new SubscriberOnNextListener<Bean<Object>>() {
                            @Override
                            public void onNext(Bean<Object> result) {

                            }

                            @Override
                            public void onError(String error) {
                                //Logger.e(TAG, "注册报错：" + error);
                                Log.e(TAG,"选择报错：" + error);
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                            }
                        }, this, true));

    }


    public static void public_pop(Activity activity, int resource,boolean is_changguan) {
        LayoutInflater lay = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lay.inflate(resource, null);
        final PopupWindow guanzhu_tishi = PopWindowHelper.PopwindowType3(activity, view,resource);
        guanzhu_tishi.setContentView(view);
        ImageView close_pop = view.findViewById(R.id.close_pop);
        close_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guanzhu_tishi.dismiss();
            }
        });
        LinearLayout submit_btn = view.findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guanzhu_tishi.dismiss();

            }
        });

    }



}
