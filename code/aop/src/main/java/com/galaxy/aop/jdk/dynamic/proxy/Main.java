package com.galaxy.aop.jdk.dynamic.proxy;


import com.galaxy.aop.jdk.dynamic.proxy.animal.Animal;
import com.galaxy.aop.jdk.dynamic.proxy.animal.Cat;
import com.galaxy.aop.jdk.dynamic.proxy.animal.Dog;

public class Main {

    public static void main(String[] args) {
        Animal dog = new Dog();
        AnimalInvocationHandler<Animal> dogInvocationHandler = new AnimalInvocationHandler<Animal>();
        Animal dogProxy = dogInvocationHandler.bind(dog);
        dogProxy.eat();

        System.out.println();
        System.out.println();
        System.out.println();

        Animal cat = new Cat();
        Animal catProxy = AnimalInvocationHandler.of(cat);
        catProxy.eat();
    }
}
