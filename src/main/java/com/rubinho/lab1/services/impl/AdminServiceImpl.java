package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.dto.UserDto;
import com.rubinho.lab1.mappers.UserMapper;
import com.rubinho.lab1.model.Role;
import com.rubinho.lab1.model.User;
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
    private final UserMapper userMapper;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> getAllPotentialAdmins(Pageable paging) {
        return userRepository.findAllByRole(Role.POTENTIAL_ADMIN, paging)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public void setNewRoleToPotentialAdmin(Long id, Role role) {
        final User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setRole(role);
        userRepository.save(user);
    }
}
