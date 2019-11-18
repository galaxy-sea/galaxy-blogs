package com.galaxy.jackson.bean;

import com.galaxy.jackson.json.filter.ValuePrefix;

import java.util.List;

public class Panda {

    public static final String PREFIX = "abcd";

    @ValuePrefix
    private String phone;
    @ValuePrefix
    private String name;
    private Integer age;

    @ValuePrefix
    private List<String> data;

    public Panda() {
    }

    public Panda(String phone, String name, Integer age) {
        this.phone = phone;
        this.name = name;
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Panda{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", data=" + data +
                '}';
    }
}
