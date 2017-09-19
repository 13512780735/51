package com.likeit.as51scholarship.activitys.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.utils.MyActivityManager;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_splash);
        View main = getLayoutInflater().from(this).inflate(R.layout.activity_splash, null);
        main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        MyActivityManager.getInstance().addActivity(this);
        setContentView(main);
        final Intent intent = new Intent(this, GuideActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task, 3000);// 此处的Delay可以是3*1000，代表三秒

    }
}
