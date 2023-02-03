package com.defers.mypastebin.service;

import com.defers.mypastebin.domain.User;
import com.defers.mypastebin.dto.UserDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<UserDTO> findAll();
    Mono<UserDTO> findUserDTOByUsername(String username);
    Mono<User> findUserByUsername(String username);
    Mono<UserDTO> save(UserDTO user);
    Mono<UserDTO> update(UserDTO user);
    Mono<Void> delete(UserDTO user);
}
