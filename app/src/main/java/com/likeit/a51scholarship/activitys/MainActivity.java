package com.likeit.a51scholarship.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.adapters.HomeViewPagerAdapter;
import com.likeit.a51scholarship.fragments.HomeFragment01;
import com.likeit.a51scholarship.fragments.MainFragment;
import com.likeit.a51scholarship.fragments.ShowFragment;
import com.likeit.a51scholarship.utils.AndroidWorkaround;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.view.NoScrollViewPager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends SlidingFragmentActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.rbHome)
    RadioButton mRbHome;
    @BindView(R.id.rbCircle)
    RadioButton mRbCircle;
    @BindView(R.id.rbFind)
    RadioButton mRbFind;
    @BindView(R.id.rbMessage)
    RadioButton mRbMessage;
    @BindView(R.id.rbTool)
    RadioButton mRbTool;
    @BindView(R.id.rgTools)
    RadioGroup mRgTools;
    @BindView(R.id.home_viewpager)
    NoScrollViewPager mViewPager;
    private MainActivity mContext;
    private View main;
    private HomeViewPagerAdapter adapter;
    private SlidingMenu menu;
    private LinearLayout schoolLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
//        View main = getLayoutInflater().from(this).inflate(R.layout.activity_main, null);
//        main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        MyActivityManager.getInstance().addActivity(this);
//
//        setContentView(main);
//        mContext = this;
//        setMiuiStatusBarDarkMode(this, true);
//        Window window = this.getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        // 透明导航栏
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
//            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
//        }
        ButterKnife.bind(this);
        initView();
        initMenu();
        //showIndentDialog1();
    }

    private void showIndentDialog1() {
        ShowFragment dialog = new ShowFragment();
        dialog.show(getSupportFragmentManager(), "EditNameDialog");
    }


    public void refresh() {
        menu.toggle();
    }

    private void initMenu() {
        HomeFragment01 homeFragment = new HomeFragment01();
        menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT);
        //设置触摸屏幕的模式
        //  menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowWidth(getWindowManager().getDefaultDisplay().getWidth() / 40);
        menu.setShadowDrawable(R.color.colorPrimaryDark);
        //设置滑动菜单的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        //设置渐入渐出效果的值
        menu.setFadeEnabled(true);//是否有渐变
        menu.setFadeDegree(0.4f);
        //把滑动菜单添加进所有的Activity中
        // menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        setBehindContentView(R.layout.left_menu);//设置SlidingMenu滑出来之后的布局
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, homeFragment).commit();
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
    }


    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void initView() {
        adapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(0);
        mRgTools.setOnCheckedChangeListener(this);
        MainFragment fragment = new MainFragment();
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
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        mViewPager.setCurrentItem(radioGroup.indexOfChild(radioGroup.findViewById(checkedId)), false);
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                } else {
                 //   MyActivityManager.getInstance().moveTaskToBack(mContext);// 不退出，后台运行
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

}
