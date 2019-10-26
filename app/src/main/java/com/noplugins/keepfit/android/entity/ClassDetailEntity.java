package com.noplugins.keepfit.android.entity;

import java.util.List;

public class ClassDetailEntity {


    /**
     * teacherList : [{"teacherNum":"GEN34567","teacherName":"张磊","phone":"18923142312","year":3,"sex":0,"card":" ","label":"3,4,5","logoUrl":"http://qnimg.ahcomg.com/jiaolian4","teacherType":2,"tips":"每一个人都拥有生命，但并非每个人都懂得生命，乃至于珍惜生命","grade":960,"skill":"3,4","serviceDur":1760,"createDate":"2019-09-12 11:11:49","updateDate":"2019-09-12 11:11:49","deleted":0,"inviteStatus":1,"gymInviteNum":"GYM19102642449792"}]
     * course : {"id":46,"courseNum":"GYM19102665489744","gymAreaNum":"GYM19102698188019","genTeacherNum":"","gymPlaceNum":"有氧操房","imgUrl":"","courseName":"金林测试团课1","type":"1","price":3500,"courseType":1,"classType":1,"loop":false,"target":4,"difficulty":1,"loopCycle":1,"maxNum":20,"suitPerson":"适合人群测试","courseDes":"测试团课介绍","tips":"注意事项测试","remark":"","reason":"","startTime":1572328800000,"endTime":1572331500000,"createDate":"2019-10-26 14:39:13","updateDate":"2019-10-26 14:39:13","comeNum":0,"status":3,"putaway":1,"deleted":false}
     */

    private CourseBean course;
    private List<TeacherListBean> teacherList;

    public CourseBean getCourse() {
        return course;
    }

    public void setCourse(CourseBean course) {
        this.course = course;
    }

    public List<TeacherListBean> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<TeacherListBean> teacherList) {
        this.teacherList = teacherList;
    }

    public static class CourseBean {
        /**
         * id : 46
         * courseNum : GYM19102665489744
         * gymAreaNum : GYM19102698188019
         * genTeacherNum :
         * gymPlaceNum : 有氧操房
         * imgUrl :
         * courseName : 金林测试团课1
         * type : 1
         * price : 3500
         * courseType : 1
         * classType : 1
         * loop : false
         * target : 4
         * difficulty : 1
         * loopCycle : 1
         * maxNum : 20
         * suitPerson : 适合人群测试
         * courseDes : 测试团课介绍
         * tips : 注意事项测试
         * remark :
         * reason :
         * startTime : 1572328800000
         * endTime : 1572331500000
         * createDate : 2019-10-26 14:39:13
         * updateDate : 2019-10-26 14:39:13
         * comeNum : 0
         * status : 3
         * putaway : 1
         * deleted : false
         */

        private int id;
        private String courseNum;
        private String gymAreaNum;
        private String genTeacherNum;
        private String gymPlaceNum;
        private String imgUrl;
        private String courseName;
        private String type;
        private int price;
        private int courseType;
        private int classType;
        private boolean loop;
        private int target;
        private int difficulty;
        private int loopCycle;
        private int maxNum;
        private String suitPerson;
        private String courseDes;
        private String tips;
        private String remark;
        private String reason;
        private long startTime;
        private long endTime;
        private String createDate;
        private String updateDate;
        private int comeNum;
        private int status;
        private int putaway;
        private boolean deleted;
        private double finalPrice;

        public double getFinalPrice() {
            return finalPrice;
        }

        public void setFinalPrice(double finalPrice) {
            this.finalPrice = finalPrice;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCourseNum() {
            return courseNum;
        }

        public void setCourseNum(String courseNum) {
            this.courseNum = courseNum;
        }

        public String getGymAreaNum() {
            return gymAreaNum;
        }

        public void setGymAreaNum(String gymAreaNum) {
            this.gymAreaNum = gymAreaNum;
        }

        public String getGenTeacherNum() {
            return genTeacherNum;
        }

        public void setGenTeacherNum(String genTeacherNum) {
            this.genTeacherNum = genTeacherNum;
        }

        public String getGymPlaceNum() {
            return gymPlaceNum;
        }

        public void setGymPlaceNum(String gymPlaceNum) {
            this.gymPlaceNum = gymPlaceNum;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getCourseType() {
            return courseType;
        }

        public void setCourseType(int courseType) {
            this.courseType = courseType;
        }

        public int getClassType() {
            return classType;
        }

        public void setClassType(int classType) {
            this.classType = classType;
        }

        public boolean isLoop() {
            return loop;
        }

        public void setLoop(boolean loop) {
            this.loop = loop;
        }

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public int getLoopCycle() {
            return loopCycle;
        }

        public void setLoopCycle(int loopCycle) {
            this.loopCycle = loopCycle;
        }

        public int getMaxNum() {
            return maxNum;
        }

        public void setMaxNum(int maxNum) {
            this.maxNum = maxNum;
        }

        public String getSuitPerson() {
            return suitPerson;
        }

        public void setSuitPerson(String suitPerson) {
            this.suitPerson = suitPerson;
        }

        public String getCourseDes() {
            return courseDes;
        }

        public void setCourseDes(String courseDes) {
            this.courseDes = courseDes;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
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

        public int getComeNum() {
            return comeNum;
        }

        public void setComeNum(int comeNum) {
            this.comeNum = comeNum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPutaway() {
            return putaway;
        }

        public void setPutaway(int putaway) {
            this.putaway = putaway;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }
    }

    public static class TeacherListBean {
        /**
         * teacherNum : GEN34567
         * teacherName : 张磊
         * phone : 18923142312
         * year : 3
         * sex : 0
         * card :
         * label : 3,4,5
         * logoUrl : http://qnimg.ahcomg.com/jiaolian4
         * teacherType : 2
         * tips : 每一个人都拥有生命，但并非每个人都懂得生命，乃至于珍惜生命
         * grade : 960
         * skill : 3,4
         * serviceDur : 1760
         * createDate : 2019-09-12 11:11:49
         * updateDate : 2019-09-12 11:11:49
         * deleted : 0
         * inviteStatus : 1
         * gymInviteNum : GYM19102642449792
         */

        private String teacherNum;
        private String teacherName;
        private String phone;
        private int year;
        private int sex;
        private String card;
        private String label;
        private String logoUrl;
        private int teacherType;
        private String tips;
        private int grade;
        private String skill;
        private int serviceDur;
        private String createDate;
        private String updateDate;
        private int deleted;
        private int inviteStatus;
        private String gymInviteNum;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
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

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
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

        public int getServiceDur() {
            return serviceDur;
        }

        public void setServiceDur(int serviceDur) {
            this.serviceDur = serviceDur;
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

        public int getInviteStatus() {
            return inviteStatus;
        }

        public void setInviteStatus(int inviteStatus) {
            this.inviteStatus = inviteStatus;
        }

        public String getGymInviteNum() {
            return gymInviteNum;
        }

        public void setGymInviteNum(String gymInviteNum) {
            this.gymInviteNum = gymInviteNum;
        }
    }
}
