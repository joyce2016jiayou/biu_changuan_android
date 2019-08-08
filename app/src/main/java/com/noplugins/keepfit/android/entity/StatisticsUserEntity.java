package com.noplugins.keepfit.android.entity;

import java.util.List;

public class StatisticsUserEntity {

    /**
     * fitness : {"six":29,"four":56,"one":29,"seven":28,"two":34,"three":38,"five":33}
     * person : {"man":1024,"women":960}
     * num : 1999
     * collect : 999
     * age : {"six":29,"four":56,"one":29,"seven":28,"two":34,"three":38,"five":33}
     */

    private List<FitnessBean> fitness;
    private PersonBean person;
    private int num;
    private int collect;
    private List<AgeBean> age;

    public List<FitnessBean> getFitness() {
        return fitness;
    }

    public void setFitness(List<FitnessBean> fitness) {
        this.fitness = fitness;
    }

    public List<AgeBean> getAge() {
        return age;
    }

    public void setAge(List<AgeBean> age) {
        this.age = age;
    }

    public PersonBean getPerson() {
        return person;
    }

    public void setPerson(PersonBean person) {
        this.person = person;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public static class FitnessBean {
        private String time;
        private int result;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }
    }

    public static class PersonBean {
        /**
         * man : 1024
         * women : 960
         */

        private int man;
        private int women;

        public int getMan() {
            return man;
        }

        public void setMan(int man) {
            this.man = man;
        }

        public int getWomen() {
            return women;
        }

        public void setWomen(int women) {
            this.women = women;
        }
    }

    public static class AgeBean {
        private int result;
        private String time;

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
