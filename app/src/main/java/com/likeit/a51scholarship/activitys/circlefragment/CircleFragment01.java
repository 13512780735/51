package com.likeit.a51scholarship.activitys.circlefragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.userdetailsfragment.UserDetailsFragment01;
import com.likeit.a51scholarship.adapters.CircleDetailsEssayAdapter;
import com.likeit.a51scholarship.configs.AppConfig;
import com.likeit.a51scholarship.fragments.BaseFragment;
import com.likeit.a51scholarship.http.HttpUtil;
import com.likeit.a51scholarship.model.circle_model.CircleEssayModel;
import com.likeit.a51scholarship.utils.ListScrollUtil;
import com.likeit.a51scholarship.view.CustomViewpager;
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
public class CircleFragment01 extends BaseFragment implements
        PullToRefreshBase.OnRefreshListener2<ScrollView>{

    private String circleId;
    private ProgressDialog dialog;
    private List<CircleEssayModel> essayData;
    private CircleDetailsEssayAdapter mAdapter;
    private MyListview mListview;
    PullToRefreshScrollView mPullToRefreshScrollView;
    @Override
    protected int setContentView() {
        return R.layout.fragment_circle_fragment01;
    }

    @Override
    protected void lazyLoad() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        Intent intent = getActivity().getIntent();
        circleId = intent.getStringExtra("circleId");
        essayData = new ArrayList<CircleEssayModel>();
        initData();
        dialog.show();
        initView();

    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        if (essayData == null || essayData.size() == 0) {
            return;
        } else {
            essayData.clear();
            // mAdapter.addAll(essayData, true);
            initData();
            dialog.show();
            mAdapter.notifyDataSetChanged();
        }

    }

    private void initView() {
        mPullToRefreshScrollView= (PullToRefreshScrollView) getActivity().findViewById(R.id.circle_details_scrollview);
        mListview = findViewById(R.id.circle_essay_listview01);
        mAdapter = new CircleDetailsEssayAdapter(getActivity(), essayData);
        mListview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
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
    }

    private void initData() {
        Log.d("TAG", circleId);
        String url = AppConfig.LIKEIT_GROUP_GETPOST;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("gid", circleId);
        params.put("rec", "0");
        //  params.put("page", String.valueOf(page));
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                dialog.dismiss();
                Log.d("TAG", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        JSONArray array = obj.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            CircleEssayModel mCircleEssayModel = JSON.parseObject(object.toString(), CircleEssayModel.class);
                            essayData.add(mCircleEssayModel);
                        }

                        mAdapter.notifyDataSetChanged();
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
//                mListview.onRefreshComplete();
            }
        });

    }

    public static Fragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text", text);
        UserDetailsFragment01 fragment = new UserDetailsFragment01();
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
