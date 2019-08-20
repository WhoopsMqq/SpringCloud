package com.whoops.cloud.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ElapsedFilter implements GatewayFilter, Ordered {

    private static final String ELAPSED_TIME_BEGIN = "elapsedTimeBegin";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(ELAPSED_TIME_BEGIN,System.currentTimeMillis());
        //chain.filter(exchange)之前的就是 “pre” 部分，之后的也就是then里边的是 “post” 部分.
        return chain.filter(exchange).then(
                Mono.fromRunnable(() ->{
                    Long startTime = (Long)exchange.getAttributes().get(ELAPSED_TIME_BEGIN);
                    if(startTime != null){
                        System.out.println(exchange.getRequest().getURI().getRawPath()+":"+ (System.currentTimeMillis() - startTime)+"ms");
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE+1;
    }
}
