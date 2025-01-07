package com.galaxy.reflect.bean;

public class Panda extends Father implements Runing, Cloneable {
    public class Nose {
    }

    protected class Ear {
    }

    class Mouth {
    }

    private class Eye {
    }

    private String name;
    private Integer age;

    public Panda() {
    }


    public Panda(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Panda(Object name, Object age) {
        this.name = (String) name;
        this.age = (Integer) age;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public void run() {

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

    @Override
    public String toString() {
        return "Panda{" +
                "name='" + name + '\'' +
                ", age=" + age +
                "} " + super.toString();
    }
}

class Hand {

}

