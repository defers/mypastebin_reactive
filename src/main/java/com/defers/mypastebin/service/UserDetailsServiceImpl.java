package com.defers.mypastebin.service;

import com.defers.mypastebin.domain.User;
import com.defers.mypastebin.dto.UserDTORequest;
import com.defers.mypastebin.dto.UserDTOResponse;
import com.defers.mypastebin.dto.converter.ConverterDTO;
import com.defers.mypastebin.exception.UserNotFoundException;
import com.defers.mypastebin.exception.ValidationException;
import com.defers.mypastebin.repository.UserRepository;
import com.defers.mypastebin.security.UserDetailsImpl;
import com.defers.mypastebin.util.SecurityUtils;
import com.defers.mypastebin.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

import static com.defers.mypastebin.util.MessagesUtils.getFormattedMessage;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserService, ReactiveUserDetailsService {
    private final UserRepository userRepository;
    private final ConverterDTO<User, UserDTOResponse> converterDTOResponse;
    private final ConverterDTO<User, UserDTORequest> converterDTORequest;
    private final Validator<User> objectValidator;
    private final TransactionalOperator transactionalOperator;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository,
                                  ConverterDTO<User, UserDTOResponse> converterDTOResponse,
                                  ConverterDTO<User, UserDTORequest> converterDTORequest,
                                  Validator<User> objectValidator,
                                  TransactionalOperator transactionalOperator,
                                  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.converterDTOResponse = converterDTOResponse;
        this.converterDTORequest = converterDTORequest;
        this.objectValidator = objectValidator;
        this.transactionalOperator = transactionalOperator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<User> findUserByUsername(String username, boolean blockForUpdate) {
        return userRepository.findUserByUsername(username, blockForUpdate)
                .as(transactionalOperator::transactional)
                .switchIfEmpty(Mono.error(
                        new UserNotFoundException(
                                getFormattedMessage("Username with name %s is not found", username)))
                )
                .doOnError(e -> log.info("=====> UserDetailsServiceImpl.findUserByUsername error ===== {}", e.getMessage()))
                .doOnSuccess(user -> log.info("=====> UserDetailsServiceImpl.findUserByUsername value ===== {}", user));
    }

    @Override
    public Mono<UserDTOResponse> findUserDTOByUsername(String username, boolean blockForUpdate) {
        return findUserByUsername(username, blockForUpdate)
                .map(converterDTOResponse::convertToDto)
                .doOnError(e -> log.info("=====> UserDetailsServiceImpl.findUserDTOByUsername error ===== {}", e.getMessage()))
                .doOnSuccess(userDTO -> log.info("=====> UserDetailsServiceImpl.findUserDTOByUsername value ===== {}", userDTO));
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository
                .findUserByUsername(username, false)
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
    public Mono<UserDTOResponse> save(UserDTORequest userDto) {
        User userEntity = converterDTORequest.convertToEntity(userDto);
        Set<String> violations = objectValidator.validate(userEntity);
        if (!violations.isEmpty()) {
            return Mono.error(new ValidationException(violations));
        }

        setEncodePassword(userEntity);
        Mono<User> userMono = userRepository.save(userEntity);
        Mono<UserDTOResponse> userDTO = userMono
                .as(transactionalOperator::transactional)
                .map(
                        converterDTOResponse::convertToDto
                );
        return userDTO;
    }

    private void setEncodePassword(User user) {
        String encodedPassword = SecurityUtils.encodePassword(user.getPassword(), passwordEncoder);
        user.setPassword(encodedPassword);
    }

    @Override
    public Mono<UserDTOResponse> update(UserDTORequest userDto) {
        Mono<User> userEntity = findUserByUsername(userDto.getUsername(), true)
                .map(e -> {
                    User user = converterDTORequest.convertToEntity(userDto);
                    Set<String> violations = objectValidator.validate(user);
                    if (!violations.isEmpty()) {
                        Mono.error(new ValidationException(violations));
                    }
                    setEncodePassword(user);
                    return user;
                });
        Mono<User> userMono = userEntity
                .flatMap(
                        userRepository::update
                )
                .as(transactionalOperator::transactional);
        Mono<UserDTOResponse> userDTO = userMono.map(
                converterDTOResponse::convertToDto
        );
        return userDTO;
    }

    @Override
    public Mono<Void> delete(String username) {
        return findUserByUsername(username, true)
                .as(transactionalOperator::transactional)
                .then(userRepository.delete(username));
    }

    @Override
    public Flux<UserDTOResponse> findAll() {
        return userRepository.findAll()
                .as(transactionalOperator::transactional)
                .map(converterDTOResponse::convertToDto);
    }
}
