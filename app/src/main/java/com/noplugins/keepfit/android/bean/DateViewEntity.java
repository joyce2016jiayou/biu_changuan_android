package com.noplugins.keepfit.android.bean;

import java.io.Serializable;
import java.util.List;

public class DateViewEntity {


    private List<CourseListBean> courseList;

    public List<CourseListBean> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseListBean> courseList) {
        this.courseList = courseList;
    }

    public static class CourseListBean implements Serializable {
        /**
         * pkname : id
         * id : 3
         * ordNum : CUS456
         * userNum : CUS19081922275624
         * areaNum : GYM19081634685143
         * courseNum : GYM7790
         * coachUserNum : Gen23456
         * areaName : 木叶训练场3
         * address : 木叶村3
         * courseType : 3
         * courseName : 木叶游泳
         * coachUserName : 自来也
         * startTime : 2019-08-19 16:38:34
         * endTime : 2019-08-19 17:38:40
         * deleted : 0
         * totalPrice : 0.0
         * status : 1
         * note : null
         * createDate : null
         * updateDate : 2019-08-19 20:39:57
         * past : null
         * classDur : 16:38-17:38
         * classStart : 16:38
         * sprotLog : 0
         * dayViewPast : 1
         */

        private String pkname;
        private int id;
        private String ordNum;
        private String userNum;
        private String areaNum;
        private String courseNum;
        private String coachUserNum;
        private String areaName;
        private String address;
        private int courseType;
        private String courseName;
        private String coachUserName;
        private String startTime;
        private String endTime;
        private int deleted;
        private double totalPrice;
        private String status;
        private Object note;
        private Object createDate;
        private String updateDate;
        private Object past;
        private String classDur;
        private String classStart;
        private int sprotLog;
        private int dayViewPast;
        private String sprotNum;
        /**
         * custOrderItemNum : CUS19091123478081
         * teacherTime : 0
         * checkIn : 0
         * checkOut : 0
         * price : 1
         * status : 1
         * createDate : 2019-09-11 09:46:55
         * beforeFace : 0
         * afterFace : 0
         */

        private String custOrderItemNum;
        private int teacherTime;
        private int checkIn;
        private int checkOut;
        private int price;

        private int beforeFace;
        private int afterFace;

        public String getSprotNum() {
            return sprotNum;
        }

        public void setSprotNum(String sprotNum) {
            this.sprotNum = sprotNum;
        }

        public String getCustUserNum() {
            return custUserNum;
        }

        public void setCustUserNum(String custUserNum) {
            this.custUserNum = custUserNum;
        }

        private String custUserNum;


        public String getCustOrderNum() {
            return custOrderNum;
        }

        public void setCustOrderNum(String custOrderNum) {
            this.custOrderNum = custOrderNum;
        }

        private String custOrderNum;
        public String getPkname() {
            return pkname;
        }

        public void setPkname(String pkname) {
            this.pkname = pkname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrdNum() {
            return ordNum;
        }

        public void setOrdNum(String ordNum) {
            this.ordNum = ordNum;
        }

        public String getUserNum() {
            return userNum;
        }

        public void setUserNum(String userNum) {
            this.userNum = userNum;
        }

        public String getAreaNum() {
            return areaNum;
        }

        public void setAreaNum(String areaNum) {
            this.areaNum = areaNum;
        }

        public String getCourseNum() {
            return courseNum;
        }

        public void setCourseNum(String courseNum) {
            this.courseNum = courseNum;
        }

        public String getCoachUserNum() {
            return coachUserNum;
        }

        public void setCoachUserNum(String coachUserNum) {
            this.coachUserNum = coachUserNum;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCourseType() {
            return courseType;
        }

        public void setCourseType(int courseType) {
            this.courseType = courseType;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCoachUserName() {
            return coachUserName;
        }

        public void setCoachUserName(String coachUserName) {
            this.coachUserName = coachUserName;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getNote() {
            return note;
        }

        public void setNote(Object note) {
            this.note = note;
        }

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public Object getPast() {
            return past;
        }

        public void setPast(Object past) {
            this.past = past;
        }

        public String getClassDur() {
            return classDur;
        }

        public void setClassDur(String classDur) {
            this.classDur = classDur;
        }

        public String getClassStart() {
            return classStart;
        }

        public void setClassStart(String classStart) {
            this.classStart = classStart;
        }

        public int getSprotLog() {
            return sprotLog;
        }

        public void setSprotLog(int sprotLog) {
            this.sprotLog = sprotLog;
        }

        public int getDayViewPast() {
            return dayViewPast;
        }

        public void setDayViewPast(int dayViewPast) {
            this.dayViewPast = dayViewPast;
        }

        public String getCustOrderItemNum() {
            return custOrderItemNum;
        }

        public void setCustOrderItemNum(String custOrderItemNum) {
            this.custOrderItemNum = custOrderItemNum;
        }

        public int getTeacherTime() {
            return teacherTime;
        }

        public void setTeacherTime(int teacherTime) {
            this.teacherTime = teacherTime;
        }

        public int getCheckIn() {
            return checkIn;
        }

        public void setCheckIn(int checkIn) {
            this.checkIn = checkIn;
        }

        public int getCheckOut() {
            return checkOut;
        }

        public void setCheckOut(int checkOut) {
            this.checkOut = checkOut;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getBeforeFace() {
            return beforeFace;
        }

        public void setBeforeFace(int beforeFace) {
            this.beforeFace = beforeFace;
        }

        public int getAfterFace() {
            return afterFace;
        }

        public void setAfterFace(int afterFace) {
            this.afterFace = afterFace;
        }
    }
}
