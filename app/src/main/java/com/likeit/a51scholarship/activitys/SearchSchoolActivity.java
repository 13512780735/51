package com.likeit.a51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.utils.AndroidWorkaround;
import com.likeit.a51scholarship.utils.ListScrollUtil;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.utils.ToastUtil;
import com.likeit.a51scholarship.view.MyListview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchSchoolActivity extends Container {
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.search_content_et)
    EditText searchContentEt;
    @BindView(R.id.audio_icon)
    ImageView audioIcon;
    @BindView(R.id.search_layout)
    LinearLayout searchLayout;
    @BindView(R.id.message_img)
    ImageView messageImg;
    @BindView(R.id.hot_school)
    RadioButton hotSchool;
    @BindView(R.id.recommend_school)
    RadioButton recommendSchool;
    @BindView(R.id.offer_school)
    RadioButton offerSchool;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.content_frame)
    FrameLayout contentFrame;
    @BindView(R.id.line_hot_school)
    View lineHotSchool;
    @BindView(R.id.line_recommend)
    View lineRecommend;
    @BindView(R.id.line_offer)
    View lineOffer;
    @BindView(R.id.school_list_scrollview)
    PullToRefreshScrollView mPullToRefreshScrollView;
    @BindView(R.id.school_list_listview)
    MyListview mListview;
    private List<Map<String, Object>> dataList;
    // 图片封装为一个数组
    private int[] icon = {R.mipmap.test01, R.mipmap.test01,
            R.mipmap.test01};
    private String[] iconNameCH = {"美国德克萨斯州立大学 阿灵顿分校", "卡迪夫学院", "巴斯学院"};
    private String[] iconNameEN = {"The University of Texas  ARLNGTON", "Cardiff Sixth Form College",
            "Bath Academy"};
    private String[] iconRank = {"200", "150", "120"};
    private String[] iconRate = {"60%", "55%", "62%"};
    private String[] iconSchoolShip = {"$6000", "$6500", "$5500"};
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_search_school);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
                mPullToRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
                mPullToRefreshScrollView.onRefreshComplete();
            }
        });
        mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间");
        mPullToRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel("下拉刷新");
        mPullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新");
        /**
         * 消息
         */
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        String[] from = {"img", "name_ch", "name_eng", "rank", "rate", "scholarship"};
        int[] to = {R.id.school_listview_iv_school, R.id.school_listview_tv_Chinese_name, R.id.school_listview_tv_English_name,
                R.id.school_listview_tv_rank,
                R.id.school_listview_tv_rate,
                R.id.school_listview_tv_scholarship};
        simpleAdapter = new SimpleAdapter(this, dataList, R.layout.school_listview_items, from, to);
        //配置适配器
        mListview.setAdapter(simpleAdapter);
        ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentDetails=new Intent();
                intentDetails.putExtra("key","2");
                intentDetails.setClass(mContext,SchoolDetailActivity.class);
                startActivity(intentDetails);
            }
        });
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < icon.length; i++) {
            Log.d("TAG", "" + icon.length);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icon[i]);
            map.put("name_ch", iconNameCH[i]);
            map.put("name_eng", iconNameEN[i]);
            map.put("rank", iconRank[i]);
            map.put("rate", iconRate[i]);
            map.put("scholarship", iconSchoolShip[i]);
            dataList.add(map);
        }
        return dataList;
    }

    @OnClick({R.id.back_img, R.id.audio_icon, R.id.search_layout, R.id.message_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.audio_icon:
            case R.id.search_layout:
                toActivity(SearchInfoActivity.class);
                break;
            case R.id.message_img:
                break;
        }
    }
}
