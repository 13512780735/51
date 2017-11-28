package com.likeit.as51scholarship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.likeit.as51scholarship.activitys.AskActivity;
import com.likeit.as51scholarship.activitys.Container;
import com.likeit.as51scholarship.adapters.AnswerDetailsListAdapter;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.question.AnswerListBean;
import com.likeit.as51scholarship.utils.MyActivityManager;
import com.likeit.as51scholarship.view.CircleImageView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnswerDetailsActivity extends Container {

    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.iv_header_right)
    ImageView ivRight;
    @BindView(R.id.iv_header_left)
    ImageView ivLeft;
    @BindView(R.id.answers_issue_avatar)
    CircleImageView img;
    @BindView(R.id.answers_issue_name)
    TextView name;
    @BindView(R.id.answers_issue_time)
    TextView time;
    @BindView(R.id.answers_issue_live01)
    TextView live01;
    @BindView(R.id.answers_issue_live02)
    TextView country01;
    @BindView(R.id.answers_issue_details)
    TextView details;
    @BindView(R.id.answer_details_listview)
    PullToRefreshListView mListview;


    private String id, author, headimg, content, category, country, interval;
    private List<AnswerListBean> answerData;
    private AnswerDetailsListAdapter mAdapter;
    private  int page=1;
    private String total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_details);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        author = intent.getStringExtra("author");
        headimg = intent.getStringExtra("headimg");
        content = intent.getStringExtra("content");
        category = intent.getStringExtra("category");
        country = intent.getStringExtra("country");
        interval = intent.getStringExtra("interval");
        answerData=new ArrayList<AnswerListBean>();
        initData(1);
        showProgress("Loading...");
        initView();
    }

    private void initData(int page) {
        String url= AppConfig.LIKEIT_ANSWER_LIST;
        RequestParams params=new RequestParams();
        params.put("ukey",ukey);
        params.put("qid",id);
        params.put("page", String.valueOf(page));
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                try {
                    JSONObject objecr=new JSONObject(response);
                    String code=objecr.optString("code");
                    String message=objecr.optString("message");
                    if("1".equals(code)){
                        total = objecr.optString("total");
                        JSONArray array=objecr.optJSONArray("data");
                        for(int i=0;i<array.length();i++){
                            JSONObject obj=array.optJSONObject(i);
                            AnswerListBean mAnswerListBean=new AnswerListBean();
                            mAnswerListBean.setId(obj.optString("id"));
                            mAnswerListBean.setAuthor(obj.optString("author"));
                            mAnswerListBean.setHeadimg(obj.optString("headimg"));
                            mAnswerListBean.setContent(obj.optString("content"));
                            mAnswerListBean.setInterval(obj.optString("interval"));
                            mAnswerListBean.setFloor(obj.optInt("floor"));
                            answerData.add(mAnswerListBean);
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
                mListview.onRefreshComplete();
            }
        });
    }
    private void initData1(int page) {
        String url= AppConfig.LIKEIT_ANSWER_LIST;
        RequestParams params=new RequestParams();
        params.put("ukey",ukey);
        params.put("qid",id);
        params.put("page", String.valueOf(page));
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                try {
                    JSONObject objecr=new JSONObject(response);
                    String code=objecr.optString("code");
                    String message=objecr.optString("message");
                    if("1".equals(code)){
                        total = objecr.optString("total");
                        JSONArray array=objecr.optJSONArray("data");
                        for(int i=0;i<array.length();i++){
                            JSONObject obj=array.optJSONObject(i);
                            AnswerListBean mAnswerListBean=new AnswerListBean();
                            mAnswerListBean.setId(obj.optString("id"));
                            mAnswerListBean.setAuthor(obj.optString("author"));
                            mAnswerListBean.setHeadimg(obj.optString("headimg"));
                            mAnswerListBean.setContent(obj.optString("content"));
                            mAnswerListBean.setInterval(obj.optString("interval"));
                            mAnswerListBean.setFloor(obj.optInt("floor"));
                            answerData.add(mAnswerListBean);
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
                mListview.onRefreshComplete();
            }
        });
    }
    private void initView() {
        mAdapter=new AnswerDetailsListAdapter(mContext,answerData);
        mListview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        tvHeader.setText("问答详情");
        ivRight.setImageResource(R.mipmap.news_icon_edit);
        ivLeft.setImageResource(R.mipmap.nav_icon_return);
        ImageLoader.getInstance().displayImage(headimg,img);
        name.setText(author);
        time.setText(interval);
        live01.setText(category);
        country01.setText(country);
        details.setText("问题："+content);
        mListview.setMode(PullToRefreshBase.Mode.BOTH);
        mListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                // 获取当前时间并格式化
                String label = DateUtils.formatDateTime(
                        mContext, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 设置刷新文本说明(刷新过程中)
                mListview.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
                mListview.getLoadingLayoutProxy().setPullLabel("下拉刷新");
                mListview.getLoadingLayoutProxy().setReleaseLabel("松开开始刷新数据");
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
                        "最后更新时间:" + label);
                refresh();

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
    }

    private void refresh() {
        mAdapter.addAll(answerData, true);
        initData(1);
        showProgress("Loading...");
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.iv_header_left,R.id.iv_header_right})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_header_left:
                onBackPressed();
                break;
            case R.id.iv_header_right:
                Intent intentAsk = new Intent(this, AskActivity.class);
                startActivity(intentAsk);
                break;
        }
}
}
