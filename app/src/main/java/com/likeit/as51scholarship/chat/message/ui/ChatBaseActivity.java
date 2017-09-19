package com.likeit.as51scholarship.chat.message.ui;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.likeit.as51scholarship.utils.AndroidWorkaround;
import com.likeit.as51scholarship.utils.MyActivityManager;
import com.umeng.analytics.MobclickAgent;

import static com.likeit.as51scholarship.activitys.Container.setMiuiStatusBarDarkMode;

/**
 * Created by Administrator on 2017/8/17.
 */

public class ChatBaseActivity extends EaseBaseActivity {
    private ChatBaseActivity mContext;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mContext = this;
        setMiuiStatusBarDarkMode(this, true);
        Window window = this.getWindow();
        // 透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }
        MyActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // umeng
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // umeng
        MobclickAgent.onPause(this);
    }
}
