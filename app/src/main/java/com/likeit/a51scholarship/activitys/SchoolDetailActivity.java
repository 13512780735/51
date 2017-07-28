package com.likeit.a51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.fragments.PictureSlideFragment;
import com.likeit.a51scholarship.utils.AndroidWorkaround;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

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
    @BindView(R.id.school_img_iv)
    CircleImageView schoolImgIv;
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
    //录取数据
    @BindView(R.id.school_admit_number_tv)
    TextView schoolAdmitNumberTv;
    @BindView(R.id.school_admit_rate_tv)
    TextView schoolAdmitRateTv;
    @BindView(R.id.school_admit_GPA_tv)
    TextView schoolAdmitGPATv;
    @BindView(R.id.school_SAT_admit_tv)
    TextView schoolSATAdmitTv;
    @BindView(R.id.school_math_SAT_tv)
    TextView schoolMathSATTv;
    @BindView(R.id.school_read_SAT_tv)
    TextView schoolReadSATTv;
    @BindView(R.id.school_admit_ACT_tv)
    TextView schoolAdmitACTTv;
    //新生高中生班级排名
    @BindView(R.id.school_class_rank_front10_tv)
    TextView schoolClassRankFront10Tv;
    @BindView(R.id.school_class_rank_front25_tv)
    TextView schoolClassRankFront25Tv;
    @BindView(R.id.school_class_rank_front50_tv)
    TextView schoolClassRankFront50Tv;
    //申请信息
    @BindView(R.id.school_apply_abort_time_tv)
    TextView schoolApplyAbortTimeTv;
    @BindView(R.id.school_inform_time_tv)
    TextView schoolInformTimeTv;
    @BindView(R.id.school_if_EA_tv)
    TextView schoolIfEATv;
    @BindView(R.id.school_EA_abort_time_tv)
    TextView schoolEAAbortTimeTv;
    @BindView(R.id.school_if_ED_tv)
    TextView schoolIfEDTv;
    @BindView(R.id.school_ED_abort_time_tv)
    TextView schoolEDAbortTimeTv;
    @BindView(R.id.school_dual_rank_tv)
    TextView schoolDualRankTv;
    @BindView(R.id.school_apply_cost_tv)
    TextView schoolApplyCostTv;
    @BindView(R.id.school_apply_html_tv)
    TextView schoolApplyHtmlTv;
    //申请材料
    @BindView(R.id.school_apply_data_tv)
    TextView schoolApplyDataTv;
    //学术介绍-教师信息

    private ArrayList<String> urlList;
    private SchoolDetailActivity mContext;
    private String name, en_name, img, key;


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
        Intent intent = getIntent();
        key = intent.getStringExtra("key");//从首页中文名字
        name = intent.getStringExtra("name");//从首页中文名字
        en_name = intent.getStringExtra("en_name");//从首页英文名字
        img = intent.getStringExtra("img");//图片
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
        if ("1".equals(key)) {
            tv_header.setText(name);
            schoolNameTv.setText(name);
            schoolEsNameTv.setText(en_name);
            ImageLoader.getInstance().displayImage(img, schoolImgIv);
        }
        /**
         * 录取数据
         */
        schoolDescTv.setText("美国德州大学(共十五分校) 阿灵顿分校商学所在美国高等" +
                "教育发展已有一百年以上的历史，是一所综合教学及研究的高等学府以商业教育最为出名。");
        schoolAdmitNumberTv.setText("34919");
        schoolAdmitRateTv.setText("5.8%");
        schoolAdmitGPATv.setText("4");
        schoolSATAdmitTv.setText("～0");
        schoolMathSATTv.setText("～0");
        schoolReadSATTv.setText("～0");
        schoolAdmitACTTv.setText("32～35");
        /**
         * 新生高中生班级排名
         */
        schoolClassRankFront10Tv.setText("95%");
        schoolClassRankFront25Tv.setText("100%");
        schoolClassRankFront50Tv.setText("100%");
        /**
         * 申请信息
         */
        schoolApplyAbortTimeTv.setText("01-01");
        schoolInformTimeTv.setText("02-01");
        schoolIfEATv.setText("是");
        schoolEAAbortTimeTv.setText("11-01");
        schoolIfEDTv.setText("是");
        schoolEDAbortTimeTv.setText("12-01");
        schoolDualRankTv.setText("否");
        schoolApplyCostTv.setText("$75");
        schoolApplyHtmlTv.setText("https://www.baidu.com");
        /**
         * 申请材料
         */
        schoolApplyDataTv.setText("1.填写申请表；2.75美金申请费；3.哈佛补充申请表；4.AST或ACT写作成绩；" +
                "5.两门SAT 11的成绩(注意：SAT 11 English Language Proficiency Test,ELPT不被接受)；6.中学成绩单；7.两篇教师推荐信。");
        /**
         * 学术介绍
         * 教师信息
         */
        /**
         * 毕业率
         */
        /**
         * 专业设置
         */
        /**
         * 本科最受欢迎的5个专业
         */
        /**
         * 费用
         */
        /**
         * 奖学金申请
         */
        /**
         * 学生数据
         */
        /**
         * 种族构成
         */
        /**
         * 联系方式
         */
        /**
         * 校园印象
         */
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

    @OnClick({R.id.iv_header_right, R.id.iv_header_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                onBackPressed();
                break;
            case R.id.iv_header_right:
                break;
        }
    }
}
