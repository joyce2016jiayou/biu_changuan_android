package com.noplugins.keepfit.android.entity;

import java.util.List;

public class ClassEntity {

    /**
     * data : [{"reason":"","gen_teacher_num":"Gen456","remark":"","type":"1","tips":"注意事项","course_des":"课程介绍","course_type":1,"loop":true,"loop_cycle":2,"price":5000,"id":14,"create_date":1564039909000,"course_num":"GYM19072547611307","teacherName":"测试2","course_name":"团课名称","end_time":1561964400000,"gym_area_num":"GYM19072138381319","gym_place_num":"","check_status":1,"update_date":1564039909000,"come_num":0,"target":1,"difficulty":1,"start_time":1561986000000,"deleted":false,"placeName":null,"max_num":20,"status":1,"course_time":60}]
     * code : 0
     * message : success
     */

    private List<DataBean> data;


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * reason :
         * gen_teacher_num : Gen456
         * remark :
         * type : 1
         * tips : 注意事项
         * course_des : 课程介绍
         * course_type : 1
         * loop : true
         * loop_cycle : 2
         * price : 5000
         * id : 14
         * create_date : 1564039909000
         * course_num : GYM19072547611307
         * teacherName : 测试2
         * course_name : 团课名称
         * end_time : 1561964400000
         * gym_area_num : GYM19072138381319
         * gym_place_num :
         * check_status : 1
         * update_date : 1564039909000
         * come_num : 0
         * target : 1
         * difficulty : 1
         * start_time : 1561986000000
         * deleted : false
         * placeName : null
         * max_num : 20
         * status : 1
         * course_time : 60
         */

        private String reason;
        private String gen_teacher_num;
        private String remark;
        private String type;
        private String tips;
        private String course_des;
        private int course_type;
        private boolean loop;
        private int loop_cycle;
        private int price;
        private int id;
        private long create_date;
        private String course_num;
        private String teacherName;
        private String course_name;
        private long end_time;
        private String gym_area_num;
        private String gym_place_num;
        private int check_status;
        private long update_date;
        private int come_num;
        private int target;
        private int difficulty;
        private long start_time;
        private boolean deleted;
        private Object placeName;
        private int max_num;
        private int status;
        private int course_time;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getGen_teacher_num() {
            return gen_teacher_num;
        }

        public void setGen_teacher_num(String gen_teacher_num) {
            this.gen_teacher_num = gen_teacher_num;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
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

        public boolean isLoop() {
            return loop;
        }

        public void setLoop(boolean loop) {
            this.loop = loop;
        }

        public int getLoop_cycle() {
            return loop_cycle;
        }

        public void setLoop_cycle(int loop_cycle) {
            this.loop_cycle = loop_cycle;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

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

        public String getGym_area_num() {
            return gym_area_num;
        }

        public void setGym_area_num(String gym_area_num) {
            this.gym_area_num = gym_area_num;
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

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public long getStart_time() {
            return start_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public Object getPlaceName() {
            return placeName;
        }

        public void setPlaceName(Object placeName) {
            this.placeName = placeName;
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

        public int getCourse_time() {
            return course_time;
        }

        public void setCourse_time(int course_time) {
            this.course_time = course_time;
        }
    }
}
