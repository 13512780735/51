package com.likeit.as51scholarship.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class SchoolApplyBean {

    private List<AreaBean> area;
    private List<StageBean> stage;
    private List<PlanTimeBean> plan_time;

    public List<AreaBean> getArea() {
        return area;
    }

    public void setArea(List<AreaBean> area) {
        this.area = area;
    }

    public List<StageBean> getStage() {
        return stage;
    }

    public void setStage(List<StageBean> stage) {
        this.stage = stage;
    }

    public List<PlanTimeBean> getPlan_time() {
        return plan_time;
    }

    public void setPlan_time(List<PlanTimeBean> plan_time) {
        this.plan_time = plan_time;
    }

    public static class AreaBean {
        /**
         * id : 1
         * name : 英国
         */

        private String id;
        private String name;

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
    }

    public static class StageBean {
        /**
         * id : 1
         * name : 中小学
         */

        private String id;
        private String name;

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
    }

    public static class PlanTimeBean {
        /**
         * id : 1
         * name : 1年内
         */

        private String id;
        private String name;

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
    }
}
