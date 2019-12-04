package com.noplugins.keepfit.android.bean;


import java.util.List;

public class GetCityCode {


    private List<CityBean> city;

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    public static class CityBean {
        /**
         * citycd : 110100
         * citynm : 北京市
         * prvnccd : 110000
         */

        private String citycd;
        private String citynm;
        private String prvnccd;

        public String getCitycd() {
            return citycd;
        }

        public void setCitycd(String citycd) {
            this.citycd = citycd;
        }

        public String getCitynm() {
            return citynm;
        }

        public void setCitynm(String citynm) {
            this.citynm = citynm;
        }

        public String getPrvnccd() {
            return prvnccd;
        }

        public void setPrvnccd(String prvnccd) {
            this.prvnccd = prvnccd;
        }
    }
}

