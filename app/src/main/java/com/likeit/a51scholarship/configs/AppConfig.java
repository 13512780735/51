package com.likeit.a51scholarship.configs;

/**
 * Created by Administrator on 2017\7\18 0018.
 */

public class AppConfig {

    //测试
    public static final String SERVER_API = "http://liuxueapp.wbteam.cn/index.php?s=";
    //正式
    // public static final String SERVER_API = "http://www.51scholarship.com/index.php?s=";

    //注册
    public static final String LIKEIT_REGISTER = SERVER_API + "/api/member/register";
    //登录
    public static final String LIKEIT_LOGIN = SERVER_API + "/api/member/login";
    //广告
    public static final String LIKEIT_ADLIST = SERVER_API + "/api/index/adlist";
    //首页学校推荐
    public static final String LIKEIT_SCHOOL= SERVER_API + "/api/index/school";
    //首页学校資訊
    public static final String LIKEIT_NEWS= SERVER_API + "/api/index/news";
}
