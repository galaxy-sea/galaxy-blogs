package com.galaxy.alien.client.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

@Service
public class SentinelService {

    @SentinelResource("common")
    public void common(){

    }
}
