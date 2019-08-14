package com.whoops.cloud.controller;

import com.whoops.cloud.feign.ConsumerRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {
    @Autowired
    private ConsumerRemote consumerRemote;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name")String name){
        return consumerRemote.hello(name);
    }

}
