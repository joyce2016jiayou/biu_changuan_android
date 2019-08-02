package com.noplugins.keepfit.android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.activity.InformationCheckActivity;
import com.noplugins.keepfit.android.entity.InformationEntity;
import com.noplugins.keepfit.android.entity.UrlEntity;
import com.noplugins.keepfit.android.util.net.Network;
import com.noplugins.keepfit.android.util.net.entity.Bean;
import com.noplugins.keepfit.android.util.net.progress.GsonSubscriberOnNextListener;
import com.noplugins.keepfit.android.util.net.progress.ProgressSubscriberNew;
import com.noplugins.keepfit.android.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.android.util.ui.NoScrollViewPager;
import com.noplugins.keepfit.android.util.ui.StepView;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import rx.Subscription;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;


public class BusinessInformationFragment extends ViewPagerFragment {

    @BindView(R.id.faren_name)
    EditText faren_name;
    @BindView(R.id.icon_id_card)
    EditText icon_id_card;
    @BindView(R.id.qiye_name)
    EditText qiye_name;
    @BindView(R.id.qiye_zhucehao)
    EditText qiye_zhucehao;
    @BindView(R.id.submit_btn)
    LinearLayout submit_btn;


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
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_value()) {
                    informationEntity = mainActivity.informationEntity;//获取基础资料信息
                    informationEntity.setLegal_person(faren_name.getText().toString());
                    informationEntity.setCard_num(icon_id_card.getText().toString());
                    informationEntity.setCompany_name(qiye_name.getText().toString());
                    informationEntity.setCompany_code(qiye_zhucehao.getText().toString());

                    //提交审核资料
                    submit_information();
                } else {
                    return;
                }

            }
        });
    }

    private boolean check_value() {
        if (TextUtils.isEmpty(faren_name.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi12, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(icon_id_card.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi13, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(qiye_name.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi14, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(qiye_zhucehao.getText())) {
            Toast.makeText(getActivity(), R.string.alert_dialog_tishi15, Toast.LENGTH_SHORT).show();
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
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);

        Log.e(TAG, "提交基础资料参数：" + json_params);
        subscription = Network.getInstance("提交审核资料", getActivity())
                .submit_information(requestBody, new ProgressSubscriberNew<>(String.class, new GsonSubscriberOnNextListener<String>() {
                    @Override
                    public void on_post_entity(String entity, String s) {
                        Log.e("提交审核资料成功", entity + "提交审核资料成功" + s);
                        viewpager_content.setCurrentItem(2);
                        int step = stepView.getCurrentStep();//设置进度条
                        stepView.setCurrentStep((step + 1) % stepView.getStepNum());

                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {

                    }

                    @Override
                    public void onError(String error) {
                        Log.e("提交审核资料失败", "提交审核资料失败:" + error);

                    }
                }, getActivity(), true));
    }

    ImageView back_btn;

    @Override
    public void onAttach(Context activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if (activity instanceof InformationCheckActivity) {
            mainActivity = (InformationCheckActivity) activity;
            stepView = (StepView) mainActivity.findViewById(R.id.sv);
            viewpager_content = mainActivity.findViewById(R.id.viewpager_content);
            back_btn = mainActivity.findViewById(R.id.back_btn);
        }

    }


    @Override
    public void fetchData() {

    }
}
