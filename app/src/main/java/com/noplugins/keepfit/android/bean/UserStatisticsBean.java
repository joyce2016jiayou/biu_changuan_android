package com.noplugins.keepfit.android.bean;

import java.util.List;

public class UserStatisticsBean {
    private int total;
    private List<ItemBean> age;
    private List<ItemBean> sex;
    private List<ItemBean> time;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ItemBean> getAge() {
        return age;
    }

    public void setAge(List<ItemBean> age) {
        this.age = age;
    }

    public List<ItemBean> getSex() {
        return sex;
    }

    public void setSex(List<ItemBean> sex) {
        this.sex = sex;
    }

    public List<ItemBean> getTime() {
        return time;
    }

    public void setTime(List<ItemBean> time) {
        this.time = time;
    }

    public class ItemBean{
        private int num;
        private String percent;
        private String value;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
