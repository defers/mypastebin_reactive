package com.defers.mypastebin.dto.converter;

import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
public class ConverterDTOImpl implements ConverterDTO {

    private final ModelMapper modelMapper;

    @Autowired
    public ConverterDTOImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public <T, U> U convertToDto(T entity, Class<U> clazz) {
        return modelMapper.map(entity, clazz);
    }

    @Override
    public <T, U> U convertToEntity(T dto, Class<U> clazz) {
        return modelMapper.map(dto, clazz);
    }
}
