package com.defers.mypastebin.service;

import com.defers.mypastebin.domain.User;
import com.defers.mypastebin.dto.UserDTO;
import com.defers.mypastebin.dto.converter.ConverterDTO;
import com.defers.mypastebin.exception.UserNotFoundException;
import com.defers.mypastebin.repository.UserRepository;
import com.defers.mypastebin.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.defers.mypastebin.util.MessagesUtils.getFormattedMessage;

@Service
public class UserDetailsServiceImpl implements ReactiveUserDetailsService, UserService {
    private final UserRepository userRepository;
    private final ConverterDTO converterDTO;

    public UserDetailsServiceImpl(UserRepository userRepository, ConverterDTO converterDTO) {
        this.userRepository = userRepository;
        this.converterDTO = converterDTO;
    }

    @Override
    public Mono<User> findUserByUsername(String username) {
        return userRepository.findById(username);
    }

    @Override
    public Mono<UserDTO> findUserDTOByUsername(String username) {
        return findUserByUsername(username)
                .map(e -> converterDTO.convertToDto(e, UserDTO.class));
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository
                .findById(username)
                .switchIfEmpty(Mono.error(new UserNotFoundException(
                        getFormattedMessage("User with username %s not found", username)))
                )
                .map(user -> UserDetailsImpl
                        .builder()
                        .user(user)
                        .build()
                );
    }

    @Override
    public Mono<UserDTO> save(UserDTO userDto) {
        return Mono.just(converterDTO.convertToEntity(userDto, User.class))
                .map(user -> userRepository.save(user))
                .map(user -> converterDTO.convertToEntity(user, UserDTO.class));
    }

    @Override
    public Mono<UserDTO> update(UserDTO userDto) {
        return null;
    }

    @Override
    public Mono<Void> delete(UserDTO userDto) {
        return null;
    }

    // Test GIT
    public void someTestMethod() {
        System.out.println("Hello Test!");
    }
    
    @Override
    public Flux<UserDTO> findAll() {
        return null;
    }
}
