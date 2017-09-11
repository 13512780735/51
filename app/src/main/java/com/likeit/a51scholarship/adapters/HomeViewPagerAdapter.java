package com.likeit.a51scholarship.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.likeit.a51scholarship.fragments.HomeFragment02;
import com.likeit.a51scholarship.fragments.HomeFragment03;
import com.likeit.a51scholarship.fragments.HomeFragment04;
import com.likeit.a51scholarship.fragments.HomeFragment05;
import com.likeit.a51scholarship.fragments.MainFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017\7\20 0020.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> frList;

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
        frList = new ArrayList<Fragment>();
        frList.add(new MainFragment());
        frList.add(new HomeFragment02());
        frList.add(new HomeFragment03());
        frList.add(new HomeFragment04());
  // frList.add(new HomeFragment05());

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
      /*  else if (position == 5) {
            return frList.get(5);
       }*/
        return null;
    }

    @Override
    public int getCount() {
        return frList.size();
    }

}
