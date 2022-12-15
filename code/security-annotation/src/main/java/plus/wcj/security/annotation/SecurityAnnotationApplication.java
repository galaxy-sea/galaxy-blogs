package plus.wcj.security.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author changjin wei(魏昌进)
 * @since 2022/12/15
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityAnnotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityAnnotationApplication.class, args);
    }

}
