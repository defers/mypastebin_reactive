package com.defers.mypastebin.route;

import com.defers.mypastebin.dto.PasteDTO;
import com.defers.mypastebin.handler.PasteHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class PasteRoute {

    private final String basePattern = "/v1/paste";

    @RouterOperations(
            {
                    @RouterOperation(path = basePattern, produces = {
                            MediaType.TEXT_EVENT_STREAM_VALUE}, method = RequestMethod.POST, beanClass = PasteHandler.class, beanMethod = "save",
                            operation = @Operation(operationId = "save", responses = {
                                    @ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = PasteDTO.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Paste details supplied")}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = PasteDTO.class)))
                            )),
                    @RouterOperation(path = basePattern + "/{id}", produces = {
                            MediaType.TEXT_EVENT_STREAM_VALUE}, method = RequestMethod.PUT, beanClass = PasteHandler.class, beanMethod = "update",
                            operation = @Operation(operationId = "update", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = PasteDTO.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Paste details supplied")}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = PasteDTO.class)))
                            )),
                    @RouterOperation(path = basePattern + "/{id}", produces = {
                            MediaType.TEXT_EVENT_STREAM_VALUE}, method = RequestMethod.DELETE, beanClass = PasteHandler.class, beanMethod = "delete",
                            operation = @Operation(operationId = "delete", responses = {
                                    @ApiResponse(responseCode = "204", description = "successful operation", content = @Content(schema = @Schema(implementation = PasteDTO.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Paste details supplied")}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = PasteDTO.class)))
                            )),
                    @RouterOperation(path = basePattern, produces = {
                            MediaType.TEXT_EVENT_STREAM_VALUE}, method = RequestMethod.GET, beanClass = PasteHandler.class, beanMethod = "listAll",
                            operation = @Operation(operationId = "listAll", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = PasteDTO.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Paste details supplied")}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = PasteDTO.class)))
                            ))

            })
    @Bean
    public RouterFunction<ServerResponse> pasteRoutes(PasteHandler pasteHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET(basePattern),
                        req -> pasteHandler.listAll(req))
                .andRoute(RequestPredicates.GET(basePattern + "/{id}"),
                        req -> pasteHandler.findById(req))
                .andRoute(RequestPredicates.POST(basePattern),
                        req -> pasteHandler.save(req))
                .andRoute(RequestPredicates.PUT(basePattern + "/{id}"),
                        req -> pasteHandler.update(req))
                .andRoute(RequestPredicates.DELETE(basePattern + "/{id}"),
                        req -> pasteHandler.delete(req));
    }
}
