package com.likeit.as51scholarship.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.likeit.as51scholarship.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebADLActivity extends Container {
    @BindView(R.id.backBtn)
    Button btBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.web_view)
    WebView webView;
    private ProgressDialog nDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_adl);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        String link=intent.getStringExtra("adlUrl");
        tvHeader.setText("院校详情");
        webView.loadUrl(link);
        webView.setWebViewClient(new MyWebViewClient());
        // 设置WebView属性，能够执行JavaScript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        // 为图片添加放大缩小功能
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);;
        webView.setInitialScale(70);   //100代表不缩放
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {// 网页页面开始加载的时候
            if (nDialog == null) {
                nDialog = new ProgressDialog(mContext);
                nDialog.setMessage("数据加载中，请稍后。。。");
                nDialog.show();
                webView.setEnabled(false);// 当加载网页的时候将网页进行隐藏
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {// 网页加载结束的时候
            // super.onPageFinished(view, url);
            if (nDialog != null && nDialog.isShowing()) {
                nDialog.dismiss();
                nDialog = null;
                webView.setEnabled(true);
            }
        }

        @SuppressWarnings("static-access")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) { // 网页加载时的连接的网址
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            // TODO Auto-generated method stub
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

    }
    @OnClick(R.id.backBtn)
    public void Onclick(View v){
        onBackPressed();
    }
}
