package com.rubinho.lab1.rest.impl;

import com.rubinho.lab1.rest.UserApi;
import com.rubinho.lab1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserApiImpl implements UserApi {
    private final UserService userService;

    @Autowired
    public UserApiImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<String>> getAllUserNames() {
        return ResponseEntity.ok(userService.getAllUserNames());
    }
}
