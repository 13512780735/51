package com.likeit.as51scholarship.activitys.login;

import android.content.Intent;
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
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.MainActivity;
import com.likeit.as51scholarship.app.MyApplication;
import com.likeit.as51scholarship.chat.message.db.DemoDBManager;
import com.likeit.as51scholarship.chat.message.widget.DemoHelper;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.utils.MyActivityManager;
import com.likeit.as51scholarship.utils.NetUtils;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.utils.UtilPreference;
import com.loopj.android.http.RequestParams;
import com.tencent.bugly.beta.Beta;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends BaseActivity implements PlatformActionListener, Handler.Callback {
    private static final int MSG_AUTH_CANCEL = 1;
    private static final int MSG_AUTH_ERROR = 2;
    private static final int MSG_AUTH_COMPLETE = 3;


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
    private String phoneNum, currentUsername, passwd;
    private String is_first;
    private boolean check;

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
    private String passwd1;
    private String is_bind;
    private String third_ukey;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        passwd1 = "eed92abc0da569ad37b6e07b1d639400";
        // check = true;
        check = UtilPreference.getBooleanValue(mContext, "check");
        //checkVersion();
        if (!check) {
            // checkVersion();
            check = false;
            UtilPreference.saveBoolean(mContext, "check", check);
        } else {
            return;
        }
        handler = new Handler(this);
    }

    private void checkVersion() {
        Beta.checkUpgrade();//检查版本号
        //  Beta.autoCheckUpgrade = false;//设置不自动检查
    }


    @OnClick({R.id.login_btn, R.id.send_code_btn, R.id.forget_passwd_tv, R.id.phone_login_tv, R.id.register_layout, R.id.login_wechat, R.id.login_qq, R.id.login_weibo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                UtilPreference.saveString(mContext, "isLogin", "0");
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
                Intent intent=new Intent(mContext,RegisterActivity.class);
                intent.putExtra("id","2");
                startActivity(intent);
                finish();
               // toActivity(RegisterActivity.class);
                break;
            case R.id.send_code_btn:
                sendCode();
                break;
            case R.id.login_wechat:
                // 微信登录
                UtilPreference.saveString(mContext, "isLogin", "0");
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                authorize(wechat);
                ToastUtil.showL(this, "微信登录");
                break;
            case R.id.login_qq:
                // QQ登录
                UtilPreference.saveString(mContext, "isLogin", "0");
                Platform qzone = ShareSDK.getPlatform(QQ.NAME);
                authorize(qzone);
                ToastUtil.showL(this, "QQ登录");
                break;
            case R.id.login_weibo:
                UtilPreference.saveString(mContext, "isLogin", "0");
                Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
                authorize(sina);
                ToastUtil.showL(this, "微博登录");
                // ToastUtil.showL(this, "暂未开通");
                break;
        }
    }

    private void signin() {
        EMClient.getInstance().login(currentUsername, passwd1, new EMCallBack() {
            @Override
            public void onSuccess() {
                disShowProgress();
                Log.d("TAG", "EM登录成功");
                Log.d("TAG", "currentUsername-->" + currentUsername);
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
                Log.d("TAG", "currentUsername-->" + currentUsername);
                Log.d("TAG", "passwd1-->" + passwd1);
                showErrorMsg("登录失败");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String name1 = UtilPreference.getStringValue(mContext, "name");
        String passwd1 = UtilPreference.getStringValue(mContext, "passwd");
        usernameEt.setText(name1);
        passwdEt.setText(passwd1);
    }

    private void login() {
        phoneNum = phoneEt.getText().toString().trim();
        currentUsername = usernameEt.getText().toString().trim();
        passwd = passwdEt.getText().toString().trim();
        if (account_login) {
            if (TextUtils.isEmpty(currentUsername) || TextUtils.isEmpty(passwd)) {
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
        params.put("mobile", currentUsername);
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
                        UtilPreference.saveString(mContext, "name", currentUsername);
                        UtilPreference.saveString(mContext, "passwd", passwd);
                        logout();

                        DemoDBManager.getInstance().closeDB();
                        DemoHelper.getInstance().setCurrentUserName(currentUsername);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                //环信登录
//                                signin();
//                            }
//                        });
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

    // 执行授权,获取用户信息
    // 文档：http://wiki.mob.com/Android_%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E8%B5%84%E6%96%99
    private void authorize(Platform plat) {

        plat.setPlatformActionListener(this);
        // 关闭SSO授权
        //plat.SSOSetting(true);
        plat.SSOSetting(false);
        // plat.authorize();
        plat.showUser(null);
    }

    @SuppressWarnings("unchecked")
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_AUTH_CANCEL: {
                // 取消授权
                disShowProgress();
                ToastUtil.showL(this, "取消授权");
                // ToastUtil.showL(this, getString(R.string.auth_cancel));
                Log.d("TAG", msg.toString());
            }
            break;
            case MSG_AUTH_ERROR: {
                disShowProgress();
                // 授权失败
                Log.d("TAG", "授权失败");
                ToastUtil.showL(this, "授权失败");
                // ToastUtil.showL(this, getString(R.string.auth_error));
                Log.d("TAG", msg.toString());
            }
            break;
            case MSG_AUTH_COMPLETE: {
                disShowProgress();
                // 授权成功
                // ToastUtil.showL(this, "授权成功");
                Log.d("TAG", msg.toString());
                Log.d("TAG", "授权成功");
                // ToastUtil.showL(this, getString(R.string.auth_complete));
                Log.d("数据2：", msg.toString());
                Object[] objs = (Object[]) msg.obj;
                Log.d("数据2：", objs.toString());
                String platform = (String) objs[0];
                //   HashMap<String, Object> res1 = (HashMap<String, Object>) objs[1];
                Platform res = (Platform) objs[1];
                // Log.e("TAG", "授权返回的信息1：" + JSON.toJSONString(objs[1]));
                Log.e("TAG", QQ.NAME + "授权返回的信息2：" + platform);
//                res.getDb().getToken();
//                res.getDb().getUserGender();
//                res.getDb().getUserIcon();
//                res.getDb().getUserId();
//                res.getDb().getUserName();
                if (res != null) {
                    if (platform.equals(QQ.NAME)) {
                        // QQ认证回调
                        //  currentUsername=(String)res.get("nickname");
                        String figureurl_qq_1 = res.getDb().getUserIcon();
                        String nickname = res.getDb().getUserName();
                        String uid = res.getDb().getUserId();
                        Log.d("TAG", "nickname-->" + nickname);
                        UtilPreference.saveString(mContext, "third_type", "third_qq");
                        Log.d("TAG", "figureurl_qq_1-->" + figureurl_qq_1);
                        userRegister(QQ.NAME, nickname, uid, figureurl_qq_1);
                        showProgress("Loading...");

                    } else if (platform.equals(SinaWeibo.NAME)) {
                        // 新浪微博认证回调
                        String figureurl_qq_1 = res.getDb().getUserIcon();
                        String nickname = res.getDb().getUserName();
                        String uid = res.getDb().getUserId();
                        Log.d("TAG", "nickname-->" + nickname);
                        Log.d("TAG", "figureurl_qq_1-->" + figureurl_qq_1);
                        UtilPreference.saveString(mContext, "third_type", "third_weibo");
                        userRegister(SinaWeibo.NAME, nickname, uid, figureurl_qq_1);
                        showProgress("Loading...");
                    } else if (platform.equals(Wechat.NAME)) {
                        // 微信认证回调
                        String figureurl_qq_1 = res.getDb().getUserIcon();
                        String nickname = res.getDb().getUserName();
                        String uid = res.getDb().getUserId();
                        Log.d("TAG", "nickname-->" + nickname);
                        Log.d("TAG", "figureurl_qq_1-->" + figureurl_qq_1);
                        UtilPreference.saveString(mContext, "third_type", "third_wechat");
                        userRegister(Wechat.NAME, nickname, uid, figureurl_qq_1);
                        showProgress("Loading...");
                    }

                } else {
                    ToastUtil.showL(this, "获取用户信息失败！");
                }
            }
            break;
        }
        return false;
    }

    @Override
    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_CANCEL);
            Log.d("TAG", platform.toString());
        }
    }

    // 获得第三方帐号资料
    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        if (action == Platform.ACTION_USER_INFOR) {
            Message msg = new Message();
            Log.d("TAG", "platform-->" + platform.toString());
            Log.d("数据：", res.toString());
            msg.what = MSG_AUTH_COMPLETE;
            // PlatformDb platDB = platform.getDb();//获取数平台数据DB
            msg.obj = new Object[]{platform.getName(), platform};
            // msg.obj = new Object[]{platform.getName(), platDB};
//            //通过DB获取各种数据
//            platDB.getToken();
//            platDB.getUserGender();
//            platDB.getUserIcon();
//            platDB.getUserId();
//            platDB.getUserName();
            handler.sendMessage(msg);
        }
    }

    @Override
    public void onError(Platform platform, int action, Throwable t) {
        if (action == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_ERROR);
            Log.d("TAG", t.toString());
        }
        t.printStackTrace();
    }

    /**
     * 用户注册
     *
     * @param name
     * @param faceUrl
     */
    private void userRegister(final String type, final String name, final String uid,
                              final String faceUrl) {
        String url = AppConfig.LIKEIT_THIRD_LOGIN;
        if (NetUtils.isOnline()) {
            Log.e("TAG789624", "userRegister");
            Log.e("TAG789624", "type-->" + type);
            Log.e("TAG789624", "uid-->" + uid);
            Log.e("TAG789624", "name-->" + name);
            Log.e("TAG789624", "faceUrl-->" + faceUrl);
            RequestParams params = new RequestParams();
            // TreeMap<String, String> params = new TreeMap<String, String>();
            params.put("third_uid", uid);
            // showErrorMsg("正在注册中");
            HttpUtil.post(url, params, new HttpUtil.RequestListener() {

                @Override
                public void success(String response) {
                    disShowProgress();
                    // MyApplication.getInstance().doLogin(userInfo);
                    try {

                        JSONObject obj = new JSONObject(response);
                        String code = obj.optString("code");
                        String message = obj.optString("message");
                        if ("1".equals(code)) {
                            third_ukey = obj.optJSONObject("data").optString("ukey");
                            String is_first = obj.optJSONObject("data").optString("is_first");
                            currentUsername = obj.optJSONObject("data").optString("easemob_id");
                            is_bind = obj.optJSONObject("data").optString("is_bind");
                            UtilPreference.saveString(getApplicationContext(),
                                    "ukey", third_ukey);
                            UtilPreference.saveString(getApplicationContext(),
                                    "is_first", "0");
                            logout();

                        } else {
                            UtilPreference.saveString(mContext, "third_uid", uid);
                            toActivity(BindAccountActivity.class);
                            // ToastUtil.showS(LoginActivity.this, message);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

                @Override
                public void failed(Throwable error) {
                    Log.d("注册失败：", error.toString());
                    ToastUtil.showS(getApplicationContext(), "网络异常请重新再试！");
                }

                @Override
                public void onFinish() {
                    // TODO Auto-generated method stub
                    Log.d("TAG", "1234");
                    disShowProgress();
                }
            });

        } else {
            ToastUtil.showL(this, "当前无网络连接");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        toActivity(GuideActivity.class);
    }
}
