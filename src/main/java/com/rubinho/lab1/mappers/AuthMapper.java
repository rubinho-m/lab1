package com.rubinho.lab1.mappers;

import com.rubinho.lab1.dto.AuthDto;
import com.rubinho.lab1.dto.UserDto;
import com.rubinho.lab1.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PersonMapper.class)
public interface AuthMapper {
    @Mapping(target = "password", ignore = true)
    User toEntity(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    User toEntity(AuthDto authDto);

    UserDto toDto(User user);
}
