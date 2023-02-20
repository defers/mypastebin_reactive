package com.defers.mypastebin.handler;

import com.defers.mypastebin.dto.ApiResponse;
import com.defers.mypastebin.dto.UserDTORequest;
import com.defers.mypastebin.enums.ApiResponseStatus;
import com.defers.mypastebin.service.UserDetailsServiceImpl;
import com.defers.mypastebin.util.HttpUtils;
import com.defers.mypastebin.util.MessagesUtils;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Builder
@Slf4j
@Data
@Component
public class UserHandler {
    private UserDetailsServiceImpl userService;

    public UserHandler(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> listAll(ServerRequest req) {
        return userService.findAll().collectList()
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .body(Mono.just(
                                HttpUtils.createApiResponse(e, ApiResponseStatus.OK)
                        ), ApiResponse.class));
    }

    public Mono<ServerResponse> save(ServerRequest req) {
        Mono<UserDTORequest> userDtoMono = req.bodyToMono(UserDTORequest.class);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return userDtoMono
                .flatMap(userDtoRequest -> userService.save(userDtoRequest))
                .flatMap(e -> ServerResponse
                        .created(req.uri())
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .body(Mono.just(
                                HttpUtils.createApiResponse(e, ApiResponseStatus.OK)
                        ), ApiResponse.class)
                )
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        return req
                .bodyToMono(UserDTORequest.class)
                .flatMap(userDTO -> userService.update(userDTO))
                .flatMap(userDTO -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .body(Mono.just(
                                HttpUtils.createApiResponse(userDTO, ApiResponseStatus.OK)
                        ), ApiResponse.class));
    }

    public Mono<ServerResponse> delete(String username) {
        return userService.delete(username)
                .then(ServerResponse
                        .accepted()
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .body(Mono.just(
                                HttpUtils.createApiResponse(
                                        MessagesUtils.getFormattedMessage("User with username %s has deleted", username),
                                        ApiResponseStatus.OK
                                )
                        ), ApiResponse.class));
    }

    public Mono<ServerResponse> findUserByUserName(String username) {
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        return userService.findUserDTOByUsername(username, false)
                .flatMap(userDTOResponse -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .body(Mono.just(
                                HttpUtils.createApiResponse(userDTOResponse, ApiResponseStatus.OK)
                        ), ApiResponse.class))
                .switchIfEmpty(notFound)
                .doOnError(e -> log.info("=====> UserHandler.findUserByUserName error ===== {}", e.getMessage()))
                .doOnSuccess(userDTO -> log.info("=====> UserHandler.findUserByUserName value ===== {}", userDTO));
    }
}
