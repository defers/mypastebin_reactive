package com.defers.mypastebin.configuration;

import com.defers.mypastebin.domain.Paste;
import com.defers.mypastebin.repository.PasteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class ApplicationConfiguration {

    private final PasteRepository pasteRepository;

    public ApplicationConfiguration(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return (args -> createTest());
    }

    private void createTest() {
        Paste paste = new Paste();
        Mono<Paste> res = pasteRepository.save(paste);
        System.out.println("=============RESULT===========" + res.block().getId());
    }
}
