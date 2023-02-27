package com.defers.mypastebin.route;

import com.defers.mypastebin.handler.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class AuthRouter {
    private final static String basePattern = "/login";

    @Bean
    public RouterFunction<ServerResponse> authRoutes(AuthHandler authHandler) {
        return RouterFunctions
                .route(RequestPredicates.POST(basePattern), authHandler::login);
    }
}
