package com.likeit.a51scholarship.activitys;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.fragments.PictureSlideFragment;
import com.likeit.a51scholarship.utils.AndroidWorkaround;
import com.likeit.a51scholarship.utils.ListScrollUtil;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.utils.UtilPreference;
import com.likeit.a51scholarship.view.CircleImageView;
import com.likeit.a51scholarship.view.MyListview;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.school_details_scrollview)
    PullToRefreshScrollView mPullToRefreshScrollView;
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
    //奖学金申请
    @BindView(R.id.school_scholarship_tv)
    TextView schoolScholarshipTv;
    //校园印象列表
    @BindView(R.id.school_details_listView)
    MyListview mListView;
    private ArrayList<String> urlList;
    private SchoolDetailActivity mContext;
    private String name, en_name, img, key;

    // 模拟评论数据
    private List<Map<String, Object>> dataList;
    private int[] icon = {R.mipmap.icon_01_3x, R.mipmap.icon_02_3x,
            R.mipmap.icon_03_3x, R.mipmap.icon_04_3x};
    private String[] iconName = {"Tom", "Lucy", "Lily", "Jim"};
    private String[] iconTime = {"2017-01-05 20:20", "2017-02-05 20:20", "2017-03-05 23:20", "2017-05-05 10:20"};
    private String[] iconDetails = {"不错！", "Is Very Good", "GoodMorning", "Hello"};
    private SimpleAdapter simpleAdapter;
    //目录列表
    private int from = 0;
    private Window window;
    //点击菜单移到位置的点
    @BindView(R.id.ll_school_admit)
    LinearLayout llAdmit;
    @BindView(R.id.school_staff_student_tv)
    TextView tvStaff;
    @BindView(R.id.ll_school_school_fee_tv)
    TextView tvSchoolFee;
    @BindView(R.id.ll_school_scholarship)
    TextView tvSchoolScholarship;
    @BindView(R.id.ll_school_number)
    TextView tvSchoolNumber;
    @BindView(R.id.ll_school_contact_way)
    TextView tvSchoolContactWay;
    private int top; //目录菜单位置
    private int scrollViewHight;
    private boolean ischecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View main = getLayoutInflater().from(this).inflate(R.layout.activity_school_detail, null);
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
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                mPullToRefreshScrollView.scrollTo(0, 900);// 改变滚动条的位置
            }
        };
        UtilPreference.saveString(mContext, "checked", "1");
    }

    private void initData() {
        if ("1".equals(key)) {
            tv_header.setText(name);
            schoolNameTv.setText(name);
            schoolEsNameTv.setText(en_name);
            ImageLoader.getInstance().displayImage(img, schoolImgIv);
        } else if ("2".equals(key)) {
            tv_header.setText("美国德克萨斯州立大学 阿灵顿分校");
            schoolNameTv.setText("美国德克萨斯州立大学 阿灵顿分校");
            schoolEsNameTv.setText("The University of Texas  ARLNGTON");
            schoolImgIv.setImageResource(R.mipmap.test01);
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
        schoolScholarshipTv.setText("1.平均发放奖学金(本科生)：44781美元。2.申请到奖学金的比例(本科生)：58%。" +
                "提供的奖学金类型：Federal PELL;Federal SEOG;state scholarships/grants;private scholarships;" +
                "college/university gift aid from institution funds.");
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
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                ListScrollUtil.setListViewHeightBasedOnChildren(mListView);
                mPullToRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                ListScrollUtil.setListViewHeightBasedOnChildren(mListView);
                mPullToRefreshScrollView.onRefreshComplete();
            }
        });
        mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间");
        mPullToRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel("下拉刷新");
        mPullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新");

        //评论列表
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        String[] from = {"img", "name", "time", "details"};
        int[] to = {R.id.school_comment_avatar, R.id.school_comment_name, R.id.school_comment_time, R.id.school_comment_details};
        simpleAdapter = new SimpleAdapter(this, dataList, R.layout.school_details_listview_items, from, to);
        //配置适配器
        mListView.setAdapter(simpleAdapter);
        ListScrollUtil.setListViewHeightBasedOnChildren(mListView);
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

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < icon.length; i++) {
            Log.d("TAG", "" + icon.length);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icon[i]);
            map.put("name", iconName[i]);
            map.put("time", iconTime[i]);
            map.put("details", iconDetails[i]);
            dataList.add(map);
        }
        return dataList;
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

    private PopupWindow popupWindow;

    @OnClick({R.id.iv_header_right, R.id.iv_header_left, R.id.icon_up_iv, R.id.icon_list_iv, R.id.icon_pinlun_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                onBackPressed();
                break;
            case R.id.iv_header_right:
                break;
            case R.id.icon_pinlun_iv:
                Intent intentComment = new Intent(this, SchoolCommentActivity.class);
                startActivity(intentComment);
                break;
            case R.id.icon_list_iv:
                from = Location.RIGHT.ordinal();
                initPopupWindow();
                break;
            case R.id.icon_up_iv:
                mPullToRefreshScrollView.getRefreshableView().scrollTo(0, 0);
                break;
        }
    }

    private void initPopupWindow() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        final View popupWindowView = getLayoutInflater().inflate(R.layout.school_details_menu_list, null);
        //内容，高度，宽度
        if (Location.BOTTOM.ordinal() == from) {
            popupWindow = new PopupWindow(popupWindowView, height, LayoutParams.WRAP_CONTENT, true);
        } else {
            popupWindow = new PopupWindow(popupWindowView, LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT, true);
        }
        //动画效果
        if (Location.RIGHT.ordinal() == from) {
            popupWindow.setAnimationStyle(R.style.AnimationRightFade);
        }
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(lp);
        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popupWindow.setBackgroundDrawable(dw);
        //宽度
        popupWindow.setWidth(width / 2);
        //高度
        popupWindow.setHeight(height);
        //显示位置
        if (Location.RIGHT.ordinal() == from) {
            popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_school_detail, null), Gravity.RIGHT, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        //设置背景半透明
        backgroundAlpha(0.5f);
        //关闭事件
        popupWindow.setOnDismissListener(new popupDismissListener());

        popupWindowView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*if( popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
					popupWindow=null;
				}*/
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });
        scrollViewHight = findViewById(R.id.ll_scrollview).getHeight();
        final RadioGroup mRadiogroup = (RadioGroup) popupWindowView.findViewById(R.id.school_details_list_container);
        mRadiogroup.check(R.id.school_details_list_rb01);
        String checked = UtilPreference.getStringValue(mContext, "checked");
        if ("1".equals(checked)) {
            mRadiogroup.check(R.id.school_details_list_rb01);
        } else if ("2".equals(checked)) {
            mRadiogroup.check(R.id.school_details_list_rb02);
        } else if ("3".equals(checked)) {
            mRadiogroup.check(R.id.school_details_list_rb03);
        } else if ("4".equals(checked)) {
            mRadiogroup.check(R.id.school_details_list_rb04);
        } else if ("5".equals(checked)) {
            mRadiogroup.check(R.id.school_details_list_rb05);
        } else if ("6".equals(checked)) {
            mRadiogroup.check(R.id.school_details_list_rb06);
        } else if ("7".equals(checked)) {
            mRadiogroup.check(R.id.school_details_list_rb07);
        }
        mRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.school_details_list_rb01:
                        Log.d("TAG", "top-->" + top);
                        mPullToRefreshScrollView.getRefreshableView().scrollTo(0, 0);
                        //popupWindow.dismiss();
                        UtilPreference.saveString(mContext, "checked", "1");
                        popupWindow.dismiss();
                        break;
                    case R.id.school_details_list_rb02:
                        top = llAdmit.getTop();
                        Log.d("TAG", "top-->" + top);
                        mPullToRefreshScrollView.getRefreshableView().scrollTo(0, top);
                        UtilPreference.saveString(mContext, "checked", "2");
                        popupWindow.dismiss();
                        break;
                    case R.id.school_details_list_rb03:
                        top = tvStaff.getTop();
                        Log.d("TAG", "top-->" + top);
                        mPullToRefreshScrollView.getRefreshableView().scrollTo(0, top);
                        UtilPreference.saveString(mContext, "checked", "3");
                        popupWindow.dismiss();
                        break;
                    case R.id.school_details_list_rb04:
                        top = tvSchoolFee.getTop();
                        Log.d("TAG", "top-->" + top);
                        mPullToRefreshScrollView.getRefreshableView().scrollTo(0, top);
                        UtilPreference.saveString(mContext, "checked", "4");
                        popupWindow.dismiss();
                        break;
                    case R.id.school_details_list_rb05:
                        top = tvSchoolScholarship.getTop();
                        Log.d("TAG", "top-->" + top);
                        mPullToRefreshScrollView.getRefreshableView().scrollTo(0, top);
                        UtilPreference.saveString(mContext, "checked", "5");
                        popupWindow.dismiss();
                        break;
                    case R.id.school_details_list_rb06:
                        top = tvSchoolNumber.getTop();
                        Log.d("TAG", "top-->" + top);
                        mPullToRefreshScrollView.getRefreshableView().scrollTo(0, top);
                        UtilPreference.saveString(mContext, "checked", "6");
                        popupWindow.dismiss();
                        break;
                    case R.id.school_details_list_rb07:
                        top = tvSchoolContactWay.getTop();
                        Log.d("TAG", "top-->" + top);
                        mPullToRefreshScrollView.getRefreshableView().scrollTo(0, top);
                        UtilPreference.saveString(mContext, "checked", "7");
                        popupWindow.dismiss();
                        break;


                }
            }
        });
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);

    }

    /**
     * 菜单弹出方向
     */
    public enum Location {

        LEFT,
        RIGHT,
        TOP,
        BOTTOM;

    }
}