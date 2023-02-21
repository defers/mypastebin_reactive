package com.defers.mypastebin.dto.converter;

import com.defers.mypastebin.domain.Paste;
import com.defers.mypastebin.domain.User;
import com.defers.mypastebin.dto.PasteDTO;
import com.defers.mypastebin.repository.UserRepository;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Data
@Slf4j
@SuperBuilder
public class PasteDTOMapper<D extends PasteDTO> extends AbstractConverterDTO<Paste, D> {

    private UserRepository userRepository;

    public PasteDTOMapper(ModelMapper modelMapper, Class<Paste> entityClass,
                          Class<D> dtoClass, UserRepository userRepository) {
        super(modelMapper, entityClass,  dtoClass);
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(entityClass, dtoClass)
                .addMappings(m -> m.skip(D::setUsername))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(dtoClass, entityClass)
                .addMappings(m -> m.skip(Paste::setUser))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(Paste source, D destination) {
        destination.setUsername(getUserName(source));
    }

    private String getUserName(Paste source) {
        return Objects
                .requireNonNullElse(source.getUser(), User.builder().build())
                .getUsername();
    }

    @Override
    void mapSpecificFields(D source, Paste destination) {
        var username = source.getUsername();
        if (Objects.nonNull(username)) {
            destination.setUser(
                    User.builder()
                            .username(username)
                            .build()
            );
        }
    }
}
