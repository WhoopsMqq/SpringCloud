package com.whoops.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
//@EnableSwagger2
public class ConfigClient {
    public static void main(String[] args){
        SpringApplication.run(ConfigClient.class,args);
    }
}
