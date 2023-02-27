package com.defers.mypastebin.security;

import com.defers.mypastebin.exception.InvalidTokenException;
import com.defers.mypastebin.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Data
@AllArgsConstructor
public class AuthManager implements ReactiveAuthenticationManager {

    private final JWTService jwtService;
    private final ReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                //.map(auth -> auth.getCredentials())
                //.cast(UsernamePasswordAuthenticationToken.class)
                .flatMap(authCredentials -> {
                    String username = jwtService.getUsername((String) authCredentials.getCredentials());
                    Mono<UserDetails> user = userDetailsService.findByUsername(username);
                    Mono<Authentication> authToken = getUsernamePasswordAuthToken(user, (String) authCredentials.getCredentials());
                    return authToken;
                });
    }

    private Mono<Authentication> getUsernamePasswordAuthToken(Mono<UserDetails> user,
                                                              String authCredentials) {
        return user.flatMap(u -> {
            if (Objects.isNull(u.getUsername())) {
                Mono.error(new UserNotFoundException("User not found"));
            }
            boolean valid = jwtService.validateToken(u, (String) authCredentials);
            if (valid) {
                return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), u.getAuthorities()));
            }
            Mono.error(new InvalidTokenException("Invalid token or expired"));
            return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), u.getAuthorities()));
        } );
    }
}
