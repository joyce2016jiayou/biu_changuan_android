package com.noplugins.keepfit.android.bean.use;

import java.util.List;

public class ManagerBean {

    private List<CourseListBean> courseList;

    public List<CourseListBean> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseListBean> courseList) {
        this.courseList = courseList;
    }

    public class CourseListBean {
        /**
         * gymAreaNum : GYM19091216883274
         * courseType : 2
         * updateDate : 2019-09-12 13:31:00
         * comeNum : 0
         * type : 2
         * maxNum : 20
         * putaway : 1
         * tips : 警告 瑜伽与其他运动一样在不正确的练习下是会给身体带来一定伤害的,需在专业人士指导下练习瑜伽。
         * checkStatus : 1
         * price : 3
         * loop : false
         * startTime : 1568894400000
         * id : 20
         * gymPlaceNum :
         * createDate : 2019-09-12 13:31:00
         * buynum : 1
         * courseDes : 哈他瑜伽 哈他又名传统瑜伽,在哈他(Hatha)这个词中,“哈”(ha)的意思是太阳,“他”(tha)的意思是月亮。它代表男与女,日与夜,阴与阳
         * applyNum : 20
         * target : 1
         * imgUrl : http://qnimg.ahcomg.com/yoga1
         * difficulty : 1
         * courseName : Yoga 瑜伽
         * deleted : false
         * courseNum : GYM190912793854392
         * genTeacherNum : GEN23456
         * checkInStatus : 0
         * suitPerson : 16岁以上
         * endTime : 1568638800000
         * classType : 5
         * checkOutStatus : 0
         * status : 4
         */



        private String gymAreaNum;
        private int courseType;
        private String updateDate;
        private int comeNum;
        private String type;
        private int maxNum;
        private int putaway;
        private String tips;
        private int checkStatus;
        private int price;
        private boolean loop;
        private int loopCycle;
        private long startTime;
        private int id;
        private String gymPlaceNum;
        private String createDate;
        private int buynum;
        private String courseDes;
        private int applyNum;
        private int target;
        private String imgUrl;
        private int difficulty;
        private String courseName;
        private boolean deleted;
        private String courseNum;
        private String genTeacherNum;
        private int checkInStatus;
        private String suitPerson;
        private long endTime;
        private int classType;
        private int checkOutStatus;
        private int status;
        /**
         * week : 周三
         * areaName : 舒服堡
         * day : 2019-09-11
         * searchType : 1
         * nowWeek : 2
         * time : 20:00-21:00
         */

        private String week;
        private String areaName;
        private String day;
        private int searchType;
        private int nowWeek;
        private String time;
        /**
         * min : 60
         */

        private int min;
        private double finalPrice;

        public double getFinalPrice() {
            return finalPrice;
        }

        public void setFinalPrice(double finalPrice) {
            this.finalPrice = finalPrice;
        }

        public int getLoopCycle() {
            return loopCycle;
        }

        public void setLoopCycle(int loopCycle) {
            this.loopCycle = loopCycle;
        }

        public String getGymAreaNum() {
            return gymAreaNum;
        }

        public void setGymAreaNum(String gymAreaNum) {
            this.gymAreaNum = gymAreaNum;
        }

        public int getCourseType() {
            return courseType;
        }

        public void setCourseType(int courseType) {
            this.courseType = courseType;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public int getComeNum() {
            return comeNum;
        }

        public void setComeNum(int comeNum) {
            this.comeNum = comeNum;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getMaxNum() {
            return maxNum;
        }

        public void setMaxNum(int maxNum) {
            this.maxNum = maxNum;
        }

        public int getPutaway() {
            return putaway;
        }

        public void setPutaway(int putaway) {
            this.putaway = putaway;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public boolean isLoop() {
            return loop;
        }

        public void setLoop(boolean loop) {
            this.loop = loop;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGymPlaceNum() {
            return gymPlaceNum;
        }

        public void setGymPlaceNum(String gymPlaceNum) {
            this.gymPlaceNum = gymPlaceNum;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getBuynum() {
            return buynum;
        }

        public void setBuynum(int buynum) {
            this.buynum = buynum;
        }

        public String getCourseDes() {
            return courseDes;
        }

        public void setCourseDes(String courseDes) {
            this.courseDes = courseDes;
        }

        public int getApplyNum() {
            return applyNum;
        }

        public void setApplyNum(int applyNum) {
            this.applyNum = applyNum;
        }

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public String getCourseNum() {
            return courseNum;
        }

        public void setCourseNum(String courseNum) {
            this.courseNum = courseNum;
        }

        public String getGenTeacherNum() {
            return genTeacherNum;
        }

        public void setGenTeacherNum(String genTeacherNum) {
            this.genTeacherNum = genTeacherNum;
        }

        public int getCheckInStatus() {
            return checkInStatus;
        }

        public void setCheckInStatus(int checkInStatus) {
            this.checkInStatus = checkInStatus;
        }

        public String getSuitPerson() {
            return suitPerson;
        }

        public void setSuitPerson(String suitPerson) {
            this.suitPerson = suitPerson;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public int getClassType() {
            return classType;
        }

        public void setClassType(int classType) {
            this.classType = classType;
        }

        public int getCheckOutStatus() {
            return checkOutStatus;
        }

        public void setCheckOutStatus(int checkOutStatus) {
            this.checkOutStatus = checkOutStatus;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public int getSearchType() {
            return searchType;
        }

        public void setSearchType(int searchType) {
            this.searchType = searchType;
        }

        public int getNowWeek() {
            return nowWeek;
        }

        public void setNowWeek(int nowWeek) {
            this.nowWeek = nowWeek;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }
}
