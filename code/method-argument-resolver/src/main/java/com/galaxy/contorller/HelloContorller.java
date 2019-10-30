package com.galaxy.contorller;

import com.galaxy.annotation.RequestHttpBean;
import com.galaxy.bean.HttpBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author galaxy
 */
@RequestMapping("hello")
@RestController
public class HelloContorller {

    @GetMapping
    public String hello() {
        return "hello";
    }

    @GetMapping("helloHttp")
    public HttpBean helloHttp(HttpBean httpBean) {
        return httpBean;
    }

    @GetMapping("helloResolver")
    public HttpBean helloResolver(@RequestHttpBean HttpBean httpBean) {
        return httpBean;
    }


}
