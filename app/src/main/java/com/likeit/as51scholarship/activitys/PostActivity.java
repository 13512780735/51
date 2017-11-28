package com.likeit.as51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.utils.MyActivityManager;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.utils.UtilPreference;
import com.likeit.as51scholarship.utils.richtext.RichText;
import com.likeit.as51scholarship.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostActivity extends Container {

    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.circle_essay_name)
    TextView tvEssayName;
    @BindView(R.id.circle_essay_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.circle_essay_time)
    TextView tvEssayTime;
    @BindView(R.id.tv_add_friend)
    TextView tvAdd;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.circle_essay_content)
    RichText tvContent;
    private String id, title, content, post_time, nickname, headimg, easemob_id, isfriend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        post_time = intent.getStringExtra("post_time");
        nickname = intent.getStringExtra("nickname");
        headimg = intent.getStringExtra("headimg");
        easemob_id = intent.getStringExtra("easemob_id");
        isfriend = intent.getStringExtra("isfriend");
        tvHeader.setText("帖子详情页");
        initView();
    }

    private void initView() {
        tvEssayName.setText(nickname);
        ImageLoader.getInstance().displayImage(headimg, ivAvatar);
        tvTitle.setText(title);
        tvEssayTime.setText(post_time);
        tvContent.setRichText(content);
        if ("1".equals(isfriend)) {
            tvAdd.setText("已添加");
        } else {
            tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (easemob_id.equals(UtilPreference.getStringValue(mContext, "easemob_id"))) {
                        ToastUtil.showS(mContext, "不能添加自己");
                    } else {
                        new Thread(new Runnable() {
                            public void run() {

                                try {
                                    String s = getResources().getString(R.string.Add_a_friend);
                                    EMClient.getInstance().contactManager().addContact(easemob_id, s);
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            //progressDialog.dismiss();
                                            String s1 = getResources().getString(R.string.send_successful);
                                            Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                } catch (final Exception e) {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            // progressDialog.dismiss();
                                            String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                                            Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                        }).start();
                    }
                }
            });

        }
    }

    @OnClick(R.id.backBtn)
    public void Onclick(View v) {
        onBackPressed();
    }
}

