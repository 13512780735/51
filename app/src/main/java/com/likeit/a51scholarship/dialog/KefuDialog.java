package com.likeit.a51scholarship.dialog;
import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.likeit.a51scholarship.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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

//        findViewById(R.id.kefu_phone).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                Uri data = Uri.parse("tel:" + "0760-88288601");
//                intent.setData(data);
//                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getContext(),"请授予拨打电话权限",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                getContext().startActivity(intent);
//            }
//        });
//        findViewById(R.id.close_img).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });

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
