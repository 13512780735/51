package com.likeit.a51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
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


public class LiveListActivity extends FragmentActivity {
    @BindView(R.id.top_bar_title)
    TextView tvHeader;
    @BindView(R.id.liveList_sliding_tabs)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.liveList_viewpager)
    ViewPager viewpager;
    private LiveListActivity mContext;
    private Window window;
    String[] names = {"直播中", "预告", "直播结束", "免费", "收费"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_live_list);
        View main = getLayoutInflater().from(this).inflate(R.layout.activity_live_list, null);
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
        tvHeader.setText("直播");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            list.add(names[i].toString());
        }

        viewpager.setAdapter(new LiveTabAdapter(getSupportFragmentManager(), list));
        slidingTabLayout.setCustomTabView(R.layout.custom_tab_view, R.id.tab_item);
        slidingTabLayout.setTabTitleTextSize(14);//标题字体大小
        slidingTabLayout.setTitleTextColor(this.getResources().getColor(R.color.login_btn_bg_color), this.getResources().getColor(R.color.defualt_textcolor_d));//标题字体颜色
        slidingTabLayout.setTabStripWidth(50);//滑动条宽度
        slidingTabLayout.setSelectedIndicatorColors(this.getResources().getColor(R.color.login_btn_bg_color));//滑动条颜色
        slidingTabLayout.setDistributeEvenly(true); //均匀平铺选项卡
        slidingTabLayout.setViewPager(viewpager);
    }
    @OnClick({R.id.top_bar_back_img,R.id.top_bar_right_img})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.top_bar_back_img:
                onBackPressed();
                break;
            case R.id.top_bar_right_img:
                Intent intent=new Intent(this,SearchInfoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
