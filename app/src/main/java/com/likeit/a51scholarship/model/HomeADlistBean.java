package com.likeit.a51scholarship.model;

/**
 * Created by Administrator on 2017\7\22 0022.
 */

public class HomeADlistBean {
    private String url;//图片点击的链接
    private String pic;//图片地址

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "HomeADlistBean{" +
                "url='" + url + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
