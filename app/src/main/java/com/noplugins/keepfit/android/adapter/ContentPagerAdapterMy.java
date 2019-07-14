package com.noplugins.keepfit.android.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ContentPagerAdapterMy extends FragmentPagerAdapter {
    private List<Fragment> m_tabFragments;

    public ContentPagerAdapterMy(FragmentManager fm,List<Fragment> tabFragments) {
        super(fm);
        m_tabFragments=tabFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return m_tabFragments.get(position);
    }

    @Override
    public int getCount() {
        return m_tabFragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //注销，使其不销毁fragment4
        super.destroyItem(container, position, object);
    }
}
