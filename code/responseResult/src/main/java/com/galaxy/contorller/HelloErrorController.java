package com.galaxy.contorller;

import com.galaxy.bean.HelloBean;
import com.galaxy.result.ResponseResultBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloError")
@ResponseResultBody
public class HelloErrorController {
    
    @GetMapping()
    public HelloBean hello() throws Exception {
        throw new Exception("wode");
    }
}
