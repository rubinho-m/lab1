package com.rubinho.lab1.services;

import com.rubinho.lab1.dto.AuthDto;
import com.rubinho.lab1.dto.UserDto;
import com.rubinho.lab1.model.RegistrationInfo;
import com.rubinho.lab1.model.User;

public interface UserService {
    RegistrationInfo register(UserDto userDto);

    String authorize(AuthDto userDto);

    User getUserByToken(String token);
}
