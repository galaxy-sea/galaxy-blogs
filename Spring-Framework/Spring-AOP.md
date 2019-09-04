<!-- TOC -->

- [AOP](#aop)
- [Spring 实现](#spring-%e5%ae%9e%e7%8e%b0)
  - [AOP Demo - bean](#aop-demo---bean)
  - [@Pointcut(切入点)](#pointcut%e5%88%87%e5%85%a5%e7%82%b9)
    - [pointcut expression(切入点表达式)](#pointcut-expression%e5%88%87%e5%85%a5%e7%82%b9%e8%a1%a8%e8%be%be%e5%bc%8f)

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
## AOP Demo - bean
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

```
环绕通知---前
方法之前执行...
Cat: hello
环绕通知---后
方法之后执行...
方法执行成功...
------------------------------
环绕通知---前
方法之前执行...
方法之后执行...
方法执行异常...

```
> 这里就有三个问题要深思了<br>
> 1：bean下的所有方法都触发了通知<br>
> 2：多个bean怎么触发通知<br>
> 3：我只要某些bean下的方法
>
> spring针对这些情况提供了 ``pointcut expression(切入点表达式)``使用这个表达式就可以自由的选择切入点了

## @Pointcut(切入点)
### pointcut expression(切入点表达式)



