package com.othershe.calendarview.bean;

public class DateBean {
    private int[] solar;//阳历年、月、日
    private String[] lunar;//农历月、日
    private String solarHoliday;//阳历节假日
    private String lunarHoliday;//阳历节假日
    private int type;//0:上月，1:当月，2:下月
    private String term;//节气
    private boolean is_out_class;//课程是否过期


    public boolean isIs_show_current_day_color() {
        return is_show_current_day_color;
    }

    public void setIs_show_current_day_color(boolean is_show_current_day_color) {
        this.is_show_current_day_color = is_show_current_day_color;
    }

    private boolean is_show_current_day_color;

    public boolean isIs_show_circle() {
        return is_show_circle;
    }

    public void setIs_show_circle(boolean is_show_circle) {
        this.is_show_circle = is_show_circle;
    }

    private boolean is_show_circle;
    private String class_number;

    public String getClass_number() {
        return class_number;
    }

    public void setClass_number(String class_number) {
        this.class_number = class_number;
    }

    public String getClass_people_number() {
        return class_people_number;
    }

    public void setClass_people_number(String class_people_number) {
        this.class_people_number = class_people_number;
    }

    private String class_people_number;
    public boolean isIs_have_class() {
        return is_have_class;
    }

    public void setIs_have_class(boolean is_have_class) {
        this.is_have_class = is_have_class;
    }

    private boolean is_have_class;//是否是将来的课程

    public boolean isIs_out_class() {
        return is_out_class;
    }

    public void setIs_out_class(boolean is_out_class) {
        this.is_out_class = is_out_class;
    }


    public int[] getSolar() {
        return solar;
    }

    public void setSolar(int year, int month, int day) {
        this.solar = new int[]{year, month, day};
    }

    public String[] getLunar() {
        return lunar;
    }

    public void setLunar(String[] lunar) {
        this.lunar = lunar;
    }

    public String getSolarHoliday() {
        return solarHoliday;
    }

    public void setSolarHoliday(String solarHoliday) {
        this.solarHoliday = solarHoliday;
    }

    public String getLunarHoliday() {
        return lunarHoliday;
    }

    public void setLunarHoliday(String lunarHoliday) {
        this.lunarHoliday = lunarHoliday;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
