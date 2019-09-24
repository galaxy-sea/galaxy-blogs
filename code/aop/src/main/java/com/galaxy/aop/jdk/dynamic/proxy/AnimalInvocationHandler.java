package com.galaxy.aop.jdk.dynamic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AnimalInvocationHandler<T> implements InvocationHandler {

    /**
     * 被代理对象
     */
    private T target;

    public static <T> T of(T target) {
        AnimalInvocationHandler<T> invocationHandler = new AnimalInvocationHandler<T>();
        return invocationHandler.bind(target);
    }

    /**
     * 绑定业务对象并返回一个代理类
     * @param target
     */
    public T bind(T target) {
        this.target = target;
        //通过反射机制，创建一个代理类对象实例并返回。用户进行方法调用时使用
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        //方法执行前加一段逻辑
        System.out.println("——————调用前处理——————");
        //调用真正的业务方法
        result = method.invoke(target, args);
        //方法执行前加一段逻辑
        System.out.println("——————调用后处理——————");
        return result;
    }
}
