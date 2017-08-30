package com.likeit.a51scholarship.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.adapters.SchoolDetailPopAdapter;
import com.likeit.a51scholarship.adapters.SchoolDetailsAdapter;
import com.likeit.a51scholarship.configs.AppConfig;
import com.likeit.a51scholarship.fragments.PictureSlideFragment;
import com.likeit.a51scholarship.http.HttpUtil;
import com.likeit.a51scholarship.model.SchoolDetailsBean;
import com.likeit.a51scholarship.model.SchoolDetailsBean01;
import com.likeit.a51scholarship.model.SchoolDetailsPopBena;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.utils.ToastUtil;
import com.likeit.a51scholarship.utils.UtilPreference;
import com.likeit.a51scholarship.view.CircleImageView;
import com.likeit.a51scholarship.view.MyListview;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import jaydenxiao.com.expandabletextview.ExpandableTextView;


public class SchoolDetailActivity extends Container {
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

    /**
     * 学校简介
     */
    @BindView(R.id.school_rank)
    TextView tvSchoolRank;
    @BindView(R.id.school_scholarship)
    TextView tvSchoolScholarship;
    @BindView(R.id.school_nature)
    TextView tvSchoolNature;
    @BindView(R.id.school_trate)
    TextView tvSchoolTrate;
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
    @BindView(R.id.school_number)
    TextView tvSchoolNumber;
    @BindView(R.id.school_desc_tv)
    ExpandableTextView schoolDescTv;
    @BindView(R.id.school_details_scrollview)
    PullToRefreshScrollView mPullToRefreshScrollView;
    @BindView(R.id.school_details_list)
    MyListview mListview;
    @BindView(R.id.ll_scrollview01)
    LinearLayout mLinearLayout;
    private ArrayList<String> urlList;
    private String name, en_name, img, key;

