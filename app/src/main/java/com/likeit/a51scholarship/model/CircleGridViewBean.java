package com.likeit.a51scholarship.model;

/**
 * Created by Administrator on 2017/7/26.
 */

public class CircleGridViewBean {
    private String id;
    private String name;
    private String img;

    @Override
    public String toString() {
        return "CricleGridViewBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
