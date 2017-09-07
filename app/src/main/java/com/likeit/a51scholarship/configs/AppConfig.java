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
    //第三方登录
    public static final String LIKEIT_THIRD_LOGIN = SERVER_API + "/api/member/third_login";
    //获取用户信息
    public static final String LIKEIT_GET_INFO = SERVER_API + "/api/member/get_info";
    //我关注的圈子接口
    public static final String LIKEIT_GET_GROUP = SERVER_API + "/api/member/get_group";
    //广告
    public static final String LIKEIT_ADLIST = SERVER_API + "/api/index/adlist";
    //上传头像接口
    public static final String LIKEIT_UPIMG= SERVER_API + "/api/member/upimg_base64";
    //用户编辑初始数据接口
    public static final String LIKEIT_MEMBER_EDIT_TMPL= SERVER_API + "/api/member/edit_tmpl";
    //编辑用户资料接口
    public static final String LIKEIT_MEMBER_EDIT_INFO= SERVER_API + "/api/member/edit_info";
    //获取地区接口
    public static final String LIKEIT_MEMBER_EDIT_DISTRICT= SERVER_API + "/api/region/district";
    //我的收藏接口
    public static final String LIKEIT_MEMBER_COLLECT= SERVER_API + "/api/member/collect";
    /**
     * 院校
     */
    //首页学校推荐
    public static final String LIKEIT_SCHOOL= SERVER_API + "/api/index/school";
    //首页学校資訊
    public static final String LIKEIT_NEWS= SERVER_API + "/api/index/news";
    //院校列表接口
    public static final String LIKEIT_SCHOOL_LIST= SERVER_API + "/api/school/getlist";
    //院校详情
    public static final String LIKEIT_SCHOOL_DETAILS= SERVER_API + "/api/school/getdetails";
    //申请院校接口
    public static final String LIKEIT_SCHOOL_APPLY= SERVER_API + "/api/school/apply";
    //申请院校初始数据接口
    public static final String LIKEIT_SCHOOL_APPLY_TMPL= SERVER_API + "/api/school/apply_tmpl";
    //院校区域接口
    public static final String LIKEIT_SCHOOL_AREA= SERVER_API + "/api/school/area";
    //院校学位接口
    public static final String LIKEIT_SCHOOL_ASTAGE= SERVER_API + "/api/school/stage";
    //院校筛选参数接口
    public static final String LIKEIT_SCHOOL_FILTR_PARAM= SERVER_API + "/api/school/filtr_param";
    //院校筛选总数接口
    public static final String LIKEIT_SCHOOL_FILTR_COUNT= SERVER_API + "/api/school/get_filtr_count";
    //院校收藏接口
    public static final String LIKEIT_SCHOOL_COLLECT= SERVER_API + "/api/school/collect";


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
    //圈子成员列表接口
    public static final String LIKEIT_GROUP_GET_MEMBER_LIST= SERVER_API + "/api/group/get_member_list";
    //圈子文章发布接口
    public static final String LIKEIT_GROUP_ADDPOST= SERVER_API + "/api/group/addpost";
    /**
     * 资讯
     */
    //资讯列表接口
    public static final String LIKEIT_NEW_GETLIST= SERVER_API + "/api/news/getlist";
    //资讯分类接口
    public static final String LIKEIT_NEW_GETCATEGORY= SERVER_API + "/api/news/getcategory";
    //资讯详情接口
    public static final String LIKEIT_NEW_GETDETAILS= SERVER_API + "/api/news/getdetails";
    //资讯发布接口
    public static final String LIKEIT_NEW_ADDNEWS= SERVER_API + "/api/news/addnews";
    /**
     * 搜索
     */
    //搜索院校接口
    public static final String LIKEIT_SEARCH_SCHOOL= SERVER_API + "/api/search/school";
    //搜索资讯接口
    public static final String LIKEIT_SEARCH_NEWS= SERVER_API + "/api/search/news";
}