package com.likeit.as51scholarship.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

public class SchoolDetailsBean {

    /**
     * school_info : {"id":"5","name":"美国威斯康星协和大学","en_name":"Concordia University Wisconsin","country_name":"美国","area_name":"纽约","ranking":"54","rate":"100.00","scholarship":"52222.00","nature_name":"私立","number":"100000","toeic":"0","toefl":"65","yasi":"60","gmat":"52","gpa":"3","description":"美国威斯康星协和大学又称『美国威斯康星康考迪亚大学』成立于1881年，已有134年悠久历史，为美国康考迪亚大学系统中最大的分校，其优秀校友遍布全球各地，由于本校是全美公认校园环境最安全的大学之一(排名Top 10)，因此就读的学生大部份来自贵族家庭，并以美国白人家庭及欧洲各国学生为主。","img":"http://liuxueapp.wbteam.cn//Uploads/Picture/2017-03-21/58d0cbb31355e.jpg","img_width":"702","img_height":"280"}
     * school_details : [{"name":"录取与申请","content":"<p style=\"white-space: normal;\"><span style=\"font-size: 18px;\"><strong>录取数据<\/strong><\/span><\/p><p style=\"white-space: normal;\">申请人数 34919&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录取率 5.8%<\/p><p style=\"white-space: normal;\">入学率<\/p><p style=\"white-space: normal;\">GPA平均分 4<\/p><p style=\"white-space: normal;\">SAT录取区间值 &nbsp;~0<\/p><p style=\"white-space: normal;\">SAT数学区间值 &nbsp;~0<\/p><p style=\"white-space: normal;\">SAT批判性阅读区间值 &nbsp;~0<\/p><p style=\"white-space: normal;\">ACT录取区间值 &nbsp;32~35<\/p><p style=\"white-space: normal;\"><br/><\/p><p style=\"white-space: normal;\"><span style=\"font-size: 18px;\"><strong>新生高中生班级排名<\/strong><\/span><\/p><p style=\"white-space: normal;\">班级排名前10%学生比例 &nbsp;95%<\/p><p style=\"white-space: normal;\">班级排名前25%学生比例 &nbsp;100%<br/><\/p><p style=\"white-space: normal;\">班级排名前50%学生比例 &nbsp;100%<\/p><p style=\"white-space: normal;\"><br/><\/p><p style=\"white-space: normal;\"><span style=\"font-size: 18px;\"><strong>申请信息<\/strong><\/span><\/p><p style=\"white-space: normal;\">申请截止日期 &nbsp;01-01 &nbsp; &nbsp;通知日期<br/><\/p><p style=\"white-space: normal;\">是否有EA &nbsp;是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EA截止日期 &nbsp;11-01<\/p><p style=\"white-space: normal;\">是否有ED &nbsp;否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ED截止日期<\/p><p style=\"white-space: normal;\">是否双录取 &nbsp;否<\/p><p style=\"white-space: normal;\">申请费 $75<\/p><p style=\"white-space: normal;\">申请网址 &nbsp;<a href=\"https://college.harvard.edu/admissions\" _src=\"https://college.harvard.edu/admissions\">https://college.harvard.edu/admissions<\/a><\/p><p style=\"white-space: normal;\"><br/><\/p><p style=\"white-space: normal;\"><span style=\"font-size: 18px;\"><strong>申请材料<\/strong><\/span><\/p><p style=\"white-space: normal;\">1.填写申请表; 2.75美金申请费; 3.哈佛补充申请表; 4.SAT或ACT写作成绩; 5.两门SAT II 的成绩 (注意: SAT II English Language Proficiency Test, ELPT 不被接受); 6.中学成绩单; 7.两篇教师推荐信.<br/><\/p><p><br/><\/p>"}]
     */

    private SchoolInfoBean school_info;
    private List<SchoolDetailsBean01> school_details;

    public SchoolInfoBean getSchool_info() {
        return school_info;
    }

