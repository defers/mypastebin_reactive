package com.defers.mypastebin.configuration;

import com.defers.mypastebin.repository.PasteRepository;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// TODO add Elastic search logstash kibana
// TODO Dockerfile
// TODO Tests
// TODO security
// TODO Redis cache
@Configuration
public class ApplicationConfiguration {

    private final PasteRepository pasteRepository;

    public ApplicationConfiguration(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

}
