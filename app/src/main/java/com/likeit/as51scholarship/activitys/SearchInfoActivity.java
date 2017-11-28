package com.likeit.as51scholarship.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.likeit.as51scholarship.AnswerDetailsActivity;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.newsfragment.NewsDetailsActivity;
import com.likeit.as51scholarship.adapters.CourseListAdapter;
import com.likeit.as51scholarship.adapters.CricleListViewAdapter;
import com.likeit.as51scholarship.adapters.HomeItemNewsAdapter;
import com.likeit.as51scholarship.adapters.QuestionListAdapter;
import com.likeit.as51scholarship.adapters.SchoolListAdapter;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.CourseListBean;
import com.likeit.as51scholarship.model.HomeItemNewsBean;
import com.likeit.as51scholarship.model.SchoolListBean;
import com.likeit.as51scholarship.model.circle_model.GroupListModel;
import com.likeit.as51scholarship.model.question.QuestionListBean;
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
    private QuestionListAdapter answerAdapter;
    private InputMethodManager inputMethodManager;
    private String total;
    int page = 1;
    private ArrayList<QuestionListBean> questionData;
    private ArrayList<CourseListBean> courseData;
    private CourseListAdapter courseAdapter;
    private CricleListViewAdapter mGroupListAdapter;
    private ArrayList<GroupListModel> groupListData;
    private String gid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_info);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        inputMethodManager = (InputMethodManager) this.getApplicationContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");//1.院校搜索，2.资讯搜索 3.问答搜索 4.課程 5.圈子
        schoolData = new ArrayList<SchoolListBean>();
        newsData = new ArrayList<HomeItemNewsBean>();
        questionData = new ArrayList<QuestionListBean>();
        courseData = new ArrayList<CourseListBean>();
        groupListData = new ArrayList<GroupListModel>();
        initView();
    }

    private void initView() {
        schoolAdater = new SchoolListAdapter(mContext, schoolData);
        newAdapter = new HomeItemNewsAdapter(mContext, newsData);
        answerAdapter = new QuestionListAdapter(mContext, questionData);
        courseAdapter = new CourseListAdapter(mContext, courseData);
        mGroupListAdapter = new CricleListViewAdapter(mContext, groupListData);
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
                initSearch1(page);
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


        if ("1".equals(key)) {
            mListView.setAdapter(schoolAdater);
            schoolAdater.notifyDataSetChanged();
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = schoolData.get(position - 1).getName();
                    String en_name = schoolData.get(position - 1).getEn_name();
                    String img = schoolData.get(position - 1).getImg();
                    String sid = schoolData.get(position - 1).getId();
                    Intent intentSchoolDetail = new Intent();
                    intentSchoolDetail.putExtra("name", name);//英文名字
                    intentSchoolDetail.putExtra("en_name", en_name);//中文名字
                    intentSchoolDetail.putExtra("img", img);//图片
                    intentSchoolDetail.putExtra("sid", sid);//图片
                    intentSchoolDetail.setClass(mContext, SchoolDetailActivity01.class);
                    startActivity(intentSchoolDetail);
                }
            });
            schoolAdater.setOnApplyClickListener(new SchoolListAdapter.onSchoolApplyClickListener() {
                @Override
                public void onApplyClick(int i) {
                    Intent intent = new Intent(mContext, SchoolApplyActivity.class);
                    intent.putExtra("name", schoolData.get(i).getName());
                    intent.putExtra("address", schoolData.get(i).getCountry_name());
                    intent.putExtra("country_id", schoolData.get(i).getCountry_id());
                    intent.putExtra("sid", schoolData.get(i).getId());
                    startActivity(intent);
                }
            });
        } else if ("2".equals(key)) {
            mListView.setAdapter(newAdapter);
            newAdapter.notifyDataSetChanged();
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String id1 = newsData.get(position - 1).getId();
                    Intent intentNewDetails = new Intent(mContext, NewsDetailsActivity.class);
                    intentNewDetails.putExtra("id", id1);
                    startActivity(intentNewDetails);
                }
            });
        } else if ("3".equals(key)) {
            answerAdapter = new QuestionListAdapter(mContext, questionData);
            mListView.setAdapter(answerAdapter);
            answerAdapter.notifyDataSetChanged();
            answerAdapter.setOnAnswerClickListener(new QuestionListAdapter.onAnswerClickListener() {
                @Override
                public void onAnswerClick(int i) {
                    //ToastUtil.showS(mContext, "點擊了");
                    Intent intent = new Intent(mContext, AnswerListActivity.class);
                    intent.putExtra("id", questionData.get(i).getId());
                    intent.putExtra("author", questionData.get(i).getAuthor());
                    intent.putExtra("headimg", questionData.get(i).getHeadimg());
                    intent.putExtra("content", questionData.get(i).getContent());
                    intent.putExtra("category", questionData.get(i).getCategory());
                    intent.putExtra("country", questionData.get(i).getCountry());
                    intent.putExtra("interval", questionData.get(i).getInterval());
                    startActivity(intent);
                }
            });
            answerAdapter.setOnCommentClickListener(new QuestionListAdapter.onCommentClickListener() {
                @Override
                public void onCommentClick(int i) {
                    Intent intent = new Intent(mContext, AnswerDetailsActivity.class);
                    intent.putExtra("id", questionData.get(i).getId());
                    intent.putExtra("author", questionData.get(i).getAuthor());
                    intent.putExtra("headimg", questionData.get(i).getHeadimg());
                    intent.putExtra("content", questionData.get(i).getContent());
                    intent.putExtra("category", questionData.get(i).getCategory());
                    intent.putExtra("country", questionData.get(i).getCountry());
                    intent.putExtra("interval", questionData.get(i).getInterval());
                    startActivity(intent);
                }
            });
            answerAdapter.setOnLikeClickListener(new QuestionListAdapter.onLikeClickListener() {
                @Override
                public void onLikeClick(int i) {
                    String qid = questionData.get(i).getId();
                    initLike(qid);
                }
            });
        } else if ("4".equals(key)) {
            mListView.setAdapter(courseAdapter);
            courseAdapter.notifyDataSetChanged();
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String video_url=courseData.get(position-1).getVideo_url();
                    String content=courseData.get(position-1).getContent();
                    Intent intent=new Intent(mContext,CourseDetailsActivity.class);
                    intent.putExtra("video_url",video_url);
                    intent.putExtra("content",content);
                    startActivity(intent);
                }
            });
        }
        else if ("5".equals(key)) {
            mListView.setAdapter(mGroupListAdapter);
            mGroupListAdapter.notifyDataSetChanged();
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String circleId = groupListData.get(position-1).getId();
                    String circleTitle = groupListData.get(position-1).getTitle();
                    String circleDetail = groupListData.get(position-1).getDetail();
                    String circleLogo = groupListData.get(position-1).getLogo();
                    String circleMemberNum = groupListData.get(position-1).getMember_num();
                    String circlePostNum = groupListData.get(position-1).getPost_num();
                    String circleIsFollow = groupListData.get(position-1).getIsfollow();
                    Intent intentDetails = new Intent(mContext, CircleDetails01Activity.class);
                    intentDetails.putExtra("circleId", circleId);
                    intentDetails.putExtra("circleTitle", circleTitle);
                    intentDetails.putExtra("circleDetail", circleDetail);
                    intentDetails.putExtra("circleLogo", circleLogo);
                    intentDetails.putExtra("circleMemberNum", circleMemberNum);
                    intentDetails.putExtra("circlePostNum", circlePostNum);
                    intentDetails.putExtra("circleIsFollow", circleIsFollow);
                    startActivity(intentDetails);
                }
            });
            mGroupListAdapter.setOnEnFollowClickListener(new CricleListViewAdapter.onBtnEnFollowClickListener() {
                @Override
                public void onEnFollowClick(int i) {
                    gid = groupListData.get(i).getId();
                    // ToastUtil.showS(getActivity(), i + "");
                    refreshList();
                }
            });
            mGroupListAdapter.setOnFollowClickListener(new CricleListViewAdapter.onBtnFollowClickListener() {
                @Override
                public void onFollowClick(int i) {
                    gid = groupListData.get(i).getId();
                    //  ToastUtil.showS(getActivity(), i + "");
                    refreshList();
                }
            });
        }

    }
    private void refreshList() {
        String url = AppConfig.LIKEIT_GROUP_FOLLOW_GROUP;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("gid", gid);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String code = object.optString("code");
                    String message = object.optString("message");
                    if ("1".equals(code)) {
                        ToastUtil.showS(mContext, message);
                        refresh();
                    } else {
                        ToastUtil.showS(mContext, message);
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

    private void refresh() {
        //获取全部圈子列表
        mGroupListAdapter.addAll(groupListData, true);
        //groupListData.clear();
       initSearch(1);
        mGroupListAdapter.notifyDataSetChanged();
    }

    private void initLike(String qid) {
        String url = AppConfig.LIKEIT_ANSWER_ADDLIKE;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("qid", qid);
        showProgress("Loading...");
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        ToastUtil.showS(mContext, message);
                        refresh2();
                    } else {
//                        ToastUtil.showS(mContext, message);
//                        refresh2();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
            }
        });
    }

    private void refresh2() {
        answerAdapter.addAll(questionData, true);
        initSearch(1);
        showProgress("Loading...");
        answerAdapter.notifyDataSetChanged();
    }

    private void refresh1() {
        if ("1".equals(key)) {
            if (schoolData == null || schoolData.size() == 0) {
                return;
            } else {
                schoolData.clear();
                initSearch(1);
                showProgress("Loading...");
                newAdapter.notifyDataSetChanged();
            }
        } else if ("2".equals(key)) {
            if (newsData == null || newsData.size() == 0) {
                return;
            } else {
                newsData.clear();
                initSearch(1);
                showProgress("Loading...");
                newAdapter.notifyDataSetChanged();
            }
        } else if ("3".equals(key)) {
            if (questionData == null || questionData.size() == 0) {
                return;
            } else {
                questionData.clear();
                initSearch(1);
                showProgress("Loading...");
                answerAdapter.notifyDataSetChanged();
            }
        }else if ("4".equals(key)) {
            if (courseData == null || courseData.size() == 0) {
                return;
            } else {
                courseData.clear();
                initSearch(1);
                showProgress("Loading...");
                courseAdapter.notifyDataSetChanged();
            }
        }else if ("5".equals(key)) {
            if (groupListData == null || groupListData.size() == 0) {
                return;
            } else {
                groupListData.clear();
                initSearch(1);
                showProgress("Loading...");
                mGroupListAdapter.notifyDataSetChanged();
            }
        }

    }

    private void initSearch1(int page) {

        Log.d("TAG", "searchContent-->" + searchContent);
        if ("1".equals(key)) {
            url = AppConfig.LIKEIT_SEARCH_SCHOOL;
        } else if ("2".equals(key)) {
            url = AppConfig.LIKEIT_SEARCH_NEWS;
        } else if ("3".equals(key)) {
            url = AppConfig.LIKEIT_SEARCH_QUESTION;
        } else if ("4".equals(key)) {
            url = AppConfig.LIKEIT_SEARCH_COURSE;
        }
        else if ("5".equals(key)) {
            url = AppConfig.LIKEIT_SEARCH_GROUP;
        }
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("page", String.valueOf(page));
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
                mListView.onRefreshComplete();
            }
        });
    }

    private void initSearch(int page) {
        Log.d("TAG", "searchContent-->" + searchContent);
        if ("1".equals(key)) {
            url = AppConfig.LIKEIT_SEARCH_SCHOOL;
        } else if ("2".equals(key)) {
            url = AppConfig.LIKEIT_SEARCH_NEWS;
        } else if ("3".equals(key)) {
            url = AppConfig.LIKEIT_SEARCH_QUESTION;
        } else if ("4".equals(key)) {
            url = AppConfig.LIKEIT_SEARCH_COURSE;
        }
        else if ("5".equals(key)) {
            url = AppConfig.LIKEIT_SEARCH_GROUP;
        }
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("page", String.valueOf(page));
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
                mListView.onRefreshComplete();
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
                    total = obj.optString("total");
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
                    total = obj.optString("total");
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
                        homeItemNewsBean.setCover(jsonObject.optString("cover"));
                        newsData.add(homeItemNewsBean);
                    }
                    Log.d("TAG", "newsData-->" + newsData);
                    // newAdapter.addAll(NewsData, false);
                    newAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if ("3".equals(key)) {
            try {
                JSONObject object = new JSONObject(response);
                String code = object.optString("code");
                String message = object.optString("message");
                if ("1".equals(code)) {
                    total = object.optString("total");
                    JSONArray array = object.optJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.optJSONObject(i);
                        QuestionListBean mQuestionListBean = new QuestionListBean();
                        mQuestionListBean.setId(jsonObject.optString("id"));
                        mQuestionListBean.setAuthor(jsonObject.optString("author"));
                        mQuestionListBean.setHeadimg(jsonObject.optString("headimg"));
                        mQuestionListBean.setContent(jsonObject.optString("content"));
                        mQuestionListBean.setCategory(jsonObject.optString("category"));
                        mQuestionListBean.setCountry(jsonObject.optString("country"));
                        mQuestionListBean.setInterval(jsonObject.optString("interval"));
                        mQuestionListBean.setView(jsonObject.optString("view"));
                        mQuestionListBean.setAnswer_num(jsonObject.optString("answer_num"));
                        mQuestionListBean.setIslike(jsonObject.optString("islike"));
                        questionData.add(mQuestionListBean);
                    }
                    answerAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if ("4".equals(key)) {
            try {
                JSONObject obj = new JSONObject(response);
                String code = obj.optString("code");
                String message = obj.optString("message");
                if ("1".equals(code)) {
                    total = obj.optString("total");
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
                    courseAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if ("5".equals(key)) {
            try {
                JSONObject object = new JSONObject(response);
                String code = object.optString("code");
                String message = object.optString("message");
                if ("1".equals(code)) {
                    total = object.optString("total");
                    JSONArray data = object.optJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        GroupListModel mGroupListModel = new GroupListModel();
                        JSONObject obj = data.optJSONObject(i);
                        mGroupListModel.setId(obj.optString("id"));
                        mGroupListModel.setTitle(obj.optString("title"));
                        mGroupListModel.setDetail(obj.optString("detail"));
                        mGroupListModel.setLogo(obj.optString("logo"));
                        mGroupListModel.setMember_num(obj.optString("member_num"));
                        mGroupListModel.setPost_num(obj.optString("post_num"));
                        mGroupListModel.setIsfollow(obj.optString("isfollow"));
                        groupListData.add(mGroupListModel);
                    }
                    mGroupListAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showS(mContext, message);
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
                    if ("1".equals(key)) {
                        if (schoolData == null || schoolData.size() == 0) {
                            initSearch(1);
                            return;
                        } else {
                            schoolData.clear();
                            initSearch(1);
                            showProgress("Loading...");
                            newAdapter.notifyDataSetChanged();
                        }
                    } else if ("2".equals(key)) {
                        if (newsData == null || newsData.size() == 0) {
                            initSearch(1);
                            return;
                        } else {
                            newsData.clear();
                            initSearch(1);
                            showProgress("Loading...");
                            newAdapter.notifyDataSetChanged();
                        }
                    } else if ("3".equals(key)) {
                        if (questionData == null || questionData.size() == 0) {
                            initSearch(1);
                            return;
                        } else {
                            questionData.clear();
                            initSearch(1);
                            showProgress("Loading...");
                            answerAdapter.notifyDataSetChanged();
                        }
                    } else if ("4".equals(key)) {
                        if (courseData == null || courseData.size() == 0) {
                            initSearch(1);
                            return;
                        } else {
                            courseData.clear();
                            initSearch(1);
                            showProgress("Loading...");
                            courseAdapter.notifyDataSetChanged();
                        }
                    } else if ("5".equals(key)) {
                        if (groupListData == null || groupListData.size() == 0) {
                            initSearch(1);
                            return;
                        } else {
                            groupListData.clear();
                            initSearch(1);
                            showProgress("Loading...");
                            mGroupListAdapter.notifyDataSetChanged();
                        }
                    }
                    // initSearch(1);
                    showProgress("Loading...");
                }
                break;
        }
    }
}
