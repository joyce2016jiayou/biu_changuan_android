package com.noplugins.keepfit.android.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.KeepFitActivity;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.SplashActivity;
import com.noplugins.keepfit.android.activity.BuyHuiYuanActivity;
import com.noplugins.keepfit.android.activity.CheckStatusFailActivity;
import com.noplugins.keepfit.android.activity.HeTongActivity;
import com.noplugins.keepfit.android.activity.InformationCheckActivity;
import com.noplugins.keepfit.android.activity.SubmitInformationSelectActivity;
import com.noplugins.keepfit.android.bean.BindCardBean;
import com.noplugins.keepfit.android.bean.CheckBean;
import com.noplugins.keepfit.android.bean.CompnyBean;
import com.noplugins.keepfit.android.entity.CheckEntity;
import com.noplugins.keepfit.android.entity.InformationEntity;
import com.noplugins.keepfit.android.entity.UrlEntity;
import com.noplugins.keepfit.android.global.AppConstants;
import com.noplugins.keepfit.android.util.SpUtils;
import com.noplugins.keepfit.android.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.NoScrollViewPager;
import com.noplugins.keepfit.android.util.ui.StepView;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.cache.Sp;
import okhttp3.RequestBody;
import rx.Subscription;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;


public class BusinessInformationFragment extends ViewPagerFragment {
    @BindView(R.id.submit_btn)
    LinearLayout submit_btn;
    @BindView(R.id.qiye_mingcheng_name)
    EditText qiye_mingcheng_name;
    @BindView(R.id.yingyezhizhao_xingyong_edittext)
    EditText yingyezhizhao_xingyong_edittext;
    @BindView(R.id.faren_name)
    EditText faren_name;
    @BindView(R.id.icon_id_card)
    EditText icon_id_card;
    @BindView(R.id.qiye_zhanghao)
    EditText qiye_zhanghao;
    @BindView(R.id.yanzheng_jine)
    EditText yanzheng_jine;
    @BindView(R.id.tixian_compny_name)
    EditText tixian_compny_name;
    @BindView(R.id.tixian_qiye_zhanghao)
    EditText tixian_qiye_zhanghao;
    @BindView(R.id.tixian_compny_layout)
    LinearLayout tixian_compny_layout;
    @BindView(R.id.yingye_ziliao_layout)
    LinearLayout yingye_ziliao_layout;
    @BindView(R.id.com_layout)
    LinearLayout com_layout;
    @BindView(R.id.geren_layout)
    LinearLayout geren_layout;
    @BindView(R.id.compny_check_btn)
    RadioButton compny_check_btn;
    @BindView(R.id.geren_check_btn)
    RadioButton geren_check_btn;
    @BindView(R.id.submit_shenhe_btn)
    LinearLayout submit_shenhe_btn;
    @BindView(R.id.edit_shenhe_user_name)
    EditText edit_shenhe_user_name;
    @BindView(R.id.edit_shenhe_idcard)
    EditText edit_shenhe_idcard;
    @BindView(R.id.edit_shenhe_bankcard_number)
    EditText edit_shenhe_bankcard_number;
    @BindView(R.id.compny_kaihuhang)
    EditText compny_kaihuhang;
    @BindView(R.id.geren_kaihuhang)
    EditText geren_kaihuhang;
    @BindView(R.id.kaihuhang_edit)
    EditText kaihuhang_edit;

    private View view;
    private InformationEntity informationEntity;
    private InformationCheckActivity mainActivity;
    private Subscription subscription;//Rxjava
    private NoScrollViewPager viewpager_content;
    private StepView stepView;


