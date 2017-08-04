package com.likeit.a51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.adapters.SchoolListAdapter;
import com.likeit.a51scholarship.configs.AppConfig;
import com.likeit.a51scholarship.http.HttpUtil;
import com.likeit.a51scholarship.model.HomeItemNewsBean;
import com.likeit.a51scholarship.model.SchoolListBean;
import com.likeit.a51scholarship.utils.AndroidWorkaround;
import com.likeit.a51scholarship.utils.ListScrollUtil;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.utils.ToastUtil;
import com.likeit.a51scholarship.view.MyListview;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchSchoolActivity extends Container {
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.search_content_et)
    EditText searchContentEt;
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
    @BindView(R.id.school_list_scrollview)
    PullToRefreshScrollView mPullToRefreshScrollView;
    @BindView(R.id.school_list_listview)
    MyListview mListview;
    private List<SchoolListBean> schoolData;
    private SchoolListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_search_school);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        schoolData=new ArrayList<SchoolListBean>();
        initData();
        showProgress("Loading...");
        initView();
    }

    private void initData() {
        String url = AppConfig.LIKEIT_SCHOOL_LIST;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
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
            }
        });    }

    private void initView() {
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
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
        });
        mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间");
        mPullToRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel("下拉刷新");
        mPullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新");
        adapter=new SchoolListAdapter(mContext,schoolData);
        adapter.addAll(schoolData,true);
        adapter.notifyDataSetChanged();
        mListview.setAdapter(adapter);
        ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = schoolData.get(position).getName();
                String en_name = schoolData.get(position).getEn_name();
                String img = schoolData.get(position).getImg();
                Intent intentSchoolDetail = new Intent();
                intentSchoolDetail.putExtra("name", name);//英文名字
                intentSchoolDetail.putExtra("en_name", en_name);//中文名字
                intentSchoolDetail.putExtra("img", img);//图片
                intentSchoolDetail.setClass(mContext, SchoolDetailActivity.class);
                startActivity(intentSchoolDetail);
            }
        });
       adapter.setOnApplyClickListener(new SchoolListAdapter.onSchoolApplyClickListener() {
           @Override
           public void onApplyClick(int i) {
               ToastUtil.showS(mContext,"申请成功！");
           }
       });
    }



    @OnClick({R.id.back_img, R.id.audio_icon, R.id.search_layout, R.id.message_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.audio_icon:
            case R.id.search_layout:
                toActivity(SearchInfoActivity.class);
                break;
            case R.id.message_img:
                break;
        }
    }
}
