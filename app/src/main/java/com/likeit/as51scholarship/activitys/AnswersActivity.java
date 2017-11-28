package com.likeit.as51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.as51scholarship.AnswerDetailsActivity;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.newsfragment.NewsDetailsActivity;
import com.likeit.as51scholarship.adapters.QuestionCategoryAdapter;
import com.likeit.as51scholarship.adapters.QuestionListAdapter;
import com.likeit.as51scholarship.adapters.SchoolApplyAddressAdapter;
import com.likeit.as51scholarship.adapters.SchoolApplyPlanTimeAdapter;
import com.likeit.as51scholarship.adapters.SchoolApplyStageAdapter;
import com.likeit.as51scholarship.adapters.userapply.UserApplyDistrictAdapter;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.HomeItemNewsBean;
import com.likeit.as51scholarship.model.SchoolApplyBean;
import com.likeit.as51scholarship.model.question.QuestionCategoryBean;
import com.likeit.as51scholarship.model.question.QuestionListBean;
import com.likeit.as51scholarship.model.userapply.UserDistrictBean;
import com.likeit.as51scholarship.utils.ListScrollUtil;
import com.likeit.as51scholarship.utils.MyActivityManager;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.view.MyListview;
import com.likeit.as51scholarship.view.expandtabview.ExpandTabView;
import com.likeit.as51scholarship.view.expandtabview.ViewLeft;
import com.likeit.as51scholarship.view.expandtabview.ViewLeft01;
import com.likeit.as51scholarship.view.expandtabview.ViewLeft02;
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


