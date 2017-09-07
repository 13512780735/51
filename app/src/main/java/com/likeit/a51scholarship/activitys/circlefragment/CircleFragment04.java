package com.likeit.a51scholarship.activitys.circlefragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.userdetailsfragment.UserDetailsFragment01;
import com.likeit.a51scholarship.fragments.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CircleFragment04 extends BaseFragment {

    @Override
    protected int setContentView() {
        return R.layout.fragment_circle_fragment04;
    }

    @Override
    protected void lazyLoad() {

    }
    public static Fragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text", text);
        UserDetailsFragment01 fragment = new UserDetailsFragment01();
        fragment.setArguments(args);
        return fragment;
    }

}
