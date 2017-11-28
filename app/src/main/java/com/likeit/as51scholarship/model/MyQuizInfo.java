package com.likeit.as51scholarship.model;

/**
 * Created by Administrator on 2017/11/23.
 */

public class MyQuizInfo {

    /**
     * id : 27
     * author : admin
     * headimg : http://liuxueapp.wbteam.cn//Uploads/Avatar/1/58b77975dada9.png
     * content : 入境被关“小黑屋”如何应对？诚实回答勿心虚！
     * category : 国内教育
     * country : 美国
     * interval : 6个月前
     * view : 10
     * answer_num : 0
     */

    private String id;
    private String author;
    private String headimg;
    private String content;
    private String category;
    private String country;
    private String interval;
    private String view;
    private String answer_num;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getAnswer_num() {
        return answer_num;
    }

    public void setAnswer_num(String answer_num) {
        this.answer_num = answer_num;
    }
}
