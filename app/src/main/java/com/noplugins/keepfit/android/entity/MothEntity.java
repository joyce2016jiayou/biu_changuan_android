package com.noplugins.keepfit.android.entity;

import java.util.List;

public class MothEntity {

    /**
     * data : [{"count":2,"days":"20190216"},{"num":25,"count":14,"days":"20190701"},{"num":29,"count":1,"days":"20190702"},{"num":5,"count":1,"days":"20190703"},{"num":5,"count":1,"days":"20190704"},{"num":5,"count":1,"days":"20190705"},{"num":5,"count":1,"days":"20190706"},{"count":1,"days":"20190801"}]
     * code : 0
     * message : success
     */
    private List<DataBean> data;


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * count : 2
         * days : 20190216
         * num : 25
         */

        private int count;
        private String days;
        private int num;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
