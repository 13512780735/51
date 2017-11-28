package com.likeit.as51scholarship.activitys.login;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.Container;
import com.likeit.as51scholarship.activitys.MainActivity;
import com.likeit.as51scholarship.app.MyApplication;
import com.likeit.as51scholarship.chat.message.db.DemoDBManager;
import com.likeit.as51scholarship.chat.message.widget.DemoHelper;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.utils.StringUtil;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.utils.UtilPreference;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

public class BindAccount02Activity extends Container {
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.send_code_btn)
    TextView sendCodeBtn;
    @BindView(R.id.passwd_et)
    EditText passwdEt;
    TimeCount time = new TimeCount(60000, 1000);
    private String phoneNum;
    private String code;
    private String passwd;
    private String is_first;
    private String passwd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_account02);
        ButterKnife.bind(this);
        passwd1 = "eed92abc0da569ad37b6e07b1d639400";
        initView();
    }

    private void initView() {
        tvHeader.setText("绑定账号");
    }



    @OnClick({R.id.send_code_btn, R.id.register_btn, R.id.web_layout, R.id.backBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.send_code_btn:
                sendCode();
                break;
            case R.id.register_btn:
                bind();
                break;
            case R.id.web_layout:
                //  toWebActivity("", "用户协议");
                toActivity(ProtocolActivity.class);
                break;
        }
    }

    private void bind() {
        phoneNum = phoneEt.getText().toString().trim();
        code = codeEt.getText().toString().trim();
        passwd = passwdEt.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtil.showS(mContext, "请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showS(mContext, "请输入验证码");
            return;
        }
        SMSSDK.submitVerificationCode("86", phoneNum, code);
        if (TextUtils.isEmpty(passwd)) {
            ToastUtil.showS(mContext, "请输入密码");
            return;
        }
        userLogin();
    }

    private void userLogin() {
        Login();
        showProgress("Loading...");
    }

    private void Login() {
        String url = AppConfig.LIKEIT_LOGIN;
        RequestParams params = new RequestParams();
        params.put("mobile", phoneNum);
        params.put("password", passwd);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG", "Login-->" + response);

                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        JSONObject data = obj.optJSONObject("data");
                        String ukey = data.optString("ukey");
                        is_first = data.optString("is_first");
                        UtilPreference.saveString(mContext, "ukey", ukey);
                        UtilPreference.saveString(mContext, "is_first", is_first);
                        UtilPreference.saveString(mContext, "name", phoneNum);
                        UtilPreference.saveString(mContext, "passwd", passwd);
                        initBindUser();
//                        logout();
//                        DemoDBManager.getInstance().closeDB();
//                        DemoHelper.getInstance().setCurrentUserName(phoneNum);
                    } else {
                        disShowProgress();
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
                // disShowProgress();
            }
        });
    }

    private void initBindUser() {
        String third_uid=UtilPreference.getStringValue(mContext,"third_uid");
        String third_type=UtilPreference.getStringValue(mContext,"third_type");
        String ukey=UtilPreference.getStringValue(mContext,"ukey");
        Log.d("TAG", "ukey-->" + ukey);
        Log.d("TAG", "ukey-->" + third_uid);
        Log.d("TAG", "ukey-->" + third_type);
        String url = AppConfig.LIKEIT_BIND_THIRD;
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        params.put("third_uid", third_uid);
        params.put("third_type", third_type);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG", "Login-->" + response);

                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                       ToastUtil.showS(mContext,message);
                        String ukey=obj.optString("ukey");
                        UtilPreference.saveString(mContext,"ukey",ukey);
                        logout();
                    } else {
                        disShowProgress();
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
                // disShowProgress();
            }
        });
    }

    private void logout() {
        DemoHelper.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        // show login screen
//                        toActivityFinish(GuideActivity.class);
//                        MyActivityManager.getInstance().finishAllActivity();
                        Log.d("TAG", "EM退出成功");
                        signin();


                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(mContext, "unbind devicetokens failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void signin() {
        EMClient.getInstance().login(phoneNum, passwd1, new EMCallBack() {
            @Override
            public void onSuccess() {
                disShowProgress();
                Log.d("TAG", "EM登录成功");
                Log.d("TAG", "currentUsername-->" + phoneNum);
                Log.d("TAG", "passwd1-->" + passwd1);
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                        MyApplication.currentUserNick.trim());
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
//                toActivityFinish(UploadImgActivity.class);
                if ("0".equals(is_first)) {
                    toActivityFinish(MainActivity.class);
                } else {
                    toActivity(UploadImgActivity.class);
                }
            }

            @Override
            public void onError(int i, String s) {
                disShowProgress();
                //  Toast.makeText(mContext,"EM登录失败",Toast.LENGTH_LONG).show();
                // ToastUtil.showS(mContext, "EM登录失败");
                Log.d("TAG", "EM登录失败");
                Log.d("TAG", s);
                Log.d("TAG", "currentUsername-->" + phoneNum);
                Log.d("TAG", "passwd1-->" + passwd1);
                showErrorMsg("登录失败");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    private void sendCode() {
        phoneNum = phoneEt.getText().toString().trim();
        if (!(StringUtil.isCellPhone(phoneNum))) {
            ToastUtil.showS(mContext, "请输入正确的手机号码");
            return;
        } else {
            SMSSDK.getVerificationCode("86", phoneNum);
            time.start();
        }
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
