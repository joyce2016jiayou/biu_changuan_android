package com.noplugins.keepfit.android.bean;

public class SelectRoomBean {

    /**
     * placeNum : 1
     * placeName : 超级瑜伽房
     * placeType : 1
     * maxNum : 50
     */

    private String placeNum;
    private String placeName;
    private int placeType;
    private int maxNum;

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
}
