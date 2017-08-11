package com.likeit.a51scholarship.activitys.newsfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.Container;
import com.likeit.a51scholarship.activitys.SendNewsActivity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvHeader.setText("资讯详情页");
        ivRight.setImageResource(R.mipmap.icon_edit);
        ivLeft.setImageResource(R.mipmap.icon_back);
    }
    @OnClick({R.id.iv_header_right,R.id.iv_header_left,R.id.new_details_share})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_header_left:
                onBackPressed();
                break;
            case R.id.iv_header_right:
                toActivity(SendNewsActivity.class);
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
