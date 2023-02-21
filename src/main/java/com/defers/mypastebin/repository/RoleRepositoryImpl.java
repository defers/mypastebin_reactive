package com.defers.mypastebin.repository;

import com.defers.mypastebin.domain.Role;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RoleRepositoryImpl implements RoleRepository {
    @Override
    public Flux<Role> findAll() {
        return null;
    }

    @Override
    public Mono<Role> findById(long id, boolean blockForUpdate) {
        return null;
    }

    @Override
    public Mono<Role> save(Role role) {
        return null;
    }

    @Override
    public Mono<Role> update(Role role) {
        return null;
    }

    @Override
    public Mono<Void> delete(String username) {
        return null;
    }
}
