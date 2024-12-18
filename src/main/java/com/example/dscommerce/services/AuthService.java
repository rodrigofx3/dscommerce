package com.example.dscommerce.services;

import com.example.dscommerce.entities.User;
import com.example.dscommerce.services.exceptions.ForbiddenException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    final
    UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public void validateSelfOrAdmin(Long userId) {

        User me = userService.authenticated();

        if (!me.getId().equals(userId) && !me.hasRole("ROLE_ADMIN")) throw new ForbiddenException("Access denied");
    }

}
