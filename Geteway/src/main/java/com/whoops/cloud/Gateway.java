package com.whoops.cloud;

import com.whoops.cloud.filter.ElapsedFilter;
import com.whoops.cloud.filter.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Gateway {

    public static void main(String[] args){
        SpringApplication.run(Gateway.class,args);
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/hello")
                        .uri("lb://CONSUMER")
                        .order(1)
                        .filter(new ElapsedFilter())
                ).build();
    }

    //全局过滤器配置
    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }
}
