package com.whoops.cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @GetMapping("/hello")
    public String getMessage(@RequestParam(value = "name") String name){
        System.out.println("get in producer2");
//        try{
//            Thread.sleep(10000);
//        }catch (InterruptedException e){
//            return "its in trouble";
//        }
        return name + ":hello spring cloud，2";
    }
}
