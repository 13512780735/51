package com.likeit.a51scholarship.activitys.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.likeit.a51scholarship.R;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import com.likeit.a51scholarship.activitys.Container;
import com.likeit.a51scholarship.configs.AppConfig;
import com.likeit.a51scholarship.http.HttpUtil;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.utils.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

public class RegisterActivity extends Container {
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.send_code_btn)
    TextView sendCodeBtn;
    @BindView(R.id.passwd_et)
    EditText passwdEt;
    @BindView(R.id.username_et)
    EditText usernameEt;
    @BindView(R.id.man_radio)
    RadioButton manRadio;
    @BindView(R.id.woman_radio)
    RadioButton womanRadio;
    @BindView(R.id.sex_radio_group)
    RadioGroup sexRadioGroup;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.backBtn)
    Button btBack;
    TimeCount time = new TimeCount(60000, 1000);
    private String phoneNum, code, passwd, username, sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        tvHeader.setText("注册");
        sexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.man_radio:
                        sex = "1";
                        break;
                    case R.id.woman_radio:
                        sex = "2";
                        break;
                }
            }
        });
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


    private void sendCode() {
        phoneNum = phoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtil.showS(mContext, "请输入手机号码");
            return;
        } else {
            SMSSDK.getVerificationCode("86", phoneNum);
            time.start();
        }
    }

    @OnClick({R.id.send_code_btn, R.id.register_btn, R.id.web_layout, R.id.backBtn, R.id.web_layout01})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                toActivityFinish(LoginActivity.class);
                break;
            case R.id.send_code_btn:
                sendCode();
                break;
            case R.id.register_btn:
                register();
                break;
            case R.id.web_layout:
                //  toWebActivity("", "用户协议");
                break;
            case R.id.web_layout01:
                toActivityFinish(LoginActivity.class);
                break;
        }
    }

    private void register() {
        code = codeEt.getText().toString().trim();
        passwd = passwdEt.getText().toString().trim();
        username = usernameEt.getText().toString().trim();

        if (TextUtils.isEmpty(sex)) {
            ToastUtil.showS(mContext, "请选择性别");
            return;
        }

        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtil.showS(mContext, "请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(passwd)) {
            ToastUtil.showS(mContext, "请输入密码");
            return;
        }
        if (TextUtils.isEmpty(username)) {
            ToastUtil.showS(mContext, "请输入姓名");
            return;
        }
        SMSSDK.submitVerificationCode("86", phoneNum, code);
    }

    private void Register() {
        String url = AppConfig.LIKEIT_REGISTER;
        RequestParams params = new RequestParams();
        params.put("mobile", phoneNum);
        params.put("password", passwd);
        params.put("nickname", username);
        Log.d("TAG", phoneNum + passwd + username);
//        params.put("sex", sex);
//        params.put("pid", "");
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
               // Log.d("TAG", "register-->" + response);
                disShowProgress();
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        JSONObject data = obj.getJSONObject("data");
                        String ukey = data.optString("ukey");
                        ToastUtil.showS(mContext, message);
                        signup();//环信注册
                        toActivityFinish(LoginActivity.class);
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
                ToastUtil.showS(mContext, "数据请求失败！");
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    private void signup() {
//        try {
//            EMClient.getInstance().createAccount(username, passwd);//同步方法
//        } catch (HyphenateException e) {
//            e.printStackTrace();
//        }
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
                            // Toast.makeText(getApplicationContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();
                            Register();
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