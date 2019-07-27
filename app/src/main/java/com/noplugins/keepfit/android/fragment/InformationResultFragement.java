package com.noplugins.keepfit.android.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;

import butterknife.ButterKnife;

public class InformationResultFragement extends ViewPagerFragment {

    private View view;




    public static InformationResultFragement homeInstance(String title) {
        InformationResultFragement fragment = new InformationResultFragement();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_information_result_fragement, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();

        }
        return view;
    }
    private void initView() {

    }



    @Override
    public void fetchData() {

    }
}
