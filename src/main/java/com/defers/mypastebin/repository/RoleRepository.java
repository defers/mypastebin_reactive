package com.defers.mypastebin.repository;

import com.defers.mypastebin.domain.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleRepository {
    Flux<Role> findAll();
    Mono<Role> findById(long id, boolean blockForUpdate);
    Mono<Role> save(Role role);
    Mono<Role> update(Role role);
    Mono<Void> delete(String username);
}
