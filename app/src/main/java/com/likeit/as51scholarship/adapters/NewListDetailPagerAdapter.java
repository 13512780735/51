package com.likeit.as51scholarship.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.likeit.as51scholarship.model.NewTabBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */

public class NewListDetailPagerAdapter extends FragmentStatePagerAdapter {


    private List<Fragment> mfragments;
    private List<NewTabBean> list_Title;

    public NewListDetailPagerAdapter(FragmentManager fm, List<Fragment> fragmentlists, List<NewTabBean> list_Title) {
        super(fm);
        this.mfragments = fragmentlists;
        this.list_Title = list_Title;
    }


    @Override
    public Fragment getItem(int position) {
        return mfragments.get(position);
    }

    @Override
    public int getCount() {
        return list_Title.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return (CharSequence) list_Title.get(position).getTitle();
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}
