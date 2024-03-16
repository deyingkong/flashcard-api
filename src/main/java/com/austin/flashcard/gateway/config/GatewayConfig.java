package com.austin.flashcard.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: Austin
 * @Create: 2/27/2024 11:53 PM
 */
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("service1", r -> r.path("/customer/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://customer"))
                .route("service2", r -> r.path("/test/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://test"))
                .route("service3", r -> r.path("/flashcard/**")
                        .filters(f -> f.stripPrefix(1)
                                .tokenRelay())
                        .uri("lb://flashcard"))
                .build();
    }

}
