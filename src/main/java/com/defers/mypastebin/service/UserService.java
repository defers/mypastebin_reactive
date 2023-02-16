package com.defers.mypastebin.service;

import com.defers.mypastebin.domain.User;
import com.defers.mypastebin.dto.UserDTORequest;
import com.defers.mypastebin.dto.UserDTOResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<UserDTOResponse> findAll();
    Mono<User> findUserByUsername(String username);
    Mono<UserDTOResponse> findUserDTOByUsername(String username);
    Mono<UserDTOResponse> save(UserDTORequest user);
    Mono<UserDTOResponse> update(UserDTORequest user);
    Mono<Void> delete(String username);
}
