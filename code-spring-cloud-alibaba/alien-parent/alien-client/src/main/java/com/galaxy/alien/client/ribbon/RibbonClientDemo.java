package com.galaxy.alien.client.ribbon;

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
public class RibbonClientDemo {


    private final RestTemplate restTemplate;
    private final WebClient.Builder webClient;

    private final DiscoveryClient discoveryClient;


    @GetMapping("restTemplate")
    public String restTemplate() {
        String result = restTemplate.getForObject("http://alien-server/hello?name=restTemplate", String.class);
        return "Return : " + result;
    }

    @GetMapping("webClient")
    public String webClient() {
        Mono<String> result = webClient.build()
                .get()
                .uri("http://alien-server/hello?name=WebClient")
                .retrieve()
                .bodyToMono(String.class);
        return "Return : " + result.block();
    }


    @GetMapping("discovery")
    public String discovery() throws Exception {
        // 获取服务
        List<String> services = discoveryClient.getServices();

        // 获取实例
        List<ServiceInstance> instances = discoveryClient.getInstances("alien-server");
        String url = instances.stream()
                .findFirst()
                .map(serviceInstance -> "http://" + serviceInstance.getServiceId() + "/hello?name=discovery")
                .orElseThrow(() -> new Exception("没有instances实例"));

        String result = restTemplate.getForObject(url, String.class);

        return "Return : " + result;
    }


}
