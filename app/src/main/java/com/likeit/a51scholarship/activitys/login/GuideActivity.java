package com.likeit.a51scholarship.activitys.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.MainActivity;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.utils.UtilPreference;
import butterknife.BindView;
import butterknife.ButterKnife;


public class GuideActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.guide_tvbrow)
    TextView tvBrow;
    @BindView(R.id.guide_tvLogin)
    TextView tvLogin;
    @BindView(R.id.guide_tvRegister)
    TextView tvRegister;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        MyActivityManager.getInstance().addActivity(this);
        mContext = this;
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvBrow.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guide_tvbrow:
                Intent intent01 = new Intent(mContext, MainActivity.class);
                String ukey="tvx2kTyMEZX5fm*gdspXeLD6GXZkpki9";
                UtilPreference.saveString(mContext, "ukey", ukey);
                intent01.putExtra("key","1");
                startActivity(intent01);
                finish();
                break;
            case R.id.guide_tvLogin:
                Intent intent02 = new Intent(mContext, LoginActivity.class);
                startActivity(intent02);
                finish();
                break;
            case R.id.guide_tvRegister:
                Intent intent03 = new Intent(mContext, RegisterActivity.class);
                startActivity(intent03);
                finish();
                break;
        }
    }
}
