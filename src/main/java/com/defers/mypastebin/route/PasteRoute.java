package com.defers.mypastebin.route;

import com.defers.mypastebin.handler.PasteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class PasteRoute {

    private final String basePattern = "/paste";

    @Bean
    public RouterFunction<ServerResponse> pasteRoutes(PasteHandler pasteHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET(basePattern),
                        req -> pasteHandler.listAll(req))
                .andRoute(RequestPredicates.POST(basePattern),
                        req -> pasteHandler.save(req))
                .andRoute((RequestPredicates.PUT(basePattern + "/{id}")),
                        req -> pasteHandler.update(req))
                .andRoute(RequestPredicates.DELETE(basePattern + "/{id}"),
                        req -> pasteHandler.delete(req));
    }
}
