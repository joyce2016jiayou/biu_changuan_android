package com.noplugins.keepfit.android.bean;

import java.util.List;

public class ChangguanBean {

    /**
     * area : {"pkname":"id","id":124,"areaNum":"GYM19091236750176","areaName":"超级新新","address":"上海闵行区七莘路3011号星钻城4楼","phone":"021-62908785","email":"232324@qq.com","longitude":121.347897,"latitude":31.155502,"haveSwim":1,"facility":"1,2,3,5","grade":950,"clickNum":106,"area":300,"gymUserNum":"","type":1,"picNum":"","logo":"http://qnimg.ahcomg.com/chaoji1","businessStart":"10:00:00","businessEnd":"22:00:00","taxpayerNum":"","charge":0,"tryout":7,"status":1,"cardNum":"232424242","companyCode":"xxx223232","companyName":"企业名称","legalPerson":"高动","remark":"","deleted":0,"createDate":"2019-09-12 10:45:32","updateDate":"2019-09-12 10:45:32","cost":10,"edxtendVar01":"","edxtendVar02":"","edxtendVar03":"","edxtendVar04":"","edxtendVar05":"","finalGradle":9.5}
     * place : [{"id":336,"placeNum":"GYM19091232970184","placeName":"","placeType":1,"maxNum":20,"gymAreaNum":"GYM19091236750176","gymCheckNum":null,"createDate":"2019-09-12 10:45:32","updateDate":"2019-09-12 10:45:32","deleted":false,"remark":""},{"id":337,"placeNum":"GYM19091213210226","placeName":"","placeType":2,"maxNum":30,"gymAreaNum":"GYM19091236750176","gymCheckNum":null,"createDate":"2019-09-12 10:45:32","updateDate":"2019-09-12 10:45:32","deleted":false,"remark":""}]
     * pic : [{"pkname":"id","id":380,"picNum":"GYM19091235679696","gymCourseNum":"GYM19091237142493","url":"http://qnimg.ahcomg.com/chaoji2","type":null,"description":"","gymAreaNum":"GYM19091236750176","gymUserNum":"","orderNum":2,"qiniuKey":"chaoji2","createDate":"2019-09-12 10:45:32","updateDate":"2019-09-12 10:45:32","deleted":0,"remark":""},{"pkname":"id","id":381,"picNum":"GYM19091242417069","gymCourseNum":"GYM19091237142493","url":"http://qnimg.ahcomg.com/chaoji3","type":null,"description":"","gymAreaNum":"GYM19091236750176","gymUserNum":"","orderNum":3,"qiniuKey":"chaoji3","createDate":"2019-09-12 10:45:32","updateDate":"2019-09-12 10:45:32","deleted":0,"remark":""}]
     */

    private AreaBean area;
    private List<PlaceBean> place;
    private List<PicBean> pic;

    public AreaBean getArea() {
        return area;
    }

    public void setArea(AreaBean area) {
        this.area = area;
    }

    public List<PlaceBean> getPlace() {
        return place;
    }

    public void setPlace(List<PlaceBean> place) {
        this.place = place;
    }

    public List<PicBean> getPic() {
        return pic;
    }

    public void setPic(List<PicBean> pic) {
        this.pic = pic;
    }

    public static class AreaBean {
        /**
         * pkname : id
         * id : 124
         * areaNum : GYM19091236750176
         * areaName : 超级新新
         * address : 上海闵行区七莘路3011号星钻城4楼
         * phone : 021-62908785
         * email : 232324@qq.com
         * longitude : 121.347897
         * latitude : 31.155502
         * haveSwim : 1
         * facility : 1,2,3,5
         * grade : 950
         * clickNum : 106
         * area : 300
         * gymUserNum :
         * type : 1
         * picNum :
         * logo : http://qnimg.ahcomg.com/chaoji1
         * businessStart : 10:00:00
         * businessEnd : 22:00:00
         * taxpayerNum :
         * charge : 0
         * tryout : 7
         * status : 1
         * cardNum : 232424242
         * companyCode : xxx223232
         * companyName : 企业名称
         * legalPerson : 高动
         * remark :
         * deleted : 0
         * createDate : 2019-09-12 10:45:32
         * updateDate : 2019-09-12 10:45:32
         * cost : 10
         * edxtendVar01 :
         * edxtendVar02 :
         * edxtendVar03 :
         * edxtendVar04 :
         * edxtendVar05 :
         * finalGradle : 9.5
         */

