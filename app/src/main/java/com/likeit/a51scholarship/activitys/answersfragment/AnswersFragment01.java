package com.likeit.a51scholarship.activitys.answersfragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.newsfragment.NewsDetailsActivity;
import com.likeit.a51scholarship.fragments.BaseFragment;
import com.likeit.a51scholarship.utils.ListScrollUtil;
import com.likeit.a51scholarship.view.MyListview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnswersFragment01 extends BaseFragment
        implements
        PullToRefreshBase.OnRefreshListener2<ScrollView> {


    private PullToRefreshScrollView mPullToRefreshScrollView;
    private MyListview mListview;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simpleAdapter;
    // 图片封装为一个数组
    private int[] icon = {R.mipmap.message_chat_avatar, R.mipmap.message_chat_avatar,
            R.mipmap.message_chat_avatar, R.mipmap.message_chat_avatar, R.mipmap.message_chat_avatar};
    private String[] iconName = {"Lana", "Tom", "Jim", "Lucy", "LiLy"};
    private String[] iconTime= {"50分钟前", "50分钟前", "50分钟前", "50分钟前", "50分钟前"};
    private String[] iconDetails = {"目前大四，留学加拿大需要准备什么呢？", "目前大四，留学加拿大需要准备什么呢？",
            "目前大四，留学加拿大需要准备什么呢？", "目前大四，留学加拿大需要准备什么呢？", "目前大四，留学加拿大需要准备什么呢？"};
    private String[] iconLikeNumber= {"9,999", "9,999", "9,999", "9,999", "9,999"};
    private String[] iconCommnentNumber = {"15", "15", "15", "15", "15"};


    @Override
    protected int setContentView() {
        return R.layout.fragment_answers_fragment01;
    }

    @Override
    protected void lazyLoad() {
        initView();
    }

    private void initView() {
        mPullToRefreshScrollView = findViewById(R.id.answer_issue_scrollview);
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
        mListview = findViewById(R.id.answer_issue_listview);
        /**
         * 消息
         */
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        String[] from = {"img", "name", "time", "details", "like_number", "commnent_number"};
        int[] to = {R.id.answers_issue_avatar, R.id.answers_issue_name, R.id.answers_issue_time,
                R.id.answers_issue_details, R.id.answers_issue_like_number, R.id.answers_issue_commnent_number};
        simpleAdapter = new SimpleAdapter(getActivity(), dataList, R.layout.answer_issue_listview_item, from, to);
        //配置适配器
        mListview.setAdapter(simpleAdapter);
        ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentNewDetails = new Intent(getActivity(), NewsDetailsActivity.class);
                startActivity(intentNewDetails);
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
            map.put("like_number", iconLikeNumber[i]);
            map.put("commnent_number", iconCommnentNumber[i]);
            dataList.add(map);
        }
        return dataList;
    }

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
}
