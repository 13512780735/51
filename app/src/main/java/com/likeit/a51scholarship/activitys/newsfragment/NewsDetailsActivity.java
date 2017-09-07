package com.likeit.a51scholarship.activitys.newsfragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.Container;
import com.likeit.a51scholarship.activitys.SendNewsActivity;
import com.likeit.a51scholarship.configs.AppConfig;
import com.likeit.a51scholarship.http.HttpUtil;
import com.likeit.a51scholarship.utils.ToastUtil;
import com.likeit.a51scholarship.utils.richtext.RichText;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        id = intent.getStringExtra("id");
        //详情数据请求
        initData();
        showProgress("Loading...");

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
        tvHeader.setText("资讯详情页");
        ivRight.setImageResource(R.mipmap.icon_edit);
        ivLeft.setImageResource(R.mipmap.icon_back);
        tvTitle.setText(title);
        tvSource.setText(source);
        tvTime.setText("时间："+time);
        richDetails.setRichText(content);
        tvPraise.setText(view);
        tvComment.setText(comment);
    }
    @OnClick({R.id.iv_header_right,R.id.iv_header_left,R.id.new_details_share})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_header_left:
                onBackPressed();
                break;
            case R.id.iv_header_right:
                toActivity(SendNewsActivity.class);
                Intent intent=new Intent(mContext,SendNewsActivity.class);
                intent.putExtra("uId","1");
                startActivity(intent);
                break;
            case R.id.new_details_share:
                newsShare();
                break;

        }

    }

    private void newsShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        //oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }
}
