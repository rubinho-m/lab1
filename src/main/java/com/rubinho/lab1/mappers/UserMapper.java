package com.rubinho.lab1.mappers;

import com.rubinho.lab1.dto.RegisteredUserDto;
import com.rubinho.lab1.dto.SignUpDto;
import com.rubinho.lab1.dto.UserDto;
import com.rubinho.lab1.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    User toEntity(SignUpDto signUpDto);

    UserDto toDto(User user);

    RegisteredUserDto toRegisteredUserDto(User user);
}
