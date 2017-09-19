package com.likeit.as51scholarship.model;

/**
 * Created by Administrator on 2017\8\31 0031.
 */

public class SchoolFilterEventBean {
    private String attrId;
    private String attrName;

    @Override
    public String toString() {
        return "SchoolFilterEventBean{" +
                "attrId='" + attrId + '\'' +
                ", attrName='" + attrName + '\'' +
                '}';
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public String setAttrName(String attrName) {
        this.attrName = attrName;
        return attrName;
    }
}
