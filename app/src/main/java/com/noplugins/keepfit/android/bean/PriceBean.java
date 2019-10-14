package com.noplugins.keepfit.android.bean;

public class PriceBean {
    private String data;
    private String price;

    public PriceBean() {
    }

    public PriceBean(String data, String price) {
        this.data = data;
        this.price = price;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
