
<!-- TOC -->

- [1. 反射](#1-%e5%8f%8d%e5%b0%84)
  - [1.1. 准备工作](#11-%e5%87%86%e5%a4%87%e5%b7%a5%e4%bd%9c)
- [2.1. 反射的简单操作](#21-%e5%8f%8d%e5%b0%84%e7%9a%84%e7%ae%80%e5%8d%95%e6%93%8d%e4%bd%9c)
  - [获取class有三种方法](#%e8%8e%b7%e5%8f%96class%e6%9c%89%e4%b8%89%e7%a7%8d%e6%96%b9%e6%b3%95)
  - [实例化对象](#%e5%ae%9e%e4%be%8b%e5%8c%96%e5%af%b9%e8%b1%a1)
- [2. Class(类)反射](#2-class%e7%b1%bb%e5%8f%8d%e5%b0%84)
  - [2.1. class相关方法](#21-class%e7%9b%b8%e5%85%b3%e6%96%b9%e6%b3%95)
    - [2.1.1. 获取class相关的方法](#211-%e8%8e%b7%e5%8f%96class%e7%9b%b8%e5%85%b3%e7%9a%84%e6%96%b9%e6%b3%95)
    - [2.1.2. 获取Constructor相关的方法](#212-%e8%8e%b7%e5%8f%96constructor%e7%9b%b8%e5%85%b3%e7%9a%84%e6%96%b9%e6%b3%95)
    - [2.1.3. 获取Field相关的方法](#213-%e8%8e%b7%e5%8f%96field%e7%9b%b8%e5%85%b3%e7%9a%84%e6%96%b9%e6%b3%95)
    - [2.1.4. 获取Method相关的方法](#214-%e8%8e%b7%e5%8f%96method%e7%9b%b8%e5%85%b3%e7%9a%84%e6%96%b9%e6%b3%95)
    - [2.1.5. 获取Annotation相关的方法](#215-%e8%8e%b7%e5%8f%96annotation%e7%9b%b8%e5%85%b3%e7%9a%84%e6%96%b9%e6%b3%95)
- [3. package(包)反射](#3-package%e5%8c%85%e5%8f%8d%e5%b0%84)
- [4. Constructor(构造方法)反射](#4-constructor%e6%9e%84%e9%80%a0%e6%96%b9%e6%b3%95%e5%8f%8d%e5%b0%84)
- [5. Field(字段)反射](#5-field%e5%ad%97%e6%ae%b5%e5%8f%8d%e5%b0%84)
- [6. Method(方法)反射](#6-method%e6%96%b9%e6%b3%95%e5%8f%8d%e5%b0%84)
- [7. 静态方法反射](#7-%e9%9d%99%e6%80%81%e6%96%b9%e6%b3%95%e5%8f%8d%e5%b0%84)
- [8. Annotation(注解)反射](#8-annotation%e6%b3%a8%e8%a7%a3%e5%8f%8d%e5%b0%84)

<!-- /TOC -->

# 1. 反射
反射一个能够骚操作的玩意儿让你不得不爱，在json序列化时用的就是反射，mybatis返回数据的时候也是用的反射，反射让你不得不爱上他
## 1.1. 准备工作
创建几个符合场景的类吧,一个父类,一个子类,一个接口,一个泛型等等等,代码如下
```java
public enum Color {
    /**
     * 白色
     */
    WHITE,
    /**
     * 黑色
     */
    BLACK,
}
```
```java
public abstract class Father {

    protected Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
```
```java
public interface Runing {

    void run();

}
```

```java
public class Panda extends Father implements Runing {

    private String name;
    private Integer age;

    public Panda() {
    }

    public Panda(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

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
```

# 2.1. 反射的简单操作
## 获取class有三种方法

```java
    /**
     * class对象的三种实例化方法
     */
    @Test
    public void test() throws ClassNotFoundException {
        // 方法1
        Panda panda = new Panda();
        Class<? extends Panda> pandaClass1 = panda.getClass();

        // 方法2
        Class<Panda> pandaClass2 = Panda.class;

        // 方法3  这个方式会抛出异常哦
        Class<?> pandaClass3 = Class.forName("com.galaxy.reflect.bean.Panda");

        System.out.println(pandaClass1);
        System.out.println(pandaClass1);
        System.out.println(pandaClass3);
    }
```
注意：在开发中一般使用第三种方法，因为第三种接收的是一个字符串路径，将来可以通过配置文件获取，通用性好, 字母大小写敏感
让我们看看class类中有什么操作
## 实例化对象


# 2. Class(类)反射

## 2.1. class相关方法

### 2.1.1. 获取class相关的方法

| 方法                       | 用途                                                                                                        |
| :------------------------- | :---------------------------------------------------------------------------------------------------------- |
| getName()                  | 获得类的完整路径名字                                                                                        |
| getPackage()               | 获得类的包                                                                                                  |
| getSimpleName()            | 获得类的名字                                                                                                |
| newInstance()              | 创建类的实例                                                                                                |
| asSubclass(Class<U> clazz) | 检查class对象是否时传入class对象的之类,不是会报异常ClassCastException, 如果此方法成功，它将返回自身的应用。 |
| cast(Object obj)           | 把对象转换成代表类或是接口的对象                                                                            |
| getClassLoader()           | 获得类的加载器                                                                                              |
| getClasses()               | 返回该类中所有公共类和接口类的对象                                                                          |
| getDeclaredClasses()       | 返回该类中所有类和接口类的对象                                                                              |
| getSuperclass()            | 获得当前类继承的父类的名字                                                                                  |
| getInterfaces()            | 获得当前类实现的类或是接口                                                                                  |
| forName(String className)  | 根据类名返回类的对象                                                                                        |

>demo
```java
   @Test
    public void classDemo() throws IllegalAccessException, InstantiationException {

        /** 通过“newInstance”方法实例化一个对象 */
        Panda panda = Panda.class.newInstance();
        System.out.println(panda);


        /** 获取class的全路径名 */
        String name = Panda.class.getName();
        System.out.println("name = " + name);


        /** 获取包路径名 */
        Package packagePath = Panda.class.getPackage();
        System.out.println("package = " + packagePath);


        /** 获取class名 */
        String simpleName = Panda.class.getSimpleName();
        System.out.println("simpleName = " + simpleName);


        /** 检查class是否是传入class的之类 */
        // Class<? extends Panda> FatherClass = Father.class.asSubclass(Panda.class);
        Class<? extends Father> PandaClass1 = Panda.class.asSubclass(Father.class);
        Class<? extends Runing> PandaClass2 = Panda.class.asSubclass(Runing.class);
        System.out.println("PandaClass1 = " + PandaClass1);
        System.out.println("PandaClass2 = " + PandaClass2);


        /** 建传入的对象转换为该class */
        // Panda panda1 = Panda.class.cast(new Father());
        Father father = Father.class.cast(panda);
        Runing runing = Runing.class.cast(panda);
        System.out.println("father = " + father);
        System.out.println("runing = " + runing);


        /** 获取类加载器 */
        ClassLoader classLoader = Panda.class.getClassLoader();

        /** 获取puclic的内部类 */
        Class<?>[] classes = Panda.class.getClasses();
        System.out.println("classes = " + Arrays.toString(classes));

        /** 获取已声明的内部类 */
        Class<?>[] declaredClasses = Panda.class.getDeclaredClasses();
        System.out.println("declared Classes =" + Arrays.toString(declaredClasses));

        /** 获取父类的class */
        Class<? super Panda> superclass = Panda.class.getSuperclass();
        System.out.println("superclass = " + superclass);

        /** 获取实现类的的class */
        Class<?>[] interfaces = Panda.class.getInterfaces();
        System.out.println("interfaces = " + Arrays.toString(interfaces));
        
    }
```

> 注意: 不带```Declared```的方法只会获取``"public"``<br>
> 注意: 带```带Declared```会获取全部的``"public"``,``"protected"``,``"默认"``,``"private"``


### 2.1.2. 获取Constructor相关的方法

| 方法                                               | 用途                                   |
| :------------------------------------------------- | :------------------------------------- |
| getConstructors()                                  | 获得该类的所有公有构造方法             |
| getConstructor(Class...<?> parameterTypes)         | 获得该类中与参数类型匹配的公有构造方法 |
| getDeclaredConstructors()                          | 获得该类所有构造方法                   |
| getDeclaredConstructor(Class...<?> parameterTypes) | 获得该类中与参数类型匹配的构造方法     |


### 2.1.3. 获取Field相关的方法

| 方法                          | 用途                   |
| :---------------------------- | :--------------------- |
| getFields()                   | 获得所有公有的属性对象 |
| getField(String name)         | 获得某个公有的属性对象 |
| getDeclaredFields()           | 获得所有属性对象       |
| getDeclaredField(String name) | 获得某个属性对象       |


### 2.1.4. 获取Method相关的方法

| 方法                                                       | 用途                   |
| :--------------------------------------------------------- | :--------------------- |
| getMethods()                                               | 获得该类所有公有的方法 |
| getMethod(String name, Class...<?> parameterTypes)         | 获得该类某个公有的方法 |
| getDeclaredMethods()                                       | 获得该类所有方法       |
| getDeclaredMethod(String name, Class...<?> parameterTypes) | 获得该类某个方法       |

### 2.1.5. 获取Annotation相关的方法

| 方法                                            | 用途                                   |
| :---------------------------------------------- | :------------------------------------- |
| getAnnotations()                                | 返回该类所有的公有注解对象             |
| getAnnotation(Class<A> annotationClass)         | 返回该类中与参数类型匹配的公有注解对象 |
| getDeclaredAnnotations()                        | 返回该类所有的注解对象                 |
| getDeclaredAnnotation(Class<A> annotationClass) | 返回该类中与参数类型匹配的所有注解对象 |

# 3. package(包)反射

# 4. Constructor(构造方法)反射

# 5. Field(字段)反射

# 6. Method(方法)反射

# 7. 静态方法反射

# 8. Annotation(注解)反射



> 参考博客
1. https://www.jianshu.com/p/9be58ee20dee