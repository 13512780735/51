package com.likeit.a51scholarship.activitys.livefragment;


import android.content.Intent;
import android.os.Bundle;
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
public class LiveFragment01 extends BaseFragment  implements
        PullToRefreshBase.OnRefreshListener2<ScrollView>{


    private PullToRefreshScrollView mPullToRefreshScrollView;
    private MyListview mListview;
    // 图片封装为一个数组
    private int[] icon = {R.mipmap.test01, R.mipmap.test01,
            R.mipmap.test01, R.mipmap.test01, R.mipmap.test01};
    private String[] iconResult = {"就读迈阿密大学是怎样的一种体验", "就读迈阿密大学是怎样的一种体验",
            "就读迈阿密大学是怎样的一种体验", "就读迈阿密大学是怎样的一种体验","就读迈阿密大学是怎样的一种体验"};
    private String[] iconTime = {"2017年02月04日 17:00-18:00", "2017年02月04日 17:00-18:00", "2017年02月04日 17:00-18:00","2017年02月04日 17:00-18:00", "2017年02月04日 17:00-18:00"};
    private String[] iconName = {"Lana", "Jim", "Lucy", "LiLy", "Tom"};
    private int[] icon01 = {R.mipmap.message_chat_avatar, R.mipmap.message_chat_avatar, R.mipmap.message_chat_avatar,R.mipmap.message_chat_avatar, R.mipmap.message_chat_avatar};
    private String[] iconSpeciality = {"迈阿密大学 金融专业", "迈阿密大学 金融专业", "迈阿密大学 金融专业", "迈阿密大学 金融专业", "迈阿密大学 金融专业"};
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> dataList;
    @Override
    protected int setContentView() {
        return R.layout.fragment_live_fragment01;
    }

    @Override
    protected void lazyLoad() {
        mPullToRefreshScrollView = findViewById(R.id.live_header_scrollview);
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
        mListview = findViewById(R.id.live_header_listview);
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        String[] from = {"name","readNumber", "readTime","commentNumber","img"};
        int[] to = {R.id.new_name, R.id.news_read_number, R.id.news_read_time, R.id.news_comment_number,R.id.imageView_avatar};
        simpleAdapter = new SimpleAdapter(getActivity(), dataList, R.layout.live_list_items, from, to);
        //配置适配器
        mListview.setAdapter(simpleAdapter);
        ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentNewDetails=new Intent(getActivity(),NewsDetailsActivity.class);
                startActivity(intentNewDetails);
            }
        });
    }
    private List<Map<String, Object>> getData() {
        for (int i = 0; i < icon.length; i++) {
            Log.d("TAG", "" + icon.length);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icon[i]);
            map.put("name", iconResult[i]);
            map.put("readNumber", iconTime[i]);
            map.put("readTime", iconName[i]);
            map.put("commentNumber", icon01[i]);
            map.put("commentNumber", iconSpeciality[i]);
            dataList.add(map);
        }
        return dataList;
    }
    public static LiveFragment01 newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text", text);
        LiveFragment01 fragment = new LiveFragment01();
        fragment.setArguments(args);
        return fragment;
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
