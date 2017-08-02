package com.likeit.a51scholarship.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.likeit.a51scholarship.activitys.answersfragment.AnswersFragment01;
import com.likeit.a51scholarship.activitys.answersfragment.AnswersFragment02;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017\7\20 0020.
 */

public class AnswersViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> frList;

    public AnswersViewPagerAdapter(FragmentManager fm) {
        super(fm);
        frList = new ArrayList<Fragment>();
        frList.add(new AnswersFragment01());
        frList.add(new AnswersFragment02());


    }

    @Override
    public Fragment getItem(int position) {
        // return frList.get(position);
        if (position == 0) {
            return frList.get(0);
        } else if (position == 1) {
            return frList.get(1);
        }
        return null;
    }

    @Override
    public int getCount() {
        return frList.size();
    }

}
