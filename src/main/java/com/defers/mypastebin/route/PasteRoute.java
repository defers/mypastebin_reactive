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

    private final String basePattern = "/paste";

    //TODO annotations
    @RouterOperations(
            {
//                    @RouterOperation(path = "/swagger-demo/employee/{employeeId}"
//                            , produces = {
//                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PUT, beanClass = EmployeeService.class, beanMethod = "updateEmployee",
//                            operation = @Operation(operationId = "updateEmployee", responses = {
//                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Employee.class))),
//                                    @ApiResponse(responseCode = "400", description = "Invalid Employee ID supplied"),
//                                    @ApiResponse(responseCode = "404", description = "Employee not found")}, parameters = {
//                                    @Parameter(in = ParameterIn.PATH, name = "employeeId")}
//                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Employee.class))))
//                    ),
                    @RouterOperation(path = basePattern, produces = {
                            MediaType.TEXT_EVENT_STREAM_VALUE}, method = RequestMethod.POST, beanClass = PasteHandler.class, beanMethod = "save",
                            operation = @Operation(operationId = "save", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = PasteDTO.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Paste details supplied")}
                                    , requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = PasteDTO.class)))
                            ))
//                    @RouterOperation(path = "/swagger-demo/employee/{employeeId}", produces = {
//                            MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE, beanClass = PasteServiceImpl.class, beanMethod = "deleteEmployee"
//                            , operation = @Operation(operationId = "deleteEmployee", responses = {
//                            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "Boolean")),
//                            @ApiResponse(responseCode = "400", description = "Invalid Employee ID supplied"),
//                            @ApiResponse(responseCode = "404", description = "Employee not found")}, parameters = {
//                            @Parameter(in = ParameterIn.PATH, name = "employeeId")}
//                    )),
//                    @RouterOperation(path = "/swagger-demo/employee/{employeeId}", produces = {
//                            MediaType.APPLICATION_JSON_VALUE},
//                            beanClass = EmployeeService.class, method = RequestMethod.GET, beanMethod = "getEmployee",
//                            operation = @Operation(operationId = "getEmployee", responses = {
//                                    @ApiResponse(responseCode = "200", description = "successful operation",
//                                            content = @Content(schema = @Schema(implementation = Employee.class))),
//                                    @ApiResponse(responseCode = "400", description = "Invalid Employee details supplied")},
//                                    parameters = {@Parameter(in = ParameterIn.PATH, name = "employeeId")}
//                            ))

            })
    @Bean
    public RouterFunction<ServerResponse> pasteRoutes(PasteHandler pasteHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET(basePattern),
                        req -> pasteHandler.listAll(req))
                .andRoute(RequestPredicates.POST(basePattern),
                        req -> pasteHandler.save(req))
                .andRoute((RequestPredicates.PUT(basePattern + "/{id}")),
                        req -> pasteHandler.update(req))
                .andRoute(RequestPredicates.DELETE(basePattern + "/{id}"),
                        req -> pasteHandler.delete(req));
    }
}
