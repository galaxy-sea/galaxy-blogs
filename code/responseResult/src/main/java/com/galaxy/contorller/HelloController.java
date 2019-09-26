package com.galaxy.contorller;

import com.galaxy.bean.HelloBean;
import com.galaxy.result.ResponseResultBody;
import com.galaxy.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping()
    public HelloBean hello() {
        HelloBean helloBean = new HelloBean("tu", 1);
        return helloBean;
    }

    @GetMapping("/result")
    @ResponseBody
    public Result<HelloBean> helloResult() {
        HelloBean helloBean = new HelloBean("result", 1);
        return Result.success(helloBean);
    }

    @GetMapping("/resultBody")
    @ResponseResultBody
    public HelloBean helloResultBody() {
        HelloBean helloBean = new HelloBean("resultBody", 1);
        return helloBean;
    }
}
