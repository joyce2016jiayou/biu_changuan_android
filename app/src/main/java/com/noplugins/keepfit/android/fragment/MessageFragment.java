package com.noplugins.keepfit.android.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.adapter.TabItemAdapter;
import com.noplugins.keepfit.android.util.data.DateHelper;
import com.noplugins.keepfit.android.util.ui.ViewPagerFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageFragment extends ViewPagerFragment {

    private View view;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.zhanghu_message_layout)
    RelativeLayout zhanghu_message_layout;
    @BindView(R.id.system_message_layout)
    RelativeLayout system_message_layout;
    @BindView(R.id.user_message_layout)
    RelativeLayout user_message_layout;
    @BindView(R.id.area_message_layout)
    RelativeLayout area_message_layout;
    @BindView(R.id.lin1)
    LinearLayout lin1;
    @BindView(R.id.lin2)
    LinearLayout lin2;
    @BindView(R.id.lin3)
    LinearLayout lin3;
    @BindView(R.id.lin4)
    LinearLayout lin4;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.circle1)
    ImageView circle1;
    @BindView(R.id.circle2)
    ImageView circle2;
    @BindView(R.id.circle3)
    ImageView circle3;
    @BindView(R.id.circle4)
    ImageView circle4;

    private ArrayList<Fragment> mFragments;

    public static MessageFragment newInstance(String title) {
        MessageFragment fragment = new MessageFragment();
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
            registerReceiver();
        }
        return view;
    }

    //注册广播接收器
    LocalBroadcastManager broadcastManager;
    private void registerReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("update_message_read_status");
        broadcastManager.registerReceiver(mRefreshReceiver, intentFilter);
    }

    private BroadcastReceiver mRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String is_read = intent.getStringExtra("is_read");
            String type_number = intent.getStringExtra("type_number");
            if(type_number.equals("1")){
                if(is_read.equals("true")){//已读,第一个提现不显示
                    circle1.setVisibility(View.GONE);
                }else{//未读
                    circle1.setVisibility(View.GONE);
                }
            }else if(type_number.equals("2")){
                if(is_read.equals("true")){//已读
                    circle2.setVisibility(View.GONE);
                }else{//未读
                    circle2.setVisibility(View.VISIBLE);
                }
            }else if(type_number.equals("3")){
                if(is_read.equals("true")){//已读
                    circle3.setVisibility(View.GONE);
                }else{//未读
                    circle3.setVisibility(View.VISIBLE);
                }
            }else if(type_number.equals("4")){
                if(is_read.equals("true")){//已读
                    circle4.setVisibility(View.GONE);
                }else{//未读
                    circle4.setVisibility(View.VISIBLE);
                }
            }



        }
    };

    private void initView() {
        mFragments = new ArrayList<>();
        mFragments.add(ZhanghuMessageFragment.newInstance("账户消息"));
        mFragments.add(SystemMessageFragment.newInstance("系统消息"));
        mFragments.add(UserMessageFragment.newInstance("用户消息"));
        mFragments.add(AreaSubmitFragment.newInstance("场地申请"));

        zhanghu_message_layout.setOnClickListener(onClickListener);
        system_message_layout.setOnClickListener(onClickListener);
        user_message_layout.setOnClickListener(onClickListener);
        area_message_layout.setOnClickListener(onClickListener);


        TabItemAdapter myAdapter = new TabItemAdapter(getActivity().getSupportFragmentManager(), mFragments);// 初始化adapter
        view_pager.setAdapter(myAdapter); // 设置adapter
        setTabTextColorAndImageView(0);// 更改text的颜色还有图片

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPs) {}

            @Override
            public void onPageSelected(int position) {
                setTabTextColorAndImageView(position);// 更改text的颜色还有图片
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void setTabTextColorAndImageView(int position) {
        switch (position) {
            case 0:
                tv1.setTextSize(20);
                tv1.setTextColor(getActivity().getResources().getColor(R.color.color_FFBA02));
                lin1.setVisibility(View.VISIBLE);
                tv2.setTextSize(18);
                tv2.setTextColor(getActivity().getResources().getColor(R.color.top_heiziti));
                lin2.setVisibility(View.GONE);
                tv3.setTextSize(18);
                tv3.setTextColor(getActivity().getResources().getColor(R.color.top_heiziti));
                lin3.setVisibility(View.GONE);
                tv4.setTextSize(18);
                tv4.setTextColor(getActivity().getResources().getColor(R.color.top_heiziti));
                lin4.setVisibility(View.GONE);
                break;
            case 1:
                tv1.setTextSize(18);
                tv1.setTextColor(getActivity().getResources().getColor(R.color.top_heiziti));
                lin1.setVisibility(View.GONE);
                tv2.setTextSize(20);
                tv2.setTextColor(getActivity().getResources().getColor(R.color.color_FFBA02));
                lin2.setVisibility(View.VISIBLE);
                tv3.setTextSize(18);
                tv3.setTextColor(getActivity().getResources().getColor(R.color.top_heiziti));
                lin3.setVisibility(View.GONE);
                tv4.setTextSize(18);
                tv4.setTextColor(getActivity().getResources().getColor(R.color.top_heiziti));
                lin4.setVisibility(View.GONE);
                break;
            case 2:
                tv1.setTextSize(18);
                tv1.setTextColor(getActivity().getResources().getColor(R.color.top_heiziti));
                lin1.setVisibility(View.GONE);
                tv2.setTextSize(18);
                tv2.setTextColor(getActivity().getResources().getColor(R.color.top_heiziti));
                lin2.setVisibility(View.GONE);
                tv3.setTextSize(20);
                tv3.setTextColor(getActivity().getResources().getColor(R.color.color_FFBA02));
                lin3.setVisibility(View.VISIBLE);
                tv4.setTextSize(18);
                tv4.setTextColor(getActivity().getResources().getColor(R.color.top_heiziti));
                lin4.setVisibility(View.GONE);
                break;
            case 3:
                tv1.setTextSize(18);
                tv1.setTextColor(getActivity().getResources().getColor(R.color.top_heiziti));
                lin1.setVisibility(View.GONE);
                tv2.setTextSize(18);
                tv2.setTextColor(getActivity().getResources().getColor(R.color.top_heiziti));
                lin2.setVisibility(View.GONE);
                tv3.setTextSize(18);
                tv3.setTextColor(getActivity().getResources().getColor(R.color.top_heiziti));
                lin3.setVisibility(View.GONE);
                tv4.setTextSize(20);
                tv4.setTextColor(getActivity().getResources().getColor(R.color.color_FFBA02));
                lin4.setVisibility(View.VISIBLE);
                break;
        }
    }
    @Override
    public void fetchData() {

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.zhanghu_message_layout:
                    view_pager.setCurrentItem(0);
                    break;
                case R.id.system_message_layout:
                    view_pager.setCurrentItem(1);
                    break;
                case R.id.user_message_layout:
                    view_pager.setCurrentItem(2);

                    break;
                case R.id.area_message_layout:
                    view_pager.setCurrentItem(3);

                    break;
            }
        }
    };


}
