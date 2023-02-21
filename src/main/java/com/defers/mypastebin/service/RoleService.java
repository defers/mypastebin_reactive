package com.defers.mypastebin.service;

import com.defers.mypastebin.dto.RoleDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleService {
    Flux<RoleDTO> findAll();
    Mono<RoleDTO> findById(long id, boolean blockForUpdate);
    Mono<RoleDTO> save(RoleDTO role);
    Mono<RoleDTO> update(RoleDTO role);
    Mono<Void> delete(String username);
}
