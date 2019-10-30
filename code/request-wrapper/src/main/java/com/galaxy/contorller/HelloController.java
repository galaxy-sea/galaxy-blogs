package com.galaxy.contorller;


import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author galaxy
 * @date 2019/10/05 16:19
 */

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String hello() {
        return "hello";
    }

    @PostMapping
    public Map<String, Object> hello(@RequestBody String requestBody, RequestEntity<String> requestEntity) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestBody", requestBody);
        map.put("requestEntity", requestEntity.getBody());
        return map;
    }


}
