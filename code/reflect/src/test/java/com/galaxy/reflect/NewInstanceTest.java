package com.galaxy.reflect;

import com.galaxy.reflect.bean.Panda;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class NewInstanceTest {

    @Test
    public void newInstanceDemo() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // 方法一  new object
        Panda newPanda = new Panda();
        System.out.println(newPanda);


        // 方法二 class 反射
        Panda newInstance = Panda.class.newInstance();
        System.out.println(newInstance);

        // 方法三 Constructor
        Constructor<Panda> constructorNotParam = Panda.class.getConstructor();
        Panda constructorNotParamPanda = constructorNotParam.newInstance();

        Constructor<Panda> constructor = Panda.class.getConstructor(String.class, Integer.class);
        Panda constructorPanda = constructor.newInstance("panda", 13);

        System.out.println(constructorNotParamPanda);
        System.out.println(constructorPanda);


    }
}
