package com.noplugins.keepfit.android.bean;

public class CheckBean {
    public String getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(String areaNum) {
        this.areaNum = areaNum;
    }

    private String areaNum;
    private int checkResult;

    public int getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(int checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckReason() {
        return checkReason;
    }

    public void setCheckReason(String checkReason) {
        this.checkReason = checkReason;
    }

    private String checkReason;
}
