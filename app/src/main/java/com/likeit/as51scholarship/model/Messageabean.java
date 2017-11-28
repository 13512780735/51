package com.likeit.as51scholarship.model;

/**
 * Created by Administrator on 2017/9/29.
 */

public class Messageabean {

    /**
     * id : 1
     * title : test公告
     * content : test公告test公告test公告test公告test公告test公告test公告test公告test公告test公告
     * link : http://www.wbteam.cn
     * create_time : 2017-09-29 16:13:41
     */

    private String id;
    private String title;
    private String content;
    private String link;
    private String create_time;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
