package com.rubinho.lab1.rest;

import com.rubinho.lab1.dto.PersonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface PersonApi {
    @GetMapping("/persons")
    ResponseEntity<List<PersonDto>> getAllPersons();
}
