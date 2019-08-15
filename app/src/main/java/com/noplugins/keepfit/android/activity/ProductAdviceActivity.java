package com.noplugins.keepfit.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
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

public class ProductAdviceActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    ImageView back_btn;

    @BindView(R.id.cb_product_suggest)
    CheckBox cb_product_suggest;
    @BindView(R.id.cb_fault_feedback)
    CheckBox cb_fault_feedback;
    @BindView(R.id.cb_other)
    CheckBox cb_other;
    @BindView(R.id.edit_content)
    EditText edit_content;
    @BindView(R.id.add_class_teacher_btn)
    LinearLayout add_class_teacher_btn;

    private int type = -1;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_product_advice);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        cb_product_suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_product_suggest.isChecked()) {
                    type = 1;
                    cb_fault_feedback.setChecked(false);
                    cb_other.setChecked(false);
                } else {
                    type = 0;
                }
            }
        });
        cb_fault_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_fault_feedback.isChecked()) {
                    type = 2;
                    cb_product_suggest.setChecked(false);
                    cb_other.setChecked(false);
                } else {
                    type = 0;
                }
            }
        });
        cb_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_other.isChecked()) {
                    type = 3;
                    cb_fault_feedback.setChecked(false);
                    cb_product_suggest.setChecked(false);
                } else {
                    type = 0;
                }
            }
        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add_class_teacher_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                if (type == 0) {
                    Toast.makeText(getApplicationContext(), "请选择反馈类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                submit();
            }
        });
    }

    private void submit() {
        //
        Map<String, String> params = new HashMap<>();
        String gymAreaNum;
        if ("".equals(SharedPreferencesHelper.get(this, Network.changguan_number, "").toString())) {
            gymAreaNum = "";
        } else {
            gymAreaNum = SharedPreferencesHelper.get(this, Network.changguan_number, "").toString();
        }
        params.put("gymAreaNum", gymAreaNum);
        params.put("gymUserNum", (String) SharedPreferencesHelper.get(getApplicationContext(), "phone_number", ""));
        params.put("feedbackType", "" + type);
        params.put("feedbackDes", edit_content.getText().toString());
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        Log.e(TAG, "修改密码的参数：" + json_params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(null, json);

        subscription = Network.getInstance("产品反馈", getApplicationContext())

                .feedback(requestBody, new ProgressSubscriberNew<>(String.class, new GsonSubscriberOnNextListener<String>() {
                    @Override
                    public void on_post_entity(String s, String message_id) {
                        Toast.makeText(getApplicationContext(), message_id, Toast.LENGTH_SHORT).show();
                        finish();
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
}
