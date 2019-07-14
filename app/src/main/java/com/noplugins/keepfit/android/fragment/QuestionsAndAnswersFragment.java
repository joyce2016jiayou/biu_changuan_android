package com.noplugins.keepfit.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;
import butterknife.ButterKnife;

public class QuestionsAndAnswersFragment  extends ViewPagerFragment {

    private View view;



    public static QuestionsAndAnswersFragment newInstance(String title) {
        QuestionsAndAnswersFragment fragment = new QuestionsAndAnswersFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.questions_and_answers_fragment, container, false);
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
