package com.noplugins.keepfit.android.bean;

public class ChooseBean {
    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    private int serviceType;
    private boolean haveHighTime;

    public boolean isHaveHighTime() {
        return haveHighTime;
    }

    public void setHaveHighTime(boolean haveHighTime) {
        this.haveHighTime = haveHighTime;
    }
}
