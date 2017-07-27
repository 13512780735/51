package com.likeit.a51scholarship.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.utils.MyActivityManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchInfoActivity extends Container {
    @BindView(R.id.top_bar_back_img)
    ImageView topBarBackImg;
    @BindView(R.id.search_content_et)
    EditText searchContentEt;
    @BindView(R.id.search_img)
    ImageView searchImg;
    @BindView(R.id.search_layout)
    LinearLayout searchLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_info);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.top_bar_back_img, R.id.search_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back_img:
                onBackPressed();
                break;
            case R.id.search_img:

                break;
        }
    }
}
