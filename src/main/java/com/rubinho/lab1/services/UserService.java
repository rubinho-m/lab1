package com.rubinho.lab1.services;

import com.rubinho.lab1.dto.AuthDto;
import com.rubinho.lab1.dto.SignUpDto;
import com.rubinho.lab1.model.RegistrationInfo;
import com.rubinho.lab1.model.User;

import java.util.List;

public interface UserService {
    RegistrationInfo register(SignUpDto signUpDto);

    String authorize(AuthDto authDto);

    User getUserByToken(String token);

    User getUserByLogin(String login);

    List<String> getAllUserNames();
}
