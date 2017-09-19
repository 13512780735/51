package com.likeit.as51scholarship.dialog;
import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;


public class KefuDialog extends Dialog {
    @BindView(R.id.touxian_img)
    ImageView touxianImg;
    @BindView(R.id.t1)
    TextView t1;
    @BindView(R.id.t2)
    TextView t2;
    @BindView(R.id.t3)
    TextView t3;
    @BindView(R.id.kefu_qq)
    LinearLayout kefuQq;
    @BindView(R.id.kefu_phone)
    LinearLayout kefuPhone;
    @BindView(R.id.close_img)
    ImageView closeImg;

    public KefuDialog(@NonNull Context context) {
        super(context, R.style.dialogStyle);
        setContentView(R.layout.dialog_kefu_service);
        ButterKnife.bind(this, getWindow().getDecorView());


    }


    @OnClick({R.id.kefu_phone, R.id.close_img, R.id.kefu_qq})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.kefu_qq:
                break;
            case R.id.kefu_phone:
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + "0760-88288601");
                intent.setData(data);
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(),"请授予拨打电话权限",Toast.LENGTH_SHORT).show();
                    List<PermissionItem> permissions = new ArrayList<PermissionItem>();
                    permissions.add(new PermissionItem(Manifest.permission.CALL_PHONE, "Call Phone", R.drawable.permission_ic_phone));
                    HiPermission.create(getContext())
                            .permissions(permissions)
                            .msg("是否授予拨打电话权限")
                            .animStyle(R.style.PermissionAnimModal)
//                        .style(R.style.CusStyle)
                            .checkMutiPermission(new PermissionCallback() {
                                @Override
                                public void onClose() {
                                    Log.i(TAG, "onClose");
                                    ToastUtil.showS(getContext(),"权限被拒绝");
                                }

                                @Override
                                public void onFinish() {
                                    ToastUtil.showS(getContext(),"权限已被开启");
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
                getContext().startActivity(intent);
                break;
            case R.id.close_img:
                dismiss();
                break;
        }
    }
}
