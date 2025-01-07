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

package plus.wcj.security.annotation;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.Set;

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

    private void ignoreAnnotation(WebSecurity.IgnoredRequestConfigurer ignoring, RequestMappingHandlerMapping requestMappingHandlerMapping) {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            if (handlerMethod.hasMethodAnnotation(IgnoreWebSecurity.class) || AnnotatedElementUtils.hasAnnotation(handlerMethod.getBeanType(), IgnoreWebSecurity.class)) {
                Set<String> patternValues = entry.getKey().getPatternValues();
                Set<RequestMethod> methods = entry.getKey().getMethodsCondition().getMethods();
                if (CollectionUtils.isEmpty(methods)) {
                    ignoring.antMatchers(patternValues.toArray(new String[0]));
                } else {
                    for (RequestMethod method : methods) {
                        ignoring.antMatchers(HttpMethod.resolve(method.name()), patternValues.toArray(new String[0]));
                    }
                }
            }
        }
    }

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
