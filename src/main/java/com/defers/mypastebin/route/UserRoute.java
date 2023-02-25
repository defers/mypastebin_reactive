package com.defers.mypastebin.route;

import com.defers.mypastebin.handler.UserHandler;
import com.defers.mypastebin.util.MessagesUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class UserRoute {

    private final static String basePattern = "/v1/users";
    private final static String id = "username";

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler userHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET(basePattern),
                        userHandler::listAll)
                .andRoute(RequestPredicates.GET(
                        basePattern + MessagesUtils.getFormattedMessage("/{%s}", id)),
                        req -> userHandler.findUserByUserName(req.pathVariable(id)))
                .andRoute(RequestPredicates.POST(basePattern),
                        userHandler::save)
                .andRoute(RequestPredicates.PUT(
                        basePattern + MessagesUtils.getFormattedMessage("/{%s}", id)),
                        userHandler::update)
                .andRoute(RequestPredicates.DELETE(
                        basePattern + MessagesUtils.getFormattedMessage("/{%s}", id)),
                        req -> userHandler.delete(req.pathVariable(id)));
    }

}
