package com.galaxy.contorller;

import com.galaxy.bean.HttpBean;
import com.galaxy.config.mvc.resolvers.annotation.RequestHttpBody;
import com.galaxy.config.mvc.resolvers.annotation.RequestHttpInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
    public HttpBean helloResolver(@RequestHttpInfo HttpBean httpBean) {
        return httpBean;
    }

    @PostMapping("helloResolver")
    public String helloResolver(@RequestHttpBody String body) {
        return body;
    }


    /**
     * I/O error while reading input message; nested exception is java.io.IOException: Stream closed 问题
     * ServletInputStream 只能读取一次, 读取两次就会报异常
     *
     * @param body
     * @param requestBody
     * @return
     */
    @PostMapping("io")
    public String helloResolver(@RequestHttpBody String body, @RequestBody String requestBody) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("body", body);
        map.put("requestBody", requestBody);
        return body;
    }


}
