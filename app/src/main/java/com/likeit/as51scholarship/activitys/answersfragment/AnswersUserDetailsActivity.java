package com.likeit.as51scholarship.activitys.answersfragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.Container;
import com.likeit.as51scholarship.activitys.livefragment.LiveDetailsFragment01;
import com.likeit.as51scholarship.activitys.userdetailsfragment.UserDetailsFragment01;
import com.likeit.as51scholarship.activitys.userdetailsfragment.UserDetailsFragment02;
import com.likeit.as51scholarship.adapters.LiveDetailsPageAdapter;
import com.likeit.as51scholarship.utils.MyActivityManager;
import com.likeit.as51scholarship.view.NoScrollViewPager01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AnswersUserDetailsActivity extends Container implements
        PullToRefreshBase.OnRefreshListener2<ScrollView> {
    @BindView(R.id.iv_header_left)
    ImageView ivLeft;
    @BindView(R.id.iv_header_right)
    ImageView ivRight;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.answer_user_details_scrollview)
    PullToRefreshScrollView mPullToRefreshScrollView;
    @BindView(R.id.answer_user_details_sliding_tabs)
    TabLayout mTabLayout;
    @BindView(R.id.answer_user_details_viewpager)
    NoScrollViewPager01 viewpager;
    private AnswersUserDetailsActivity mContext;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers_user_details);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvHeader.setText("资料卡");
        ivLeft.setImageResource(R.mipmap.answers_details_icon_left);
        ivRight.setImageResource(R.mipmap.answers_details_add_friend);
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshScrollView.setOnRefreshListener(this);
        mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间");
        mPullToRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel("下拉刷新");
//          mPullRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel(
//                      "refreshingLabel");
        mPullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新");

        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(viewpager);
        mDatas = new ArrayList<String>(Arrays.asList("资料","直播","动态"));
        List<Fragment> mfragments = new ArrayList<Fragment>();
        mfragments.add(new UserDetailsFragment01());
        mfragments.add(new UserDetailsFragment02());
        mfragments.add(new LiveDetailsFragment01());
        mfragments.add(new UserDetailsFragment02());
        //Toast.makeText(this,mDatas.toString(),Toast.LENGTH_SHORT).show();
       viewpager.setAdapter(new LiveDetailsPageAdapter(getSupportFragmentManager(), mfragments, mDatas));
        viewpager.setCurrentItem(0);
    }

    @OnClick({R.id.iv_header_left, R.id.iv_header_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                onBackPressed();
                break;
            case R.id.iv_header_right:
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        //ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
        mPullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        mPullToRefreshScrollView.onRefreshComplete();
    }
}
