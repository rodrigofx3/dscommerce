package com.example.dscommerce.services;

import com.example.dscommerce.entities.Role;
import com.example.dscommerce.entities.User;
import com.example.dscommerce.projections.UserDetailsProjection;
import com.example.dscommerce.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        List<UserDetailsProjection> list = repository.searchUserAndRolesByEmail(email);
        if (list.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = new User();
        user.setEmail(list.getFirst().getEmail());
        user.setPassword(list.getFirst().getPassword());
        for (UserDetailsProjection p : list) {
            user.addRole(new Role(p.getRoleId(), p.getAuthority()));
        }
        return user;
    }

}
