package com.noplugins.keepfit.android.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.noplugins.keepfit.android.KeepFitActivity;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.SplashActivity;
import com.noplugins.keepfit.android.activity.CheckStatusFailActivity;
import com.noplugins.keepfit.android.activity.HeTongActivity;
import com.noplugins.keepfit.android.activity.InformationCheckActivity;
import com.noplugins.keepfit.android.activity.SubmitInformationSelectActivity;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.entity.CheckEntity;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.LoadingButton;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

public class SetPasswordActivity extends BaseActivity {
    @BindView(R.id.tiaoguo_tv)
    TextView tiaoguo_tv;
    @BindView(R.id.sure_btn)
    LoadingButton sure_btn;
    @BindView(R.id.edit_password_number)
    EditText edit_password_number;
    @BindView(R.id.edit_password_again)
    EditText edit_password_again;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_set_password);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        tiaoguo_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取审核状态
                if (SpUtils.getInt(getApplicationContext(), AppConstants.USER_TYPE) == 1) {
                    get_check_status();
                } else {
                    Intent intent = new Intent(SetPasswordActivity.this, SubmitInformationSelectActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        sure_btn.setBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edit_password_number.getText().toString())) {
                    Toast.makeText(SetPasswordActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(edit_password_again.getText().toString())) {
                    Toast.makeText(SetPasswordActivity.this, "再次输入的密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!edit_password_number.getText().toString().equals(edit_password_again.getText().toString())) {
                    Toast.makeText(SetPasswordActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String passRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
                    Pattern p = Pattern.compile(passRegex);
                    Matcher m = p.matcher(edit_password_number.getText().toString());
                    boolean b = m.matches();
                    if (!b) {
                        Toast.makeText(SetPasswordActivity.this, "请输入正确的格式！", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        set_password();
                    }

                }

            }
        });

    }

    private void set_password() {
        Map<String, Object> params = new HashMap<>();
        params.put("userNum", SpUtils.getString(getApplicationContext(), AppConstants.USER_NAME));
        params.put("password", edit_password_again.getText().toString());
        subscription = Network.getInstance("设置密码", this)
                .setPassword(params,
                        new ProgressSubscriber<>("设置密码", new SubscriberOnNextListener<Bean<String>>() {
                            @Override
                            public void onNext(Bean<String> result) {
                                sure_btn.loadingComplete();

//                                if(null!=SpUtils.getString(getApplicationContext(),AppConstants.TEACHER_TYPE)){
//                                    if(SpUtils.getString(getApplicationContext(),AppConstants.TEACHER_TYPE).length()>0){//已经审核过了
//                                        Intent intent = new Intent(SetPasswordActivity.this, KeepFitActivity.class);
//                                        startActivity(intent);
//                                    }else{//未审核
//
//                                        Intent intent = new Intent(SetPasswordActivity.this, KeepFitActivity.class);
//                                        startActivity(intent);
//                                    }
//                                }
                                //获取审核状态
//                                if (SpUtils.getInt(getApplicationContext(), AppConstants.USER_TYPE) == 1) {
//
//                                }
                                get_check_status();

                            }

                            @Override
                            public void onError(String error) {
                                sure_btn.loadingComplete();


                            }
                        }, this, false));
    }

    private void get_check_status() {
        Map<String, Object> params = new HashMap<>();
        params.put("token", SpUtils.getString(getApplicationContext(), AppConstants.TOKEN));
        Log.e("获取审核状态参数", params.toString());
        Subscription subscription = Network.getInstance("获取审核状态", this)
                .get_check_status(params,
                        new ProgressSubscriber<>("获取审核状态", new SubscriberOnNextListener<Bean<CheckEntity>>() {
                            @Override
                            public void onNext(Bean<CheckEntity> result) {
                                Log.e(TAG, "获取审核状态成功：" + result.getData().getStatus());

                                if (result.getData().getStatus() == 1) {
                                    if (result.getData().getHaveMember().equals("0")) {
                                        Intent intent = new Intent(SetPasswordActivity.this, HeTongActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (result.getData().getHaveMember().equals("1")) {
                                        SpUtils.putString(getApplicationContext(), AppConstants.USER_DENGJI, "2999");
                                        Intent intent = new Intent(SetPasswordActivity.this, KeepFitActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (result.getData().getHaveMember().equals("2")) {
                                        SpUtils.putString(getApplicationContext(), AppConstants.USER_DENGJI, "3999");
                                        Intent intent = new Intent(SetPasswordActivity.this, KeepFitActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (result.getData().getHaveMember().equals("3")) {
                                        SpUtils.putString(getApplicationContext(), AppConstants.USER_DENGJI, "6999");
                                        Intent intent = new Intent(SetPasswordActivity.this, KeepFitActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else if (result.getData().getStatus() == 0) {
                                    Intent intent = new Intent(SetPasswordActivity.this, CheckStatusFailActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if (result.getData().getStatus() == 2) {//没有绑定银行卡
                                    Intent intent = new Intent(SetPasswordActivity.this, InformationCheckActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("is_not_bind", 1);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                } else if (result.getData().getStatus() == -2 || result.getData().getStatus() == 4) {//没有提交过
                                    Intent intent = new Intent(SetPasswordActivity.this, SubmitInformationSelectActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }

                            @Override
                            public void onError(String error) {
                                Intent intent = new Intent(SetPasswordActivity.this, Login2Activity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, this, false));


    }

}
