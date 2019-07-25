package com.noplugins.keepfit.android.entity;

public class BiaoqianEntity {
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private int number;
    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(this==obj){
            return true;
        }
        if(obj instanceof BiaoqianEntity){
            BiaoqianEntity serviceBean=(BiaoqianEntity)obj;
            if(serviceBean.getIndex()==getIndex() && serviceBean.getNumber()==getNumber()){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

}
