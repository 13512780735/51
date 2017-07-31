package com.likeit.a51scholarship.activitys.newsfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.fragments.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewFragment01 extends BaseFragment {

    PullToRefreshListView mPullToRefreshListView;

    @Override
    protected int setContentView() {
        return R.layout.fragment_new_fragment01;
    }

    @Override
    protected void lazyLoad() {
        initView();
    }

    private void initView() {
        mPullToRefreshListView = findViewById(R.id.news_header_listview);

    }

    public static NewFragment01 newInstance(String text) {

        Bundle args = new Bundle();
        args.putString("text", text);
        NewFragment01 fragment = new NewFragment01();
        fragment.setArguments(args);
        return fragment;
    }
}
