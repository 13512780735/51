package com.likeit.as51scholarship.activitys.my_center;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.Container;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpenActivity extends Container {
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvHeader.setText("开播提醒");
    }
    @OnClick({R.id.backBtn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.backBtn:
                onBackPressed();
                break;

        }
    }
}
