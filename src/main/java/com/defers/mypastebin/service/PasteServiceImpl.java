package com.defers.mypastebin.service;

import com.defers.mypastebin.domain.Paste;
import com.defers.mypastebin.domain.utils.PasteIdGenerator;
import com.defers.mypastebin.dto.PasteDTO;
import com.defers.mypastebin.dto.converter.PasteDTOMapper;
import com.defers.mypastebin.exception.PasteNotFoundException;
import com.defers.mypastebin.exception.ValidationException;
import com.defers.mypastebin.repository.PasteRepository;
import com.defers.mypastebin.util.MessagesUtils;
import com.defers.mypastebin.validator.Validator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Data
@Slf4j
@Service
public class PasteServiceImpl implements PasteService {
    private final PasteRepository pasteRepository;
    private final PasteDTOMapper<PasteDTO> converterDTO;
    private final Validator<Paste> objectValidator;
    private final TransactionalOperator transactionalOperator;

    @Autowired
    public PasteServiceImpl(PasteRepository pasteRepository,
                            PasteDTOMapper<PasteDTO> converterDTO,
                            Validator<Paste> objectValidator,
                            TransactionalOperator transactionalOperator) {
        this.pasteRepository = pasteRepository;
        this.converterDTO = converterDTO;
        this.objectValidator = objectValidator;
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Flux<PasteDTO> findAll() {
        return pasteRepository.findAll()
                .map(e -> converterDTO.convertToDto(e));
    }

    @Override
    public Mono<PasteDTO> findById(String id) {
        return pasteRepository.findById(id, false)
                .map(e -> converterDTO.convertToDto(e))
                .switchIfEmpty(
                        Mono.error(
                                new PasteNotFoundException(
                                        MessagesUtils.getFormattedMessage("Paste with id %s not found", id))
                        )
                );
    }

    @Override
    public Mono<PasteDTO> save(PasteDTO paste) {
//        return Mono.justOrEmpty(paste)
//                .doOnNext(e -> System.out.println("qqqqTqEEEESSSS!!!!!!!!"))
//                .map(d -> {
//                    System.out.println("!!!TEST!!!!!!!!!!!!!!!");
//                    return converterDTO.convertToEntity(d);
//                }
//                )
//                .map(e -> {
//                            System.out.println("TEST!!!!!!!!!!!!!!!");
//                            Set<String> violations = objectValidator.validate(e);
//                            if (violations.size() > 0) {
//                                Mono.error(new ValidationException(violations));
//                            }
//                            e.setId(PasteIdGenerator.generate());
//                            return e;
//                        }
//                )
//                .flatMap(e -> pasteRepository.save(e))
//                //.as(transactionalOperator::transactional)
//                .map(e -> converterDTO.convertToDto(e))
//                .log()
//                .doOnError(e -> log.info("=====> PasteServiceImpl.save error ===== {}", e.getMessage()))
//                .doOnSuccess(pasteDTO -> log.info("=====> PasteServiceImpl.save value ===== {}", pasteDTO));

        Paste pasteEntity = converterDTO.convertToEntity(paste);
        Set<String> violations = objectValidator.validate(pasteEntity);
        if (violations.size() > 0) {
            return Mono.error(new ValidationException(violations));
        }
//        var pasteEntity = Paste.builder()
//                .textDescription("yoyoyoy12345")
//                .build();
        pasteEntity.setId(PasteIdGenerator.generate());
        return pasteRepository.save(pasteEntity)
                .as(transactionalOperator::transactional)
                .map(e -> converterDTO.convertToDto(e));

    }

    @Override
    public Mono<PasteDTO> update(PasteDTO paste, String id) {
        Paste pasteEntity = converterDTO.convertToEntity(paste);
        Set<String> violations = objectValidator.validate(pasteEntity);
        if (violations.size() > 0) {
            return Mono.error(new ValidationException(violations));
        }
        return pasteRepository.findById(id, true)
                .switchIfEmpty(Mono.error(
                        new PasteNotFoundException(
                                MessagesUtils.getFormattedMessage("Paste with id %s not found", id)))
                )
                .doOnNext(e -> pasteRepository.save(e))
                .map(e -> converterDTO.convertToDto(e))
                .as(transactionalOperator::transactional);
    }

    @Override
    public Mono<Void> delete(PasteDTO paste, String id) {
        return pasteRepository.findById(id, true)
                .switchIfEmpty(Mono.error(
                        new PasteNotFoundException(
                                MessagesUtils.getFormattedMessage("Paste with id %s not found", id)))

                )
                .doOnNext(e -> pasteRepository.delete(id))
                .thenEmpty(Mono.empty())
                .as(transactionalOperator::transactional);
    }
}
