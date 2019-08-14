package com.whoops.cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @GetMapping("/hello")
    public String getMessage(@RequestParam(value = "name") String name){
        return name + ":hello spring cloud";
    }
}
