package com.galaxy.result;

import com.galaxy.result.exception.IResultStatus;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用这个注解就可以返回统一的json格式,  用于类和方法上
 *
 * @author galaxy
 * @date 2019/10/05 16:19
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ResponseBody
public @interface ResponseResultBody {
    /*
     * todo 善于利用 注解可以实现元数据的解析
     *
     */

    String message() default "OK";

}
