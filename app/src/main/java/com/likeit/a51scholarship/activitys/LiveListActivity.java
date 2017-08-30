package com.likeit.a51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.livefragment.LiveDetailsFragment01;
import com.likeit.a51scholarship.activitys.livefragment.LiveFragment01;
import com.likeit.a51scholarship.activitys.userdetailsfragment.UserDetailsFragment01;
import com.likeit.a51scholarship.activitys.userdetailsfragment.UserDetailsFragment02;
import com.likeit.a51scholarship.adapters.LiveDetailsPageAdapter;
import com.likeit.a51scholarship.adapters.LiveTabAdapter;
import com.likeit.a51scholarship.utils.AndroidWorkaround;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.likeit.a51scholarship.activitys.Container.setMiuiStatusBarDarkMode;


public class LiveListActivity extends Container {
    @BindView(R.id.top_bar_title)
    TextView tvHeader;
    @BindView(R.id.liveList_sliding_tabs)
    TabLayout mTabLayout;
    @BindView(R.id.liveList_viewpager)
    ViewPager viewpager;
    private LiveListActivity mContext;
    private ArrayList<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_list);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvHeader.setText("直播");
        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(viewpager);
        mDatas = new ArrayList<String>(Arrays.asList("直播中", "预告", "直播结束", "免费", "收费"));
        List<Fragment> mfragments = new ArrayList<Fragment>();
        mfragments.add(new LiveFragment01());
        mfragments.add(new LiveFragment01());
        mfragments.add(new LiveFragment01());
        mfragments.add(new LiveFragment01());
        mfragments.add(new LiveFragment01());
        //Toast.makeText(this,mDatas.toString(),Toast.LENGTH_SHORT).show();
     viewpager.setAdapter(new LiveDetailsPageAdapter(getSupportFragmentManager(), mfragments, mDatas));
        viewpager.setCurrentItem(0);
    }

    @OnClick({R.id.top_bar_back_img, R.id.top_bar_right_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back_img:
                onBackPressed();
                break;
            case R.id.top_bar_right_img:
                Intent intent = new Intent(this, SearchInfoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
