package com.defers.mypastebin.dto.converter;

public interface ConverterDTO {
    <T, U> U convertToDto(T entity, Class<U> clazz);
    <T, U> U convertToEntity(T dto, Class<U> clazz);
}
