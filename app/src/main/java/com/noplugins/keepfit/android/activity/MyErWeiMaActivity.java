package com.noplugins.keepfit.android.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwj.widget.viewpagerindicator.ViewPagerIndicator;
import com.noplugins.keepfit.android.R;
import com.noplugins.keepfit.android.base.BaseActivity;
import com.noplugins.keepfit.android.fragment.ErWeiMaFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyErWeiMaActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    ImageView back_btn;
    @BindView(R.id.weweima_viewpage)
    ViewPager weweima_viewpage;
    @BindView(R.id.indicator_spring)
    ViewPagerIndicator indicator_spring;
    @BindView(R.id.title_tv)
    TextView title_tv;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> strings = new ArrayList<>();
    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_my_er_wei_ma);
        ButterKnife.bind(this);
        isShowTitle(false);

    }

    @Override
    public void doBusiness(Context mContext) {
        strings.add("用户二维码");
        strings.add("教练二维码");

        for (int i=0;i<strings.size();i++){
            ErWeiMaFragment fragment = new ErWeiMaFragment();
            Bundle bundle = new Bundle();
            bundle.putString("key",strings.get(i));
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        int allNum=100000;
        final int num= strings.size();  // 100/7=14..2    7*7=49  20/7=2..6   7   30/7=4..2    14   40/7=5..5   14
        int firstIndex=0;
        if(num>0)
        {
            firstIndex=allNum/num/2*num;
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),strings,num,allNum);
        weweima_viewpage.setAdapter(adapter);
        weweima_viewpage.setCurrentItem(firstIndex);
        indicator_spring.setViewPager(weweima_viewpage,num);
        weweima_viewpage.setOnPageChangeListener(onPageChangeListener);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.e("JJ分开了第三代",""+position);
            if(position==50000){
                title_tv.setText("用户端二维码");
            }else{
                title_tv.setText("教练端二维码");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.e("JJ分开了第",""+state);

        }
    };


    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private FragmentManager mSupportFragmentManager;
        private  int mNum;
        private  int mAllNum;
        private List<String> mStrings;


        public ViewPagerAdapter(FragmentManager supportFragmentManager, List  strings, int num, int allNum) {
            super(supportFragmentManager);
            mSupportFragmentManager = supportFragmentManager;
            mNum = num;
            mStrings = strings;
            mAllNum = allNum;
        }


        @Override
        public Fragment getItem(int position) {



            ErWeiMaFragment blankFragment=new ErWeiMaFragment();
            Bundle bundle = new Bundle();
            if(mNum>0)
            {
                bundle.putString("key",mStrings.get(position%mNum));
            }
            blankFragment.setArguments(bundle);
            return   blankFragment;
        }


        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mAllNum;
        }
    }


}
