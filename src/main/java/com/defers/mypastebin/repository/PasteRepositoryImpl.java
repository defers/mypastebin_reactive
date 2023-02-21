package com.defers.mypastebin.repository;

import com.defers.mypastebin.domain.Paste;
import com.defers.mypastebin.exception.PasteNotFoundException;
import com.defers.mypastebin.repository.converter.PasteConverter;
import com.defers.mypastebin.repository.query.PasteQuery;
import com.defers.mypastebin.util.MessagesUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.Parameter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@Slf4j
@RequiredArgsConstructor
@Component
public class PasteRepositoryImpl implements PasteRepository {
    private final DatabaseClient databaseClient;

    @Override
    public Flux<Paste> findAll() {
        return databaseClient.sql(PasteQuery.findAll())
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("p_id"))
                .flatMap(rows -> PasteConverter.fromRows(rows))
                .doOnError(e -> log.info("=====> PasteRepositoryImpl.findAll error ===== {}", e.getMessage()));
    }

    @Override
    public Mono<Paste> findById(String id, boolean blockForUpdate) {
        return databaseClient.sql(PasteQuery.findById(blockForUpdate))
                .bind("id", id)
                .fetch()
                .all()
                .switchIfEmpty(Mono.error(new PasteNotFoundException(
                        MessagesUtils.getFormattedMessage("Paste with id %s has not found", id))))
                .bufferUntilChanged(result -> result.get("p_id"))
                .flatMap(rows -> PasteConverter.fromRows(rows))
                .single()
                .doOnError(e -> log.info("=====> PasteRepositoryImpl.findById error ===== {}", e.getMessage()));
    }

    @Override
    public Mono<Paste> save(Paste paste) {
        return databaseClient.sql(PasteQuery.save())
                .bind("id", paste.getId())
                .bind("text_description", paste.getTextDescription())
                .bind("username", Parameter.fromOrEmpty(
                        paste.getUser() != null ? paste.getUser().getUsername() : null, String.class))
                .fetch()
                .first()
                .thenReturn(paste)
                .doOnSuccess(e -> log.info("=====> PasteRepositoryImpl.databaseClient success ===== {}", e))
                .doOnError(e -> log.info("=====> PasteRepositoryImpl.databaseClient error ===== {}", e.getMessage()));
    }

    @Override
    public Mono<Paste> update(Paste paste) {
        return null;
    }

    @Override
    public Mono<Void> delete(String id) {
        return null;
    }
}
