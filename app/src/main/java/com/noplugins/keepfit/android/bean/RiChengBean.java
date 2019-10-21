package com.noplugins.keepfit.android.bean;

import java.util.List;

public class RiChengBean {

    private List<ResultBean> result;
    private List<MonthBean> month;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public List<MonthBean> getMonth() {
        return month;
    }

    public void setMonth(List<MonthBean> month) {
        this.month = month;
    }

    public static class ResultBean {
        /**
         * courseType : 2
         * userCheckIn : 未签
         * teacherName : 未查询到教练姓名
         * courseNum : GYM190912828619572
         * price : ¥ 0.05
         * person :
         * userPhone : 15618081181
         * teacherPhone : 17621233147
         * courseStatus : 1
         * className : Dance Party
         * userName :
         * courseTime : 10:00-11:00
         */

        private int courseType;
        private String userCheckIn;
        private String teacherName;
        private String courseNum;
        private String price;
        private String person;
        private String userPhone;
        private String teacherPhone;
        private int courseStatus;
        private String className;
        private String userName;
        private String courseTime;

        public int getCourseType() {
            return courseType;
        }

        public void setCourseType(int courseType) {
            this.courseType = courseType;
        }

        public String getUserCheckIn() {
            return userCheckIn;
        }

        public void setUserCheckIn(String userCheckIn) {
            this.userCheckIn = userCheckIn;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getCourseNum() {
            return courseNum;
        }

        public void setCourseNum(String courseNum) {
            this.courseNum = courseNum;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPerson() {
            return person;
        }

        public void setPerson(String person) {
            this.person = person;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getTeacherPhone() {
            return teacherPhone;
        }

        public void setTeacherPhone(String teacherPhone) {
            this.teacherPhone = teacherPhone;
        }

        public int getCourseStatus() {
            return courseStatus;
        }

        public void setCourseStatus(int courseStatus) {
            this.courseStatus = courseStatus;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCourseTime() {
            return courseTime;
        }

        public void setCourseTime(String courseTime) {
            this.courseTime = courseTime;
        }
    }

    public static class MonthBean {
        /**
         * days : 20190919
         */

        private String days;

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }
    }
}
