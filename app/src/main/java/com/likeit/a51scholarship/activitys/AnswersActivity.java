package com.likeit.a51scholarship.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.likeit.a51scholarship.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AnswersActivity extends Container {
    @BindView(R.id.iv_header_left)
    ImageView ivLeft;
    @BindView(R.id.iv_header_right)
    ImageView ivRight;
    @BindView(R.id.iv_header_right01)
    ImageView ivRight01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setImageResource(R.mipmap.icon_return);
        ivRight.setImageResource(R.mipmap.icon_issue);
        ivRight01.setImageResource(R.mipmap.nav_icon_search_sel);
    }
    @OnClick({R.id.iv_header_left,R.id.iv_header_right,R.id.iv_header_right01})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_header_left:
                onBackPressed();
                break;
            case R.id.iv_header_right:
                toActivity(AskActivity.class);
                break;
            case R.id.iv_header_right01:
                toActivity(SearchInfoActivity.class);
                break;
        }
    }
}
