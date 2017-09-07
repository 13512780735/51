package com.likeit.a51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.adapters.SchoolListAdapter;
import com.likeit.a51scholarship.configs.AppConfig;
import com.likeit.a51scholarship.http.HttpUtil;
import com.likeit.a51scholarship.model.SchoolListBean;
import com.likeit.a51scholarship.utils.ListScrollUtil;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.view.MyListview;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchSchoolActivity extends Container {
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.search_content_et)
    TextView searchContentEt;
    @BindView(R.id.audio_icon)
    ImageView audioIcon;
    @BindView(R.id.search_layout)
    LinearLayout searchLayout;
    @BindView(R.id.message_img)
    ImageView messageImg;
    @BindView(R.id.hot_school)
    RadioButton hotSchool;
    @BindView(R.id.recommend_school)
    RadioButton recommendSchool;
    @BindView(R.id.offer_school)
    RadioButton offerSchool;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.content_frame)
    FrameLayout contentFrame;
    @BindView(R.id.line_hot_school)
    View lineHotSchool;
    @BindView(R.id.line_recommend)
    View lineRecommend;
    @BindView(R.id.line_offer)
    View lineOffer;
    @BindView(R.id.school_list_listview)
    PullToRefreshListView mListview;
    private List<SchoolListBean> schoolData;
    private SchoolListAdapter adapter;
    private String stage, country, area, lang, nature, style, toefl, toeic, yasi;
    private String rank, rate, scholarship;
    private String total;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_school);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        stage = intent.getStringExtra("stage");
        country = intent.getStringExtra("country");
        area = intent.getStringExtra("area");
        lang = intent.getStringExtra("lang");
        nature = intent.getStringExtra("nature");
        style = intent.getStringExtra("style");
        toefl = intent.getStringExtra("toefl");
        toeic = intent.getStringExtra("toeic");
        yasi = intent.getStringExtra("yasi");
        rank = "1";
        rate = "0";
        scholarship = "0";
        schoolData = new ArrayList<SchoolListBean>();
