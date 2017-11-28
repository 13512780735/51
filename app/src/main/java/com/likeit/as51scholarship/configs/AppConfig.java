package com.likeit.as51scholarship.configs;
/**
 * Created by Administrator on 2017\7\18 0018.
 */

public class AppConfig {
    //域名
    public static final String IMAGE_URL_HOST = "http://liuxueapp.wbteam.cn/";
    //测试
    public static final String SERVER_API = IMAGE_URL_HOST+"/index.php?s=";
    //正式
    // public static final String SERVER_API = "http://www.51scholarship.com/index.php?s=";
    //token
    public static final String TOKEN="token";
    //用户id
    public static final String USER_ID="userId";
    //用户环信id
    public static final String USER_EASEMOBID="easemobId";
    //用户名
    public static final String USER_NAME="userName";
    //密码
    public static final String PASS_WORD="passWord";
    //用户手机号
    public static final String USRE_PHONE="userPhone";
    //用户头像
    public static final String USER_HEAD_IMG="userHeadImg";


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
    //修改用户密码接口
    public static final String LIKEIT_CHANGE_PWD= SERVER_API + "/api/member/changepwd";
    //重置密码接口
    public static final String LIKEIT_RSETPWD= SERVER_API + "/api/member/resetpwd";
    //身份证上传接口
    public static final String LIKEIT_UPLOAD_FORBASE64= SERVER_API + "/api/member/uploadForBase64";
    //申请个人认证接口
    public static final String LIKEIT_APPROVE= SERVER_API + "/api/member/approve";
    //用户意见反馈接口
    public static final String LIKEIT_FEEDBACK= SERVER_API + "/api/member/feedback";
    //绑定第三方登录接口
    public static final String LIKEIT_BIND_THIRD= SERVER_API + "/api/member/bind_third";
    //我发布的资讯
    public static final String LIKEIT_NEWS_MYLIST= SERVER_API + "/api/news/mylist";
    //删除资讯接口
    public static final String LIKEIT_NEWS_DELETENEWS= SERVER_API + "/api/news/deletenews";
    //我的问题列表接口
    public static final String LIKEIT_QUESTION_MYLIST= SERVER_API + "/api/question/mylist";
    //删除问题接口
    public static final String LIKEIT_QUESTION_DELETEQUESTION= SERVER_API + "/api/question/deletequestion";
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
    //通用图片上传接口
    public static final String LIKEIT_UPLOAD_FORBASE64_01= SERVER_API + "/api/index/uploadForBase64";

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
    //搜索问答接口
    public static final String LIKEIT_SEARCH_QUESTION= SERVER_API + "/api/search/question";
    //搜索课程接口
    public static final String LIKEIT_SEARCH_COURSE= SERVER_API + "/api/search/course";
    //搜索圈子接口
    public static final String LIKEIT_SEARCH_GROUP= SERVER_API + "/api/search/group";

    /**
     * 问答
     */
    //问答列表接口
    public static final String LIKEIT_QUESTION_GETLIST= SERVER_API + "/api/question/getlist";
    //问答分类接口
    public static final String LIKEIT_QUESTION_GETCATEGORY= SERVER_API + "/api/question/getcategory";
    //问题发布接口
    public static final String LIKEIT_QUESTION_ADDQUESTION= SERVER_API + "/api/question/addquestion";
    //回答列表接口
    public static final String LIKEIT_ANSWER_LIST= SERVER_API + "/api/question/get_answer_list";
    //回答发布接口
    public static final String LIKEIT_ANSWER_ADDANSWER= SERVER_API + "/api/question/addanswer";
    //问答赞接口
    public static final String LIKEIT_ANSWER_ADDLIKE= SERVER_API + "/api/question/addlike";
    /**
     * 环信
     */
    //获取环信好友列表接口
    public static final String LIKEIT_GET_EASE_FRIEND= SERVER_API + "/api/index/get_ease_friend";
    //获取环信用户信息接口
    public static final String LIKEIT_GET_EASEMOB_USERINFO= SERVER_API + "/api/index/get_easemob_userinfo";
    /**
     * 课程
     */
    //课程列表接口
    public static final String LIKEIT_COURSE_GETLIST= SERVER_API + "/api/video/getlist";
    //课程详情接口
    public static final String LIKEIT_COURSE_DETAILS= SERVER_API + "/api/video/getdetails";
    //课程目录列表接口
    public static final String LIKEIT_COURSE_COURSELIST= SERVER_API + "/api/video/getcourselist";
    /**
     * 公告
     */
    //网站公告接口
    public static final String LIKEIT_INDEX_NOTICE= SERVER_API + "/api/index/notice";
}