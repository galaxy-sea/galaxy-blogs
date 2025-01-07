package com.galaxy.aop.jdk.dynamic.proxy.animal;

public class Dog implements Animal {

    @Override
    public void eat() {
        System.out.println("Dog吃狗粮");
    }
}
