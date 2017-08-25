package com.likeit.a51scholarship.fragments;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.MainActivity;
import com.likeit.a51scholarship.activitys.SearchInfoActivity;
import com.likeit.a51scholarship.chat.message.ui.ChatActivity;
import com.likeit.a51scholarship.chat.message.ui.FriendActivity;
import com.likeit.a51scholarship.chat.message.ui.GroupsActivity;
import com.likeit.a51scholarship.chat.message.ui.NewFriendsMsgActivity;
import com.likeit.a51scholarship.chat.message.widget.Constant;
import com.likeit.a51scholarship.dialog.KefuDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment03 extends BaseFragment implements View.OnClickListener,
        PullToRefreshBase.OnRefreshListener2<ScrollView> {
    private ImageView iv_header_left;
    private TextView tv_header;
    private RelativeLayout ll_find_grounp;
    private RelativeLayout ll_find_new_friend;
    private RelativeLayout ll_find_friend;
    private RelativeLayout ll_find_kefu;
    private RelativeLayout ll_find_serve;
    private PullToRefreshScrollView mPullToRefreshScrollView;

    @Override
    protected int setContentView() {
        return R.layout.fragment_home_fragment03;
    }

    @Override
    protected void lazyLoad() {
        initView();
        initListener();

    }

    private void initView() {
        mPullToRefreshScrollView = findViewById(R.id.ll_find_scrollview);
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
        iv_header_left = findViewById(R.id.iv_header_left);
        tv_header = findViewById(R.id.tv_header);
        tv_header.setText("发现");
        iv_header_left.setImageResource(R.mipmap.nav_icon_user);
        ll_find_grounp = findViewById(R.id.ll_find_grounp);
        ll_find_new_friend = findViewById(R.id.ll_find_new_friend);
        ll_find_friend = findViewById(R.id.ll_find_friend);
        ll_find_kefu = findViewById(R.id.ll_find_kefu);
        ll_find_serve = findViewById(R.id.ll_find_serve);

    }

    private void initListener() {
        iv_header_left.setOnClickListener(this);
        ll_find_grounp.setOnClickListener(this);
        ll_find_new_friend.setOnClickListener(this);
        ll_find_friend.setOnClickListener(this);
        ll_find_kefu.setOnClickListener(this);
        ll_find_serve.setOnClickListener(this);
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
            case R.id.ll_find_grounp:
                toActivity(GroupsActivity.class);
                break;
            case R.id.ll_find_new_friend:
                toActivity(NewFriendsMsgActivity.class);
                break;
            case R.id.ll_find_friend:
                toActivity(FriendActivity.class);
//                Intent intent = new Intent(getActivity(), FriendActivity.class);
//                // it is group chat
//                intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
//                intent.putExtra("userId", groupAdapter.getItem(position - 3).getGroupId());
 //               startActivityForResult(intent, 0);
                break;
            case R.id.ll_find_kefu:
                KefuDialog kefuDialog = new KefuDialog(getContext());
                kefuDialog.show();
                break;
            case R.id.ll_find_serve:
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        mPullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        mPullToRefreshScrollView.onRefreshComplete();
    }
}
