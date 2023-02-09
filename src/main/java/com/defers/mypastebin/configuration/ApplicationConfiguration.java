package com.defers.mypastebin.configuration;

import com.defers.mypastebin.dto.PasteDTO;
import com.defers.mypastebin.dto.converter.ConverterDTOImpl;
import com.defers.mypastebin.repository.PasteRepository;
import com.defers.mypastebin.service.PasteServiceImpl;
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
        var modelMapper = new ModelMapper();
//        modelMapper.getConfiguration()
//                .setMatchingStrategy(MatchingStrategies.STRICT)
//        .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }

//    @Bean
//    public Docket swaggerApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return (args -> createTest());
    }

    private void createTest() {
        var service = new PasteServiceImpl(pasteRepository,
                new ConverterDTOImpl(new ModelMapper()));


        var paste = new PasteDTO(null, "Test1", "fdsdadas");
        Mono<PasteDTO> res = service.save(paste);
        System.out.println("=============RESULT===========" + res.block());
    }
}
