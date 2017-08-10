package com.likeit.a51scholarship.activitys.answersfragment;


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
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.newsfragment.NewsDetailsActivity;
import com.likeit.a51scholarship.fragments.BaseFragment;
import com.likeit.a51scholarship.utils.ListScrollUtil;
import com.likeit.a51scholarship.view.MyListview;
import com.likeit.a51scholarship.view.expandtabview.ExpandTabView;
import com.likeit.a51scholarship.view.expandtabview.ViewLeft;
import com.likeit.a51scholarship.view.expandtabview.ViewLeft01;
import com.likeit.a51scholarship.view.expandtabview.ViewLeft02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnswersFragment01 extends BaseFragment implements PullToRefreshBase.OnRefreshListener2<ScrollView> {


    private PullToRefreshScrollView mPullToRefreshScrollView;
    private MyListview mListview;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simpleAdapter;
    // 图片封装为一个数组
    private int[] icon = {R.mipmap.message_chat_avatar, R.mipmap.message_chat_avatar,
            R.mipmap.message_chat_avatar, R.mipmap.message_chat_avatar, R.mipmap.message_chat_avatar};
    private String[] iconName = {"Lana", "Tom", "Jim", "Lucy", "LiLy"};
    private String[] iconTime = {"50分钟前", "50分钟前", "50分钟前", "50分钟前", "50分钟前"};
    private String[] iconDetails = {"目前大四，留学加拿大需要准备什么呢？", "目前大四，留学加拿大需要准备什么呢？",
            "目前大四，留学加拿大需要准备什么呢？", "目前大四，留学加拿大需要准备什么呢？", "目前大四，留学加拿大需要准备什么呢？"};
    private String[] iconLikeNumber = {"9,999", "9,999", "9,999", "9,999", "9,999"};
    private String[] iconCommnentNumber = {"15", "15", "15", "15", "15"};
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton01,mRadioButton02,mRadioButton03;
    private ViewLeft viewLeft;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    ExpandTabView expandTabView;
    private ViewLeft01 viewLeft01;
    private ViewLeft02 viewLeft02;

    @Override
    protected int setContentView() {
        return R.layout.fragment_answers_fragment01;
    }

    @Override
    protected void lazyLoad() {
        initView();
       initVaule();
        initListener();
    }

    private void initListener() {
        viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {
            @Override
            public void getValue(String distance, String showText) {
                onRefresh(viewLeft,showText);
            }
        });
        viewLeft01.setOnSelectListener(new ViewLeft01.OnSelectListener() {
            @Override
            public void getValue(String distance, String showText) {
                onRefresh(viewLeft01,showText);
            }
        });
        viewLeft02.setOnSelectListener(new ViewLeft02.OnSelectListener() {
            @Override
            public void getValue(String distance, String showText) {
                onRefresh(viewLeft02,showText);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void initVaule() {
        mViewArray.add(viewLeft);
        mViewArray.add(viewLeft01);
        mViewArray.add(viewLeft02);
        // mViewArray.add(viewLeft);
        ArrayList<String> mTextArray = new ArrayList<String>();
        mTextArray.add("国家");
        mTextArray.add("热度");
        mTextArray.add("分类");
        expandTabView.setValue(mTextArray, mViewArray);
//        expandTabView.setTitle(viewLeft.getShowText(), 0);
//        expandTabView.setTitle(viewLeft01.getShowText(), 1);
//        expandTabView.setTitle(viewLeft02.getShowText(), 2);
    }

    private void initView() {
        viewLeft = new ViewLeft(getActivity());
        viewLeft01 = new ViewLeft01(getActivity());
        viewLeft02 = new ViewLeft02(getActivity());
        expandTabView=findViewById(R.id.expandtab_view);
//        mRadioGroup=findViewById(R.id.radio_group_answers_issue);
//        mRadioButton01=findViewById(R.id.country_answers_issue);
//        mRadioButton02=findViewById(R.id.heat_answers_issue);
//        mRadioButton03=findViewById(R.id.sort_answers_issue);
        mPullToRefreshScrollView = findViewById(R.id.answer_issue_scrollview);
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
        mListview = findViewById(R.id.answer_issue_listview);
        /**
         * 消息
         */
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        String[] from = {"img", "name", "time", "details", "like_number", "commnent_number"};
        int[] to = {R.id.answers_issue_avatar, R.id.answers_issue_name, R.id.answers_issue_time,
                R.id.answers_issue_details, R.id.answers_issue_like_number, R.id.answers_issue_commnent_number};
        simpleAdapter = new SimpleAdapter(getActivity(), dataList, R.layout.answer_issue_listview_item, from, to);
        //配置适配器
        mListview.setAdapter(simpleAdapter);
        ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentNewDetails = new Intent(getActivity(), NewsDetailsActivity.class);
                startActivity(intentNewDetails);
            }
        });
//        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                switch (checkedId){
//                    case R.id.country_answers_issue:
//                        break;
//                    case R.id.heat_answers_issue:
//                        break;
//                    case R.id.sort_answers_issue:
//                        break;
//                }
//            }
//        });
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < icon.length; i++) {
            Log.d("TAG", "" + icon.length);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icon[i]);
            map.put("name", iconName[i]);
            map.put("time", iconTime[i]);
            map.put("details", iconDetails[i]);
            map.put("like_number", iconLikeNumber[i]);
            map.put("commnent_number", iconCommnentNumber[i]);
            dataList.add(map);
        }
        return dataList;
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
        Toast.makeText(getActivity(), showText, Toast.LENGTH_SHORT).show();

    }

    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
    }

//    @Override
//    public void onBackPressed() {
//
//        if (!expandTabView.onPressBack()) {
//            finish();
//        }
//    }

}
