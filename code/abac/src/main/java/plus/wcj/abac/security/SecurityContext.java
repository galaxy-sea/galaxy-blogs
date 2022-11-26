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
 * @author changjin wei(魏昌进)
 * @since 2022/11/26
 */
@Component
public class SecurityContext {

    private final ExpressionParser expressionParser = new SpelExpressionParser();

    public List<String> rbacPermissions(User user, List<Abac> abacs) {
        return this.rbacPermissions(user, abacs, Collections.emptyList());
    }


    public List<String> rbacPermissions(User user, List<Abac> abacs, List<MetadataCustomizer> metadataCustomizers) {
        metadataCustomizers.forEach(metadataCustomizer -> metadataCustomizer.customize(user));
        List<String> expressions = new ArrayList<>();
        for (Abac abac : abacs) {
            Expression expression = expressionParser.parseExpression(abac.getExpression());
            EvaluationContext context = new StandardEvaluationContext(user);
            if (expression.getValue(context, boolean.class)) {
                expressions.addAll(abac.getPermissions());
            }
        }
        return expressions;
    }

}
