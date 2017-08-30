package com.likeit.a51scholarship.model;

/**
 * Created by Administrator on 2017/8/29.
 */

public class NewListDetailsBean {
    /**
     * id : 1
     * author : admin
     * category_name : 头条
     * title : 牛津布鲁克斯大学2017年奖学金开放申请 7月截止
     * description : 英国牛津布鲁克斯大学（Oxford Brookes University）2017年入学奖学金开放申请，7月7日截止。
     * interval : 6个月前
     * view : 49
     * comment : 3
     * content : 英国牛津布鲁克斯大学（Oxford Brookes University）2017年入学奖学金开放申请，7月7日截止。
     */

    private String id;
    private String author;
    private String category_name;
    private String title;
    private String description;
    private String interval;
    private String view;
    private String comment;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
