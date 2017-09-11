package com.likeit.a51scholarship.activitys.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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


public class ChangePWDActivity extends Container {
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.passwd_old_et)
    EditText passwdOldEt;
    @BindView(R.id.passwd_et)
    EditText passwdEt;
    @BindView(R.id.re_passwd_et)
    EditText rePasswdEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        ButterKnife.bind(this);
        tvHeader.setText("修改密码");
    }
    @OnClick({ R.id.ok_btn, R.id.backBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.ok_btn:
                changeInfo();
                break;

        }
    }

    private void changeInfo() {
        final String ordPwd=passwdOldEt.getText().toString().trim();
        String newPwd=passwdEt.getText().toString().trim();
        String surePwd=rePasswdEt.getText().toString().trim();
        if(StringUtil.isBlank(ordPwd)){
            ToastUtil.showS(mContext,"原密码不能为空");
            return;
        }
        if(StringUtil.isBlank(newPwd)){
            ToastUtil.showS(mContext,"新密码不能为空");
            return;
        }
        if(StringUtil.isBlank(surePwd)){
            ToastUtil.showS(mContext,"确认密码不能为空");
            return;
        }
        ToastUtil.showS(mContext,"修改成功");
        String url= AppConfig.LIKEIT_CHANGE_PWD;
        RequestParams params=new RequestParams();
        params.put("ukey",ukey);
        params.put("old_pwd",ordPwd);
        params.put("pwd",newPwd);
        params.put("rpwd",surePwd);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG",response);
                try {
                    JSONObject object=new JSONObject(response);
                    String code=object.optString("code");
                    String message=object.optString("message");
                    if("1".equals(code)){
                        ToastUtil.showS(mContext,message);
                        onBackPressed();
                    }else{
                        ToastUtil.showS(mContext,message);
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
