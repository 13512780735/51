package com.likeit.as51scholarship.activitys.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.Container;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindAccountActivity extends Container {
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.bind_btn)
    TextView tvBind;
    @BindView(R.id.register_btn)
    TextView tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_account);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvHeader.setText("绑定账号");
    }
    @OnClick({R.id.backBtn,R.id.bind_btn,R.id.register_btn})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.bind_btn:
                toActivity(BindAccount02Activity.class);
                break;
            case R.id.register_btn:
                toActivity(RegisterActivity.class);
                break;
        }
    }
}
