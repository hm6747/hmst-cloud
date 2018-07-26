package com.syscloud.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created by hm on 2018/7/20 0020.
 */
@Configuration
public class GlobalRouteFilter implements GlobalFilter {
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
/*
        builder.header("GlobalFilter","GlobalFilter success");
*/
/*
        chain.filter(exchange.mutate().request(builder.build()).build());
*/
        return chain.filter(exchange.mutate().request(builder.build()).build());
    }
}
