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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /** 注解方式 */
    @GetMapping("/annotation")
    @IgnoreWebSecurity
    public String annotation(){
        return "Hello, annotation";
    }


    /** 测试PathVariable参数 */
    @GetMapping("/{path}/annotation")
    @IgnoreWebSecurity
    public String annotationPath(@PathVariable String path){
        return "Hello, annotation. path: " + path;
    }



    /** 测试PathVariable参数 */
    @GetMapping("/properties")
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
