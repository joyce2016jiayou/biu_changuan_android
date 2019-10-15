package com.noplugins.keepfit.android.bean.mine;

public class MineFunctionBean {
    private String name;
    private int drawImg;

    public MineFunctionBean(String name, int drawImg) {
        this.name = name;
        this.drawImg = drawImg;
    }

    public MineFunctionBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawImg() {
        return drawImg;
    }

    public void setDrawImg(int drawImg) {
        this.drawImg = drawImg;
    }
}
