package com.likeit.a51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.adapters.AnswersUserDetailsTabAdapter;
import com.likeit.a51scholarship.fragments.MainFragment;
import com.likeit.a51scholarship.view.CustomViewpager;
import com.likeit.a51scholarship.view.NoScrollViewPager01;
import com.likeit.a51scholarship.view.NoScrollViewPager02;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jaydenxiao.com.expandabletextview.ExpandableTextView;


public class CircleDetailsActivity extends Container implements
        PullToRefreshBase.OnRefreshListener2<ScrollView>, ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.backBtn)
    Button btBack;
    @BindView(R.id.circle_details_attention)
    Button btAttention;//关注
    @BindView(R.id.circle_details_attention01)
    Button btAttention01;//未关注
    @BindView(R.id.circle_details_Posted)
    Button btPosted;//发帖
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.circle_details_posts)
    TextView tvPosts;
    @BindView(R.id.circle_details_membership)
    TextView tvMember;
    @BindView(R.id.circle_details_desc_tv)
    ExpandableTextView circleDetailsDescTv;
    @BindView(R.id.circle_details_scrollview)
    PullToRefreshScrollView mPullToRefreshScrollView;
    @BindView(R.id.circle_details_viewpager)
    NoScrollViewPager02 viewpager;
    @BindView(R.id.rgTools)
    RadioGroup mRgTools;
    private String circleId;
    private String circleTitle;
    private String circleDetail;
    private String circleLogo;
    private String circleMemberNum;
    private String circlePostNum;
    private String circleIsFollow;
    private AnswersUserDetailsTabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        circleDetail = intent.getStringExtra("circleDetail");
        circleId = intent.getStringExtra("circleId");
        circleTitle = intent.getStringExtra("circleTitle");
        circleLogo = intent.getStringExtra("circleLogo");
        circleMemberNum = intent.getStringExtra("circleMemberNum");
        circlePostNum = intent.getStringExtra("circlePostNum");
        circleIsFollow = intent.getStringExtra("circleIsFollow");
        initView();
    }

    private void initView() {
        tvHeader.setText("圈子详情");
        if ("1".equals(circleIsFollow)) {
            btAttention01.setVisibility(View.VISIBLE);
        } else {
            btAttention.setVisibility(View.VISIBLE);
        }
        tvPosts.setText("帖子:" + circlePostNum);
        tvMember.setText("成员数:" + circleMemberNum);
        circleDetailsDescTv.setText(circleDetail);
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

        adapter = new AnswersUserDetailsTabAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(this);
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(0);
        mRgTools.setOnCheckedChangeListener(this);
        MainFragment fragment = new MainFragment();
        viewpager.setAdapter(adapter);
     viewpager.setNoScroll(false);

    }

    @OnClick({R.id.backBtn, R.id.circle_details_Posted})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.circle_details_Posted:
                // toActivity(SendNewsActivity.class);
                Intent intent = new Intent(mContext, SendNewsActivity.class);
                intent.putExtra("uid", "2");
                intent.putExtra("gid", circleId);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        mPullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        mPullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mRgTools.check(mRgTools.getChildAt(position).getId());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        viewpager.setCurrentItem(group.indexOfChild(group.findViewById(checkedId)), false);
    }
}
