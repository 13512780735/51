package com.likeit.a51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.livefragment.LiveFragment01;
import com.likeit.a51scholarship.activitys.userdetailsfragment.UserDetailsFragment01;
import com.likeit.a51scholarship.activitys.userdetailsfragment.UserDetailsFragment02;
import com.likeit.a51scholarship.adapters.LiveTabAdapter;
import com.likeit.a51scholarship.utils.AndroidWorkaround;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.likeit.a51scholarship.activitys.Container.setMiuiStatusBarDarkMode;


public class LiveListActivity extends Container {
    @BindView(R.id.top_bar_title)
    TextView tvHeader;
    @BindView(R.id.liveList_sliding_tabs)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.liveList_viewpager)
    ViewPager viewpager;
    private LiveListActivity mContext;
    private Window window;
    private String[] titles=new String[]{"直播中", "预告", "直播结束", "免费", "收费"};
    private List<Fragment> fragments=new ArrayList<>();
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
        fragments.add(new LiveFragment01());
        fragments.add(new LiveFragment01());
        fragments.add(new LiveFragment01());
        fragments.add(new LiveFragment01());
        fragments.add(new LiveFragment01());
        viewpager.setAdapter(new LiveTabAdapter(getSupportFragmentManager(),titles, fragments));
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
