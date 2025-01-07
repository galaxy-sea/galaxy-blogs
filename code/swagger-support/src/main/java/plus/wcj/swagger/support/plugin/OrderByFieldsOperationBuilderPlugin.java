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

package plus.wcj.swagger.support.plugin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.fasterxml.classmate.ResolvedType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author changjin wei(魏昌进)
 * @since 2022/8/12
 */
//@Component
@Order(SwaggerPluginSupport.OAS_PLUGIN_ORDER + 101)
public class OrderByFieldsOperationBuilderPlugin implements OperationBuilderPlugin {
    @Override
    public void apply(OperationContext context) {
        List<ResolvedMethodParameter> parameters = context.getParameters();

        for (ResolvedMethodParameter resolvedMethodParameter : parameters) {
            Class<?> erasedType = resolvedMethodParameter.getParameterType().getErasedType();
            if (IPage.class.isAssignableFrom(erasedType)) {
                List<String> orderItems = this.toOrderItems(resolvedMethodParameter);
                if (!CollectionUtils.isEmpty(orderItems)) {
                    this.joinNotes(orderItems, context);
                }
                return;
            }
        }
    }

        @SuppressWarnings("NullableProblems")
    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    private void joinNotes(List<String> orderItems, OperationContext context) {
        StringBuilder order = new StringBuilder();
        order.append("OrderBy: ");
        for (String orderItem : orderItems) {
            order.append(orderItem).append(" ");
        }
        String notes = context.operationBuilder().build().getNotes();

        notes = StringUtils.hasText(notes) ? notes + "<p />" + order : order.toString();
        context.operationBuilder().notes(notes);
    }


    private List<String> toOrderItems(ResolvedMethodParameter resolvedMethodParameter) {
        ResolvedType boundType = resolvedMethodParameter.getParameterType().getTypeBindings().getBoundType(0);
        if (boundType == null) {
            return List.of();
        }
        Class<?> orderClass = boundType.getErasedType();
        return TableInfoHelper.getAllFields(orderClass)
                              .stream()
                              .map(Field::getName)
                              .toList();
    }
}
