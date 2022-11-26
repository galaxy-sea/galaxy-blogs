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

import plus.wcj.abac.dao.AbacDao;
import plus.wcj.abac.entity.Abac;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author changjin wei(魏昌进)
 * @since 2022/11/26
 */
@Service
@RequiredArgsConstructor
public class AbacService {
    private final AbacDao abacDao;

    /** 获取abac表达式详细信息列表 */
    public List<Abac> getAll() {
        List<Abac> abacs = abacDao.selectList(null);
        for (Abac abac : abacs) {
            List<String> permissions = abacDao.selectPermissions(abac.getId());
            abac.setPermissions(permissions);
        }
        return abacs;
    }

}
