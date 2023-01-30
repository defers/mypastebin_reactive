package com.defers.mypastebin.handler;

import com.defers.mypastebin.dto.UserDTO;
import com.defers.mypastebin.security.UserDetailsServiceImpl;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Builder
@Data
@Component
public class UserHandler {
    private UserDetailsServiceImpl userService;

    public UserHandler(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> listAll(ServerRequest req) {
        return ServerResponse.ok().body(userService.findAll(), UserDTO.class);
    }

    public Mono<ServerResponse> save(ServerRequest req) {
        return req
                .bodyToMono(UserDTO.class)
                .doOnNext(userDTO -> userService.save(userDTO))
                .flatMap(userDTO -> ServerResponse.created(
                        URI.create(req.uri().getQuery()))
                        .body(userDTO, UserDTO.class));
    }
    public Mono<ServerResponse> update(ServerRequest req) {
        return req
                .bodyToMono(UserDTO.class)
                .doOnNext(userDTO -> userService.update(userDTO))
                .flatMap(userDTO -> ServerResponse.ok().body(userDTO, UserDTO.class));
    }
    public Mono<ServerResponse> delete(ServerRequest req) {
        return req
                .bodyToMono(UserDTO.class)
                .doOnNext(userDTO -> userService.delete(userDTO))
                .then(ServerResponse
                        .accepted()
                        .build());
    }
}
