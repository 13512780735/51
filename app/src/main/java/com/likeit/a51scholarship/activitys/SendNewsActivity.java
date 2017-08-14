package com.likeit.a51scholarship.activitys;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.model.ImageBean;
import com.likeit.a51scholarship.utils.Loader;
import com.likeit.a51scholarship.utils.MyActivityManager;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yzs.imageshowpickerview.ImageShowPickerBean;
import com.yzs.imageshowpickerview.ImageShowPickerListener;
import com.yzs.imageshowpickerview.ImageShowPickerView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
    @BindView(R.id.it_picker_view)
    ImageShowPickerView pickerView;
    private List<ImageBean> list;
    private static final int REQUEST_CODE_CHOOSE = 233;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_news);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initTitle("发布资讯");
        topBarRightTv.setText("发布");
        initView();
    }

    private void initView() {
        list = new ArrayList<>();
        pickerView.setImageLoaderInterface(new Loader());
        pickerView.setNewData(list);
        //展示有动画和无动画

        pickerView.setShowAnim(true);
        pickerView.setPickerListener(new ImageShowPickerListener() {
            @Override
            public void addOnClickListener(int remainNum) {
                Matisse.from(SendNewsActivity.this)
                        .choose(MimeType.allOf())
                        .countable(true)
                        .maxSelectable(remainNum + 1)
                        .gridExpectedSize(300)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
             //   Toast.makeText(mContext, "remainNum" + remainNum, Toast.LENGTH_SHORT).show();

//                list.add(new ImageBean("http://pic78.huitu.com/res/20160604/1029007_20160604114552332126_1.jpg"));
            }

            @Override
            public void picOnClickListener(List<ImageShowPickerBean> list, int position, int remainNum) {
                //Toast.makeText(mContext, list.size() + "========" + position + "remainNum" + remainNum, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void delOnClickListener(int position, int remainNum) {
               // Toast.makeText(mContext, "delOnClickListenerremainNum" + remainNum, Toast.LENGTH_SHORT).show();
            }
        });
        pickerView.show();
        AndPermission.with(this)
                .requestCode(300)
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .rationale(rationaleListener)
                .callback(this)
                .start();
    }
        @PermissionYes(300)
        private void getPermissionYes (List < String > grantedPermissions) {
            // Successfully.

        }

        @PermissionNo(300)
        private void getPermissionNo (List < String > deniedPermissions) {
            // Failure.
        }

        /**
         * Rationale支持，这里自定义对话框。
         */
        private RationaleListener rationaleListener = new RationaleListener() {
            @Override
            public void showRequestPermissionRationale(int i, final Rationale rationale) {
                // 自定义对话框。
                AlertDialog.newBuilder(mContext)
                        .setTitle("请求权限")
                        .setMessage("请求权限")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                rationale.resume();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                rationale.cancel();
                            }
                        }).show();
            }
        };

        List<Uri> mSelected;

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
//            mSelected = Matisse.obtainResult(data);
                List<Uri> uriList = Matisse.obtainResult(data);
                if (uriList.size() == 1) {
                    pickerView.addData(new ImageBean(getRealFilePath(mContext, uriList.get(0))));
                } else {
                    List<ImageBean> list = new ArrayList<>();
                    for (Uri uri : uriList) {
                        list.add(new ImageBean(getRealFilePath(mContext, uri)));
                    }
                    pickerView.addData(list);
                }
            }
        }


    public String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
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
}
