package com.defers.mypastebin.service;

import com.defers.mypastebin.domain.User;
import com.defers.mypastebin.dto.UserDTORequest;
import com.defers.mypastebin.dto.UserDTOResponse;
import com.defers.mypastebin.dto.converter.ConverterDTO;
import com.defers.mypastebin.exception.UserNotFoundException;
import com.defers.mypastebin.exception.ValidationException;
import com.defers.mypastebin.repository.UserRepository;
import com.defers.mypastebin.util.MessagesUtils;
import com.defers.mypastebin.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserService {//ReactiveUserDetailsService, UserService {
    private final UserRepository userRepository;
    private final ConverterDTO converterDTO;
    private final Validator<User> objectValidator;
    private final TransactionalOperator transactionalOperator;

    public UserDetailsServiceImpl(UserRepository userRepository,
                                  ConverterDTO converterDTO,
                                  Validator<User> objectValidator,
                                  TransactionalOperator transactionalOperator) {
        this.userRepository = userRepository;
        this.converterDTO = converterDTO;
        this.objectValidator = objectValidator;
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<User> findUserByUsername(String username, boolean blockForUpdate) {
        return userRepository.findUserByUsername(username, blockForUpdate)
                .as(transactionalOperator::transactional)
                .switchIfEmpty(Mono.error(
                        new UserNotFoundException(
                                MessagesUtils.getFormattedMessage("Username with name %s is not found", username)))
                )
                .doOnError(e -> log.info("=====> UserDetailsServiceImpl.findUserByUsername error ===== {}", e.getMessage()))
                .doOnSuccess(user -> log.info("=====> UserDetailsServiceImpl.findUserByUsername value ===== {}", user));
    }

    @Override
    public Mono<UserDTOResponse> findUserDTOByUsername(String username, boolean blockForUpdate) {
        return findUserByUsername(username, blockForUpdate)
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

    @Override
    public Mono<UserDTOResponse> save(UserDTORequest userDto) {
        User userEntity = converterDTO.convertToEntity(userDto, User.class);
        Set<String> violations = objectValidator.validate(userEntity);
        if (violations.size() > 0) {
            return Mono.error(new ValidationException(violations));
        }

        Mono<User> userMono = userRepository.save(userEntity);
        Mono<UserDTOResponse> userDTO = userMono
                .as(transactionalOperator::transactional)
                .map(
                        user -> converterDTO.convertToDto(user, UserDTOResponse.class)
                );
        return userDTO;
    }

    @Override
    public Mono<UserDTOResponse> update(UserDTORequest userDto) {
        Mono<User> userEntity = findUserByUsername(userDto.getUsername(), true)
                .map(e -> converterDTO.convertToEntity(userDto, User.class));
        Mono<User> userMono = userEntity
                .flatMap(
                        entity -> userRepository.update(entity)
                )
                .as(transactionalOperator::transactional);
        Mono<UserDTOResponse> userDTO = userMono.map(
                user -> converterDTO.convertToDto(user, UserDTOResponse.class)
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
                .map(e -> converterDTO.convertToDto(e, UserDTOResponse.class));
    }
}
