package com.likeit.as51scholarship.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.newsfragment.NewsDetailsActivity;
import com.likeit.as51scholarship.adapters.HomeItemNewsAdapter;
import com.likeit.as51scholarship.adapters.SchoolListAdapter;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.HomeItemNewsBean;
import com.likeit.as51scholarship.model.SchoolListBean;
import com.likeit.as51scholarship.utils.MyActivityManager;
import com.likeit.as51scholarship.utils.StringUtil;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchInfoActivity extends Container {
    @BindView(R.id.top_bar_back_img)
    ImageView topBarBackImg;
    @BindView(R.id.search_content_et)
    EditText searchContentEt;
    @BindView(R.id.search_img)
    ImageView searchImg;
    @BindView(R.id.search_layout)
    LinearLayout searchLayout;
    @BindView(R.id.search_listview)
    PullToRefreshListView mListView;
    private String key;
    private String url;
    private String searchContent;
    private JSONObject obj;
    private String message;
    private String code;
    private List<SchoolListBean> schoolData;
    private List<HomeItemNewsBean> newsData;
    private SchoolListAdapter schoolAdater;
    private HomeItemNewsAdapter newAdapter;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_info);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        inputMethodManager =(InputMethodManager)this.getApplicationContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");//1.院校搜索，2.资讯搜索
        schoolData = new ArrayList<SchoolListBean>();
        newsData = new ArrayList<HomeItemNewsBean>();
        initView();
    }

    private void initView() {
        schoolAdater = new SchoolListAdapter(mContext, schoolData);
        newAdapter = new HomeItemNewsAdapter(mContext, newsData);
        if ("1".equals(key)) {
            mListView.setAdapter(schoolAdater);
            schoolAdater.notifyDataSetChanged();
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = schoolData.get(position).getName();
                    String en_name = schoolData.get(position).getEn_name();
                    String img = schoolData.get(position).getImg();
                    String sid = schoolData.get(position).getId();
                    Intent intentSchoolDetail = new Intent();
                    intentSchoolDetail.putExtra("name", name);//英文名字
                    intentSchoolDetail.putExtra("en_name", en_name);//中文名字
                    intentSchoolDetail.putExtra("img", img);//图片
                    intentSchoolDetail.putExtra("sid", sid);//图片
                    intentSchoolDetail.setClass(mContext, SchoolDetailActivity.class);
                    startActivity(intentSchoolDetail);
                }
            });
        } else if ("2".equals(key)) {
            mListView.setAdapter(newAdapter);
            newAdapter.notifyDataSetChanged();
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String id1=newsData.get(position).getId();
                    Intent intentNewDetails = new Intent(mContext, NewsDetailsActivity.class);
                    intentNewDetails.putExtra("id",id1);
                    startActivity(intentNewDetails);
                }
            });
        }

    }


    private void initSearch() {
        Log.d("TAG", "searchContent-->" + searchContent);
        if ("1".equals(key)) {
            url = AppConfig.LIKEIT_SEARCH_SCHOOL;
        } else if ("2".equals(key)) {
            url = AppConfig.LIKEIT_SEARCH_NEWS;
        }
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("keywords", searchContent);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                Log.d("TAG", response);
                json(response);
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
                ToastUtil.showS(mContext, "网络异常！");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                disShowProgress();
            }
        });
    }

    private void json(String response) {
        if ("1".equals(key)) {
            try {
                obj = new JSONObject(response);
                code = obj.optString("code");
                message = obj.optString("message");
                if ("1".equals(code)) {
                    JSONArray array = obj.optJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.optJSONObject(i);
                        SchoolListBean schoolListBean = new SchoolListBean();
                        schoolListBean.setId(jsonObject.optString("id"));
                        schoolListBean.setName(jsonObject.optString("name"));
                        schoolListBean.setEn_name(jsonObject.optString("en_name"));
                        schoolListBean.setCountry_name(jsonObject.optString("country_name"));
                        schoolListBean.setRanking(jsonObject.optString("ranking"));
                        schoolListBean.setRate(jsonObject.optString("rate"));
                        schoolListBean.setScholarship(jsonObject.optString("scholarship"));
                        schoolListBean.setImg(jsonObject.optString("img"));
                        schoolData.add(schoolListBean);
                    }
                    schoolAdater.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if ("2".equals(key)) {
            try {
                obj = new JSONObject(response);
                code = obj.optString("code");
                message = obj.optString("message");
                if ("1".equals(code)) {
                    JSONArray arrayNews = obj.optJSONArray("data");
                    for (int i = 0; i < arrayNews.length(); i++) {
                        JSONObject jsonObject = arrayNews.optJSONObject(i);
                        HomeItemNewsBean homeItemNewsBean = new HomeItemNewsBean();
                        homeItemNewsBean.setId(jsonObject.optString("id"));
                        homeItemNewsBean.setAuthor(jsonObject.optString("author"));
                        homeItemNewsBean.setTitle(jsonObject.optString("title"));
                        homeItemNewsBean.setDescription(jsonObject.optString("description"));
                        homeItemNewsBean.setInterval(jsonObject.optString("interval"));
                        homeItemNewsBean.setView(jsonObject.optString("view"));
                        homeItemNewsBean.setComment(jsonObject.optString("comment"));
                        newsData.add(homeItemNewsBean);
                    }
                    Log.d("TAG", "newsData-->" + newsData);
                    // newAdapter.addAll(NewsData, false);
                    newAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.top_bar_back_img, R.id.search_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back_img:
                onBackPressed();
                break;
            case R.id.search_img:
                searchContent = searchContentEt.getText().toString().trim();
                inputMethodManager.hideSoftInputFromWindow(searchContentEt.getWindowToken(), 0);
                if (StringUtil.isBlank(searchContent)) {
                    ToastUtil.showS(mContext, "搜索内容不能为空！");
                } else {
                    initSearch();
                    showProgress("Loading...");
                }
                break;
        }
    }
}
