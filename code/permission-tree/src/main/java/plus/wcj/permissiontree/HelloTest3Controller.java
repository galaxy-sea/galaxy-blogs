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
@RequestMapping("hello3")
@PreAuthorize("hasAuthority('测试有子级')")
public class HelloTest3Controller {

    @PreAuthorize("hasAuthority('测试有子级:测试子级合并到其他父级')")
    @GetMapping
    public String helloWorld() {
        return "hello";
    }
}
