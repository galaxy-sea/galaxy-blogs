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

package plus.wcj.abac.service;

import plus.wcj.abac.dao.UserDao;
import plus.wcj.abac.entity.User;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author changjin wei(魏昌进)
 * @since 2022/11/26
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    /** 根据userId获取用户详细信息 */
    public User get(Long userId) {
        User user = userDao.selectById(userId);
        List<String> repository = userDao.selectRepository(userId);
        user.setContributions(repository);
        return user;
    }
}
