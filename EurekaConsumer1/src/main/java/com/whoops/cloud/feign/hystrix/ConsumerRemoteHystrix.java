package com.whoops.cloud.feign.hystrix;


import com.whoops.cloud.feign.ConsumerRemote;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class ConsumerRemoteHystrix implements ConsumerRemote {

    @Override
    public String hello(@RequestParam(value = "name")String name) {
        return name + " ,sorry web is close";
    }
}
