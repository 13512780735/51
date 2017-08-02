package com.likeit.a51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.adapters.AnswersViewPagerAdapter;
import com.likeit.a51scholarship.utils.AndroidWorkaround;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.view.NoScrollViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.likeit.a51scholarship.activitys.Container.setMiuiStatusBarDarkMode;


public class AnswersActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.iv_header_left)
    ImageView ivLeft;
    @BindView(R.id.iv_header_right)
    ImageView ivRight;
    @BindView(R.id.iv_header_right01)
    ImageView ivRight01;
    @BindView(R.id.answers_rgTools)
    RadioGroup rgTools;
    @BindView(R.id.answers_rbIssue)
    RadioButton rbIssue;
    @BindView(R.id.answers_rbSenior)
    RadioButton rbSenior;
    @BindView(R.id.answers_viewpager)
    NoScrollViewPager mViewPager;
    private AnswersViewPagerAdapter adapter;
    private AnswersActivity mContext;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_answers);
        View main = getLayoutInflater().from(this).inflate(R.layout.activity_answers, null);
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
        //setContentView(R.layout.activity_school_detail);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setImageResource(R.mipmap.icon_return);
        ivRight.setImageResource(R.mipmap.icon_issue);
        ivRight01.setImageResource(R.mipmap.nav_icon_search_sel);
        adapter = new AnswersViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(0);
        rgTools.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.iv_header_left, R.id.iv_header_right, R.id.iv_header_right01})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                onBackPressed();
                break;
            case R.id.iv_header_right:
                Intent intentAsk = new Intent(this, AskActivity.class);
                startActivity(intentAsk);
                break;
            case R.id.iv_header_right01:
                Intent intentSearchInfo = new Intent(this, SearchInfoActivity.class);
                startActivity(intentSearchInfo);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        rgTools.check(rgTools.getChildAt(position).getId());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        mViewPager.setCurrentItem(group.indexOfChild(group.findViewById(checkedId)), false);
    }
}
