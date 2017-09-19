package com.likeit.as51scholarship.activitys.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.Container;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.utils.MyActivityManager;
import com.likeit.as51scholarship.utils.StringUtil;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.utils.UtilPreference;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

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
    private String code;
    private String phone;
    private String passwd;
    private String rePasswd;
    TimeCount time = new TimeCount(60000, 1000);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_passwd);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        tvHeader.setText("忘记密码");
        EventHandler eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eh);
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
        phone = phoneEt.getText().toString().trim();
        if (!StringUtil.isCellPhone(phone)) {
            ToastUtil.showS(mContext, "请输入正确的手机号码");
            return;
        } else {
            SMSSDK.getVerificationCode("86", phone);
            time.start();
        }
    }

    private void okBtn() {
        phone = phoneEt.getText().toString();
        passwd = passwdEt.getText().toString();
        code = codeEt.getText().toString().trim();
        rePasswd = rePasswdEt.getText().toString();
        if (TextUtils.isEmpty(passwd) || TextUtils.isEmpty(rePasswd) || TextUtils.isEmpty(phone)) {
            showToast("请填写完整信息");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showS(mContext, "请输入验证码");
            return;
        }
        SMSSDK.submitVerificationCode("86", phone, code);
        if (!TextUtils.equals(passwd, rePasswd)) {
            showToast("两次密码不一致");
            return;
        }
//        okInfo();
//        showProgress("loading...");
    }

    private void okInfo() {
        String url = AppConfig.LIKEIT_RSETPWD;
        RequestParams params = new RequestParams();
        params.put("mobile", phone);
        params.put("pwd", passwd);
        params.put("rpwd", rePasswd);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                try {
                    JSONObject object = new JSONObject(response);
                    String code = object.optString("code");
                    String message = object.optString("message");
                    if ("1".equals(code)) {
                        ToastUtil.showS(mContext, message);
                        UtilPreference.saveString(mContext, "passwd", passwd);
                        UtilPreference.saveString(mContext, "name", phone);
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
                disShowProgress();
            }
        });
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            sendCodeBtn.setText("获取验证码");
            sendCodeBtn.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            sendCodeBtn.setClickable(false);//防止重复点击
            sendCodeBtn.setText(millisUntilFinished / 1000 + "s");
        }
    }


    Handler mHandler = new

            Handler() {
                public void handleMessage(Message msg) {

                    // TODO Auto-generated method stub
                    super.handleMessage(msg);
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    Log.e("event", "event=" + event);
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        System.out.println("--------result" + event);
                        //短信注册成功后，返回MainActivity,然后提示新好友
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                            //Toast.makeText(getApplicationContext(), "提交验证码成功"+data.toString(), Toast.LENGTH_SHORT).show();
                            okInfo();
                            showProgress("Loading...");
                        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            //已经验证
                            Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();


                        }

                    } else {
                        int status = 0;
                        try {
                            ((Throwable) data).printStackTrace();
                            Throwable throwable = (Throwable) data;
                            JSONObject object = new JSONObject(throwable.getMessage());
                            String des = object.optString("detail");
                            status = object.optInt("status");
                            if (!TextUtils.isEmpty(des)) {
                                Toast.makeText(mContext, des, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            SMSLog.getInstance().w(e);
                        }
                    }


                }
            };
}
