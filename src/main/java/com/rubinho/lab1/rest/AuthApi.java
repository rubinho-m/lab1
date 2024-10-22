package com.rubinho.lab1.rest;

import com.rubinho.lab1.dto.AuthDto;
import com.rubinho.lab1.dto.RegisteredUserDto;
import com.rubinho.lab1.dto.SignUpDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface AuthApi {
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    ResponseEntity<RegisteredUserDto> register(@RequestBody SignUpDto signUpDto);

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    ResponseEntity<RegisteredUserDto> authorize(@RequestBody AuthDto authDto);

    @PostMapping("/request/admin")
    ResponseEntity<Void> requestAdminRole(@RequestHeader(name = "Authorization") String token);
}
