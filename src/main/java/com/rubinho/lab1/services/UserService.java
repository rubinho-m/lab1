package com.rubinho.lab1.services;

import com.rubinho.lab1.dto.AuthDto;
import com.rubinho.lab1.dto.RegisteredUserDto;
import com.rubinho.lab1.dto.SignUpDto;
import com.rubinho.lab1.model.User;

import java.util.List;

public interface UserService {
    RegisteredUserDto register(SignUpDto signUpDto);

    RegisteredUserDto authorize(AuthDto authDto);

    boolean requestAdminRights(String token);

    User getUserByToken(String token);

    User getUserByLogin(String login);

    List<String> getAllUserNames();
}
