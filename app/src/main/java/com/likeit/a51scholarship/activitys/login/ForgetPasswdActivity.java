package com.likeit.a51scholarship.activitys.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.Container;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.utils.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ForgetPasswdActivity extends Container {
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.send_code_btn)
    TextView sendCodeBtn;
    @BindView(R.id.passwd_et)
    EditText passwdEt;
    @BindView(R.id.re_passwd_et)
    EditText rePasswdEt;
    @BindView(R.id.ok_btn)
    TextView okBtn;
    @BindView(R.id.backBtn)
    Button btBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    private String phoneNum;

    private int time = 60;
    Handler myH = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    time--;
                    if (time > 0) {
                        sendCodeBtn.setText(time + "s");
                        sendEmptyMessage(1);
                    } else {
                        sendCodeBtn.setEnabled(true);
                        sendCodeBtn.setText("发送验证码");
                    }
                    break;
                case 2:
                    time = 60;
                    sendCodeBtn.setEnabled(false);
                    ToastUtil.showS(mContext, "验证码发送成功,请稍后查收");
                    sendEmptyMessage(1);
                    break;
                case 3:
                    ToastUtil.showS(mContext, "请输入正确的验证码!");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_passwd);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        tvHeader.setText("忘记密码");
    }

    @OnClick({R.id.send_code_btn, R.id.ok_btn, R.id.backBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.send_code_btn:
                sendCode();
                break;
            case R.id.ok_btn:
                okBtn();
                break;

        }
    }

    private void sendCode() {
        phoneNum = phoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtil.showS(mContext, "请输入手机号码");
            return;
        }


        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        myH.sendEmptyMessage(2);
                        //获取验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调

        SMSSDK.getVerificationCode("86", phoneNum);
    }

    private void okBtn() {
        String phone = phoneEt.getText().toString();
        String passwd = passwdEt.getText().toString();
        String rePasswd = rePasswdEt.getText().toString();
        if (TextUtils.isEmpty(passwd) || TextUtils.isEmpty(rePasswd) || TextUtils.isEmpty(phone)) {
            showToast("请填写完整信息");
            return;
        }

        if (!TextUtils.equals(passwd, rePasswd)) {
            showToast("两次密码不一致");
            return;
        }


//        HttpMethods.getInstance().up_pwd(new MySubscriber<UserInfoEntity>(this) {
//
//            @Override
//            public void onHttpCompleted(HttpResult<UserInfoEntity> userInfoEntityHttpResult) {
//                showToast("修改密码成功");
//                toActivityFinish(LoginActivity.class);
//            }
//
//            @Override
//            public void onHttpError(Throwable e) {
//
//            }
//        },phone,passwd,rePasswd);
    }
}
