package com.noplugins.keepfit.android.bean;

public class SelectChangGuanBean {

    /**
     * address : 上海市静安寺
     * isFront : true
     * areaName : 小杨场馆
     * areaNum : GYM19120577383652
     * start : 09:00:00
     * end : 23:59:00
     */

    private String address;
    private boolean isFront;
    private String areaName;
    private String areaNum;
    private String start;
    private String end;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    private boolean check;
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isIsFront() {
        return isFront;
    }

    public void setIsFront(boolean isFront) {
        this.isFront = isFront;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(String areaNum) {
        this.areaNum = areaNum;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
