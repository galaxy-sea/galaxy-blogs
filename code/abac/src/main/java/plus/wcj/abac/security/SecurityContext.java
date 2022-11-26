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

package plus.wcj.abac.security;

import plus.wcj.abac.entity.Abac;
import plus.wcj.abac.entity.User;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 解析abac表达式
 *
 * @author changjin wei(魏昌进)
 * @since 2022/11/26
 */
@Component
public class SecurityContext {
    /** SpEL表达式解析器 */
    private final ExpressionParser expressionParser = new SpelExpressionParser();

    /**
     *  解析abac表达式
     * @param user 用户详细信息
     * @param abacs abac表达式详细信息集合
     * @return expressions集合, 将这个结果集存放到 Spring Security 或者Apache APISIX的userDetail上下文中
     */
    public List<String> rbacPermissions(User user, List<Abac> abacs) {
        return this.rbacPermissions(user, abacs, Collections.emptyList());
    }

    /**
     * 解析abac表达式
     * @param user 用户详细信息
     * @param abacs abac表达式详细信息集合
     * @param metadataCustomizers  自定义用户元数据  用于获取一些实体的属性、操作类型、相关的环境
     * @return expressions集合, 将这个结果集存放到 Spring Security 或者Apache APISIX的userDetail上下文中
     */
    public List<String> rbacPermissions(User user, List<Abac> abacs, List<MetadataCustomizer> metadataCustomizers) {
        // 处理自定义元数据
        metadataCustomizers.forEach(metadataCustomizer -> metadataCustomizer.customize(user));

        List<String> expressions = new ArrayList<>();
        for (Abac abac : abacs) {
            // 解析表达式的求值器
            Expression expression = expressionParser.parseExpression(abac.getExpression());
            // 创建环境上下文
            EvaluationContext context = new StandardEvaluationContext(user);
            // 获取expression的结果
            if (expression.getValue(context, boolean.class)) {
                expressions.addAll(abac.getPermissions());
            }
        }
        return expressions;
    }

}
