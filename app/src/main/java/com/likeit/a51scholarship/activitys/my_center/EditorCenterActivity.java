package com.likeit.a51scholarship.activitys.my_center;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.Container;
import com.likeit.a51scholarship.activitys.login.GuideActivity;
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
    @BindView(R.id.birthday_et)
    TextView tvBirthday;
    @BindView(R.id.editorCenter_iv_logout)
    CircleImageView ivLogout;
    final int DATE_DIALOG = 1;
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_center);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvHeader.setText("个人资料");
        tvRight.setText("保存");
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

    @OnClick({R.id.backBtn, R.id.iv_birthday_arrow,R.id.editorCenter_iv_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.iv_birthday_arrow:
                showDialog(DATE_DIALOG);
                break;
            case R.id.editorCenter_iv_logout:
                EMClient.getInstance().logout(true);
                Log.d("TAG","EM成功退出");
                // MyActivityManager.getInstance().logout(mContext);
                //MyActivityManager.getInstance().appExit(mContext);
                toActivityFinish(GuideActivity.class);
                break;
        }
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
