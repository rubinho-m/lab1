package com.rubinho.lab1.jwt;

import com.rubinho.lab1.model.Role;
import org.springframework.security.core.Authentication;

public interface UserAuthProvider {
    String createToken(String login, Role role);

    String getLoginFromJwt(String token);

    Authentication validateToken(String token);
}
