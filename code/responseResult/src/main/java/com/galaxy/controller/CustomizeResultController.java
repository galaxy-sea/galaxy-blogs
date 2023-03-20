package com.galaxy.controller;

import com.galaxy.result.ResponseResultBody;
import com.galaxy.result.Result;
import com.galaxy.result.exception.ResultException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author galaxy
 * @date 2019/10/05 16:19
 */

@RestController
@RequestMapping("/customize")
@ResponseResultBody(message = "customize")
public class CustomizeResultController {

    private static final HashMap<String, Object> INFO;

    static {
        INFO = new HashMap<String, Object>();
        INFO.put("name", "galaxy");
        INFO.put("age", "70");
    }

    @GetMapping("hello")
    public HashMap<String, Object> hello() {
        return INFO;
    }

}
