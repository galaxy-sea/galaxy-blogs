package com.galaxy.config.mvc;

import com.galaxy.config.mvc.resolvers.HttpBeanArgumentResolver;
import com.galaxy.config.mvc.resolvers.HttpBodyArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author galaxy
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    HttpBeanArgumentResolver httpBeanArgumentResolver;
    @Autowired
    HttpBodyArgumentResolver httpBodyArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(httpBeanArgumentResolver);
        argumentResolvers.add(httpBodyArgumentResolver);
    }


}
