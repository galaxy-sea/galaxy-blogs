package com.galaxy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author galaxy
 * @date 2019/10/05 16:19
 */

@RestController
@RequestMapping("/error")
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Java的异常")
public class HelloExceptionController {

    private static final HashMap<String, Object> INFO;

    static {
        INFO = new HashMap<String, Object>();
        INFO.put("name", "galaxy");
        INFO.put("age", "70");
    }

    @GetMapping()
    public HashMap<String, Object> helloError() throws Exception {
        throw new Exception("helloError");
    }

    @GetMapping("helloJavaError")
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Java的异常")
    public HashMap<String, Object> helloJavaError() throws Exception {
        throw new Exception("helloError");
    }

    @GetMapping("helloMyError")
    public HashMap<String, Object> helloMyError() throws Exception {
        throw new MyException();
    }
}

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "自己定义的异常")
class MyException extends Exception {

}
