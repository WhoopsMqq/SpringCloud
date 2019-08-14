package com.whoops.cloud;

import com.whoops.cloud.zuulfilter.ZuulFilterList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class Zuul extends ZuulFilterList {
    public static void main(String[] args){
        SpringApplication.run(Zuul.class,args);
    }

}
