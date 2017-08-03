package com.likeit.a51scholarship.adapters;

/**
 * Created by Administrator on 2017/7/31.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.likeit.a51scholarship.activitys.newsfragment.NewFragment01;

import java.util.List;

public class NewTabAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private String[] titles;

    public NewTabAdapter(FragmentManager fm, String[] titles,List<Fragment> fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }
    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
