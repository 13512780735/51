package com.likeit.a51scholarship.activitys.my_center;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.Container;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NearSeeActivity extends Container {

    @BindView(R.id.tv_header)
    TextView tvHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_see);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        tvHeader.setText("最近观看");
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
