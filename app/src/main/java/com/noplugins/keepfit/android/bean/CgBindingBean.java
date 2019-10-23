package com.noplugins.keepfit.android.bean;

import java.util.List;

public class CgBindingBean {

    /**
     * teacherNum : [{"num":"CUS19101136561526"}]
     * areaNum : GYM19091294424251
     */

    private String areaNum;
    private List<TeacherNumBean> teacherNum;

    public String getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(String areaNum) {
        this.areaNum = areaNum;
    }

    public List<TeacherNumBean> getTeacherNum() {
        return teacherNum;
    }

    public void setTeacherNum(List<TeacherNumBean> teacherNum) {
        this.teacherNum = teacherNum;
    }

    public static class TeacherNumBean {
        /**
         * num : CUS19101136561526
         */

        private String num;

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
