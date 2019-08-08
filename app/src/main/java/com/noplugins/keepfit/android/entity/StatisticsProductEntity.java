package com.noplugins.keepfit.android.entity;

import java.util.List;

public class StatisticsProductEntity {

    /**
     * efficiency : 30
     * sale : [{"result":29,"time":"1"},{"result":34,"time":"2"},{"result":56,"time":"3"},{"result":33,"time":"4"},{"result":22,"time":"5"},{"result":70,"time":"7"},{"result":80,"time":"8"},{"result":80,"time":"9"},{"result":80,"time":"10"},{"result":80,"time":"11"},{"result":80,"time":"12"},{"result":66,"time":"6"}]
     * price : 30
     * consume : [{"time":"gym","value":30},{"time":"private","value":40},{"time":"roll","value":30}]
     */

    private int efficiency;
    private int price;
    private List<SaleBean> sale;
    private List<ConsumeBean> consume;

    public int getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<SaleBean> getSale() {
        return sale;
    }

    public void setSale(List<SaleBean> sale) {
        this.sale = sale;
    }

    public List<ConsumeBean> getConsume() {
        return consume;
    }

    public void setConsume(List<ConsumeBean> consume) {
        this.consume = consume;
    }

    public static class SaleBean {
        /**
         * result : 29
         * time : 1
         */

        private int result;
        private String time;

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public static class ConsumeBean {
        /**
         * time : gym
         * value : 30
         */

        private String time;
        private int value;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
