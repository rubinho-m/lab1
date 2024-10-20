package com.rubinho.lab1.rest.impl;

import com.rubinho.lab1.dto.PersonDto;
import com.rubinho.lab1.rest.PersonApi;
import com.rubinho.lab1.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonApiImpl implements PersonApi {
    private final PersonService personService;

    public PersonApiImpl(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        return ResponseEntity.ok(personService.getAllPersons());
    }
}
