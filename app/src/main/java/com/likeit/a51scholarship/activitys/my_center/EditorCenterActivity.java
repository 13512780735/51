package com.likeit.a51scholarship.activitys.my_center;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.Container;
import com.likeit.a51scholarship.activitys.login.GuideActivity;
import com.likeit.a51scholarship.chat.message.widget.DemoHelper;
import com.likeit.a51scholarship.model.UserInfoBean;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.view.CircleImageView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EditorCenterActivity extends Container {
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_birthday_arrow)
    ImageView rlBirthday;
    @BindView(R.id.address_et)
    TextView tvAddress;
    @BindView(R.id.birthday_et)
    TextView tvBirthday;
    @BindView(R.id.editorCenter_iv_logout)
    CircleImageView ivLogout;
    @BindView(R.id.username_et)
    EditText edName;
    @BindView(R.id.phone_et)
    EditText edPhone;
    @BindView(R.id.education_et)
    EditText edEducation;
    final int DATE_DIALOG = 1;
    int mYear, mMonth, mDay;
    private UserInfoBean userInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_center);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        userInfoBean = (UserInfoBean) getIntent().getSerializableExtra("userInfoBean");
        initView();
    }

    private void initView() {
        tvHeader.setText("个人资料");
        tvRight.setText("保存");
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        edName.setText(userInfoBean.getNickname());
        edPhone.setText(userInfoBean.getMobile());
        tvBirthday.setText(userInfoBean.getBirthday());
        tvAddress.setText(userInfoBean.getPos_province()+userInfoBean.getCountry());
    }

    @OnClick({R.id.backBtn, R.id.iv_birthday_arrow, R.id.editorCenter_iv_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.iv_birthday_arrow:
                showDialog(DATE_DIALOG);
                break;
            case R.id.editorCenter_iv_logout:
                logout();
                //  EMClient.getInstance().logout(true);
                Log.d("TAG", "EM成功退出");
                // MyActivityManager.getInstance().logout(mContext);
                //MyActivityManager.getInstance().appExit(mContext);
//                toActivityFinish(GuideActivity.class);
//                MyActivityManager.getInstance().finishAllActivity();
                break;
        }
    }

    private void logout() {
        DemoHelper.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        // show login screen
                        toActivityFinish(GuideActivity.class);
                        MyActivityManager.getInstance().finishAllActivity();

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

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        tvBirthday.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };
}
