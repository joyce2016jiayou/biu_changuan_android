package com.noplugins.keepfit.android.entity;

public class CoordinateBean {

    public CoordinateBean(float x, float y) {
        this.x = x;
        this.y = y;
    }

    private float x;
    private float y;

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
