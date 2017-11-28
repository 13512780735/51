package com.likeit.as51scholarship.activitys.answersfragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.newsfragment.NewsDetailsActivity;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.fragments.BaseFragment;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.utils.ListScrollUtil;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.view.MyListview;
import com.likeit.as51scholarship.view.expandtabview.ExpandTabView;
import com.likeit.as51scholarship.view.expandtabview.ViewLeft;
import com.likeit.as51scholarship.view.expandtabview.ViewLeft01;
import com.likeit.as51scholarship.view.expandtabview.ViewLeft02;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnswersFragment01 extends BaseFragment implements PullToRefreshBase.OnRefreshListener2<ScrollView> {


    private PullToRefreshScrollView mPullToRefreshScrollView;
    private MyListview mListview;
   // private RadioGroup mRadioGroup;
    //private RadioButton mRadioButton01,mRadioButton02,mRadioButton03;
    private ProgressDialog dialog;
    @Override
    protected int setContentView() {
        return R.layout.fragment_answers_fragment01;
    }

    @Override
    protected void lazyLoad() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        initData();
        dialog.show();
        initView();
    }

    private void initData() {
        String url= AppConfig.LIKEIT_QUESTION_GETLIST;
        RequestParams param=new RequestParams();
        param.put("ukey",ukey);
        HttpUtil.post(url, param, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                dialog.dismiss();
                Log.d("TAG",response);
                try {
                    JSONObject object=new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                dialog.dismiss();
                ToastUtil.showS(getActivity(),"网络异常请重新再试！");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void initView() {
        mPullToRefreshScrollView = findViewById(R.id.answer_issue_scrollview);
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshScrollView.setOnRefreshListener(this);
        mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间");
        mPullToRefreshScrollView.getLoadingLayoutProxy()
                .setPullLabel("下拉刷新");
        mPullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新");
        mListview = findViewById(R.id.answer_issue_listview);
//        /**
//         * 消息
//         */
//        dataList = new ArrayList<Map<String, Object>>();
//        getData();
//        String[] from = {"img", "name", "time", "details", "like_number", "commnent_number"};
//        int[] to = {R.id.answers_issue_avatar, R.id.answers_issue_name, R.id.answers_issue_time,
//                R.id.answers_issue_details, R.id.answers_issue_like_number, R.id.answers_issue_commnent_number};
//        simpleAdapter = new SimpleAdapter(getActivity(), dataList, R.layout.answer_issue_listview_item, from, to);
//        //配置适配器
//        mListview.setAdapter(simpleAdapter);
        ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentNewDetails = new Intent(getActivity(), NewsDetailsActivity.class);
                startActivity(intentNewDetails);
            }
        });
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
