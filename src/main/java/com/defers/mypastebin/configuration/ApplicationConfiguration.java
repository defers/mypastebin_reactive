package com.defers.mypastebin.configuration;

import com.defers.mypastebin.repository.PasteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    private final PasteRepository pasteRepository;

    public ApplicationConfiguration(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return (args -> createTest());
    }

    private void createTest() {
//        var service = new PasteServiceImpl(pasteRepository,
//                new ConverterDTOImpl(new ModelMapper()));
//
//
//        var paste = new PasteDTO(null, "Test1", "fdsdadas");
//        Mono<PasteDTO> res = service.save(paste);
//        System.out.println("=============RESULT===========" + res.block());
    }
}
