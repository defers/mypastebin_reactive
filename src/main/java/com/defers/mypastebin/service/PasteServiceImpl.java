package com.defers.mypastebin.service;

import com.defers.mypastebin.domain.Paste;
import com.defers.mypastebin.domain.utils.PasteIdGenerator;
import com.defers.mypastebin.dto.PasteDTO;
import com.defers.mypastebin.dto.converter.ConverterDTO;
import com.defers.mypastebin.exception.PasteNotFoundException;
import com.defers.mypastebin.repository.PasteRepository;
import com.defers.mypastebin.util.MessagesUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@org.springframework.transaction.annotation.Transactional(readOnly = true)
@Service
public class PasteServiceImpl implements PasteService {
    private final PasteRepository pasteRepository;
    private final ConverterDTO converterDTO;

    @Autowired
    public PasteServiceImpl(PasteRepository pasteRepository,
                            ConverterDTO converterDTO) {
        this.pasteRepository = pasteRepository;
        this.converterDTO = converterDTO;
    }

    @Override
    public Flux<PasteDTO> findAll() {
        return pasteRepository.findAll()
                .map(e -> converterDTO.convertToDto(e, PasteDTO.class));
    }

    @Override
    public Mono<PasteDTO> findById(String id) {
        return pasteRepository.findById(id)
                .map(e -> converterDTO.convertToDto(e, PasteDTO.class))
                .switchIfEmpty(
                        Mono.error(
                                new PasteNotFoundException(
                                        MessagesUtils.getFormattedMessage("Paste with id %s not found", id))
                        )
                );
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = false)
    @Override
    public Mono<PasteDTO> save(PasteDTO paste) {
        return Mono.just(paste)
                .map(e -> converterDTO.convertToEntity(e, Paste.class))
                .flatMap(e -> {
                    e.setId(PasteIdGenerator.generate());
                    return pasteRepository.save(e);
                })
                .map(e -> converterDTO.convertToDto(e, PasteDTO.class));
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = false)
    @Override
    public Mono<PasteDTO> update(PasteDTO paste, String id) {
        return pasteRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new PasteNotFoundException(
                                MessagesUtils.getFormattedMessage("Paste with id %s not found", id)))
                )
                .doOnNext(e -> pasteRepository.save(e))
                .map(e -> converterDTO.convertToDto(e, PasteDTO.class));
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = false)
    @Override
    public Mono<Void> delete(PasteDTO paste, String id) {
        return pasteRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new PasteNotFoundException(
                                MessagesUtils.getFormattedMessage("Paste with id %s not found", id)))

                )
                .map(e -> converterDTO.convertToEntity(e, Paste.class))
                .doOnNext(e -> pasteRepository.delete(e))
                .thenEmpty(Mono.empty());
    }
}
