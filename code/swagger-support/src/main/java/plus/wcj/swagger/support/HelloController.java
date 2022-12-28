/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package plus.wcj.swagger.support;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author changjin wei(魏昌进)
 * @since 2022/12/27
 */
@RestController
@RequestMapping("/hello")
@PostAuthorize("hasAuthority('class')")
@PostFilter("hasAuthority('class')")
@PreAuthorize("hasAuthority('class')")
@PreFilter("hasAuthority('class')")
public class HelloController {

    @GetMapping("/security")
    @PostAuthorize("hasAuthority('method')")
    @PostFilter("hasAuthority('method')")
    @PreAuthorize("hasAuthority('method')")
    @PreFilter("hasAuthority('method')")
    @ApiOperation(value = "", notes = "Spring Security注解追加到接口描述")
    public String security() {
        return "hello security";
    }
    @SuppressWarnings("unused")
    @GetMapping("/page")
    @ApiOperationSupport(ignoreParameters = {"current",})
    public String page(Page<Object> page) {
        return "hello page";
    }


    @SuppressWarnings("unused")
    @GetMapping("/orderBy")
    public String orderBy(Page<User> page) {
        return "hello orderBy";
    }


}
