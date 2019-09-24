package com.galaxy.aop.jdk.dynamic.proxy.animal;

public class Cat implements Animal {

    @Override
    public void eat() {
        System.out.println("Cat吃猫粮");
    }
}
