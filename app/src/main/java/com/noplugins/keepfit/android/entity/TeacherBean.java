package com.noplugins.keepfit.android.entity;

import java.util.List;

public class TeacherBean {
    private List<TeacherEntity> userList;

    public List<TeacherEntity> getTeacherList() {
        return userList;
    }

    public void setTeacherList(List<TeacherEntity> teacherList) {
        this.userList = teacherList;
    }

    public static class TeacherEntity{
        private String gymAreaNum;
        private String userName;
        private String phone;
        private int deleted = -1;
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getGymAreaNum() {
            return gymAreaNum;
        }

        public void setGymAreaNum(String gymAreaNum) {
            this.gymAreaNum = gymAreaNum;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }
    }
}
