package com.noplugins.keepfit.android.bean;

import java.util.List;

public class UserStatisticsBean {
    private int total;
    private List<ItemBean> age;
    private List<ItemBean> sex;
    private List<ItemBean> time;
    /**
     * function : [{"prices":[{"price":30,"type":1,"room":"单车房"},{"price":10,"type":2,"room":"瑜伽房"},{"price":100,"type":3,"room":"有氧操房"}],"value":"12:00-15:00"},{"prices":[{"price":7000,"type":1,"room":"单车房"},{"price":800,"type":2,"room":"瑜伽房"},{"price":400,"type":3,"room":"有氧操房"}],"value":"15:00-18:00"},{"prices":[{"price":300,"type":1,"room":"单车房"},{"price":100,"type":2,"room":"瑜伽房"},{"price":2000,"type":3,"room":"有氧操房"}],"value":"18:00-21:00"}]
     * sales : {"y2y":[{"date":"2019-10","currentPrice":20150,"lastYearPrice":0,"percent":0}],"product":[{"price":4,"type":"场馆","percent":"57.14"},{"price":0.02,"type":"私教","percent":"28.57"},{"price":0.01,"type":"团课","percent":"14.29"}],"m2m":[{"date":"2019-01","currentPrice":0,"percent":0,"lastMonthPrice":0},{"date":"2019-02","currentPrice":0,"percent":"�","lastMonthPrice":0},{"date":"2019-03","currentPrice":0,"percent":"�","lastMonthPrice":0},{"date":"2019-04","currentPrice":0,"percent":"�","lastMonthPrice":0},{"date":"2019-05","currentPrice":0,"percent":"�","lastMonthPrice":0},{"date":"2019-06","currentPrice":0,"percent":"�","lastMonthPrice":0},{"date":"2019-07","currentPrice":0,"percent":"�","lastMonthPrice":0},{"date":"2019-08","currentPrice":0,"percent":"�","lastMonthPrice":0},{"date":"2019-09","currentPrice":0,"percent":"�","lastMonthPrice":0},{"date":"2019-10","currentPrice":201.5,"percent":"∞","lastMonthPrice":0},{"date":"2019-11","currentPrice":0,"percent":"-100","lastMonthPrice":201.5},{"date":"2019-12","currentPrice":0,"percent":"�","lastMonthPrice":0}]}
     */

    private SalesBean sales;
    private List<FunctionBean> function;

    private int emptyProduct;//产品消费占比图是否是空 1是0否
    private int emptyAge;//用户年龄占比图是否是空 1是0否
    private int emptySex;//用户性别占比图是否是空 1是0否
    private int emptyTime;//健身时间占比图是否是空 1是0否

    public int getEmptyProduct() {
        return emptyProduct;
    }

    public void setEmptyProduct(int emptyProduct) {
        this.emptyProduct = emptyProduct;
    }

    public int getEmptyAge() {
        return emptyAge;
    }

    public void setEmptyAge(int emptyAge) {
        this.emptyAge = emptyAge;
    }

    public int getEmptySex() {
        return emptySex;
    }

    public void setEmptySex(int emptySex) {
        this.emptySex = emptySex;
    }

    public int getEmptyTime() {
        return emptyTime;
    }

    public void setEmptyTime(int emptyTime) {
        this.emptyTime = emptyTime;
    }

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

    public SalesBean getSales() {
        return sales;
    }

    public void setSales(SalesBean sales) {
        this.sales = sales;
    }

    public List<FunctionBean> getFunction() {
        return function;
    }

    public void setFunction(List<FunctionBean> function) {
        this.function = function;
    }

    public static class SalesBean {
        private List<Y2yBean> y2y;
        private List<ProductBean> product;
        private List<M2mBean> m2m;
        private int emptyProduct;

        public int getEmptyProduct() {
            return emptyProduct;
        }

        public void setEmptyProduct(int emptyProduct) {
            this.emptyProduct = emptyProduct;
        }

        public List<Y2yBean> getY2y() {
            return y2y;
        }

        public void setY2y(List<Y2yBean> y2y) {
            this.y2y = y2y;
        }

        public List<ProductBean> getProduct() {
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public List<M2mBean> getM2m() {
            return m2m;
        }

        public void setM2m(List<M2mBean> m2m) {
            this.m2m = m2m;
        }

        public static class Y2yBean {
            /**
             * date : 2019-10
             * currentPrice : 20150
             * lastYearPrice : 0
             * percent : 0
             */

            private String date;
            private double currentPrice;
            private double lastYearPrice;
            private int percent;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public double getCurrentPrice() {
                return currentPrice;
            }

            public void setCurrentPrice(double currentPrice) {
                this.currentPrice = currentPrice;
            }

            public double getLastYearPrice() {
                return lastYearPrice;
            }

            public void setLastYearPrice(double lastYearPrice) {
                this.lastYearPrice = lastYearPrice;
            }

            public int getPercent() {
                return percent;
            }

            public void setPercent(int percent) {
                this.percent = percent;
            }
        }

        public static class ProductBean {
            /**
             * price : 4
             * type : 场馆
             * percent : 57.14
             */

            private double price;
            private String type;
            private String percent;

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
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

        public static class M2mBean {
            /**
             * date : 2019-01
             * currentPrice : 0
             * percent : 0
             * lastMonthPrice : 0
             */

            private String date;
            private double currentPrice;
            private int percent;
            private double lastMonthPrice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public double getCurrentPrice() {
                return currentPrice;
            }

            public void setCurrentPrice(double currentPrice) {
                this.currentPrice = currentPrice;
            }

            public int getPercent() {
                return percent;
            }

            public void setPercent(int percent) {
                this.percent = percent;
            }

            public double getLastMonthPrice() {
                return lastMonthPrice;
            }

            public void setLastMonthPrice(double lastMonthPrice) {
                this.lastMonthPrice = lastMonthPrice;
            }
        }
    }

    public static class FunctionBean {
        /**
         * prices : [{"price":30,"type":1,"room":"单车房"},{"price":10,"type":2,"room":"瑜伽房"},{"price":100,"type":3,"room":"有氧操房"}]
         * value : 12:00-15:00
         */

        private String value;
        private List<PricesBean> prices;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<PricesBean> getPrices() {
            return prices;
        }

        public void setPrices(List<PricesBean> prices) {
            this.prices = prices;
        }

        public static class PricesBean {
            /**
             * price : 30
             * type : 1
             * room : 单车房
             */

            private int price;
            private int type;
            private String room;

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getRoom() {
                return room;
            }

            public void setRoom(String room) {
                this.room = room;
            }
        }
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
