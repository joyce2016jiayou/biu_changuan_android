package com.noplugins.keepfit.android.entity;

public class MessageDetailEntity {


    /**
     * data : {"pkname":"id","id":1,"gymCourseCheckNum":"GYM999","gymCourseNum":"","gymAreaNum":"GYM19080489223196","gymPlaceNum":"GYM19080409493132","genTeacherNum":"Gen789","tips":"注意事项","courseDes":"课程描述","maxPerson":20,"teacherName":"教师名称","price":50,"courseName":"课程名称","checkTime":null,"createDate":"2019-08-05 10:27:13","updateDate":"2019-08-05 10:27:13","deleted":0,"checkStatus":0,"reason":"没有","remark":"","extendBigint01":null,"extendBigint02":null,"extendBigint03":null,"extendBigint04":null,"extendBigint05":null,"extendDec01":null,"extendDec02":null,"extendDec03":null,"extendDec04":null,"extendDec05":null,"extendVar02":null,"extendVar03":null,"extendVar04":null,"extendVar05":null,"extendVar01":null}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * pkname : id
         * id : 1
         * gymCourseCheckNum : GYM999
         * gymCourseNum :
         * gymAreaNum : GYM19080489223196
         * gymPlaceNum : GYM19080409493132
         * genTeacherNum : Gen789
         * tips : 注意事项
         * courseDes : 课程描述
         * maxPerson : 20
         * teacherName : 教师名称
         * price : 50
         * courseName : 课程名称
         * checkTime : null
         * createDate : 2019-08-05 10:27:13
         * updateDate : 2019-08-05 10:27:13
         * deleted : 0
         * checkStatus : 0
         * reason : 没有
         * remark :
         * extendBigint01 : null
         * extendBigint02 : null
         * extendBigint03 : null
         * extendBigint04 : null
         * extendBigint05 : null
         * extendDec01 : null
         * extendDec02 : null
         * extendDec03 : null
         * extendDec04 : null
         * extendDec05 : null
         * extendVar02 : null
         * extendVar03 : null
         * extendVar04 : null
         * extendVar05 : null
         * extendVar01 : null
         */

        private String pkname;
        private int id;
        private String gymCourseCheckNum;
        private String gymCourseNum;
        private String gymAreaNum;
        private String gymPlaceNum;
        private String genTeacherNum;
        private String tips;
        private String courseDes;
        private int maxPerson;
        private String teacherName;
        private int price;
        private String courseName;
        private Object checkTime;
        private String createDate;
        private String updateDate;
        private int deleted;
        private int checkStatus;
        private String reason;
        private String remark;
        private Object extendBigint01;
        private Object extendBigint02;
        private Object extendBigint03;
        private Object extendBigint04;
        private Object extendBigint05;
        private Object extendDec01;
        private Object extendDec02;
        private Object extendDec03;
        private Object extendDec04;
        private Object extendDec05;
        private Object extendVar02;
        private Object extendVar03;
        private Object extendVar04;
        private Object extendVar05;
        private Object extendVar01;

        public int getPlaceType() {
            return placeType;
        }

        public void setPlaceType(int placeType) {
            this.placeType = placeType;
        }

        private int placeType;
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

        public String getGymCourseCheckNum() {
            return gymCourseCheckNum;
        }

        public void setGymCourseCheckNum(String gymCourseCheckNum) {
            this.gymCourseCheckNum = gymCourseCheckNum;
        }

        public String getGymCourseNum() {
            return gymCourseNum;
        }

        public void setGymCourseNum(String gymCourseNum) {
            this.gymCourseNum = gymCourseNum;
        }

        public String getGymAreaNum() {
            return gymAreaNum;
        }

        public void setGymAreaNum(String gymAreaNum) {
            this.gymAreaNum = gymAreaNum;
        }

        public String getGymPlaceNum() {
            return gymPlaceNum;
        }

        public void setGymPlaceNum(String gymPlaceNum) {
            this.gymPlaceNum = gymPlaceNum;
        }

        public String getGenTeacherNum() {
            return genTeacherNum;
        }

        public void setGenTeacherNum(String genTeacherNum) {
            this.genTeacherNum = genTeacherNum;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getCourseDes() {
            return courseDes;
        }

        public void setCourseDes(String courseDes) {
            this.courseDes = courseDes;
        }

        public int getMaxPerson() {
            return maxPerson;
        }

        public void setMaxPerson(int maxPerson) {
            this.maxPerson = maxPerson;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public Object getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(Object checkTime) {
            this.checkTime = checkTime;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Object getExtendBigint01() {
            return extendBigint01;
        }

        public void setExtendBigint01(Object extendBigint01) {
            this.extendBigint01 = extendBigint01;
        }

        public Object getExtendBigint02() {
            return extendBigint02;
        }

        public void setExtendBigint02(Object extendBigint02) {
            this.extendBigint02 = extendBigint02;
        }

        public Object getExtendBigint03() {
            return extendBigint03;
        }

        public void setExtendBigint03(Object extendBigint03) {
            this.extendBigint03 = extendBigint03;
        }

        public Object getExtendBigint04() {
            return extendBigint04;
        }

        public void setExtendBigint04(Object extendBigint04) {
            this.extendBigint04 = extendBigint04;
        }

        public Object getExtendBigint05() {
            return extendBigint05;
        }

        public void setExtendBigint05(Object extendBigint05) {
            this.extendBigint05 = extendBigint05;
        }

        public Object getExtendDec01() {
            return extendDec01;
        }

        public void setExtendDec01(Object extendDec01) {
            this.extendDec01 = extendDec01;
        }

        public Object getExtendDec02() {
            return extendDec02;
        }

        public void setExtendDec02(Object extendDec02) {
            this.extendDec02 = extendDec02;
        }

        public Object getExtendDec03() {
            return extendDec03;
        }

        public void setExtendDec03(Object extendDec03) {
            this.extendDec03 = extendDec03;
        }

        public Object getExtendDec04() {
            return extendDec04;
        }

        public void setExtendDec04(Object extendDec04) {
            this.extendDec04 = extendDec04;
        }

        public Object getExtendDec05() {
            return extendDec05;
        }

        public void setExtendDec05(Object extendDec05) {
            this.extendDec05 = extendDec05;
        }

        public Object getExtendVar02() {
            return extendVar02;
        }

        public void setExtendVar02(Object extendVar02) {
            this.extendVar02 = extendVar02;
        }

        public Object getExtendVar03() {
            return extendVar03;
        }

        public void setExtendVar03(Object extendVar03) {
            this.extendVar03 = extendVar03;
        }

        public Object getExtendVar04() {
            return extendVar04;
        }

        public void setExtendVar04(Object extendVar04) {
            this.extendVar04 = extendVar04;
        }

        public Object getExtendVar05() {
            return extendVar05;
        }

        public void setExtendVar05(Object extendVar05) {
            this.extendVar05 = extendVar05;
        }

        public Object getExtendVar01() {
            return extendVar01;
        }

        public void setExtendVar01(Object extendVar01) {
            this.extendVar01 = extendVar01;
        }
    }
}
