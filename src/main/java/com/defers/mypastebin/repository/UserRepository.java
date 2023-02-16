package com.defers.mypastebin.repository;

import com.defers.mypastebin.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Flux<User> findAll();
    Mono<User> findUserByUsername(String username);
    Mono<User> save(User user);
    Mono<User> update(User user);
    Mono<Void> delete(String username);
}
