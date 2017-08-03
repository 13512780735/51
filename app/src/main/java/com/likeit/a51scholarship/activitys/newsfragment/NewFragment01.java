package com.likeit.a51scholarship.activitys.newsfragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.adapters.HomeItemNewsAdapter;
import com.likeit.a51scholarship.configs.AppConfig;
import com.likeit.a51scholarship.fragments.BaseFragment;
import com.likeit.a51scholarship.http.HttpUtil;
import com.likeit.a51scholarship.model.HomeItemNewsBean;
import com.likeit.a51scholarship.utils.ListScrollUtil;
import com.likeit.a51scholarship.view.MyListview;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewFragment01 extends BaseFragment implements
        PullToRefreshBase.OnRefreshListener2<ScrollView> {

    PullToRefreshScrollView mPullToRefreshScrollView;
    private MyListview mListview;
    private ProgressDialog dialog;
    private List<HomeItemNewsBean> NewsData;
    private HomeItemNewsAdapter newAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_new_fragment01;
    }

    @Override
    protected void lazyLoad() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        NewsData = new ArrayList<HomeItemNewsBean>();
        newAdapter = new HomeItemNewsAdapter(getActivity(), NewsData);
        initData();
        dialog.show();
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        //refresh();
    }

    private void refresh() {
        newAdapter.addAll(NewsData, true);
        newAdapter.notifyDataSetChanged();
    }

    private void initData() {
        String url = AppConfig.LIKEIT_NEWS;
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
                        JSONArray newData = obj.optJSONArray("data");
                        for (int i = 0; i < newData.length(); i++) {
                            JSONObject jsonObject = newData.optJSONObject(i);
                            HomeItemNewsBean homeItemNewsBean = new HomeItemNewsBean();
                            homeItemNewsBean.setId(jsonObject.optString("id"));
                            homeItemNewsBean.setAuthor(jsonObject.optString("author"));
                            homeItemNewsBean.setTitle(jsonObject.optString("title"));
                            homeItemNewsBean.setDescription(jsonObject.optString("description"));
                            homeItemNewsBean.setInterval(jsonObject.optString("interval"));
                            homeItemNewsBean.setView(jsonObject.optString("view"));
                            homeItemNewsBean.setComment(jsonObject.optString("comment"));
                            NewsData.add(homeItemNewsBean);
                        }
                        Log.d("TAG", "HomeSchool-->" + NewsData);
                       //newAdapter.addAll(NewsData, false);
                        newAdapter.notifyDataSetChanged();
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

    private void initView() {
        mPullToRefreshScrollView = findViewById(R.id.news_header_scrollview);
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
        mListview = findViewById(R.id.news_header_listview);
        newAdapter.addAll(NewsData,true);
        newAdapter.notifyDataSetChanged();
        mListview.setAdapter(newAdapter);
        ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentNewDetails = new Intent(getActivity(), NewsDetailsActivity.class);
                startActivity(intentNewDetails);
            }
        });
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
