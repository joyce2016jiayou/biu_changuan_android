package com.noplugins.keepfit.android.bean.mine;

import java.util.List;

public class BalanceListBean {

    /**
     * monthWithDraw : 0.0
     * monthIncome : 7.0
     * list : [{"pkname":"id","id":1,"walletDetailNum":"GYM888","coachWalletNum":"GYM999","coachUserNum":"CUS19091292977313","balance":800,"money":700,"type":3,"status":null,"createDate":"2019-09-24 16:49:46","updateDate":"2019-09-24 16:49:46","deleted":0,"remark":"","finalmoney":7}]
     * maxPage : 1
     */

    private double monthWithDraw;
    private double monthIncome;
    private int maxPage;
    private List<ListBean> list;

    public double getMonthWithDraw() {
        return monthWithDraw;
    }

    public void setMonthWithDraw(double monthWithDraw) {
        this.monthWithDraw = monthWithDraw;
    }

    public double getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(double monthIncome) {
        this.monthIncome = monthIncome;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * pkname : id
         * id : 1
         * walletDetailNum : GYM888
         * coachWalletNum : GYM999
         * coachUserNum : CUS19091292977313
         * balance : 800
         * money : 700
         * type : 3
         * status : null
         * createDate : 2019-09-24 16:49:46
         * updateDate : 2019-09-24 16:49:46
         * deleted : 0
         * remark :
         * finalmoney : 7.0
         */


        private String pkname;
        private int id;
        private String walletDetailNum;
        private String coachWalletNum;
        private String coachUserNum;
        private int balance;
        private int money;
        private int type;
        private int status;
        private String createDate;
        private String updateDate;
        private int deleted;
        private String remark;
        private double finalMoney;
        /**
         * courseNum :
         * courseName : 课程名称
         * areaName : 场馆名称
         * finalmoney : 7
         */

        private String courseNum;
        private String courseName;
        private String areaName;

        public double getFinalMoney() {
            return finalMoney;
        }

        public void setFinalMoney(double finalMoney) {
            this.finalMoney = finalMoney;
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

        public String getWalletDetailNum() {
            return walletDetailNum;
        }

        public void setWalletDetailNum(String walletDetailNum) {
            this.walletDetailNum = walletDetailNum;
        }

        public String getCoachWalletNum() {
            return coachWalletNum;
        }

        public void setCoachWalletNum(String coachWalletNum) {
            this.coachWalletNum = coachWalletNum;
        }

        public String getCoachUserNum() {
            return coachUserNum;
        }

        public void setCoachUserNum(String coachUserNum) {
            this.coachUserNum = coachUserNum;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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


        public String getCourseNum() {
            return courseNum;
        }

        public void setCourseNum(String courseNum) {
            this.courseNum = courseNum;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

    }
}
