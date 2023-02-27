package com.defers.mypastebin.handler;

import com.defers.mypastebin.dto.ApiResponse;
import com.defers.mypastebin.dto.LoginDTO;
import com.defers.mypastebin.enums.ApiResponseStatus;
import com.defers.mypastebin.exception.InvalidTokenException;
import com.defers.mypastebin.security.JWTService;
import com.defers.mypastebin.util.HttpUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthHandler {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private ReactiveUserDetailsService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Mono<ServerResponse> login(ServerRequest req) {
        Mono<LoginDTO> loginDtoMono = req.bodyToMono(LoginDTO.class);
        return loginDtoMono
                .switchIfEmpty(Mono.error(new InvalidTokenException("Credentials are empty")))
                .flatMap(e -> {
                    Mono<String> token = createToken(e);
                    return token;
                })
                .flatMap(t -> ServerResponse
                        .created(req.uri())
                        .contentType(MediaType.APPLICATION_NDJSON)
                        .body(Mono.just(
                                HttpUtils.createApiResponse(t, ApiResponseStatus.OK)
                        ), ApiResponse.class)
                );
    }

    private Mono<String> createToken(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        return userService.findByUsername(username)
                .flatMap(u -> {
                    boolean isValid = validatePassword(u.getPassword(), loginDTO.getPassword());
                    if (!isValid) {
                        return Mono.error(new InvalidTokenException("Wrong password"));
                    }
                    return Mono.just(jwtService.createToken(username));
                });
    }

    private boolean validatePassword(String currentPassword, String passwordToValidate) {
        return passwordEncoder.matches(passwordToValidate, currentPassword);
    }
}
