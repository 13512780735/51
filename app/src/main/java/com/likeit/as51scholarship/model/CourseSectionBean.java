package com.likeit.as51scholarship.model;

/**
 * Created by Administrator on 2017/11/28.
 */

public class CourseSectionBean {

    /**
     * id : 1
     * author : admin
     * title : 托福名师讲解，高效提分
     * cover : http://liuxueapp.wbteam.cn//Uploads/Picture/2017-10-18/59e6f89d9a697.png
     * cover_width : 300
     * cover_height : 250
     * description : 新东方在线托福听力、SSAT词汇教师，教书4年余载，国际经济法硕士，高级口译岗位资格证书和法律职业资格证书持有者多次在大型展会担任口译员，对口译笔记系统有深入了解。
     * interval : 1个月前
     * view : 0
     * comment : 0
     * content : <p>新东方在线托福听力、SSAT词汇教师，教书4年余载，国际经济法硕士，高级口译岗位资格证书和法律职业资格证书持有者多次在大型展会担任口译员，对口译笔记系统有深入了解。<br/></p><p><br/></p>
     * video_url : http://ox15r5io7.bkt.clouddn.com/UC-download.MP4
     * file_url : http://liuxueapp.wbteam.cn//Uploads/Download/2017-11-27/5a1c199b782b2.docx
     * file_name : 20171120留学APP功能开发问题反馈.docx
     * create_time : 2017-10-01 17:04:39
     */

    private String id;
    private String author;
    private String title;
    private String cover;
    private String cover_width;
    private String cover_height;
    private String description;
    private String interval;
    private String view;
    private String comment;
    private String content;
    private String video_url;
    private String file_url;
    private String file_name;
    private String create_time;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover_width() {
        return cover_width;
    }

    public void setCover_width(String cover_width) {
        this.cover_width = cover_width;
    }

    public String getCover_height() {
        return cover_height;
    }

    public void setCover_height(String cover_height) {
        this.cover_height = cover_height;
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

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
