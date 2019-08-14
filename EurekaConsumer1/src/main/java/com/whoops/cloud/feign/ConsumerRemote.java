package com.whoops.cloud.feign;

import com.whoops.cloud.feign.hystrix.ConsumerRemoteHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "producer",fallback = ConsumerRemoteHystrix.class)
public interface ConsumerRemote {
    @RequestMapping(value = "/hello")
    String hello(@RequestParam(value = "name") String name);
}
