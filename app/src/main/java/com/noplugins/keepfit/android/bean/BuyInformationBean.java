package com.noplugins.keepfit.android.bean;

public class BuyInformationBean {

    /**
     * priceOne : 2999
     * areaName : 超级新新
     * logo : http://qnimg.ahcomg.com/chaoji1
     * priceTwo : 3999
     * priceThree : 6999
     */

    private double priceOne;
    private String areaName;
    private String logo;
    private double priceTwo;
    private double priceThree;

    public double getPriceOne() {
        return priceOne;
    }

    public void setPriceOne(double priceOne) {
        this.priceOne = priceOne;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public double getPriceTwo() {
        return priceTwo;
    }

    public void setPriceTwo(double priceTwo) {
        this.priceTwo = priceTwo;
    }

    public double getPriceThree() {
        return priceThree;
    }

    public void setPriceThree(double priceThree) {
        this.priceThree = priceThree;
    }
}
