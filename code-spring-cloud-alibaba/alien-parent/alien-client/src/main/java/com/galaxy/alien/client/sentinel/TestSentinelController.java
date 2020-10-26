package com.galaxy.alien.client.sentinel;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sentinel")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestSentinelController {

    private final SentinelService sentinelService;


    @GetMapping("test1")
    public String test1() {
        sentinelService.common();
        return "test1";
    }

    @GetMapping("test2")
    public String test2() {
        sentinelService.common();
        return "test2";
    }
}
