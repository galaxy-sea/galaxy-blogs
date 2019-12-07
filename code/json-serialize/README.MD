# Spring Boot 自定义JSON序列化
最近遇到一个烦心的事情,公司用了OSS图片服务,但是呐主管说我们的OSS数据入库只存储OSS对象的相对路径(WTF什么鬼呀,哪有入库不用绝对路径呀, OSS服务商又不换,干啥要相对选对路径呀, 各种反对让我心累. 

取出数据格式就是这样的``/img/XXX.jpg``, 没有域名, 要把域名拼接上去呀想了四个方法

1. 前端做拼接
2. SQL拼接
3. 业务层拼接
4. JSON序列化拼接

> 好我们分析一下利弊哦, 

1. 让前端同学做数据的拼接最方便(OS: 我想偷懒). not pass: 前端拿数据做展示凭什么要拼接, 以及主管还支持他 
2. SQL拼接, img字段直接拼接方便呀. not pass: 我会做这么愚蠢的事嘛, 我绝对不会在SQL做操作, 担心会有脏数据
3. 业务层拼接, 注解+反射+静态方法. not pass: 狗, 隔壁工位的忘记调用静态方法了, fxxk dog
4. JSON序列化拼接, 用一个注解然后让JSON框架去处理 pass: 好方法,不用每一个server都调用静态方法了 ,不用打您隔壁工位忘记调静态方法了, 不用担心数据库脏数据了

> 内心又崩溃了, 我没看过jackson的源码呀, 要看框架源码, 不看不知道一看吓一跳(反正是头发秃了呀)
> 
> 平常用jackson的时候就真心反感(没fastjson好用), 看了源码, fxxk jackson团队在想屁吃吗(发一下牢骚哦, 其实他们还是很牛逼的, 不然会成为Spring Boot的亲儿子嘛, 文章后面总结一下jackson的深坑, 真的坑)
> 
> 学习第一步先百度

# 业务分析
> 定义一个注解和一个bean, 后面就是操作这两个类了, 
>
> 对注解``@ValuePrefix``的字段我们都需要添加前缀


```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ValuePrefix {
    String prefix() default "前缀";
}
```

```java
public class Panda {

    private String name;

    @ValuePrefix(prefix = "我是前缀")
    private String img;
    // getter and setter
}
```

# jackson
jackson是啥就不介绍了, 都是成年人都懂的(``本山大叔的猥琐笑容.jpg``)
## 序列化
我们先看看UML图

![StdScalarSerializer UML](https://github.com/galaxy-sea/galaxy-blogs/blob/master/image/StdScalarSerializer.png)

![StdScalarSerializer UML](https://raw.githubusercontent.com/galaxy-sea/galaxy-blogs/master/image/StdScalarSerializer.png)

![StdScalarSerializer UML](/image/StdScalarSerializer.png)



## 反序列化

## 总结

# fastjson
### 序列化
### 反序列化


# 参考
- https://yanbin.blog/customize-jackson-annotation-and-disable-specific-annotation/
- https://blog.csdn.net/liuxiao723846/article/details/42921333
- https://stackoverflow.com/questions/43214545/customdeserializer-has-no-default-no-arg-constructor
- https://codeday.me/bug/20190516/1112991.html
- https://ketao1989.github.io/2015/02/10/Jackson-Deserialize-Java-Object-Implementation/
- https://github.com/FasterXML/jackson-databind/issues/2324
- https://stackoverflow.com/questions/8944086/custom-jackson-deserializer-getting-access-to-current-field-class