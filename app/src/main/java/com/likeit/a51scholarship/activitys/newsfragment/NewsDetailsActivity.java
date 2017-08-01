package com.likeit.a51scholarship.activitys.newsfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.Container;
import com.likeit.a51scholarship.activitys.SendNewsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NewsDetailsActivity extends Container {
    @BindView(R.id.iv_header_left)
    ImageView ivLeft;
    @BindView(R.id.iv_header_right)
    ImageView ivRight;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvHeader.setText("资讯详情页");
        ivRight.setImageResource(R.mipmap.icon_edit);
        ivLeft.setImageResource(R.mipmap.icon_back);
    }
    @OnClick({R.id.iv_header_right,R.id.iv_header_left})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_header_left:
                onBackPressed();
                break;
            case R.id.iv_header_right:
                toActivity(SendNewsActivity.class);
                break;
        }

    }
}
