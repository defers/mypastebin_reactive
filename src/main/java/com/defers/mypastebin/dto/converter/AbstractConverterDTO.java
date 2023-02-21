package com.defers.mypastebin.dto.converter;

import com.defers.mypastebin.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.Objects;

@Data
@SuperBuilder
public abstract class AbstractConverterDTO<E extends BaseEntity, D>
        implements ConverterDTO<E, D> {

    final ModelMapper modelMapper;
    final Class<E> entityClass;
    final Class<D> dtoClass;

    AbstractConverterDTO(ModelMapper modelMapper, Class<E> entityClass, Class<D> dtoClass) {
        this.modelMapper = modelMapper;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public D convertToDto(E entity) {
        entity = Objects.requireNonNull(entity, "Entity can not be null");
        return modelMapper.map(entity, dtoClass);
    }

    @Override
    public E convertToEntity(D dto) {
        dto = Objects.requireNonNull(dto, "DTO can not be null");
        return modelMapper.map(dto, entityClass);
    }

    Converter<E, D> toDtoConverter() {
        return context -> {
            E source = context.getSource();
            D destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<D, E> toEntityConverter() {
        return context -> {
            D source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    void mapSpecificFields(E source, D destination) {
    }

    void mapSpecificFields(D source, E destination) {
    }
}
