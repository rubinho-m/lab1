package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.dto.PersonDto;
import com.rubinho.lab1.mappers.PersonMapper;
import com.rubinho.lab1.repository.PersonRepository;
import com.rubinho.lab1.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    @Override
    public List<PersonDto> getAllPersons() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toDto)
                .toList();
    }
}
