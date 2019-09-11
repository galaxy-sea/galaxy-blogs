<!-- TOC -->

- [AOP](#aop)
- [Spring 实现](#spring-%e5%ae%9e%e7%8e%b0)
  - [基本概念](#%e5%9f%ba%e6%9c%ac%e6%a6%82%e5%bf%b5)
  - [Aspect(切面) && Advice(通知)](#aspect%e5%88%87%e9%9d%a2--advice%e9%80%9a%e7%9f%a5)
  - [Pointcut(切入点)](#pointcut%e5%88%87%e5%85%a5%e7%82%b9)
  - [pointcut expression(切入点表达式)](#pointcut-expression%e5%88%87%e5%85%a5%e7%82%b9%e8%a1%a8%e8%be%be%e5%bc%8f)
    - [execution 示例](#execution-%e7%a4%ba%e4%be%8b)

<!-- /TOC -->

# AOP
面向切面编程,是面向对象编程的重要组成部分.在不改变业务逻辑功能的基础上,对横切逻辑进行扩展

# Spring 实现

- Java 8
- Spring 5.0
- Spring Boot
```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

> JavaBean

```Java
@Component
public class Cat {
    public void hello() {
        System.out.println("Cat: hello");
    }

    public void helloException() throws Exception {
        throw new Exception("Cat: 异常了");
    }
}
```

```java
@Component
public class Dog {
    public void hello() {
        System.out.println("Dog: hello");
    }

    public void helloException() throws Exception {
        throw new Exception("Cat: 异常了");
    }
}
```
## 基本概念
- Aspect
- Join point
- Advice
- Pointcut
- Introduction
- Target object
- AOP proxy
- Weaving

advice(通知)类型
- Before(前)
- After(后)
- AfterReturning(运行成功))
- AfterThrowing(运行异常)
- Around(环绕))


## Aspect(切面) && Advice(通知)

```java
@Component
@Aspect
public class AopTest {

    @Before("bean(cat)")
    public void beforeTest() {
        System.out.println("方法之前执行...");
    }

    @After("bean(cat)")
    public void afterTest() {
        System.out.println("方法之后执行...");
    }

    @AfterReturning("bean(cat)")
    public void afterReturningTest() {
        System.out.println("方法执行成功...");
    }

    @AfterThrowing("bean(cat)")
    public void afterThrowingTest() {
        System.out.println("方法执行异常...");
    }

    @Around("bean(cat)")
    public Object aroundTest(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("环绕通知---前");
        //调用层的方法
        Object obj = jp.proceed();
        System.out.println("环绕通知---后");
        return obj;
    }
}
```
```java
public class SpringAopTest {

    @Autowired
    private Cat cat;

    @Test
    public void AopTest() throws Exception {
        cat.hello();
        System.out.println("------------------------------");
        cat.helloException();
    }
}
```

> ``@Aspect(切面)`` 是用于表示当前类是AOP类的声明, 只有使用了@Aspect注解才能使用AOP的功能

> ``Advice(通知)`` 通知定义了在切入点代码执行时间点附近需要做的工作 Spring支持5种类型的通知``@Before`` ``@After``   ``@AfterReturning`` ``@AfterThrowing`` ``@Around``



## Pointcut(切入点)
> Pointcut是指要在那个方法种切入``Advice(通知)``, 类的全部方法还是类的某些方法

创建切入点的方式有两种
1. @Pointcut注解
2. 覆盖``@Before`` ``@After``   ``@AfterReturning`` ``@AfterThrowing`` ``@Around``中的``String value()``或者``String pointcut()``

```Java
@Component
@Aspect
public class AopDemo {

    @Pointcut("execution(public * hello(..))")
    private void hello() {}

    @Pointcut("execution(public * helloException(..))")
    private void helloException() {}

    @AfterReturning("hello()")
    public void afterReturningTest() {
        System.out.println("方法执行成功...");
    }

    @AfterThrowing(pointcut = "helloException()")
    public void afterThrowingTest() {
        System.out.println("方法执行异常...");
    }
}
```


## pointcut expression(切入点表达式)

|                    |                                              |
| ------------------ | -------------------------------------------- |
| execution          | 匹配方法                        |
| within             | 匹配指定类型内的方法执行                     |
| this               | 匹配当前AOP代理对象的执行方法                |
| target             | 匹配当前目标对象类型的执行方法               |
| args               | 当执行的方法的参数是指定类型时生效           |
| @target            | 当代理的目标对象上拥有指定的注解时生效       |
| @args              | 当执行的方法参数类型上拥有指定的注解时生效。 |
| @within            | 匹配所有持有指定类型内的方法                 |
| @annotation        | 用于匹配当前执行方法持有指定注解的方法       |
| bean               | 匹配指定的对象                               |
| reference pointcut | 表示用于其他命名切入点                       |

### execution 示例




