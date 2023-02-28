package com.defers.mypastebin.route;

import com.defers.mypastebin.dto.PasteDTO;
import com.defers.mypastebin.handler.PasteHandler;
import com.defers.mypastebin.service.PasteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
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
    @Autowired
    ApplicationContext applicationContext;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToRouterFunction(pasteRoute.pasteRoutes(pasteHandler))
                .build();
    }

    @Test
    void pasteRoutes() {
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
                .expectBody(PasteDTO.class);
    }
}