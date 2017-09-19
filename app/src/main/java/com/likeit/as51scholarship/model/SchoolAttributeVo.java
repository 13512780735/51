package com.likeit.as51scholarship.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/31.
 */

public class SchoolAttributeVo implements Serializable{
    private boolean isChecked;
    private String attr_id;
    private String attr_name;

    @Override
    public String toString() {
        return "SchoolAttributeVo{" +
                "isChecked=" + isChecked +
                ", attr_id='" + attr_id + '\'' +
                ", attr_name='" + attr_name + '\'' +
                '}';
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(String attr_id) {
        this.attr_id = attr_id;
    }

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }
}
