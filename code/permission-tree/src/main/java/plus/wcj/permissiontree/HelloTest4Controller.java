package plus.wcj.permissiontree;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author changjin wei(魏昌进)
 * @since 2023/6/2
 */
@RestController
@RequestMapping("hello4")
public class HelloTest4Controller {

    @PreAuthorize("hasAuthority('测试无父亲级:测试无父亲级')")
    @GetMapping
    public String helloWorld() {
        return "hello";
    }

    @PreAuthorize("hasAuthority('测试无父亲级:测试无父亲级')")
    @PostMapping
    public String helloWorld2() {
        return "hello";
    }

    @PreAuthorize("hasAuthority('测试无父亲级:测试无父亲级11')")
    @PutMapping
    public String helloWorld3() {
        return "hello";
    }
}
