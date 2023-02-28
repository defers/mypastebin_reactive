package com.defers.mypastebin.service;

import com.defers.mypastebin.domain.User;
import com.defers.mypastebin.exception.UserNotFoundException;
import com.defers.mypastebin.repository.UserRepository;
import com.defers.mypastebin.util.MessagesUtils;
import com.defers.mypastebin.util.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

@ExtendWith(value = {MockitoExtension.class})
class UserDetailsServiceImplTest {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @InjectMocks
    private UserDetailsServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
    }

    @Test
    void findUserByUsername_ShouldReturnUserWhenFindByUsername() {
        String password = SecurityUtils.encodePassword("password1", passwordEncoder);
        String username = "username1";
        User userMocked = User.builder()
                .username(username)
                .password(password)
                .email("email1@mail.com")
                .createdDate(LocalDateTime.of(2023, 1, 10, 15, 10, 0))
                .updatedDate(LocalDateTime.of(2023, 1, 12, 14, 12, 0))
                .build();

        Mockito.when(userRepository.findUserByUsername(Mockito.eq(username), Mockito.anyBoolean()))
                .thenReturn(Mono.just(userMocked));

        StepVerifier.create(userService.findUserByUsername(username, false))
                .expectNext(userMocked)
                .expectComplete()
                .verify();
    }

    @Test
    void findUserByUsername_ShouldThrowErrorIfNotFound() {
        String username = "username1";

        Mockito.when(userRepository.findUserByUsername(Mockito.eq(username), Mockito.anyBoolean()))
                .thenReturn(Mono.error(new UserNotFoundException(
                                MessagesUtils.getFormattedMessage("Username with name %s has not found", username)))
                );

        StepVerifier.create(userService.findUserByUsername(username, false))
                .expectError(UserNotFoundException.class)
                .verify();
    }

}