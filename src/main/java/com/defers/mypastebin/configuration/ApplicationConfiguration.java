package com.defers.mypastebin.configuration;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// TODO Dockerfile
// TODO Tests
// TODO Roles
// TODO Redis cache
@Configuration
public class ApplicationConfiguration {

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

}
