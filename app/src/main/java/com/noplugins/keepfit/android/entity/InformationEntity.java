package com.noplugins.keepfit.android.entity;

import java.util.List;

public class InformationEntity {

    /**
     * area_name : 场馆名称
     * type : 2
     * area : 900
     * address : 上海
     * business_start : 12:23:00
     * business_end : 20:23:00
     * phone : 123
     * email : 场馆联系邮箱
     * facility : 1,2,3
     * legal_person : 法人姓名
     * card_num : 232323232
     * company_name : 企业名称
     * company_code : xxx223232
     * gymPlaces : [{"max_num":20,"place_name":"test01"},{"max_num":30,"place_name":"test02"}]
     * gym_pic : [{"order_num":1,"url":"www.logo99.com"},{"order_num":"2","url":"www.url2.com"},{"order_num":"3","url":"www.url6.com"}]
     */

    private String area_num;

    public String getArea_num() {
        return area_num;
    }

    public void setArea_num(String area_num) {
        this.area_num = area_num;
    }

    private String area_name;
    private int type;
    private int area;
    private String address;
    private String business_start;
    private String business_end;
    private String phone;
    private String email;
    private String facility;
    private String legal_person;
    private String card_num;
    private String company_name;
    private String company_code;
    private List<GymPlacesBean> gymPlaces;
    private List<GymPicBean> gym_pic;
    private String province;
    private String city;
    private String district;
    private String bankName;
    private String bank_card_num;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBank_card_num() {
        return bank_card_num;
    }

    public void setBank_card_num(String bank_card_num) {
        this.bank_card_num = bank_card_num;
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusiness_start() {
        return business_start;
    }

    public void setBusiness_start(String business_start) {
        this.business_start = business_start;
    }

    public String getBusiness_end() {
        return business_end;
    }

    public void setBusiness_end(String business_end) {
        this.business_end = business_end;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getLegal_person() {
        return legal_person;
    }

    public void setLegal_person(String legal_person) {
        this.legal_person = legal_person;
    }

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public List<GymPlacesBean> getGymPlaces() {
        return gymPlaces;
    }

    public void setGymPlaces(List<GymPlacesBean> gymPlaces) {
        this.gymPlaces = gymPlaces;
    }

    public List<GymPicBean> getGym_pic() {
        return gym_pic;
    }

    public void setGym_pic(List<GymPicBean> gym_pic) {
        this.gym_pic = gym_pic;
    }

    public static class GymPlacesBean {
        /**
         * max_num : 20
         * place_name : test01
         */

        private int max_num;

        public String getPlace_type() {
            return place_type;
        }

        public void setPlace_type(String place_type) {
            this.place_type = place_type;
        }

        private String place_type;

        private String place_num;

        public String getPlace_num() {
            return place_num;
        }

        public void setPlace_num(String place_num) {
            this.place_num = place_num;
        }

        public int getMax_num() {
            return max_num;
        }

        public void setMax_num(int max_num) {
            this.max_num = max_num;
        }



    }

    public static class GymPicBean {
        /**
         * order_num : 1
         * url : www.logo99.com
         */

        private int order_num;
        private String url;
        private String qiniu_key;

        public String getQiniu_key() {
            return qiniu_key;
        }

        public void setQiniu_key(String qiniu_key) {
            this.qiniu_key = qiniu_key;
        }

        public int getOrder_num() {
            return order_num;
        }

        public void setOrder_num(int order_num) {
            this.order_num = order_num;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
