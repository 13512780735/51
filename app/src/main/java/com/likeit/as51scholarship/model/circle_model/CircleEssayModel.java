package com.likeit.as51scholarship.model.circle_model;

/**
 * Created by Administrator on 2017/9/1.
 */

public class CircleEssayModel {

    /**
     * id : 13
     * title : 测试
     * content : <p>测试</p>
     * post_time : 2017-07-28 10:43:34
     * userinfo : {"nickname":"admin","headimg":"http://liuxueapp.wbteam.cn//Uploads/Avatar/1/58b77975dada9.png"}
     */

    private String id;
    private String title;
    private String content;
    private String post_time;
    private UserinfoBean userinfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public static class UserinfoBean {
        /**
         * nickname : admin
         * headimg : http://liuxueapp.wbteam.cn//Uploads/Avatar/1/58b77975dada9.png
         */

        private String nickname;
        private String headimg;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }
    }
}
