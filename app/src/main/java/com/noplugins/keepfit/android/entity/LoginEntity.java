package com.noplugins.keepfit.android.entity;

public class LoginEntity {
    private int type;

    private int havePayPassWord;

    public int getHavePayPassWord() {
        return havePayPassWord;
    }

    public void setHavePayPassWord(int havePayPassWord) {
        this.havePayPassWord = havePayPassWord;
    }

    public String getGymAreaNum() {
        return gymAreaNum;
    }

    public void setGymAreaNum(String gymAreaNum) {
        this.gymAreaNum = gymAreaNum;
    }

    private String gymAreaNum;


    public  int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
}