//        initData(1);
//        showProgress("Loading...");
        initView();
    }

    private void initData(int page) {
        String url = AppConfig.LIKEIT_SCHOOL_LIST;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("stage", stage);
        params.put("country", country);
        params.put("area", area);
        params.put("lang", lang);
        params.put("nature", nature);
        params.put("style", style);
        params.put("toefl", toefl);
        params.put("toeic", toeic);
        params.put("yasi", yasi);
        params.put("rank", rank);
        params.put("rate", rate);
        params.put("scholarship", scholarship);
        params.put("page", String.valueOf(page));
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                Log.d("TAG", "HomeSchool-->" + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");

                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        total = obj.optString("total");
                        JSONArray newData = obj.optJSONArray("data");
                        for (int i = 0; i < newData.length(); i++) {
                            JSONObject jsonObject = newData.optJSONObject(i);
                            SchoolListBean schoolListBean = new SchoolListBean();
                            schoolListBean.setId(jsonObject.optString("id"));
                            schoolListBean.setName(jsonObject.optString("name"));
                            schoolListBean.setEn_name(jsonObject.optString("en_name"));
                            schoolListBean.setCountry_name(jsonObject.optString("country_name"));
                            schoolListBean.setRanking(jsonObject.optString("ranking"));
                            schoolListBean.setRate(jsonObject.optString("rate"));
                            schoolListBean.setScholarship(jsonObject.optString("scholarship"));
                            schoolListBean.setImg(jsonObject.optString("img"));
                            schoolListBean.setCountry_id(jsonObject.optString("country_id"));
                            schoolData.add(schoolListBean);
                        }
                        Log.d("TAG", "HomeSchool-->" + schoolData);
                        //newAdapter.addAll(NewsData, false);
                        adapter.notifyDataSetChanged();
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

    //    @Override
//    protected void onResume() {
//        super.onResume();
//        refresh();
//    }
    @Override
    protected void onResume() {
        super.onResume();
        refresh1();
    }

    private void refresh1() {
        adapter.addAll(schoolData, true);
        initData(1);
        showProgress("Loading...");
        adapter.notifyDataSetChanged();
    }

    private void refresh() {
        if (schoolData.size() == 0) {
            return;
        }
        schoolData.clear();
        initData(1);
        showProgress("Loading...");
        // adapter.addAll(schoolData,true);
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        adapter = new SchoolListAdapter(mContext, schoolData);
        mListview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
        adapter.setOnApplyClickListener(new SchoolListAdapter.onSchoolApplyClickListener() {
            @Override
            public void onApplyClick(int i) {
                //ToastUtil.showS(mContext, "申请成功！");
                Intent intent = new Intent(mContext, SchoolApplyActivity.class);
                intent.putExtra("name", schoolData.get(i).getName());
                intent.putExtra("address", schoolData.get(i).getCountry_name());
                intent.putExtra("country_id", schoolData.get(i).getCountry_id());
                intent.putExtra("sid", schoolData.get(i).getId());
                startActivity(intent);
            }
        });
    }

    private void initData1(int page) {
        String url = AppConfig.LIKEIT_SCHOOL_LIST;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("stage", stage);
        params.put("country", country);
        params.put("area", area);
        params.put("lang", lang);
        params.put("nature", nature);
        params.put("style", style);
        params.put("toefl", toefl);
        params.put("toeic", toeic);
        params.put("yasi", yasi);
        params.put("rank", rank);
        params.put("rate", rate);
        params.put("page", String.valueOf(page));
        params.put("scholarship", scholarship);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
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
                            SchoolListBean schoolListBean = new SchoolListBean();
                            schoolListBean.setId(jsonObject.optString("id"));
                            schoolListBean.setName(jsonObject.optString("name"));
                            schoolListBean.setEn_name(jsonObject.optString("en_name"));
                            schoolListBean.setCountry_name(jsonObject.optString("country_name"));
                            schoolListBean.setRanking(jsonObject.optString("ranking"));
                            schoolListBean.setRate(jsonObject.optString("rate"));
                            schoolListBean.setScholarship(jsonObject.optString("scholarship"));
                            schoolListBean.setImg(jsonObject.optString("img"));
                            schoolListBean.setCountry_id(jsonObject.optString("country_id"));
                            schoolData.add(schoolListBean);
                        }
                        Log.d("TAG", "HomeSchool-->" + schoolData);
                        //newAdapter.addAll(NewsData, false);
                        adapter.notifyDataSetChanged();
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


    @OnClick({R.id.back_img, R.id.search_layout, R.id.message_img, R.id.hot_school, R.id.recommend_school, R.id.offer_school})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.search_layout:
                Intent intentSchool = new Intent(mContext, SearchInfoActivity.class);
                intentSchool.putExtra("key", "1");
                startActivity(intentSchool);
                break;
            case R.id.message_img:
                //Intent intentFilter=new Intent(mContext,SchoolFilterActivity.class);
                toActivity(SchoolFilterActivity.class);
                Intent intentFilter = new Intent(mContext, SchoolFilterActivity.class);
                intentFilter.putExtra("filterId", "2");
                startActivity(intentFilter);
                finish();
                break;
            case R.id.hot_school:
                if ("0".equals(rank)) {
                    rank = "1";
                    rate="0";
                    scholarship="0";
                } else {
                    rate="0";
                    rank = "0";
                    scholarship="0";
                }
                Log.d("TAG","rank-->"+rank);
                refresh();
                break;
            case R.id.recommend_school:
                if ("0".equals(rate)) {
                    rate = "1";
                    rank = "0";
                    scholarship="0";
                } else {
                    rate = "0";
                    rank = "0";
                    scholarship="0";
                }
                Log.d("TAG","rate-->"+rate);
                refresh();
                break;
            case R.id.offer_school:
                if ("0".equals(scholarship)) {
                    scholarship = "1";
                    rate = "0";
                    rank = "0";
                } else {
                    scholarship = "0";
                    rate = "0";
                    rank = "0";
                }
                Log.d("TAG","scholarship-->"+scholarship);
                refresh();
                break;
        }
    }
}
