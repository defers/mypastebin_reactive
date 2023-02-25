package com.defers.mypastebin.dto.converter;

import com.defers.mypastebin.domain.Role;
import com.defers.mypastebin.domain.User;
import com.defers.mypastebin.dto.AbstractUserDTO;
import com.defers.mypastebin.repository.RoleRepository;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@SuperBuilder
public class UserDTOMapper<D extends AbstractUserDTO> extends AbstractConverterDTO<User, D> {
    private final RoleRepository roleRepository;

    public UserDTOMapper(ModelMapper modelMapper, Class<User> entityClass,
                  Class<D> dtoClass, RoleRepository roleRepository) {
        super(modelMapper, entityClass, dtoClass);
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(entityClass, dtoClass)
                .addMappings(m -> m.skip(D::setRoles))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(dtoClass, entityClass)
                .addMappings(m -> m.skip(User::setRoles))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(User source, D destination) {
        destination.setRoles(getRolesId(source));
    }

    private Set<Long> getRolesId(User source) {
        return source.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
    }

    @Override
    void mapSpecificFields(D source, User destination) {
        Set<Long> rolesDto = source.getRoles();
        if (Objects.nonNull(rolesDto)) {
            Set<Role> roles = rolesDto.stream()
                    .map(id -> roleRepository.findById(id, true).onErrorComplete().block())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            destination.setRoles(roles);
        }
    }

}
