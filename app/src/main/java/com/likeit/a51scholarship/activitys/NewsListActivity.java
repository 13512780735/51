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
import com.likeit.a51scholarship.activitys.newsfragment.NewFragment01;
import com.likeit.a51scholarship.activitys.userdetailsfragment.UserDetailsFragment01;
import com.likeit.a51scholarship.activitys.userdetailsfragment.UserDetailsFragment02;
import com.likeit.a51scholarship.adapters.AnswersUserDetailsTabAdapter;
import com.likeit.a51scholarship.adapters.NewTabAdapter;
import com.likeit.a51scholarship.utils.AndroidWorkaround;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.likeit.a51scholarship.activitys.Container.setMiuiStatusBarDarkMode;


public class NewsListActivity extends FragmentActivity {
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
    private String[] titles=new String[]{"头条", "热点", "视频", "留学", "社会", "奇闻"};
    private NewsListActivity mContext;
    private Window window;
    private NewTabAdapter adapter;
    private List<Fragment> fragments=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_news_list);
        View main = getLayoutInflater().from(this).inflate(R.layout.activity_news_list, null);
        main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        MyActivityManager.getInstance().addActivity(this);
        setContentView(main);
        mContext = this;
        setMiuiStatusBarDarkMode(this, true);
        window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {


        fragments.add(new NewFragment01());
        fragments.add(new NewFragment01());
        fragments.add(new NewFragment01());
        fragments.add(new NewFragment01());
        fragments.add(new NewFragment01());
        fragments.add(new NewFragment01());
        adapter=new NewTabAdapter(getSupportFragmentManager(),titles,fragments);
        viewpager.setAdapter(adapter);
        slidingTabLayout.setCustomTabView(R.layout.custom_tab_view, R.id.tab_item);
        slidingTabLayout.setTabTitleTextSize(14);//标题字体大小
        slidingTabLayout.setTitleTextColor(this.getResources().getColor(R.color.login_btn_bg_color), this.getResources().getColor(R.color.defualt_textcolor_d));//标题字体颜色
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        slidingTabLayout.setTabStripWidth(width/(titles.length+1));//滑动条宽度
        slidingTabLayout.setSelectedIndicatorColors(this.getResources().getColor(R.color.login_btn_bg_color));//滑动条颜色
        slidingTabLayout.setDistributeEvenly(false); //均匀平铺选项卡
        slidingTabLayout.setViewPager(viewpager);
    }


    @OnClick({R.id.top_bar_back_img, R.id.top_bar_right_img, R.id.top_bar_edit_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back_img:
                onBackPressed();
                break;
            case R.id.top_bar_right_img:
                //oActivity(SearchInfoActivity.class);
                Intent intentSearchInfo=new Intent(this,SearchInfoActivity.class);
                startActivity(intentSearchInfo);
                break;
            case R.id.top_bar_edit_img:
                // toActivity(SendNewsActivity.class);
                Intent intentSendNews=new Intent(this,SendNewsActivity.class);
                startActivity(intentSendNews);
                break;
        }
    }
}
