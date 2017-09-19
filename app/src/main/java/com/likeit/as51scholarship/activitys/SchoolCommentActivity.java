package com.likeit.as51scholarship.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.likeit.as51scholarship.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SchoolCommentActivity extends Container {
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.school_comment_editdetails)
    EditText edDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_comment);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvHeader.setText("发表评论");
        tvRight.setText("发表");
        String editDetails = edDetails.getText().toString();
    }

    @OnClick(R.id.backBtn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
        }
    }
}
