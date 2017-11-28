package com.likeit.as51scholarship.activitys;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.easemob.redpacket.utils.RedPacketUtil;
import com.easemob.redpacketsdk.constant.RPConstant;
import com.hyphenate.EMClientListener;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.NetUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.login.GuideActivity;
import com.likeit.as51scholarship.adapters.HomeViewPagerAdapter;
import com.likeit.as51scholarship.chat.message.DemoModel;
import com.likeit.as51scholarship.chat.message.db.InviteMessgeDao;
import com.likeit.as51scholarship.chat.message.db.UserDao;
import com.likeit.as51scholarship.chat.message.utils.SharePrefConstant;
import com.likeit.as51scholarship.chat.message.utils.UserInfoCacheSvc;
import com.likeit.as51scholarship.chat.message.widget.Constant;
import com.likeit.as51scholarship.chat.message.widget.DemoHelper;
import com.likeit.as51scholarship.fragments.HomeFragment01;
import com.likeit.as51scholarship.fragments.MainFragment;
import com.likeit.as51scholarship.fragments.ShowFragment;
import com.likeit.as51scholarship.utils.MyActivityManager;
import com.likeit.as51scholarship.view.BadgeView;
import com.likeit.as51scholarship.view.MessageEvent;
import com.likeit.as51scholarship.view.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends SlidingFragmentActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.rbHome)
    RadioButton mRbHome;
    @BindView(R.id.rbCircle)
    RadioButton mRbCircle;
    @BindView(R.id.rbFind)
    RadioButton mRbFind;
    @BindView(R.id.rbMessage)
    RadioButton mRbMessage;
    //小工具
  /*  @BindView(R.id.rbTool)
    RadioButton mRbTool;*/
    @BindView(R.id.rgTools)
    RadioGroup mRgTools;
    @BindView(R.id.home_viewpager)
    NoScrollViewPager mViewPager;
    private View main;
    private HomeViewPagerAdapter adapter;
    private SlidingMenu menu;
    private LinearLayout schoolLayout;
    private BadgeView badgeView2;//申请通知消息数量
    private BadgeView badgeView; //聊天和通知消息数量
    public boolean isConflict = false;
    private boolean isCurrentAccountRemoved = false;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver broadcastReceiver;
    private Map<String, EaseUser> contactList;
    private DemoModel demoModel = null;
    private String ukey;
    private String userName;
    private String userPic;
    private String userId;
    private String hxIdFrom;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyActivityManager.getInstance().addActivity(this);
        EventBus.getDefault().register(this);
        demoModel = new DemoModel(this);
        ButterKnife.bind(this);

        mViewPager.setOffscreenPageLimit(2);
        //initUser();
        //dialog.show();
        initView();
        initMenu();
        //showIndentDialog1();
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        //注册一个监听连接状态的listener
        inviteMessgeDao = new InviteMessgeDao(this);
        UserDao userDao = new UserDao(this);
        registerBroadcastReceiver();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        Log.d("TAGsadasd",messageEvent.getMessage());
    }



    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        intentFilter.addAction(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                updateUnreadLabel();
                updateUnreadAddressLable();
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
        EventBus.getDefault().unregister(this);

    }

    EMClientListener clientListener = new EMClientListener() {
        @Override
        public void onMigrate2x(boolean success) {
            Toast.makeText(MainActivity.this, "onUpgradeFrom 2.x to 3.x " + (success ? "success" : "fail"), Toast.LENGTH_LONG).show();
            if (success) {
                refreshUIWithMessage();
            }
        }
    };

    @Override
    protected void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        EMClient.getInstance().removeClientListener(clientListener);
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.popActivity(this);

        super.onStop();
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        //Log.d("TAG","您的账号已在其他设备登录！");
                        //  ToastUtil.showS(mContext,"您的账号已在其他设备登录！");
                        Intent intent = new Intent(MainActivity.this, GuideActivity.class);
                        intent.putExtra("online", "1");
                        startActivity(intent);
                        finish();
                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)) ;
                            //连接不到聊天服务器
                        else {
                            //当前网络不可用，请检查网络设置
                            //
                        }
                    }
                }
            });
        }


    }

    private void showIndentDialog1() {
        ShowFragment dialog = new ShowFragment();
        dialog.show(getSupportFragmentManager(), "EditNameDialog");
    }


    public void refresh() {
        menu.toggle();
    }

    private void initMenu() {
        HomeFragment01 homeFragment = new HomeFragment01();
        menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT);
        //设置触摸屏幕的模式
        //  menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowWidth(getWindowManager().getDefaultDisplay().getWidth() / 40);
        menu.setShadowDrawable(R.color.colorPrimaryDark);
        //设置滑动菜单的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        //设置渐入渐出效果的值
        menu.setFadeEnabled(true);//是否有渐变
        menu.setFadeDegree(0.4f);
        //把滑动菜单添加进所有的Activity中
        // menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        setBehindContentView(R.layout.left_menu);//设置SlidingMenu滑出来之后的布局
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, homeFragment).commit();
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
    }


    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadLabel();
            updateUnreadAddressLable();
        }

        // unregister this event listener when this activity enters the
        // background
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(this);

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                // 先将头像和昵称保存在本地缓存
                try {
                    String chatUserId = message.getStringAttribute(SharePrefConstant.ChatUserId);
                    String avatarUrl = message.getStringAttribute(SharePrefConstant.ChatUserPic);
                    String nickName = message.getStringAttribute(SharePrefConstant.ChatUserNick);
                    Log.d("TAG", "chatUserId-->" + chatUserId + "avatarUrl-->" + avatarUrl + "nickName-->" + nickName);
                    UserInfoCacheSvc.createOrUpdate(chatUserId, nickName, avatarUrl);

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }

                DemoHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
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
            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                updateUnreadLabel();
            }
        });
    }

    private void initView() {
        adapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(0);
        mRgTools.setOnCheckedChangeListener(this);
        MainFragment fragment = new MainFragment();
        badgeView2 = new BadgeView(this);
        badgeView2.setTargetView(findViewById(R.id.bt));
        badgeView2.setBadgeGravity(Gravity.RIGHT | Gravity.TOP);
        badgeView2.setBadgeMargin(0, 0, 10, 0);
        badgeView = new BadgeView(this);
        badgeView.setTargetView(findViewById(R.id.bt1));
        badgeView.setBadgeGravity(Gravity.RIGHT | Gravity.TOP);
        badgeView.setBadgeMargin(0, 0, 10, 0);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mRgTools.check(mRgTools.getChildAt(position).getId());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        mViewPager.setCurrentItem(radioGroup.indexOfChild(radioGroup.findViewById(checkedId)), false);
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                } else {
                    //MyActivityManager.getInstance().moveTaskToBack(mContext);// 不退出，后台运行
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        int count1 = getUnreadAddressCountTotal();
        if (count > 0) {
            badgeView.setBadgeCount(count);

        } else {
            badgeView.setVisibility(View.GONE);
        }
        if (count1 > 0) {
            badgeView.setBadgeCount(count + count1);
        }
    }

    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMsgsCount();
    }

    public void updateUnreadAddressLable() {
        runOnUiThread(new Runnable() {
            public void run() {
                int count = getUnreadAddressCountTotal();
                if (count > 0) {
                    badgeView2.setBadgeCount(count);
                } else {
                    badgeView2.setVisibility(View.INVISIBLE);
                }
            }
        });
      //  Log.d("TAG65",dao.getMessagesList().get(0).getFrom());
    }

    private InviteMessgeDao inviteMessgeDao;

    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal = 0;
        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
        return unreadAddressCountTotal;
    }
}
