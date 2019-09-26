package com.galaxy.config;

import com.galaxy.result.ResultHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigurerImpl implements WebMvcConfigurer {


    private final ResultHandlerInterceptor resultHandlerInterceptor;

    public WebMvcConfigurerImpl(ResultHandlerInterceptor resultHandlerInterceptor) {
        this.resultHandlerInterceptor = resultHandlerInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(resultHandlerInterceptor).addPathPatterns("/**");
    }
}
