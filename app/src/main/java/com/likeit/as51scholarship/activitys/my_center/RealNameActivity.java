package com.likeit.as51scholarship.activitys.my_center;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.Container;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.utils.PhotoUtils;
import com.likeit.as51scholarship.utils.StringUtil;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.utils.ToastUtils;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RealNameActivity extends Container {
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.commit_btn)
    TextView btCommit;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_ID)
    EditText etId;
    @BindView(R.id.rl_id01)
    RelativeLayout rl01;
    @BindView(R.id.rl_id02)
    RelativeLayout rl02;
    @BindView(R.id.iv_id01)
    ImageView iv01;
    @BindView(R.id.iv_id02)
    ImageView iv02;
    @BindView(R.id.ll_id)
    LinearLayout ll_id;
    private PopupWindow mPopupWindow;
    private View mpopview;
    private String stats;
    private String pathId;
    private String pathId02;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvHeader.setText("实名认证");
    }

    @OnClick({R.id.backBtn,R.id.rl_id01,R.id.rl_id02,R.id.commit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.rl_id01:
                 stats="1";
                selectAvatar();
                break;
            case R.id.rl_id02:
                 stats="2";
                selectAvatar();
                break;
            case R.id.commit_btn:
                commit();
                break;
        }
    }

    private void commit() {
        String name=etName.getText().toString();
        String id_num=etId.getText().toString();
        String imageId=pathId+","+pathId02;
        if(StringUtil.isBlank(name)){
            ToastUtil.showS(mContext,"姓名不能为空");
            return;
        }
        if(StringUtil.isBlank(id_num)){
            ToastUtil.showS(mContext,"身份证号不能为空");
            return;
        }
        if(StringUtil.isBlank(pathId)){
            ToastUtil.showS(mContext,"身份证正面照不能为空");
            return;
        }  if(StringUtil.isBlank(pathId02)){
            ToastUtil.showS(mContext,"身份证反面照不能为空");
            return;
        }
        String url=AppConfig.LIKEIT_APPROVE;
        RequestParams params=new RequestParams();
        params.put("ukey",ukey);
        params.put("name",name);
        params.put("id_num",id_num);
        params.put("image",imageId);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG",response);
                try {
                    JSONObject object=new JSONObject(response);
                    String code=object.optString("code");
                    String message=object.optString("message");
                    if("1".equals(code)){
                        ToastUtil.showS(mContext,message);
                        onBackPressed();
                    }else{
                        ToastUtil.showS(mContext,message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
                showErrorMsg("网络异常请重新再试！");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                disShowProgress();
            }
        });

    }

    @SuppressLint("InflateParams")
    private void selectAvatar() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mpopview = inflater.inflate(R.layout.layout_choose_photo, null);
        mPopupWindow = new PopupWindow(mpopview, ll_id.getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.mid_filter_bg));

     //   mPopupWindow.showAsDropDown(ll_id, 0, 20, Gravity.CENTER);
        mPopupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.layout_choose_photo, null), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        mPopupWindow.setOutsideTouchable(false);
        //mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
        mPopupWindow.setAnimationStyle(R.style.AnimBottom);
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setTouchable(true); // 设置popupwindow可点击
        mPopupWindow.setOutsideTouchable(true); // 设置popupwindow外部可点击
        mPopupWindow.setFocusable(true); // 获取焦点
        mPopupWindow.update();

        Button mbuttonTakePhoto = (Button) mpopview
                .findViewById(R.id.button_take_photo);
        Button mbuttonChoicePhoto = (Button) mpopview
                .findViewById(R.id.button_choice_photo);
        Button mbuttonChoicecannce = (Button) mpopview
                .findViewById(R.id.button_choice_cancer);

        // 相册上传
        mbuttonChoicePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                autoObtainStoragePermission();
            }
        });

        // 拍照上传
        mbuttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                autoObtainCameraPermission();
            }
        });

        mbuttonChoicecannce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 如果点击了popupwindow的外部，popupwindow也会消失
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mPopupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ToastUtils.showShort(this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(mContext, "com.likeit.as51scholarship.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.showShort(this, "设备没有SD卡！");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(mContext, "com.likeit.as51scholarship.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showShort(this, "设备没有SD卡！");
                    }
                } else {

                    ToastUtils.showShort(this, "请允许打开相机！！");
                }
                break;


            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {

                    ToastUtils.showShort(this, "请允许打操作SDCard！！");
                }
                break;
        }
    }

    private static final int output_X = 480;
    private static final int output_Y = 480;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(this, "com.likeit.as51scholarship.fileprovider", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    } else {
                        ToastUtils.showShort(this, "设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    Log.d("TAG555",cropImageUri.toString());
                    if (bitmap != null) {
                        showImages(bitmap);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] bytes = baos.toByteArray();
                        String base64Token = Base64.encodeToString(bytes, Base64.DEFAULT);//  编码后
                        Log.d("TAG666",base64Token);
                        upload(base64Token);
                    }
                    break;
            }
        }
    }


    /**
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }

    }

    private void showImages(Bitmap bitmap) {
        if("1".equals(stats)){
                    rl01.setVisibility(View.GONE);
                    iv01.setVisibility(View.VISIBLE);
                    iv01.setImageBitmap(bitmap);
                }else{
                    rl02.setVisibility(View.GONE);
                    iv02.setVisibility(View.VISIBLE);
                    iv02.setImageBitmap(bitmap);
                }
        //ivUserAvatar.setImageBitmap(bitmap);
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private void upload(String base64Token) {
            String url= AppConfig.LIKEIT_UPLOAD_FORBASE64;
        RequestParams params=new RequestParams();
        params.put("ukey",ukey);
        params.put("pic",base64Token);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG",response);
                try {
                    JSONObject obj=new JSONObject(response);
                    String code=obj.optString("code");
                    String messgae=obj.optString("message");
                    if("1".equals(code)){
                        JSONObject data=obj.optJSONObject("data");
                        if("1".equals(stats)){
                            pathId=data.optString("id");
                        }else{
                            pathId02=data.optString("id");
                        }
                       // onBackPressed();
                        Log.d("TAG","pathId-->"+pathId);
                        Log.d("TAG","pathId02-->"+pathId02);
                    }else{
                        ToastUtil.showS(mContext,messgae);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failed(Throwable e) {

            }
        });
    }

//    @SuppressLint("SdCardPath")
//    static boolean saveBitmap2file(Bitmap bmp, String filename) {
//        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
//        int quality = 100;
//        OutputStream stream = null;
//        try {
//            stream = new FileOutputStream("/sdcard/" + filename);
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return bmp.compress(format, quality, stream);
//    }
}
