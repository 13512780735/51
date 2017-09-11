package com.likeit.a51scholarship.activitys.my_center;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.Container;
import com.likeit.a51scholarship.configs.AppConfig;
import com.likeit.a51scholarship.http.HttpUtil;
import com.likeit.a51scholarship.utils.BitmapOption;
import com.likeit.a51scholarship.utils.StringUtil;
import com.likeit.a51scholarship.utils.ToastUtil;
import com.loopj.android.http.RequestParams;
import com.pk4pk.baseappmoudle.utils.FileUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
            }

            @Override
            public void failed(Throwable e) {

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
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
                startActivityForResult(i, 2);
            }
        });

        // 拍照上传
        mbuttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
                @SuppressWarnings("unused")
                Uri photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                startActivityForResult(intent, 1);
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
    @SuppressLint("SdCardPath")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) { // 拍照
                Bundle extras = data.getExtras();
                Bitmap photoBit = (Bitmap) extras.get("data");
                Bitmap option = BitmapOption.bitmapOption(photoBit, 5);
                if("1".equals(stats)){
                    rl01.setVisibility(View.GONE);
                    iv01.setVisibility(View.VISIBLE);
                    iv01.setImageBitmap(option);
                }else{
                    rl02.setVisibility(View.GONE);
                    iv02.setVisibility(View.VISIBLE);
                    iv02.setImageBitmap(option);
                }
                Log.d("TAG",data.toString());
                saveBitmap2file(option, "0001.jpg");
                final File file = new File("/sdcard/" + "0001.jpg");
                try {
                    String base64Token = Base64.encodeToString(FileUtil.getFileToByte(file), Base64.DEFAULT);//  编码后
                    upload(base64Token);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("TAG", "file333333333333333   " + file.getName());
                // 开始联网上传的操作

            } else if (requestCode == 2) { // 相册
                try {
                    Uri uri = data.getData();
                    String[] pojo = { MediaStore.Images.Media.DATA };
                    @SuppressWarnings("deprecation")
                    Cursor cursor = managedQuery(uri, pojo, null, null, null);
                    if (cursor != null) {
                        ContentResolver cr = this.getContentResolver();
                        int colunm_index = cursor
                                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String path = cursor.getString(colunm_index);
                        final File file = new File(path);
                        Bitmap bitmap = BitmapFactory.decodeStream(cr
                                .openInputStream(uri));
                        Bitmap option = BitmapOption.bitmapOption(bitmap, 5);
                        if("1".equals(stats)){
                            rl01.setVisibility(View.GONE);
                            iv01.setVisibility(View.VISIBLE);
                            iv01.setImageBitmap(option);
                        }else{
                            rl02.setVisibility(View.GONE);
                            iv02.setVisibility(View.VISIBLE);
                            iv02.setImageBitmap(option);
                        }
                      //  iv01.setImageBitmap(option);// 设置为头像的背景
                        Log.e("TAG", "fiels11111  " + file.getName());
                        try {
                            String base64Token = Base64.encodeToString(FileUtil.getFileToByte(file), Base64.DEFAULT);//  编码后
                            upload(base64Token);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // 开始联网上传的操作

                    }
                } catch (Exception e) {

                }
            }
        }

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
                        onBackPressed();
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

    @SuppressLint("SdCardPath")
    static boolean saveBitmap2file(Bitmap bmp, String filename) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream("/sdcard/" + filename);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bmp.compress(format, quality, stream);
    }
}
