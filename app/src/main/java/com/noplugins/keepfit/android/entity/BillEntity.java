package com.noplugins.keepfit.android.entity;

import java.util.List;

public class BillEntity {

    private String withdraw;
    private String income;
    private List<BillItemBean> billItemBeans;

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public List<BillItemBean> getBillItemBeans() {
        return billItemBeans;
    }

    public void setBillItemBeans(List<BillItemBean> billItemBeans) {
        this.billItemBeans = billItemBeans;
    }

    public class BillItemBean{
        private String time;
        private String projectName;
        private String imgUrl;
        private String projectContent;
        private String money;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getProjectContent() {
            return projectContent;
        }

        public void setProjectContent(String projectContent) {
            this.projectContent = projectContent;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
