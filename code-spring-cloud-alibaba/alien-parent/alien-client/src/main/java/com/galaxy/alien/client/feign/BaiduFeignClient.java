package com.galaxy.alien.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * 非微服务的调用
 */
@FeignClient(name = "baidu", url = "http://ww.baidu.com")
public interface BaiduFeignClient {

    @GetMapping("")
    String hello();
}
