package com.galaxy.alien.server;

import com.galaxy.alien.feign.server.AlienServerFeignServer;
import com.galaxy.alien.feign.server.domain.dto.User;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlienServerFeignController implements AlienServerFeignServer {

    @Override
    public User helloFeignServer(String name) {
        User user = new User();
        user.setName(name);
        return user;
    }
}
