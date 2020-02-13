
<!-- TOC -->

- [1. spring cloud alibaba 消费方式](#1-spring-cloud-alibaba-%e6%b6%88%e8%b4%b9%e6%96%b9%e5%bc%8f)
  - [1.1. discovery](#11-discovery)
  - [1.2. restTemplate](#12-resttemplate)
  - [1.3. webClient](#13-webclient)
- [2. Ribbon 负载均衡](#2-ribbon-%e8%b4%9f%e8%bd%bd%e5%9d%87%e8%a1%a1)
  - [2.1. @LoadBalanced 引入模版](#21-loadbalanced-%e5%bc%95%e5%85%a5%e6%a8%a1%e7%89%88)
  - [2.2. Ribbon的配置](#22-ribbon%e7%9a%84%e9%85%8d%e7%bd%ae)
  - [2.3. 客户端配置](#23-%e5%ae%a2%e6%88%b7%e7%ab%af%e9%85%8d%e7%bd%ae)
  - [2.4. Ribbon热加载](#24-ribbon%e7%83%ad%e5%8a%a0%e8%bd%bd)
- [2. Feign](#2-feign)
  - [2.1. 引入Feign](#21-%e5%bc%95%e5%85%a5feign)
  - [2.2. Feign配置项](#22-feign%e9%85%8d%e7%bd%ae%e9%a1%b9)
  - [2.3. Feign日志](#23-feign%e6%97%a5%e5%bf%97)
  - [2.4. Fallbacks](#24-fallbacks)

<!-- /TOC -->

# 1. spring cloud alibaba 消费方式

## 1.1. discovery
``org.springframework.cloud.client.discovery.DiscoveryClient  `` 是服务发现客户端的顶级父类

``org.springframework.cloud.client.ServiceInstance``服务实例

```java
    public String discovery() throws Exception {
        // 获取服务
        List<String> services = discoveryClient.getServices();
        
        // 获取实例
        List<ServiceInstance> instances = discoveryClient.getInstances("alien-server");
        String url = instances.stream()
                .findFirst()
                .map(serviceInstance -> "http://" + serviceInstance.getServiceId() + "/hello?name=discovery")
                .orElseThrow(() -> new Exception("没有instances实例"));
        
        String result = restTemplate.getForObject(url, String.class);

        return "Return : " + result;
    }
```



## 1.2. restTemplate
```java
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
```

```java
    public String restTemplate() {
        String result = restTemplate.getForObject("http://alien-server/hello?name=restTemplate", String.class);
        return "Return : " + result;
    }
```

## 1.3. webClient
```java
    @Bean
    @LoadBalanced
    public WebClient.Builder webClient() {
        return WebClient.builder();
    }
```

```java
    public String webClient() {
        Mono<String> result = webClient.build()
                .get()
                .uri("http://alien-server/hello?name=WebClient")
                .retrieve()
                .bodyToMono(String.class);
        return "Return : " + result.block();
    }
```

# 2. Ribbon 负载均衡

- 资料
- https://cloud.spring.io/spring-cloud-static/Greenwich.SR5/single/spring-cloud.html#spring-cloud-ribbon



``Ribbon是负载均衡``, spring cloud alibaba 默认引入了

只要在引入的的``httpClient``添加``@LoadBalanced``就可以了

```java
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder webClient() {
        return WebClient.builder();
    }
```

## 2.1. @LoadBalanced 引入模版
标记了``@LoadBalanced``的``http client``可以实现负债均衡算法
> 不使用LoadBalanced: 
```java
    // http的address要使用域名或者IP
    restTemplate.getForObject("http://127.0.0.1:8080/hello?name=restTemplate", String.class);
    restTemplate.getForObject("http://galaxy.com/hello?name=restTemplate", String.class);
```

> 使用LoadBalanced
```java
    // http的address使用服务名称
    restTemplate.getForObject("http://alien-server/hello?name=restTemplate", String.class);
```

## 2.2. Ribbon的配置
[Ribbon属性配置](https://cloud.spring.io/spring-cloud-static/Greenwich.SR5/single/spring-cloud.html#_customizing_the_ribbon_client)

## 2.3. 客户端配置
[客户端配置](https://cloud.spring.io/spring-cloud-static/Greenwich.SR5/single/spring-cloud.html#_customizing_the_ribbon_client_by_setting_properties)

## 2.4. Ribbon热加载
[热加载](https://cloud.spring.io/spring-cloud-static/Greenwich.SR5/single/spring-cloud.html#ribbon-child-context-eager-load)



# 2. Feign
资料
1. [spring cloud Feign资料](https://cloud.spring.io/spring-cloud-static/Greenwich.SR5/single/spring-cloud.html#_spring_cloud_openfeign)


## 2.1. 引入Feign
[引入](https://cloud.spring.io/spring-cloud-static/Greenwich.SR5/single/spring-cloud.html#netflix-feign-starter)

## 2.2. Feign配置项
[Feign配置项](https://cloud.spring.io/spring-cloud-static/Greenwich.SR5/single/spring-cloud.html#spring-cloud-feign-overriding-defaults)

## 2.3. Feign日志
[Feign日志](https://cloud.spring.io/spring-cloud-static/Greenwich.SR5/single/spring-cloud.html#_feign_logging)

## 2.4. Fallbacks
[Fallbacks](https://cloud.spring.io/spring-cloud-static/Greenwich.SR5/single/spring-cloud.html#spring-cloud-feign-hystrix-fallback)