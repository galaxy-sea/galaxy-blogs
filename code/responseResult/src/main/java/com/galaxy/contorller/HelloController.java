package com.galaxy.contorller;

import com.galaxy.result.ResponseResultBody;
import com.galaxy.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author galaxy
 * @date 2019/10/05 16:19
 */

@RestController
@RequestMapping("/hello")
public class HelloController {

    private static final HashMap<String, Object> INFO;

    static {
        INFO = new HashMap<>();
        INFO.put("name", "galaxy");
        INFO.put("age", "70");
    }

    @GetMapping("/hello")
    public Map<String, Object> hello() {
        return INFO;
    }

    @GetMapping("/result")
    @ResponseBody
    public Result<Map<String, Object>> helloResult() {
        return Result.success(INFO);
    }

    @GetMapping("/resultBody")
    @ResponseResultBody
    public Map<String, Object> helloResultBody() {
        return INFO;
    }
}
