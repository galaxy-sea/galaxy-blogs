package plus.wcj.permissiontree;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author changjin wei(魏昌进)
 * @since 2023/6/2
 */
@RestController
@RequestMapping("hello")
@PreAuthorize("hasAuthority('hello')")
public class HelloController {

    @PreAuthorize("hasAuthority('hello:helloWorld')")
    @GetMapping
    public String helloWorld() {
        return "hello";
    }
}
