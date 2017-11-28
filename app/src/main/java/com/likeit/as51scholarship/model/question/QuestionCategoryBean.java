package com.likeit.as51scholarship.model.question;

/**
 * Created by Administrator on 2017/9/28.
 */

public class QuestionCategoryBean {

    /**
     * id : 2
     * title : 国内教育
     */

    private String id;
    private String title;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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
}
