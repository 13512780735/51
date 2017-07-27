package com.likeit.a51scholarship.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.utils.MyActivityManager;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_news);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initTitle("发布资讯");
        topBarRightTv.setText("发布");
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
        if(TextUtils.isEmpty(title)||TextUtils.isEmpty(content)){
            showToast("标题或内容不能为空!");
            return;
        }
    }
}
