package com.galaxy.alien.feign.server;

import com.galaxy.alien.feign.server.domain.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public interface AlienServerFeignServer {

    @GetMapping("/hello/FeignServer")
    User helloFeignServer(@RequestParam String name);

}
