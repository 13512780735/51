package com.likeit.as51scholarship.model.circle_model;

/**
 * Created by Administrator on 2017/9/1.
 */

public class CircleEssayModel {

    /**
     * id : 24
     * title : 测试
     * content : 测试
     * post_time : 1970-01-01 08:00:00
     * userinfo : {"uid":"204","nickname":"小灰爸爸03","headimg":"http://liuxueapp.wbteam.cn//Uploads/Avatar/204/59c8fdf1a25e2.png","easemob_id":"13680260576"}
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
         * uid : 204
         * nickname : 小灰爸爸03
         * headimg : http://liuxueapp.wbteam.cn//Uploads/Avatar/204/59c8fdf1a25e2.png
         * easemob_id : 13680260576
         */

        private String uid;
        private String nickname;
        private String headimg;
        private String easemob_id;
        private String isfriend;

        public String getIsfriend() {
            return isfriend;
        }

        public void setIsfriend(String isfriend) {
            this.isfriend = isfriend;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

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

        public String getEasemob_id() {
            return easemob_id;
        }

        public void setEasemob_id(String easemob_id) {
            this.easemob_id = easemob_id;
        }
    }
}
