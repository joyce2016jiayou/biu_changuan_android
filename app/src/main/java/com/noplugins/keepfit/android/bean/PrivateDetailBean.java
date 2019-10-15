package com.noplugins.keepfit.android.bean;

import java.util.List;

public class PrivateDetailBean {

    /**
     * courseList : [{"gymAreaNum":"GYM19081634685143","reason":"","courseType":2,"updateDate":"2019-08-19 10:48:38","extendBigint02":null,"comeNum":0,"extendBigint01":null,"extendBigint04":null,"extendBigint03":null,"extendBigint05":null,"extendDec01":null,"remark":"","extendDec02":null,"extendDec03":null,"type":"1","maxNum":20,"extendDec04":null,"extendVar03":null,"extendDec05":null,"extendVar04":null,"tips":"","extendVar05":null,"checkStatus":1,"extendVar01":null,"extendVar02":null,"price":3900,"loop":false,"lable":null,"startTime":1565837303000,"id":1,"gymPlaceNum":"GYM19081657827460","createDate":"2019-08-19 10:48:38","teacherTime":1,"courseDes":"","applyNum":10,"target":1,"imgUrl":" ","difficulty":1,"courseName":"卡卡西私教","deleted":false,"courseNum":"GYM777","genTeacherNum":"Gen123","suitPerson":null,"endTime":1566186512000,"classType":null,"loopCycle":null,"courseTime":1,"status":1},{"gymAreaNum":"GYM19081634685143","reason":null,"courseType":2,"updateDate":"2019-08-19 10:48:38","extendBigint02":null,"comeNum":0,"extendBigint01":null,"extendBigint04":null,"extendBigint03":null,"extendBigint05":null,"extendDec01":null,"remark":null,"extendDec02":null,"extendDec03":null,"type":"1","maxNum":20,"extendDec04":null,"extendVar03":null,"extendDec05":null,"extendVar04":null,"tips":null,"extendVar05":null,"checkStatus":1,"extendVar01":null,"extendVar02":null,"price":2900,"loop":false,"lable":null,"startTime":1565664503000,"id":2,"gymPlaceNum":"GYM19081657827460","createDate":"2019-08-19 10:48:38","teacherTime":2,"courseDes":null,"applyNum":10,"target":1,"imgUrl":" ","difficulty":1,"courseName":"卡卡西私教2","deleted":false,"courseNum":"GYM778","genTeacherNum":"Gen123","suitPerson":null,"endTime":1565668112000,"classType":null,"loopCycle":null,"courseTime":1,"status":1}]
     * teacherDetail : {"updateDate":"2019-08-19 10:14:22","teacherName":"卡卡西","skillList":["增肌减脂","尊巴","动感单车","体适能","瑜伽"],"year":1,"sex":1,"label":"1,2,3","logoUrl":"http://qnimg.ahcomg.com/AllDefaultLogo","teacherType":2,"labelList":["专业","阳光","细致"],"deleted":0,"phone":"123","grade":980,"skill":"1,2,3,4,5","teacherNum":"Gen123","serviceDur":100,"collect":0,"card":"","createDate":"2019-08-19 10:14:22"}
     */

    private TeacherDetailBean teacherDetail;
    private List<CourseListBean> courseList;

    public TeacherDetailBean getTeacherDetail() {
        return teacherDetail;
    }

    public void setTeacherDetail(TeacherDetailBean teacherDetail) {
        this.teacherDetail = teacherDetail;
    }

