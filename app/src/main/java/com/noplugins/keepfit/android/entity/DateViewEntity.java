package com.noplugins.keepfit.android.entity;

import java.util.List;

public class DateViewEntity {


    public List<DateBean> getDayView() {
        return dayView;
    }

    public void setDayView(List<DateBean> dayView) {
        this.dayView = dayView;
    }

    private List<DateBean> dayView;


    public class DateBean{
        /**
         * course_name : 尊巴
         * end_time : 1561965629000
         * gen_teacher_num : Gen123
         * gym_area_num : GYM19072138381319
         * type : 1
         * gym_place_num : GYM19072246436767
         * check_status : 1
         * tips : 无
         * update_date : 1563798055000
         * come_num : 10
         * start_time : 1561962007000
         * course_des : 尊巴训练
         * course_type : 1
         * deleted : false
         * loop : false
         * price : 20
         * apply_num : 10
         * lable : 1
         * id : 1
         * course_time : 60
         * create_date : 1563798055000
         * course_num : GYM123
         * max_num : 20
         * status : 1
         */
        private String course_name;
        private long end_time;
        private String gen_teacher_num;
        private String gym_area_num;
        private String type;
        private String gym_place_num;
        private int check_status;
        private String tips;
        private long update_date;
        private int come_num;
        private long start_time;
        private String course_des;
        private int course_type;
        private boolean deleted;
        private boolean loop;
        private int price;
        private int apply_num;
        private int lable;
        private int id;
        private int course_time;
        private long create_date;
        private String course_num;
        private int max_num;
        private int status;

        public int getPast() {
            return past;
        }

        public void setPast(int past) {
            this.past = past;
        }

        private int past;

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        private String teacher_name;

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }

        public String getGen_teacher_num() {
            return gen_teacher_num;
        }

        public void setGen_teacher_num(String gen_teacher_num) {
            this.gen_teacher_num = gen_teacher_num;
        }

        public String getGym_area_num() {
            return gym_area_num;
        }

        public void setGym_area_num(String gym_area_num) {
            this.gym_area_num = gym_area_num;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getGym_place_num() {
            return gym_place_num;
        }

        public void setGym_place_num(String gym_place_num) {
            this.gym_place_num = gym_place_num;
        }

        public int getCheck_status() {
            return check_status;
        }

        public void setCheck_status(int check_status) {
            this.check_status = check_status;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public long getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(long update_date) {
            this.update_date = update_date;
        }

        public int getCome_num() {
            return come_num;
        }

        public void setCome_num(int come_num) {
            this.come_num = come_num;
        }

        public long getStart_time() {
            return start_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }

        public String getCourse_des() {
            return course_des;
        }

        public void setCourse_des(String course_des) {
            this.course_des = course_des;
        }

        public int getCourse_type() {
            return course_type;
        }

        public void setCourse_type(int course_type) {
            this.course_type = course_type;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public boolean isLoop() {
            return loop;
        }

        public void setLoop(boolean loop) {
            this.loop = loop;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getApply_num() {
            return apply_num;
        }

        public void setApply_num(int apply_num) {
            this.apply_num = apply_num;
        }

        public int getLable() {
            return lable;
        }

        public void setLable(int lable) {
            this.lable = lable;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCourse_time() {
            return course_time;
        }

        public void setCourse_time(int course_time) {
            this.course_time = course_time;
        }

        public long getCreate_date() {
            return create_date;
        }

        public void setCreate_date(long create_date) {
            this.create_date = create_date;
        }

        public String getCourse_num() {
            return course_num;
        }

        public void setCourse_num(String course_num) {
            this.course_num = course_num;
        }

        public int getMax_num() {
            return max_num;
        }

        public void setMax_num(int max_num) {
            this.max_num = max_num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

}
