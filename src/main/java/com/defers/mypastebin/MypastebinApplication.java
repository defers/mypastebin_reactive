package com.defers.mypastebin;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Swagger Demo",
        version = "1.0",
        description = "Documentation APIs v1.0")
)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableR2dbcRepositories
@EnableWebFlux
public class MypastebinApplication {

    public static void main(String[] args) {
        SpringApplication.run(MypastebinApplication.class, args);
    }

}
