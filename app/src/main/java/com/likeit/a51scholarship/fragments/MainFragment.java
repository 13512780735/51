package com.likeit.a51scholarship.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.MainActivity;
import com.likeit.a51scholarship.activitys.MessageActivity;
import com.likeit.a51scholarship.activitys.NewsListActivity;
import com.likeit.a51scholarship.activitys.SchoolDetailActivity;
import com.likeit.a51scholarship.activitys.SearchInfoActivity;
import com.likeit.a51scholarship.activitys.SearchSchoolActivity;
import com.likeit.a51scholarship.adapters.HomeItemSchoolAdapter;
import com.likeit.a51scholarship.configs.AppConfig;
import com.likeit.a51scholarship.dialog.KefuDialog;
import com.likeit.a51scholarship.http.HttpUtil;
import com.likeit.a51scholarship.model.HomeADlistBean;
import com.likeit.a51scholarship.model.HomeItemSchoolBean;
import com.likeit.a51scholarship.utils.ListScrollUtil;
import com.likeit.a51scholarship.utils.ToastUtil;
import com.likeit.a51scholarship.view.MyListview;
import com.loopj.android.http.RequestParams;
import com.renj.hightlight.HighLight;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends MyBaseFragment implements View.OnClickListener,
        PullToRefreshBase.OnRefreshListener2<ScrollView>, RadioGroup.OnCheckedChangeListener {

    ImageView userinfoImg;
    EditText searchContentEt;
    ImageView audioIcon;
    LinearLayout searchLayout;
    ImageView messageImg;
    private ImageView kefuService;
    private PullToRefreshScrollView mPullToRefreshScrollView;
    private LinearLayout schoolLayout;
    private LinearLayout newsLayout;
    private LinearLayout lookLive;
    private LinearLayout onlineLayout;
    private LinearLayout aqLayout;
    private ProgressDialog dialog;
    private ArrayList<String> avdList;  //广告集合
    private SliderLayout sliderShow;
    private List<HomeADlistBean> ADListData;
    private HomeADlistBean homeADlistBean;
    private MyListview mListView;
    private List<HomeItemSchoolBean> SchoolData;
    private HomeItemSchoolAdapter schoolAdater;
    private RadioButton radio_school;
    private RadioButton radio_news;
    private RadioGroup main_radio_group;
    private static final String SHOWCASE_ID = "sequence example";
    private View iv_school_layout;
    private View line_school, line_news;
    private int status = 1;  // 判断是院校选择还是资讯选择 ，1为院校、2为资讯
    private String key;  //判断是否显示指示层

    @Override
    protected int setContentView() {
        return R.layout.fragment_main;
    }

    @Override
    protected void lazyLoad() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        ADListData = new ArrayList<HomeADlistBean>();
        SchoolData = new ArrayList<HomeItemSchoolBean>();
        Intent intent = getActivity().getIntent();
        key = intent.getStringExtra("key");
        Log.d("TAG","key-->"+key);
        initData();
        dialog.show();
        initView();
        initListener();
        if ("1".equals(key)) {
            showCaseView();
        } else return;
    }

    private void showCaseView() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(300);
                    iv_school_layout = findViewById(R.id.iv_school_layout);
                    iv_school_layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            addHightView();
                            iv_school_layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void addHightView() {
        // 使用默认的设置
        HighLight highLight = new HighLight(getActivity())
                .anchor(getActivity().findViewById(R.id.id_content))
                .setMyBroderType(HighLight.MyType.FULL_LINE) // 使用实线
                .addHighLight(R.id.iv_school_layout, R.layout.info_up, new HighLight.OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                        marginInfo.leftMargin = rectF.right + rectF.width() / 2;
                        marginInfo.topMargin = rectF.bottom;
                    }
                }, HighLight.MyShape.CIRCULAR);// 圆形高亮
        highLight.show();
    }


    private void initData() {
        String url = AppConfig.LIKEIT_ADLIST;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                // Log.d("TAG", "Home-->" + response);
                dialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {

                        JSONArray data01 = obj.optJSONArray("data");
                        for (int i = 0; i < data01.length(); i++) {
                            JSONObject jsonObject = data01.optJSONObject(i);
                            homeADlistBean = new HomeADlistBean();
                            homeADlistBean.setUrl(jsonObject.optString("url"));
                            homeADlistBean.setPic(jsonObject.optString("pic"));
                            ADListData.add(homeADlistBean);
                        }
                        // Log.d("TAG", "ADListData-->" + ADListData);
                        imageSlider();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failed(Throwable e) {
                dialog.dismiss();
                showToast("数据请求失败！");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
            }
        });
        getListData();
        dialog.show();
    }

    private void imageSlider() {
        for (int i = 0; i < ADListData.size(); i++) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(getActivity());
            // textSliderView.description("");//设置标题
            defaultSliderView.image(ADListData.get(i).getPic());//设置图片的网络地址
            //添加到布局中显示
            sliderShow.addSlider(defaultSliderView);
        }
        //其他设置
        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//使用默认指示器，在底部显示
        sliderShow.setDuration(5000);//停留时间
        sliderShow.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderShow.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        sliderShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showS(getActivity(), "1");
            }
        });
    }

    private void getListData() {
        String url = AppConfig.LIKEIT_SCHOOL;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                dialog.dismiss();
                // Log.d("TAG", "HomeSchool-->" + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        JSONArray schoolData = obj.optJSONArray("data");
                        for (int i = 0; i < schoolData.length(); i++) {
                            JSONObject jsonObject = schoolData.optJSONObject(i);
                            HomeItemSchoolBean homeItemSchoolBean = new HomeItemSchoolBean();
                            homeItemSchoolBean.setCountry_name(jsonObject.optString("country_name"));
                            homeItemSchoolBean.setEn_name(jsonObject.optString("en_name"));
                            homeItemSchoolBean.setId(jsonObject.optString("id"));
                            homeItemSchoolBean.setImg(jsonObject.optString("img"));
                            homeItemSchoolBean.setName(jsonObject.optString("name"));
                            SchoolData.add(homeItemSchoolBean);
                        }
                        Log.d("TAG", "HomeSchool-->" + SchoolData);
                        schoolAdater.addAll(SchoolData, false);
                        schoolAdater.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failed(Throwable e) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
            }
        });

    }

    private void initListener() {
        userinfoImg.setOnClickListener(this);
        kefuService.setOnClickListener(this);
        searchContentEt.setOnClickListener(this);
        audioIcon.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        messageImg.setOnClickListener(this);
        schoolLayout.setOnClickListener(this);
        newsLayout.setOnClickListener(this);
        lookLive.setOnClickListener(this);
        onlineLayout.setOnClickListener(this);
        aqLayout.setOnClickListener(this);
        main_radio_group.setOnCheckedChangeListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (status == 1) {
                    toActivity(SchoolDetailActivity.class);

                }
            }
        });
    }

    private void initView() {
        sliderShow = findViewById(R.id.slider);
        mPullToRefreshScrollView = findViewById(R.id.ll_home_scrollview);
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshScrollView.setOnRefreshListener(this);
        mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间");
        mPullToRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel("下拉刷新");
//          mPullRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel(
//                      "refreshingLabel");
        mPullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新");
        mListView = findViewById(R.id.listView);
        schoolAdater = new HomeItemSchoolAdapter(getActivity(), SchoolData);
        userinfoImg = findViewById(R.id.userinfo_img);
        searchContentEt = findViewById(R.id.search_content_et);
        audioIcon = findViewById(R.id.audio_icon);
        searchLayout = findViewById(R.id.search_layout);
        messageImg = findViewById(R.id.message_img);
        kefuService = findViewById(R.id.kefu_service);
        schoolLayout = findViewById(R.id.school_layout);
        newsLayout = findViewById(R.id.news_layout);
        lookLive = findViewById(R.id.look_live);
        onlineLayout = findViewById(R.id.online_layout);
        aqLayout = findViewById(R.id.aq_layout);
        radio_school = findViewById(R.id.radio_school);
        radio_news = findViewById(R.id.radio_news);
        line_school = findViewById(R.id.line_school);
        line_news = findViewById(R.id.line_news);
        main_radio_group = findViewById(R.id.main_radio_group);
        mListView.setAdapter(schoolAdater);
        schoolAdater.notifyDataSetChanged();
        ListScrollUtil.setListViewHeightBasedOnChildren(mListView);
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userinfo_img:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.refresh();
                break;
            case R.id.search_content_et:
            case R.id.audio_icon:
            case R.id.search_layout:
                toActivity(SearchInfoActivity.class);
                break;
            case R.id.message_img:
                toActivity(MessageActivity.class);
                break;
            case R.id.kefu_service:
                KefuDialog kefuDialog = new KefuDialog(getContext());
                kefuDialog.show();
                break;
            case R.id.school_layout:
//                toActivity(AutoSearchActivity.class);
                toActivity(SearchSchoolActivity.class);
                break;
            case R.id.news_layout:
                toActivity(NewsListActivity.class);
                break;
            case R.id.look_live:
                break;
            case R.id.online_layout:
                break;
            case R.id.aq_layout:
                break;
        }
    }

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

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.radio_school:
                //mListView.removeAllViews();
                status = 1;
                getListData();
                line_news.setVisibility(View.GONE);
                line_school.setVisibility(View.VISIBLE);
                schoolAdater.addAll(SchoolData, true);
                schoolAdater.notifyDataSetChanged();
                ListScrollUtil.setListViewHeightBasedOnChildren(mListView);
                mPullToRefreshScrollView.onRefreshComplete();
                radio_school.setChecked(true);
                radio_news.setChecked(false);
                break;
            case R.id.radio_news:
                status = 2;
                line_news.setVisibility(View.VISIBLE);
                line_school.setVisibility(View.GONE);
                getListData();
                schoolAdater.addAll(SchoolData, true);
                schoolAdater.notifyDataSetChanged();
                ListScrollUtil.setListViewHeightBasedOnChildren(mListView);
                mPullToRefreshScrollView.onRefreshComplete();
                radio_news.setChecked(true);
                radio_school.setChecked(false);
                break;
        }

    }
}
