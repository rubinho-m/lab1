package com.rubinho.lab1.rest;

import com.rubinho.lab1.model.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface UserApi {
    @GetMapping("/users")
    ResponseEntity<List<String>> getAllUserNames();

    @GetMapping("/role/current")
    ResponseEntity<Role> getRole(@RequestHeader("Authorization") String token);
}
