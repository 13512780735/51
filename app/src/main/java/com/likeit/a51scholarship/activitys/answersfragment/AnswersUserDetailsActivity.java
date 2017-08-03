package com.likeit.a51scholarship.activitys.answersfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.Container;
import com.likeit.a51scholarship.activitys.livefragment.LiveFragment01;
import com.likeit.a51scholarship.activitys.newsfragment.NewFragment01;
import com.likeit.a51scholarship.activitys.userdetailsfragment.UserDetailsFragment01;
import com.likeit.a51scholarship.activitys.userdetailsfragment.UserDetailsFragment02;
import com.likeit.a51scholarship.adapters.AnswersUserDetailsTabAdapter;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.view.NoScrollViewPager01;
import com.likeit.a51scholarship.view.SlidingTabLayout;

import java.util.ArrayList;
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
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.answer_user_details_viewpager)
    NoScrollViewPager01 viewpager;
    private AnswersUserDetailsActivity mContext;
    private String[] titles=new String[]{"资料","直播","动态"};
    private AnswersUserDetailsTabAdapter adapter;
    private List<Fragment> fragments=new ArrayList<>();
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

        fragments.add(new UserDetailsFragment01());
        fragments.add(new UserDetailsFragment02());
        fragments.add(new UserDetailsFragment01());
        adapter=new AnswersUserDetailsTabAdapter(getSupportFragmentManager(),titles,fragments);
        viewpager.setAdapter(adapter);
        viewpager.setNoScroll(false);
        slidingTabLayout.setCustomTabView(R.layout.custom_tab_view, R.id.tab_item);
        slidingTabLayout.setTabTitleTextSize(14);//标题字体大小
        slidingTabLayout.setTitleTextColor(this.getResources().getColor(R.color.login_btn_bg_color), this.getResources().getColor(R.color.defualt_textcolor_d));//标题字体颜色
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        slidingTabLayout.setTabStripWidth(width/(titles.length+1));//滑动条宽度
        slidingTabLayout.setSelectedIndicatorColors(this.getResources().getColor(R.color.login_btn_bg_color));//滑动条颜色
        slidingTabLayout.setDistributeEvenly(true); //均匀平铺选项卡
        slidingTabLayout.setViewPager(viewpager);
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