    public void setSchool_info(SchoolInfoBean school_info) {
        this.school_info = school_info;
    }

    public List<SchoolDetailsBean01> getSchool_details() {
        return school_details;
    }

    public void setSchool_details(List<SchoolDetailsBean01> school_details) {
        this.school_details = school_details;
    }

    public static class SchoolInfoBean {
        /**
         * id : 5
         * name : 美国威斯康星协和大学
         * en_name : Concordia University Wisconsin
         * country_name : 美国
         * area_name : 纽约
         * ranking : 54
         * rate : 100.00
         * scholarship : 52222.00
         * nature_name : 私立
         * number : 100000
         * toeic : 0
         * toefl : 65
         * yasi : 60
         * gmat : 52
         * gpa : 3
         * description : 美国威斯康星协和大学又称『美国威斯康星康考迪亚大学』成立于1881年，已有134年悠久历史，为美国康考迪亚大学系统中最大的分校，其优秀校友遍布全球各地，由于本校是全美公认校园环境最安全的大学之一(排名Top 10)，因此就读的学生大部份来自贵族家庭，并以美国白人家庭及欧洲各国学生为主。
         * img : http://liuxueapp.wbteam.cn//Uploads/Picture/2017-03-21/58d0cbb31355e.jpg
         * img_width : 702
         * img_height : 280
         *  is_collect: 1
         */

        private String id;
        private String name;
        private String en_name;
        private String country_name;
        private String area_name;
        private String ranking;
        private String rate;
        private String scholarship;
        private String nature_name;
        private String number;
        private String toeic;
        private String toefl;
        private String yasi;
        private String gmat;
        private String gpa;
        private String description;
        private String img;
        private String img_width;
        private String img_height;
        private String is_collect;

        @Override
        public String toString() {
            return "SchoolInfoBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", en_name='" + en_name + '\'' +
                    ", country_name='" + country_name + '\'' +
                    ", area_name='" + area_name + '\'' +
                    ", ranking='" + ranking + '\'' +
                    ", rate='" + rate + '\'' +
                    ", scholarship='" + scholarship + '\'' +
                    ", nature_name='" + nature_name + '\'' +
                    ", number='" + number + '\'' +
                    ", toeic='" + toeic + '\'' +
                    ", toefl='" + toefl + '\'' +
                    ", yasi='" + yasi + '\'' +
                    ", gmat='" + gmat + '\'' +
                    ", gpa='" + gpa + '\'' +
                    ", description='" + description + '\'' +
                    ", img='" + img + '\'' +
                    ", img_width='" + img_width + '\'' +
                    ", img_height='" + img_height + '\'' +
                    ", is_collect='" + is_collect + '\'' +
                    '}';
        }

        public String getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

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

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getRanking() {
            return ranking;
        }

        public void setRanking(String ranking) {
            this.ranking = ranking;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getScholarship() {
            return scholarship;
        }

        public void setScholarship(String scholarship) {
            this.scholarship = scholarship;
        }

        public String getNature_name() {
            return nature_name;
        }

        public void setNature_name(String nature_name) {
            this.nature_name = nature_name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getToeic() {
            return toeic;
        }

        public void setToeic(String toeic) {
            this.toeic = toeic;
        }

        public String getToefl() {
            return toefl;
        }

        public void setToefl(String toefl) {
            this.toefl = toefl;
        }

        public String getYasi() {
            return yasi;
        }

        public void setYasi(String yasi) {
            this.yasi = yasi;
        }

        public String getGmat() {
            return gmat;
        }

        public void setGmat(String gmat) {
            this.gmat = gmat;
        }

        public String getGpa() {
            return gpa;
        }

        public void setGpa(String gpa) {
            this.gpa = gpa;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getImg_width() {
            return img_width;
        }

        public void setImg_width(String img_width) {
            this.img_width = img_width;
        }

        public String getImg_height() {
            return img_height;
        }

        public void setImg_height(String img_height) {
            this.img_height = img_height;
        }
    }
}
