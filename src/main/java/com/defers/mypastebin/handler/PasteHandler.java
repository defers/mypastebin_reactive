package com.defers.mypastebin.handler;

import com.defers.mypastebin.dto.ApiResponse;
import com.defers.mypastebin.dto.PasteDTO;
import com.defers.mypastebin.enums.ApiResponseStatus;
import com.defers.mypastebin.service.PasteService;
import com.defers.mypastebin.util.HttpUtils;
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
        return pasteService.findAll().collectList()
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .body(Mono.just(
                                HttpUtils.createApiResponse(e, ApiResponseStatus.OK)
                        ), ApiResponse.class));
    }

    public Mono<ServerResponse> save(ServerRequest req) {
        Mono<PasteDTO> pasteDtoMono = req.bodyToMono(PasteDTO.class);
        return pasteDtoMono
                .flatMap(pasteService::save)
                .flatMap(e -> ServerResponse
                        .created(req.uri())
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .body(Mono.just(
                                HttpUtils.createApiResponse(e, ApiResponseStatus.OK)
                        ), ApiResponse.class)
                );
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        return req.bodyToMono(PasteDTO.class)
                .flatMap(e -> pasteService.update(e, req.pathVariable("id")))
                .flatMap(e -> ServerResponse.accepted()
                        .body(Mono.just(HttpUtils.createApiResponse(e, ApiResponseStatus.OK)
                        ), ApiResponse.class)
                );
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        return pasteService.delete(req.pathVariable("id"))
                .then(ServerResponse
                        .noContent()
                        .build()
                );
    }

    public Mono<ServerResponse> findById(ServerRequest req) {
        return pasteService.findById(req.pathVariable("id"))
                .flatMap(pasteDTO -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .body(Mono.just(
                                HttpUtils.createApiResponse(pasteDTO, ApiResponseStatus.OK)
                        ), ApiResponse.class));
    }


}
