package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.dto.PersonDto;
import com.rubinho.lab1.mappers.PersonMapper;
import com.rubinho.lab1.model.Person;
import com.rubinho.lab1.model.Role;
import com.rubinho.lab1.model.User;
import com.rubinho.lab1.repository.PersonRepository;
import com.rubinho.lab1.repository.UserRepository;
import com.rubinho.lab1.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, PersonRepository personRepository, PersonMapper personMapper) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    @Override
    public List<PersonDto> getAllPotentialAdmins(Pageable paging) {
        return userRepository.findAllByRole(Role.POTENTIAL_ADMIN, paging)
                .stream()
                .map(User::getPerson)
                .map(personMapper::toDto)
                .toList();
    }

    @Override
    public void setNewRoleToPotentialAdmin(Long id, Role role) {
        final Person person = personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));
        final User user = userRepository.findByPerson(person).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setRole(role);
        userRepository.save(user);
    }
}
