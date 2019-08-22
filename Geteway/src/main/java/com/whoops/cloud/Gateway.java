package com.whoops.cloud;

import com.whoops.cloud.filter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@SpringBootApplication
public class Gateway {

    //基于cpu使用率来进行限流
    @Autowired
    private RateLimitByCpuGatewayFilter rateLimitByCpuGatewayFilter;

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
                        .filter(new RateLimitByIpGatewayFilter(10,1,Duration.ofSeconds(1)))
                        .filter(rateLimitByCpuGatewayFilter)
                ).build();
    }

    //全局过滤器配置
    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }
    //自定义过滤器工厂
    @Bean
    public ElapsedGatewayFilterFactory elapsedGatewayFilterFactory(){
        return new ElapsedGatewayFilterFactory();
    }

    //系统提供的限流过滤器工厂
    @Bean(name = RemoteAddrKeyResolver.BEAN_NAME)
    public RemoteAddrKeyResolver remoteAddrKeyResolver(){
        return new RemoteAddrKeyResolver();
    }
}
