package com.likeit.as51scholarship.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/26.
 */

public class SchoolDetailsBean01 implements Serializable{
    /**
     * name : 录取与申请
     * content : <p style="white-space: normal;"><span style="font-size: 18px;"><strong>录取数据</strong></span></p><p style="white-space: normal;">申请人数 34919&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录取率 5.8%</p><p style="white-space: normal;">入学率</p><p style="white-space: normal;">GPA平均分 4</p><p style="white-space: normal;">SAT录取区间值 &nbsp;~0</p><p style="white-space: normal;">SAT数学区间值 &nbsp;~0</p><p style="white-space: normal;">SAT批判性阅读区间值 &nbsp;~0</p><p style="white-space: normal;">ACT录取区间值 &nbsp;32~35</p><p style="white-space: normal;"><br/></p><p style="white-space: normal;"><span style="font-size: 18px;"><strong>新生高中生班级排名</strong></span></p><p style="white-space: normal;">班级排名前10%学生比例 &nbsp;95%</p><p style="white-space: normal;">班级排名前25%学生比例 &nbsp;100%<br/></p><p style="white-space: normal;">班级排名前50%学生比例 &nbsp;100%</p><p style="white-space: normal;"><br/></p><p style="white-space: normal;"><span style="font-size: 18px;"><strong>申请信息</strong></span></p><p style="white-space: normal;">申请截止日期 &nbsp;01-01 &nbsp; &nbsp;通知日期<br/></p><p style="white-space: normal;">是否有EA &nbsp;是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EA截止日期 &nbsp;11-01</p><p style="white-space: normal;">是否有ED &nbsp;否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ED截止日期</p><p style="white-space: normal;">是否双录取 &nbsp;否</p><p style="white-space: normal;">申请费 $75</p><p style="white-space: normal;">申请网址 &nbsp;<a href="https://college.harvard.edu/admissions" _src="https://college.harvard.edu/admissions">https://college.harvard.edu/admissions</a></p><p style="white-space: normal;"><br/></p><p style="white-space: normal;"><span style="font-size: 18px;"><strong>申请材料</strong></span></p><p style="white-space: normal;">1.填写申请表; 2.75美金申请费; 3.哈佛补充申请表; 4.SAT或ACT写作成绩; 5.两门SAT II 的成绩 (注意: SAT II English Language Proficiency Test, ELPT 不被接受); 6.中学成绩单; 7.两篇教师推荐信.<br/></p><p><br/></p>
     */

    private String name;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