    public static BusinessInformationFragment homeInstance(String title) {
        BusinessInformationFragment fragment = new BusinessInformationFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_business_information, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();
        }
        return view;
    }

    private void initView() {


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_value()) {


                    informationEntity = mainActivity.informationEntity;//获取基础资料信息
                    informationEntity.setCompany_name(qiye_mingcheng_name.getText().toString());//公司名字
                    informationEntity.setCompany_code(yingyezhizhao_xingyong_edittext.getText().toString());//社会统一码
                    informationEntity.setLegal_person(faren_name.getText().toString());//法人姓名
                    informationEntity.setCard_num(icon_id_card.getText().toString());//身份证号
                    informationEntity.setBank_card_num(qiye_zhanghao.getText().toString());//企业账号
                    informationEntity.setBankName(kaihuhang_edit.getText().toString());//开户行


                    //提交审核资料
                    submit_information();
                } else {
                    return;
                }


            }
        });

        compny_check_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    com_layout.setVisibility(View.VISIBLE);
                    geren_layout.setVisibility(View.GONE);
                    geren_check_btn.setChecked(false);
                }
            }
        });
        geren_check_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    com_layout.setVisibility(View.GONE);
                    geren_layout.setVisibility(View.VISIBLE);
                    compny_check_btn.setChecked(false);
                }
            }
        });

        //提交审核
        submit_shenhe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (compny_check_btn.isChecked()) {//提交公司
                    bind_card(true);
                } else if (geren_check_btn.isChecked()) {//提交个人
                    if (check_shenhe_value()) {
                        //提交审核
                        bind_card(false);
                    }
                }

            }
        });

    }

    private void bind_card(boolean is_bind_compny) {
        BindCardBean bindCardBean = new BindCardBean();
        if (is_bind_compny) {
            bindCardBean.setUserName(tixian_compny_name.getText().toString());
            bindCardBean.setBankNum(tixian_qiye_zhanghao.getText().toString());
            bindCardBean.setBankName(compny_kaihuhang.getText().toString());//开户行
        } else {
            bindCardBean.setUserName(edit_shenhe_user_name.getText().toString());//用户名
            bindCardBean.setCardNum(edit_shenhe_idcard.getText().toString());//身份证号
            bindCardBean.setBankNum(edit_shenhe_bankcard_number.getText().toString());//银行卡号
            bindCardBean.setBankName(geren_kaihuhang.getText().toString());//开户行
        }
        Subscription subscription = Network.getInstance("绑定银行卡", getActivity())
                .bind_card(bindCardBean,
                        new ProgressSubscriber<>("绑定银行卡", new SubscriberOnNextListener<Bean<Object>>() {
                            @Override
                            public void onNext(Bean<Object> result) {
                                //切换到第三个进度条
                                /*viewpager_content.setCurrentItem(2);
                                int step = stepView.getCurrentStep();//设置进度条
                                stepView.setCurrentStep((step + 1) % stepView.getStepNum());
                                //删除缓存的状态,目的是下次进启动页的时候不会跳转"角色选择页面"
                                SharedPreferencesHelper.remove(getActivity(), Network.no_submit_information);*/

                                //跳转到合同页面
                                Intent intent = new Intent(getActivity(), HeTongActivity.class);
                                startActivity(intent);

                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), false));
    }


    private boolean check_shenhe_value() {
        if (TextUtils.isEmpty(edit_shenhe_user_name.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi24, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edit_shenhe_idcard.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi25, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(edit_shenhe_bankcard_number.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi26, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    private boolean check_value() {
        if (TextUtils.isEmpty(qiye_mingcheng_name.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi27, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(yingyezhizhao_xingyong_edittext.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi13, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(faren_name.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi14, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(icon_id_card.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi15, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(qiye_zhanghao.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi28, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    private void submit_information() {
        Map<String, Object> params = new HashMap<>();
        params.put("area_name", informationEntity.getArea_name());
        params.put("type", informationEntity.getType());
        params.put("area", informationEntity.getArea());
        params.put("address", informationEntity.getAddress());
        params.put("business_start", informationEntity.getBusiness_start());
        params.put("business_end", informationEntity.getBusiness_end());
        params.put("phone", informationEntity.getPhone());
        params.put("email", informationEntity.getEmail());
        params.put("facility", informationEntity.getFacility());
        params.put("legal_person", informationEntity.getLegal_person());
        params.put("card_num", informationEntity.getCard_num());
        params.put("company_name", informationEntity.getCompany_name());
        params.put("company_code", informationEntity.getCompany_code());
        params.put("gymPlaces", informationEntity.getGymPlaces());
        params.put("gym_pic", informationEntity.getGym_pic());
        params.put("province", informationEntity.getProvince());
        params.put("city", informationEntity.getCity());
        params.put("district", informationEntity.getDistrict());
        params.put("bank_card_num", informationEntity.getBank_card_num());
        params.put("bank_name", informationEntity.getBankName());
        params.put("gym_user_num",SpUtils.getString(getActivity(),AppConstants.CHANGGUAN_NUM));
        Subscription subscription = Network.getInstance("提交审核资料", getActivity())
                .submit_information(params,
                        new ProgressSubscriber<>("提交审核资料", new SubscriberOnNextListener<Bean<CheckBean>>() {
                            @Override
                            public void onNext(Bean<CheckBean> result) {
                                //缓存场馆ID
                                SpUtils.putString(getActivity(), AppConstants.CHANGGUAN_NUM, result.getData().getAreaNum());
                                //获取公司账户的数据
                                get_compny_resource(result.getData().getAreaNum());
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), false));
    }

    private void get_compny_resource(String changguan_number) {
        Map<String, Object> params = new HashMap<>();
        params.put("areaNum", changguan_number);
        Subscription subscription = Network.getInstance("获取公司信息", getActivity())
                .get_compny_information(params,
                        new ProgressSubscriber<>("获取公司信息", new SubscriberOnNextListener<Bean<CompnyBean>>() {
                            @Override
                            public void onNext(Bean<CompnyBean> result) {
                                //切换到提现布局
                                yingye_ziliao_layout.setVisibility(View.GONE);
                                tixian_compny_layout.setVisibility(View.VISIBLE);
                                compny_check_btn.setChecked(true);

                                tixian_compny_name.setText(result.getData().getCardOwner());
                                tixian_qiye_zhanghao.setText(result.getData().getBankCardNum());
                                compny_kaihuhang.setText(result.getData().getBankName());
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), false));


    }


    @Override
    public void onAttach(Context activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if (activity instanceof InformationCheckActivity) {
            mainActivity = (InformationCheckActivity) activity;
            stepView = (StepView) mainActivity.findViewById(R.id.sv);
            viewpager_content = mainActivity.findViewById(R.id.viewpager_content);
        }

    }


    @Override
    public void fetchData() {

    }
}
