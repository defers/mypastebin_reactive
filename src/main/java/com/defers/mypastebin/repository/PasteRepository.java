package com.defers.mypastebin.repository;

import com.defers.mypastebin.domain.Paste;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PasteRepository {
    Flux<Paste> findAll();
    Mono<Paste> findById(String id, boolean blockForUpdate);
    Mono<Paste> save(Paste paste);
    Mono<Paste> update(Paste paste);
    Mono<Void> delete(String id);
}
