package com.galaxy.aop.cglib.dynamic.proxy;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class PandaMethodInterceptor implements MethodInterceptor, Callback {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("——————调用前处理——————");
        //对被代理对象方法的调用
        Object object = methodProxy.invokeSuper(o, objects);
        System.out.println("——————调用后处理——————");
        return object;
    }
}
