package com.galaxy.alien.client.feign;

import com.galaxy.alien.feign.server.domain.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HttpClientDemo {

    private final AlienServerFeignClient alienServerFeignClient;
    private final AlienServerFeignClientV2 alienServerFeignClientV2;
    private final BaiduFeignClient baiduFeignClient;



    @GetMapping("feignClient")
    public String feignClient() throws Exception {
        String result = alienServerFeignClient.hello("feignClient");
        return "Return : " + result;
    }


    @GetMapping("feignClientV2")
    public User feignClientV2() throws Exception {
        User result = alienServerFeignClientV2.helloFeignServer("feignClientV2");
        return result;
    }


    @GetMapping("baidu")
    public String baidu() throws Exception {
        return baiduFeignClient.hello();
    }


}
