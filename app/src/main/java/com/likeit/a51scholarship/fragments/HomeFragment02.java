package com.likeit.a51scholarship.fragments;


import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.MainActivity;
import com.likeit.a51scholarship.activitys.SearchInfoActivity;
import com.likeit.a51scholarship.adapters.CircleGridViewAdapter;
import com.likeit.a51scholarship.utils.ListScrollUtil;
import com.likeit.a51scholarship.view.MyGridView;
import com.likeit.a51scholarship.view.MyListview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment02 extends BaseFragment implements View.OnClickListener ,
        PullToRefreshBase.OnRefreshListener2<ScrollView>{


    private ImageView iv_header_left;
    private ImageView iv_header_right;
    private TextView tv_header;
    private MyGridView mGridView;
    private MyListview mListView;
    private ProgressDialog dialog;
    private CircleGridViewAdapter mGridviewAdapter;
    private List<Map<String, Object>> dataList;
    private List<Map<String, Object>> dataList01;
    // 图片封装为一个数组
    private int[] icon = {R.mipmap.icon_01_3x, R.mipmap.icon_02_3x,
            R.mipmap.icon_03_3x, R.mipmap.icon_04_3x, R.mipmap.icon_05_3x,
            R.mipmap.icon_06_3x, R.mipmap.icon_07_3x};
    private String[] iconName = {"留学英国", "留学美国", "留学加拿大", "澳大利亚", "托福考试", "IELTS考试", "CRE考试"};
    private String[] iconNumber = {"成员：66666", "成员：66666", "成员：66666", "成员：66666", "成员：66666", "成员：66666", "成员：66666"};
    private String[] iconTopic = {"帖子：66666", "帖子：66666", "帖子：66666", "帖子：66666", "帖子：66666", "帖子：66666", "帖子：66666"};
    private SimpleAdapter simpleAdapter;
    private SimpleAdapter simpleAdapter01;
    private PullToRefreshScrollView mPullToRefreshScrollView;

    @Override
    protected int setContentView() {
        return R.layout.fragment_home_fragment02;
    }

    @Override
    protected void lazyLoad() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        // 图片封装为一个数组

        //circleGridViewData = new ArrayList<CircleGridViewBean>();

        //dialog.show();
        initView();
        initListener();

    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < icon.length; i++) {
            Log.d("TAG", "" + icon.length);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icon[i]);
            map.put("name", iconName[i]);
            dataList.add(map);
        }
        return dataList;
    }

    private List<Map<String, Object>> getData01() {
        for (int i = 0; i < icon.length; i++) {
            Log.d("TAG", "" + icon.length);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icon[i]);
            map.put("name", iconName[i]);
            map.put("number", iconNumber[i]);
            map.put("topic", iconTopic[i]);
            dataList01.add(map);
        }
        return dataList01;
    }

    private void initView() {
        mPullToRefreshScrollView = findViewById(R.id.ll_circle_scrollview);
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
        iv_header_left = findViewById(R.id.iv_header_left);
        iv_header_right = findViewById(R.id.iv_header_right);
        tv_header = findViewById(R.id.tv_header);
        tv_header.setText("圈子");
        iv_header_left.setImageResource(R.mipmap.nav_icon_user);
        iv_header_right.setImageResource(R.mipmap.nav_icon_search_sel);
        mGridView = findViewById(R.id.circle_gridview);
        mListView = findViewById(R.id.circle_listview);
        /**
         * 关注的圈子
         */
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        String[] from = {"img", "name"};
        int[] to = {R.id.cricle_gridview_iv_Avatar, R.id.cricle_gridview_tv_name};
        simpleAdapter = new SimpleAdapter(getActivity(), dataList, R.layout.cricle_gridview_items, from, to);
        //配置适配器
        mGridView.setAdapter(simpleAdapter);
        /**
         * 全部圈子
         */
        dataList01 = new ArrayList<Map<String, Object>>();
        getData01();
        String[] from01 = {"img", "name","number","topic"};
        int[] to01 = {R.id.iv_avatar, R.id.tv_school_name,R.id.tv_school_number,R.id.tv_school_topic};
        simpleAdapter01 = new SimpleAdapter(getActivity(), dataList01, R.layout.circle_listview_items, from01, to01);
        //配置适配器
        mListView.setAdapter(simpleAdapter01);
//        mGridView.setAdapter(mGridviewAdapter);
//        mGridviewAdapter = new CircleGridViewAdapter(getActivity(), dataList);
//        mGridviewAdapter.notifyDataSetChanged();
        ListScrollUtil.setListViewHeightBasedOnChildren(mListView);

    }

    private void initListener() {
        iv_header_left.setOnClickListener(this);
        iv_header_right.setOnClickListener(this);
    }

    @Override
    public boolean haveEventBus() {
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_header_left:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.refresh();
                break;
            case R.id.iv_header_right:
                toActivity(SearchInfoActivity.class);
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
}
