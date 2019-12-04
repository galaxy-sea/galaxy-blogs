package com.galaxy.jackson.bean;

import com.galaxy.jackson.json.filter.ValuePrefix;
import com.galaxy.jackson.json.filter.ValuePrefixCollection;

import java.util.List;

/**
 * @author galaxy
 */
public class Panda {

    public static final String PREFIX = "前缀";

    @ValuePrefix(Prefix = "phone")
    private String phone;
    @ValuePrefix(Prefix = "name")
    private String name;
    private Integer age;

    @ValuePrefixCollection
    private List<String> nameList;

    public Panda() {
    }

    public Panda(String phone, String name, Integer age) {
        this.phone = phone;
        this.name = name;
        this.age = age;
    }

    public Panda(String phone, String name, Integer age, List<String> nameList) {
        this.phone = phone;
        this.name = name;
        this.age = age;
        this.nameList = nameList;
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

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    @Override
    public String toString() {
        return "Panda{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", nameList=" + nameList +
                '}';
    }
}
