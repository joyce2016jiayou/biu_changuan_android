package com.noplugins.keepfit.android.bean;

import java.util.List;

public class TeacherDetailBean {

    /**
     * teacherNum : GEN12345
     * teacherName : 廖生云
     * phone : 15612342312
     * year : 1
     * sex : 1
     * password :
     * card :
     * label : 1,2,3
     * logoUrl : http://qnimg.ahcomg.com/jiaolian1
     * teacherType : 1
     * tips : 命运由我不由天
     * grade : 980
     * skill : 1,2
     * serviceDur : 1209
     * createDate : 2019-09-12 11:11:24
     * updateDate : 2019-09-12 11:11:24
     * deleted : 0
     * skillList : ["增肌减脂","尊巴"]
     * labelList : ["不错的教练","效果非常好","再来一节课"]
     * finalGrade : 9.8
     */

    private String teacherNum;
    private String teacherName;
    private String phone;
    private int year;
    private int sex;
    private String password;
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
    private double finalGrade;
    private List<String> skillList;
    private List<String> labelList;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public double getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(double finalGrade) {
        this.finalGrade = finalGrade;
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
