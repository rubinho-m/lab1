package com.rubinho.lab1.rest.impl;

import com.rubinho.lab1.dto.AuthDto;
import com.rubinho.lab1.dto.SignUpDto;
import com.rubinho.lab1.model.RegistrationInfo;
import com.rubinho.lab1.rest.AuthApi;
import com.rubinho.lab1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class AuthApiImpl implements AuthApi {
    private final UserService userService;

    @Autowired
    public AuthApiImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<String> register(SignUpDto signUpDto) {
        final RegistrationInfo info = userService.register(signUpDto);
        final HttpStatus httpStatus = info.success() ? HttpStatus.CREATED : HttpStatus.ACCEPTED;
        return ResponseEntity.status(httpStatus).body(info.token());
    }

    @Override
    public ResponseEntity<String> authorize(AuthDto authDto) {
        return ResponseEntity.ok(userService.authorize(authDto));
    }
}
