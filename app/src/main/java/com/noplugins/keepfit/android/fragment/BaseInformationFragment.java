package com.noplugins.keepfit.android.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.activity.InformationCheckActivity;
import com.noplugins.keepfit.android.util.ui.StepView;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.wheelpicker.TimePicker;
import cn.qqtheme.framework.wheelview.annotation.TimeMode;
import cn.qqtheme.framework.wheelview.contract.OnTimeSelectedListener;
import cn.qqtheme.framework.wheelview.entity.TimeEntity;
import lib.demo.spinner.MaterialSpinner;


public class BaseInformationFragment extends ViewPagerFragment {

    private View view;
    private StepView stepView;
    TimePicker picker;
    @BindView(R.id.spinner_type)
    MaterialSpinner spinner_type;
    @BindView(R.id.time1_edit)
    TextView time1_edit;
    @BindView(R.id.time2_edit)
    TextView time2_edit;

    public static BaseInformationFragment homeInstance(String title) {
        BaseInformationFragment fragment = new BaseInformationFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_base_information, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();
        }
        return view;
    }

    private void initView() {
        String[] typeArrays = getResources().getStringArray(R.array.identify_types);
        spinner_type.setItems(typeArrays);
        spinner_type.setSelectedIndex(0);
        spinner_type.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

            }
        });
        spinner_type.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                spinner.getSelectedIndex();
            }
        });

        time1_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                time_check(time1_edit);
            }
        });

        time2_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_check(time2_edit);
            }
        });
    }

    private void time_check(TextView textView) {
        picker = new TimePicker(getActivity(), TimeMode.HOUR_24);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        picker.setDefaultValue(new TimeEntity(hour, minute));
        picker.showAtBottom();
        picker.setOnTimeSelectedListener(new OnTimeSelectedListener() {
            @Override
            public void onItemSelected(int hour, int minute, int second) {
                if(minute<=9){
                    textView.setText(hour+":0"+minute);
                }else{
                    textView.setText(hour+":"+minute);
                }
            }
        });
    }


    @Override
    public void onAttach(Context activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if (activity instanceof InformationCheckActivity) {
            InformationCheckActivity mainActivity = (InformationCheckActivity) activity;
            stepView = (StepView)mainActivity.findViewById(R.id.sv);
        }
    }


    @Override
    public void fetchData() {

    }
}
