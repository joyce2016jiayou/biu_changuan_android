package com.noplugins.keepfit.android.bean;

import java.util.List;

public class CityCode {

    private List<AreaBean> area;
    private List<ProvinceBean> province;

    public List<AreaBean> getArea() {
        return area;
    }

    public void setArea(List<AreaBean> area) {
        this.area = area;
    }

    public List<ProvinceBean> getProvince() {
        return province;
    }

    public void setProvince(List<ProvinceBean> province) {
        this.province = province;
    }

    public static class AreaBean {
        /**
         * citycd : 130400
         * distcd : 130402
         * distnm : 邯山区
         * prvnccd : 130000
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

    public static class ProvinceBean {
        /**
         * prvncnm : 北京市
         * prvnccd : 110000
         */

        private String prvncnm;
        private String prvnccd;

        public String getPrvncnm() {
            return prvncnm;
        }

        public void setPrvncnm(String prvncnm) {
            this.prvncnm = prvncnm;
        }

        public String getPrvnccd() {
            return prvnccd;
        }

        public void setPrvnccd(String prvnccd) {
            this.prvnccd = prvnccd;
        }
    }
}