        private String pkname;
        private int id;
        private String areaNum;
        private String areaName;
        private String address;
        private String phone;
        private String email;
        private double longitude;
        private double latitude;
        private int haveSwim;
        private String facility;
        private int grade;
        private int clickNum;
        private int area;
        private String gymUserNum;
        private int type;
        private String picNum;
        private String logo;
        private String businessStart;
        private String businessEnd;
        private String taxpayerNum;
        private int charge;
        private int tryout;
        private int status;
        private String cardNum;
        private String companyCode;
        private String companyName;
        private String legalPerson;
        private String remark;
        private int deleted;
        private String createDate;
        private String updateDate;
        private int cost;
        private String edxtendVar01;
        private String edxtendVar02;
        private String edxtendVar03;
        private String edxtendVar04;
        private String edxtendVar05;
        private double finalGradle;

        private String bankName;

        private String bankCardNum;

        private String coachNotify;

        private String province;
        private String city;
        private String district;

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

        public String getCoachNotify() {
            return coachNotify;
        }

        public void setCoachNotify(String coachNotify) {
            this.coachNotify = coachNotify;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankCardNum() {
            return bankCardNum;
        }

        public void setBankCardNum(String bankCardNum) {
            this.bankCardNum = bankCardNum;
        }

        public String getPkname() {
            return pkname;
        }

        public void setPkname(String pkname) {
            this.pkname = pkname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAreaNum() {
            return areaNum;
        }

        public void setAreaNum(String areaNum) {
            this.areaNum = areaNum;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public int getHaveSwim() {
            return haveSwim;
        }

        public void setHaveSwim(int haveSwim) {
            this.haveSwim = haveSwim;
        }

        public String getFacility() {
            return facility;
        }

        public void setFacility(String facility) {
            this.facility = facility;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public int getClickNum() {
            return clickNum;
        }

        public void setClickNum(int clickNum) {
            this.clickNum = clickNum;
        }

        public int getArea() {
            return area;
        }

        public void setArea(int area) {
            this.area = area;
        }

        public String getGymUserNum() {
            return gymUserNum;
        }

        public void setGymUserNum(String gymUserNum) {
            this.gymUserNum = gymUserNum;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPicNum() {
            return picNum;
        }

        public void setPicNum(String picNum) {
            this.picNum = picNum;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getBusinessStart() {
            return businessStart;
        }

        public void setBusinessStart(String businessStart) {
            this.businessStart = businessStart;
        }

        public String getBusinessEnd() {
            return businessEnd;
        }

        public void setBusinessEnd(String businessEnd) {
            this.businessEnd = businessEnd;
        }

        public String getTaxpayerNum() {
            return taxpayerNum;
        }

        public void setTaxpayerNum(String taxpayerNum) {
            this.taxpayerNum = taxpayerNum;
        }

        public int getCharge() {
            return charge;
        }

        public void setCharge(int charge) {
            this.charge = charge;
        }

        public int getTryout() {
            return tryout;
        }

        public void setTryout(int tryout) {
            this.tryout = tryout;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getLegalPerson() {
            return legalPerson;
        }

        public void setLegalPerson(String legalPerson) {
            this.legalPerson = legalPerson;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public String getEdxtendVar01() {
            return edxtendVar01;
        }

        public void setEdxtendVar01(String edxtendVar01) {
            this.edxtendVar01 = edxtendVar01;
        }

        public String getEdxtendVar02() {
            return edxtendVar02;
        }

        public void setEdxtendVar02(String edxtendVar02) {
            this.edxtendVar02 = edxtendVar02;
        }

        public String getEdxtendVar03() {
            return edxtendVar03;
        }

        public void setEdxtendVar03(String edxtendVar03) {
            this.edxtendVar03 = edxtendVar03;
        }

        public String getEdxtendVar04() {
            return edxtendVar04;
        }

        public void setEdxtendVar04(String edxtendVar04) {
            this.edxtendVar04 = edxtendVar04;
        }

        public String getEdxtendVar05() {
            return edxtendVar05;
        }

        public void setEdxtendVar05(String edxtendVar05) {
            this.edxtendVar05 = edxtendVar05;
        }

        public double getFinalGradle() {
            return finalGradle;
        }

        public void setFinalGradle(double finalGradle) {
            this.finalGradle = finalGradle;
        }
    }

    public static class PlaceBean {
        /**
         * id : 336
         * placeNum : GYM19091232970184
         * placeName :
         * placeType : 1
         * maxNum : 20
         * gymAreaNum : GYM19091236750176
         * gymCheckNum : null
         * createDate : 2019-09-12 10:45:32
         * updateDate : 2019-09-12 10:45:32
         * deleted : false
         * remark :
         */

        private int id;
        private String placeNum;
        private String placeName;
        private int placeType;
        private int maxNum;
        private String gymAreaNum;
        private Object gymCheckNum;
        private String createDate;
        private String updateDate;
        private boolean deleted;
        private String remark;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPlaceNum() {
            return placeNum;
        }

        public void setPlaceNum(String placeNum) {
            this.placeNum = placeNum;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public int getPlaceType() {
            return placeType;
        }

        public void setPlaceType(int placeType) {
            this.placeType = placeType;
        }

        public int getMaxNum() {
            return maxNum;
        }

        public void setMaxNum(int maxNum) {
            this.maxNum = maxNum;
        }

        public String getGymAreaNum() {
            return gymAreaNum;
        }

        public void setGymAreaNum(String gymAreaNum) {
            this.gymAreaNum = gymAreaNum;
        }

        public Object getGymCheckNum() {
            return gymCheckNum;
        }

        public void setGymCheckNum(Object gymCheckNum) {
            this.gymCheckNum = gymCheckNum;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public static class PicBean {
        /**
         * pkname : id
         * id : 380
         * picNum : GYM19091235679696
         * gymCourseNum : GYM19091237142493
         * url : http://qnimg.ahcomg.com/chaoji2
         * type : null
         * description :
         * gymAreaNum : GYM19091236750176
         * gymUserNum :
         * orderNum : 2
         * qiniuKey : chaoji2
         * createDate : 2019-09-12 10:45:32
         * updateDate : 2019-09-12 10:45:32
         * deleted : 0
         * remark :
         */

        private String pkname;
        private int id;
        private String picNum;
        private String gymCourseNum;
        private String url;
        private Object type;
        private String description;
        private String gymAreaNum;
        private String gymUserNum;
        private int orderNum;
        private String qiniuKey;
        private String createDate;
        private String updateDate;
        private int deleted;
        private String remark;

        public String getPkname() {
            return pkname;
        }

        public void setPkname(String pkname) {
            this.pkname = pkname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPicNum() {
            return picNum;
        }

        public void setPicNum(String picNum) {
            this.picNum = picNum;
        }

        public String getGymCourseNum() {
            return gymCourseNum;
        }

        public void setGymCourseNum(String gymCourseNum) {
            this.gymCourseNum = gymCourseNum;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGymAreaNum() {
            return gymAreaNum;
        }

        public void setGymAreaNum(String gymAreaNum) {
            this.gymAreaNum = gymAreaNum;
        }

        public String getGymUserNum() {
            return gymUserNum;
        }

        public void setGymUserNum(String gymUserNum) {
            this.gymUserNum = gymUserNum;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public String getQiniuKey() {
            return qiniuKey;
        }

        public void setQiniuKey(String qiniuKey) {
            this.qiniuKey = qiniuKey;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
