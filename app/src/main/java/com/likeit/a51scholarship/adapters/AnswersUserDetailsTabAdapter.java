package com.likeit.a51scholarship.adapters;

/**
 * Created by Administrator on 2017/7/31.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.likeit.a51scholarship.activitys.circlefragment.CircleFragment01;
import com.likeit.a51scholarship.activitys.circlefragment.CircleFragment02;
import com.likeit.a51scholarship.activitys.circlefragment.CircleFragment03;
import com.likeit.a51scholarship.activitys.circlefragment.CircleFragment04;
import com.likeit.a51scholarship.activitys.livefragment.LiveFragment01;
import com.likeit.a51scholarship.activitys.userdetailsfragment.UserDetailsFragment01;
import com.likeit.a51scholarship.fragments.HomeFragment02;
import com.likeit.a51scholarship.fragments.HomeFragment03;
import com.likeit.a51scholarship.fragments.HomeFragment04;
import com.likeit.a51scholarship.fragments.HomeFragment05;
import com.likeit.a51scholarship.fragments.MainFragment;

import java.util.ArrayList;
import java.util.List;

public class AnswersUserDetailsTabAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> frList;

    public AnswersUserDetailsTabAdapter(FragmentManager fm) {
        super(fm);
        frList = new ArrayList<Fragment>();
        frList.add(new CircleFragment01());
        frList.add(new CircleFragment02());
        frList.add(new CircleFragment03());
        frList.add(new CircleFragment04());

    }

    @Override
    public Fragment getItem(int position) {
        // return frList.get(position);
        if (position == 0) {
            return frList.get(0);
        } else if (position == 1) {
            return frList.get(1);
        } else if (position == 2) {
            return frList.get(2);
        } else if (position == 3) {
            return frList.get(3);
        } else if (position == 4) {
            return frList.get(4);
        }
        return null;
    }

    @Override
    public int getCount() {
        return frList.size();
    }
}
