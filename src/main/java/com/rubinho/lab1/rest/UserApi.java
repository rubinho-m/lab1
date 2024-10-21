package com.rubinho.lab1.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface UserApi {
    @GetMapping("/users")
    ResponseEntity<List<String>> getAllUserNames();
}
