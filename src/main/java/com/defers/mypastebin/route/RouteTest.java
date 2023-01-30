package com.defers.mypastebin.route;

import com.defers.mypastebin.handler.TestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;

@Component
public class RouteTest {
    @Bean
    public RouterFunction<ServerResponse> routes(TestHandler testHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/test"), req -> testHandler.test(req));
    }

}
