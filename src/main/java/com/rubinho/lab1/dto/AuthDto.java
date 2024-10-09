package com.rubinho.lab1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AuthDto {
    private String login;
    private String password;
}