public class AnswersActivity extends Container {
    @BindView(R.id.iv_header_left)
    ImageView ivLeft;
    @BindView(R.id.iv_header_right)
    ImageView ivRight;
    @BindView(R.id.iv_header_right01)
    ImageView ivRight01;
    @BindView(R.id.answer_issue_listview)
    PullToRefreshListView mListview;
    @BindView(R.id.question_country)
    CheckBox questionCountry;
    @BindView(R.id.question_hot)
    CheckBox questionHot;
    @BindView(R.id.question_type)
    CheckBox questionType;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    private AnswersActivity mContext;
    private Window window;
    private List<QuestionListBean> questionData;
    private QuestionListAdapter mAdapter;
    int page = 1;
    private String total;
    private List<UserDistrictBean> countryData;
    private List<QuestionCategoryBean> categoryData;
    private String tag;
    private View layoutMenu;
    private ListView popMenuList;
    private PopupWindow popMenu;
    private UserApplyDistrictAdapter adapter1;
    private QuestionCategoryAdapter adapter2;
    private String countryid;
    private String categoryid;
    private String vieworder = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        MyActivityManager.getInstance().addActivity(this);
        // setContentView(main);
        mContext = this;
        ButterKnife.bind(this);
        questionData = new ArrayList<QuestionListBean>();
        categoryData = new ArrayList<QuestionCategoryBean>();
        countryData = new ArrayList<UserDistrictBean>();
        initData(1);
        initcountry(); //国家
        initcategory(); //分类
        showProgress("Loading...");
        initView();
    }

    private void initcategory() {
        String url = AppConfig.LIKEIT_QUESTION_GETCATEGORY;
        RequestParams param = new RequestParams();
        param.put("ukey", ukey);
        HttpUtil.post(url, param, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                try {
                    Log.d("TAG", response);
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    if ("1".equals(code)) {
                        JSONArray array = obj.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            QuestionCategoryBean mQuestionCategoryBean = new QuestionCategoryBean();
                            mQuestionCategoryBean.setId(object.optString("id"));
                            mQuestionCategoryBean.setTitle(object.optString("title"));
                            categoryData.add(mQuestionCategoryBean);
                        }
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

    private void initcountry() {
        String url = AppConfig.LIKEIT_MEMBER_EDIT_DISTRICT;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    if ("1".equals(code)) {
                        JSONArray array = obj.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            UserDistrictBean mUserDistrictBean = new UserDistrictBean();
                            mUserDistrictBean.setId(object.optString("id"));
                            mUserDistrictBean.setName(object.optString("name"));
                            countryData.add(mUserDistrictBean);
                        }
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


    private void initData(int page) {
        String url = AppConfig.LIKEIT_QUESTION_GETLIST;
        RequestParams param = new RequestParams();
        param.put("ukey", ukey);
        param.put("page", String.valueOf(page));
        param.put("countryid", countryid);
        param.put("categoryid", categoryid);
        param.put("vieworder", vieworder);
        HttpUtil.post(url, param, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                Log.d("TAG", response);
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
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
                ToastUtil.showS(mContext, "网络异常请重新再试！");
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
        mAdapter = new QuestionListAdapter(mContext, questionData);
        mListview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnAnswerClickListener(new QuestionListAdapter.onAnswerClickListener() {
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
        mAdapter.setOnCommentClickListener(new QuestionListAdapter.onCommentClickListener() {
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

        mAdapter.setOnLikeClickListener(new QuestionListAdapter.onLikeClickListener() {
            @Override
            public void onLikeClick(int i) {
                String qid = questionData.get(i).getId();
                initLike(qid);
            }
        });
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

    private void initLike(String qid) {
        String url = AppConfig.LIKEIT_ANSWER_ADDLIKE;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("qid", qid);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        ToastUtil.showS(mContext, message);
                        refresh();
                    } else {
//                        ToastUtil.showS(mContext,message);
//                        refresh();
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
        mAdapter.addAll(questionData, true);
        initData(1);
        showProgress("Loading...");
        mAdapter.notifyDataSetChanged();
    }

    private void initData1(int page) {
        String url = AppConfig.LIKEIT_QUESTION_GETLIST;
        RequestParams param = new RequestParams();
        param.put("ukey", ukey);
        param.put("page", String.valueOf(page));
        param.put("countryid", countryid);
        param.put("categoryid", categoryid);
        param.put("vieworder", vieworder);
        HttpUtil.post(url, param, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                Log.d("TAG", response);
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
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
                ToastUtil.showS(mContext, "网络异常请重新再试！");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                disShowProgress();
                mListview.onRefreshComplete();
            }
        });
    }

    @OnClick({R.id.iv_header_left, R.id.iv_header_right, R.id.iv_header_right01, R.id.question_country, R.id.question_hot, R.id.question_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                onBackPressed();
                break;
            case R.id.iv_header_right:
                Intent intentAsk = new Intent(this, AskActivity.class);
                startActivity(intentAsk);
                break;
            case R.id.iv_header_right01:
                Intent intentSearchInfo = new Intent(this, SearchInfoActivity.class);
                intentSearchInfo.putExtra("key", "3");
                startActivity(intentSearchInfo);
                break;
            case R.id.question_country://国家
                questionHot.setChecked(false);
                questionType.setChecked(false);
                tag = "1";
                selectMenu(tag);
                break;
            case R.id.question_hot://热度
                questionCountry.setChecked(false);
                questionType.setChecked(false);
                if ("1".equals(vieworder)) {
                    vieworder = "2";
                    countryid = "";
                    categoryid = "";
                    refresh();
                } else {
                    vieworder = "1";
                    countryid = "";
                    categoryid = "";

                    refresh();
                }
                break;
            case R.id.question_type://分类
                questionHot.setChecked(false);
                questionCountry.setChecked(false);
                tag = "2";
                selectMenu(tag);
                break;
        }
    }


    private void selectMenu(final String tag) {
        if (popMenu != null && popMenu.isShowing()) {
            popMenu.dismiss();
        } else {

            layoutMenu = this.getLayoutInflater().inflate(
                    R.layout.operationinto_popmenulist, null);
            popMenuList = (ListView) layoutMenu
                    .findViewById(R.id.menulist);

            // 创建ArrayAdapter
            if ("1".equals(tag)) {
                adapter1 = new UserApplyDistrictAdapter(mContext, countryData);
                popMenuList.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
            } else if ("2".equals(tag)) {
                adapter2 = new QuestionCategoryAdapter(
                        mContext,
                        categoryData);
                popMenuList.setAdapter(adapter2);
                adapter2.notifyDataSetChanged();

            }

            // 绑定适配器
            backgroundAlpha(0.5f);

            // 点击listview中item的处理
            popMenuList
                    .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {
                            // 隐藏弹出窗口
                            if (popMenu != null && popMenu.isShowing()) {
                                popMenu.dismiss();
                                backgroundAlpha(1f);
                            }
                            if ("1".equals(tag)) {
                                countryid = countryData.get(position).getId();
                                categoryid = "";
                                vieworder = "1";
                                refresh();
                            } else if ("2".equals(tag)) {
                                countryid = "";
                                vieworder = "1";
                                categoryid = categoryData.get(position).getId();
                                refresh();
                            }
                        }
                    });

            // 创建弹出窗口
            // 窗口内容为layoutLeft，里面包含一个ListView
            // 窗口宽度跟tvLeft一样
            //关闭事件
            popMenu = new PopupWindow(layoutMenu, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            popMenu.showAsDropDown(radioGroup);
            // popMenu.showAtLocation(getLayoutInflater().inflate(R.layout.activity_school_apply, null), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            popMenu.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.filter_bg));
            popMenu.setAnimationStyle(R.style.PopupAnimation);
            popMenu.update();
            popMenu.setOnDismissListener(new popupDismissListener());
            popMenu.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            popMenu.setTouchable(true); // 设置popupwindow可点击
            popMenu.setOutsideTouchable(true); // 设置popupwindow外部可点击
            popMenu.setFocusable(true); // 获取焦点


            popMenu.setTouchInterceptor(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // 如果点击了popupwindow的外部，popupwindow也会消失
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        popMenu.dismiss();
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);

    }

}
