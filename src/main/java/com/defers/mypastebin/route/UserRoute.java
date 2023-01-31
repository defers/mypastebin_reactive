package com.defers.mypastebin.route;

import com.defers.mypastebin.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class UserRoute {

    private final String basePattern = "/users";

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler userHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET(basePattern),
                        req -> userHandler.listAll(req))
                .andRoute(RequestPredicates.POST(basePattern),
                        req -> userHandler.save(req))
                .andRoute(RequestPredicates.PUT(basePattern + "/{username}"),
                        req -> userHandler.update(req))
                .andRoute(RequestPredicates.DELETE(basePattern + "/{username}"),
                        req -> userHandler.delete(req));
    }

}
