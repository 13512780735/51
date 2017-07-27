package com.likeit.a51scholarship.activitys.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import com.likeit.a51scholarship.configs.AppConfig;
import com.likeit.a51scholarship.http.HttpUtil;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.utils.ToastUtil;
import com.likeit.a51scholarship.utils.UtilPreference;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.username_et)
    EditText usernameEt;
    @BindView(R.id.passwd_et)
    EditText passwdEt;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.send_code_btn)
    TextView sendCodeBtn;
    @BindView(R.id.forget_passwd_tv)
    TextView forgetPasswdTv;
    @BindView(R.id.phone_login_tv)
    TextView phoneLoginTv;
    @BindView(R.id.register_layout)
    LinearLayout registerLayout;
    @BindView(R.id.login_wechat)
    ImageView loginWechat;
    @BindView(R.id.login_qq)
    ImageView loginQq;
    @BindView(R.id.login_weibo)
    ImageView loginWeibo;
    @BindView(R.id.phone_num_layout)
    RelativeLayout phoneNumLayout;
    @BindView(R.id.phone_code_layout)
    RelativeLayout phoneCodeLayout;
    @BindView(R.id.account_name_layout)
    RelativeLayout accountNameLayout;
    @BindView(R.id.account_passwd_layout)
    RelativeLayout accountPasswdLayout;

    private boolean account_login = true;

    private int time = 60;
    Handler myH = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    time--;
                    if (time > 0) {
                        sendCodeBtn.setText("剩余" + time + "s");
                        sendEmptyMessageDelayed(1, 1000);
                    } else {
                        sendCodeBtn.setEnabled(true);
                        sendCodeBtn.setText("发送验证码");
                    }
                    break;
                case 2:
                    time = 60;
                    sendCodeBtn.setEnabled(false);
                    showToast("验证码发送成功,请稍后查收");
                    sendEmptyMessage(1);
                    break;
                case 3:
                    showToast("请输入正确的验证码!");
                    break;
            }
        }
    };
    private String phoneNum, name, passwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.login_btn, R.id.send_code_btn, R.id.forget_passwd_tv, R.id.phone_login_tv, R.id.register_layout, R.id.login_wechat, R.id.login_qq, R.id.login_weibo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                login();
                break;
            case R.id.forget_passwd_tv:
                toActivity(ForgetPasswdActivity.class);
                break;
            case R.id.phone_login_tv:
                account_login = !account_login;
                if (account_login) {
                    phoneLoginTv.setText("手机动态登录");
                } else {
                    phoneLoginTv.setText("普通用户登录");
                }
                accountPasswdLayout.setVisibility(account_login ? View.VISIBLE : View.GONE);
                accountNameLayout.setVisibility(account_login ? View.VISIBLE : View.GONE);
                phoneCodeLayout.setVisibility(account_login ? View.GONE : View.VISIBLE);
                phoneNumLayout.setVisibility(account_login ? View.GONE : View.VISIBLE);
                break;
            case R.id.register_layout:
                toActivity(RegisterActivity.class);
                break;
            case R.id.send_code_btn:
                sendCode();
                break;
            case R.id.login_wechat:
                break;
            case R.id.login_qq:
                break;
            case R.id.login_weibo:
                break;
        }
    }

    private void login() {
        phoneNum = phoneEt.getText().toString().trim();
        name = usernameEt.getText().toString();
        passwd = passwdEt.getText().toString();
        if (account_login) {
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(passwd)) {
                showToast("请填写账号或密码");
                return;
            }
            Login();
            showProgress("Loading...");

        } else {
            final String phone = phoneEt.getText().toString();
            final String code = codeEt.getText().toString();
            if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(code)) {
                showToast("请填写手机号码或验证码");
                return;
            }

            EventHandler eh = new EventHandler() {
                @Override
                public void afterEvent(int event, int result, Object data) {

                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //回调完成
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//                            myH.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    //提交验证码成功
//                                    HttpMethods.getInstance().login_phone(new MySubscriber<UserInfoEntity>(LoginActivity.this) {
//
//                                        @Override
//                                        public void onHttpCompleted(HttpResult<UserInfoEntity> userInfoEntityHttpResult) {
//                                            handlerLogin(userInfoEntityHttpResult);
//                                        }
//
//                                        @Override
//                                        public void onHttpError(Throwable e) {
//
//                                        }
//                                    }, phone);
//                                }
//                            }, 100);
                        }
                    } else {
                        myH.sendEmptyMessage(3);
                    }
                }
            };
            SMSSDK.registerEventHandler(eh); //注册短信回调
            SMSSDK.submitVerificationCode("86", phone, code);


        }
    }

    private void Login() {
        String url = AppConfig.LIKEIT_LOGIN;
        RequestParams params = new RequestParams();
        params.put("mobile", name);
        params.put("password", passwd);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG", "Login-->" + response);
                disShowProgress();
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        JSONObject data = obj.optJSONObject("data");
                        String ukey = data.optString("ukey");
                        UtilPreference.saveString(mContext, "ukey", ukey);
                        toActivity(UploadImgActivity.class);
                        //toActivityFinish(MainActivity.class);
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
                showErrorMsg("数据请求失败！");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                disShowProgress();
            }
        });

    }

//    private void handlerLogin(HttpResult<UserInfoEntity> userInfoEntityHttpResult) {
//        if (userInfoEntityHttpResult.isStatus()) {
//            showToast("登陆成功");
//            ((MyApplication) getApplication()).userInfoEntity = userInfoEntityHttpResult.getData();
//            PreferencesUtil.putStringValue(PreferConfigs.uKey, userInfoEntityHttpResult.getData().getUkey());
//            PreferencesUtil.putStringValue(PreferConfigs.uid, userInfoEntityHttpResult.getData().getUid());
//            toActivityFinish(MainActivity.class);
//            toFinish();
//        } else {
//            showToast("登录失败");
//        }
//    }


    private void sendCode() {
        if (TextUtils.isEmpty(phoneNum)) {
            showToast("请输入手机号码");
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
                        //获取验证码成功
                        myH.sendEmptyMessage(2);
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
}
