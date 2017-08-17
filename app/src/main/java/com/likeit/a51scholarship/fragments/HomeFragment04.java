package com.likeit.a51scholarship.fragments;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.MainActivity;
import com.likeit.a51scholarship.activitys.SearchInfoActivity;
import com.likeit.a51scholarship.adapters.AnswersViewPagerAdapter;
import com.likeit.a51scholarship.chat.message.adapter.Message_Chat_ViewPagerAdatper;
import com.likeit.a51scholarship.dialog.KefuDialog;
import com.likeit.a51scholarship.utils.ListScrollUtil;
import com.likeit.a51scholarship.view.MyListview;
import com.likeit.a51scholarship.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment04 extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    private ImageView iv_header_left;
    private ImageView kefuService;
    private PullToRefreshScrollView mPullToRefreshScrollView;
    private ImageView iv_header_right;
    private Button bt_header_chat;
    private TextView tv_header_chat;
    private Button bt_header_notice;
    private TextView tv_header_notice;
    private MyListview mListview;
    private TextView tv_header_right;
    private RadioGroup rg_message;
    private NoScrollViewPager viewpage_message;
    private Message_Chat_ViewPagerAdatper adapter;
    //  private List<Map<String, Object>> dataList;
//    // 图片封装为一个数组
//    private int[] icon = {R.mipmap.message_chat_avatar, R.mipmap.message_chat_avatar,
//            R.mipmap.message_chat_avatar};
//    private String[] iconName = {"Lana", "Lili", "Sina"};
//    private String[] iconMessage = {"我通过了你的好友验证请求，现在我们可以开始聊天了...", "我通过了你的好友验证请求，现在我们可以开始聊天了...",
//            "我通过了你的好友验证请求，现在我们可以开始聊天了..."};
//    private String[] iconTime = {"2017-05-11", "2017-05-18", "2017-05-22"};
//    private SimpleAdapter simpleAdapter;


    @Override
    protected int setContentView() {
        return R.layout.fragment_home_fragment04;
    }

    @Override
    protected void lazyLoad() {
        initView();
        initListener();

    }


//    private List<Map<String, Object>> getData() {
//        for (int i = 0; i < icon.length; i++) {
//            Log.d("TAG", "" + icon.length);
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("img", icon[i]);
//            map.put("name", iconName[i]);
//            map.put("message", iconMessage[i]);
//            map.put("time", iconTime[i]);
//            dataList.add(map);
//        }
//        return dataList;
//    }

    private void initView() {
//        mPullToRefreshScrollView = findViewById(R.id.ll_message_scrollview);
//        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
//        mPullToRefreshScrollView.setOnRefreshListener(this);
//        mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(
//                "上次刷新时间");
//        mPullToRefreshScrollView.getLoadingLayoutProxy()
//                .setPullLabel("下拉刷新");
////          mPullRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel(
////                      "refreshingLabel");
//        mPullToRefreshScrollView.getLoadingLayoutProxy().setReleaseLabel(
//                "松开即可刷新");
//        mListview = findViewById(R.id.ll_message_listview);
        rg_message=findViewById(R.id.message_rgTools);
        viewpage_message=findViewById(R.id.message_viewpager);
        iv_header_left = findViewById(R.id.iv_header_left);
        iv_header_right = findViewById(R.id.iv_header_right);
        tv_header_right = findViewById(R.id.tv_header_right);
        kefuService = findViewById(R.id.kefu_service);
        iv_header_left.setImageResource(R.mipmap.nav_icon_user);
        iv_header_right.setImageResource(R.mipmap.nav_icon_search_sel);
        adapter = new Message_Chat_ViewPagerAdatper(getChildFragmentManager());
        viewpage_message.setAdapter(adapter);
        viewpage_message.setOnPageChangeListener(this);
        rg_message.setOnCheckedChangeListener(this);
//        /**
//         * 消息
//         */
//        dataList = new ArrayList<Map<String, Object>>();
//        getData();
//        String[] from = {"img", "name", "message", "time"};
//        int[] to = {R.id.iv_message_chat_avatar, R.id.iv_message_chat_name, R.id.iv_message_chat_message, R.id.tv_message_time};
//        simpleAdapter = new SimpleAdapter(getActivity(), dataList, R.layout.message_listview_chat_items, from, to);
//        //配置适配器
//        mListview.setAdapter(simpleAdapter);
//        ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
    }

    private void initListener() {
        iv_header_left.setOnClickListener(this);
        iv_header_right.setOnClickListener(this);
        kefuService.setOnClickListener(this);
    }

    @Override
    public boolean haveEventBus() {
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_header_left:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.refresh();
                break;
            case R.id.iv_header_right:
                toActivity(SearchInfoActivity.class);
                break;
            case R.id.kefu_service:
                KefuDialog kefuDialog = new KefuDialog(getContext());
                kefuDialog.show();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        rg_message.check(rg_message.getChildAt(position).getId());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        viewpage_message.setCurrentItem(group.indexOfChild(group.findViewById(checkedId)), false);
    }

//    @Override
//    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//        ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
//        mPullToRefreshScrollView.onRefreshComplete();
//    }
//
//    @Override
//    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//        ListScrollUtil.setListViewHeightBasedOnChildren(mListview);
//        mPullToRefreshScrollView.onRefreshComplete();
//    }
}