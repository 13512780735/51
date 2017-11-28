package com.likeit.as51scholarship.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.utils.MyActivityManager;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.view.CircleImageView;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnswerListActivity extends Container {

    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.answers_issue_avatar)
    CircleImageView img;
    @BindView(R.id.answers_issue_name)
    TextView name;
    @BindView(R.id.answers_issue_time)
    TextView time;
    @BindView(R.id.answers_issue_live01)
    TextView live01;
    @BindView(R.id.answers_issue_live02)
    TextView country01;
    @BindView(R.id.answers_issue_details)
    TextView details;
    @BindView(R.id.tv_room02_length)
    TextView tvRoomLength;
    private String id, author, headimg, content, category, country, interval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_list);
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        author = intent.getStringExtra("author");
        headimg = intent.getStringExtra("headimg");
        content = intent.getStringExtra("content");
        category = intent.getStringExtra("category");
        country = intent.getStringExtra("country");
        interval = intent.getStringExtra("interval");
        initView();

    }

    private void initView() {
        tvRight.setText("提交");
        tvHeader.setText("回答");
        ImageLoader.getInstance().displayImage(headimg,img);
        name.setText(author);
        time.setText(interval);
        live01.setText(category);
        country01.setText(country);
        details.setText(content);
        editText.addTextChangedListener(mTextWatcher);
    }
    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart ;
        private int editEnd ;
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
//          mTextView.setText(s);//将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            editStart = editText.getSelectionStart();
            editEnd = editText.getSelectionEnd();
            tvRoomLength.setText("" + temp.length());
            if (temp.length() > 250) {
                Toast.makeText(mContext,
                        "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT)
                        .show();
                s.delete(editStart-1, editEnd);
                int tempSelection = editStart;
                editText.setText(s);
                editText.setSelection(tempSelection);
            }
        }
    };
    @OnClick({R.id.backBtn,R.id.tv_right})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.tv_right:
                initSend();
                break;
        }
    }

    private void initSend() {
        String content1=editText.getText().toString();
        if (TextUtils.isEmpty(content1)) {
            ToastUtil.showS(mContext, "内容不能为空!");
            return;
        }
        String url= AppConfig.LIKEIT_ANSWER_ADDANSWER;
        RequestParams params=new RequestParams();
        params.put("ukey",ukey);
        params.put("content",content1);
        params.put("qid",id);
        showProgress("Loading...");
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                disShowProgress();
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("1".equals(code)) {
                        ToastUtil.showS(mContext, message);
                        onBackPressed();
                    } else {
                        ToastUtil.showS(mContext, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
            }
        });

    }
}
