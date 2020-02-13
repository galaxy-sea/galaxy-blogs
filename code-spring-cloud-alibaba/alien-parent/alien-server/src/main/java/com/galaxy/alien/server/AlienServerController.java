package com.galaxy.alien.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AlienServerController {

    @GetMapping("hello/")
    public String hello(@RequestParam String name){
        log.info("----------");
        return "hello " + name;
    }
}
