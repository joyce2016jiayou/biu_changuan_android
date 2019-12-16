package com.noplugins.keepfit.android.bean;

public class DictionaryeBean {
    /**
     * a : a
     * name : 专业
     * id : 155
     * value : 1
     * properties : 教练标签
     * object : 5
     */

    private String a;
    private String name;
    private int id;
    private String value;
    private String properties;
    private String object;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    private boolean check;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
