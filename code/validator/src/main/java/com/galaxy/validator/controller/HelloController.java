package com.galaxy.validator.controller;

import com.galaxy.validator.bean.FOO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author galaxy
 * @date 2019/10/07 11:06
 */
@RestController
@RequestMapping("hello")
public class HelloController {

    private final Validator validator;

    public HelloController(Validator validator) {
        this.validator = validator;
    }


    /** Valid JSR303 Bean Validation规范的支持,支持级联校验 */
    @PostMapping("valid")
    public FOO helloValid(@Valid @RequestBody FOO foo) {
        return foo;
    }

    /** Validated Spring封装 Bean Validation 支持校验分组 */
    @PostMapping("validated")
    @ResponseBody
    public FOO helloValidated(@Validated @RequestBody FOO foo) {
        return foo;
    }

    /**
     * validator 可以手动校验, 分别有两个接口类
     * javax.validation.Validator和org.springframework.validation.Validator
     * 这里的实现类是org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
     */
    @PostMapping("validator")
    public FOO helloValidator(@RequestBody FOO foo) {
        Set<ConstraintViolation<FOO>> constraintViolationSet = validator.validate(foo);
        for (ConstraintViolation<FOO> fooConstraintViolation : constraintViolationSet) {
            fooConstraintViolation.getMessage();
            // todo galaxy 针对单个参数的异常抛出
        }
        return foo;
    }

    /** BindingResult用于获取获取Validated和Valid的校验信息, 需要与Validated和Valid一一对应 */
    @PostMapping("validatedBindingResult")
    public FOO helloValidatedBindingResult(@Validated @RequestBody FOO foo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                // todo galaxy 针对单个参数的异常抛出
            }
        }
        return foo;
    }

    /** BindingResult用于获取获取Validated和Valid的校验信息, 需要与Validated和Valid一一对应 */
    @PostMapping("validBindingResult")
    public FOO helloValidBindingResult(@Valid @RequestBody FOO foo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                // todo galaxy 针对单个参数的异常抛出
            }
        }
        return foo;
    }

    /** Validated提供校验分组,根据不同的业务进行分组 */
    @PostMapping("groups")
    public FOO helloGroups(@Validated(value = FOO.NameNotBlank.class) @RequestBody FOO foo) {
        return foo;
    }
}
