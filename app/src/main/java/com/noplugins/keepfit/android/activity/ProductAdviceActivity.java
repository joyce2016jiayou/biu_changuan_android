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
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
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
                setResult(3);
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
        Map<String, Object> params = new HashMap<>();
        params.put("gymAreaNum", SpUtils.getString(getApplicationContext(), AppConstants.CHANGGUAN_NUM));
        params.put("gymUserNum", SpUtils.getString(getApplicationContext(),AppConstants.USER_NAME));
        params.put("feedbackType", "" + type);
        params.put("feedbackDes", edit_content.getText().toString());

        subscription = Network.getInstance("产品反馈", getApplicationContext())

                .feedback(params, new ProgressSubscriber<>("产品反馈", new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> objectBean) {
                        Toast.makeText(getApplicationContext(), objectBean.getMessage(), Toast.LENGTH_SHORT).show();
                        setResult(3);
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                    }
                }, this, true));
    }

    @Override
    public void onBackPressed() {
        setResult(3);
        finish();
    }
}
