```text
/*
* Copyright 2021-2022 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
  */
```

<!-- TOC -->

- [Spring Security 拦截问题](#spring-security-拦截问题)
- [一个注解搞定Spring Security 忽略拦截](#一个注解搞定spring-security-忽略拦截)
  - [基于注解形式](#基于注解形式)
  - [基于配置形式](#基于配置形式)
- [总结](#总结)

<!-- /TOC -->


>源码地址 [galaxy-sea/galaxy-blogs/code/security-annotation](https://github.com/galaxy-sea/galaxy-blogs/tree/master/code/security-annotation)   
>源码地址 [galaxy-sea/galaxy-blogs/code/security-annotation](https://github.com/galaxy-sea/galaxy-blogs/tree/master/code/security-annotation)   
>源码地址 [galaxy-sea/galaxy-blogs/code/security-annotation](https://github.com/galaxy-sea/galaxy-blogs/tree/master/code/security-annotation)


# Spring Security 拦截问题

Spring Security是干啥的我就不想解释了, 毕竟百度上面的讲解一大堆解说的可是会比我细致😄。

开发的时候我们经常需要对部分的@RequestMapping设置为 public api 不需要登陆也可以访问如登陆接口等等。偏爱Spring注解编程，那我们就基于注解的方式来忽略Spring Security的拦截吧。

```java
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
  WebSecurity.IgnoredRequestConfigurer ignoring = web.ignoring();
     ignoring.antMatchers("/login");
     ignoring.antMatchers("/public api...");
}
```

就如我们上面代码展示的一样，我们经常需要对Spring Security的忽略名单进行硬编码配置或者配置文件配置我都感觉挺繁琐的，Spring都已经提倡基于注解编程了，百度到的内容居然还是教我用硬编码所以下面我们就基于注解来实现Spring Security忽略拦截的实现吧。

#  一个注解搞定Spring Security 忽略拦截
本章节我们就用注解的方式来实现Spring Security忽略拦截吧。本章会分别介绍基于注解方式和配置文件方式
- 注解主要针对@RequestMapping进行忽略
- 配置文件主要针对静态文件（js，html，css，...）进行忽略

> 环境配置

- Spring Boot 2.5.X
  - Spring MVC
  - Spring Security
  - lombok
- Java 1.8

> 因为是单纯的演示一下如何基于注解忽略Spring Security拦截，所以只会使用最小demo而不是大篇长论的去魔改Spring Security（其实是我懒得写啦反正百度都有😄）  
> 老规矩下面直接放代码吧。

代码就需要大家去[start.spring.io](https://start.spring.io/)生成一下啦，然后改一下Spring Boot的版本号，或者去我的GitHub直接clone代码： [galaxy-sea/galaxy-blogs](https://github.com/galaxy-sea/galaxy-blogs/tree/master/code/security-annotation)

> SecurityConfiguration 配置类
```java
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(WebSecurity web) {
    WebSecurity.IgnoredRequestConfigurer ignoring = web.ignoring();
    ignoring.antMatchers(HttpMethod.GET, "/hello/hardcode");
  }
}
```

> HelloController用于测试Spring Security拦截器

```java

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author changjin wei(魏昌进)
 * @since 2022/12/15
 */
@RestController
@RequestMapping("hello")
public class HelloController {

  @GetMapping("/security")
  public String security(){
    return "Hello, Security";
  }

  @GetMapping("/hardcode")
  public String hardcode(){
    return "Hello, hardcode";
  }
}
```

> 测试结果

```shell
# 测试结果1
curl http://127.0.0.1:8080/hello/security -i
HTTP/1.1 401 
Content-Type: application/json

{"timestamp":"2022-12-16T10:54:44.403+00:00","status":401,"error":"Unauthorized","path":"/hello/security"}

# 测试结果2
curl http://127.0.0.1:8080/hello/hardcode -i
HTTP/1.1 200 
Content-Type: text/plain;charset=UTF-8

Hello, hardcod
```



## 基于注解形式

其实硬编码这种处理方式如果public api较少的情况下还是没有问题的，但是public api变多了就不是很友好了。  
那么现在我们就基于注解的形式实现一下吧，代码量及其精简的哦。

首先我们创建一个``IgnoreWebSecurity``注解用户将api设置为public api, 然后我们改造一下``SecurityConfiguration``类。

> IgnoreWebSecurity

```java
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author changjin wei(魏昌进)
 * @since 2022/1/3
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface IgnoreWebSecurity {
}
```


> 改造 SecurityConfiguration

```java

/**
 * @author changjin wei(魏昌进)
 * @since 2022/12/15
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


  private final RequestMappingHandlerMapping requestMappingHandlerMapping;


  @Override
  public void configure(WebSecurity web) {
    WebSecurity.IgnoredRequestConfigurer ignoring = web.ignoring();
    ignoring.antMatchers(HttpMethod.GET, "/hello/hardcode");
    this.ignoreAnnotation(ignoring, this.requestMappingHandlerMapping);
  }

  private void ignoreAnnotation(WebSecurity.IgnoredRequestConfigurer ignoring, RequestMappingHandlerMapping requestMappingHandlerMapping) {
    Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
    for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
      HandlerMethod handlerMethod = entry.getValue();
      if (handlerMethod.hasMethodAnnotation(IgnoreWebSecurity.class)) {
        Set<String> patternValues = entry.getKey().getPatternValues();
        Set<RequestMethod> methods = entry.getKey().getMethodsCondition().getMethods();
        if (CollectionUtils.isEmpty(methods)) {
          // RequestMapping没有指定method
          ignoring.antMatchers(patternValues.toArray(new String[0]));
        } else {
          for (RequestMethod method : methods) {
            // RequestMapping指定了method
            ignoring.antMatchers(HttpMethod.resolve(method.name()), patternValues.toArray(new String[0]));
          }
        }
      }
    }
  }
}
```


> HelloController 增加一下测试方法

```java
/**
 * @author changjin wei(魏昌进)
 * @since 2022/12/15
 */
@RestController
@RequestMapping("hello")
public class HelloController {

  /** 注解方式 */
  @GetMapping("/annotation")
  @IgnoreWebSecurity
  public String annotation() {
    return "Hello, annotation";
  }


  /** 测试PathVariable参数 */
  @GetMapping("/{path}/annotation")
  @IgnoreWebSecurity
  public String annotationPath(@PathVariable String path) {
    return "Hello, annotation. path: " + path;
  }
}
```


> 测试结果

```shell
# 测试注解
curl http://127.0.0.1:8080/hello/annotation -i
HTTP/1.1 200 
Content-Type: text/plain;charset=UTF-8
Content-Length: 17
Date: Sun, 18 Dec 2022 12:25:05 GMT

Hello, annotatio


# 测试注解
curl http://127.0.0.1:8080/hello/public_api/annotation -i
HTTP/1.1 200 
Content-Type: text/plain;charset=UTF-8
Content-Length: 35
Date: Sun, 18 Dec 2022 12:25:35 GMT

Hello, annotation. path: public_api
```


通过解析``RequestMappingHandlerMapping``我们就可以将api设置为public api了。这种方式大大简便的配置。



## 基于配置形式
上一章我们通过注解的方式去将api设置为public api但是这种方式对静态资源（html，js，css等）并不是很友好，好需要通过配置的文件的方式将一些静态资源设置为public，所以本章节通过配置文件方式来实现public api的忽略拦截

创建一个配置文件``IgnoreWebSecurityProperties``来配置

> IgnoreWebSecurityProperties

```java

/**
 * @author changjin wei(魏昌进)
 * @since 2022/12/15
 */
@Data
@ConfigurationProperties(prefix = "security.ignoring")
public class IgnoreWebSecurityProperties {
  /** 需要忽略的 URL 格式，不考虑请求方法 */
  private String[] pattern = {};

  /** 需要忽略的 GET 请求 */
  private String[] get = {};

  /**  需要忽略的 POST 请求 */
  private String[] post = {};

  /**  需要忽略的 DELETE 请求 */
  private String[] delete = {};

  /**  需要忽略的 PUT 请求 */
  private String[] put = {};

  /** 需要忽略的 HEAD 请求 */
  private String[] head = {};

  /** 需要忽略的 PATCH 请求 */
  private String[] patch = {};

  /** 需要忽略的 OPTIONS 请求 */
  private String[] options = {};

  /** 需要忽略的 TRACE 请求 */
  private String[] trace = {};
}
```
> 改造 IgnoreWebSecurityProperties

```java

/**
 * @author changjin wei(魏昌进)
 * @since 2022/12/15
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(IgnoreWebSecurityProperties.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final IgnoreWebSecurityProperties ignoreWebSecurityProperties;
  private final RequestMappingHandlerMapping requestMappingHandlerMapping;

  @Override
  public void configure(WebSecurity web) {
    WebSecurity.IgnoredRequestConfigurer ignoring = web.ignoring();
    ignoring.antMatchers(HttpMethod.GET, "/hello/hardcode");
    this.ignoreAnnotation(ignoring, this.requestMappingHandlerMapping);
    this.ignoreProperties(ignoring, this.ignoreWebSecurityProperties);
  }
    ...
  private void ignoreProperties(WebSecurity.IgnoredRequestConfigurer ignoring, IgnoreWebSecurityProperties ignoreWebSecurityProperties) {
    ignoring.antMatchers(HttpMethod.GET, ignoreWebSecurityProperties.getGet())
            .antMatchers(HttpMethod.POST, ignoreWebSecurityProperties.getPost())
            .antMatchers(HttpMethod.DELETE, ignoreWebSecurityProperties.getDelete())
            .antMatchers(HttpMethod.PUT, ignoreWebSecurityProperties.getPut())
            .antMatchers(HttpMethod.HEAD, ignoreWebSecurityProperties.getHead())
            .antMatchers(HttpMethod.PATCH, ignoreWebSecurityProperties.getPatch())
            .antMatchers(HttpMethod.OPTIONS, ignoreWebSecurityProperties.getOptions())
            .antMatchers(HttpMethod.TRACE, ignoreWebSecurityProperties.getTrace())
            .antMatchers(ignoreWebSecurityProperties.getPattern());
  }
}

```

> 改造HelloController
```java
/**
 * @author changjin wei(魏昌进)
 * @since 2022/12/15
 */
@RestController
@RequestMapping("hello")
public class HelloController {
  /** 测试PathVariable参数 */
  @GetMapping("/properties")
  @IgnoreWebSecurity
  public String properties(){
    return "Hello, properties";
  }

  /** 测试PathVariable参数 */
  @GetMapping("/{path}/properties")
  @IgnoreWebSecurity
  public String propertiesPath(@PathVariable String path){
    return "Hello, properties. path: " + path;
  }
}
```


> application.yml
```yaml
security:
  ignoring:
    get:
      - /hello/{path}/properties
```

> 测试结果

```shell
# 配置文件测试
curl http://127.0.0.1:8080/hello/properties -i
HTTP/1.1 200 
Content-Type: text/plain;charset=UTF-8
Content-Length: 17
Date: Sun, 18 Dec 2022 12:35:14 GMT

Hello, properties

# 配置文件测试
curl http://127.0.0.1:8080/hello/public_api/properties -i
HTTP/1.1 200 
Content-Type: text/plain;charset=UTF-8
Content-Length: 35
Date: Sun, 18 Dec 2022 12:36:01 GMT

Hello, properties. path: public_api
```

# 总结

其实无论是介于注解方式还是配置文件方式都各有优缺点看大家是否的选择了，通过这种方式可以减少代码硬编码的问题。
