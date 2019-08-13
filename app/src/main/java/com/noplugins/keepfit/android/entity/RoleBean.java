package com.noplugins.keepfit.android.entity;

import java.util.List;

public class RoleBean {

    private List<RoleEntity> userList;

    public List<RoleEntity> getUserList() {
        return userList;
    }

    public void setUserList(List<RoleEntity> userList) {
        this.userList = userList;
    }

    public static class RoleEntity{
        private String gymAreaNum;
        private String userNum;
        private String name;
        private String phone;
        private int type = 0;
        private int userType;
        private boolean focus;
        private int deleted = -1;

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public String getGymAreaNum() {
            return gymAreaNum;
        }

        public void setGymAreaNum(String gymAreaNum) {
            this.gymAreaNum = gymAreaNum;
        }

        public String getUserNum() {
            return userNum;
        }

        public void setUserNum(String userNum) {
            this.userNum = userNum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public boolean isFocus() {
            return focus;
        }

        public void setFocus(boolean focus) {
            this.focus = focus;
        }

    }

}
