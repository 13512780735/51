package com.likeit.as51scholarship.activitys.newsfragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.adapters.HomeItemNewsAdapter;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.fragments.BaseFragment;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.HomeItemNewsBean;
import com.likeit.as51scholarship.model.NewTabBean;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewFragment01 extends BaseFragment {

    private PullToRefreshListView mListview;
    private ProgressDialog dialog;
    private List<HomeItemNewsBean> NewsData;
    private HomeItemNewsAdapter newAdapter;
    private static final String KEY = "title";
    private String cid;
    private String total;
    int page = 1;

    @Override
    protected int setContentView() {
        return R.layout.fragment_new_fragment01;
    }

    @Override
    protected void lazyLoad() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        Bundle bundle = getArguments();
        cid = bundle.getString("url");
        NewsData = new ArrayList<HomeItemNewsBean>();

        initData(1);
        dialog.show();
        initView();
    }

    private void refresh1() {
        if (NewsData == null || NewsData.size() == 0) {
            return;
        } else {
        NewsData.clear();
        initData(1);
        dialog.show();
        newAdapter.notifyDataSetChanged();}
    }

    private void initData(int page) {
        String url = AppConfig.LIKEIT_NEW_GETLIST;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("page", String.valueOf(page));
        params.put("cid", cid);
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
                        total = obj.optString("total");
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
                dialog.dismiss();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
                mListview.onRefreshComplete();
            }
        });
    }

    private void initView() {
        mListview = findViewById(R.id.news_header_listview);
        //  newAdapter.addAll(NewsData, true);
        newAdapter = new HomeItemNewsAdapter(getActivity(), NewsData);
        mListview.setAdapter(newAdapter);
        newAdapter.notifyDataSetChanged();
        mListview.setMode(PullToRefreshBase.Mode.BOTH);
        mListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                // 获取当前时间并格式化
                String label = DateUtils.formatDateTime(
                        getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 设置刷新文本说明(刷新过程中)
                mListview.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
                mListview.getLoadingLayoutProxy().setPullLabel("下拉刷新");
                mListview.getLoadingLayoutProxy().setReleaseLabel("松开开始刷新数据");
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
                        "最后更新时间:" + label);
                refresh1();

            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                page++;
                initData1(page);
                int totalPage = Integer.valueOf(total) % 10;
                if (totalPage == 0) {
                    totalPage = Integer.valueOf(total) / 10;
                } else {
                    totalPage = Integer.valueOf(total) / 10 + 1;
                }
                if (page <= totalPage) {// 上一次请求有数据
                    // 自定义上拉header内容
                    mListview.getLoadingLayoutProxy().setRefreshingLabel(
                            "正在加载更多数据");
                    mListview.getLoadingLayoutProxy().setPullLabel(
                            "上拉可以加载更多");
                    mListview.getLoadingLayoutProxy().setReleaseLabel(
                            "松开立即加载更多");

                } else {
                    // 上一次请求已经没有数据了
                    mListview.getLoadingLayoutProxy().setPullLabel(
                            "已经全部数据加载完毕...");
                    mListview.getLoadingLayoutProxy().setReleaseLabel(
                            "已经全部数据加载完毕...");
                }

            }
        });
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String id1 = NewsData.get(position).getId();
                Intent intentNewDetails = new Intent(getActivity(), NewsDetailsActivity.class);
                intentNewDetails.putExtra("id", id1);
                startActivity(intentNewDetails);
            }
        });
    }

    private void initData1(int page) {
        String url = AppConfig.LIKEIT_NEW_GETLIST;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("page", String.valueOf(page));
        params.put("cid", cid);
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
                        total = obj.optString("total");
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
                dialog.dismiss();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
                mListview.onRefreshComplete();
            }
        });
    }

    public static NewFragment01 newInstance(NewTabBean str) {
        NewFragment01 fragment = new NewFragment01();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, String.valueOf(str));
        fragment.setArguments(bundle);

        return fragment;
    }


}
