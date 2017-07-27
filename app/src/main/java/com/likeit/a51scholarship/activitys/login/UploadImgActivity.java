package com.likeit.a51scholarship.activitys.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.pk4pk.baseappmoudle.utils.FileUtil;

import java.io.File;

import com.likeit.a51scholarship.activitys.Container;
import com.likeit.a51scholarship.utils.MyActivityManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.utils.Logger;

public class UploadImgActivity extends Container {
    private static final int SELECT_ORIGINAL_PIC = 101;
    @BindView(R.id.head_img)
    ImageView headImg;
    @BindView(R.id.backBtn)
    Button btBack;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_right)
    TextView tvRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_img);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        tvHeader.setText("上传头像");
        tvRight.setText("跳过");
    }

    @OnClick({R.id.head_img, R.id.next_btn, R.id.tv_right, R.id.backBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.head_img:
//                selectFromGallery();
                openImgSelect();
                break;
            case R.id.tv_right:
            case R.id.next_btn:
                toActivity(PerfectFirstActivity.class);
                break;
        }
    }

    private void openImgSelect() {
        RxGalleryFinal
                .with(this)
                .image()
                .radio()
                .crop()
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                        //图片选择结果
                        String cropPath = imageRadioResultEvent.getResult().getCropPath();
                        Logger.d("cropPath :" + cropPath);
                        uploadFileBase64(cropPath);
                    }
                })
                .openGallery();
    }




    private void uploadFileBase64(String filePath) {
        if (filePath == null || TextUtils.isEmpty(filePath)) {
            showToast("圖片不存在");
            return;
        }
        Logger.d("filePath  :" + filePath);
        File file = new File(filePath);
        if (!file.exists()) {
            showToast("圖片不存在");
            return;
        }

        try {
            String base64Token = Base64.encodeToString(FileUtil.getFileToByte(file), Base64.DEFAULT);//  编码后
//            String  base64Token = Base64.encodeToString(bytes, Base64.DEFAULT);//  编码后
            Log.d("TAG","base64Token-->>"+base64Token);
//            Logger.d("base64Token  start");
//            Logger.d("base64Token  :" + base64Token);
//            Logger.d("base64Token  end");
//            HttpMethods.getInstance().uploadFileBase64(new MySubscriber<UploadImgEntity>(this) {
//
//                @Override
//                public void onHttpCompleted(HttpResult<UploadImgEntity> httpResult) {
//                    if (httpResult.isStatus()) {
//                        showToast("上傳成功");
//                        Glide.with(context).load(MyApiService.IMG_BASE_URL2+ httpResult.getData().getHeadimg()).into(headImg);
//                    } else {
//                        showToast(httpResult.getMsg());
//                    }
//                }
//
//                @Override
//                public void onHttpError(Throwable e) {
//
//                }
//            }, ukey, base64Token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
