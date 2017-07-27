package com.likeit.a51scholarship.model;

/**
 * Created by Administrator on 2017/7/25.
 */

public class Toolsbean {
    String img_url;
    String name;
    String details;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Toolsbean{" +
                "img_url='" + img_url + '\'' +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
