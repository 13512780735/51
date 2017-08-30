package com.likeit.a51scholarship.activitys.my_center;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.likeit.a51scholarship.R;
import com.likeit.a51scholarship.activitys.Container;
import com.likeit.a51scholarship.configs.AppConfig;
import com.likeit.a51scholarship.http.HttpUtil;
import com.loopj.android.http.RequestParams;

import java.net.URL;

public class InviteFriendsActivity extends Container {

    private String sid;
    private TextView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
        sid="4";
        //initData();
        initView();
    }

    private void initView() {
        webview=(TextView) findViewById(R.id.webview);
        String url1=" <p style=\"white-space: normal;\">美国德州大学（共十五分校） 阿灵顿分校商学所在美国高等教育发展己有一百年以上的历史，是一所综合教学及研究的高等学府以商业教育最为出名。UTA位于德州Dallas的心脏位置，为美国经济成功发展的大城之一。UTA学生每年入学人数超过2万名，分别来自全美50州及90个不同国家。UTA是全美中西部前50名之优良大学，并且是美国德州大学系统第二大之分校。 而UTA之商学院更在全美排名前80名，提供许多专业训练课程及学位。UTA 为有效训练中高级经理人迈向国际化的商业环境，针对中国及其它亚洲国家，提供面试学习者的绿色通道课程。</p><p style=\"white-space: normal;\"><img src=\"http://liuxueapp.wbteam.cn/Uploads/Editor/Picture/2017-03-07/58be6daeee881.png \n" +
                "\n" +
                "\" title=\"\" alt=\"美国德克萨斯州立大学 阿灵顿分校02.png\"/><img src=\"http://liuxueapp.wbteam.cn/Uploads/Editor/Picture/2017-03-07/58be6dab7b7a6.png \n" +
                "\n" +
                "\" title=\"\" alt=\"美国德克萨斯州立大学 阿灵顿分校01.png\"/></p><p><br/></p>";
        webview.setText(Html.fromHtml(url1,imgGetter,null));
    }

    private void initData() {
        String url= AppConfig.LIKEIT_SCHOOL_DETAILS;
        RequestParams params=new RequestParams();
        params.put("ukey",ukey);
        params.put("sid",sid);
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.e("TAG",response);
            }

            @Override
            public void failed(Throwable e) {

            }
        });
    }
    //这里面的resource就是fromhtml函数的第一个参数里面的含有的url
    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Log.i("RG", "source---?>>>" + source);
            Drawable drawable = null;
            URL url;
            try {
                url = new URL(source);
                Log.i("RG", "url---?>>>" + url);
                drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            Log.i("RG", "url---?>>>" + url);
            return drawable;
        }
    };
    public static void struct() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork() // or
                // .detectAll()
                // for
                // all
                // detectable
                // problems
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects() // 探测SQLite数据库操作
                .penaltyLog() // 打印logcat
                .penaltyDeath().build());
    }
}
