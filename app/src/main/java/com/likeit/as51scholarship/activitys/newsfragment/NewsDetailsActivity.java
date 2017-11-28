package com.likeit.as51scholarship.activitys.newsfragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.Container;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.utils.DialogUtils;
import com.likeit.as51scholarship.utils.ToastUtil;
import com.likeit.as51scholarship.utils.richtext.RichText;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NewsDetailsActivity extends Container {
    @BindView(R.id.iv_header_left)
    ImageView ivLeft;
    @BindView(R.id.iv_header_right)
    ImageView ivRight;
    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.new_details_title)
    TextView tvTitle;
    @BindView(R.id.new_details_source01)
    TextView tvSource;
    @BindView(R.id.new_details_time)
    TextView tvTime;
    @BindView(R.id.new_details_praise)
    TextView tvPraise;
    @BindView(R.id.new_details_comment)
    TextView tvComment;
    @BindView(R.id.new_details_details)
    RichText richDetails;
    private String id;
    private String title,source,time,content,comment,view;
    private String logo;
    private String title1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        id = intent.getStringExtra("id");
        logo = intent.getStringExtra("logo");
        title1 = intent.getStringExtra("title");
        //详情数据请求
        initData();
        showProgress("Loading...");
        tvHeader.setText("资讯详情页");
        ivRight.setImageResource(R.mipmap.icon_share);
        ivLeft.setImageResource(R.mipmap.icon_back);
    }

    private void initData() {
        String url= AppConfig.LIKEIT_NEW_GETDETAILS;
        RequestParams params=new RequestParams();
        params.put("ukey",ukey);
        params.put("id",id);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG8885",response);
                disShowProgress();
                try {
                    JSONObject obj=new JSONObject(response);
                    String code=obj.optString("code");
                    String message=obj.optString("message");
                    if("1".equals(code)){
                        JSONObject object=obj.optJSONObject("data");
                        title=object.optString("title");//标题
                        source=object.optString("author");//作者
                        time=object.optString("interval");//发布时间间隔
                        content=object.optString("content");//内容
                        comment=object.optString("comment");//评论数
                        view=object.optString("view");//阅读数
                        initView();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable e) {
                disShowProgress();
                ToastUtil.showS(mContext,"网络异常！");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                disShowProgress();
            }
        });
    }

    private void initView() {
        tvTitle.setText(title);
        tvSource.setText(source);
        tvTime.setText("时间："+time);
        richDetails.setRichText(content);
        tvPraise.setText(view);
        tvComment.setText(comment);

    }
    @OnClick({R.id.iv_header_right,R.id.iv_header_left})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_header_left:
                onBackPressed();
                break;
            case R.id.iv_header_right:
                newsShare();
                break;

        }

    }

    private void newsShare() {
        String url = "http://liuxueapp.wbteam.cn/51SchoolShare/shareNew.html?id=";
        String link = url + id;
        DialogUtils.showShare(NewsDetailsActivity.this,logo, title1, "", link);
    }
}
