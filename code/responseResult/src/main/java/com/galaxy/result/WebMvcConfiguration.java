package com.galaxy.result;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author changjinwei
 * @date 2020/11/10
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {


    /**
     * // 这里是修复 com.galaxy.controller.HelloResultController.testString 方法， 解决方案有两个
     * // 1。 重写 org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor.writeWithMessageConverters(T, org.springframework.core.MethodParameter, org.springframework.http.server.ServletServerHttpRequest, org.springframework.http.server.ServletServerHttpResponse)
     * // 2。 实现WebMvcConfigurer或者继承WebMvcConfigurationSupport 重写 #extendMessageConverters(java.util.List)方法,
     * // 第二个方法实现如下
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(converter -> converter instanceof StringHttpMessageConverter);
    }
}

