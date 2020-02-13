package com.galaxy.alien.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "alien-server")
public interface AlienServerFeignClient {

    @GetMapping("/hello")
    String hello(@RequestParam String name);
}
