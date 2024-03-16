package com.austin.flashcard.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @Description:
 * @Author: Austin
 * @Create: 3/14/2024 3:24 PM
 */
@Slf4j
public class PrintRequestFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var session = exchange.getSession();
        var request = exchange.getRequest();
        log.info("request:{}, method:{}", request.getPath(), request.getMethod());
        return chain.filter(exchange);
    }
}
