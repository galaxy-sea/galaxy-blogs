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

package plus.wcj.abac;

import org.junit.jupiter.api.Test;
import plus.wcj.abac.entity.Abac;
import plus.wcj.abac.entity.User;
import plus.wcj.abac.security.MetadataCustomizer;
import plus.wcj.abac.security.SecurityContext;
import plus.wcj.abac.service.AbacService;
import plus.wcj.abac.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author changjin wei(魏昌进)
 * @since 2022/11/26
 */
@SpringBootTest
class AbacApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private AbacService abacService;

    @Autowired
    private SecurityContext securityContext;

    /** 获取不同用户的abac权限 */
    @Test
    void testRbac() {
        User user = userService.get(1L);
        List<Abac> rbac = abacService.getAll();
        List<String> permissions = securityContext.rbacPermissions(user, rbac);
        System.out.println(permissions);


        user = userService.get(2L);
        permissions = securityContext.rbacPermissions(user, rbac);
        System.out.println(permissions);

        user = userService.get(3L);
        permissions = securityContext.rbacPermissions(user, rbac);
        System.out.println(permissions);


    }

    /**
     * 获取自定义权限
     */
    @Test
    void testMetadataCustomizer() {
        User user = userService.get(1L);
        List<Abac> rbac = abacService.getAll();

        List<String> permissions = securityContext.rbacPermissions(user, rbac);
        System.out.println(permissions);

        permissions = securityContext.rbacPermissions(user, rbac, getMetadataCustomizer());
        System.out.println(permissions);
    }

    /** 模拟网络ip */
    private List<MetadataCustomizer> getMetadataCustomizer() {
        return new ArrayList<MetadataCustomizer>() {{
            add(user -> user.getMetadata().put("ip", "192.168.0.1"));
        }};
    }
}
