package com.defers.mypastebin.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

// TODO DELETE CLASS
@Component
public class TestHandler {

    public Mono<ServerResponse> test(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(Flux.just("Hello!!!", "YO!", " YO MAN!", " Bro!")
                        .delayElements(Duration.ofSeconds(1)), String.class);

    }
}
