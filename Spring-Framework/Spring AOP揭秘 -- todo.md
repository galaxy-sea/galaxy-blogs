<!-- TOC -->

- [1. AOP 前置知识](#1-aop-前置知识)
  - [1.1. 静态代理](#11-静态代理)
  - [1.2. JDK 动态代理](#12-jdk-动态代理)
  - [1.3. CGLIB 动态代理](#13-cglib-动态代理)
  - [1.4. AOP 联盟](#14-aop-联盟)
- [2. AOP 概述](#2-aop-概述)
  - [2.1. AOP 基本概念](#21-aop-基本概念)
  - [2.2. Spring AOP 相关概念](#22-spring-aop-相关概念)
- [3. Spring AOP 实现](#3-spring-aop-实现)
  - [3.1. 基于JDK动态代理实现](#31-基于jdk动态代理实现)
  - [3.2. 基于CGLIB动态代理实现](#32-基于cglib动态代理实现)
  - [3.3. 小结](#33-小结)
- [4. Spring支持的通知](#4-spring支持的通知)
  - [4.1. Before Advice(前置通知)](#41-before-advice前置通知)
  - [4.2. After Returning Advice(后置通知)](#42-after-returning-advice后置通知)
  - [4.3. Interception Around Advice(环绕通知)](#43-interception-around-advice环绕通知)
  - [4.4. Throws Advice(异常抛出通知)](#44-throws-advice异常抛出通知)
  - [4.5. Introduction Advice(引介通知)](#45-introduction-advice引介通知)
  - [4.6. 切入点类型](#46-切入点类型)
- [5. Spring集成AspectJ实战](#5-spring集成aspectj实战)
  - [5.1. 使用 AspectJ方式配置Spring AOP](#51-使用-aspectj方式配置spring-aop)
  - [5.2. AspectJ各种切点指示器](#52-aspectj各种切点指示器)
  - [5.3. execution](#53-execution)
  - [5.4. this()](#54-this)
  - [5.5. @annotation()](#55-annotation)
  - [5.8. target()与@target()](#58-target与target)
  - [5.6. args()与@args()](#56-args与args)
  - [5.7. within()与@within()](#57-within与within)
- [6. 文献](#6-文献)

<!-- /TOC -->

> 嗨, 2020年是让人心烦的一年，新冠肺炎波及各行各业。万万没有想到我居然会背波及到造成我无缝对接我的新工作。  
> 来到新公司也没有什么事情干呀，刚开始搭建了一个Dubbo Spring Cloud后无所事事了2个多月吧, 想着没有事情干还不如写一篇博客。   
> 想着AOP去年写了半篇就荒废了。今年就重新写一篇吧。好了下面就开始介绍AOP吧


# 1. AOP 前置知识
``AOP``为Aspect Oriented Programming的缩写，意为：``面向切面编程``，通过预编译方式和运行期间动态代理实现程序功能的统一维护的一种技术。``AOP``是``OOP``的延续，是软件开发中的一个热点，也是``Spring``框架中的一个重要内容，是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率

``动态代理``是相对于``静态代理``而提出的设计模式。在``Spring``中有两种方式可以是实现动态代理——``JDK动态代理``和``CGLIB动态代理``。

## 1.1. 静态代理
代理模式是使用一个类代表另一个类的动能，通过代理模式可以控制这个对象的访问。   

> 下面以一个简单的案例说明静态代理, 小魏同学想买车但是加班没有时间去买车，他就叫了客户经理（中介）代理他买车

> 创建BuyCar接口, BuyCar定义一个buy()方法, 表示需要买什么车子
```java
/**
 * 买车接口
 * @author changjinwei
 */
public interface BuyCar {
    void buy();
}

```
> 创建Customer类，Customer实现BuyCar接口重写buy()方法

```java
/**
 * @author changjinwei
 */
public class Customer implements BuyCar{

    @Override
    public void buy() {
        System.out.println("我是小魏, 我想买五菱宏光Plus");
    }
}
```

> 创建客户代理类CustomerProxy, CustomerProxy实现BuyCar接口重写buy()方法, 具体代理类如下

```java
  /**
 * @author changjinwei
 */
public class CustomerProxy implements BuyCar{

    /**
     * 要代理的客户
     */
    private Customer customer;

    public CustomerProxy(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void buy() {
        customer.buy();
    }
}
```

> 测试代码如下

```java
/**
 * @author changjinwei
 */
public class Proxy4staticTest {

    @Test
    public void proxy4static() {
        CustomerProxy customerProxy = new CustomerProxy(new Customer());
        customerProxy.buy();
    }
}
```

> 运行测试代码，执行结果如下

```
我是小魏, 我想买五菱宏光Plus
```


## 1.2. JDK 动态代理

对于静态代理，一个代理类只能代理一个对象，如果有多个对象需要被代理，就系要很多代理类，在成代码的荣誉。JDK动态代理，从字面意思就可以看出，JDK动态代理对象是动态生成的。   
JDK动态代理的条件是被代理对象必须实现接口。  

> 下面以一个简单的案例说明JDK动态代理

> 创建Animal接口, 接口中定义eat方法(),表示动物需要吃饭

```java
/**
 * @author changjinwei
 */
public interface Animal {

    /**
     * 接口方法
     */
    void eat();
}
```
> 创建Cat类实现Animal接口, 重写eat()方法
```java
/**
 * @author changjinwei
 */
public class Cat implements Animal {
    @Override
    public void eat() {
        System.out.println("Cat 吃老鼠");
    }
}
```
> 创建动态代理类, 动态代理类需要实现InvocationHandler接口, 具体动态代理类实现如下: 

```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author changjinwei
 */
public class AnimalInvocationHandler<T extends Animal> implements InvocationHandler {

    /**
     * 被代理对象
     */
    private T target;

    /**
     * 通过反射, 创建一个代理类实例并返回, 用户进行方法调用时使用
     *
     * @param target
     * @return 绑定业务对象并返回一个代理类
     */
    public Animal bind(T target) {
        this.target = target;
        return (Animal) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this::invoke);
    }

    /**
     * 在动态代理类中, 在被代理的方法前后各加一段输出, 而不破坏原方法
     * @param proxy 调用方法的实例
     * @param method 调用的方法
     * @param args   参数列表
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("-----调用前处理-----");
        Object result = method.invoke(target, args);
        System.out.println("-----调用后处理-----");
        return result;
    }
}
```
> 下面将用一个测试类, 证明动态代理生效, 测试类如下
```java
/**
 * @author changjinwei
 */
public class Proxy4JdkTest {

    @Test
    public void proxy4Jdk() {
        // 被代理的对象
        Cat cat = new Cat();
        // 动态代理对象
        AnimalInvocationHandler<Cat> animalInvocationHandler = new AnimalInvocationHandler<Cat>();
        // 代理对象
        Animal animal = animalInvocationHandler.bind(cat);
        animal.eat();
    }
}
```
> 测试输出
```
-----调用前处理-----
Cat 吃老鼠
-----调用后处理-----
```
这已经证明童泰代理生效了，想要在cat对象的eat()方法前后加上额外的逻辑，可以不直接修改eat()方法。   
以上就是Spring AOP的基本原理，只是Spring不需要开发人员自己维护代理类，其已帮开发人员生成了代理类。Spring AOP的实现是通过在程序运行时，根据具体的类对象和方法等信息动态的生成了一个代理类的class文件的字节码，再通过ClassLoader讲代理类加载到内存中，最后通过生成的代理对象进行程序的方法调用。

## 1.3. CGLIB 动态代理
通过对JDK动态代理的实现可以发现，JDK动态代理有一个缺点，即被代理类必须实现接口。这肯定不能满足开发工作中的需要，这个时候界需要CGLIB发挥作用了

> 下面将用一个简单的案例说明CGLIB是如何实现动态代理的

> 创建一个Dog类, 其中有一个cry()方法
```java
/**
 * @author changjinwei
 */
public class Dog {

    public void cry(){
        System.out.println("狗 汪汪叫");
    }
}
```
> CGLIB 动态代理的实现需要实现MethodInterceptor接口, 重写intercept()方法。
```java
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author changjinwei
 */
public class DogMethodInterceptor implements MethodInterceptor {

    /**
     * 生成方法拦截器, 在被代理的方法前后各加一段输出,以观察拦截的效果
     * @param o           要通知的对象
     * @param method      拦截的方法
     * @param objects     参数列表
     * @param methodProxy 方法的代理
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("-----调用前处理-----");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("-----调用后处理-----");
        return result;
    }
}
```
> 测试代码如下
```java
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @author changjinwei
 */
public class Proxy4CglibTest {

    @Test
    public void proxy4Cglib() {
        Enhancer enhancer = new Enhancer();
        // 被代理类: Dog
        enhancer.setSuperclass(Dog.class);
        // 设置回调
        enhancer.setCallback(new DogMethodInterceptor());
        // 生成代理对象
        Dog dog = (Dog) enhancer.create();
        // 调用代理方法
        dog.cry();
    }
}
```
> 测试结果
```
-----调用前处理-----
Cat 吃老鼠
-----调用后处理-----
```

## 1.4. AOP 联盟
这里是[aop联盟官网](http://aopalliance.sourceforge.net)[http://aopalliance.sourceforge.net/](http://aopalliance.sourceforge.net/)，下面对官网大致的翻译(塑料英语)

面向方面的编程（AOP）是一种编程技术，将能够通知几个现有的中间件环境（例如J2EE）或开发环境（例如JBuilder，Eclipse）。

现在有几个项目提供了与AOP相关的技术，例如通用代理，拦截器或字节码转换器。

- ASM：轻量级的字节码转换器。
- AspectJ：一个面向切面的的框架，扩展了Java语言。
- AspectWerkz：一个面向切面的框架，基于字节码级别的动态织入和配置。
- BCEL：字节码转换器。
- CGLIB：用户类工件插座和方法拦截的高级API。
- JAC：AO中间件（字节码级动态织入 + 配置 + 切面）框架。
- Javassist：具有高级API的字节码转换器。
- JBoss-AOP：拦截和基于元数据的AO框架。
- JMangler：一个字节码转换器，具有用于翻译的组合框架
- Nanning：切面织入（框架）。
- Prose：字节码级别的动态织入框架。

所有这些项目都有自己的目标和特色。但是，构建一个完整的AO系统仍然有几个常用的基本组件（有时是必需的）。
例如，一个能够在基本组件上添加元数据的组件，一个拦截框架，一个能够执行代码转换器以便为类提供advice的组件，
一个weaver组件，一个配置组件等等。
对于

对我们来说，能够重用来自不同项目的不同组件来构建一个完整的AO系统将是非常棒的，主要有三个原因。
1. 对于已存在的几个组件来说重构它们非常愚蠢
2. 取决于环境，相同组件的各种实现多少是有用的列如:
   1. 可以根据编译基本程序的组件的实现，使用拦截或代码插入机制在编译或运行时在字节码或源代码级别上完成程序转换
   2. weaver可以在标准Java上下文中或EJB上下文中使用，这可能意味着某些实现上的差异
3. 拥有通用的接口和组件将有助于重用不同编织环境中的各个方面……这将大大提高软件的重用性。

由于这些原因，面向方面的组件的接口的标准化将是巨大的，并且将为整个AOSD社区以及所有不久的将来将使用AOP的社区带来极大的简化



# 2. AOP 概述

## 2.1. AOP 基本概念
Aspect Oriented Programming（AOP）是较为热门的一个话题。AOP，国内大致译作“面向切面编程”。   

“面向切面编程”,这样的名字并不是非常容易理解，且容易产生一些误导。有些人认为“OOP/OOD11即将落伍，AOP是新一代软件开发方式”。
显然，发言者并没有理解AOP的含义。Aspect，的确是“方面”的意思。不过，汉语传统语义中的“方面”，大多数情况下指的是一件事情的不同维度、或者说不同角度上的特性，
比如我们常说：“这件事情要从几个方面来看待”，往往意思是：需要从不同的角度来看待同一个事物。这里的“方面”，指的是事物的外在特性在不同观察角度下的体现。而在AOP中，Aspect的含义，可能更多的理解为“切面”比较合适。

可以通过预编译方式和运行期动态代理实现在不修改源代码的情况下给程序动态统一添加功能的一种技术。
AOP实际是GoF设计模式的延续，设计模式孜孜不倦追求的是调用者和被调用者之间的解耦,提高代码的灵活性和可扩展性，AOP可以说也是这种目标的一种实现。

在Spring中提供了面向切面编程的丰富支持，允许通过分离应用的业务逻辑与系统级服务（例如审计（auditing）和事务（transaction）管理）进行内聚性的开发。
应用对象只实现它们应该做的——完成业务逻辑——仅此而已。它们并不负责（甚至是意识）其它的系统级关注点，例如日志或事务支持。

> 主要功能

日志记录，性能统计，安全控制，事务处理，异常处理等等。

> 主要意图

将日志记录，性能统计，安全控制，事务处理，异常处理等代码从业务逻辑代码中划分出来，通过对这些行为的分离，我们希望可以将它们独立到非指导业务逻辑的方法中，进而改变这些行为的时候不影响业务逻辑的代码。

## 2.2. Spring AOP 相关概念
> 让我们首先定义一些重要的AOP概念和术语。这些术语不是特定于Spring的。不幸的是，AOP术语并不是特别直观。但是Spring使用自己的术语，那将更加令人困惑。

1. **Aspect(切面)**   
    切面就是对横切关注点的抽象, 这个关注点可能会横切多个对象
2. **Join point(连接点)**   
    连接点是在程序执行过程中某个特定的点，比如某个方法调用的时候或者处理异常的时候。
    由于Spring只能支持方法类型的连接点，所以在Spring AOP中一个连接点总是表示一个方法的执行。
3. **Advice(通知)**   
    在切面上拦截到某个特定的连接点之后执行的动作。   
    通知动作有``Before advice(前置通知)``, ``After returning advice(正常运行通知)``,
    ``After throwing advice(异常抛出通知)``,``After (finally) advice(后置通知)``,``Around advice(环绕通知)``
4. **Pointcut(切入点)**   
    切入点是匹配连接点的拦截规则，在满足这个切入点的连接点上运行通知。
    切入点表达式如何和连接点匹配是AOP的核心，Spring默认送AspectJ切入点语法。
5. **Introduction(引入)**   
    引入是用来在运行时给一个类声明额外的方法或者属性，你不需要为了实现一个接口，就能够使用接口中的方法。
6. **Target object(目标对象)**   
    目标对象，在一个或多个切面所通知的对象，及业务中需要进行通知的业务对象。
7. **AOP proxy(AOP代理)**   
    由AOP框架创建的一个对象，用于实现方面合同（建议方法执行等）。
    在Spring Framework中，AOP代理是JDK动态代理或CGLIB动态代理
8. **Weaving(织入)**   
    织入是把切面作用到目标对象，然后产生一个代理对象的过程。

# 3. Spring AOP 实现
Spring AOP的实现方式有两种，分别是基于JDK动态代理的实现和基于CGLIB的动态代理实现。~~(其实用起来没有什么差别, Spring大法好 ╮(╯▽╰)╭  )~~   

Spring的配置配置方式有基于XML的配置方式和注解的配置方式，现在Spring Boot大行其道那我们就都基于注解方式进行开发 ~~(主要XML配置很久没有用了, 给忘记了O(∩_∩)O哈哈)~~

## 3.1. 基于JDK动态代理实现
Spring AOP的一个特点是被代理的对象需要实现一个接口。

>添加配置
```properties
# Spring Boot默认开启  // 尝试设置为false有惊喜哦
spring.aop.auto=true
```

> 定义Fruit接口

```java
/**
 * 水果接口
 *
 * @author changjinwei
 * @data 2020/8/4
 */
public interface Fruit {

    /**
     * 吃水果
     */
    void eat() throws InterruptedException;
}
```
> 接下来，实现被代理的对象，分别是Apple类和Banana类实现Fruit接口
```java
import org.springframework.stereotype.Component;

/**
 * 苹果
 *
 * @author changjinwei
 * @data 2020/8/4
 */
@Component
public class Apple implements Fruit {
    /**
     * 吃苹果
     */
    @Override
    public void eat() throws InterruptedException {
        System.out.println("开始吃苹果");
        Thread.sleep(1000);
        System.out.println("结束吃苹果");
    }
}

```

```java
import org.springframework.stereotype.Component;

/**
 * 香蕉
 *
 * @author changjinwei
 * @data 2020/8/4
 */
@Component
public class Banana implements Fruit {

    /**
     * 吃香蕉
     */
    @Override
    public void eat() throws InterruptedException {
        System.out.println("开始吃香蕉");
        Thread.sleep(1000);
        System.out.println("结束吃香蕉");
    }
}
```

> 下面基于注解方式实现Spring AOP   
> ``@Aspect``注解用来声明当前类是切面, ``@Pointcut``定义切点, ``Before``表明前置通知, ``@After``表明后置通知
```java
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author changjinwei
 * @data 2020/8/4
 */
@Component
@Aspect
public class FruitAspect {
    /**
     * 定义切点
     */
    @Pointcut("execution(* com.galaxy.aop.demo.aop.jdk.Fruit.*(..))")
    public void eatFruit() {

    }

    /**
     * 前置通知
     * 打印开始吃水果的时间
     */
    @Before("eatFruit()")
    public void startEatFruitDate() {
        System.out.println("开始吃水果的时间是：" + new Date());
    }

    /**
     * 后置通知
     * 打印吃完吃水果的时间
     */
    @After("eatFruit()")
    public void endEatFruitDate() {
        System.out.println("结束吃水果的时间是：" + new Date());
    }
}
```
> 测试代码
```java
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author changjinwei
 * @data 2020/8/4
 */
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JdkAspectTest {

    private final Apple apple;
    private final Banana banana;

    @Test
    public void aspectTest() throws InterruptedException {
        apple.eat();
        System.out.println("\n------------------\n");
        banana.eat();
    }
}
```

> 测试结果
```
开始吃水果的时间是：Tue Aug 04 17:44:56 CST 2020
开始吃苹果
结束吃苹果
结束吃水果的时间是：Tue Aug 04 17:44:57 CST 2020

------------------

开始吃水果的时间是：Tue Aug 04 17:44:57 CST 2020
开始吃香蕉
结束吃香蕉
结束吃水果的时间是：Tue Aug 04 17:44:58 CST 2020
```

执行结果可以看出, 在没用修改Apple类和Banana类的代码情况加, 每次执行eat()方法, 都会输出"开始吃水果的时间"和"结束吃水果的时间"的日志记录逻辑, 验证了Spring AOP 是可以正常使用的


## 3.2. 基于CGLIB动态代理实现
基于JDK动态代理实现的Spring AOP, 可以发现JDK动态代理的一个缺陷, 被代理对象需要实现一个接口. 在这种严苛的条件并不能符合日常开发, 有没有办法实现对没有实现接口的配进行代理呐? 这就是要使用基于CGLIB方式实现的Spring AOP编程


> 添加配置
```properties
# Spring Boot默认开启  // 尝试设置为false有惊喜哦
spring.aop.proxy-target-class=true
```

> 创建AmericanShorthair类和BritishShorthair类分别打印它们的叫声

```java
import org.springframework.stereotype.Component;

/**
 * 美短
 *
 * @author changjinwei
 * @data 2020/8/4
 */
@Component
public class AmericanShorthair {

    void cry() throws InterruptedException {
        System.out.println("美短 喵喵叫");
        Thread.sleep(1000);
    }
}
```

```java
import org.springframework.stereotype.Component;

/**
 * 英短
 *
 * @author changjinwei
 * @data 2020/8/4
 */
@Component
public class BritishShorthair {

    void cry() throws InterruptedException {
        System.out.println("英短 咪咪叫");
        Thread.sleep(1000);
    }
}
```
> 用注解配置横切关注点, `@Aspect``注解用来声明当前类是切面, ``@Pointcut``定义切点, ``Before``表明前置通知, ``@After``表明后置通知 ~~(感觉自己在讲废话o(╥﹏╥)o)~~

```java
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author changjinwei
 * @data 2020/8/4
 */
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CglibTest {

    private final AmericanShorthair americanShorthair;
    private final BritishShorthair britishShorthair;


    @Test
    public void cglibTest() throws InterruptedException {
        americanShorthair.cry();
        System.out.println("\n------------------\n");
        britishShorthair.cry();
    }
}
```
> 测试结果
```
开始吃水果的时间是：Tue Aug 04 18:44:05 CST 2020
开始吃苹果
结束吃苹果
结束吃水果的时间是：Tue Aug 04 18:44:06 CST 2020

------------------

开始吃水果的时间是：Tue Aug 04 18:44:06 CST 2020
开始吃香蕉
结束吃香蕉
结束吃水果的时间是：Tue Aug 04 18:44:07 CST 2020
```

## 3.3. 小结
嗨, 真的很无奈 Spring Boot统一大法好, 不用关心AOP框架是如何实现的    
开启``spring.aop.auto``和``spring.aop.proxy-target-class``两个配置后基本上对于我们来说基本上就是无感使用JDK动态代理和CGLIB动态代理   
如果你将两个配置设置为false你就会发现你的代理类失效了, 是的失效了 ~~(设置一个为false也是失效的, 我也不知道为什么呀, 内心想起了杨超越"我太难了 啥都不知道呀")~~


# 4. Spring支持的通知

> Spring支持的5种通知类型
- **Before advice(前置通知)** 表示目标方法执行前实施通知
- **After returning advice(正常运行通知)** 表示目标方法正常执行后实施通知
- **After throwing advice(异常抛出通知)** 表示目标方法抛出异常后实施通知
- **After (finally) advice(后置通知)** 表示目标方法最终(正常或异常)实施通知
- **Around advice(环绕通知)** 表示目标方法执行前后实施通知

下面将通过API的方式阐述Spring的各个通知类型的编程方式, 我们先创建一个简单的POJO类演示每一个API的使用
```java
import org.springframework.stereotype.Component;

/**
 * 用于测试的pojo类,
 *
 * @author changjinwei
 * @data 2020/8/5
 */
@Component
public class SimplePojo {

    /**
     * 用于测试
     * Before advice(前置通知)
     * After Returning Advice(后置通知)
     * Interception Around Advice(环绕通知)
     */
    public boolean hello(String name) {
        if (name == null) {
            return false;
        }
        System.out.println("我是" + name);
        return true;
    }

    /**
     * 用于测试
     * Throws Advice(异常抛出通知)
     */
    public void error() throws Exception {
        throw new Exception("错误");
    }
}
```
## 4.1. Before Advice(前置通知)

Spring的前置通知主要接口是MethodBeforeAdvice. 下面将通过实现MethodBeforeAdvice接口来新增一个前置通知类
```java
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 前置通知
 *
 * @author changjinwei
 * @data 2020/8/5
 */
@Component
public class SpringMethodBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("MethodBeforeAdvice通知的方法是: " + method.getName());
        System.out.println("MethodBeforeAdvice通知的方法的参数是: " + Arrays.toString(args));
        System.out.println("MethodBeforeAdvice通知的对象是: " + target);
    }
}
```


> 测试代码

```java
import com.galaxy.aop.demo.advice.type.apis.pojo.ISayHi;
import com.galaxy.aop.demo.advice.type.apis.pojo.SimplePojo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author changjinwei
 * @data 2020/8/5
 */
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdviceTest {

    /** 用于通知的类 */
    private final SimplePojo pojo;

    /** Before Advice(前置通知) */
    private final SpringMethodBeforeAdvice springMethodBeforeAdvice;

    /** 前置通知测试 */
    @Test
    public void methodBeforeAdviceTest() {
        //Spring提供的代理工厂
        ProxyFactory pf = new ProxyFactory(pojo);
        pf.addAdvice(springMethodBeforeAdvice);
        SimplePojo proxy = (SimplePojo) pf.getProxy();
        proxy.hello("小魏");
    }
}
```


> 测试结果

```
MethodBeforeAdvice通知的方法是: hello
MethodBeforeAdvice通知的方法的参数是: [小魏]
MethodBeforeAdvice通知的对象是: com.galaxy.aop.demo.advice.type.apis.pojo.SimplePojo@5ecba515
我是小魏
```


## 4.2. After Returning Advice(后置通知)

Spring的后置通知主要接口是AfterReturningAdvice, 下面将通过实现SpringAfterReturningAdvice接口来新增一个后置通知实现类

> 
```java
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 后置通知
 * @author changjinwei
 * @data 2020/8/5
 */
@Component
public class SpringAfterReturningAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("AfterReturningAdvice通知的方法返回值是: " + returnValue);
        System.out.println("AfterReturningAdvice通知的方法是: " + method.getName());
        System.out.println("AfterReturningAdvice通知的方法的参数是: " + Arrays.toString(args));
        System.out.println("AfterReturningAdvice通知的对象是: " + target);
    }
}
```


> 测试代码

```java
import com.galaxy.aop.demo.advice.type.apis.pojo.ISayHi;
import com.galaxy.aop.demo.advice.type.apis.pojo.SimplePojo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author changjinwei
 * @data 2020/8/5
 */
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdviceTest {
  
    /** 用于通知的类 */
    private final SimplePojo pojo;

    /** After Returning Advice(后置通知) */
    private final SpringAfterReturningAdvice springAfterReturningAdvice;

    /** 后置通知测试 */
    @Test
    public void afterReturningAdviceTest() {
        //Spring提供的代理工厂
        ProxyFactory pf = new ProxyFactory(pojo);
        pf.addAdvice(springAfterReturningAdvice);
        SimplePojo proxy = (SimplePojo) pf.getProxy();
        proxy.hello("小魏");
    }
}
```


> 测试结果

```
我是小魏
AfterReturningAdvice通知的方法返回值是: true
AfterReturningAdvice通知的方法是: hello
AfterReturningAdvice通知的方法的参数是: [小魏]
AfterReturningAdvice通知的对象是: com.galaxy.aop.demo.advice.type.apis.pojo.SimplePojo@29314cc9
```


## 4.3. Interception Around Advice(环绕通知)

Spring的环绕通知接口主要是SpringMethodInterceptor, 下面将通过SpringMethodInterceptor接口来新增一个环绕通知实现类

```java
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 环绕通知
 *
 * @author changjinwei
 * @data 2020/8/5
 */
@Component
public class SpringMethodInterceptor implements MethodInterceptor {


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("-----前置环绕通知-----");
        Object returnValue = invocation.proceed();
        System.out.println("-----后置环绕通知-----");


        System.out.println("MethodInterceptor通知的方法返回值是: " + returnValue);
        System.out.println("MethodInterceptor通知的方法是: " + invocation.getMethod());
        System.out.println("MethodInterceptor通知的方法的参数是: " + Arrays.toString(invocation.getArguments()));
        System.out.println("MethodInterceptor通知的对象是: " + invocation.getThis());
        return returnValue;
    }
}
```

> 测试代码

```java
import com.galaxy.aop.demo.advice.type.apis.pojo.ISayHi;
import com.galaxy.aop.demo.advice.type.apis.pojo.SimplePojo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author changjinwei
 * @data 2020/8/5
 */
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdviceTest {
  
    /** 用于通知的类 */
    private final SimplePojo pojo;

    /** Interception Around Advice(环绕通知) */
    private final SpringMethodInterceptor springMethodInterceptor;

    /** 环绕通知 */
    @Test
    public void methodInterceptorTest() {
        //Spring提供的代理工厂
        ProxyFactory pf = new ProxyFactory(pojo);
        pf.addAdvice(springMethodInterceptor);
        SimplePojo proxy = (SimplePojo) pf.getProxy();
        proxy.hello("小魏");
    }
}
```


> 测试结果

```
-----前置环绕通知-----
我是小魏
-----后置环绕通知-----
MethodInterceptor通知的方法返回值是: true
MethodInterceptor通知的方法是: public boolean com.galaxy.aop.demo.advice.type.apis.pojo.SimplePojo.hello(java.lang.String)
MethodInterceptor通知的方法的参数是: [小魏]
MethodInterceptor通知的对象是: com.galaxy.aop.demo.advice.type.apis.pojo.SimplePojo@2d6aca33
```

## 4.4. Throws Advice(异常抛出通知)

Spring的异常抛出通知主要接口是SpringThrowsAdvice, 下面将通过实现SpringThrowsAdvice接口来新增一个异常抛出通知实现类

```java
import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 异常通知
 *
 * @author changjinwei
 * @data 2020/8/5
 */
@Component
public class SpringThrowsAdvice implements ThrowsAdvice {


    public void afterThrowing(Exception e) throws Throwable {
        System.out.println("ThrowsAdvice异常通知抛出: " + e.getClass());
    }

    public void afterThrowing(Method method, Object[] args, Object target, Exception e) {
        System.out.println("ThrowsAdvice异常通知抛出: " + e.getClass());
        System.out.println("ThrowsAdvice通知的方法是: " + method.getName());
        System.out.println("ThrowsAdvice通知的方法的参数是: " + Arrays.toString(args));
        System.out.println("ThrowsAdvice通知的对象是: " + target);
    }
}
```

> 测试代码

```java
import com.galaxy.aop.demo.advice.type.apis.pojo.ISayHi;
import com.galaxy.aop.demo.advice.type.apis.pojo.SimplePojo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author changjinwei
 * @data 2020/8/5
 */
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdviceTest {
  
    /** 用于通知的类 */
    private final SimplePojo pojo;

    /** Throws Advice(异常抛出通知) */
    private final SpringThrowsAdvice springThrowsAdvice;

    /** 异常通知 */
    @Test
    public void throwsAdviceTest() {
        //Spring提供的代理工厂
        ProxyFactory pf = new ProxyFactory(pojo);
        pf.addAdvice(springThrowsAdvice);
        SimplePojo proxy = (SimplePojo) pf.getProxy();
        try {
            proxy.error();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
```


> 测试结果

```
ThrowsAdvice异常通知抛出: class java.lang.Exception
ThrowsAdvice通知的方法是: error
ThrowsAdvice通知的方法的参数是: []
ThrowsAdvice通知的对象是: com.galaxy.aop.demo.advice.type.apis.pojo.SimplePojo@29314cc9
java.lang.Exception: 错误
```

## 4.5. Introduction Advice(引介通知)
Spring的引介通知实现类是DelegatingIntroductionInterceptor, 下面将通过继承DelegatingIntroductionInterceptor来实现


```java
import com.galaxy.aop.demo.advice.type.apis.pojo.ISayHi;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.stereotype.Component;

/**
 * 引介通知
 *
 * @author changjinwei
 * @data 2020/8/7
 */

@Component
public class SpringDelegatingIntroductionInterceptor extends DelegatingIntroductionInterceptor implements ISayHi {

    @Override
    public boolean hi(String name) {
        if (name == null) {
            return false;
        }
        System.out.println("hi " + name);
        return true;
    }
}
```

> 测试代码

```java
import com.galaxy.aop.demo.advice.type.apis.pojo.ISayHi;
import com.galaxy.aop.demo.advice.type.apis.pojo.SimplePojo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author changjinwei
 * @data 2020/8/5
 */
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdviceTest {
  
    /** 用于通知的类 */
    private final SimplePojo pojo;

    /** Introduction Advice(引介通知) */
    private final SpringDelegatingIntroductionInterceptor springDelegatingIntroductionInterceptor;


    /** 引介通知 */
    @Test
    public void delegatingIntroductionInterceptorTest() {
        //Spring提供的代理工厂
        ProxyFactory pf = new ProxyFactory(pojo);
        pf.setProxyTargetClass(true);
        pf.addAdvice(springDelegatingIntroductionInterceptor);

        Object proxy = pf.getProxy();

        if (proxy instanceof SimplePojo) {
            ((SimplePojo) proxy).hello("小魏");
        }

        if (proxy instanceof ISayHi) {
            ((ISayHi) proxy).hi("小魏");
        }
    }
}
```


> 测试结果

```
我是小魏
hi 小魏
```

## 4.6. 切入点类型
切入点是匹配连接点的拦截规则. 之前的案例中使用的是@Pointcut注解, 该注解是AspectJ中的, 除了这个注解之外, Spring也提供了其他一些切入点类型

- StaticMethodMatcherPointcut(静态方法切入点)
- DynamicMethodMatcherPointcut(动态方法切入点)
- AnnotationMatchingPointcut(注解方法切入点)
- ExpressionPointcut(表达式切入点)
- ControlFlowPointcut(流程切入点)
- ComposablePointcut(复合切入点)
- TruePointcut(标准切入点)

# 5. Spring集成AspectJ实战

AspectJ是一个面向切面的框架, 其可以生成遵循Java字节码规范的Class文件.   
Spring AOP和AspectJ之间的关系是Spring使用了和AspectJ一样的注解, 并使用AspectJ来做切入点解析和匹配. 但是Spring AOP运行时并不依赖于AspectJ的编译器和编译器或者织入器等特性 ~~(Spring就是喜欢砍特性)~~

## 5.1. 使用 AspectJ方式配置Spring AOP

下面将通过AspectJ的方式实现AOP编程, 并通过案例阐述使用不同的AspectJ注解实现各种类型的通知.   
在AspectJ中
- @Aspect注解来表示一个切面 
- @Pointcut注解表示切入点
- @Before注解表示前置通知
- @Around注解表示环绕通知
- @AfterReturning注解表示后置通知
- @AfterThrowing表示异常通知


> 




## 5.2. AspectJ各种切点指示器

1. execution:  
    用于匹配方法执行的连接点。这是使用Spring AOP时要使用的主要切入点指示符。
2. this
    将匹配限制为联接点（使用Spring AOP时方法的执行），其中bean引用（Spring AOP代理）是给定类型的实例。
3. within
    将匹配限制为某些类型内的连接点（使用Spring AOP时，在匹配类型内声明的方法的执行）
4. @within
   将匹配限制为具有给定注释的类型内的连接点（使用Spring AOP时，使用给定注释的类型中声明的方法的执行）。
5. target
    在目标对象（代理的应用程序对象）是给定类型的实例的情况下，将匹配限制为连接点（使用Spring AOP时方法的执行）
6. @target
    在执行对象的类具有给定类型的注释的情况下，将匹配限制为连接点（使用Spring AOP时方法的执行）。
7. args
    将匹配限制为连接点（使用Spring AOP时方法的执行），其中参数是给定类型的实例。
8. @args
    限制匹配的连接点（使用Spring AOP时方法的执行），其中传递的实际参数的运行时类型具有给定类型的注释。
9.  @annotation
    将匹配点限制在连接点的主题（在Spring AOP中运行的方法）具有给定注释的连接点。

>完整的AspectJ切入点语言支持Spring不支持的其他切入点指示符   
call, get, set, preinitialization, staticinitialization, initialization, handler, adviceexecution, withincode, cflow, cflowbelow, if, @this, @withincode  
在Spring AOP解释的切入点表达式中使用这些切入点指示符会导致IllegalArgumentException抛出异常  
Spring AOP支持的切入点指示符集合可能会在将来的版本中扩展，以支持更多的AspectJ切入点指示符


## 5.3. execution

execution是最常见的切点函数, 期具体语法如下:

```java
// execution 表达式
execution(修饰符模式? 返回值模式 声明模式? 方法名模式 (参数模式) 异常模式?)
execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern)throws-pattern?)
// Java语法
void method()
public void method(String arg)
```

在execution表达式中只有``返回值模式``, ``方法名模式``, ``参数模式``是必选, 其他的都是可选的 ~~(参考一下Java语法就知道了嘛)~~   
``声明模式``中``.*``包中的所有类，不包括子孙包中的类, ``..*``包中以及子孙包的所有类。
``参数模式``中``*``表示任意类型参数, ``..``表示任意类型参数且参数不限个数




> 让我看看Spring官网中execution的demo

- 匹配所有目标类所有的public方法:   
  
      execution(public * *(..))
- 匹配所有目标类所有已set为前缀的方法:

      execution(* set*(..))
- 匹配AccountService类定义的所有方法:

      execution(* com.xyz.service.AccountService.*(..))
- 匹配service包中定义的所有方法:

      execution(* com.xyz.service.*.*(..))
- 匹配service包及子包中定义的所有方法:

      execution(* com.xyz.service..*.*(..))
- 匹配方法第一个入参是 int，第二个参数可以是任意类型

      execution(* *(int,*))

- 匹配方法只有一个入参，它可以是 Object 类型或该类型的子类
  
      execution(* *(Object+))	匹配 make() 方法，



## 5.4. this()

## 5.5. @annotation()

## 5.8. target()与@target()

## 5.6. args()与@args()

## 5.7. within()与@within()




# 6. 文献
- https://docs.spring.io/spring/docs/5.2.8.RELEASE/spring-framework-reference/core.html#aop-api
- https://docs.spring.io/spring/docs/2.0.x/reference/aop.html
- https://zhuanlan.zhihu.com/p/67041662
- https://developer.ibm.com/zh/technologies/java/articles/j-lo-springaopcglib/
- https://blog.csdn.net/weixin_34015860/article/details/91928418
- https://blog.csdn.net/z69183787/article/details/48733937
- https://zhuanlan.zhihu.com/p/139353840
- https://juejin.im/post/6845166891204345869
- https://blog.csdn.net/somilong/article/details/74568223