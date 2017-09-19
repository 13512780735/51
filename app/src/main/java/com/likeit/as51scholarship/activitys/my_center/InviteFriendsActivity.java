package com.likeit.as51scholarship.activitys.my_center;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;

import com.likeit.as51scholarship.R;
import com.likeit.as51scholarship.activitys.Container;
import com.likeit.as51scholarship.configs.AppConfig;
import com.likeit.as51scholarship.http.HttpUtil;
import com.likeit.as51scholarship.utils.richtext.RichText;
import com.loopj.android.http.RequestParams;

import java.net.URL;

public class InviteFriendsActivity extends Container {

    private String sid;
    private RichText webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
        sid="4";
        //initData();
        initView();
    }

    private void initView() {
        webview=(RichText) findViewById(R.id.webview);
        String url1=" <p style=\\\"white-space: normal;\\\"><span style=\\\"font-size: 18px;\\\"><strong>录取数据</strong></span></p><p style=\\\"white-space: normal;\\\">申请人数 34919&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录取率 5.8%</p><p style=\\\"white-space: normal;\\\">入学率</p><p style=\\\"white-space: normal;\\\">GPA平均分 4</p><p style=\\\"white-space: normal;\\\">SAT录取区间值 &nbsp;~0</p><p style=\\\"white-space: normal;\\\">SAT数学区间值 &nbsp;~0</p><p style=\\\"white-space: normal;\\\">SAT批判性阅读区间值 &nbsp;~0</p><p style=\\\"white-space: normal;\\\">ACT录取区间值 &nbsp;32~35</p><p style=\\\"white-space: normal;\\\"><br/></p><p style=\\\"white-space: normal;\\\"><span style=\\\"font-size: 18px;\\\"><strong>新生高中生班级排名</strong></span></p><p style=\\\"white-space: normal;\\\">班级排名前10%学生比例 &nbsp;95%</p><p style=\\\"white-space: normal;\\\">班级排名前25%学生比例 &nbsp;100%<br/></p><p style=\\\"white-space: normal;\\\">班级排名前50%学生比例 &nbsp;100%</p><p style=\\\"white-space: normal;\\\"><br/></p><p style=\\\"white-space: normal;\\\"><span style=\\\"font-size: 18px;\\\"><strong>申请信息</strong></span></p><p style=\\\"white-space: normal;\\\">申请截止日期 &nbsp;01-01 &nbsp; &nbsp;通知日期<br/></p><p style=\\\"white-space: normal;\\\">是否有EA &nbsp;是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EA截止日期 &nbsp;11-01</p><p style=\\\"white-space: normal;\\\">是否有ED &nbsp;否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ED截止日期</p><p style=\\\"white-space: normal;\\\">是否双录取 &nbsp;否</p><p style=\\\"white-space: normal;\\\">申请费 $75</p><p style=\\\"white-space: normal;\\\">申请网址 &nbsp;<a href=\\\"https://college.harvard.edu/admissions\\\" _src=\\\"https://college.harvard.edu/admissions\\\">https://college.harvard.edu/admissions</a></p><p style=\\\"white-space: normal;\\\"><br/></p><p style=\\\"white-space: normal;\\\"><span style=\\\"font-size: 18px;\\\"><strong>申请材料</strong></span></p><p style=\\\"white-space: normal;\\\">1.填写申请表; 2.75美金申请费; 3.哈佛补充申请表; 4.SAT或ACT写作成绩; 5.两门SAT II 的成绩 (注意: SAT II English Language Proficiency Test, ELPT 不被接受); 6.中学成绩单; 7.两篇教师推荐信.<br/></p><p><br/></p>";
       // webview.setText(Html.fromHtml(url1,imgGetter,null));
        webview.setRichText(url1);
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
