package com.noplugins.keepfit.android.entity;

public class WalletEntity {

    /**
     * pkname : id
     * id : 1
     * walletNum : GYM23123
     * balance : 2000
     * totalWithdraw : 100
     * canWithdraw : 100
     * gymUserNum :
     * gymAreaNum : GYM19073183130511
     * deleted : 0
     * remark :
     * createDate : 2019-08-07 14:30:26
     * updateDate : 2019-08-07 14:30:26
     * finalBalance : 20.0
     * finalTotalWithdraw : 1.0
     * finaTotalCanWithdraw : 1.0
     */

    private String pkname;
    private int id;
    private String walletNum;
    private int balance;
    private int totalWithdraw;
    private int canWithdraw;
    private String gymUserNum;
    private String gymAreaNum;
    private int deleted;
    private String remark;
    private String createDate;
    private String updateDate;
    private double finalBalance;
    private double finalTotalWithdraw;
    private double finaTotalCanWithdraw;

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

    public String getWalletNum() {
        return walletNum;
    }

    public void setWalletNum(String walletNum) {
        this.walletNum = walletNum;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getTotalWithdraw() {
        return totalWithdraw;
    }

    public void setTotalWithdraw(int totalWithdraw) {
        this.totalWithdraw = totalWithdraw;
    }

    public int getCanWithdraw() {
        return canWithdraw;
    }

    public void setCanWithdraw(int canWithdraw) {
        this.canWithdraw = canWithdraw;
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

    public double getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(double finalBalance) {
        this.finalBalance = finalBalance;
    }

    public double getFinalTotalWithdraw() {
        return finalTotalWithdraw;
    }

    public void setFinalTotalWithdraw(double finalTotalWithdraw) {
        this.finalTotalWithdraw = finalTotalWithdraw;
    }

    public double getFinaTotalCanWithdraw() {
        return finaTotalCanWithdraw;
    }

    public void setFinaTotalCanWithdraw(double finaTotalCanWithdraw) {
        this.finaTotalCanWithdraw = finaTotalCanWithdraw;
    }
}
