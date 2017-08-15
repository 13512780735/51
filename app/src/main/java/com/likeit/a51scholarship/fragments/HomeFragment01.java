package com.likeit.a51scholarship.fragments;


import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.login.LoginActivity;
import com.likeit.a51scholarship.activitys.my_center.AboutActivity;
import com.likeit.a51scholarship.activitys.my_center.CollectActivity;
import com.likeit.a51scholarship.activitys.my_center.DianActivity;
import com.likeit.a51scholarship.activitys.my_center.EditorCenterActivity;
import com.likeit.a51scholarship.activitys.my_center.FeeBackActivity;
import com.likeit.a51scholarship.activitys.my_center.InviteFriendsActivity;
import com.likeit.a51scholarship.activitys.my_center.NearSeeActivity;
import com.likeit.a51scholarship.activitys.my_center.OpenActivity;
import com.likeit.a51scholarship.activitys.my_center.SetActivity;
import com.likeit.a51scholarship.activitys.my_center.SpentActivity;
import com.likeit.a51scholarship.activitys.my_center.UserInfoActivity;
import com.likeit.a51scholarship.event.MainMessageEvent;
import com.likeit.a51scholarship.utils.UtilPreference;

import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment01 extends MyBaseFragment implements View.OnClickListener {
    private ImageView userHeadImg;
    private TextView accountTv;
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
    private String isLogin;


    @Override
    protected int setContentView() {
        return R.layout.fragment_home_fragment01;
    }

    @Override
    protected void lazyLoad() {
       isLogin= UtilPreference.getStringValue(getActivity(),"isLogin");
        initView();
    }


    private void initView() {
        userHeadImg = findViewById(R.id.user_head_img);
        accountTv = findViewById(R.id.account_tv);
        accountLayout = findViewById(R.id.account_layout);
        accountManagerTv = findViewById(R.id.account_manager_tv);
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
        goAccountImg = findViewById(R.id.go_account_img);
        feedBackLyout = findViewById(R.id.feedback_layout);
        inviteLayout = findViewById(R.id.invite_layout);
        userHeadImg.setOnClickListener(this);
        accountLayout.setOnClickListener(this);
        accountManagerLayout.setOnClickListener(this);
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
                toActivity(EditorCenterActivity.class);
                break;
            case R.id.account_layout:
            case R.id.account_manager_layout:
                if (!TextUtils.isEmpty(ukey)) {
                    toActivity(UserInfoActivity.class);
                } else {
                    toActivity(LoginActivity.class);
                    getActivity().finish();
                }
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
            case R.id.set_layout://设置
                toActivity(SetActivity.class);
                break;
            case R.id.feedback_layout://意见反馈
                toActivity(FeeBackActivity.class);
                break;
        }
    }


}
