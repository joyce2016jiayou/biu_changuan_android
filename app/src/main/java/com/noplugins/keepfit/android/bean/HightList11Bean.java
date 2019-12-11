package com.noplugins.keepfit.android.bean;

import java.util.List;

public class HightList11Bean {
    private List<HightListBean> gymTimes;
    private double normalPrice;
    private double cost;

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public List<HightListBean> getGymTimes() {
        return gymTimes;
    }

    public void setGymTimes(List<HightListBean> gymTimes) {
        this.gymTimes = gymTimes;
    }

    public double getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(double normalPrice) {
        this.normalPrice = normalPrice;
    }
}
