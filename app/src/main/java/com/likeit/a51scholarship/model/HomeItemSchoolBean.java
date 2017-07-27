package com.likeit.a51scholarship.model;

/**
 * Created by Administrator on 2017\7\22 0022.
 */

public class HomeItemSchoolBean {
    /**
     * id : 4
     * name : 学院1
     * en_name : school1
     * country_name : 英国
     * img : /Uploads/Picture/2017-02-25/58b10b3059455.jpg
     */

    private String id;
    private String name;
    private String en_name;
    private String country_name;
    private String img;

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

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    @Override
    public String toString() {
        return "IndexSchoolEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", en_name='" + en_name + '\'' +
                ", country_name='" + country_name + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
