package com.likeit.as51scholarship.fragments;


import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.MainActivity;
import com.likeit.as51scholarship.activitys.SearchInfoActivity;
import com.likeit.as51scholarship.chat.message.adapter.Message_Chat_ViewPagerAdatper;
import com.likeit.as51scholarship.chat.message.adapter.NewFriendsMsgAdapter;
import com.likeit.as51scholarship.chat.message.db.DemoDBManager;
import com.likeit.as51scholarship.chat.message.db.InviteMessgeDao;
import com.likeit.as51scholarship.chat.message.model.InviteMessage;
import com.likeit.as51scholarship.dialog.KefuDialog;
import com.likeit.as51scholarship.view.BadgeView;
import com.likeit.as51scholarship.view.MyListview;
import com.likeit.as51scholarship.view.NoScrollViewPager;

import java.util.Collections;
import java.util.List;

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
    private BadgeView badgeView2;
    private BadgeView badgeView;


    @Override
    protected int setContentView() {
        return R.layout.fragment_home_fragment04;
    }

    @Override
    protected void lazyLoad() {
        initView();
        initListener();

    }

    private void initView() {

        rg_message = findViewById(R.id.message_rgTools);
        viewpage_message = findViewById(R.id.message_viewpager);
        iv_header_left = findViewById(R.id.iv_header_left);
        iv_header_right = findViewById(R.id.iv_header_right);
        tv_header_right = findViewById(R.id.tv_header_right);
        kefuService = findViewById(R.id.kefu_service);
        iv_header_left.setImageResource(R.mipmap.nav_icon_user);
        iv_header_right.setImageResource(R.mipmap.nav_icon_search_sel);
        iv_header_right.setVisibility(View.INVISIBLE);
        adapter = new Message_Chat_ViewPagerAdatper(getChildFragmentManager());
        viewpage_message.setAdapter(adapter);
        viewpage_message.setOnPageChangeListener(this);
        rg_message.setOnCheckedChangeListener(this);
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
        switch (checkedId) {
            case R.id.bt_head_chat:
                viewpage_message.setCurrentItem(0);
                break;
            case R.id.bt_head_notice:
                viewpage_message.setCurrentItem(1);
                findViewById(R.id.tv_header_right).setVisibility(View.VISIBLE);
                //InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
                findViewById(R.id.tv_header_right).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //ToastUtil.showL(getActivity(),"111");
                        // EventBus.getDefault().post(new MessageEvent("1"));
                        ListView listView = (ListView) findViewById(R.id.message_list);
                        InviteMessgeDao dao = new InviteMessgeDao(getActivity());
                        List<InviteMessage> msgs = dao.getMessagesList();
                        Collections.reverse(msgs);
                        msgs.clear();
                        DemoDBManager.getInstance().closeDB();
                        dao.getMessagesList().clear();
                        DemoDBManager.getInstance().deleteMessage(msgs.toString());
                        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(getActivity(), 1, msgs);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        //dao.saveUnreadMessageCount(0);
                    }
                });
                break;
        }
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