package plus.wcj.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author changjin wei(魏昌进)
 * @since 2022/12/15
 */
@RestController
@RequestMapping("hello")
public class HelloController {

    @GetMapping("/security")
    public String security(){
        return "Hello, Security";
    }

    @GetMapping("/hardcode")
    public String hardcode(){
        return "Hello, hardcode";
    }

    /** 注解方式 */
    @GetMapping("/annotation")
    @IgnoreWebSecurity
    public String annotation(){
        return "Hello, annotation";
    }

    /** 测试PathVariable参数 */
    @GetMapping("/properties")
    @IgnoreWebSecurity
    public String properties(){
        return "Hello, properties";
    }

    /** 测试PathVariable参数 */
    @GetMapping("/{path}/annotation")
    @IgnoreWebSecurity
    public String annotationPath(@PathVariable String path){
        return "Hello, annotation. path: " + path;
    }

    /** 测试PathVariable参数 */
    @GetMapping("/{path}/properties")
    @IgnoreWebSecurity
    public String propertiesPath(@PathVariable String path){
        return "Hello, properties. path: " + path;
    }

}
