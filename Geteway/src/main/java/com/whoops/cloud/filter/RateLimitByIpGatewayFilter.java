package com.whoops.cloud.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.*;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//通过令牌桶算法实现自定义的限流过滤器
@CommonsLog
@Builder
@Data
//@AllArgsConstructor(access = AccessLevel.PUBLIC)
//@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class RateLimitByIpGatewayFilter implements GatewayFilter, Ordered {

//    capacity：桶的最大容量，即能装载 Token 的最大数量
    private int capacity;
//    refillTokens：每次 Token 补充量
    private int refillTokens;
//    refillDuration：补充 Token 的时间间隔
    private Duration refillDuration;

    public RateLimitByIpGatewayFilter(int capacity, int refillTokens, Duration refillDuration) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillDuration = refillDuration;
    }

    public RateLimitByIpGatewayFilter() {
    }

    private static final Map<String, Bucket> CACHE = new ConcurrentHashMap<>();

    public Bucket createNewBucket(){
        Refill refill = Refill.of(refillTokens,refillDuration);
        Bandwidth limit = Bandwidth.classic(capacity,refill);
        return Bucket4j.builder().addLimit(limit).build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        Bucket bucket = CACHE.computeIfAbsent(ip,k -> createNewBucket());
        System.out.println("IP: " + ip + "，TokenBucket Available Tokens: " + bucket.getAvailableTokens());
        if (bucket.tryConsume(1)) {
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -10000;
    }
}
