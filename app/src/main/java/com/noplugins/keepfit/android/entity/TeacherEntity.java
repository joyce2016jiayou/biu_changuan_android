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
        private String createDate;
        private int inviteStatus;
        private String gymInviteNum;

        public String getGymInviteNum() {
            return gymInviteNum;
        }

        public void setGymInviteNum(String gymInviteNum) {
            this.gymInviteNum = gymInviteNum;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        private String logoUrl;

        public int getInviteStatus() {
            return inviteStatus;
        }

        public void setInviteStatus(int inviteStatus) {
            this.inviteStatus = inviteStatus;
        }


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

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
