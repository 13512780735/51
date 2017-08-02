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
public class AnswersFragment02 extends BaseFragment  implements
        PullToRefreshBase.OnRefreshListener2<ScrollView>{


    private PullToRefreshScrollView mPullToRefreshScrollView;
    private MyListview mListview;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simpleAdapter;
    // 图片封装为一个数组
    private int[] icon = {R.mipmap.message_chat_avatar, R.mipmap.message_chat_avatar,
            R.mipmap.message_chat_avatar, R.mipmap.message_chat_avatar, R.mipmap.message_chat_avatar};
    private String[] iconName = {"Lana","Tom","Jim","Lucy","LiLy"};
    private String[] iconAddress= {"美国", "美国", "美国", "美国", "美国"};
    private String[] iconState = {"在读", "在读", "在读", "在读", "在读"};
    private String[] iconSchool = {"迈阿密大学", "迈阿密大学", "迈阿密大学", "迈阿密大学", "迈阿密大学"};
    private String[] iconMajor = {"医学|本科", "医学|本科", "医学|本科", "医学|本科", "医学|本科"};
    @Override
    protected int setContentView() {
        return R.layout.fragment_answers_fragment02;
    }

    @Override
    protected void lazyLoad() {
        initView();
    }

    private void initView() {
        mPullToRefreshScrollView = findViewById(R.id.answer_senior_scrollview);
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
        mListview = findViewById(R.id.answer_senior_listview);
        /**
         * 消息
         */
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        String[] from = {"img","name", "address","state","school","major"};
        int[] to = {R.id.answers_senior_avatar, R.id.answers_senior_name, R.id.answers_senior_address,
                R.id.answers_senior_states,R.id.answers_senior_school,R.id.answers_senior_major};
        simpleAdapter = new SimpleAdapter(getActivity(), dataList, R.layout.answers_senior_listview_items, from, to);
        //配置适配器
        mListview.setAdapter(simpleAdapter);
        ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentNewDetails=new Intent(getActivity(),AnswersUserDetailsActivity.class);
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
            map.put("address", iconAddress[i]);
            map.put("state", iconState[i]);
            map.put("school", iconSchool[i]);
            map.put("major", iconMajor[i]);
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
