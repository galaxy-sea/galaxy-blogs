package com.galaxy.alien.client.feign;

import com.galaxy.alien.feign.server.AlienServerFeignServer;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * feign 继承
 */
@FeignClient(name = "alien-server")
public interface AlienServerFeignClientV2 extends AlienServerFeignServer {
}
