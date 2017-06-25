package com.bz.netdiary.ui.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class AppTitleFragmentPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragmentList;
    String[] titleArr;

    public AppTitleFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titleArr) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleArr = titleArr;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArr[position];
    }
}