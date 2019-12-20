package com.noplugins.keepfit.android.bean.use;

import java.util.List;

public class TeamDetailBean {

    /**
     * teacher : {"updateDate":"2019-08-19 10:14:22","teacherName":"卡卡西","year":1,"sex":1,"label":"1,2,3","logoUrl":"http://qnimg.ahcomg.com/teacherDefault","teacherType":2,"labelList":["专业","阳光","细致"],"password":"","deleted":0,"phone":"123","grade":980,"skill":"1,2,3,4,5","teacherNum":"Gen123","finalGrade":9.8,"serviceDur":100,"card":"","createDate":"2019-08-19 10:14:22"}
     * course : {"gymAreaNum":"GYM19081634685143","courseType":1,"updateDate":"2019-08-19 10:48:38","comeNum":0,"latitude":31.208032,"type":"1","maxNum":20,"checkStatus":1,"areaName":"场馆名称9","price":2900,"loop":false,"startTime":1566182903000,"id":3,"gymPlaceNum":"GYM19081657827460","createDate":"2019-08-19 10:48:38","longitude":121.468417,"address":"上海田子坊","applyNum":10,"target":1,"imgUrl":"http://qnimg.ahcomg.com/courseDefault","difficulty":1,"courseName":"卡卡西团课","deleted":false,"courseNum":"GYM779","phone":"123456","genTeacherNum":"Gen123","endTime":1566186512000,"classType":1,"courseTime":1,"status":1}
     */

    private TeacherBean teacher;
    private CourseBean course;

    public TeacherBean getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherBean teacher) {
        this.teacher = teacher;
    }

    public CourseBean getCourse() {
        return course;
    }

    public void setCourse(CourseBean course) {
        this.course = course;
    }

    public static class TeacherBean {
        /**
         * updateDate : 2019-08-19 10:14:22
         * teacherName : 卡卡西
         * year : 1
         * sex : 1
         * label : 1,2,3
         * logoUrl : http://qnimg.ahcomg.com/teacherDefault
         * teacherType : 2
         * labelList : ["专业","阳光","细致"]
         * password :
         * deleted : 0
         * phone : 123
         * grade : 980
         * skill : 1,2,3,4,5
         * teacherNum : Gen123
         * finalGrade : 9.8
         * serviceDur : 100
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
        private String password;
        private int deleted;
        private String phone;
        private int grade;
        private String skill;
        private String teacherNum;
        private double finalGrade;
        private int serviceDur;
        private String card;
        private String createDate;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public double getFinalGrade() {
            return finalGrade;
        }

        public void setFinalGrade(double finalGrade) {
            this.finalGrade = finalGrade;
        }

        public int getServiceDur() {
            return serviceDur;
        }

        public void setServiceDur(int serviceDur) {
            this.serviceDur = serviceDur;
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

        public List<String> getLabelList() {
            return labelList;
        }

        public void setLabelList(List<String> labelList) {
            this.labelList = labelList;
        }
    }

    public static class CourseBean {
        /**
         * gymAreaNum : GYM19081634685143
         * courseType : 1
         * updateDate : 2019-08-19 10:48:38
         * comeNum : 0
         * latitude : 31.208032
         * type : 1
         * maxNum : 20
         * checkStatus : 1
         * areaName : 场馆名称9
         * price : 2900
         * loop : false
         * startTime : 1566182903000
         * id : 3
         * gymPlaceNum : GYM19081657827460
         * createDate : 2019-08-19 10:48:38
         * longitude : 121.468417
         * address : 上海田子坊
         * applyNum : 10
         * target : 1
         * imgUrl : http://qnimg.ahcomg.com/courseDefault
         * difficulty : 1
         * courseName : 卡卡西团课
         * deleted : false
         * courseNum : GYM779
         * phone : 123456
         * genTeacherNum : Gen123
         * endTime : 1566186512000
         * classType : 1
         * courseTime : 1
         * status : 1
         */

        private String gymAreaNum;
        private int courseType;
        private String updateDate;
        private int comeNum;
        private double latitude;
        private String type;
        private int maxNum;
        private int checkStatus;
        private String areaName;
        private int price;
        private boolean loop;
        private long startTime;
        private int id;
        private String gymPlaceNum;
        private String createDate;
        private double longitude;
        private String address;
        private int applyNum;
        private int target;
        private String imgUrl;
        private int difficulty;
        private String courseName;
        private boolean deleted;
        private String courseNum;
        private String phone;
        private String genTeacherNum;
        private long endTime;
        private int classType;
        private int courseTime;
        private int status;

        private String courseDes;
        private String tips;
        private String suitPerson;
        private double finalPrice;
        private int fullPerson;

        public int getFullPerson() {
            return fullPerson;
        }

        public void setFullPerson(int fullPerson) {
            this.fullPerson = fullPerson;
        }

        private List<String> urls;

        public List<String> getUrls() {
            return urls;
        }

        public void setUrls(List<String> urls) {
            this.urls = urls;
        }

        public double getFinalPrice() {
            return finalPrice;
        }

        public void setFinalPrice(double finalPrice) {
            this.finalPrice = finalPrice;
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

        public String getSuitPerson() {
            return suitPerson;
        }

        public void setSuitPerson(String suitPerson) {
            this.suitPerson = suitPerson;
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

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
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

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
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

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getGenTeacherNum() {
            return genTeacherNum;
        }

        public void setGenTeacherNum(String genTeacherNum) {
            this.genTeacherNum = genTeacherNum;
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
