package com.likeit.a51scholarship.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.userdetailsfragment.UserDetailsFragment01;
import com.likeit.a51scholarship.activitys.userdetailsfragment.UserDetailsFragment02;
import com.likeit.a51scholarship.adapters.AnswersUserDetailsTabAdapter;
import com.likeit.a51scholarship.view.NoScrollViewPager01;
import com.likeit.a51scholarship.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jaydenxiao.com.expandabletextview.ExpandableTextView;


public class CircleDetailsActivity extends Container implements
        PullToRefreshBase.OnRefreshListener2<ScrollView>{
    @BindView(R.id.backBtn)
    Button btBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.circle_details_desc_tv)
    ExpandableTextView circleDetailsDescTv;
    @BindView(R.id.circle_details_scrollview)
    PullToRefreshScrollView mPullToRefreshScrollView;
    @BindView(R.id.circle_details_sliding_tabs)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.circle_details_viewpager)
    NoScrollViewPager01 viewpager;
    private String[] titles=new String[]{"全部","精华","成员","群组"};
    private AnswersUserDetailsTabAdapter adapter;
    private List<Fragment> fragments=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_details);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvHeader.setText("圈子详情");
        circleDetailsDescTv.setText("美国留学党们，快快加入吧~美国留学党们，快快加入吧~美国留学党们，快快加入吧~美国留学党们，快快加入吧~美国留学党们，快快加入吧~");
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
        fragments.add(new UserDetailsFragment02());
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
    @OnClick({R.id.backBtn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.backBtn:
                onBackPressed();
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
}
