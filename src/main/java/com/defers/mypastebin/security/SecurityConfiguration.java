package com.defers.mypastebin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(
                (exchange) -> exchange.anyExchange().permitAll());

        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(WebSecurity web) {
//        return web1 -> web.ignoring().antMatchers("/resources/");
//
//    }

}
