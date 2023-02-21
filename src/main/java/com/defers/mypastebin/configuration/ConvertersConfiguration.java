package com.defers.mypastebin.configuration;

import com.defers.mypastebin.domain.Paste;
import com.defers.mypastebin.domain.User;
import com.defers.mypastebin.dto.PasteDTO;
import com.defers.mypastebin.dto.UserDTORequest;
import com.defers.mypastebin.dto.UserDTOResponse;
import com.defers.mypastebin.dto.converter.PasteDTOMapper;
import com.defers.mypastebin.dto.converter.UserDTOMapper;
import com.defers.mypastebin.repository.RoleRepository;
import com.defers.mypastebin.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConvertersConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }

    @Bean
    public UserDTOMapper<UserDTORequest> userDTOMapperRequest(RoleRepository roleRepository) {
        return new UserDTOMapper<>(modelMapper(), User.class,
                UserDTORequest.class, roleRepository);
    }

    @Bean
    public UserDTOMapper<UserDTOResponse> userDTOMapperResponse(RoleRepository roleRepository) {
        return new UserDTOMapper<>(modelMapper(), User.class,
                UserDTOResponse.class, roleRepository);
    }

    @Bean
    public PasteDTOMapper<PasteDTO> pasteDTOMapper(UserRepository userRepository) {
        return new PasteDTOMapper<>(modelMapper(), Paste.class,
                PasteDTO.class, userRepository);
    }
}
