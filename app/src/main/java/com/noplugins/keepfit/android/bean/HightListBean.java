package com.noplugins.keepfit.android.bean;

public class HightListBean {

    /**
     * pkname : id
     * id : 1
     * gymTimeNum : GYM19081985505377
     * gymAreaNum : GYM19081634685143
     * highTimeStart : 19:00:00
     * highTimeEnd : 21:00:00
     * normalPrice : 1
     * highTimePrice : 2
     * createDate : 2019-08-19 15:14:56
     * updateDate : 2019-08-19 15:14:56
     * deleted : 0
     */

    private String pkname;
    private int id;
    private String gymTimeNum;
    private String gymAreaNum;
    private String highTimeStart;
    private String highTimeEnd;
    private int normalPrice;
    private int highTimePrice;
    private String createDate;
    private String updateDate;
    private int deleted;

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

    public String getGymTimeNum() {
        return gymTimeNum;
    }

    public void setGymTimeNum(String gymTimeNum) {
        this.gymTimeNum = gymTimeNum;
    }

    public String getGymAreaNum() {
        return gymAreaNum;
    }

    public void setGymAreaNum(String gymAreaNum) {
        this.gymAreaNum = gymAreaNum;
    }

    public String getHighTimeStart() {
        return highTimeStart;
    }

    public void setHighTimeStart(String highTimeStart) {
        this.highTimeStart = highTimeStart;
    }

    public String getHighTimeEnd() {
        return highTimeEnd;
    }

    public void setHighTimeEnd(String highTimeEnd) {
        this.highTimeEnd = highTimeEnd;
    }

    public int getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(int normalPrice) {
        this.normalPrice = normalPrice;
    }

    public int getHighTimePrice() {
        return highTimePrice;
    }

    public void setHighTimePrice(int highTimePrice) {
        this.highTimePrice = highTimePrice;
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
}
