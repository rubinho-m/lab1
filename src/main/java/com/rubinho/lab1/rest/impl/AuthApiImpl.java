package com.rubinho.lab1.rest.impl;

import com.rubinho.lab1.dto.AuthDto;
import com.rubinho.lab1.dto.RegisteredUserDto;
import com.rubinho.lab1.dto.SignUpDto;
import com.rubinho.lab1.rest.AuthApi;
import com.rubinho.lab1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthApiImpl implements AuthApi {
    private final UserService userService;

    @Autowired
    public AuthApiImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<RegisteredUserDto> register(SignUpDto signUpDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(signUpDto));
    }

    @Override
    public ResponseEntity<RegisteredUserDto> authorize(AuthDto authDto) {
        return ResponseEntity.ok(userService.authorize(authDto));
    }

    @Override
    public ResponseEntity<Void> requestAdminRole(String token) {
        final boolean success = userService.requestAdminRights(token);
        final HttpStatus httpStatus = success ? HttpStatus.OK : HttpStatus.ACCEPTED;
        return ResponseEntity.status(httpStatus).build();
    }
}