    //目录列表
    private int from = 0;
    private Window window;
    //点击菜单移到位置的点
//    @BindView(R.id.ll_school_admit)
//    LinearLayout llAdmit;
//    @BindView(R.id.school_staff_student_tv)
//    TextView tvStaff;
//    @BindView(R.id.ll_school_school_fee_tv)
//    TextView tvSchoolFee;
//    @BindView(R.id.ll_school_scholarship)
//    TextView tvSchoolScholarship;
//    @BindView(R.id.ll_school_number)
//    TextView tvSchoolNumber;
//    @BindView(R.id.ll_school_contact_way)
//    TextView tvSchoolContactWay;
    private int top; //目录菜单位置
    private int scrollViewHight;
    private boolean ischecked;
    private String sid;
    private SchoolDetailsBean mSchoolDetailsBean;
    private List<SchoolDetailsBean01> mSchoolDetailsBean01;
    private SchoolDetailsAdapter mAdapter;
    private List<SchoolDetailsPopBena> popData;
    private String name11;
    private SchoolDetailPopAdapter01 adapter;
    private ListView popMenuList;
    private SchoolDetailsPopBena mSchoolDetailPopBena;
    private int selectPosition = 0;//用于记录用户选择的变量
    private TextView et;
    private int hight01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail01);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");//从首页中文名字
        en_name = intent.getStringExtra("en_name");//从首页英文名字
        img = intent.getStringExtra("img");//图片
        sid = intent.getStringExtra("sid");//院校ID
        iv_header_left.setImageResource(R.mipmap.icon_back);
        iv_header_right.setImageResource(R.mipmap.icon_share);
        initLogo();
        initData1();
        showProgress("Loading...");

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                mPullToRefreshScrollView.scrollTo(0, 900);// 改变滚动条的位置
            }
        };
        UtilPreference.saveString(mContext, "checked", "1");


    }

    private void initLogo() {
        String[] urls = {img};

        urlList = new ArrayList<String>();
        Collections.addAll(urlList, urls);
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

    private void initData1() {
        String url = AppConfig.LIKEIT_SCHOOL_DETAILS;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("sid", sid);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                Log.e("TAG", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        JSONObject object = obj.optJSONObject("data");
                        mSchoolDetailsBean = JSON.parseObject(object.toString(), SchoolDetailsBean.class);
                        Log.d("TAG", mSchoolDetailsBean.toString());
                        JSONArray array = object.optJSONArray("school_details");
                        Log.d("TAG45685", array.toString());
                        initData();

                        popData = new ArrayList<SchoolDetailsPopBena>();
                        //   List<SchoolDetailsPopBena> popData01=new ArrayList<SchoolDetailsPopBena>();
                        SchoolDetailsPopBena mSchoolDetailPopBena01 = new SchoolDetailsPopBena();
                        mSchoolDetailPopBena01.setName("简介");
                        popData.add(mSchoolDetailPopBena01);
                        for (int i = 0; i <= array.length(); i++) {
                            JSONObject object1 = array.optJSONObject(i);
                            mSchoolDetailPopBena = new SchoolDetailsPopBena();
                            mSchoolDetailPopBena.setName(object1.optString("name"));
                            popData.add(mSchoolDetailPopBena);
                        }
                        Log.d("TAG999", popData.toString());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
                ToastUtil.showS(mContext, "网络异常！");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                disShowProgress();
            }
        });
    }

    private void initData() {
        tv_header.setText(name);
        schoolNameTv.setText(name);
        schoolEsNameTv.setText(en_name);
        String imgUrl = mSchoolDetailsBean.getSchool_info().getImg();
        Log.d("TAG", imgUrl + "sid-->" + sid + "ukey-->" + ukey);
        ImageLoader.getInstance().displayImage(imgUrl, schoolImgIv);
        tvSchoolRank.setText(mSchoolDetailsBean.getSchool_info().getRanking());
        tvSchoolScholarship.setText(mSchoolDetailsBean.getSchool_info().getScholarship());
        tvSchoolNature.setText(mSchoolDetailsBean.getSchool_info().getNature_name() + "高校");
        tvSchoolTrate.setText(mSchoolDetailsBean.getSchool_info().getRate());
        satNumTv.setText(mSchoolDetailsBean.getSchool_info().getToefl());
        toeflNumTv.setText(mSchoolDetailsBean.getSchool_info().getYasi());
        actNumTv.setText(mSchoolDetailsBean.getSchool_info().getToeic());
        perpalNumTv.setText(mSchoolDetailsBean.getSchool_info().getGmat());
        gpaNumTv.setText(mSchoolDetailsBean.getSchool_info().getGpa());
        tvSchoolNumber.setText(mSchoolDetailsBean.getSchool_info().getNumber());
        schoolDescTv.setText(mSchoolDetailsBean.getSchool_info().getDescription());
        mSchoolDetailsBean01 = mSchoolDetailsBean.getSchool_details();

        Log.d("TAG123", mSchoolDetailsBean01.get(0).getName());
        Log.d("TAG456", mSchoolDetailsBean01.get(0).getContent());
        mAdapter = new SchoolDetailsAdapter(mContext, mSchoolDetailsBean01);
        mListview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //ListScrollUtil.setListViewHeightBasedOnChildren(mListView);
                mPullToRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                // ListScrollUtil.setListViewHeightBasedOnChildren(mListView);
                mPullToRefreshScrollView.onRefreshComplete();
            }
        });
        mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间");
        mPullToRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel("下拉刷新");
        mPullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新");


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
                share();
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

    private void share() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        //oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    private void initPopupWindow() {

        Log.d("TAG909", popData.toString());
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
        popMenuList = (ListView) popupWindowView
                .findViewById(R.id.pop_listview);
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

        scrollViewHight = findViewById(R.id.ll_scrollview).getHeight();
        adapter = new SchoolDetailPopAdapter01(mContext, popData);
        popMenuList.setAdapter(adapter);
        //adapter.setDefSelect(0);
        adapter.notifyDataSetChanged();
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
        adapter = new SchoolDetailPopAdapter01(mContext, popData);
        popMenuList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        popMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                popupWindow.dismiss();
                selectPosition = position;
                adapter.notifyDataSetChanged();
                // mListview.setSelection(0);
                if (position == 0) {
                    popupWindow.dismiss();
                    mPullToRefreshScrollView.getRefreshableView().scrollTo(0, 0);
                } else {
                    View listItem = mListview.getChildAt(position-1);
                    hight01 = listItem.getMeasuredHeight();
                    int hight = mLinearLayout.getHeight();
                    Log.d("TAG", "Top-->" + top);
                    mPullToRefreshScrollView.getRefreshableView().scrollTo(0, hight01 * (position - 1) + hight );

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

    public class SchoolDetailPopAdapter01 extends BaseAdapter {

        Context context;
        List<SchoolDetailsPopBena> brandsList;
        LayoutInflater mInflater;

        public SchoolDetailPopAdapter01(Context context, List<SchoolDetailsPopBena> mList) {
            this.context = context;
            this.brandsList = mList;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return brandsList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.school_details_popwindow_listview_itmes, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.id_name);
                viewHolder.select = (RadioButton) convertView.findViewById(R.id.id_select);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(brandsList.get(position).getName());

            if (selectPosition == position) {
                viewHolder.select.setChecked(true);
            } else {
                viewHolder.select.setChecked(false);
            }
            return convertView;
        }

        public class ViewHolder {
            TextView name;
            RadioButton select;
        }
    }
}