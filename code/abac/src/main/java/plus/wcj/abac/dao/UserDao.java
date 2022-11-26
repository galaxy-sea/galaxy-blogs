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

package plus.wcj.abac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import plus.wcj.abac.entity.User;

import java.util.List;

/**
 * @author changjin wei(魏昌进)
 * @since 2022/11/26
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

    /** 获取用户的仓库信息 */
    @Select("SELECT repository FROM user_contribution WHERE user_id = #{userId}")
    List<String> selectRepository(@Param("userId") Long userId);
}
