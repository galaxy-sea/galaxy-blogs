package com.galaxy.contorller;

import com.galaxy.bean.HelloBean;
import com.galaxy.result.ResponseResultBody;
import com.galaxy.result.exception.ResultException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloResult")
@ResponseResultBody
public class HelloResultController {

    @GetMapping("hello")
    public HelloBean hello() {
        HelloBean helloBean = new HelloBean("tu", 1);
        return helloBean;
    }

    @GetMapping("helloError")
    public HelloBean helloError() throws Exception {
        throw new Exception("helloError");
    }

    @GetMapping("helloMyError")
    public HelloBean helloMyError() throws Exception {
        throw new ResultException();
    }
}
