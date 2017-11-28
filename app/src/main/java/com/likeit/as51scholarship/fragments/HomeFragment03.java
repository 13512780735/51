package com.likeit.as51scholarship.fragments;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.easemob.redpacket.utils.RedPacketUtil;
import com.easemob.redpacketsdk.constant.RPConstant;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.MainActivity;
import com.likeit.as51scholarship.chat.message.DemoModel;
import com.likeit.as51scholarship.chat.message.db.InviteMessgeDao;
import com.likeit.as51scholarship.chat.message.db.UserDao;
import com.likeit.as51scholarship.chat.message.ui.ChatActivity;
import com.likeit.as51scholarship.chat.message.ui.FriendActivity;
import com.likeit.as51scholarship.chat.message.ui.FriendActivity01;
import com.likeit.as51scholarship.chat.message.ui.GroupsActivity;
import com.likeit.as51scholarship.chat.message.ui.NewFriendsMsgActivity;
import com.likeit.as51scholarship.chat.message.widget.DemoHelper;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.utils.StringUtil;
import com.likeit.as51scholarship.utils.UtilPreference;
import com.likeit.as51scholarship.view.BadgeView;
import com.likeit.as51scholarship.view.MessageEvent;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
    private BadgeView badgeView;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver broadcastReceiver;
    public boolean isConflict = false;
    private boolean isCurrentAccountRemoved = false;
    private int count;

    private ProgressDialog dialog;

    @Override
    protected int setContentView() {
        return R.layout.fragment_home_fragment03;
    }

    @Override
    protected void lazyLoad() {

        initView();
        initListener();
        inviteMessgeDao = new InviteMessgeDao(getActivity());
        UserDao userDao = new UserDao(getActivity());
        //registerBroadcastReceiver();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadAddressLable();
        }

        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(getActivity());

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    public void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.popActivity(getActivity());

        super.onStop();
    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                DemoHelper.getInstance().getNotifier().onNewMsg(message);
            }
            //refreshUIWithMessage();
//            String userName=messages.get(0).getFrom();
//            Log.d("TAG585899",userName);
//            initUser(userName);
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //red packet code : 处理红包回执透传消息
            for (EMMessage message : messages) {
                EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                final String action = cmdMsgBody.action();//获取自定义action
                if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
                    RedPacketUtil.receiveRedPacketAckMessage(message);
                }
            }
            //end of red packet code
            //  refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            // refreshUIWithMessage();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };


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
        ll_find_serve.setVisibility(View.GONE);
        badgeView = new BadgeView(getActivity());
        badgeView.setTargetView(findViewById(R.id.bt_friend_code));
        badgeView.setBadgeGravity(Gravity.RIGHT | Gravity.TOP);
        badgeView.setBadgeMargin(0, 0, 10, 0);
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
                if (count > 0) {
                    badgeView.setVisibility(View.INVISIBLE);
                }
                toActivity(NewFriendsMsgActivity.class);

                break;
            case R.id.ll_find_friend:
                toActivity(FriendActivity01.class);
//                Intent intent = new Intent(getActivity(), FriendActivity.class);
//                // it is group chat
//                intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
//                intent.putExtra("userId", groupAdapter.getItem(position - 3).getGroupId());
                //               startActivityForResult(intent, 0);
                break;
            case R.id.ll_find_kefu:
//                KefuDialog kefuDialog = new KefuDialog(getContext());
//                kefuDialog.show();
                //  toActivity(ChatActivity.class);
                String nickname = UtilPreference.getStringValue(getActivity(), "nickname");
                // demo中直接进入聊天页面，实际一般是进入用户详情页
                startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("userId", "13556789999"));
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

    public void updateUnreadAddressLable() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (inviteMessgeDao == null) {
                    inviteMessgeDao = new InviteMessgeDao(getActivity());
                }
                if (inviteMessgeDao.getUnreadMessagesCount() > 0) {
                    count = getUnreadAddressCountTotal();
                    Log.d("TAG",count+"");
                    if (Integer.valueOf(count) == null) {
                        return;
                    }else{
                    if (count > 0) {
                     // badgeView.setBadgeCount(count);
                    } else {
                        badgeView.setVisibility(View.INVISIBLE);
                    }}
                }
            }
        });

    }


    private InviteMessgeDao inviteMessgeDao;

    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal;
        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
        return unreadAddressCountTotal;
    }

}
