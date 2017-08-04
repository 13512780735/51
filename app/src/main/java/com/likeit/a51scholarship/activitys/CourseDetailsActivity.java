package com.likeit.a51scholarship.activitys;


import android.os.Bundle;


import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.view.videoview.utils.X5WebView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CourseDetailsActivity extends Container {
    X5WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        ButterKnife.bind(this);
        initView();


    }

    private void initView() {
        String url = "http://bvideo.spriteapp.cn/video/2016/0704/577a4c29e1f14_wpd.mp4";
        webView.loadUrl(url);
    }


}
