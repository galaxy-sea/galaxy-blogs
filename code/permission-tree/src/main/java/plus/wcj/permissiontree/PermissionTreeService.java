package plus.wcj.permissiontree;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author changjin wei(魏昌进)
 * @since 2023/6/2
 */
@Component
public class PermissionTreeService {

    @Autowired
    private ObjectProvider<RequestMappingHandlerMapping> requestMappingHandlerMappingObjectProvider;

    public Map<String, PermissionTree> permissionTrees = new HashMap<>();

    @PostConstruct
    public void init() {
        RequestMappingHandlerMapping requestMappingHandlerMapping = this.requestMappingHandlerMappingObjectProvider.getIfAvailable();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        findParent(permissionTrees, handlerMethods);
        findChildren(permissionTrees, handlerMethods);
        System.out.println(JSONObject.toJSONString(permissionTrees.values()));
    }

    private void findChildren(Map<String, PermissionTree> permissionTrees, Map<RequestMappingInfo, HandlerMethod> handlerMethods) {
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            Optional<PreAuthorize> methodPreAuthorizeOptional = Optional.ofNullable(handlerMethod.getMethodAnnotation(PreAuthorize.class));
            methodPreAuthorizeOptional.ifPresent(methodPreAuthorize -> {
                String value = match(methodPreAuthorize.value());
                Optional<PreAuthorize> postAuthorizeOptional = Optional.ofNullable(AnnotatedElementUtils.getMergedAnnotation(handlerMethod.getBeanType(), PreAuthorize.class));
                postAuthorizeOptional.ifPresentOrElse(preAuthorize -> {
                    PermissionTree permissionTree = permissionTrees.get(match(preAuthorize.value()));
                    if (permissionTree != null) {
                        permissionTree.getChildren().add(new PermissionTree(value));
                    } else {
                        permissionTrees.put(value, new PermissionTree(value));
                    }
                }, () -> permissionTrees.put(value, new PermissionTree(value)));
            });
        }
    }

    private void findParent(Map<String, PermissionTree> permissionTrees, Map<RequestMappingInfo, HandlerMethod> handlerMethods) {
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            Optional<PreAuthorize> preAuthorizeOptional = Optional.ofNullable(AnnotatedElementUtils.getMergedAnnotation(handlerMethod.getBeanType(), PreAuthorize.class));
            preAuthorizeOptional.ifPresent(preAuthorize -> {
                String value = match(preAuthorize.value());
                permissionTrees.putIfAbsent(value, new PermissionTree(value));
            });
        }
    }

    String regex = "'([^']*)'";
    Pattern pattern = Pattern.compile(regex);

    private String match(String value) {
        Matcher matcher = pattern.matcher(value);
        while (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
