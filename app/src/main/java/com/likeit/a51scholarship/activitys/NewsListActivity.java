package com.likeit.a51scholarship.activitys;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.pk4pk.baseappmoudle.ui.SlidingTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NewsListActivity extends Container {
    @BindView(R.id.top_bar_back_img)
    ImageView topBarBackImg;
    @BindView(R.id.top_bar_title)
    TextView topBarTitle;
    @BindView(R.id.top_bar_right_img)
    ImageView topBarRightImg;
    @BindView(R.id.top_bar_edit_img)
    ImageView topBarEditImg;
    @BindView(R.id.sliding_tabs)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.top_bar_back_img, R.id.top_bar_right_img, R.id.top_bar_edit_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back_img:
                toFinish();
                break;
            case R.id.top_bar_right_img:
                toActivity(SearchInfoActivity.class);
                break;
            case R.id.top_bar_edit_img:
                toActivity(SendNewsActivity.class);
                break;
        }
    }
}
