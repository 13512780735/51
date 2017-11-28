package com.likeit.as51scholarship.activitys.my_center;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.Container;
import com.likeit.as51scholarship.adapters.MyQuizAdapter;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.MyQuizInfo;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizActivity extends Container {
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.quiz_listview)
    PullToRefreshListView mListView;
    private List<MyQuizInfo> data;
    private MyQuizAdapter mAdapter;
    int page = 1;
    private String total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
        data = new ArrayList<>();
        initData(1);
        showProgress("Loading...");
        initView();
    }

    private void refresh1() {
        if (data == null || data.size() == 0) {
            return;
        } else {
            data.clear();
            initData(1);
            showProgress("Loading...");
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initData(int page) {
        String url = AppConfig.LIKEIT_QUESTION_MYLIST;
        final RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("page", String.valueOf(page));
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                Log.d("TAG", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String msg = obj.optString("message");
                    if ("1".equals(code)) {
                        total = obj.optString("total");
                        JSONArray array = obj.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            MyQuizInfo mMyQuizInfo = new MyQuizInfo();
                            JSONObject object = array.optJSONObject(i);
                            mMyQuizInfo.setId(object.optString("id"));
                            mMyQuizInfo.setAuthor(object.optString("author"));
                            mMyQuizInfo.setHeadimg(object.optString("headimg"));
                            mMyQuizInfo.setContent(object.optString("content"));
                            mMyQuizInfo.setCategory(object.optString("category"));
                            mMyQuizInfo.setCountry(object.optString("country"));
                            mMyQuizInfo.setInterval(object.optString("interval"));
                            mMyQuizInfo.setView(object.optString("view"));
                            mMyQuizInfo.setAnswer_num(object.optString("answer_num"));
                            data.add(mMyQuizInfo);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                disShowProgress();
                mListView.onRefreshComplete();
            }
        });
    }

    private void initView() {
        tvHeader.setText("我的提问");
        mAdapter = new MyQuizAdapter(mContext, data);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                // 获取当前时间并格式化
                String label = DateUtils.formatDateTime(
                        mContext, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 设置刷新文本说明(刷新过程中)
                mListView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
                mListView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
                mListView.getLoadingLayoutProxy().setReleaseLabel("松开开始刷新数据");
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
                    mListView.getLoadingLayoutProxy().setRefreshingLabel(
                            "正在加载更多数据");
                    mListView.getLoadingLayoutProxy().setPullLabel(
                            "上拉可以加载更多");
                    mListView.getLoadingLayoutProxy().setReleaseLabel(
                            "松开立即加载更多");

                } else {
                    // 上一次请求已经没有数据了
                    mListView.getLoadingLayoutProxy().setPullLabel(
                            "已经全部数据加载完毕...");
                    mListView.getLoadingLayoutProxy().setReleaseLabel(
                            "已经全部数据加载完毕...");
                }

            }
        });
        mAdapter.setOnDelClickListener(new MyQuizAdapter.onDelClickListener() {
            @Override
            public void onDelClick(int i) {
                String nid = data.get(i).getId();
                deleteIssue(nid);
            }
        });
    }

    private void deleteIssue(String nid) {
        String url = AppConfig.LIKEIT_QUESTION_DELETEQUESTION;
        final RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("qid", nid);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String msg = obj.optString("msg");
                    if ("1".equals(code)) {
                        showToast(msg);
                        refresh1();
                    } else showToast(msg);
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
            }
        });
    }

    private void initData1(int page) {
        String url = AppConfig.LIKEIT_QUESTION_MYLIST;
        final RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("page", String.valueOf(page));
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                Log.d("TAG", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String msg = obj.optString("message");
                    if ("1".equals(code)) {
                        total = obj.optString("total");
                        JSONArray array = obj.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            MyQuizInfo mMyQuizInfo = new MyQuizInfo();
                            JSONObject object = array.optJSONObject(i);
                            mMyQuizInfo.setId(object.optString("id"));
                            mMyQuizInfo.setAuthor(object.optString("author"));
                            mMyQuizInfo.setHeadimg(object.optString("headimg"));
                            mMyQuizInfo.setContent(object.optString("content"));
                            mMyQuizInfo.setCategory(object.optString("category"));
                            mMyQuizInfo.setCountry(object.optString("country"));
                            mMyQuizInfo.setInterval(object.optString("interval"));
                            mMyQuizInfo.setView(object.optString("view"));
                            mMyQuizInfo.setAnswer_num(object.optString("answer_num"));
                            data.add(mMyQuizInfo);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                disShowProgress();
                mListView.onRefreshComplete();
            }
        });
    }


    @OnClick({R.id.backBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;

        }
    }
}
