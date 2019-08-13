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
        private String pkname;
        private int id;
        private String walletDetailNum;
        private String gymWalletNum;
        private String gymUserNum;
        private String gymAreaNum;
        private double balance;
        private double money;
        private int type;
        private String createDate;
        private String updateDate;
        private int deleted;
        private String remark;
        private Object extendBigint01;
        private Object extendBigint02;
        private Object extendBigint03;
        private Object extendBigint04;
        private Object extendBigint05;
        private Object extendDec01;
        private Object extendDec02;
        private Object extendDec03;
        private Object extendDec04;
        private Object extendDec05;
        private Object extendVar01;
        private Object extendVar02;
        private Object extendVar03;
        private Object extendVar04;
        private Object extendVar05;
        private Object start;

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

        public String getWalletDetailNum() {
            return walletDetailNum;
        }

        public void setWalletDetailNum(String walletDetailNum) {
            this.walletDetailNum = walletDetailNum;
        }

        public String getGymWalletNum() {
            return gymWalletNum;
        }

        public void setGymWalletNum(String gymWalletNum) {
            this.gymWalletNum = gymWalletNum;
        }

        public String getGymUserNum() {
            return gymUserNum;
        }

        public void setGymUserNum(String gymUserNum) {
            this.gymUserNum = gymUserNum;
        }

        public String getGymAreaNum() {
            return gymAreaNum;
        }

        public void setGymAreaNum(String gymAreaNum) {
            this.gymAreaNum = gymAreaNum;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public Object getExtendBigint01() {
            return extendBigint01;
        }

        public void setExtendBigint01(Object extendBigint01) {
            this.extendBigint01 = extendBigint01;
        }

        public Object getExtendBigint02() {
            return extendBigint02;
        }

        public void setExtendBigint02(Object extendBigint02) {
            this.extendBigint02 = extendBigint02;
        }

        public Object getExtendBigint03() {
            return extendBigint03;
        }

        public void setExtendBigint03(Object extendBigint03) {
            this.extendBigint03 = extendBigint03;
        }

        public Object getExtendBigint04() {
            return extendBigint04;
        }

        public void setExtendBigint04(Object extendBigint04) {
            this.extendBigint04 = extendBigint04;
        }

        public Object getExtendBigint05() {
            return extendBigint05;
        }

        public void setExtendBigint05(Object extendBigint05) {
            this.extendBigint05 = extendBigint05;
        }

        public Object getExtendDec01() {
            return extendDec01;
        }

        public void setExtendDec01(Object extendDec01) {
            this.extendDec01 = extendDec01;
        }

        public Object getExtendDec02() {
            return extendDec02;
        }

        public void setExtendDec02(Object extendDec02) {
            this.extendDec02 = extendDec02;
        }

        public Object getExtendDec03() {
            return extendDec03;
        }

        public void setExtendDec03(Object extendDec03) {
            this.extendDec03 = extendDec03;
        }

        public Object getExtendDec04() {
            return extendDec04;
        }

        public void setExtendDec04(Object extendDec04) {
            this.extendDec04 = extendDec04;
        }

        public Object getExtendDec05() {
            return extendDec05;
        }

        public void setExtendDec05(Object extendDec05) {
            this.extendDec05 = extendDec05;
        }

        public Object getExtendVar01() {
            return extendVar01;
        }

        public void setExtendVar01(Object extendVar01) {
            this.extendVar01 = extendVar01;
        }

        public Object getExtendVar02() {
            return extendVar02;
        }

        public void setExtendVar02(Object extendVar02) {
            this.extendVar02 = extendVar02;
        }

        public Object getExtendVar03() {
            return extendVar03;
        }

        public void setExtendVar03(Object extendVar03) {
            this.extendVar03 = extendVar03;
        }

        public Object getExtendVar04() {
            return extendVar04;
        }

        public void setExtendVar04(Object extendVar04) {
            this.extendVar04 = extendVar04;
        }

        public Object getExtendVar05() {
            return extendVar05;
        }

        public void setExtendVar05(Object extendVar05) {
            this.extendVar05 = extendVar05;
        }

        public Object getStart() {
            return start;
        }

        public void setStart(Object start) {
            this.start = start;
        }
    }
}
