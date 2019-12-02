package com.noplugins.keepfit.android.bean;


import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * TODO<json数据源>
 *
 * @author: 小嵩
 * @date: 2017/3/16 15:36
 */

public class JsonBean implements IPickerViewData {


    /**
     * name : 省份
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */

    private String name;

    public String getName_code() {
        return name_code;
    }

    public void setName_code(String name_code) {
        this.name_code = name_code;
    }

    private String name_code;
    private List<CityBean> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCityList() {
        return city;
    }

    public void setCityList(List<CityBean> city) {
        this.city = city;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return this.name;
    }


    public static class CityBean {
        /**
         * name : 城市
         * area : ["东城区","西城区","崇文区","昌平区"]
         */

        private String name;
        private String name_code;

        public String getName_code() {
            return name_code;
        }

        public void setName_code(String name_code) {
            this.name_code = name_code;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private List<QuBean> area;

        public List<QuBean> getArea() {
            return area;
        }

        public void setArea(List<QuBean> area) {
            this.area = area;
        }
    }

    public static class QuBean{
        private String name;

        public String getName() {
            return name;
        }

        public String getName_code() {
            return name_code;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setName_code(String name_code) {
            this.name_code = name_code;
        }

        private String name_code;
    }
}
