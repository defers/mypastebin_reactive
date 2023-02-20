package com.defers.mypastebin.repository;

import com.defers.mypastebin.domain.User;
import com.defers.mypastebin.exception.UserNotFoundException;
import com.defers.mypastebin.repository.converter.UserConverter;
import com.defers.mypastebin.repository.query.UserQuery;
import com.defers.mypastebin.util.MessagesUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@Slf4j
@Component
public class UserRepositoryImpl implements UserRepository {

    private DatabaseClient databaseClient;

    public UserRepositoryImpl(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Flux<User> findAll() {
        return databaseClient.sql(UserQuery.findAll())
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("u_username"))
                .flatMap(rows -> UserConverter.fromRows(rows));
    }

    @Override
    public Mono<User> findUserByUsername(String username, boolean blockForUpdate) {
        return databaseClient.sql(UserQuery.findUserByUsername(blockForUpdate))
                .bind("username", username)
                .fetch()
                .all()
                .switchIfEmpty(Mono.error(new UserNotFoundException(
                        MessagesUtils.getFormattedMessage("Username with name %s has not found", username))))
                .bufferUntilChanged(result -> result.get("u_username"))
                .flatMap(rows -> UserConverter.fromRows(rows))
                .single()
                .doOnError(e -> log.info("=====> UserRepositoryImpl.findUserByUsername error ===== {}", e.getMessage()));
    }

    @Override
    public Mono<User> save(User user) {
        return databaseClient.sql(UserQuery.save())
                .bind("username", user.getUsername())
                .bind("password", user.getPassword())
                .bind("email", user.getEmail())
                .fetch()
                .first()
                .thenReturn(user);
    }

    @Override
    public Mono<User> update(User user) {
        return databaseClient.sql(UserQuery.update())
                .bind("username", user.getUsername())
                .bind("password", user.getPassword())
                .bind("email", user.getEmail())
                .fetch()
                .first()
                .thenReturn(user);
    }

    @Override
    public Mono<Void> delete(String username) {
        return databaseClient.sql(UserQuery.delete())
                .bind("username", username)
                .fetch()
                .first()
                .then();
    }
}
