package com.likeit.as51scholarship.model;

/**
 * Created by Administrator on 2017/9/29.
 */

public class CourseListBean {

    /**
     * id : 1
     * title : 托福名师讲解,高效提分
     * duration : 5课时,177分钟
     * cover : http://www.wbteam.cn
     * cover : http://www.wbteam.cn
     * isfree : 1
     * view : 100
     * amount : 0.00
     * create_time : 2017-09-29 16:13:41
     * video_url : http://ox15r5io7.bkt.clouddn.com/job_M2U00862.mp4
     */

    private String id;
    private String title;
    private String duration;
    private String cover;
    private String isfree;
    private String view;
    private String amount;
    private String create_time;
    private String video_url;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getIsfree() {
        return isfree;
    }

    public void setIsfree(String isfree) {
        this.isfree = isfree;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }
}
