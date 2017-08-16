package com.likeit.a51scholarship.configs;

/**
 * Created by Administrator on 2017\7\18 0018.
 */

public class AppConfig {

    //测试
    public static final String SERVER_API = "http://liuxueapp.wbteam.cn/index.php?s=";
    //正式
    // public static final String SERVER_API = "http://www.51scholarship.com/index.php?s=";
    /**
     * 用户
     */
    //注册
    public static final String LIKEIT_REGISTER = SERVER_API + "/api/member/register";
    //登录
    public static final String LIKEIT_LOGIN = SERVER_API + "/api/member/login";
    //获取用户信息
    public static final String LIKEIT_GET_INFO = SERVER_API + "/api/member/get_info";
    //我关注的圈子接口
    public static final String LIKEIT_GET_GROUP = SERVER_API + "/api/member/get_group";
    //广告
    public static final String LIKEIT_ADLIST = SERVER_API + "/api/index/adlist";
    /**
     * 院校
     */
    //首页学校推荐
    public static final String LIKEIT_SCHOOL= SERVER_API + "/api/index/school";
    //首页学校資訊
    public static final String LIKEIT_NEWS= SERVER_API + "/api/index/news";
    //院校列表接口
    public static final String LIKEIT_SCHOOL_LIST= SERVER_API + "/api/school/getlist";

    /**
     * 圈子
     */
    //圈子列表接口
    public static final String LIKEIT_GROUP_LIST= SERVER_API + "/api/group/getlist";
    //圈子群组列表接口  筛选
    public static final String LIKEIT_GROUP_GETTYPE= SERVER_API + "/api/group/gettype";
    //圈子关注转换接口
    public static final String LIKEIT_GROUP_FOLLOW_GROUP= SERVER_API + "/api/group/follow_group";
    //圈子文章列表接口
    public static final String LIKEIT_GROUP_GETPOST= SERVER_API + "/api/group/getpost";
}
