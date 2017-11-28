package com.likeit.as51scholarship.activitys.login;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.Container;
import com.likeit.as51scholarship.utils.MyActivityManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProtocolActivity extends Container {
    @BindView(R.id.tv_header)
    TextView textView;
    @BindView(R.id.web_view)
    WebView web_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        textView.setText("用户协议");
        web_view.loadUrl("file:///android_asset/userProtocol.html");

    }
    @OnClick(R.id.backBtn)
    public void Onclcik(View v){
        onBackPressed();
    }
}
