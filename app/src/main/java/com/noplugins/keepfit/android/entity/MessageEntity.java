package com.noplugins.keepfit.android.entity;

import java.util.List;

public class MessageEntity {
    private boolean read;

    private List<MessageBean> message;


    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    private  int maxPage;
    public static class MessageBean {
        /**
         * id : 1
         * messageNum : GYM1222
         * gymUserNum :
         * gymAreaNum : GYM19073183130511
         * gymCourseCheckNum : null
         * messageCon :
         * status : null
         * type : 1
         * withdrawBalance : 2000
         * withdrawMoney : 3000
         * withdrawTime : 1564551690000
         * messageType : 1
         * messageTime : 1564551690000
         * createDate : 2019-07-31 13:41:30
         * updateDate : 2019-07-31 13:41:30
         * remark :
         * deleted : false
         */

        private int id;
        private String messageNum;
        private String gymUserNum;
        private String gymAreaNum;
        private Object gymCourseCheckNum;
        private String messageCon;
        private Object status;
        private int type;
        private int withdrawBalance;
        private int withdrawMoney;
        private long withdrawTime;
        private int messageType;
        private long messageTime;
        private String createDate;
        private String updateDate;
        private String remark;
        private boolean deleted;



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMessageNum() {
            return messageNum;
        }

        public void setMessageNum(String messageNum) {
            this.messageNum = messageNum;
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

        public Object getGymCourseCheckNum() {
            return gymCourseCheckNum;
        }

        public void setGymCourseCheckNum(Object gymCourseCheckNum) {
            this.gymCourseCheckNum = gymCourseCheckNum;
        }

        public String getMessageCon() {
            return messageCon;
        }

        public void setMessageCon(String messageCon) {
            this.messageCon = messageCon;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getWithdrawBalance() {
            return withdrawBalance;
        }

        public void setWithdrawBalance(int withdrawBalance) {
            this.withdrawBalance = withdrawBalance;
        }

        public int getWithdrawMoney() {
            return withdrawMoney;
        }

        public void setWithdrawMoney(int withdrawMoney) {
            this.withdrawMoney = withdrawMoney;
        }

        public long getWithdrawTime() {
            return withdrawTime;
        }

        public void setWithdrawTime(long withdrawTime) {
            this.withdrawTime = withdrawTime;
        }

        public int getMessageType() {
            return messageType;
        }

        public void setMessageType(int messageType) {
            this.messageType = messageType;
        }

        public long getMessageTime() {
            return messageTime;
        }

        public void setMessageTime(long messageTime) {
            this.messageTime = messageTime;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }
    }
}
