package com.defers.mypastebin.dto.converter;

import com.defers.mypastebin.domain.BaseEntity;

public interface ConverterDTO <E extends BaseEntity, D>{
    D convertToDto(E entity);
    E convertToEntity(D dto);
}
