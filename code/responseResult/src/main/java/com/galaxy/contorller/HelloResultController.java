package com.galaxy.contorller;

import com.galaxy.bean.HelloBean;
import com.galaxy.result.ResponseResultBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloResult")
@ResponseResultBody
public class HelloResultController {
    
    @GetMapping()
    public HelloBean hello() {
        HelloBean helloBean = new HelloBean("tu", 1);
        return helloBean;
    }
}
