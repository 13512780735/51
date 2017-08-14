package com.likeit.a51scholarship.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.imageutil.custom.CommandPhotoUtil;
import com.likeit.a51scholarship.imageutil.custom.CustomScrollGridView;
import com.likeit.a51scholarship.imageutil.custom.GridAdapter;
import com.likeit.a51scholarship.imageutil.custom.PhotoSystemOrShoot;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.likeit.a51scholarship.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;


public class SendNewsActivity extends Container {

    @BindView(R.id.tv_header)
    TextView topBarTitle;
    @BindView(R.id.tv_right)
    TextView topBarRightTv;
    @BindView(R.id.title_et)
    EditText titleEt;
    @BindView(R.id.content_et)
    EditText contentEt;
    @BindView(R.id.imgs_layout)
    LinearLayout imgsLayout;
    @BindView(R.id.news_type_layout)
    LinearLayout newsTypeLayout;
    @BindView(R.id.photo_im)
    ImageView photoIm;
    @BindView(R.id.camear_im)
    ImageView camearIm;
    @BindView(R.id.label_im)
    ImageView labelIm;
    //图片添加
    @BindView(R.id.gv_all_photo)
    CustomScrollGridView mGridView;
    /**
     * GridView适配器
     */
    private GridAdapter gridAdapter;

    /**
     * 管理图片操作
     */
    private CommandPhotoUtil commandPhoto;

    /**
     * 选择图片来源
     */
    private PhotoSystemOrShoot selectPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_news);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initTitle("发布资讯");
        topBarRightTv.setText("发布");
        addPlus();
    }

    /**
     * 实例化组件
     */
    private void addPlus() {
        gridAdapter = new GridAdapter(mContext, 4);
        mGridView.setAdapter(gridAdapter);

        // 选择图片获取途径
        selectPhoto = new PhotoSystemOrShoot(mContext) {
            @Override
            public void onStartActivityForResult(Intent intent, int requestCode) {
                startActivityForResult(intent, requestCode);
            }
        };
        commandPhoto = new CommandPhotoUtil(mContext, mGridView, gridAdapter, selectPhoto);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(mContext,"请授予打开相机的权限",Toast.LENGTH_SHORT).show();
            List<PermissionItem> permissions = new ArrayList<PermissionItem>();
            permissions.add(new PermissionItem(Manifest.permission.CAMERA, "Camera", R.drawable.permission_ic_camera));
            HiPermission.create(mContext)
                    .permissions(permissions)
                    .msg("是否授予打开相机的权限")
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
                            ToastUtil.showS(mContext,"权限已被开启");
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


    @OnClick({R.id.backBtn, R.id.tv_right, R.id.photo_im, R.id.camear_im, R.id.label_im})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                finish();
                break;
            case R.id.tv_right:
                send();
                break;
            case R.id.photo_im:
                initImg();
                photoIm.setImageResource(R.mipmap.btn_icon_photo_selected);
                break;
            case R.id.camear_im:
                initImg();
                camearIm.setImageResource(R.mipmap.btn_icon_photographs_selected);
                break;
            case R.id.label_im:
                initImg();
                labelIm.setImageResource(R.mipmap.btn_icon_label_selected);
                break;
        }
    }

    private void initImg() {
        photoIm.setImageResource(R.mipmap.btn_icon_photo_default);
        camearIm.setImageResource(R.mipmap.btn_icon_photographs_default);
        labelIm.setImageResource(R.mipmap.btn_icon_label_default);

    }

    private void send() {
        String title = titleEt.getText().toString();
        String content = contentEt.getText().toString();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            showToast("标题或内容不能为空!");
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 获取照片返回
        if (selectPhoto != null) {
            String photoPath = selectPhoto.getPhotoResultPath(requestCode, resultCode, data);
            if (!TextUtils.isEmpty(photoPath)) {
                commandPhoto.showGridPhoto(photoPath);
            }
        }
    }
}