    public List<CourseListBean> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseListBean> courseList) {
        this.courseList = courseList;
    }

    public static class TeacherDetailBean {
        /**
         * updateDate : 2019-08-19 10:14:22
         * teacherName : 卡卡西
         * skillList : ["增肌减脂","尊巴","动感单车","体适能","瑜伽"]
         * year : 1
         * sex : 1
         * label : 1,2,3
         * logoUrl : http://qnimg.ahcomg.com/AllDefaultLogo
         * teacherType : 2
         * labelList : ["专业","阳光","细致"]
         * deleted : 0
         * phone : 123
         * grade : 980
         * skill : 1,2,3,4,5
         * teacherNum : Gen123
         * serviceDur : 100
         * collect : 0
         * card :
         * createDate : 2019-08-19 10:14:22
         */

        private String updateDate;
        private String teacherName;
        private int year;
        private int sex;
        private String label;
        private String logoUrl;
        private int teacherType;
        private int deleted;
        private String phone;
        private int grade;
        private String skill;
        private String teacherNum;
        private int serviceDur;
        private int collect;
        private String card;
        private String createDate;
        private List<String> skillList;
        private List<String> labelList;

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public int getTeacherType() {
            return teacherType;
        }

        public void setTeacherType(int teacherType) {
            this.teacherType = teacherType;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getSkill() {
            return skill;
        }

        public void setSkill(String skill) {
            this.skill = skill;
        }

        public String getTeacherNum() {
            return teacherNum;
        }

        public void setTeacherNum(String teacherNum) {
            this.teacherNum = teacherNum;
        }

        public int getServiceDur() {
            return serviceDur;
        }

        public void setServiceDur(int serviceDur) {
            this.serviceDur = serviceDur;
        }

        public int getCollect() {
            return collect;
        }

        public void setCollect(int collect) {
            this.collect = collect;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public List<String> getSkillList() {
            return skillList;
        }

        public void setSkillList(List<String> skillList) {
            this.skillList = skillList;
        }

        public List<String> getLabelList() {
            return labelList;
        }

        public void setLabelList(List<String> labelList) {
            this.labelList = labelList;
        }
    }

    public static class CourseListBean {
        /**
         * gymAreaNum : GYM19081634685143
         * reason :
         * courseType : 2
         * updateDate : 2019-08-19 10:48:38
         * extendBigint02 : null
         * comeNum : 0
         * extendBigint01 : null
         * extendBigint04 : null
         * extendBigint03 : null
         * extendBigint05 : null
         * extendDec01 : null
         * remark :
         * extendDec02 : null
         * extendDec03 : null
         * type : 1
         * maxNum : 20
         * extendDec04 : null
         * extendVar03 : null
         * extendDec05 : null
         * extendVar04 : null
         * tips :
         * extendVar05 : null
         * checkStatus : 1
         * extendVar01 : null
         * extendVar02 : null
         * price : 3900
         * loop : false
         * lable : null
         * startTime : 1565837303000
         * id : 1
         * gymPlaceNum : GYM19081657827460
         * createDate : 2019-08-19 10:48:38
         * teacherTime : 1
         * courseDes :
         * applyNum : 10
         * target : 1
         * imgUrl :
         * difficulty : 1
         * courseName : 卡卡西私教
         * deleted : false
         * courseNum : GYM777
         * genTeacherNum : Gen123
         * suitPerson : null
         * endTime : 1566186512000
         * classType : null
         * loopCycle : null
         * courseTime : 1
         * status : 1
         */

        private String gymAreaNum;
        private String reason;
        private int courseType;
        private String updateDate;
        private Object extendBigint02;
        private int comeNum;
        private Object extendBigint01;
        private Object extendBigint04;
        private Object extendBigint03;
        private Object extendBigint05;
        private Object extendDec01;
        private String remark;
        private Object extendDec02;
        private Object extendDec03;
        private String type;
        private int maxNum;
        private Object extendDec04;
        private Object extendVar03;
        private Object extendDec05;
        private Object extendVar04;
        private String tips;
        private Object extendVar05;
        private int checkStatus;
        private Object extendVar01;
        private Object extendVar02;
        private double price;
        private boolean loop;
        private Object lable;
        private long startTime;
        private int id;
        private String gymPlaceNum;
        private String createDate;
        private int teacherTime;
        private String courseDes;
        private int applyNum;
        private int target;
        private String imgUrl;
        private int difficulty;
        private String courseName;
        private boolean deleted;
        private String courseNum;
        private String genTeacherNum;
        private Object suitPerson;
        private long endTime;
        private Object classType;
        private Object loopCycle;
        private int courseTime;
        private int status;
        private double finalPrice;

        public double getFinalPrice() {
            return finalPrice;
        }

        public void setFinalPrice(double finalPrice) {
            this.finalPrice = finalPrice;
        }

        public String getGymAreaNum() {
            return gymAreaNum;
        }

        public void setGymAreaNum(String gymAreaNum) {
            this.gymAreaNum = gymAreaNum;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
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

        public Object getExtendBigint02() {
            return extendBigint02;
        }

        public void setExtendBigint02(Object extendBigint02) {
            this.extendBigint02 = extendBigint02;
        }

        public int getComeNum() {
            return comeNum;
        }

        public void setComeNum(int comeNum) {
            this.comeNum = comeNum;
        }

        public Object getExtendBigint01() {
            return extendBigint01;
        }

        public void setExtendBigint01(Object extendBigint01) {
            this.extendBigint01 = extendBigint01;
        }

        public Object getExtendBigint04() {
            return extendBigint04;
        }

        public void setExtendBigint04(Object extendBigint04) {
            this.extendBigint04 = extendBigint04;
        }

        public Object getExtendBigint03() {
            return extendBigint03;
        }

        public void setExtendBigint03(Object extendBigint03) {
            this.extendBigint03 = extendBigint03;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public Object getExtendDec04() {
            return extendDec04;
        }

        public void setExtendDec04(Object extendDec04) {
            this.extendDec04 = extendDec04;
        }

        public Object getExtendVar03() {
            return extendVar03;
        }

        public void setExtendVar03(Object extendVar03) {
            this.extendVar03 = extendVar03;
        }

        public Object getExtendDec05() {
            return extendDec05;
        }

        public void setExtendDec05(Object extendDec05) {
            this.extendDec05 = extendDec05;
        }

        public Object getExtendVar04() {
            return extendVar04;
        }

        public void setExtendVar04(Object extendVar04) {
            this.extendVar04 = extendVar04;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public Object getExtendVar05() {
            return extendVar05;
        }

        public void setExtendVar05(Object extendVar05) {
            this.extendVar05 = extendVar05;
        }

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }

        public Object getExtendVar01() {
            return extendVar01;
        }

        public void setExtendVar01(Object extendVar01) {
            this.extendVar01 = extendVar01;
        }

        public Object getExtendVar02() {
            return extendVar02;
        }

        public void setExtendVar02(Object extendVar02) {
            this.extendVar02 = extendVar02;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public boolean isLoop() {
            return loop;
        }

        public void setLoop(boolean loop) {
            this.loop = loop;
        }

        public Object getLable() {
            return lable;
        }

        public void setLable(Object lable) {
            this.lable = lable;
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

        public int getTeacherTime() {
            return teacherTime;
        }

        public void setTeacherTime(int teacherTime) {
            this.teacherTime = teacherTime;
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

        public Object getSuitPerson() {
            return suitPerson;
        }

        public void setSuitPerson(Object suitPerson) {
            this.suitPerson = suitPerson;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public Object getClassType() {
            return classType;
        }

        public void setClassType(Object classType) {
            this.classType = classType;
        }

        public Object getLoopCycle() {
            return loopCycle;
        }

        public void setLoopCycle(Object loopCycle) {
            this.loopCycle = loopCycle;
        }

        public int getCourseTime() {
            return courseTime;
        }

        public void setCourseTime(int courseTime) {
            this.courseTime = courseTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
