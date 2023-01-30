package com.defers.mypastebin.repository;

import com.defers.mypastebin.domain.Paste;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasteRepository extends R2dbcRepository<Paste, String> {
}
