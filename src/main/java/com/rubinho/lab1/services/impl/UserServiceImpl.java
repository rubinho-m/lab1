package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.dto.AuthDto;
import com.rubinho.lab1.dto.RegisteredUserDto;
import com.rubinho.lab1.dto.SignUpDto;
import com.rubinho.lab1.jwt.UserAuthProvider;
import com.rubinho.lab1.mappers.UserMapper;
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
    public RegisteredUserDto register(SignUpDto signUpDto) {
        final User user = userMapper.toEntity(signUpDto);
        user.setPassword(encodePassword(signUpDto.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        final RegisteredUserDto registeredUserDto = userMapper.toRegisteredUserDto(user);
        registeredUserDto.setToken(userAuthProvider.createToken(user.getLogin(), user.getRole()));
        return registeredUserDto;
    }

    @Override
    public RegisteredUserDto authorize(AuthDto authDto) {
        final User user = userRepository.findByLogin(authDto.getLogin())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such user"));
        final String requestPassword = encodePassword(authDto.getPassword());
        if (requestPassword.equals(user.getPassword())) {
            final RegisteredUserDto registeredUserDto = userMapper.toRegisteredUserDto(user);
            registeredUserDto.setToken(userAuthProvider.createToken(user.getLogin(), user.getRole()));
            return registeredUserDto;
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
                .orElse(null);
    }

    @Override
    public List<String> getAllUserNames() {
        return userRepository.findAll().stream().map(User::getLogin).toList();
    }

    @Override
    public boolean requestAdminRights(String token) {
        final User user = getUserByToken(token.split(" ")[1]);
        if (userRepository.existsByRole(Role.ADMIN)) {
            user.setRole(Role.POTENTIAL_ADMIN);
            userRepository.save(user);
            return false;
        }
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return true;
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
