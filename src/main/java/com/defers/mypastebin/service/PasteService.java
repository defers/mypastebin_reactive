package com.defers.mypastebin.service;

import com.defers.mypastebin.dto.PasteDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PasteService {
    Flux<PasteDTO> findAll();
    Mono<PasteDTO> findById(String id);
    Mono<PasteDTO> save(PasteDTO paste);
    Mono<PasteDTO> update(PasteDTO paste, String id);
    Mono<Void> delete(PasteDTO paste, String id);
}
