package com.galaxy.validator.controller;

import com.galaxy.validator.bean.FOO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author galaxy
 * @date 2019/10/07 11:06
 */
@RestController
@RequestMapping("hello")
public class HelloController {

    @PostMapping
    public FOO hello(@Valid @RequestBody FOO foo) {
        return foo;
    }
}
