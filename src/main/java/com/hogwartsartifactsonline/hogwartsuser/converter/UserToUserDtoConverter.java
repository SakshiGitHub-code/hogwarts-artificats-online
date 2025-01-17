package com.hogwartsartifactsonline.hogwartsuser.converter;

import com.hogwartsartifactsonline.hogwartsuser.HogwartsUser;
import com.hogwartsartifactsonline.hogwartsuser.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<HogwartsUser, UserDto> {

    @Override
    public UserDto convert(HogwartsUser source) {
        // We are not setting password in DTO.
        final UserDto userDto = new UserDto(source.getId(),
                source.getUsername(),
                source.isEnabled(),
                source.getRoles());
        return userDto;
    }

}
