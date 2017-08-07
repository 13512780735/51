package com.likeit.a51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.utils.ListScrollUtil;
import com.likeit.a51scholarship.view.MyListview;
import com.likeit.a51scholarship.view.expandtabview.ExpandTabView;
import com.likeit.a51scholarship.view.expandtabview.ViewMiddle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> dataList;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    private ViewMiddle viewMiddle;
    // 图片封装为一个数组
    private int[] icon = {R.mipmap.course_test_bg, R.mipmap.course_test_bg,
            R.mipmap.course_test_bg, R.mipmap.course_test_bg, R.mipmap.course_test_bg};
    private String[] iconName = {"托福名师讲解，高效提分", "托福名师讲解，高效提分",
            "托福名师讲解，高效提分", "托福名师讲解，高效提分", "托福名师讲解，高效提分"};
    private String[] iconTime = {"5课时，177分钟", "5课时，177分钟", "5课时，177分钟", "5课时，177分钟", "5课时，177分钟"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        ButterKnife.bind(this);
        initView();
        initVaule();
        initListener();
    }

    private void initListener() {
        viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {

            @Override
            public void getValue(String showText) {

                onRefresh(viewMiddle,showText);

            }
        });

    }

    private void initVaule() {
        mViewArray.add(viewMiddle);
        ArrayList<String> mTextArray = new ArrayList<String>();
        mTextArray.add("全部类别");
        expandTabView.setValue(mTextArray, mViewArray);
    }
    String videoUrl = "http://bvideo.spriteapp.cn/video/2016/0704/577a4c29e1f14_wpd.mp4";
    private void initView() {
        viewMiddle = new ViewMiddle(this);
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
        /**
         * 消息
         */
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        String[] from = {"img", "name", "readTime"};
        int[] to = {R.id.course_listview_avatar, R.id.course_listview_name, R.id.course_listview_time};
        simpleAdapter = new SimpleAdapter(mContext, dataList, R.layout.course_listview_items, from, to);
        //配置适配器
        mListview.setAdapter(simpleAdapter);
        ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentNewDetails = new Intent(mContext, CourseDetailsActivity.class);
                startActivity(intentNewDetails);
               // openVideo(videoUrl);
            }
        });
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < icon.length; i++) {
            Log.d("TAG", "" + icon.length);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icon[i]);
            map.put("name", iconName[i]);
            map.put("readTime", iconTime[i]);
            dataList.add(map);
        }
        return dataList;
    }

    @OnClick({R.id.top_bar_back_img, R.id.top_bar_right_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back_img:
                onBackPressed();
                break;
            case R.id.top_bar_right_img:
                toActivity(SearchInfoActivity.class);
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
