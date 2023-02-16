package com.defers.mypastebin.service;

import com.defers.mypastebin.domain.User;
import com.defers.mypastebin.dto.UserDTORequest;
import com.defers.mypastebin.dto.UserDTOResponse;
import com.defers.mypastebin.dto.converter.ConverterDTO;
import com.defers.mypastebin.exception.UserNotFoundException;
import com.defers.mypastebin.exception.ValidationException;
import com.defers.mypastebin.repository.UserRepository;
import com.defers.mypastebin.util.MessagesUtils;
import com.defers.mypastebin.validator.ObjectValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Slf4j
@Service
//@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserService {//ReactiveUserDetailsService, UserService {
    private final UserRepository userRepository;
    private final ConverterDTO converterDTO;
    private final ObjectValidator<User> objectValidator;

    public UserDetailsServiceImpl(UserRepository userRepository,
                                  ConverterDTO converterDTO,
                                  ObjectValidator<User> objectValidator) {
        this.userRepository = userRepository;
        this.converterDTO = converterDTO;
        this.objectValidator = objectValidator;
    }

    @Override
    public Mono<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .switchIfEmpty(Mono.error(
                        new UserNotFoundException(
                                MessagesUtils.getFormattedMessage("Username with name %s is not found", username)))
                )
                .doOnError(e -> log.info("=====> UserDetailsServiceImpl.findUserByUsername error ===== {}", e.getMessage()))
                .doOnSuccess(user -> log.info("=====> UserDetailsServiceImpl.findUserByUsername value ===== {}", user));
    }

    @Override
    public Mono<UserDTOResponse> findUserDTOByUsername(String username) {
        return findUserByUsername(username)
                .map(e -> converterDTO.convertToDto(e, UserDTOResponse.class))
                .doOnError(e -> log.info("=====> UserDetailsServiceImpl.findUserDTOByUsername error ===== {}", e.getMessage()))
                .doOnSuccess(userDTO -> log.info("=====> UserDetailsServiceImpl.findUserDTOByUsername value ===== {}", userDTO));
    }

//    @Override
//    public Mono<UserDetails> findByUsername(String username) {
//        return userRepository
//                .findUserByUsername(username)
//                .switchIfEmpty(Mono.error(new UserNotFoundException(
//                        getFormattedMessage("User with username %s not found", username)))
//                )
//                .map(user -> UserDetailsImpl
//                        .builder()
//                        .user(user)
//                        .build()
//                );
//    }

    //@Transactional(readOnly = false)
    @Override
    public Mono<UserDTOResponse> save(UserDTORequest userDto) {
        User userEntity = converterDTO.convertToEntity(userDto, User.class);
        Set<String> violations = objectValidator.validate(userEntity);
        if (violations.size() > 0) {
            return Mono.error(new ValidationException(violations));
        }

        Mono<User> userMono = userRepository.save(userEntity);
        Mono<UserDTOResponse> userDTO = userMono.map(
                user -> converterDTO.convertToDto(user, UserDTOResponse.class)
        );
        return userDTO;
    }

    //@Transactional(readOnly = false)
    @Override
    public Mono<UserDTOResponse> update(UserDTORequest userDto) {
        Mono<User> userEntity = findUserByUsername(userDto.getUsername())
                .map(e -> converterDTO.convertToEntity(userDto, User.class));
        Mono<User> userMono = userEntity.flatMap(
                entity -> userRepository.update(entity)
        );
        Mono<UserDTOResponse> userDTO = userMono.map(
                user -> converterDTO.convertToDto(user, UserDTOResponse.class)
        );
        return userDTO;
    }

    //@Transactional(readOnly = false)
    @Override
    public Mono<Void> delete(String username) {
        return findUserByUsername(username)
                .then(userRepository.delete(username));
    }

    // Test GIT
    public void someTestMethod() {
        System.out.println("Hello Test!");
    }
    
    @Override
    public Flux<UserDTOResponse> findAll() {
        return userRepository.findAll()
                .map(e -> converterDTO.convertToDto(e, UserDTOResponse.class));
    }
}
