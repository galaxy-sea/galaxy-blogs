# knife4j(Swagger) 的二次封装
我是一个不爱说话的宅男, 经常因为接口问题需要被迫和前端交流, 虽然 knife4j(Swagger) 能够生成API文档, 但是经常会出现一些沟通上面的问题如
- XXX api 接口的权限是什么
- 这个接口支持那些排序
- XXX对象的参数太多了,能不能不要全部给我啊, 特别是你的分页对象
- ......

> “Talk is cheap. Show me the code.” ― Linus Torvalds
> 特别喜欢这句话, 所以就放在这里了😬. 下方都是代码copy就能用
> 
> 详细代码详见GitHub [https://github.com/galaxy-sea/galaxy-blogs/tree/master/code/swagger-support](https://github.com/galaxy-sea/galaxy-blogs/tree/master/code/swagger-support)

Swagger 提供``springfox.documentation.spi.service.OperationBuilderPlugin``让我们对``springfox.documentation.spi.service.contexts.OperationContext``进行进行一些自定义对封装.

开发环境配置
- jdk 17
- knife4j 3.0.3
- Spring Boot 2.7.7
  - Spring Security
- mybatis plus 3.5.2


# Spring Security 注解展示
将Spring Security的`PostAuthorize`、`PostFilter`、`PreAuthorize`、`PreFilter`的注解信息追加到接口描述中.

```java

/**
 * @author changjin wei(魏昌进)
 * @since 2022/12/27
 */
@Order(SwaggerPluginSupport.OAS_PLUGIN_ORDER + 100)
@Component
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
```

# MyBatis Plus

## orderBy 参数展示
展示mybatis plus page对象支持的排序的字段

```java
/**
 * @author changjin wei(魏昌进)
 * @since 2022/8/12
 */
@Component
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
```


## 忽略不必要地请求参数
这个功能是仅支持knife4j不支持Swagger, 
经常因为看着page对象一大串不需要的字段而烦恼着, 这里我们就将不需要的字段给忽略掉, 仅保留``current``, ``size``, ``orders[].asc``, ``orders[0].column``这是个字段.

```java

/**
 * @author changjin wei(魏昌进)
 * @since 2022/8/12
 */
@Component
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
```

# 展示结果对比
## 测试代码
```java
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

@TableName
@Data
public class User {
    private String id;
    private String name;
    private String age;
    private String sex;
    private String company;
    private String job;
}

```
## 结果对比
1. Spring Security
![security.png](images%2Fsecurity.png)
2. mybatis plus 排序字段
![orderBy.png](images%2ForderBy.png)
3. mybatis plus 忽略不必要的字段
![PageIgnore.png](images%2FPageIgnore.png)

# 后记
其实这些功能很久以前就写完了, 一直放在我的[heifer](https://github.com/galaxy-sea/heifer)中, 但是heifer目前就几家公司在用. 所以渐渐的将heifer框架中的一些功能剥离出来单独发博客.

顺带提一下在我写这篇博客的博客的时候Spring Security的功能已经提交给了 [knife4j](https://github.com/xiaoymin/swagger-bootstrap-ui) 并合并进去了, 详见 [PR 520](https://github.com/xiaoymin/swagger-bootstrap-ui/pull/520) 
