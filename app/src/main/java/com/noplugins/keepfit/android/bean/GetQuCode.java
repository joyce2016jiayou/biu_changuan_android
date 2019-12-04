package com.noplugins.keepfit.android.bean;

import java.util.List;

public class GetQuCode {

    private List<AreaBean> area;

    public List<AreaBean> getArea() {
        return area;
    }

    public void setArea(List<AreaBean> area) {
        this.area = area;
    }

    public static class AreaBean {
        /**
         * citycd : 120100
         * distcd : 120101
         * distnm : 和平区
         * prvnccd : 120000
         */

        private String citycd;
        private String distcd;
        private String distnm;
        private String prvnccd;

        public String getCitycd() {
            return citycd;
        }

        public void setCitycd(String citycd) {
            this.citycd = citycd;
        }

        public String getDistcd() {
            return distcd;
        }

        public void setDistcd(String distcd) {
            this.distcd = distcd;
        }

        public String getDistnm() {
            return distnm;
        }

        public void setDistnm(String distnm) {
            this.distnm = distnm;
        }

        public String getPrvnccd() {
            return prvnccd;
        }

        public void setPrvnccd(String prvnccd) {
            this.prvnccd = prvnccd;
        }
    }
}
