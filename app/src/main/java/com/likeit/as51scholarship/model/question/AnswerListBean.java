package com.likeit.as51scholarship.model.question;

/**
 * Created by Administrator on 2017/9/28.
 */

public class AnswerListBean {


    /**
     * id : 1
     * author : 酸甜苦辣咸
     * headimg :
     * content : 1231231
     * interval : 2小时前
     * floor : 1
     */

    private String id;
    private String author;
    private String headimg;
    private String content;
    private String interval;
    private int floor;

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

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
