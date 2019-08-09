package com.noplugins.keepfit.android.entity;

import java.util.List;

public class MessageEntity {




        private boolean read;
        private int maxPage;
        private List<MessageBean> message;
        private List<NoReadBean> noRead;

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

        public List<MessageBean> getMessage() {
            return message;
        }

        public void setMessage(List<MessageBean> message) {
            this.message = message;
        }

        public List<NoReadBean> getNoRead() {
            return noRead;
        }

        public void setNoRead(List<NoReadBean> noRead) {
            this.noRead = noRead;
        }

        public static class MessageBean {
            /**
             * id : 4
             * messageNum : GYM1666
             * gymUserNum :
             * gymAreaNum : GYM19073183130511
             * gymCourseCheckNum : GYM999
             * readStatus : 0
             * messageCon : 用户场地申请
             * status : 1
             * type : 15
             * withdrawBalance : null
             * withdrawMoney : null
             * withdrawTime : 1564551879000
             * messageType : 4
             * messageTime : 1564551879000
             * createDate : 2019-07-31 13:44:39
             * updateDate : 2019-07-31 13:44:39
             * remark : well
             * deleted : false
             * extendBigint01 : null
             * extendBigint02 : null
             * extendBigint03 : null
             * extendBigint04 : null
             * extendBigint05 : null
             * extendDec01 : null
             * extendDec02 : null
             * extendDec03 : null
             * extendDec04 : null
             * extendDec05 : null
             * extendVar01 : null
             * extendVar02 : null
             * extendVar03 : null
             * extendVar04 : null
             * extendVar05 : null
             */

            private int id;
            private String messageNum;
            private String gymUserNum;
            private String gymAreaNum;
            private String gymCourseCheckNum;
            private int readStatus;
            private String messageCon;
            private int status;
            private int type;
            private Object withdrawBalance;
            private Object withdrawMoney;
            private long withdrawTime;
            private int messageType;
            private long messageTime;
            private String createDate;
            private String updateDate;
            private String remark;
            private boolean deleted;
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

            public String getGymCourseCheckNum() {
                return gymCourseCheckNum;
            }

            public void setGymCourseCheckNum(String gymCourseCheckNum) {
                this.gymCourseCheckNum = gymCourseCheckNum;
            }

            public int getReadStatus() {
                return readStatus;
            }

            public void setReadStatus(int readStatus) {
                this.readStatus = readStatus;
            }

            public String getMessageCon() {
                return messageCon;
            }

            public void setMessageCon(String messageCon) {
                this.messageCon = messageCon;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public Object getWithdrawBalance() {
                return withdrawBalance;
            }

            public void setWithdrawBalance(Object withdrawBalance) {
                this.withdrawBalance = withdrawBalance;
            }

            public Object getWithdrawMoney() {
                return withdrawMoney;
            }

            public void setWithdrawMoney(Object withdrawMoney) {
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
        }

        public static class NoReadBean {
            /**
             * num : 1
             * type : 15
             */

            private int num;
            private int type;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

}
