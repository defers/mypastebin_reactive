package com.defers.mypastebin.route;

import com.defers.mypastebin.dto.ApiResponse;
import com.defers.mypastebin.dto.PasteDTO;
import com.defers.mypastebin.enums.ApiResponseStatus;
import com.defers.mypastebin.handler.PasteHandler;
import com.defers.mypastebin.service.PasteService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = {PasteRoute.class, PasteHandler.class})
@WebFluxTest
class PasteRouteTest {

    @Autowired
    private PasteRoute pasteRoute;
    @Autowired
    @InjectMocks
    private PasteHandler pasteHandler;
    @MockBean
    private PasteService pasteService;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToRouterFunction(pasteRoute.pasteRoutes(pasteHandler))
                .build();
    }

    @Test
    void shouldReturnPasteDTOByIdWhenGetRequest() {
        PasteDTO paste = PasteDTO.builder()
                .id("test1")
                .textDescription("test descr")
                .build();

        Mockito.when(pasteService.findById("test1"))
                .thenReturn(Mono.just(paste));

        webTestClient.get()
                .uri("/v1/paste/test1")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ApiResponse.class)
                .value(p -> {
                    Assertions.assertThat(p.getStatus()).isEqualTo(ApiResponseStatus.OK);
                });
    }

    @Test
    void shouldReturnPasteDTOWhenPostRequestSaved() {
        PasteDTO paste = PasteDTO.builder()
                .textDescription("test descr")
                .build();

        PasteDTO pasteResp = PasteDTO.builder()
                .id("testId")
                .textDescription("test descr")
                .build();
        Mockito.when(pasteService.save(paste))
                .thenReturn(Mono.just(pasteResp));

        webTestClient.post()
                .uri("/v1/paste")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(paste), PasteDTO.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(ApiResponse.class)
                .value(p -> {
                    Assertions.assertThat(p.getStatus()).isEqualTo(ApiResponseStatus.OK);
                });
    }
}