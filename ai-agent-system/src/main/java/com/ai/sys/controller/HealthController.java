package com.ai.sys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengYuJun
 */
@RestController
@RequestMapping("health")
public class HealthController {
    @GetMapping("/system")
    public String hello() {
        return "ok";
    }
}
