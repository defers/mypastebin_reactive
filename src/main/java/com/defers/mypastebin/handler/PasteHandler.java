package com.defers.mypastebin.handler;

import com.defers.mypastebin.dto.PasteDTO;
import com.defers.mypastebin.service.PasteService;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Data
@Builder
public class PasteHandler {

    private final PasteService pasteService;

    public PasteHandler(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    public Mono<ServerResponse> listAll(ServerRequest req) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(pasteService.findAll(), PasteDTO.class);
    }

    public Mono<ServerResponse> save(ServerRequest req) {
        return req.bodyToMono(PasteDTO.class)
                .doOnNext(e -> pasteService.save(e))
                .flatMap(e -> ServerResponse.created(req.uri())
                        .body(e, PasteDTO.class)
                );
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        return req.bodyToMono(PasteDTO.class)
                .doOnNext(e -> pasteService.update(e, req.pathVariable("id")))
                .flatMap(e -> ServerResponse.accepted()
                        .body(e, PasteDTO.class)
                );
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        return req.bodyToMono(PasteDTO.class)
                .doOnNext(e -> pasteService.delete(e, req.pathVariable("id")))
                .flatMap(e -> ServerResponse
                        .noContent()
                        .build()
                );
    }
}
