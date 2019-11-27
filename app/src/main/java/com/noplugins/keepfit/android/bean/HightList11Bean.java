package com.noplugins.keepfit.android.bean;

import java.util.List;

public class HightList11Bean {
    private List<HightListBean> gymTimes;
    private double normalPrice;

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
