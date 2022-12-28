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
package plus.wcj.swagger.support.plugin;

import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.lang.annotation.Annotation;

/**
 * @author changjin wei(魏昌进)
 * @since 2022/12/27
 */
@Order(SwaggerPluginSupport.OAS_PLUGIN_ORDER + 100)
//@Component
public class SecurityAnnotationOperationBuilderPlugin implements OperationBuilderPlugin {

    private static final String PACKAGE_PREFIX = "org.springframework.security.access.prepost.";

    @Override
    public void apply(OperationContext context) {
        String notes = context.operationBuilder().build().getNotes();
        StringBuffer notesBuffer = new StringBuffer(notes == null ? "" : notes);
        getClassAnnotationNote(notesBuffer, context);
        getMethodAnnotationNote(notesBuffer, context);
        context.operationBuilder().notes(notesBuffer.toString());
    }


    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    private void getClassAnnotationNote(StringBuffer notesBuffer, OperationContext context) {
        StringBuffer securityNotes = new StringBuffer();
        context.findControllerAnnotation(PostAuthorize.class)
               .ifPresent(ann -> append(securityNotes, ann));
        context.findControllerAnnotation(PostFilter.class)
               .ifPresent(ann -> append(securityNotes, ann));
        context.findControllerAnnotation(PreAuthorize.class)
               .ifPresent(ann -> append(securityNotes, ann));
        context.findControllerAnnotation(PreFilter.class)
               .ifPresent(ann -> append(securityNotes, ann));
        if (securityNotes.length() > 0) {
            notesBuffer.append("<p />");
            notesBuffer.append("class: ");
            notesBuffer.append(securityNotes);
        }
    }

    private void getMethodAnnotationNote(StringBuffer notesBuffer, OperationContext context) {
        StringBuffer securityNotes = new StringBuffer();
        context.findAnnotation(PostAuthorize.class)
               .ifPresent(ann -> append(securityNotes, ann));
        context.findAnnotation(PostFilter.class)
               .ifPresent(ann -> append(securityNotes, ann));
        context.findAnnotation(PreAuthorize.class)
               .ifPresent(ann -> append(securityNotes, ann));
        context.findAnnotation(PreFilter.class)
               .ifPresent(ann -> append(securityNotes, ann));
        if (securityNotes.length() > 0) {
            notesBuffer.append("<p />");
            notesBuffer.append("method: ");
            notesBuffer.append(securityNotes);
        }
    }

    private void append(StringBuffer securityNotes, Annotation ann) {
        String txt = ann.toString().replace(PACKAGE_PREFIX, "").replace("\\'", "'");
        securityNotes.append(txt)
                     .append(" ");
    }

}