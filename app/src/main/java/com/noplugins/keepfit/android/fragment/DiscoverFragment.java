package com.noplugins.keepfit.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;
import java.util.HashMap;
import java.util.Map;
import butterknife.ButterKnife;

/**
 * 发现页
 */
public class DiscoverFragment extends ViewPagerFragment {
    private View view;


    public static DiscoverFragment newInstance(String title) {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.discovers_fragment, container, false);
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
