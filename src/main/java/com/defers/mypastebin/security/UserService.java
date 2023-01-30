package com.defers.mypastebin.security;

import com.defers.mypastebin.dto.UserDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<UserDTO> findAll();
    Mono<UserDTO> save(UserDTO User);
    Mono<UserDTO> update(UserDTO User);
    Mono<Void> delete(UserDTO User);
}
