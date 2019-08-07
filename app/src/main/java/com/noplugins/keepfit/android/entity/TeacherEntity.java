package com.noplugins.keepfit.android.entity;

import java.util.List;

public class TeacherEntity {


    /**
     * teacher : [{"teacherNum":"Gen456","teacherName":"测试2","createDate":1564033041000},{"teacherNum":"Gen789","teacherName":"测试3","createDate":1564033773000}]
     * maxPage : 1
     */

    private int maxPage;
    private List<TeacherBean> teacher;

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public List<TeacherBean> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<TeacherBean> teacher) {
        this.teacher = teacher;
    }

    public static class TeacherBean {
        /**
         * teacherNum : Gen456
         * teacherName : 测试2
         * createDate : 1564033041000
         */

        private String teacherNum;
        private String teacherName;
        private long createDate;

        public String getSkill() {
            return skill;
        }

        public void setSkill(String skill) {
            this.skill = skill;
        }

        private String skill;

        public String getTeacherNum() {
            return teacherNum;
        }

        public void setTeacherNum(String teacherNum) {
            this.teacherNum = teacherNum;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }
    }
}
