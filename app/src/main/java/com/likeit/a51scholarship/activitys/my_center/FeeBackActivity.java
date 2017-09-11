package com.likeit.a51scholarship.activitys.my_center;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.Container;
import com.likeit.a51scholarship.configs.AppConfig;
import com.likeit.a51scholarship.http.HttpUtil;
import com.likeit.a51scholarship.utils.StringUtil;
import com.likeit.a51scholarship.utils.ToastUtil;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeeBackActivity extends Container {
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.content_et)
    EditText contentEt;
    @BindView(R.id.title_et)
    EditText title_et;
    private String title;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_back);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        tvHeader.setText("意见反馈");
        tvRight.setText("提交");
    }

    @OnClick({R.id.backBtn, R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.tv_right:

                offerInfo();
                break;

        }
    }

    private void offerInfo() {
        title = title_et.getText().toString();
        content = contentEt.getText().toString();
        if (StringUtil.isBlank(title)) {
            ToastUtil.showS(mContext, "输入标题！");
            return;
        }
        if (StringUtil.isBlank(content)) {
            ToastUtil.showS(mContext, "输入内容不能为空！");
            return;
        }
        upload();

    }

    private void upload() {
        String url = AppConfig.LIKEIT_FEEDBACK;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("title", title);
        params.put("content", content);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        ToastUtil.showS(mContext, message);
                        onBackPressed();
                    } else {
                        ToastUtil.showS(mContext, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failed(Throwable e) {

            }
        });
    }
}
