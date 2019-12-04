package com.galaxy.jackson.bean;

import com.galaxy.jackson.json.filter.ValuePrefix;
import com.galaxy.jackson.json.filter.v1.ValuePrefixCollection;

import java.util.List;

/**
 * @author galaxy
 */
public class Panda {

    @ValuePrefix(Prefix = "phone")
    private String phone;
    @ValuePrefix(Prefix = "name")
    private String name;
    private Integer age;

//    @ValuePrefixCollection(Prefix = "nameListV1     ")
    private List<String> nameListV1;

    @ValuePrefix(Prefix = "nameListV2   ")
    private List<String> nameListV2;

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
        this.nameListV1 = nameList;
        this.nameListV2 = nameList;
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

    public List<String> getNameListV1() {
        return nameListV1;
    }

    public void setNameListV1(List<String> nameListV1) {
        this.nameListV1 = nameListV1;
    }

    public List<String> getNameListV2() {
        return nameListV2;
    }

    public void setNameListV2(List<String> nameListV2) {
        this.nameListV2 = nameListV2;
    }

    @Override
    public String toString() {
        return "Panda{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", nameListV1=" + nameListV1 +
                ", nameListV2=" + nameListV2 +
                '}';
    }
}
