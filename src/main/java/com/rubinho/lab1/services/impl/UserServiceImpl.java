package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.dto.AuthDto;
import com.rubinho.lab1.dto.SignUpDto;
import com.rubinho.lab1.jwt.UserAuthProvider;
import com.rubinho.lab1.mappers.UserMapper;
import com.rubinho.lab1.model.RegistrationInfo;
import com.rubinho.lab1.model.Role;
import com.rubinho.lab1.model.User;
import com.rubinho.lab1.repository.UserRepository;
import com.rubinho.lab1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserAuthProvider userAuthProvider;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserAuthProvider userAuthProvider,
                           UserRepository userRepository,
                           UserMapper userMapper) {
        this.userAuthProvider = userAuthProvider;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public RegistrationInfo register(SignUpDto signUpDto) {
        final User user = userMapper.toEntity(signUpDto);
        boolean success = true;
        user.setPassword(encodePassword(signUpDto.getPassword()));
        if (user.getRole().equals(Role.ADMIN)) {
            if (userRepository.existsByRole(Role.ADMIN)) {
                user.setRole(Role.POTENTIAL_ADMIN);
                success = false;
            }
        }
        userRepository.save(user);
        return new RegistrationInfo(userAuthProvider.createToken(user.getLogin(), user.getRole()), success);
    }

    @Override
    public String authorize(AuthDto authDto) {
        final User user = userRepository.findByLogin(authDto.getLogin())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such user"));
        final String requestPassword = encodePassword(authDto.getPassword());
        if (requestPassword.equals(user.getPassword())) {
            return userAuthProvider.createToken(user.getLogin(), user.getRole());
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
    }

    @Override
    public User getUserByToken(String token) {
        final String login = userAuthProvider.getLoginFromJwt(token);
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such user"));
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such user"));
    }

    @Override
    public List<String> getAllUserNames() {
        return userRepository.findAll().stream().map(User::getLogin).toList();
    }

    private String encodePassword(String password) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            return new String(messageDigest.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Couldn't encode password", e);
        }
    }
}
