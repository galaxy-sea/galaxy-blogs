package com.galaxy.jackson.bean;

import com.galaxy.jackson.json.filter.ValuePrefix;
import com.galaxy.jackson.json.filter.ValuePrefixCollection;

import java.util.List;

/**
 * @author galaxy
 */
public class Panda {

    public static final String PREFIX = "前缀";

    @ValuePrefix
    private String phone;
    @ValuePrefix
    private String name;
    private Integer age;

    @ValuePrefixCollection
    private List<String> nameTest;

    public Panda() {
    }

    public Panda(String phone, String name, Integer age) {
        this.phone = phone;
        this.name = name;
        this.age = age;
    }

    public Panda(String phone, String name, Integer age, List<String> nameTest) {
        this.phone = phone;
        this.name = name;
        this.age = age;
        this.nameTest = nameTest;
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

    public List<String> getNameTest() {
        return nameTest;
    }

    public void setNameTest(List<String> nameTest) {
        this.nameTest = nameTest;
    }

    @Override
    public String toString() {
        return "Panda{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", data=" + nameTest +
                '}';
    }
}
