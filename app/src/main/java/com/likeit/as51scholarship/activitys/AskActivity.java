package com.likeit.as51scholarship.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.likeit.as51scholarship.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AskActivity extends Container {
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvHeader.setText("提问");
        tvRight.setText("发布");
    }
    @OnClick(R.id.backBtn)
    public void onClick(View view){
        switch (view.getId()){
            case  R.id.backBtn:
                onBackPressed();
                break;
        }
    }
}
