package com.noplugins.keepfit.android.bean.use;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UseBean {

    /**
     * area : {"price":0.03,"count":3}
     * private : {"price":0.01,"count":1}
     * total : {"price":0.04,"count":4}
     * product : [{"price":3,"type":"场馆","percent":"75"},{"price":1,"type":"私教","percent":"25"},{"price":0,"type":"团课","percent":"0.00"}]
     * roll : {"price":0,"count":0}
     */

    private AreaBean area;
    @SerializedName("private")
    private PrivateBean privateX;
    private TotalBean total;
    private RollBean roll;
    private List<ProductBean> product;
    private int isProduct;

    public int getIsProduct() {
        return isProduct;
    }

    public void setIsProduct(int isProduct) {
        this.isProduct = isProduct;
    }

    public AreaBean getArea() {
        return area;
    }

    public void setArea(AreaBean area) {
        this.area = area;
    }

    public PrivateBean getPrivateX() {
        return privateX;
    }

    public void setPrivateX(PrivateBean privateX) {
        this.privateX = privateX;
    }

    public TotalBean getTotal() {
        return total;
    }

    public void setTotal(TotalBean total) {
        this.total = total;
    }

    public RollBean getRoll() {
        return roll;
    }

    public void setRoll(RollBean roll) {
        this.roll = roll;
    }

    public List<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ProductBean> product) {
        this.product = product;
    }

    public static class AreaBean {
        /**
         * price : 0.03
         * count : 3
         */

        private double price;
        private int count;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class PrivateBean {
        /**
         * price : 0.01
         * count : 1
         */

        private double price;
        private int count;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class TotalBean {
        /**
         * price : 0.04
         * count : 4
         */

        private double price;
        private int count;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class RollBean {
        /**
         * price : 0.0
         * count : 0
         */

        private double price;
        private int count;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class ProductBean {
        /**
         * price : 3
         * type : 场馆
         * percent : 75
         */

        private int price;
        private String type;
        private String percent;

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }
    }
}
