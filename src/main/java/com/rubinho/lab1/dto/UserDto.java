package com.rubinho.lab1.dto;

import com.rubinho.lab1.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    private String login;
    private String password;
    private Role role;
    private PersonDto person;
}
