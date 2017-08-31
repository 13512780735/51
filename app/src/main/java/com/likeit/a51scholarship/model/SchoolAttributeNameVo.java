package com.likeit.a51scholarship.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class SchoolAttributeNameVo implements Serializable {
    private String name;
    private List<SchoolAttributeVo> values;
    private String schoolAttr;
    private boolean nameIsChecked;

    @Override
    public String toString() {
        return "SchoolAttributeNameVo{" +
                "name='" + name + '\'' +
                ", values=" + values +
                ", schoolAttr='" + schoolAttr + '\'' +
                ", nameIsChecked=" + nameIsChecked +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SchoolAttributeVo> getValues() {
        return values;
    }

    public void setValues(List<SchoolAttributeVo> values) {
        this.values = values;
    }

    public String getSchoolAttr() {
        return schoolAttr;
    }

    public void setSchoolAttr(List<SchoolAttributeVo> values) {

        this.values = values;
    }

    public boolean isNameIsChecked() {
        return nameIsChecked;
    }

    public void setNameIsChecked(boolean nameIsChecked) {
        this.nameIsChecked = nameIsChecked;
    }
}
