package com.likeit.as51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.adapters.CourseListAdapter;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.CourseListBean;
import com.likeit.as51scholarship.utils.ListScrollUtil;
import com.likeit.as51scholarship.view.MyListview;
import com.likeit.as51scholarship.view.expandtabview.ExpandTabView;
import com.likeit.as51scholarship.view.expandtabview.ViewLeft;
import com.likeit.as51scholarship.view.expandtabview.ViewMiddle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CourseListActivity extends Container implements
        PullToRefreshBase.OnRefreshListener2<ScrollView> {
    @BindView(R.id.course_scrollview)
    PullToRefreshScrollView mPullToRefreshScrollView;
    @BindView(R.id.course_listview)
    MyListview mListview;
    @BindView(R.id.expandtab_view)
    ExpandTabView expandTabView;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    private ViewMiddle viewMiddle;
    private ViewLeft viewLeft;
    private ArrayList<CourseListBean> courseData;
    private CourseListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        ButterKnife.bind(this);
        courseData = new ArrayList<CourseListBean>();
        initData();
        showProgress("Loading...");
        initView();
        //  initVaule();
        // initListener();
    }

    private void initData() {
        String url = AppConfig.LIKEIT_COURSE_GETLIST;
        RequestParams parmas = new RequestParams();
        parmas.put("ukey", ukey);
        HttpUtil.post(url, parmas, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        JSONArray array = obj.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            CourseListBean mCourseListBean = new CourseListBean();
                            mCourseListBean.setId(object.optString("id"));
                            mCourseListBean.setTitle(object.optString("title"));
                            mCourseListBean.setDuration(object.optString("duration"));
                            mCourseListBean.setCover(object.optString("cover"));
                            mCourseListBean.setIsfree(object.optString("isfree"));
                            mCourseListBean.setView(object.optString("view"));
                            mCourseListBean.setAmount(object.optString("amount"));
                            mCourseListBean.setCreate_time(object.optString("create_time"));
                            mCourseListBean.setVideo_url(object.optString("video_url"));
                            mCourseListBean.setVideo_url(object.optString("video_url"));
                            mCourseListBean.setContent(object.optString("content"));
                            courseData.add(mCourseListBean);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {

            }
        });
    }

    private void initListener() {
        viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {

            @Override
            public void getValue(String showText) {

                onRefresh(viewMiddle, showText);

            }
        });

    }

    //    private void initVaule() {
//     mViewArray.add(viewMiddle);
//       // mViewArray.add(viewLeft);
//        ArrayList<String> mTextArray = new ArrayList<String>();
//        mTextArray.add("全部类别");
//        expandTabView.setValue(mTextArray, mViewArray);
//    }
    private void initView() {
        mAdapter = new CourseListAdapter(mContext, courseData);
        mListview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
//        viewMiddle = new ViewMiddle(this);
//        viewLeft = new ViewLeft(this);
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
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String video_url = courseData.get(position).getVideo_url();
                String content = courseData.get(position).getContent();
                String mId = courseData.get(position).getId();
                Intent intent = new Intent(mContext, CourseDetailsActivity.class);
                intent.putExtra("video_url", video_url);
                intent.putExtra("content", content);
                intent.putExtra("mId", mId);
                startActivity(intent);
            }
        });
    }


    @OnClick({R.id.top_bar_back_img, R.id.top_bar_right_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back_img:
                onBackPressed();
                break;
            case R.id.top_bar_right_img:
                Intent intentSearchInfo = new Intent(this, SearchInfoActivity.class);
                intentSearchInfo.putExtra("key", "4");
                startActivity(intentSearchInfo);
                break;
        }

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

    private void onRefresh(View view, String showText) {

        expandTabView.onPressBack();
//        int position = getPositon(view);
//        if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
//            expandTabView.setTitle(showText, position);
//        }
        Toast.makeText(mContext, showText, Toast.LENGTH_SHORT).show();

    }

    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onBackPressed() {

        if (!expandTabView.onPressBack()) {
            finish();
        }
    }

}
