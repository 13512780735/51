package com.likeit.as51scholarship.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.login.ChangePWDActivity;
import com.likeit.as51scholarship.activitys.login.LoginActivity;
import com.likeit.as51scholarship.activitys.my_center.AboutActivity;
import com.likeit.as51scholarship.activitys.my_center.CollectActivity;
import com.likeit.as51scholarship.activitys.my_center.DianActivity;
import com.likeit.as51scholarship.activitys.my_center.EditorCenterActivity;
import com.likeit.as51scholarship.activitys.my_center.FeeBackActivity;
import com.likeit.as51scholarship.activitys.my_center.InviteFriendsActivity;
import com.likeit.as51scholarship.activitys.my_center.NearSeeActivity;
import com.likeit.as51scholarship.activitys.my_center.OpenActivity;
import com.likeit.as51scholarship.activitys.my_center.RealNameActivity;
import com.likeit.as51scholarship.activitys.my_center.SetActivity;
import com.likeit.as51scholarship.activitys.my_center.SpentActivity;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.model.UserInfoBean;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.utils.UtilPreference;
import com.likeit.as51scholarship.view.CircleImageView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment01 extends MyBaseFragment implements View.OnClickListener {
    private CircleImageView userHeadImg;
    private TextView accountTv;
    private TextView accountTv01;
    private LinearLayout accountLayout;
    private TextView accountManagerTv;
    private LinearLayout accountManagerLayout;
    private View line1;
    private View collectRedV;
    private TextView collectNumTv;
    private LinearLayout collectLayout;
    private View spentRedV;
    private TextView spentNumTv;
    private LinearLayout spentLayout;
    private LinearLayout dianLayout;
    private LinearLayout nearSeeLayout;
    private View nearOpenRedV;
    private TextView openNumTv;
    private LinearLayout openLayout;
    private LinearLayout aboutLayout;
    private LinearLayout setLayout;
    private ImageView goAccountImg;
    ImageView realImg;
    private String ukey;
    private LinearLayout feedBackLyout;
    private LinearLayout inviteLayout;
    private LinearLayout realLayout;
    private ImageView userEditorImg;
    /**
     * 判断用户是否登录
     * 0.代表登录用户
     * 1.代表未登录用户
     */
    private String isLogin;
    private ProgressDialog dialog;
    //数据获取
    private String nickName, headImg, mobile;
    private UserInfoBean userInfobean;
    private TextView accountMoveTv;
    private TextView real_tv;


    @Override
    protected int setContentView() {
        return R.layout.fragment_home_fragment01;
    }

    @Override
    protected void lazyLoad() {
        initView();
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        isLogin = UtilPreference.getStringValue(getActivity(), "isLogin");
        Log.d("TAG", "isLogin-->" + isLogin);


    }

    @Override
    public void onResume() {
        super.onResume();
        if ("0".equals(isLogin)) {
            //获取用户信息
            initUser();
            dialog.show();

        } else {
            return;
        }
    }

    private void initUser() {
        String url = AppConfig.LIKEIT_GET_INFO;
        ukey = UtilPreference.getStringValue(getActivity(), "ukey");
        RequestParams params = new RequestParams();
        params.put("ukey", ukey);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                dialog.dismiss();
                Log.d("TAG", "用户信息-->" + response + "ukey-->" + ukey);
                try {
                    JSONObject object = new JSONObject(response);
                    String code = object.optString("code");
                    String message = object.optString("message");
                    JSONObject data = object.optJSONObject("data");
                    Log.d("TAG", data.toString());
                    if ("1".equals(code)) {
                        userInfobean = JSON.parseObject(String.valueOf(data), UserInfoBean.class);
                        Log.d("TAG", "mobile3-->" + userInfobean.getNickname());
                        userInfo();
                    } else {
                        ToastUtil.showS(getActivity(), message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                dialog.dismiss();
                ToastUtil.showS(getActivity(), "获取用户信息失败！");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
            }
        });

    }

    private void userInfo() {
        if ("0".equals(isLogin)) {
            userEditorImg.setVisibility(View.VISIBLE);
           // realImg.setVisibility(View.VISIBLE);
            accountTv.setVisibility(View.GONE);
            accountTv01.setVisibility(View.VISIBLE);
            accountMoveTv.setVisibility(View.GONE);
            accountManagerTv.setVisibility(View.VISIBLE);
            if("0".equals(userInfobean.getIsapprove())){
                real_tv.setText("未认证");
            }else if("1".equals(userInfobean.getIsapprove())){
                real_tv.setText("已认证");
            }else if("2".equals(userInfobean.getIsapprove())){
                real_tv.setText("未审核");
            }else if("3".equals(userInfobean.getIsapprove())){
                real_tv.setText("不通过");
            }
            Log.d("TAG", "mobile2-->" + userInfobean.getMobile());
            accountTv01.setText(UtilPreference.getStringValue(getActivity(), "name"));
            ImageLoader.getInstance().displayImage(userInfobean.getHeadimg(), userHeadImg);
            //userHeadImg.setImageResource(R.mipmap.icon_03_3x);
            userHeadImg.setOnClickListener(this);
            accountManagerLayout.setOnClickListener(this);
        } else {
            //accountManagerLayout.setOnClickListener(this);
            accountLayout.setOnClickListener(this);

        }
    }


    private void initView() {
        real_tv=findViewById(R.id.real_tv);

        userHeadImg = findViewById(R.id.user_head_img);
        userEditorImg = findViewById(R.id.user_edit_iv);
        accountTv = findViewById(R.id.account_tv);
        accountTv01 = findViewById(R.id.account_tv01);
        accountLayout = findViewById(R.id.account_layout);
        accountManagerTv = findViewById(R.id.account_manager_tv);
        accountMoveTv = findViewById(R.id.account_move_tv);
        accountManagerLayout = findViewById(R.id.account_manager_layout);
        line1 = findViewById(R.id.line1);
        collectRedV = findViewById(R.id.collect_red_v);
        collectNumTv = findViewById(R.id.collect_num_tv);
        collectLayout = findViewById(R.id.collect_layout);
        spentRedV = findViewById(R.id.spent_red_v);
        spentNumTv = findViewById(R.id.spent_num_tv);
        spentLayout = findViewById(R.id.spent_layout);
        dianLayout = findViewById(R.id.dian_layout);
        nearSeeLayout = findViewById(R.id.near_see_layout);
        nearOpenRedV = findViewById(R.id.near_open_red_v);
        openNumTv = findViewById(R.id.open_num_tv);
        openLayout = findViewById(R.id.open_layout);
        aboutLayout = findViewById(R.id.about_layout);
        setLayout = findViewById(R.id.set_layout);
        realImg = findViewById(R.id.real_img);
        realLayout = findViewById(R.id.real_layout);
        feedBackLyout = findViewById(R.id.feedback_layout);
        inviteLayout = findViewById(R.id.invite_layout);
        Log.d("TAG", "mobile1-->" + mobile);

        collectLayout.setOnClickListener(this);
        spentLayout.setOnClickListener(this);
        dianLayout.setOnClickListener(this);
        nearSeeLayout.setOnClickListener(this);
        openLayout.setOnClickListener(this);
        accountLayout.setOnClickListener(this);
        setLayout.setOnClickListener(this);
        feedBackLyout.setOnClickListener(this);
        inviteLayout.setOnClickListener(this);
        aboutLayout.setOnClickListener(this);
        realLayout.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_head_img:
                // toActivity(EditorCenterActivity.class);
                Intent intentEdit = new Intent(getActivity(), EditorCenterActivity.class);
                intentEdit.putExtra("userInfoBean", userInfobean);
                startActivity(intentEdit);
                break;
            case R.id.account_layout:

                toActivity(LoginActivity.class);
                getActivity().finish();
                break;
            case R.id.account_manager_layout:
                toActivity(ChangePWDActivity.class);
                break;
//            case R.id.account_layout:
//            case R.id.account_manager_layout:
//                toActivity(AccountManageActivity.class);
//                break;
            case R.id.invite_layout:
                toActivity(InviteFriendsActivity.class);
                break;
            case R.id.collect_layout: //我的收藏
                toActivity(CollectActivity.class);
                break;
            case R.id.spent_layout://我的消费
                toActivity(SpentActivity.class);
                break;
            case R.id.dian_layout://我的点数
                toActivity(DianActivity.class);
                break;
            case R.id.near_see_layout://最近观看
                toActivity(NearSeeActivity.class);
                break;
            case R.id.open_layout://开播提醒
                toActivity(OpenActivity.class);
                break;
            case R.id.about_layout://关于我们
                toActivity(AboutActivity.class);
                break;
            case R.id.real_layout://实名认证
                toActivity(RealNameActivity.class);
                break;
            case R.id.set_layout://设置
                toActivity(SetActivity.class);
                break;
            case R.id.feedback_layout://意见反馈
                toActivity(FeeBackActivity.class);
                break;
        }
    }


}
