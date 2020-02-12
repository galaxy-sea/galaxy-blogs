package com.galaxy.alien.client.request.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RestTemplateClient {

    @Autowired
    RestTemplate restTemplate;


    @GetMapping("hello")
    public String hello() {
        String result = restTemplate.getForObject("http://alibaba-nacos-discovery-server/hello?name=didi", String.class);
        return "Return : " + result;
    }


}
