package com.likeit.a51scholarship.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.utils.MyActivityManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchSchoolActivity extends Container {
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.search_content_et)
    EditText searchContentEt;
    @BindView(R.id.audio_icon)
    ImageView audioIcon;
    @BindView(R.id.search_layout)
    LinearLayout searchLayout;
    @BindView(R.id.message_img)
    ImageView messageImg;
    @BindView(R.id.hot_school)
    RadioButton hotSchool;
    @BindView(R.id.recommend_school)
    RadioButton recommendSchool;
    @BindView(R.id.offer_school)
    RadioButton offerSchool;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.content_frame)
    FrameLayout contentFrame;
    @BindView(R.id.line_hot_school)
    View lineHotSchool;
    @BindView(R.id.line_recommend)
    View lineRecommend;
    @BindView(R.id.line_offer)
    View lineOffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_school);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);

    }
    @OnClick({R.id.back_img, R.id.audio_icon, R.id.search_layout, R.id.message_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.audio_icon:
            case R.id.search_layout:
                toActivity(SearchInfoActivity.class);
                break;
            case R.id.message_img:
                break;
        }
    }
}
