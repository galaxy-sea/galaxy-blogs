package plus.wcj.security.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityAnnotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityAnnotationApplication.class, args);
    }

}
