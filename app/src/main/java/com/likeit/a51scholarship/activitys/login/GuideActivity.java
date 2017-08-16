package com.likeit.a51scholarship.activitys.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.MainActivity;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.utils.ToastUtil;
import com.likeit.a51scholarship.utils.UtilPreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;


public class GuideActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.guide_tvbrow)
    TextView tvBrow;
    @BindView(R.id.guide_tvLogin)
    TextView tvLogin;
    @BindView(R.id.guide_tvRegister)
    TextView tvRegister;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        MyActivityManager.getInstance().addActivity(this);
        mContext = this;
        ButterKnife.bind(this);
       openPermission();
        initView();
    }
    private void openPermission() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE + Manifest.permission.CAMERA+Manifest.permission.WRITE_EXTERNAL_STORAGE
        +Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        //Toast.makeText(mContext,"请授予下面权限",Toast.LENGTH_SHORT).show();
        List<PermissionItem> permissions = new ArrayList<PermissionItem>();
        permissions.add(new PermissionItem(Manifest.permission.CALL_PHONE, "电话", R.drawable.permission_ic_phone));
        permissions.add(new PermissionItem(Manifest.permission.CAMERA, "照相", R.drawable.permission_ic_camera));
        permissions.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE , "储存空间", R.drawable.permission_ic_storage));
       // permissions.add(new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, "Camera", R.drawable.permission_ic_storage));
        HiPermission.create(mContext)
                .permissions(permissions)
                .msg("为了您正常使用51奖学金应用，需要以下权限")
                .animStyle(R.style.PermissionAnimModal)
//                        .style(R.style.CusStyle)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                        Log.i(TAG, "onClose");
                        ToastUtil.showS(mContext,"权限被拒绝");
                    }

                    @Override
                    public void onFinish() {
                        //ToastUtil.showS(mContext,"权限已被开启");
                    }

                    @Override
                    public void onDeny(String permission, int position) {
                        Log.i(TAG, "onDeny");
                    }

                    @Override
                    public void onGuarantee(String permission, int position) {
                        Log.i(TAG, "onGuarantee");
                    }
                });
        return;
    }
    }
    private void initView() {
        tvBrow.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guide_tvbrow:
                Intent intent01 = new Intent(mContext, MainActivity.class);
                UtilPreference.saveString(mContext, "isLogin", "1");
                startActivity(intent01);
                finish();
                break;
            case R.id.guide_tvLogin:
                Intent intent02 = new Intent(mContext, LoginActivity.class);
                UtilPreference.saveString(mContext, "isLogin", "0");
                startActivity(intent02);
                finish();
                break;
            case R.id.guide_tvRegister:
                Intent intent03 = new Intent(mContext, RegisterActivity.class);

                startActivity(intent03);
                finish();
                break;
        }
    }
}
