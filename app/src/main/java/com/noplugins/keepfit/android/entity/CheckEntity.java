package com.noplugins.keepfit.android.entity;

public class CheckEntity {
    private String gymAreaNum;
    private String haveMember;
    private int status;
    private int highTime;

    public int getHighTime() {
        return highTime;
    }

    public void setHighTime(int highTime) {
        this.highTime = highTime;
    }

    public String getGymAreaNum() {
        return gymAreaNum;
    }

    public void setGymAreaNum(String gymAreaNum) {
        this.gymAreaNum = gymAreaNum;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getHaveMember() {
        return haveMember;
    }

    public void setHaveMember(String haveMember) {
        this.haveMember = haveMember;
    }

}
