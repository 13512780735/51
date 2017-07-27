package com.likeit.a51scholarship.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.fragments.PictureSlideFragment;
import com.likeit.a51scholarship.utils.AndroidWorkaround;
import com.likeit.a51scholarship.utils.MyActivityManager;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.likeit.a51scholarship.activitys.Container.setMiuiStatusBarDarkMode;


public class SchoolDetailActivity extends FragmentActivity {
    @BindView(R.id.iv_header_left)
    ImageView iv_header_left;
    @BindView(R.id.iv_header_right)
    ImageView iv_header_right;
    @BindView(R.id.tv_header)
    TextView tv_header;
    @BindView(R.id.tv_indicator)
    TextView tv_indicator;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.school_name_tv)
    TextView schoolNameTv;
    @BindView(R.id.school_es_name_tv)
    TextView schoolEsNameTv;
    @BindView(R.id.city_tv)
    TextView cityTv;
    @BindView(R.id.school_icon)
    ImageView schoolIcon;
    @BindView(R.id.apply_btn)
    TextView applyBtn;
    @BindView(R.id.school_sex_tv)
    TextView schoolSexTv;
    @BindView(R.id.school_sort_tv)
    TextView schoolSortTv;
    @BindView(R.id.school_money_tv)
    TextView schoolMoneyTv;
    @BindView(R.id.school_record_tv)
    TextView schoolRecordTv;
    @BindView(R.id.sat_num_tv)
    TextView satNumTv;
    @BindView(R.id.toefl_num_tv)
    TextView toeflNumTv;
    @BindView(R.id.act_num_tv)
    TextView actNumTv;
    @BindView(R.id.perpal_num_tv)
    TextView perpalNumTv;
    @BindView(R.id.gpa_num_tv)
    TextView gpaNumTv;
    @BindView(R.id.school_desc_tv)
    TextView schoolDescTv;
    private ArrayList<String> urlList;
    private SchoolDetailActivity mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View main = getLayoutInflater().from(this).inflate(R.layout.activity_school_detail, null);
        main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        MyActivityManager.getInstance().addActivity(this);

        setContentView(main);
        mContext = this;
        setMiuiStatusBarDarkMode(this, true);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }
        //setContentView(R.layout.activity_school_detail);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        tv_header.setText("学校详情");
        iv_header_left.setImageResource(R.mipmap.icon_back);
        iv_header_right.setImageResource(R.mipmap.icon_share);
        String[] urls = {"http://img.netbian.com/file/2017/0722/3393191520f5569243da56526850c6f5.jpg",
                "http://img.netbian.com/file/2017/0722/3393191520f5569243da56526850c6f5.jpg",
                "http://img.netbian.com/file/2017/0722/3393191520f5569243da56526850c6f5.jpg",
                "http://img.netbian.com/file/2017/0722/3393191520f5569243da56526850c6f5.jpg",
                "http://img.netbian.com/file/2017/0722/3393191520f5569243da56526850c6f5.jpg"
        };

        urlList = new ArrayList<String>();
        Collections.addAll(urlList, urls);
        initData();
    }

    private void initData() {

       viewPager.setAdapter(new PictureSlidePagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tv_indicator.setText(String.valueOf(position + 1) + "/" + urlList.size());
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class PictureSlidePagerAdapter extends FragmentStatePagerAdapter {

        public PictureSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PictureSlideFragment.newInstance(urlList.get(position));
        }

        @Override
        public int getCount() {
            return urlList.size();
        }
    }

    @OnClick({R.id.iv_header_right, R.id.iv_header_left, R.id.school_icon, R.id.apply_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                onBackPressed();
                break;
            case R.id.iv_header_right:
                break;
            case R.id.school_icon:
                break;
            case R.id.apply_btn:
                break;
        }
    }
}
