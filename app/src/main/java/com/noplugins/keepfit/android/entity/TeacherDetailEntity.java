package com.noplugins.keepfit.android.entity;

import java.util.List;

public class TeacherDetailEntity {
    /**
     * teacherNum : Gen123
     * teacherName : 测试
     * phone : 123
     * password :
     * card :
     * logoUrl : null
     * teacherType : null
     * tips : null
     * skill : 1,2,3,4
     * serviceDur : null
     * createDate : 1563862366000
     * updateDate : 2019-07-23 14:12:51
     * deleted : 0
     * inviteType : 已邀请
     */

    private String teacherNum;
    private String teacherName;
    private String phone;
    private String password;
    private String card;
    private String logoUrl;
    private Object teacherType;
    private String tips;
    private Object serviceDur;
    private long createDate;
    private String updateDate;
    private int deleted;
    private String inviteType;

    public List<String> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<String> labelList) {
        this.labelList = labelList;
    }

    private List<String> labelList;
    public List<String> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<String> skillList) {
        this.skillList = skillList;
    }

    private List<String> skillList;
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Object getTeacherType() {
        return teacherType;
    }

    public void setTeacherType(Object teacherType) {
        this.teacherType = teacherType;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Object getServiceDur() {
        return serviceDur;
    }

    public void setServiceDur(Object serviceDur) {
        this.serviceDur = serviceDur;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
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

    public String getInviteType() {
        return inviteType;
    }

    public void setInviteType(String inviteType) {
        this.inviteType = inviteType;
    }

}
