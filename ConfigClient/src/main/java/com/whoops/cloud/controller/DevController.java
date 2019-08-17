package com.whoops.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope // 使用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
public class DevController {
    @Value("${dev.server.port}")
    private String port;

    @Value("${dev.eureka.client.service-url.defaultZone}")
    private String defaultUrl;

    @GetMapping("/getPort")
    public String getPort(){
        return port;
    }

    @GetMapping("/getUrl")
    public String getUrl(){
        return defaultUrl;
    }

    @GetMapping("/hello")
    public String hello(){
        return "just a test ,say hello";
    }

}
