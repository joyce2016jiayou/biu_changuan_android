package com.noplugins.keepfit.android.entity;

public class HightLowTimeEntity {

    /**
     * gym_area_num : GYM19072946802499
     * high_time_start : 10:00
     * high_time_end : 11:00
     * high_time_price : 60
     * low_time_start : 20:00
     * low_time_end : 21:00
     * low_time_price : 20
     */

    private String gym_area_num;
    private String high_time_start;
    private String high_time_end;
    private String high_time_price;
    private String normal_price;

    public HightLowTimeEntity() {

    }

    public HightLowTimeEntity(String high_time_start, String high_time_end) {
        this.high_time_start = high_time_start;
        this.high_time_end = high_time_end;
    }

    public String getGym_area_num() {
        return gym_area_num;
    }

    public void setGym_area_num(String gym_area_num) {
        this.gym_area_num = gym_area_num;
    }

    public String getHigh_time_start() {
        return high_time_start;
    }

    public void setHigh_time_start(String high_time_start) {
        this.high_time_start = high_time_start;
    }

    public String getHigh_time_end() {
        return high_time_end;
    }

    public void setHigh_time_end(String high_time_end) {
        this.high_time_end = high_time_end;
    }

    public String getHigh_time_price() {
        return high_time_price;
    }

    public void setHigh_time_price(String high_time_price) {
        this.high_time_price = high_time_price;
    }

    public String getNormal_price() {
        return normal_price;
    }

    public void setNormal_price(String normal_price) {
        this.normal_price = normal_price;
    }
}
