package com.defers.mypastebin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableCaching
@EnableR2dbcRepositories
@EnableWebFlux
public class MypastebinApplication {

    public static void main(String[] args) {
        SpringApplication.run(MypastebinApplication.class, args);
    }

}
